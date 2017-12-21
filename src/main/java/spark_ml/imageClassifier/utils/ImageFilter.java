package spark_ml.imageClassifier.utils;

import java.io.File;

/**
 * @author Ripunjoy Gohain <ripunjoygohain79@gmail.com>
 * 
 * method to filter out only specific kind of images for further processing
 * 
 */

public class ImageFilter{
	
	/**
	 * Image filter will accept only the following type of files
	 * 
	 * @param jpeg,jpg,gif,png,bmp
	 * @return boolean if format is matching
	 */
	
    static String GIF = "gif";
    static String PNG = "png";
    static String JPG = "jpg";
    static String BMP = "bmp";
    static String JPEG = "jpeg";
    public static boolean accept(File file) {
        if(file != null) {
            if(file.isDirectory())
                return false;
            String extension = getExtension(file);
            if(extension != null && isSupported(extension))
                return true;
        }
        return false;
    }
    private static String getExtension(File file) {
        if(file != null) {
            String filename = file.getName();
            int dot = filename.lastIndexOf('.');
            if(dot > 0 && dot < filename.length()-1)
                return filename.substring(dot+1).toLowerCase();
        }
        return null;
    }
    private static boolean isSupported(String ext) {
        return ext.equalsIgnoreCase(GIF) || ext.equalsIgnoreCase(PNG) ||
                ext.equalsIgnoreCase(JPG) || ext.equalsIgnoreCase(BMP) ||
                ext.equalsIgnoreCase(JPEG);
    }
}