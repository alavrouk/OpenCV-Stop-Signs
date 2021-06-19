package util;

import org.opencv.core.Scalar;

public class Misc {
    public static Scalar argbtoScalar(int r, int g, int b, int a) {
        Scalar s = new Scalar(b, g, r, a);
        return s;
    }
}
