
#ifndef ASSIGNMENT3_TIMEINTERVAL_H
#define ASSIGNMENT3_TIMEINTERVAL_H
#include "Order.h"
#include "Cashier.h"
#include "Barista.h"

class TimeInterval {
    public:
        int type;
        double targetTime;
        Order* order;
        Cashier* cashier;
        Barista* barista;
        TimeInterval* nextTimeInterval;

        TimeInterval(int type){
            this->type = type;
        }


};


#endif