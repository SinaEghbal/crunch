package au.edu.anu.cs.crunch;

/**
 * Created by boris on 18/05/16.
 *
 * A Facade class for the Math libraries that used the Facade design pattern
 */

public class MathFacade {
    /**
     * Uses the Math library to define a concept of equals for floats and doubles
     * @param x value to be compared with second value
     * @param y value to be compared with first value
     * @return boolean
     */
    public static boolean floatEquals(double x, double y){
        return Math.abs(x-y) < 0.0000001;
    }

    /**
     * Wrapper for Math.sin, automatically converts to degrees
     * @param x value to be calculated
     * @param deg boolean, true if value is in degrees
     * @return
     */
    public static double sin(double x, boolean deg){
        if (deg){
            x =(float) Math.toRadians(x);
        }
        return Math.sin(x);
    }

    /**
     * Wrapper for Math.cos, automatically converts to degrees
     * @param x value to be calculated
     * @param deg boolean, true if value is in degrees
     * @return
     */
    public static double cos(double x, boolean deg){
        if (deg){
            x =(float) Math.toRadians(x);
        }
        return Math.cos(x);
    }

    /**
     * Wrapper for Math.tan, automatically converts to degrees
     * @param x value to be calculated
     * @param deg boolean, true if value is in degrees
     * @return
     */
    public static double tan(double x, boolean deg){
        if (deg){
            x =(float) Math.toRadians(x);
        }
        return Math.tan(x);
    }


}
