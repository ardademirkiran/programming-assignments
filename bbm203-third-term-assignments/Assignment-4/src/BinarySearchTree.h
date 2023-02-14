#ifndef ASSIGNMENT4_BINARYSEARCHTREE_H
#define ASSIGNMENT4_BINARYSEARCHTREE_H
#include "PrimaryNode.h"
#include<string.h>
#include <cstring>

class BinarySearchTree {
public:
    PrimaryNode* root = nullptr;

    PrimaryNode* search(const char *key, PrimaryNode* nodeToCheck){
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

    PrimaryNode* insert(const char *key, PrimaryNode* nodeToCheck){
        if(nodeToCheck == nullptr){
            PrimaryNode* newPrimaryNode = new PrimaryNode(key);
            nodeToCheck = newPrimaryNode;
            return nodeToCheck;
        }
        int result = strcmp(key, nodeToCheck->key);
        if (result > 0) {
            nodeToCheck->rightNode = insert(key, nodeToCheck->rightNode);
        } else {
            nodeToCheck->leftNode = insert(key, nodeToCheck->leftNode);
        }
        return nodeToCheck;
    }
};


#endif //ASSIGNMENT4_BINARYSEARCHTREE_H
