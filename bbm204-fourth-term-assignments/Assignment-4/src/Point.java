
public class Point implements Comparable<Point> {
    public double distTo;
    public Point prevPoint;
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }



    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    @Override
    public int compareTo(Point o) {
        return Double.compare(this.distTo, o.distTo);
    }

    // You can add additional variables and methods if necessary.
}
