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
    private int id;
    private TurtleCanvas tc;
    private Location location;
    private double currentAngle;
    private boolean penDown;
    private boolean show;
    private ImageView image;

    public Turtle(int id, Location location, double orientationAngle, String imageFilePath) {
        this.id = id;
        this.location = location;
        this.currentAngle = orientationAngle;
        this.penDown = true;
        image = new ImageView(imageFilePath);
        image.setRotate(this.currentAngle);
        image.setFitHeight(15);
        image.setFitWidth(15);
    }

    void giveTurtleCanvas(TurtleCanvas tc) {
        this.tc = tc;
        image.setX(location.getX() + tc.getTRANSLATE_X());
        image.setY(location.getY() + tc.getTRANSLATE_Y());
    }

    @Override
    public void moveRelative(double distance) {
        //System.out.println("start" + currentAngle);
        boolean backward = false;
        if (distance < 0) {
            backward = true;
            distance *= -1;
        }
        QuadrantHelper quadrant = findQuadrant();
        double referenceAngle = referenceAngle(quadrant);
        //System.out.println(normalizedAngle);
        double xTranslate = MathOps.sin(referenceAngle) * distance;
        double yTranslate = MathOps.cos(referenceAngle) * distance;

        //System.out.println(""+ xTranslate + "  " +  yTranslate);
        // modifying the signs of the translations based on the quadrant the turtle is on.
        if (quadrant == QuadrantHelper.QUADRANT1) {
            yTranslate = -yTranslate;
        } else if (quadrant == QuadrantHelper.QUADRANT3) {
            xTranslate = -xTranslate;
        } else if (quadrant == QuadrantHelper.QUADRANT4) {
            xTranslate = -xTranslate;
            yTranslate = -yTranslate;
        }

        if (backward) {
            xTranslate *= -1;
            yTranslate *= -1;
        }


        //call to internal API drawPath
        //System.out.println("End" + currentAngle);
        drawOnCanvas(false, xTranslate, yTranslate);
    }

    // Provides the reference angle given the current angle
    private double referenceAngle(QuadrantHelper quadrant) {
        switch (quadrant) {
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

    private QuadrantHelper findQuadrant() {
        if (currentAngle > 180) {
            return (currentAngle > 270) ? QuadrantHelper.QUADRANT4 : QuadrantHelper.QUADRANT3;
        } else {
            return (currentAngle > 90) ? QuadrantHelper.QUADRANT2 : QuadrantHelper.QUADRANT1;
        }
    }

    @Override
    public void moveTo(Location l) {
        double x = l.getX();
        double y = l.getY();

        drawOnCanvas(true, x, y);
    }

    private void drawOnCanvas(boolean absolute, double x, double y) {
        //System.out.println("start" + currentAngle);
        Path p = new Path();
        PathElement move = new MoveTo(location.getX(), location.getY());
        p.getElements().add(move);

        if (getPenDown()) {
            PathElement line;
            line = new LineTo(x, y);
            line.setAbsolute(absolute);
            p.getElements().add(line);
        } else {
            move = new MoveTo(x, y);
            move.setAbsolute(absolute);
            p.getElements().add(move);
        }
        if (tc != null) {
            tc.drawPath(this, p);
        }
        //System.out.println("end" + currentAngle);

    }

    @Override
    public void rotate(double angle) {
        // case when right
        if (angle > 0) {
            if (currentAngle + angle >= 360) {
                double temp = 360 - currentAngle;
                currentAngle = angle - temp;
            } else {
                currentAngle += angle;
            }
            // case when left
        } else {
            if (currentAngle + angle < 0) {
                double temp = currentAngle + angle;
                currentAngle = 360 + temp;
            } else {
                currentAngle += angle;
            }
        }
        image.setRotate(currentAngle);
    }

    @Override
    public void setHeading(double angle) {
        //add angle normalization
        currentAngle = angle;
        image.setRotate(currentAngle);

        double angleDiff = (currentAngle > angle) ? currentAngle - angle : angle - currentAngle;
        //return Math.abs(angleDiff);
    }

    @Override
    public void towards(double x, double y) {
        if (location.getX() == x || location.getY() == y) {
            handleTowardsEqual(x, y);
        } else {
            handleTowardsNotEqual(x, y);
        }
    }

    private void handleTowardsNotEqual(double x, double y) {
        double temp = Math.abs(location.getX() - x) / Math.abs(location.getY() - y);
        double angleBetweenPoints = MathOps.arcTan(temp);
        double newHeading = 0;
        //When y is higher on the display then the current turtle location
        if (y < location.getY()) {
            newHeading = (x > location.getX()) ? 0 + angleBetweenPoints : 360 - angleBetweenPoints;
        } else {
            newHeading = (x > location.getX()) ? 180 - angleBetweenPoints : 180 + angleBetweenPoints;
        }
        this.setHeading(newHeading);
    }

    private void handleTowardsEqual(double x, double y) {
        double newHeading = 0.0;
        if (location.getX() == x) {
            newHeading = (location.getY() < y) ? 180 : 0;
        } else {
            newHeading = (location.getX() > x) ? 270 : 90;
        }
        this.setHeading(newHeading);
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
        image.setFitWidth(15);
        image.setFitHeight(15);
        show = true;
    }

    @Override
    public void hide() {
        image.setFitWidth(0.1);
        image.setFitHeight(0.1);
        show = false;
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
        // To handle the case when there is an effect
        int offSetMultiplier = 2;
        if (image.getEffect() != null){
            offSetMultiplier = 4;
        }
        this.location = l;
        double xOffset = -image.getBoundsInLocal().getWidth() / offSetMultiplier;
        double yOffset = -image.getBoundsInLocal().getHeight() / offSetMultiplier;
        // update the turtle image
        image.setX(location.getX() + tc.getTRANSLATE_X() + xOffset);
        image.setY(location.getY() + tc.getTRANSLATE_Y() + yOffset);
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

    @Override
    public int getId() {
        return id;
    }

//    //testing
//    public static void main(String[] args) {
//        Turtle test = new Turtle(new Location(20,20), 0.0, "slogo/view/resources/Turtle.gif");
//        test.towards(50,50);
//        //test.moveRelative(50);
//
//    }
}
