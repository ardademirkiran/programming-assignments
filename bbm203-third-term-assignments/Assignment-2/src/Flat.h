
#ifndef ASSIGNMENT2_FLAT_H
#define ASSIGNMENT2_FLAT_H
using namespace std;

class Flat {
    public:
        int id, initialBandwidth;
        Flat* nextFlat;
        Flat* prevFLat;
        int isEmpty;

        Flat(int id, int initialBandwidth){
            this->id = id;
            this->initialBandwidth = initialBandwidth;
            this->isEmpty = 0;
        }

        ~Flat(){

        }

};


#endif //ASSIGNMENT2_FLAT_H
