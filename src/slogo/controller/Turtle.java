package slogo.controller;

import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.utility.Location;
import slogo.utility.MathOps;
import slogo.view.element.TurtleCanvas;

public class Turtle implements ITurtle {
    private TurtleCanvas tc;
    private Location location;
    private double currentAngle;
    private boolean penDown;
    private boolean show;
    ImageView image;

    public Turtle (Location location, double orientationAngle, String imageFilePath){
        this.location = location;
        this.currentAngle = orientationAngle;
        this.penDown = true;
        image = new ImageView(imageFilePath);
        image.setFitHeight(15); image.setFitWidth(15);
    }

    public void giveTurtleCanvas(TurtleCanvas tc) {
        this.tc = tc;
        image.setX(location.getX() + tc.getTRANSLATE_X() ); image.setY(location.getY() + tc.getTRANSLATE_Y());
    }

    @Override
    public void moveRelative(double distance) {
        boolean backward = false;
        if (distance < 0){backward = true; distance *= -1;}
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

        if (backward){xTranslate *= -1; yTranslate*=-1;}


        //call to internal API drawPath
        image.setX(50); image.setY(50);
        drawOnCanvas(false, xTranslate, yTranslate);
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
        double x = l.getX();
        double y = l.getY();

        // TODO: refactor this chunk of code.
        drawOnCanvas(true, x, y);
    }

    private void drawOnCanvas(boolean absolute, double x, double y){
        Path p = new Path();
        PathElement move = new MoveTo(location.getX(), location.getY());
        p.getElements().add(move);

        if (getPenDown()){
            PathElement line;
            line = new LineTo(x, y);
            line.setAbsolute(absolute);
            p.getElements().add(line);
        }else{
            move = new MoveTo(x, y);
            move.setAbsolute(absolute);
            p.getElements().add(move);
        }
        if (tc != null) {
            tc.drawPath(p);
        }

    }

    @Override
    public void rotate(double angle) {
       // case when right
        if (angle > 0){
            if (currentAngle + angle >= 360){
                double temp = 360 - currentAngle;
                currentAngle = angle - temp;
            }else{
                currentAngle += angle;
            }
        // case when left
        }else{
            if (currentAngle + angle < 0){
                double temp = currentAngle + angle;
                currentAngle = 360 + temp;
            }else{
                currentAngle += angle;
            }
        }
        image.setRotate(currentAngle);
    }

    @Override
    public void setHeading(double angle) {
        //add angle normalization
        currentAngle = angle;
    }

    @Override
    public void towards(double x, double y) {

    }

    @Override
    public void penUp() {
        penDown = false;
    }

    @Override
    public void penDown() {
        penDown = true;
    }

    @Override
    public void show() {
        image.setFitWidth(15);image.setFitHeight(15);
    }

    @Override
    public void hide() {
        image.setFitWidth(0.1);image.setFitHeight(0.1);
    }

    @Override
    public void clear() {
        tc.clear();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public void setLocation(Location l) {
        this.location = l;
        double xOffset = -image.getBoundsInLocal().getWidth()/2;
        double yOffset = -image.getBoundsInLocal().getHeight()/2;
        image.setX(location.getX() + tc.getTRANSLATE_X() + xOffset); image.setY(location.getY() + tc.getTRANSLATE_Y() + yOffset);
    }

    @Override
    public double getHeading() {
        return currentAngle;
    }

    @Override
    public boolean getPenDown() {
        return penDown;
    }

    @Override
    public boolean getShowing() {
        return show;
    }
//
//    //testing
//    public static void main(String[] args) {
//        Turtle test = new Turtle(new Location(0,0), 45, "");
//        test.giveTurtleCanvas(new TurtleCanvas(test, ));
//        test.moveRelative(50);
//
//    }
}
