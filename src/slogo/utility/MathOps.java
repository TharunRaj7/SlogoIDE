package slogo.utility;

import java.util.Random;

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
    public static double tan (double angle) { return Math.tan(Math.toRadians(angle));}
    public static double arcTan(double angle){
        return Math.atan(angle);
    }
    public static double sum (double x, double y){
        return x + y;
    }
    public static double difference (double x, double y){
        return (x > y)? x-y : y-x;
    }
    public static double product (double x, double y){
        return x*y;
    }
    public static double quotient (double x, double y){
        return x/y;
    }
    public static double remainder(double x, double y){
        return x%y;
    }
    public static double minus (double x){
        return -x;
    }
    public static double log (double value){
        return Math.log(value);
    }
    public static double pow (double base, double exp){
        return Math.pow(base, exp);
    }
    public static double random (int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }
    public static double getPI (){
        return Math.PI;
    }
}
