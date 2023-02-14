#ifndef ASSIGNMENT4_PRIMARYNODE_H
#define ASSIGNMENT4_PRIMARYNODE_H
#include <string>
#include "SecondaryNode.h"
#include "AVLTree.h"
#include "LLRBT.h"

using namespace std;
class PrimaryNode{
public:
    AVLTree* avlTree = new AVLTree;
    LLRBT* llrbTree = new LLRBT;
    PrimaryNode* leftNode = nullptr;
    PrimaryNode* rightNode = nullptr;
    const char *key;

    PrimaryNode(const char* categoryName){
        this->key = categoryName;
    }


};



#endif //ASSIGNMENT4_PRIMARYNODE_H
