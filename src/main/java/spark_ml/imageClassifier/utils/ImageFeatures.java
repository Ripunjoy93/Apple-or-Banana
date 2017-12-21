package spark_ml.imageClassifier.utils;

/**
 * @author Ripunjoy Gohain <ripunjoygohain79@gmail.com>
 * 
 * class defining the features of an image
 * pixel values, though it ranges from 0-255 as integer, I am taking it as zero so that 
 * I can use same class after normalizing the data
 */

public class ImageFeatures {
	private double[] pixels;

	public double[] getPixels() {
		return pixels;
	}

	public void setPixels(double[] pixels) {
		this.pixels = pixels;
	}
}
