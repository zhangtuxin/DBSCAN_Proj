
public class Point {
    public double x;
    public double y;
    public boolean clustered; // did we cluster this point or not ?

    Point(double x,double y, boolean clustered) {
        this.x = x;
        this.y = y;
        this.clustered = clustered;
    }

}