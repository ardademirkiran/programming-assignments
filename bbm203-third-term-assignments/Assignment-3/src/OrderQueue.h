#ifndef ASSIGNMENT3_ORDERQUEUE_H
#define ASSIGNMENT3_ORDERQUEUE_H
#include "Order.h"


class OrderQueue {
    public:
        int maxLength = 0;
        Order* headOrder = nullptr;
        Order* rearOrder = nullptr;

    int isEmpty(){
        if (headOrder == nullptr){
            return 1;
        }
        return 0;
    }

    void enqueue(Order** orderNode) {
        if(isEmpty()){
            headOrder = *orderNode;
            rearOrder = *orderNode;
        } else {
            rearOrder->nextOrder = *orderNode;
            rearOrder = *orderNode;
        }
        checkMaxLength();
    }
    Order* dequeue(){
        Order* temp;
        if(isEmpty()){
            return nullptr;
        }
        else{
            temp = headOrder;
            if(headOrder == rearOrder){
                headOrder = nullptr;
                rearOrder = nullptr;
            } else headOrder = headOrder->nextOrder;

            temp->nextOrder = nullptr;
            return temp;
        }
    }

    void insert(Order** orderNode){
        if(isEmpty()) {
            headOrder = *orderNode;
            rearOrder = *orderNode;
            checkMaxLength();
            return;
        }
        if((*orderNode)->price > headOrder->price){
            (*orderNode)->nextOrder = headOrder;
            headOrder = *orderNode;
            checkMaxLength();
            return;
        }
        Order* temp = headOrder;
        while (temp->nextOrder != nullptr && temp->nextOrder->price > (*orderNode)->price){
            temp = temp->nextOrder;
        }
            (*orderNode)->nextOrder = temp->nextOrder;
            temp->nextOrder = *orderNode;
            if(temp == rearOrder) rearOrder = *orderNode;
            checkMaxLength();
    }

    void checkMaxLength(){
        int currentLength = 0;
        Order* temp = headOrder;
        while(temp != nullptr){
            currentLength++;
            temp = temp->nextOrder;
        }
        if(currentLength > maxLength){
            maxLength = currentLength;
        }
    }

};


#endif //ASSIGNMENT3_ORDERQUEUE_H
