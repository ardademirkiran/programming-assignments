

#ifndef ASSIGNMENT3_ORDER_H
#define ASSIGNMENT3_ORDER_H


class Order {
    public:
        double arrivalTime;
        double orderInterval;
        double brewInterval;
        double turnAroundTime;
        double price;
        double cashierEntry;
        double cashierExit;
        double baristaEntry;
        double baristaExit;
        Order* nextOrder = nullptr;
        Order(double startTime, double orderInterval, double brewInterval, double price){
            this->arrivalTime = startTime;
            this->orderInterval = orderInterval;
            this->brewInterval = brewInterval;
            this->price = price;
        }

        double calculateTurnAroundTime(double currenTime){

            this->turnAroundTime = currenTime - arrivalTime;
        }


};


#endif //ASSIGNMENT3_ORDER_H
