package functions;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import util.ImageManipulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopSigns {

    public static int findStopSign(String path, boolean debug) throws IOException {
        /*
         * Creating a stopSign matrix from the directed path.
         * Also creates two copies for the eventual side by side.
         */
        Mat stopSign = ImageManipulation.imageLoader(path);
        Imgproc.resize(stopSign, stopSign, new Size(825, 550));
        Mat original = new Mat(stopSign.width(), stopSign.height(), stopSign.type());
        Mat originalWithRectangle = new Mat(stopSign.width(), stopSign.height(), stopSign.type());
        stopSign.copyTo(original);
        stopSign.copyTo(originalWithRectangle);

        double area = stopSign.size().width * stopSign.size().height;
        double epsilon = 0.0000484848 * area;
        System.out.println(stopSign.size());
        System.out.println(epsilon);
        /*
         * Isolation via colour.
         * Matrix: step1
         */
        Imgproc.cvtColor(stopSign, stopSign, Imgproc.COLOR_BGR2HSV);
        Mat mask1 = new Mat();
        Mat mask2 = new Mat();
        Core.inRange(stopSign, new Scalar(0, 120, 70), new Scalar(10, 255, 255), mask1);
        Core.inRange(stopSign, new Scalar(170, 120, 70), new Scalar(180, 255, 255), mask2);
        Mat stepOneHalf = new Mat();
        stopSign.copyTo(stepOneHalf);
        Core.add(mask1, mask2, mask1);
        Mat kernel = new Mat(3,3, CvType.CV_32F);
        Imgproc.morphologyEx(mask1, mask1, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(mask1, mask1, Imgproc.MORPH_DILATE, kernel);
        Core.bitwise_not(mask1, mask2);
        Mat res1 = new Mat();
        Mat res2 = new Mat();
        Core.bitwise_and(stopSign, stopSign, res1, mask1);
        Core.addWeighted(res1, 1, res1,1,0,stopSign);
        Mat step1 = new Mat();
        stopSign.copyTo(step1);

        /*
         * Setting to black and white.
         * Matrix: step2.
         */
        Imgproc.cvtColor(stopSign, stopSign, Imgproc.COLOR_BGR2GRAY, 0);
        Imgproc.threshold(stopSign, stopSign, 177, 200, Imgproc.THRESH_BINARY);
        Mat step2 = new Mat();
        Mat smoothed = new Mat();
        Imgproc.Canny(stopSign, stopSign, 3, 9, 7);
        stopSign.copyTo(step2);


        /*
         * Finding contours and adding a bounding rectangle to original.
         * Matrix: step3
         */
        Mat step3 = Mat.zeros(stopSign.rows(), stopSign.cols(), stopSign.type());
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        List<MatOfPoint> poly = new ArrayList<MatOfPoint>();
        Imgproc.findContours(stopSign, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (MatOfPoint contour : contours) {
            MatOfPoint2f cnt = new MatOfPoint2f();
            contour.convertTo(cnt, CvType.CV_32FC2);
            MatOfPoint2f temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(cnt, temp, 20, true);
            MatOfPoint added = new MatOfPoint();
            temp.convertTo(added, CvType.CV_32S);
            poly.add(added);
        }
        List<Rect> rectangles = new ArrayList<Rect>();
        int stopSignCount = 0;
        for (int i = 0; i < contours.size(); i++) {
            if (((int) poly.get(i).size().height) == 8) {
                Rect rect = Imgproc.boundingRect(poly.get(i));
                rectangles.add(rect);
                System.out.println(rect);
                Imgproc.drawContours(step3, poly, i, new Scalar(255, 0, 0) ,1,8, hierarchy, 0);
            }
        }
        Rect rect = new Rect();
        int areaRect = 0;
        for (int i = 0; i < rectangles.size(); i++) {
            if (rectangles.get(i).size().area() > areaRect) {
                rect = rectangles.get(i);
            }
        }
        if (!rectangles.isEmpty()) {
            stopSignCount++;
        }
        Imgproc.rectangle(originalWithRectangle, rect, new Scalar(0, 255, 0), 5);
        /*
         * Displaying matrices depending on boolean flag.
         * Returning stopSignCount for testing.
         */
        if (debug) {
            ImageManipulation.displayMat(stepOneHalf);
            ImageManipulation.displayMat(original);
            ImageManipulation.displayMat(step1);
            ImageManipulation.displayMat(step2);
            ImageManipulation.displayMat(step3);
            ImageManipulation.displayMat(originalWithRectangle);
        } else {
            ImageManipulation.displayMatSideToSide(original, originalWithRectangle);
        }
        return stopSignCount;
    }

}
