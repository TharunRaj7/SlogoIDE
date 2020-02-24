package slogo.controller;

import javafx.scene.image.ImageView;
import slogo.utility.Location;
import slogo.utility.MathOps;

public class Turtle implements ITurtle {
    Location location;
    private double currentAngle;
    ImageView image;

    public Turtle (Location location, double orientationAngle, String imageFilePath){
        this.location = location;
        this.currentAngle = orientationAngle;
        //this.image = image;
    }
    @Override
    public void moveForward(double distance) {
        QuadrantHelper quadrant = findQuadrant();
        double normalizedAngle = referenceAngle(quadrant);
        System.out.println(normalizedAngle);
        double xTranslate = MathOps.sin(normalizedAngle) * distance;
        double yTranslate = MathOps.cos(normalizedAngle) * distance;

        System.out.println(""+ xTranslate + "  " +  yTranslate);



        //change angle of the turtle as well
    }

    // Provides the reference angle given the current angle
    private double referenceAngle(QuadrantHelper quadrant) {
        switch (quadrant){
            case QUADRANT1:
                return currentAngle;
            case QUADRANT2:
                return 180 - currentAngle;
            case QUADRANT3:
                return currentAngle - 180;
            case QUADRANT4:
                return 360 - currentAngle;
            default:
                return 0;
        }
    }

    private QuadrantHelper findQuadrant(){
        if (currentAngle > 180){
            return (currentAngle > 270)? QuadrantHelper.QUADRANT4 : QuadrantHelper.QUADRANT3;
        }else{
            return (currentAngle > 90)? QuadrantHelper.QUADRANT2 : QuadrantHelper.QUADRANT1;
        }
    }

    @Override
    public void moveTo(Location l) {

    }

    @Override
    public void rotate(int angle) {

    }

    @Override
    public void setHeading(int angle) {

    }

    @Override
    public void penUp() {

    }

    @Override
    public void penDown() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void clear() {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public int getHeading() {
        return 0;
    }

    @Override
    public boolean getPenDown() {
        return false;
    }

    @Override
    public boolean getShowing() {
        return false;
    }

    //testing
    public static void main(String[] args) {
        Turtle test = new Turtle(new Location(0,0), 45, "");
        test.moveForward(50);

    }
}
