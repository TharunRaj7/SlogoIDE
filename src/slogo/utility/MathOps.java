package slogo.utility;

/**
 * This class handles the math operations
 */
public class MathOps {
    public static double sin (double angle){
        return Math.sin(Math.toRadians(angle));
    }
    public static double cos (double angle){
        return Math.cos(Math.toRadians(angle));
    }
    public static double arcTan(double x, double y){
        double temp = x/y;
        return Math.atan(temp);
    }
//    public static double cos (double angle){
//        return Math.cos(Math.toRadians(angle));
//    }
//    public static double cos (double angle){
//        return Math.cos(Math.toRadians(angle));
//    }
//    public static double cos (double angle){
//        return Math.cos(Math.toRadians(angle));
//    }
}
