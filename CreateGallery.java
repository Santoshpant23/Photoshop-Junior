import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.security.Permission;

import javax.imageio.ImageIO;

import photoshopJr.Picture;

public class CreateGallery
{
    /*
     * XXX FIXME
     * NOTE to TAs:
     * 
     * Check the student code. For technical reasons,
     * some of the students have the Picture class in
     * a photoshopJr package, and some do not. Check
     * the imports of their ImageFilters and ImageTool
     * class and pick whichever of the following two
     * lines makes it compile.
     * 
     * 
     */
    static final String PICTURE_CLASS = "photoshopJr.Picture";
    //static final String PICTURE_CLASS = "Picture";
    
    
    static final String MATT = "bergmatt";
    static final String ROSES = "roses";
    static final String JERRY = "jerry+lawson";
    
    static File outputDir = new File("output/images");
    static File outputDir(String filename) {
        return new File(outputDir, extension(filename));
    }
    
    static final String IMAGE_FILTERS = "ImageFilters";
    
    static final Method FLIP_HORIZONTAL;
    static final Method FLIP_VERTICAL;
    
    static final Method TO_GRAYSCALE;
    static final Method ZOMBIE_VISION;
    
    static final Method MIRROR_VERTICAL;
    static final Method MIRROR_HORIZONTAL;
    
    static final Method SHIFT_RIGHT;
    static final Method SHIFT_LEFT;
    
    static final Method SCALE_UP;
    static final Method SCALE_DOWN;
    
    static final Method ROTATE_RIGHT;
    static final Method ROTATE_LEFT;
    
    static final Method COMIC;
    
    static final Method DETECT_EDGES;
    static final Method BLUR;
    
    static final Method BUTTON1;
    static final Method BUTTON2;
    //static final Method BUTTON3;

    static {
        try {
            // static initializer for looking up all of these methods to use reflection
            Class<Picture> PICTURE = (Class<Picture>) Class.forName(PICTURE_CLASS);
            FLIP_HORIZONTAL = Class.forName(IMAGE_FILTERS).getMethod("flipHorizontal", PICTURE);
            FLIP_VERTICAL = Class.forName(IMAGE_FILTERS).getMethod("flipVertical", PICTURE);
            
            TO_GRAYSCALE = Class.forName(IMAGE_FILTERS).getMethod("toGrayscale", PICTURE);
            ZOMBIE_VISION = Class.forName(IMAGE_FILTERS).getMethod("zombieVision", PICTURE);
            
            MIRROR_VERTICAL = Class.forName(IMAGE_FILTERS).getMethod("mirrorVertical", PICTURE);
            MIRROR_HORIZONTAL = Class.forName(IMAGE_FILTERS).getMethod("mirrorHorizontal", PICTURE);
            
            SHIFT_RIGHT = Class.forName(IMAGE_FILTERS).getMethod("shiftRight", PICTURE, Integer.TYPE);
            SHIFT_LEFT = Class.forName(IMAGE_FILTERS).getMethod("shiftLeft", PICTURE, Integer.TYPE);
            
            SCALE_UP = Class.forName(IMAGE_FILTERS).getMethod("scaleUp", PICTURE);
            SCALE_DOWN = Class.forName(IMAGE_FILTERS).getMethod("scaleDown", PICTURE);
            
            ROTATE_RIGHT = Class.forName(IMAGE_FILTERS).getMethod("rotateRight", PICTURE);
            ROTATE_LEFT = Class.forName(IMAGE_FILTERS).getMethod("rotateLeft", PICTURE);
            
            COMIC = Class.forName(IMAGE_FILTERS).getMethod("blackAndWhiteComic", PICTURE);
            
            DETECT_EDGES = Class.forName(IMAGE_FILTERS).getMethod("detectEdges", PICTURE);
            BLUR = Class.forName(IMAGE_FILTERS).getMethod("blur", PICTURE);
            
            BUTTON1 = Class.forName(IMAGE_FILTERS).getMethod("button1", PICTURE);
            BUTTON2 = Class.forName(IMAGE_FILTERS).getMethod("button2", PICTURE);
            //BUTTON3 = Class.forName(IMAGE_FILTERS).getMethod("button3", PICTURE);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    static String extension(String filename){
        if (!filename.endsWith(".png") && !filename.endsWith(".jpg")){
            return filename+".jpg";
        }
        return filename;
    }
    
    static class MySecurityManager extends SecurityManager {
        // XXX Security manager hack to prevent System.exit()
        // because I stupidly called System.exit() when handling index out of bounds exceptions
        // in the Picture class
        @Override
        public void checkExit(int status) {
            // IGNORE
            //System.out.println("Blocking System.exit");
            try {
                throw new IndexOutOfBoundsException();
            } catch (Exception e){
                // Only block calls to System.exit() that come from this class
                // Necessary because I also call System.exit() when we call Quit
                // in the GUI, and BlueJ somehow installs the security manager
                // and then keeps it active.
                boolean createGallery = false;
                for (StackTraceElement s : e.getStackTrace()){
                    if (s.getClassName().equals("CreateGallery")){
                        createGallery=true;
                    }
                }
                if (createGallery){
                    throw e;
                }
            }
        }
        @Override
        public void checkPermission(Permission perm) {
            // IGNORE
        }
        @Override
        public void checkPermission(Permission perm, Object context) {
            // IGNORE
        }
      }

    public static void main(String[] args) throws Exception {
        MySecurityManager secManager = new MySecurityManager();
        System.setSecurityManager(secManager);
        
        File outputDir = new File("output/images");
        outputDir.mkdirs();

        Picture matt = new Picture("images/bergmatt.jpg");
        saveFile(matt, new File(outputDir, "bergmatt.jpg"));
        
        Picture roses = new Picture("images/roses.png");
        saveFile(roses, new File(outputDir, "roses.jpg"));
        
        Picture jerry = new Picture("images/jerry+lawson.jpg");
        saveFile(jerry, new File(outputDir, "jerry+lawson.jpg"));
        
        PrintStream out = new PrintStream(new File("output/TESTING.html"));
        
        out.println("<html><head><title>testing PhotoshopJr</title></head><body>");
        out.println("<table border=2><tr><th>filter</th><th>original</th><th>filtered</th></tr>");
        
        // horizontal flip
        runFilter("flipHorizontal", matt, "horizontal", MATT, out, FLIP_HORIZONTAL, 1);

        // vertical flip
        runFilter("flipVertical", matt, "vertical", MATT, out, FLIP_VERTICAL, 1);

        // grayscale
        runFilter("toGrayscale", matt, "grayscale", MATT, out, TO_GRAYSCALE, 1);
        
        // zombie vision
        runFilter("zombieVision", matt, "zombievision1", MATT, out, ZOMBIE_VISION, 1);
        runFilter("zombieVision, two runs", matt, "zombievision2", MATT, out, ZOMBIE_VISION, 2);
        
        // mirror vertical
        runFilter("mirrorVertical", matt, "mirrorvertical", MATT, out, MIRROR_VERTICAL, 1);
        
        // mirror horizontal
        runFilter("mirrorHorizontal", matt, "mirrorhorizontal", MATT, out, MIRROR_HORIZONTAL, 1);
        
        // shift right
        runFilter("shiftRight by 100", matt, "shiftright100", MATT, out, SHIFT_RIGHT, 1, 100);
        runFilter("shiftRight by 5000", matt, "shiftright5000", MATT, out, SHIFT_RIGHT, 1, 5000);
        
        // shift left
        runFilter("shiftLeft by 100", matt, "shiftleft100", MATT, out, SHIFT_LEFT, 1, 100);
        runFilter("shiftLeft by 5000", matt, "shiftleft5000", MATT, out, SHIFT_LEFT, 1, 5000);
        
        // scale up
        runFilter("scaleUp", matt, "scaleup1", MATT, out, SCALE_UP, 1);
        runFilter("scaleUp twice", matt, "scaleup2", MATT, out, SCALE_UP, 2);

        // scale down -- even and odd sizes
        runFilter("scaleDown", matt, "scaledown1", MATT, out, SCALE_DOWN, 1);
        runFilter("scaleDown twice", matt, "scaledown2", MATT, out, SCALE_DOWN, 2);
        runFilter("scaleDown, odd width and height", roses, "scaledown3", ROSES, out, SCALE_DOWN, 1);

        // rotate right
        runFilter("rotateRightx1", matt, "rotateright1", MATT, out, ROTATE_RIGHT, 1);
        runFilter("rotateRightx2", matt, "rotateright2", MATT, out, ROTATE_RIGHT, 2);
        runFilter("rotateRightx3", matt, "rotateright3", MATT, out, ROTATE_RIGHT, 3);
        runFilter("rotateRightx4", matt, "rotateright4", MATT, out, ROTATE_RIGHT, 4);
        
        // rotate left
        runFilter("rotateLeft", matt, "rotateleft1", MATT, out, ROTATE_LEFT, 1);
        runFilter("rotateLeftx2", matt, "rotateleft2", MATT, out, ROTATE_LEFT, 2);
        runFilter("rotateLeftx3", matt, "rotateleft3", MATT, out, ROTATE_LEFT, 3);
        runFilter("rotateLeftx4", matt, "rotateleft4", MATT, out, ROTATE_LEFT, 4);
        
        // black and white comic
        runFilter("blackAndWhiteComic matt", matt, "blackwhitecomic1", MATT, out, COMIC, 1);
        runFilter("blackandWhiteComic roses", roses, "blackwhitecomic2", ROSES, out, COMIC, 1);
        
        // detect edges
        runFilter("detectEdges", matt, "detectedges1", MATT, out, DETECT_EDGES, 1);
        runFilter("detectEdges roses", roses, "detectedges2", ROSES, out, DETECT_EDGES, 1);
        
        // blur
        runFilter("blur", jerry, "blur1", JERRY, out, DETECT_EDGES, 1);
        runFilter("blurx2", jerry, "blur2", JERRY, out, DETECT_EDGES, 2);
        runFilter("blurx3", jerry, "blur3", JERRY, out, DETECT_EDGES, 3);
        runFilter("blurx4", jerry, "blur4", JERRY, out, DETECT_EDGES, 4);
        
        // custom buttons
        runFilter("button1", matt, "button1", MATT, out, BUTTON1, 1);
        runFilter("button2", matt, "button2", MATT, out, BUTTON2, 1);
        //runFilter("button3", matt, "button3", MATT, out, BUTTON3, 1);

        
        out.println("</table></body></html>");
        out.flush();
        out.close();

    }
    
    static Picture copyImage(Picture pic){
        Picture copy = new Picture(pic.getWidth(), pic.getHeight());
        for (int r=0; r<pic.getHeight(); r++){
            for (int c=0; c<pic.getWidth(); c++){
                copy.setPixel(r,c,pic.getPixel(r,c));
            }
        }
        return copy;
    }
    
    
    
    static void runFilter(String filterName, Picture originalImage, String outputFilename, String originalFilename, PrintStream out, Method filter, int numTimes, int... params) {
        out.println("<tr><td><b><font size=20>"+filterName+"</font></b></td><td><img src=\"images/"+originalFilename+".jpg\"></td>");
        try {
            Picture picture = copyImage(originalImage);
            for (int i=0; i<numTimes; i++){
                if (params.length > 0){
                    picture = (Picture)filter.invoke(null, picture, params[0]);
                } else {
                    picture = (Picture)filter.invoke(null, picture);
                }
            }
            saveFile(picture, new File("output/images", extension(outputFilename)));
            out.println("<td><img src=\"images/"+extension(outputFilename)+"\"></td></tr>");
        } catch (Exception e){
            out.println("<td>Crashed!<br>Probably an index out of bounds exception</td></tr>");
        }
    }
    
    static void runFilter2(String filterName, Picture picture, String filename, String originalFilename, PrintStream out) throws Exception {
        saveFile(picture, new File("output/images", extension(filename)));
        out.println("<tr><td><b><font size=20>"+filterName+"</font></b></td><td><img src=\"images/"+originalFilename+".jpg\"></td><td><img src=\"images/"+filename+".jpg\"></td>");
    }
    
    public static void saveFile(Picture picture, File file)
    throws IOException
    {
        int width = picture.getWidth(); 
        int height = picture.getHeight();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = bi.getGraphics(); 
        g.drawImage(picture.getBufferedImage(), 0, 0, null);
        ImageIO.write(bi,"jpg",new File(file.getAbsolutePath())); 
        g.dispose();
    }

    public static void applyFilter(File picture, File output, Method filter, int... params)
    throws Exception
    {
        Picture pic = new Picture(picture.getAbsolutePath());
        
        Picture result;
        if (params.length > 0){
            result = (Picture)filter.invoke(null, pic, params[0]);
        } else {
            result = (Picture)filter.invoke(null, pic);
        }
        
        saveFile(result, output);
        
    }

}
