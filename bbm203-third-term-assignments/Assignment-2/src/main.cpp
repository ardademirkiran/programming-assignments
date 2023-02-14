#include <iostream>
#include <fstream>
#include "Apartment.h"
#include "Flat.h"
#include <cstring>
using namespace std;

int getSumOfBandwidthes(Apartment** headApt){
    if(headApt == NULL){return 0;}
    Apartment* tempApt = *headApt;
    int sum = 0;
    do{
        sum += tempApt->maxBandwidth;
        tempApt = tempApt->nextApartment;
    } while(tempApt != *headApt);
    return sum;
}


/* It is a function to remove apartment with the given name.
 * It traverses all apartments and if an apartment is found with the given name
 * removes it and returns the new apartment list. Otherwise, returns NULL.
 */
Apartment** removeApartment(Apartment** headApt, Apartment** tailApt, string name){
    int checkLoop = 0; // Since it is not a do/while loop I need to handle the infinite loop in case there is not an apartment with given name.
    Apartment* tempApt = *headApt;
    Apartment* aptToRemove;
    while(tempApt->nextApartment->name != name){
        if(checkLoop == 1){
            return NULL;
        }
        tempApt = tempApt->nextApartment;
        if(tempApt == *headApt){checkLoop++;};
    }
    aptToRemove = tempApt->nextApartment;
    tempApt->nextApartment = tempApt->nextApartment->nextApartment;
    if(aptToRemove == *headApt){
        *headApt = aptToRemove->nextApartment;
    }
    if (aptToRemove == *tailApt){
        *tailApt = tempApt;
    }
    delete aptToRemove; //calls the destructor of Apartment object and deletes its fields internally
    return headApt;
}


/* It is a recursive function to relocate given flats to given apartment.
 * It takes flat id's as an integer array and works on them one by one.
 * First it finds the target flat which is the flat where we will position the other flats next to it.
 * After that it finds the flat that to be relocated. When all the relocation process are completed,
 * it calls itself again with next flat's index that to be relocated in the array.
 */
void relocateFlats(Apartment** headApt, Apartment** tailApt, Apartment** targetApt, int targetFlatId, int numIndex, int flatsArray[]) {
    if (!flatsArray[numIndex]){ //base case of the recursion process. It means there is no remaining flats to relocate.
        return;
    };
    Apartment* tempApt = *headApt;
    do {
        Flat* targetFlat = (*targetApt)->headFlat;
        while (targetFlat->id != targetFlatId){
            targetFlat = targetFlat->nextFlat;
        }
        Flat* tempFlat = tempApt->headFlat;
        while (tempFlat != NULL) {
            if (tempFlat->id == flatsArray[numIndex]){
                if(tempFlat == tempApt->headFlat){tempApt->headFlat = tempFlat->nextFlat;}
                if(tempFlat->prevFLat) {tempFlat->prevFLat->nextFlat = tempFlat->nextFlat;}
                if(tempFlat->nextFlat){tempFlat->nextFlat->prevFLat = tempFlat->prevFLat;}
                tempFlat->prevFLat = targetFlat->prevFLat;
                if(targetFlat->prevFLat){targetFlat->prevFLat->nextFlat = tempFlat;}
                targetFlat->prevFLat = tempFlat;
                tempFlat->nextFlat = targetFlat;
                if((*targetApt)->headFlat == targetFlat){(*targetApt)->headFlat = tempFlat;}
                tempApt->maxBandwidth -= tempFlat->initialBandwidth;
                (*targetApt)->maxBandwidth += tempFlat->initialBandwidth;

                return relocateFlats(headApt, tailApt, targetApt, targetFlatId, numIndex +1, flatsArray);
            }
            tempFlat = tempFlat->nextFlat;
        }
        tempApt = tempApt->nextApartment;
    } while(tempApt != *headApt);
}


/*
 * Function to find the apartment with given name by traversing.
 */
Apartment* getApartment(Apartment** headApt, string name){
    int checkLoop = 0;
    Apartment* tempApt = *headApt;
    do{
        tempApt = tempApt->nextApartment;
        if(checkLoop == 2){return NULL;}
        if(tempApt == *headApt){checkLoop++;}
    }
    while(tempApt->name != name);
    return tempApt;
}

/*
 * A function the merge two apartments.
 */
Apartment** mergeTwoApartments(Apartment** headApt, string nameApt1, string nameApt2) {
    Apartment *apt1 = getApartment(headApt, nameApt1);
    Apartment *apt2 = getApartment(headApt, nameApt2);
    Flat *tempFlat = apt1->headFlat;
    if (apt1->headFlat == NULL) {
        apt1->headFlat = apt2->headFlat;
    } else {

        while (tempFlat->nextFlat != NULL) { tempFlat = tempFlat->nextFlat; }

        tempFlat->nextFlat = apt2->headFlat;
    }
    apt1->maxBandwidth += apt2->maxBandwidth;
    apt2->headFlat = NULL;
    return headApt;
}

/*
 * A function to insert a new apartment to apartments list.
 * It uses a flag for inserting new apartment before or after the given apartment.
 * Finds the target apartment by traversing then inserts new apartment before or after target apartment by using flag.
 */
void insertApartment(Apartment** headApt, Apartment** tailApt, string name, int maxBandwidth, int funcFlag, string targetName){
    Apartment* newApt = new Apartment(name, maxBandwidth);
    switch (funcFlag){
        case 0: {
            if (!(*headApt)) {
                *headApt = newApt;
                *tailApt = newApt;
                newApt->nextApartment = newApt;
            } else {
                newApt->nextApartment = *headApt;
                (*tailApt)->nextApartment = newApt;
                *headApt = newApt;
            }
            break;
        }
        case 1: {
            Apartment *tempApt = *headApt;
            while (tempApt->nextApartment->name != targetName) { tempApt = tempApt->nextApartment; }
            if (tempApt->nextApartment == *headApt) {
                *headApt = newApt;
            }
            newApt->nextApartment = tempApt->nextApartment;
            tempApt->nextApartment = newApt;
            break;
        }
        case 2:{
            Apartment *tempApt = *headApt;
            while(tempApt->name != targetName){
                tempApt = tempApt->nextApartment;
            }
            newApt->nextApartment = tempApt->nextApartment;
            tempApt->nextApartment = newApt;
            if (newApt->nextApartment == *headApt){
                *tailApt = newApt;
            }
            break;
        }

    }
}

string listApartment(Apartment** headApt){
    string outputText = "";
    Apartment* tempApt = *headApt;
    do{
        outputText += tempApt->name + "(" + to_string(tempApt->maxBandwidth )+ ")\t";
        Flat* tempFlat = tempApt->headFlat;
        while(tempFlat  != NULL){
            outputText += "Flat" + to_string(tempFlat->id) + "(" + to_string(tempFlat->initialBandwidth) + ")\t";
            tempFlat = tempFlat->nextFlat;
        }
        tempApt = tempApt->nextApartment;
        outputText += "\n";
    } while(tempApt != *headApt);
    return outputText;
}


/*
 * It is a function to split strings with given delimiter.
 * Returns splitted string as a string array.
 */
string* splitString(string stringToSplit, char* delimiter){
    string* lineArray = new string[100];
    int stringIndex = 0;
    char *pSplitted;
    char *pLine = &stringToSplit[0];
    pSplitted = strtok(pLine, delimiter);
    while (pSplitted != NULL) {
    lineArray[stringIndex] = pSplitted;
    stringIndex++;
    pSplitted = strtok (NULL, delimiter);
    }
    return lineArray;
}
int main(int argc, char *argv[]) {
    Apartment* headApt = NULL;
    Apartment* tailApt = NULL;
    string line;
    ifstream inputFile(argv[1]);
    ofstream outputFile(argv[2]);
    while (getline (inputFile, line)) {
        if(line.substr(line.size() - 1, line.size()) == "\r" || line.substr(line.size() - 2, line.size()) == "\n" ){
            line = line.substr(0, line.size() - 1);
        }
        string* splittedStringArray = splitString(line, "\t");
            if(splittedStringArray[0] == "add_apartment"){
                if(splittedStringArray[2] == "head"){
                    insertApartment(&headApt, &tailApt, splittedStringArray[1], stoi(splittedStringArray[3]), 0, "");
                } else {
                    int flag = 0;
                    string* splittedPosition = splitString(splittedStringArray[2], "_");
                    flag = splittedPosition[0] == "before" ? 1 : 2;
                    insertApartment(&headApt, &tailApt, splittedStringArray[1], stoi(splittedStringArray[3]), flag, splittedPosition[1]);
                }
            } else if(splittedStringArray[0] == "add_flat"){
                Apartment* apartmentToAddFlat = getApartment(&headApt, splittedStringArray[1]);
                if(splittedStringArray[2] == "0"){
                    apartmentToAddFlat->insertHeadFlat(stoi(splittedStringArray[4]), stoi(splittedStringArray[3]));
                }else {
                    apartmentToAddFlat->insertFlat(stoi(splittedStringArray[4]), stoi(splittedStringArray[3]),stoi(splittedStringArray[2]));
                }
            } else if(splittedStringArray[0] == "remove_apartment"){
                if(!removeApartment(&headApt, &tailApt, splittedStringArray[1])){
                    outputFile << "There is no such apartment!\n";
                };
            } else if(splittedStringArray[0] == "make_flat_empty"){
                Apartment* apartmentToEmpty = getApartment(&headApt, splittedStringArray[1]);
                apartmentToEmpty->makeFlatEmpty(stoi(splittedStringArray[2]));
            } else if(splittedStringArray[0] == "find_sum_of_max_bandwidths"){
                outputFile << "sum of bandwidth: " << getSumOfBandwidthes(&headApt) << endl;
                outputFile << endl;
            } else if(splittedStringArray[0] == "merge_two_apartments"){
                mergeTwoApartments(&headApt, splittedStringArray[1], splittedStringArray[2]);
                removeApartment(&headApt, &tailApt, splittedStringArray[2]);
            } else if(splittedStringArray[0] == "relocate_flats_to_same_apartment"){
                string* flatsArray = splitString(splittedStringArray[3].substr(1, splittedStringArray[3].size() - 2), ",");
                int* intFlatsArray = new int[100];
                for(int i = 0; i < 100; i++){
                        if(flatsArray[i] != "") {
                            intFlatsArray[i] = stoi(flatsArray[i]);
                        } else{
                            break;
                        }
                }
                Apartment* targetApartment = getApartment(&headApt, splittedStringArray[1]);
                relocateFlats(&headApt, &tailApt, &targetApartment, stoi(splittedStringArray[2]), 0,  intFlatsArray);
            } else if(splittedStringArray[0] == "list_apartments"){
                outputFile << listApartment(&headApt);
                outputFile << endl;
            }
   }


    inputFile.close();
    outputFile.close();







    return 0;
}
