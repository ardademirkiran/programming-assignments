#include <iostream>
#include <fstream>
#include <cstring>

using namespace std;


void moveKeyMatrix(int *currentPosition, int direction, int horizontalSize, int verticalSize,  // function to move the key matrix by using results of dot product operations
                   int keyMatrixSize) {                                                        // which calculated by function calculateScore()
    switch (direction) {
        case 1:
            if ((currentPosition[0] - keyMatrixSize) < 0) {
                currentPosition[0] += keyMatrixSize;
            } else {
                currentPosition[0] -= keyMatrixSize;
            }
            break;
        case 3:
            if ((currentPosition[1] + keyMatrixSize + keyMatrixSize - 1) > horizontalSize) {
                currentPosition[1] -= keyMatrixSize;
            } else {
                currentPosition[1] += keyMatrixSize;
            }
            break;
        case 2:
            if ((currentPosition[0] + keyMatrixSize + keyMatrixSize - 1) > verticalSize + 1) {
                currentPosition[0] -= keyMatrixSize;
            } else {
                currentPosition[0] += keyMatrixSize;
            }
            break;
        case 4:
            if ((currentPosition[1] - keyMatrixSize) < 0) {
                currentPosition[1] += keyMatrixSize;
            } else {
                currentPosition[1] -= keyMatrixSize;
            }
            break;
    }
}

int calculateScore(int *mapMatrix[], int *keyMatrix[], int *currentPosition, // function to calculate result of the dot product of key and map matrixes
                   int keyMatrixSize) {
    int score = 0;
    for (int rowIndex = 0; rowIndex < keyMatrixSize; rowIndex++) {
        for (int columnIndex = 0; columnIndex < keyMatrixSize; columnIndex++) {
            score += mapMatrix[currentPosition[0] + rowIndex][currentPosition[1] + columnIndex] * keyMatrix[rowIndex][columnIndex];
        }
    }
    return score;
}


void createMatrix(int *matrixPtr[], string matrixFilePath, int verticalSize,  // function to create pointer arrays for key and map matrixes, reads input file line by line,
                  int horizontalSize) {                                       // splits each line and puts them into a 2d array which is pointed by first parameter
    ifstream matrixFile(
            matrixFilePath);
    int rowIndex = 0;
    int columnIndex = 0;
    string lineOfMatrix;
    while (getline(matrixFile, lineOfMatrix)) {
        int *rowPtr = new int[horizontalSize]; // creates a new pointer for each row
        char *pSplitted;
        char *pLine = &lineOfMatrix[0];
        pSplitted = strtok(pLine, " ");
        while (pSplitted != NULL) {
            rowPtr[columnIndex] = stoi(pSplitted);
            pSplitted = strtok(NULL, " ");
            columnIndex++;
        }
        columnIndex = 0;
        matrixPtr[rowIndex] = rowPtr;
        rowIndex++;
        delete pSplitted;
    }
    matrixFile.close();

}

string* splitSystemArgument(string mapSizeInput) {    //function to split the map size argument by using "x" as separator
    string* sizePtr;
    sizePtr = new string[2];
    int indexOfSeparator = mapSizeInput.find("x");
    string verticalSize = mapSizeInput.substr(0, indexOfSeparator);
    string horizontalSize = mapSizeInput.substr(indexOfSeparator + 1, mapSizeInput.length() + 1);
    sizePtr[0] = verticalSize;
    sizePtr[1] = horizontalSize;
    return sizePtr;
}


string search(int *mapMatrix[], int *keyMatrix[], int *currentPosition, int horizontalSize, int verticalSize,       // a recursive function to organize other functions,
              int keyMatrixSize, string outputText) {                                                               // checks score and if treasure is found returns the output text,
    int score = calculateScore(mapMatrix, keyMatrix, currentPosition, keyMatrixSize);                               // otherwise calls itself again and continues searching for treasure
    outputText += to_string(currentPosition[0] + ((int) (keyMatrixSize / 2))) + "," +
                  to_string(currentPosition[1] + ((int) (keyMatrixSize / 2))) + ":" + to_string(score) + "\n";
    if (score % 5 == 0) {
        return outputText;
    } else {
        moveKeyMatrix(currentPosition, score % 5, horizontalSize, verticalSize, keyMatrixSize);
        return search(mapMatrix, keyMatrix, currentPosition, horizontalSize, verticalSize, keyMatrixSize, outputText);
    }

}

void clearPointerArray(int *array[], int verticalSize) { //function to deallocate pointer arrays
    for (int rowIndex = 0; rowIndex <= verticalSize; rowIndex++) {
        delete[] array[rowIndex];
    }
}

int main(int argc, char *argv[]) {
    string *sizePtr = splitSystemArgument(argv[1]);;
    int verticalSize = stoi(sizePtr[0]) - 1;
    int horizontalSize = stoi(sizePtr[1]) - 1;
    int keyMatrixSize = stoi(argv[2]);
    int *ptrPosition = new int[2]; // a pointer to keep position of the key matrix's left-top cell
    int *mapMatrixPtr[verticalSize]; // a pointer to an array containing pointers, keeps the map matrix
    int *keyMatrixPtr[keyMatrixSize]; // a pointer to an array containing pointers, keeps the key matrix
    createMatrix(mapMatrixPtr, argv[3], verticalSize, horizontalSize);
    createMatrix(keyMatrixPtr, argv[4], keyMatrixSize, keyMatrixSize);
    ptrPosition[0] = 0;
    ptrPosition[1] = 0;
    ofstream outputFile(argv[5]);
    string outputText = "";
    outputText = search(mapMatrixPtr, keyMatrixPtr, ptrPosition, horizontalSize, verticalSize, keyMatrixSize,
                        outputText);
    outputFile << outputText;
    outputFile.close();
    clearPointerArray(mapMatrixPtr, verticalSize);
    clearPointerArray(keyMatrixPtr, keyMatrixSize - 1);
    delete[] ptrPosition;
    delete[] sizePtr;

    return 0;
}
