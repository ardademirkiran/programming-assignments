#ifndef ASSIGNMENT3_CASHIER_H
#define ASSIGNMENT3_CASHIER_H

#include "OrderQueue.h"


class Cashier {
public:
    double workBeginningTime = 0;
    double totalUtilization = 0;
    int available;
    int connectedBaristaIndex;

    Cashier(int cashierIndex){
        this->available = 1;
        this->connectedBaristaIndex = int(cashierIndex / 3);
    }

    void takeOrder(double workDoneTime){
        this->available = 1;
        this->totalUtilization += workDoneTime - workBeginningTime;

    }
};


#endif //ASSIGNMENT3_CASHIER_H
