
#ifndef ASSIGNMENT3_BARISTA_H
#define ASSIGNMENT3_BARISTA_H


#include "Order.h"
#include "OrderQueue.h"

class Barista {
    public:
        double workBeginningTime = 0;
        double totalUtilization = 0;
        OrderQueue* takenOrders = new OrderQueue();
        int available;

        Barista(){
            this->available = 1;
        }

        void brew(double workDoneTime){
            this->available = 1;
            this->totalUtilization += workDoneTime - workBeginningTime;
        }
};


#endif
