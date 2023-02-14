#ifndef ASSIGNMENT4_LLRBT_H
#define ASSIGNMENT4_LLRBT_H
#include <cstring>


class LLRBT{
public:
    SecondaryNode* root = nullptr;

    int findMaxHeight(SecondaryNode* root) {
        if (root == NULL) {
            return 0;
        }

        int leftHeight = findMaxHeight(root->leftNode);
        int rightHeight = findMaxHeight(root->rightNode);

        return max(leftHeight, rightHeight) + 1;
    }

    bool isRed(SecondaryNode* node){
        if(node == nullptr){
            return false;
        }
        return node->isRed;
    }
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

    SecondaryNode* insert(const char* key, SecondaryNode* node, int price){
        if (node == nullptr){
            SecondaryNode* newNode = new SecondaryNode(key, price);
            if(node == root){
                newNode->isRed = false;
            }
            node = newNode;
            return node;
        }

        int result = strcmp(key, node->key);
        if (result > 0) {
            node->rightNode = insert(key, node->rightNode, price);
        } else {
            node->leftNode = insert(key, node->leftNode, price);
        }
        node = manageBalance(node);
        this->root->isRed = false;
        return node;
    }

    SecondaryNode* manageBalance(SecondaryNode* Node){
        if(!isRed(Node->leftNode) && isRed(Node->rightNode)){
            Node = rotateLeft(Node);
            //rotateleft
        }
        if(isRed(Node->leftNode) && isRed(Node->leftNode->leftNode)){
            Node = rotateRight(Node);
            //rotateRight
        }
        if(isRed(Node->leftNode) && isRed(Node->rightNode)){
            Node = flipColors(Node);
            //flipColors
        }
        return Node;
    }

    SecondaryNode* rotateLeft(SecondaryNode* Node){
        SecondaryNode* rightNode = Node->rightNode;
        Node->rightNode = rightNode->leftNode;
        rightNode->leftNode = Node;
        if(Node == this->root) root = rightNode;
        rightNode->isRed = Node->isRed;
        Node->isRed = true;
        return rightNode;
    }

    SecondaryNode* rotateRight(SecondaryNode* Node){
        SecondaryNode* leftNode = Node->leftNode;
        Node->leftNode = leftNode->rightNode;
        leftNode->rightNode= Node;
        if(Node == this->root) root = leftNode;
        leftNode->isRed = Node->isRed;
        Node->isRed = true;
        return leftNode;
    }

    SecondaryNode* flipColors(SecondaryNode* Node){
        Node->rightNode->isRed = false;
        Node->leftNode->isRed = false;
        Node->isRed = true;
        return Node;
    }

    SecondaryNode* moveRedLeft(SecondaryNode* Node){
        Node = flipColors(Node);
        if (isRed(Node->rightNode->leftNode)) // isRed(h.right.left)
        {
            Node->rightNode = rotateRight(Node->rightNode);
            Node = rotateLeft(Node);
            Node = flipColors(Node);
        }
        return Node;
    }
    SecondaryNode* moveRedRight(SecondaryNode* Node){
        Node = flipColors(Node);
        if (isRed(Node->leftNode->leftNode))
        {
            Node = rotateRight(Node);
            Node = flipColors(Node);
        }
        return Node;
    }

    SecondaryNode* findInorderSuccessor(SecondaryNode* node){
        SecondaryNode* searcherNode = node->rightNode;
        while(searcherNode->leftNode != nullptr){
            searcherNode = searcherNode->leftNode;
        }
        return searcherNode;
    }

    SecondaryNode* deleteMin(SecondaryNode* h) {
        if (h->leftNode == nullptr) {
            delete h;
            return nullptr;
        }
        if (!isRed(h->leftNode) && !isRed(h->leftNode->leftNode)) {
            h = moveRedLeft(h);
        }
        h->leftNode = deleteMin(h->leftNode);
        return manageBalance(h);
    }

    SecondaryNode* deleteNode(SecondaryNode* h, const char* key) {
        if (strcmp(key, h->key) < 0) {
            if (!isRed(h->leftNode) && !isRed(h->leftNode->leftNode)) {
                h = moveRedLeft(h);
            }
            h->leftNode = deleteNode(h->leftNode, key);
        } else {
            if (isRed(h->leftNode)) {
                h = rotateRight(h);
            }
            if (strcmp(key, h->key) == 0 && (h->rightNode == nullptr)) {
                delete h;
                return nullptr;
            }
            if (!isRed(h->rightNode) && !isRed(h->rightNode->leftNode)) {
                h = moveRedRight(h);
            }
            if (strcmp(key, h->key) == 0) {
                SecondaryNode* x = findInorderSuccessor(h);
                h->key = x->key;
                h->rightNode = deleteMin(h->rightNode);
            } else {
                h->rightNode = deleteNode(h->rightNode, key);
            }
        }
        return manageBalance(h);
    }


};


#endif //ASSIGNMENT4_LLRBT_H
