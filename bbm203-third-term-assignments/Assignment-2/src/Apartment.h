#ifndef ASSIGNMENT2_APARTMENT_H
#define ASSIGNMENT2_APARTMENT_H
#include "Flat.h"
#include <string>

using namespace std;
class Apartment {
    public:
        string name;
        int maxBandwidth;
        Apartment* nextApartment;
        Flat* headFlat;
        Apartment(string name, int maxBandwidth){
            this->name = name;
            this->maxBandwidth = maxBandwidth;
            this->nextApartment = NULL;
            this->headFlat = NULL;
        }

        ~Apartment(){
            Flat* tempFlat;
            while(headFlat != NULL){
                tempFlat = headFlat->nextFlat;
                delete headFlat;
                headFlat = tempFlat;
            }

        }

        void insertHeadFlat(int flatId, int initialBandwidth){
            Flat* newFLat = new Flat(flatId, initialBandwidth);
            if (this->headFlat == NULL){
                this->headFlat = newFLat;
                newFLat->prevFLat = NULL;
            } else {
                newFLat->nextFlat = this->headFlat;
                this->headFlat->prevFLat = newFLat;
                this->headFlat = newFLat;
            }
            int bwDiff = maxBandwidth - getSumOfBandwidths();
            if(bwDiff < 0){
                newFLat->initialBandwidth += bwDiff;
                if(newFLat->initialBandwidth == 0){newFLat->isEmpty = 1;}
            }
        }

        void insertFlat(int flatId, int initialBandwidth, int index){
            Flat* newFLat = new Flat(flatId, initialBandwidth);
            Flat* tempFlat = this->headFlat;
            for(int i = 0; i < index - 1; i++){
                tempFlat = tempFlat->nextFlat;
            }
            newFLat->nextFlat = tempFlat->nextFlat;
            tempFlat->nextFlat = newFLat;
            newFLat->prevFLat = tempFlat;
            if (newFLat->nextFlat){
                newFLat->nextFlat->prevFLat = newFLat;
            }
            int bwDiff = maxBandwidth - getSumOfBandwidths();
            if(bwDiff < 0){
                newFLat->initialBandwidth += bwDiff;
                if(newFLat->initialBandwidth == 0){newFLat->isEmpty = 1;}
            }


        }

        void makeFlatEmpty(int flatId){
            Flat* tempFlat = headFlat;
            while (tempFlat->id != flatId){
                tempFlat = tempFlat->nextFlat;
            }
            tempFlat->initialBandwidth = 0;
            tempFlat->isEmpty = 1;
        }

        int getSumOfBandwidths(){
            int sumOfBandwidths = 0;
            Flat* tempFlat = headFlat;
            while(tempFlat != NULL){
                sumOfBandwidths += tempFlat->initialBandwidth;
                tempFlat = tempFlat->nextFlat;
            }
            return sumOfBandwidths;
        }
};


#endif //ASSIGNMENT2_APARTMENT_H
