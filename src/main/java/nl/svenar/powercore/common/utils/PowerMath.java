package nl.svenar.powercore.common.utils;

public class PowerMath {

    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static int contsrain(int i, int min, int max) {
        if (i < min) {
            return min;
        }
        if (i > max) {
            return max;
        }
        return i;
    }

}
