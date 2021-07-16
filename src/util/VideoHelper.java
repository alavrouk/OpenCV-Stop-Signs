package util;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;

public class VideoHelper {

    public static JFrame createVideoReader() {
        VideoCapture camera = new VideoCapture(0);
        JFrame jframe = new JFrame("Video");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(500, 500);
        return jframe;
    }
}
