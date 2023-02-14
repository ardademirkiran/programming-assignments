#include <iostream>
#include <fstream>

using namespace std;
#include "BinarySearchTree.h"
#include "AVLTree.h"

string getString(char x)
{
    string s(1, x);

    return s;
}

string removeTabCharacter(string s){
    if(s[s.size() - 1] == '\t') {
        s = s.substr(0, s.size() - 1);
    }
    return s;
}

//This function inserts new records to this TWO PHASED BINARY SEARCH TREE.
//First it searches "category" through the PrimaryTree. If it is found, then inserts new element to related SecondaryTree
//Otherwise, if there is no PrimaryNode with that "category" key, it creates one inserts it to the Primary Tree. After that
//inserts new SecondaryNode to that new created PrimaryNode.
void insert(BinarySearchTree** bsTree, const char* category, const char* name, int price){
    PrimaryNode* primaryTreeNode = (*bsTree)->search(category, (*bsTree)->root);
    if(primaryTreeNode == nullptr){
        (*bsTree)->root = (*bsTree)->insert(category, (*bsTree)->root);
        primaryTreeNode = (*bsTree)->search(category, (*bsTree)->root);
    }
    primaryTreeNode->avlTree->root = primaryTreeNode->avlTree->insert(name, primaryTreeNode->avlTree->root, price);
    primaryTreeNode->llrbTree->root = primaryTreeNode->llrbTree->insert(name, primaryTreeNode->llrbTree->root, price);
    primaryTreeNode->llrbTree->root->isRed = false;
}

// This function removes a node from the secondary tree associated with a particular primary tree node.
// If no primary tree node with the specified "category" label exists, does nothing.
void remove(BinarySearchTree** bsTree, const char* category, const char* name){
    PrimaryNode* primaryTreeNode = (*bsTree)->search(category, (*bsTree)->root);
    if(primaryTreeNode == nullptr){
        return;
    }
    if(primaryTreeNode->llrbTree->search(name, primaryTreeNode->llrbTree->root) && primaryTreeNode->avlTree->search(name, primaryTreeNode->avlTree->root)) {
        primaryTreeNode->avlTree->root = primaryTreeNode->avlTree->deleteNode(primaryTreeNode->avlTree->root, name);
        primaryTreeNode->llrbTree->root = primaryTreeNode->llrbTree->deleteNode(primaryTreeNode->llrbTree->root, name);
    }
}

// This function updates the price of a node in the secondary tree associated with a particular primary tree node.
void updatePrice(BinarySearchTree** bsTree, const char* categoryName, const char* key, int newPrice){
    PrimaryNode* primaryNode = (*bsTree)->search(categoryName, (*bsTree)->root);
    SecondaryNode* avlNode = primaryNode->avlTree->search(key, primaryNode->avlTree->root);
    SecondaryNode* llrbTree = primaryNode->llrbTree->search(key, primaryNode->llrbTree->root);
    if(avlNode != nullptr && llrbTree != nullptr) {
        avlNode->price = newPrice;
        llrbTree->price = newPrice;
    }
}

//This recursive function writes the nodes at the same height in a Secondary Tree
string printSecondaryTree(SecondaryNode* secondaryNode, int level){
    string outputText = "";
    if(secondaryNode == nullptr){
        return "";
    }
    if(level == 0){
        outputText +=  getString('\"') + secondaryNode->key + getString('\"') + ":" + getString('\"') + to_string(secondaryNode->price) + getString('"') + ",";
    } else if(level > 0){
        outputText += printSecondaryTree(secondaryNode->leftNode, level - 1);
        outputText += printSecondaryTree(secondaryNode->rightNode, level - 1);
    }
    return outputText;
}

//This function prints all heights of a category in Primary Tree. It uses a flag to decide whether to
//print AVLTree or LLRBTree by using it.
string printCategory(PrimaryNode* primaryNode , int flag){
    string outputText = "";
    outputText += getString('\"') + primaryNode->key + getString('\"') + ":";
    if (flag == 0) {
        if (primaryNode->avlTree->root != nullptr) {
            outputText += "\n";
            for (int i = 0; i <= primaryNode->avlTree->root->height; i++) {
                outputText += "\t";
                outputText += printSecondaryTree(primaryNode->avlTree->root, i);
                if (outputText[outputText.size() - 1] == ',') outputText += "\n";
            }
        } else {
            outputText += "{}\n";
        }
    } else {
        if (primaryNode->llrbTree->root != nullptr) {
            outputText += "\n";
            int maxHeight = primaryNode->llrbTree->findMaxHeight(primaryNode->llrbTree->root);
            for (int i = 0; i <= maxHeight; i++) {
                if(i != maxHeight) outputText += "\t";
                outputText += printSecondaryTree(primaryNode->llrbTree->root, i);
                if (outputText[outputText.size() - 1] == ',') outputText += "\n";
            }
        } else {
            outputText += "{}\n";
        }
    }
    outputText = removeTabCharacter(outputText);
    return outputText;
}

// This function prints the nodes in the primary tree at a particular level.
// The function searches for nodes at the specified level and prints them by
// using the "printCategory" function.
string printPrimaryLevel(PrimaryNode* node, int level, int flag, bool& found_node) {
    if (!node) return "";

    if (level == 0) {
        found_node = true;
        return printCategory(node, flag);
    } else {
        string leftOutput = printPrimaryLevel(node->leftNode, level - 1, flag, found_node);
        string rightOutput = printPrimaryLevel(node->rightNode, level - 1, flag, found_node);
        return leftOutput + rightOutput;
    }
}

//This function makes printPrimaryLevel work height-by-height
string printAllItems(PrimaryNode* primaryNode, int flag) {
    string outputText = "";

    if (!primaryNode) return "";

    int level = 0;
    while (true) {
        bool found_node = false;
        outputText += printPrimaryLevel(primaryNode, level, flag, found_node);
        if (!found_node) break;
        ++level;
    }

    return outputText;
}

//This functions splits the given string by using given delimiter. Puts split string into an array
//Returns a pointer for that array.
string* splitString(string stringToSplit, char* delimiter){
    string* lineArray = new string[100];
    int stringIndex = 0;
    char *pSplitted;
    char *pLine = &stringToSplit[0];
    pSplitted = strtok(pLine, delimiter);
    while (pSplitted != nullptr) {
        lineArray[stringIndex] = pSplitted;
        stringIndex++;
        pSplitted = strtok (nullptr, delimiter);
    }
    return lineArray;
}

//This function searches through the given primaryNode's SecondaryTree and if an element with given key is found
//prints it.
string printItem(const char* key, PrimaryNode* primaryNode){
    string output = "";
    if(primaryNode == nullptr){
        return "\n{}\n";
    }
    SecondaryNode* nodeToSearch = primaryNode->avlTree->search(key, primaryNode->avlTree->root);
    if(nodeToSearch == nullptr){
        return "\n{}\n";
    }
    output += "\n{\n";
    output += getString('\"') + primaryNode->key + getString('\"') + ":" + "\n";
    output += "\t" + getString('\"') + nodeToSearch->key + getString('\"') + ":" + getString('\"') + to_string(nodeToSearch->price) + getString('\"');
    output += "\n}\n";
    return output;
}

int main(int argc, char *argv[]) {

    string line;
    string output1 = "";
    string output2 = "";
    ifstream inputFile(argv[1]);
    ofstream outputFile(argv[2]);
    ofstream outputFile2(argv[3]);
    BinarySearchTree* bsTree = new BinarySearchTree();
    while (getline (inputFile, line)) {
        string* splittedStringArray = splitString(line, "\t");
        if(splittedStringArray[0] == "insert"){
            insert(&bsTree, splittedStringArray[1].c_str(), splittedStringArray[2].c_str(), stoi(splittedStringArray[3]));
        } else if(splittedStringArray[0] == "remove"){
            remove(&bsTree, splittedStringArray[1].c_str(), splittedStringArray[2].c_str());
        } else if(splittedStringArray[0] == "printAllItems"){
            output1 += "command:printAllItems\n";
            output2 += "command:printAllItems\n";
            output1 += "{\n";
            output2 += "{\n";
            output1 += printAllItems(bsTree->root, 0);
            output2 += printAllItems(bsTree->root, 1);
            output1 = removeTabCharacter(output1);
            output2 = removeTabCharacter(output2);
            output1 += "}\n";
            output2 += "}\n";
        } else if(splittedStringArray[0] == "printAllItemsInCategory"){
            output1 += "command:printAllItemsInCategory\t" + splittedStringArray[1] + "\n";
            output2 += "command:printAllItemsInCategory\t" + splittedStringArray[1] + "\n";
            PrimaryNode* nodeToPrint = bsTree->search(splittedStringArray[1].c_str(), bsTree->root);
            output1 += "{\n";
            output2 += "{\n";
            output1 += printCategory(nodeToPrint, 0);
            output2 += printCategory(nodeToPrint, 1);
            output1 = removeTabCharacter(output1);
            output2 = removeTabCharacter(output2);
            output1 += "}\n";
            output2 += "}\n";
        } else if(splittedStringArray[0] == "printItem"){
            output1 += "command:printItem\t" + splittedStringArray[1] + "\t" + splittedStringArray[2];
            output2 += "command:printItem\t" + splittedStringArray[1] + "\t" + splittedStringArray[2];
            PrimaryNode* nodeToSearch = bsTree->search(splittedStringArray[1].c_str(), bsTree->root);
            output1 += printItem(splittedStringArray[2].c_str(), nodeToSearch);
            output2 += printItem(splittedStringArray[2].c_str(), nodeToSearch);
        } else if(splittedStringArray[0] == "updateData"){
            updatePrice(&bsTree, splittedStringArray[1].c_str(), splittedStringArray[2].c_str(), stoi(splittedStringArray[3]));
        } else if(splittedStringArray[0] == "find"){
            output1 += "command:find\t" + splittedStringArray[1] + "\t" + splittedStringArray[2];
            output2 += "command:find\t" + splittedStringArray[1] + "\t" + splittedStringArray[2];
            PrimaryNode* nodeToSearch = bsTree->search(splittedStringArray[1].c_str(), bsTree->root);
            output1 += printItem(splittedStringArray[2].c_str(), nodeToSearch);
            output2 += printItem(splittedStringArray[2].c_str(), nodeToSearch);
        }
    }
    outputFile << output1;
    outputFile2 << output2;
    outputFile.close();
    outputFile2.close();
    inputFile.close();







    return 0;
}
