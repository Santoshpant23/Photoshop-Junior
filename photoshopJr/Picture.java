package photoshopJr;




/**
 * Staring Point Code for Image Processing Project
 * @author Richard Wicentowski and Tia Newhall (2005)
 * 
 * 2019 version
 * 
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

public class Picture {

    public static int COLOR = BufferedImage.TYPE_INT_RGB;
    public static int GRAY = BufferedImage.TYPE_BYTE_GRAY;

    static int defaultImageType = BufferedImage.TYPE_INT_RGB;

    int imageType;
    BufferedImage bufferedImage;
    WritableRaster raster;

    public Picture() { 
        imageType = defaultImageType;
        bufferedImage = null;
        raster = null;
    }

    /**
     * Create a new picture of the given height and width
     * @param width the width of the picture
     * @param height the height of the picture
     */
    public Picture(int width, int height) {
        this(width, height, defaultImageType);
    }

    Picture(int width, int height, int type) {
        bufferedImage = new BufferedImage(width, height, type);
        raster = bufferedImage.getRaster();
        imageType = type;
    }

    /**
     * Read the image in the file with the given filename
     * @param filename
     */
    public Picture(String filename) {
        this(filename, defaultImageType);
    }
    
//    /**
//     * Read the image from the given file
//     * @param file
//     */
//    public Picture(File file){
//        this(file.getAbsolutePath(), defaultImageType);
//    }

    Picture(String filename, int type) {
        if ((type != COLOR) && (type != GRAY)) { type = defaultImageType; }
        imageType = type;
        load(filename);
    }

    int getImageType() { return imageType; }

    /**
     * Get the width of the image in pixels
     * @return the width of the image in pixels
     */
    public int getWidth() { return bufferedImage.getWidth(); }
    /**
     * Get the height of the image in pixels
     * @return the height of the image in pixels
     */
    public int getHeight() { return bufferedImage.getHeight(); }

    public BufferedImage getBufferedImage() { return bufferedImage; }
    WritableRaster getRaster() { return raster; }

    void load(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        Image image = icon.getImage();
        bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null),
                imageType);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        raster = bufferedImage.getRaster();
    }

    /**
     * Replace the given picture with the current picture.
     * <p>
     * For example:
     * </p>
     * <p>
     * pic1.copy(pic2);
     * </p>
     * <p>
     * replaces pic1 with pic2.
     * </p>
     * <p>
     * Note that this is the <b>only</b> effective way of changing the dimensions of an image.
     * </p>
     * @param p The picture to replace the current picture with.
     */
    public void copy(Picture p) {
        imageType = p.getImageType();

        bufferedImage = new BufferedImage(p.getWidth(),
                p.getHeight(),
                imageType);

        raster = bufferedImage.getRaster();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                // reversing coordinates
                setPixel(y,x, p.getPixel(y, x));
            }
        }
    }

    private void boundsCheck(int row, int col) {
        if (row>=raster.getHeight() || col>=raster.getWidth()) {
            throw new IndexOutOfBoundsException("row "+row+
                    ", col "+col+" is out of bounds! "+
                    "number of rows is "+raster.getHeight()+
                    ", number of columns is "+raster.getWidth());
        
        }
    }
    
    /**
     * Get the pixel at the given x and y coordinates of the image.
     * @param x The x coordinate of the desired pixel
     * @param y The y coordinate of the desired pixel
     * @return The pixel at the given x and y coordinates.
     */
    public Pixel getPixel(int row, int col) {
        int[] pixelArray = null;

        try {
            // the raster does (x, y) instead of (row, col)
            // so we reverse the coordinates
            boundsCheck(row, col);
            pixelArray = raster.getPixel(col, row, pixelArray);
            Pixel pixel = new Pixel(pixelArray);

            return pixel;
            
        } catch (IndexOutOfBoundsException e) {
            //System.err.println(e.getMessage());
            processError(e);
            throw e;
        }
        
    }


    /**
     * Replaces the pixel at the given x and y coordinates with the given pixel
     * @param x The x coordinate of the pixel to be replaced
     * @param y The y coordinate of the pixel to be replaced
     * @param pixel The pixel with which to replace the pixel at the given coordinates.
     */
    public void setPixel(int row, int col, Pixel pixel) {
        int[] pixelArray = pixel.getComponents();

        try {
            boundsCheck(row, col);
            raster.setPixel(col, row, pixelArray);

        } catch (IndexOutOfBoundsException e) {
            // TODO: Process the stack trace and generate an error message
            // that is based on the code in ImageFilters
            processError(e);
            throw e;
        }
    }
    
    private static void processError(IndexOutOfBoundsException e) throws IndexOutOfBoundsException {
        List<StackTraceElement> newStack = new LinkedList<>();
    	for (StackTraceElement st : e.getStackTrace()) {
            if (st.getClassName().contains("ImageFilters")) {
                System.err.println(e.getMessage());
                System.err.println("in file "+st.getFileName()+
                        " at line "+st.getLineNumber());
            }
            if (!st.getClassName().startsWith("java")) {
            	newStack.add(st);
            }
        }
    	e.setStackTrace((StackTraceElement[])newStack.toArray(new StackTraceElement[newStack.size()]));
    }



}
