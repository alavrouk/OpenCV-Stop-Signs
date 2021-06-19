import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import util.ImageManipulation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class MyTests {
    private static HashMap<String, Integer> stopMap = new HashMap<>();

    @Before
    public void populateStopMap() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String extension = "resources/stopsigns/";
        stopMap.put(extension+"stop1.png", 1);
        stopMap.put(extension+"stop2.png", 1);
        stopMap.put(extension+"stop3.png", 1);
        stopMap.put(extension+"stop4.png", 1);
    }

    @Test
    public void stopSignTester() throws IOException, InterruptedException {
        Iterator iterator = stopMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> sign = (Map.Entry<String, Integer>)iterator.next();
            int stopSigns = functions.StopSigns.findStopSign(sign.getKey(), false);
            java.util.concurrent.TimeUnit.SECONDS.sleep(2);
            ImageManipulation.currentShowing.setVisible(false);
            ImageManipulation.currentShowing.dispose();
            assertEquals(sign.getValue().intValue(), stopSigns);
        }
    }
}
