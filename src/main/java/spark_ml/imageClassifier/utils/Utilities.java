package spark_ml.imageClassifier.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * @author Ripunjoy Gohain <ripunjoygohain79@gmail.com>
 * 
 * Method for extracting pixel values
 * @input: Image, (width & height): desired size to compress
 * @output: array of doubles (pixel values)
 */

public class Utilities {

	// Method returns the grayscale pixel values of desired dimension from a given
	// image
	public static double[] ConvertGrayPixels(BufferedImage inputImage, int scaledWidth, int scaledHeight) {

		BufferedImage rescaleImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_GRAY);

		// scales the input image to the output image
		Graphics2D g2d = rescaleImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		Raster r = rescaleImage.getData();

		int[] pixels = r.getPixels(0, 0, r.getWidth(), r.getHeight(), (int[]) null);

		// directly we can get double array
		// double[] dpixels = r.getPixels(0, 0, r.getWidth(), r.getHeight(),
		// (double[])null);

		double[] pixeldoubles = Arrays.stream(pixels).asDoubleStream().toArray();

		return pixeldoubles;
	}

	// Method to get the grayscale pixel values of desired dimension from a given
	// image PATH(String)
	public static double[] ConvertGrayPixels(String path, int scaledWidth, int scaledHeight) throws IOException {

		BufferedImage inputImage = ImageIO.read(new File(path));

		BufferedImage rescaleImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_GRAY);

		// scales the input image
		Graphics2D g2d = rescaleImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		Raster r = rescaleImage.getData();

		int[] pixels = r.getPixels(0, 0, r.getWidth(), r.getHeight(), (int[]) null);

		double[] pixeldoubles = Arrays.stream(pixels).asDoubleStream().toArray();

		return pixeldoubles;
	}

	// Method to get the grayscale pixel values of desired dimension from a given
	// image PATH(File)
	public static double[] ConvertGrayPixels(File path, int scaledWidth, int scaledHeight) throws IOException {

		BufferedImage inputImage = ImageIO.read(path);

		BufferedImage rescaleImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_GRAY);

		// scales the input image
		Graphics2D g2d = rescaleImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		Raster r = rescaleImage.getData();

		int[] pixels = r.getPixels(0, 0, r.getWidth(), r.getHeight(), (int[]) null);

		double[] pixeldoubles = Arrays.stream(pixels).asDoubleStream().toArray();

		return pixeldoubles;
	}
}
