package functions;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;

import java.io.IOException;

import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static util.ImageManipulation.Mat2BufferedImage;
import static util.VideoHelper.createVideoReader;

public class Video {

    public static void displayVideo() throws IOException {
        Mat frame = new Mat();
        VideoCapture camera = new VideoCapture(0);
        JFrame videoFrame = createVideoReader();
        videoFrame.setVisible(true);

        while (true) {
            if (camera.read(frame)) {
                Mat output = processVideo(frame);
                ImageIcon image = new ImageIcon(Mat2BufferedImage(output));
                ((JLabel) videoFrame.getContentPane()).setIcon(image);
                videoFrame.getContentPane().repaint();
            }
        }
    }

    public static Mat processVideo(Mat frame) {
        cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        frame.convertTo(frame, -1, 2, 0);
        BackgroundSubtractorMOG2 bgs = org.opencv.video.Video.createBackgroundSubtractorMOG2();
        Mat fgMask = new Mat();
        bgs.apply(frame, fgMask);
        Mat output = new Mat();
        frame.copyTo(output, fgMask);
        return output;
    }
}
