package slogo.controller;

import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.utility.Location;
import slogo.utility.MathOps;
import slogo.view.element.TurtleCanvas;

public class Turtle implements ITurtle {
    TurtleCanvas tc;
    Location location;
    private double currentAngle;
    ImageView image;

    public Turtle (TurtleCanvas tc, Location location, double orientationAngle, String imageFilePath){
        this.tc = tc;
        this.location = location;
        this.currentAngle = orientationAngle;
        //this.image = image;
    }
    @Override
    public void moveForward(double distance) {
        QuadrantHelper quadrant = findQuadrant();
        double referenceAngle = referenceAngle(quadrant);
        //System.out.println(normalizedAngle);
        double xTranslate = MathOps.sin(referenceAngle) * distance;
        double yTranslate = MathOps.cos(referenceAngle) * distance;

        //System.out.println(""+ xTranslate + "  " +  yTranslate);
        // modifying the signs of the translations based on the quadrant the turtle is on.
        if (quadrant == QuadrantHelper.QUADRANT1){
            yTranslate = -yTranslate;
        }else if (quadrant == QuadrantHelper.QUADRANT3){
            xTranslate = -xTranslate;
        }else if (quadrant == QuadrantHelper.QUADRANT4){
            xTranslate = -xTranslate;
            yTranslate = -yTranslate;
        }


        //call to internal API drawPath
        Path p = new Path();
        PathElement line;
        line = new LineTo(xTranslate, yTranslate);
        line.setAbsolute(false);
        p.getElements().add(line);
        tc.drawPath(p);


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
    public void rotate(double angle) {
        // TODO: implement this function to inlcude all the different input cases
        if (angle > 0){
            if (currentAngle + angle >= 360){
                double temp = 360 - currentAngle;
                currentAngle = angle - temp;
            }else{
                currentAngle += angle;
            }
        }else{
            if (currentAngle + angle < 0){
                double temp = currentAngle + angle;
                currentAngle = 360 + temp;
            }else{
                currentAngle += angle;
            }
        }
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
        Turtle test = new Turtle(new TurtleCanvas(), new Location(0,0), 45, "");
        test.moveForward(50);

    }
}
