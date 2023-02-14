#include <iostream>
#include "OrderQueue.h"
#include "TimeInterval.h"
#include "TimeIntervalList.h"
#include "Cashier.h"
#include "Barista.h"
#include <cstring>
#include <fstream>
#include <cmath>


using namespace std;
void addTimeInterval(TimeIntervalList** timeIntervals, Order** order, Cashier** cashier, Barista** barista, int type, double globalTime){
    TimeInterval* timeInterval = new TimeInterval(type);
    timeInterval->order = *order;
    if(type == 0) {
        timeInterval->cashier = *cashier;
        timeInterval->targetTime = globalTime + (*order)->orderInterval;
    } else {
        timeInterval->barista = *barista;
        timeInterval->targetTime = globalTime + (*order)->brewInterval;
    }
    (*timeIntervals)->insertInterval(&timeInterval);
}

void assignBaristaM2(Barista** baristaArray, Order** order, TimeIntervalList** timeIntervals, double globalTime, Cashier** cashier){
    if(baristaArray[(*cashier)->connectedBaristaIndex]->available == 1) {
        baristaArray[(*cashier)->connectedBaristaIndex]->available = 0;
        baristaArray[(*cashier)->connectedBaristaIndex]->workBeginningTime = globalTime;
        addTimeInterval(timeIntervals, order, nullptr, &baristaArray[(*cashier)->connectedBaristaIndex], 1, globalTime);
    } else {
        baristaArray[(*cashier)->connectedBaristaIndex]->takenOrders->insert(order);
    }
}

void assignBarista(Barista** baristaArray, Order** order, TimeIntervalList** timeIntervals, OrderQueue** takenOrders , int lenBaristaArray, double globalTime){
    for(int i = 0 ; i < lenBaristaArray; i++){
        if (baristaArray[i]->available == 1){
            baristaArray[i]->available = 0;
            baristaArray[i]->workBeginningTime = globalTime;
            (*order)->baristaEntry = globalTime;
            addTimeInterval(timeIntervals, order, nullptr, &baristaArray[i], 1, globalTime);
            return;
        }
    }
    (*takenOrders)->insert(order);

}

void assignCashier(Cashier** cashierArray, Order** order, TimeIntervalList** timeIntervals, OrderQueue** pendingOrders, int lenCashierArray, double globalTime){
    if ((*order)->arrivalTime == 102.32){
    }
    for(int i = 0 ; i < lenCashierArray; i++){
        if (cashierArray[i]->available == 1){
            cashierArray[i]->available = 0;
            cashierArray[i]->workBeginningTime = globalTime;
            (*order)->cashierEntry = globalTime;
            addTimeInterval(timeIntervals, order, &cashierArray[i], nullptr, 0, globalTime);
            return;
        }
    }
    (*pendingOrders)->enqueue(order);


}

double fastForwardTimeM2(TimeIntervalList** timeIntervals, double targetTime, OrderQueue** pendingOrders, Barista** baristaArray, int lenBaristaArray, Cashier** cashierArray, int lenCashierArray, double globalTime, double** lastDeliveryTime){
    TimeInterval* temp = (*timeIntervals)->headInterval;
    while(temp != nullptr && temp->targetTime <= targetTime){
        globalTime = temp->targetTime;
        if(temp->type == 0){
            temp->cashier->takeOrder(globalTime);
            assignBaristaM2(baristaArray, &(temp->order), timeIntervals, globalTime, &(temp->cashier));
            if(!((*pendingOrders)->isEmpty())) {
                Order *newOrder = (*pendingOrders)->dequeue();
                assignCashier(cashierArray, &newOrder, timeIntervals, pendingOrders, lenCashierArray, globalTime);
            }
        } else {
            temp->barista->brew(globalTime);
            *lastDeliveryTime = &(temp->targetTime);
            temp->order->calculateTurnAroundTime(temp->targetTime);
            if(!(temp->barista->takenOrders->isEmpty())) {
                temp->barista->available = 0;
                temp->barista->workBeginningTime = globalTime;
                Order* newOrder = temp->barista->takenOrders->dequeue();
                addTimeInterval(timeIntervals, &newOrder, nullptr, &(temp->barista), 1, globalTime);
            }
        }
        temp = temp->nextTimeInterval;
    }
    (*timeIntervals)->headInterval = temp;
    globalTime = targetTime;
    return globalTime;
}


double fastForwardTime(TimeIntervalList** timeIntervals, double targetTime, OrderQueue** takenOrders, OrderQueue** pendingOrders, Barista** baristaArray, int lenBaristaArray, Cashier** cashierArray, int lenCashierArray, double globalTime, double** lastDeliveryTime){
    TimeInterval* temp = (*timeIntervals)->headInterval;
    while(temp != nullptr && temp->targetTime <= targetTime){
        globalTime = temp->targetTime;
        if(temp->type == 0){
            temp->cashier->takeOrder(globalTime);
            (temp->order)->cashierExit = globalTime;
            assignBarista(baristaArray, &(temp->order), timeIntervals, takenOrders, lenBaristaArray, globalTime);
            if(!((*pendingOrders)->isEmpty())) {
                Order *newOrder = (*pendingOrders)->dequeue();
                assignCashier(cashierArray, &newOrder, timeIntervals, pendingOrders, lenCashierArray, globalTime);
            }
        } else {
            temp->barista->brew(globalTime);
            (temp->order)->baristaExit = globalTime;
            *lastDeliveryTime = &(temp->targetTime);
            temp->order->calculateTurnAroundTime(temp->targetTime);
            if(!((*takenOrders)->isEmpty())) {

                Order *newOrder = (*takenOrders)->dequeue();
                assignBarista(baristaArray, &newOrder, timeIntervals, takenOrders, lenBaristaArray, globalTime);
            }
        }
        temp = temp->nextTimeInterval;
    }
    (*timeIntervals)->headInterval = temp;
    globalTime = targetTime;
    return globalTime;
}

string* splitString(string stringToSplit, char* delimiter){
    string* lineArray = new string[100];
    int stringIndex = 0;
    char *pSplitted;
    char *pLine = &stringToSplit[0];
    pSplitted = strtok(pLine, delimiter);
    while (pSplitted != nullptr) {
        lineArray[stringIndex] = pSplitted;
        stringIndex++;
        pSplitted = strtok (nullptr, delimiter);
    }
    return lineArray;
}

string createCashierOutput(Cashier** cashierArray, int cashierNumber, double lastDeliveryTime){
    string returnString = "";
    string cashierText = "";
    for(int i = 0; i < cashierNumber; i++){
         cashierText = to_string((round((cashierArray[i]->totalUtilization / lastDeliveryTime) * 100.0) / 100.0));
         cashierText = cashierText.substr(0, cashierText.find(".")+3);
         returnString += cashierText + "\n";
    }
    return returnString;
}

string createBaristaOutput(Barista** baristaArray, int BaristaNumber, double lastDeliveryTime){
    string returnString = "";
    string baristaText = "";
    for(int i = 0; i < BaristaNumber; i++){
        baristaText = to_string((round((baristaArray[i]->totalUtilization / lastDeliveryTime) * 100.0) / 100.0));
        baristaText = baristaText.substr(0, baristaText.find(".")+3);
        returnString += baristaText + "\n";
    }
    return returnString;
}

string createOrderOutput(Order** orderArray, int orderNumber){
    string returnString = "";
    string orderText = "";
    for(int i = 0; i < orderNumber; i++){
        orderText = to_string((round(orderArray[i]->turnAroundTime * 100) / 100));
        orderText = orderText.substr(0, orderText.find(".") + 3);
        returnString += orderText + "\n";
    }
    return returnString;
}

void cashierArrayCreator(Cashier** cashierArray, int cashierNumber){
    for(int i = 0 ; i < cashierNumber; i++){
        cashierArray[i] = new Cashier(i);
    }
}

void baristaArrayCreator(Barista** baristaArray, int baristaNumber){
    for(int i = 0 ; i < baristaNumber; i++){
        baristaArray[i] = new Barista();
    }
}

int main(int argc, char *argv[]) {
    double* lastDeliveryTime = 0;
    double* lastDeliveryTimeM2 = 0;
    double globalTime = 0;
    double globalTimeM2 = 0;
    int cashierNumber = 0;
    int baristaNumber = 0;
    int orderNumber = 0;
    TimeIntervalList* timeIntervalList = new TimeIntervalList();
    TimeIntervalList* timeIntervalListM2 = new TimeIntervalList();
    Order* completedOrders[100];
    Order* completedOrdersM2[100];
    OrderQueue* pendingOrders = new OrderQueue();
    OrderQueue* pendingOrdersM2 = new OrderQueue();
    OrderQueue* takenOrders = new OrderQueue();

    Cashier* cashierArray[100];
    Cashier* cashierArrayM2[100];
    Barista* baristaArray[100];
    Barista* baristaArrayM2[100];

    string line;
    ifstream inputFile(argv[1]);
    ofstream outputFile(argv[2]);
    int orderCounter = 0;
    int lineCounter = 0;
    while (getline (inputFile, line)) {
        if (lineCounter == 0){
            cashierNumber = stoi(line);
            baristaNumber = cashierNumber / 3;
            cashierArrayCreator(cashierArray, cashierNumber);
            cashierArrayCreator(cashierArrayM2, cashierNumber);
            baristaArrayCreator(baristaArray, baristaNumber);
            baristaArrayCreator(baristaArrayM2, baristaNumber);
            lineCounter++;
        } else if(lineCounter == 1){
            orderNumber = stoi(line);
            lineCounter++;
        } else {
            string *splittedStringArray = splitString(line, " ");
            double orderStartTime = stod(splittedStringArray[0]);
            double orderInterval = stod(splittedStringArray[1]);
            double orderBrewInterval = stod(splittedStringArray[2]);
            double orderPrice = stod(splittedStringArray[3]);
            Order *newArrivalOrder = new Order(orderStartTime, orderInterval, orderBrewInterval, orderPrice);
            Order *newArrivalOrderM2 = new Order(orderStartTime, orderInterval, orderBrewInterval, orderPrice);
            completedOrders[orderCounter] = newArrivalOrder;
            completedOrdersM2[orderCounter] = newArrivalOrderM2;
            orderCounter++;
            globalTime = fastForwardTime(&timeIntervalList, orderStartTime, &takenOrders, &pendingOrders, baristaArray,
                                         baristaNumber, cashierArray, cashierNumber, globalTime, &lastDeliveryTime);
            globalTimeM2 = fastForwardTimeM2(&timeIntervalListM2, orderStartTime, &pendingOrdersM2, baristaArrayM2,
                                             baristaNumber, cashierArrayM2, cashierNumber, globalTimeM2,
                                             &lastDeliveryTimeM2);
            assignCashier(cashierArray, &newArrivalOrder, &timeIntervalList, &pendingOrders, cashierNumber, globalTime);
            assignCashier(cashierArrayM2, &newArrivalOrderM2, &timeIntervalListM2, &pendingOrdersM2, cashierNumber,
                          globalTimeM2);
        }
    }
    globalTime = fastForwardTime(&timeIntervalList, 10000000, &takenOrders, &pendingOrders, baristaArray, baristaNumber, cashierArray, cashierNumber, globalTime, &lastDeliveryTime);
    globalTimeM2 = fastForwardTimeM2(&timeIntervalListM2, 1000000, &pendingOrdersM2, baristaArrayM2, baristaNumber, cashierArrayM2, cashierNumber, globalTimeM2, &lastDeliveryTimeM2);
    outputFile << *lastDeliveryTime << "\n";
    outputFile << pendingOrders->maxLength << "\n";
    outputFile << takenOrders->maxLength << "\n";
    outputFile << createCashierOutput(cashierArray, cashierNumber, *lastDeliveryTime);
    outputFile << createBaristaOutput(baristaArray, baristaNumber, *lastDeliveryTime);
    outputFile << createOrderOutput(completedOrders, orderNumber);
    outputFile << "\n";
    outputFile << *lastDeliveryTimeM2 << "\n";
    outputFile << pendingOrdersM2->maxLength << "\n";
    for(int i = 0; i < baristaNumber; i++){
        outputFile << baristaArrayM2[i]->takenOrders->maxLength << "\n";
    }
    outputFile << createCashierOutput(cashierArrayM2, cashierNumber, *lastDeliveryTimeM2);
    outputFile << createBaristaOutput(baristaArrayM2, baristaNumber, *lastDeliveryTimeM2);
    outputFile << createOrderOutput(completedOrdersM2, orderNumber);
    inputFile.close();
    outputFile.close();



    return 0;
}
