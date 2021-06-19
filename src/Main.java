import org.opencv.core.Core;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int foundSigns = functions.StopSigns.findStopSign("resources/stopsigns/stop5.png", true);
        System.out.println(foundSigns);
    }
}
