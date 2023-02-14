#ifndef ASSIGNMENT4_SECONDARYNODE_H
#define ASSIGNMENT4_SECONDARYNODE_H


class SecondaryNode{
public:
    bool isRed = true;
    SecondaryNode* rightNode = nullptr;
    SecondaryNode* leftNode = nullptr;
    string categoryName;
    int balance = 0;
    int height = 0;
    int price;
    const char *key;
    SecondaryNode(const char* key, int price){
        this->key = key;
        this->price = price;
    }

};


#endif //ASSIGNMENT4_SECONDARYNODE_H
