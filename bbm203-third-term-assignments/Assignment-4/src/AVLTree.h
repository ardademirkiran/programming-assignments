
#ifndef ASSIGNMENT4_AVLTREE_H
#define ASSIGNMENT4_AVLTREE_H
#include <algorithm>
#include <cstring>


using namespace std;
class AVLTree {
public:
    SecondaryNode* root = nullptr;

    SecondaryNode* search(const char *key, SecondaryNode* nodeToCheck){
        if(nodeToCheck == nullptr){
            return nullptr;
        }
        int result = strcmp(key, nodeToCheck->key);

        if (result == 0) {
            return nodeToCheck;
        } else if (result > 0) {
            return search(key, nodeToCheck->rightNode);
        } else {
            return search(key, nodeToCheck->leftNode);
        }
    }
    SecondaryNode* insert(const char *key, SecondaryNode* Node, int price){
        if(Node == nullptr){
            SecondaryNode* newPrimaryNode = new SecondaryNode(key, price);
            Node = newPrimaryNode;
            return Node;
        }

        int result = strcmp(key, Node->key);
        if (result > 0) {
            Node->rightNode = insert(key, Node->rightNode, price);
        } else {
            Node->leftNode = insert(key, Node->leftNode, price);
        }
        Node = manageHeights(Node);
        Node = manageBalance(Node);
        return Node;
    }

    SecondaryNode* rightRotate(SecondaryNode* Node){
        SecondaryNode* leftNode= Node->leftNode;
        Node->leftNode = leftNode->rightNode;
        leftNode->rightNode = Node;
        if(Node == this->root) root = leftNode;
        Node = manageHeights(Node);
        leftNode = manageHeights(leftNode);
        return leftNode;
    }

    SecondaryNode* leftRotate(SecondaryNode* Node){
        SecondaryNode* rightNode= Node->rightNode;
        Node->rightNode = rightNode->leftNode;
        rightNode->leftNode= Node;
        if(Node == this->root) root = rightNode;
        Node = manageHeights(Node);
        rightNode = manageHeights(rightNode);
        return rightNode;
    }

    SecondaryNode* manageBalance(SecondaryNode* node){
        if(node->balance >= 2){ //leftHeavy
            if (node->leftNode->balance >= 0){ //leftleftcase
                node = rightRotate(node);
            } else { //leftrightcase
                node->leftNode = leftRotate(node->leftNode);
                node = rightRotate(node);
            }
        } else if(node->balance <= -2){ //rightHeavy
            if(node->rightNode->balance <= 0){//rightrightcase
                node = leftRotate(node);
            } else {//rightleftcase
                node->rightNode = rightRotate(node->rightNode);
                node = leftRotate(node);
            }
        }
        return node;
    }
    SecondaryNode* manageHeights(SecondaryNode* node){
        int leftHeight = 0;
        int rightHeight = 0;
        if(node->leftNode == nullptr){
            leftHeight = -1;
        } else {
            leftHeight = node->leftNode->height;
        }

        if(node->rightNode == nullptr){
            rightHeight = -1;
        } else {
            rightHeight = node->rightNode->height;
        }
        node->height = max(rightHeight, leftHeight) + 1;
        node->balance = leftHeight- rightHeight;
        return node;
    }

    SecondaryNode* deleteNode(SecondaryNode* node, const char* key){
        if(node == nullptr){
            return nullptr;
        }
        int result = strcmp(key, node->key);
        if(result == 0) {
            if (node->leftNode == nullptr && node->rightNode == nullptr) {
                delete node;
                return nullptr;
            } else if(node->leftNode == nullptr){
                node = node->rightNode;
                return node;
            } else if(node->rightNode == nullptr){
                node = node->leftNode;
                return node;
            }
        }
        if (result > 0) {
            node->rightNode = deleteNode(node->rightNode, key);
        } else if(result < 0){
            node->leftNode = deleteNode(node->leftNode, key);
        } else {
            SecondaryNode* inorderSuccessor = findInorderSuccessor(node);
            node->key = inorderSuccessor->key;
            node->rightNode = deleteNode(node->rightNode, inorderSuccessor->key);
            node = manageHeights(node);
            node = manageBalance(node);

        }
        return node;


    }

    SecondaryNode* findInorderSuccessor(SecondaryNode* node){
        SecondaryNode* searcherNode = node->rightNode;
        while(searcherNode->leftNode != nullptr){
            searcherNode = searcherNode->leftNode;
        }
        return searcherNode;
    }

};


#endif //ASSIGNMENT4_AVLTREE_H
