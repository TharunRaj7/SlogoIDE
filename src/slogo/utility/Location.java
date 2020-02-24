package slogo.utility;

/**
 * TODO: Implement a utility class that holds a location.
 */
public class Location {
    private int xValue;
    private int yValue;

    public Location(int x, int y){
        xValue = x;
        yValue = y;
    }
    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    public int getxValue(){
        return xValue;
    }

    public int getyValue(){
        return yValue;
    }
}
