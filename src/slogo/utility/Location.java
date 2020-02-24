package slogo.utility;

/**
 * TODO: Implement a utility class that holds a location.
 */
public class Location {
    private double xValue;
    private double yValue;

    public Location(double x, double y){
        xValue = x;
        yValue = y;
    }
    public void setxValue(double xValue) {
        this.xValue = xValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }

    public double getxValue(){
        return xValue;
    }

    public double getyValue(){
        return yValue;
    }
}
