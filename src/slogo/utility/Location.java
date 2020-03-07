package slogo.utility;

public class Location {

    public static final Location ORIGIN = new Location(0, 0);

    private double xValue;
    private double yValue;

    public Location(double x, double y){
        xValue = x;
        yValue = y;
    }

    public Location add(Location other) {
        return new Location(xValue + other.xValue, yValue + other.yValue);
    }

    public void setX(double xValue) {
        this.xValue = xValue;
    }

    public void setY(double yValue) {
        this.yValue = yValue;
    }

    public double getX(){
        return xValue;
    }

    public double getY(){
        return yValue;
    }
}
