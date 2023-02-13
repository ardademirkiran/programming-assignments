public class Jewel {
    int[][] directionsToSearch;
    private String[] matchingJewels;
    private String letter;
    private int point;

    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public String[] getMatchingJewels() {
        return matchingJewels;
    }


    public int getPoint() {return point;}

    public void setPoint(int point) {this.point = point;}

    public String toString() {return letter;}

    public void setLetter(String letter) {this.letter = letter;}


}

class Diamond extends Jewel{
    private String[] matchingJewels = {"D"};
    private int[][] directionsToSearch = {{-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Diamond(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}
class Square extends Jewel{
    String[] matchingJewels = {"S"};
    int[][] directionsToSearch = {{0, -1}, {0, 1}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Square(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}
class Triangle extends Jewel{
    String[] matchingJewels = {"T"};
    int[][] directionsToSearch = {{-1, 0}, {1, 0}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Triangle(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}
class Wildcard extends Jewel{
    String[] matchingJewels = {"D", "W", "T", "S"};
    int[][] directionsToSearch = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1} };
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Wildcard(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}

class Empty extends Jewel{
    public Empty(String letter){
        setLetter(letter);
    }
}

class Slash extends Jewel{
    String[] matchingJewels = {"/", "-", "+", "\\", "|"};
    int[][] directionsToSearch = {{-1, 1}, {1, -1}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Slash(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}
class Dash extends Jewel{
    String[] matchingJewels = {"/", "-", "+", "\\", "|"};
    int[][] directionsToSearch = {{0, -1}, {0, 1}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Dash(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}
class Plus extends Jewel{
    String[] matchingJewels = {"/", "-", "+", "\\", "|"};
    int[][] directionsToSearch = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Plus(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}
class Backslash extends Jewel{
    String[] matchingJewels = {"/", "-", "+", "\\", "|"};
    int[][] directionsToSearch = {{-1, -1}, {1, 1}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Backslash(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}

class Bar extends Jewel{
    String[] matchingJewels = {"/", "-", "+", "\\", "|"};
    int[][] directionsToSearch = {{-1, 0}, {1, 0}};
    public String[] getMatchingJewels() {
        return matchingJewels;
    }
    public int[][] getDirectionsToSearch() {
        return directionsToSearch;
    }
    public Bar(String letter, int point){
        setLetter(letter);
        setPoint(point);
    }
}



