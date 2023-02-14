
#ifndef ASSIGNMENT3_TIMEINTERVALLIST_H
#define ASSIGNMENT3_TIMEINTERVALLIST_H
#include "TimeInterval.h"


class TimeIntervalList {
    public:
        TimeInterval* headInterval = nullptr;
        TimeInterval* rearInterval = nullptr;

        void insertInterval(TimeInterval** timeInterval){
            TimeInterval* temp = headInterval;
            if (this->headInterval == nullptr) {
                this->headInterval = *timeInterval;
                this->rearInterval = *timeInterval;
                return;
            }
            if(headInterval->targetTime > (*timeInterval)->targetTime){
                (*timeInterval)->nextTimeInterval = headInterval;
                headInterval = *timeInterval;
                return;
            }
           while(temp->nextTimeInterval != nullptr && temp->nextTimeInterval->targetTime < (*timeInterval)->targetTime){
                temp = temp->nextTimeInterval;
            }
            (*timeInterval)->nextTimeInterval = temp->nextTimeInterval;
            temp->nextTimeInterval = *timeInterval;
            if(temp == rearInterval) rearInterval = *timeInterval;
        }


};


#endif
