package util;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageManipulation {
    public static JFrame currentShowing;

    public static Mat imageLoader(String path) throws IOException {
        //TODO: add extension checker for file Extension
        Mat image = Imgcodecs.imread(path);
        if (image.empty()) {
            throw new IOException("Could not read image from path: " + path);
        }
        System.out.println("Image from path " + path + " read successfully");
        return image;
    }

    public static void displayMat(Mat mat) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage image =  javax.imageio.ImageIO.read(in);
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }

    public static void displayMatSideToSide(Mat original, Mat manipulated) throws IOException {
        BufferedImage originalImage = Mat2BufferedImage(original);
        BufferedImage manipulatedImage = Mat2BufferedImage(manipulated);


        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(originalImage)));
        frame.getContentPane().add(new JLabel(new ImageIcon(manipulatedImage)));
        frame.pack();
        frame.setVisible(true);
        currentShowing = frame;
    }

    public static BufferedImage Mat2BufferedImage(Mat mat) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        InputStream in = new ByteArrayInputStream(byteArray);
        return javax.imageio.ImageIO.read(in);
    }
}
