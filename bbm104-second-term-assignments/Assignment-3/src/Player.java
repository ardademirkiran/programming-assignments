public class Player implements Comparable<Player>{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    private int point = 0;
    public Player(String name, int point){
        this.name = name;
        this.point = point;
    }

    @Override
    public int compareTo(Player o) {
        if (this.point > o.point){
            return 1;
        } else if(this.point < o.point){
            return -1;
        } else {
            return 0;
        }
    }
 public String toString(){
        return name + " " + point;
 }

}
