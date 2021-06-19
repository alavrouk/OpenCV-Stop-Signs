package functions;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import util.ImageManipulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopSigns {

    public static int findStopSign(String path, boolean showSideBySide) throws IOException {
        Mat stopSign = ImageManipulation.imageLoader(path);
        Mat original = new Mat(stopSign.width(), stopSign.height(), stopSign.type());
        stopSign.copyTo(original);

        Mat blackAndWhite = new Mat();
        Imgproc.cvtColor(stopSign, blackAndWhite, Imgproc.COLOR_RGBA2GRAY, 0);
        Imgproc.threshold(blackAndWhite, blackAndWhite, 177, 200, Imgproc.THRESH_BINARY);

        Mat destination = Mat.zeros(blackAndWhite.rows(), blackAndWhite.cols(), blackAndWhite.type());
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        List<MatOfPoint> poly = new ArrayList<MatOfPoint>();
        Imgproc.findContours(blackAndWhite, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (MatOfPoint contour : contours) {
            MatOfPoint2f cnt = new MatOfPoint2f();
            contour.convertTo(cnt, CvType.CV_32FC2);
            MatOfPoint2f temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(cnt, temp, 12, true);
            MatOfPoint added = new MatOfPoint();
            temp.convertTo(added, CvType.CV_32S);
            poly.add(added);
        }
        int stopSignCount = 0;
        for (int i = 0; i < contours.size(); i++) {
            if (((int) poly.get(i).size().height) == 8) {
                stopSignCount++;
                Rect rect = Imgproc.boundingRect(poly.get(i));
                System.out.println(rect);
                Imgproc.rectangle(stopSign, rect, new Scalar(0, 255, 0), 5);
                Imgproc.drawContours(destination, poly, i, new Scalar(255, 0, 0) ,1,8, hierarchy, 0);
            }
        }
        if (showSideBySide) {
            ImageManipulation.displayMatSideToSide(original, stopSign);
        }
        return stopSignCount;
    }

}
