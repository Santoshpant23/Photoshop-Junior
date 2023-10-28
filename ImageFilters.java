import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

import javax.imageio.ImageIO;

import photoshopJr.Picture;
import photoshopJr.Pixel;

/**
 * This class contains a series of static method that manipulate an image.
 * 
 * @author 
 * @version 
 */
public class ImageFilters
{
    /*
     * Change the names of your custom buttons here
     * 
     * These are static constants
     */
    public static final String YOUR_BUTTON_1 = "[OPTIONAL] CustomFilter #1";
    public static final String YOUR_BUTTON_2 = "[OPTIONAL] CustomFilter #2";
    public static final String YOUR_BUTTON_3 = "[OPTIONAL] CustomFilter #3";

    /**
     * flipHorizontal: Flip the image horizontally along the y-axis in the
     * middle of the image
     * @param picture
     */
    public static Picture flipHorizontal(Picture picture)
    {
        System.err.println("Flip the image horizontally along the y-axis in the middle of the image");
        int w = picture.getWidth();
        int h = picture.getHeight();
        for (int c = 0; c < w/2; c++) {
            for (int r = 0; r < h; r++) {
                Pixel pix1 = picture.getPixel(r, c); 
                Pixel pix2 = picture.getPixel(r, w-c-1);
                picture.setPixel(r, c, pix2);
                picture.setPixel(r, w-c-1, pix1);
            }
        }
        return picture;
    }

    /**
     * flipVertical: Flip the image vertically along its X-axis
     * Only use flipHorizontal and rotateRight for this one. Do not alter pixels directly in this function.
     * @param picture
     */
    public static Picture flipVertical(Picture picture)
    {
        System.err.println("Flip the image vertically along its X-axis");
        //As I am instructed above, I have to use flipHorizontal and rotateRight to solve this problem.
        //So, the process is simple.
        picture = flipHorizontal(picture);
        picture = rotateRight(picture);
        picture = rotateRight(picture);
        return picture;
    }

    /**
     * Convert an image to black-and-white
     * @param picture
     */
    public static Picture toGrayscale(Picture picture)
    {
        System.err.println("Convert the image to black-and-white");
        int w = picture.getWidth();
        int h = picture.getHeight();
        //So, I have this habit of always starting loops with i and j. So, if it is making my code look more complicated, 
        //you can always give me feedback. I will try working on improving my habit.
        //As far as how this code is working, I have followed the instructions on slides  i.e. taking the average and updating photo.
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Pixel pix = picture.getPixel(i,j);
                int total = (pix.getGreen() + pix.getRed() + pix.getBlue() )/3;
                pix.setRed(total);
                pix.setGreen(total);
                pix.setBlue(total);
                picture.setPixel(i,j,pix);

            }
        }

        return picture;
    }

    /**
     * Convert an image to "Zombie vision" by subtracting each color intensity from 255 to 
     * "invert" the image.
     * 
     * This is like "mirroring" the color!
     * 
     * @param picture
     */
    public static Picture zombieVision(Picture picture)
    {
        System.err.println("Convert the image to 'Zombie vision' by subtracting each color intensity from 255");
        int w = picture.getWidth();
        int h = picture.getHeight();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                //Here, I am just subtracting each colour from 255 to mirror this color which will create zombie vision.
                Pixel pix = picture.getPixel(i,j);
                pix.setRed(255 - pix.getRed());
                pix.setGreen(255 - pix.getGreen());
                pix.setBlue(255 - pix.getBlue());
                picture.setPixel(i,j,pix);

            }
        }
        return picture;
    }

    /**
     * scaleUp: scale up the image by a factor of 2.  This turns every pixel into 4 pixels
     * @param picture
     */
    public static Picture scaleUp(Picture picture)
    {
        System.err.println("scaleUp: scale up the image by a factor of 2 by expanding each pixel into 4 pixels");
        //First, I will create new picture of height and width twice of original picture.
        Picture pic2 =  new Picture(2* picture.getWidth() , 2* picture.getHeight());
        int h = picture.getHeight();
        int w = picture.getWidth();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Pixel pxl = picture.getPixel(i,j);
                Pixel pxl2 = pic2.getPixel(i,j);
                pxl2.setRed(pxl.getRed());
                pxl2.setGreen(pxl.getGreen());
                pxl2.setBlue(pxl.getBlue());
                //This is the formula/logic I am using to scale up the image.
                pic2.setPixel(2*i, 2*j, pxl2);
                pic2.setPixel(2*i, 2*j+1, pxl2);
                pic2.setPixel(2*i+1, 2*j, pxl2);
                pic2.setPixel(2*i+1, 2*j+1, pxl2);
            }
        }
        return pic2;
    }

    /**
     * scaleDown: scale down the image by a factor of 2 by merging every 4 pixels into 1 pixel
     * (or by randomly picking one of the 4 pixels from the old image to be in the new image)
     *
     * @param picture
     */
    public static Picture scaleDown(Picture picture)
    {
        System.err.println("scaleDown: scale down the image by a factor of two by merging every 4 pixels into 1 pixel");
        //First, I will create new picture of height and width half of original picture.
        Picture pic2 =  new Picture(picture.getWidth()/2 , picture.getHeight()/2);
        int h = picture.getHeight();
        int w = picture.getWidth();
        for(int i=1; i<h; i+=2){
            for(int j=1; j<w; j+=2){
                //Now, Instead of picking a random pixel, I am going with the another solution i.e. taking average of 4 pixels
                // and putting them in one pixel of new image.
                //I am starting both of my i and j from 1 because just to make sure I do not go out of bound.
                Pixel pxl = picture.getPixel(i,j);
                Pixel pxl1 = picture.getPixel(i-1,j-1);
                Pixel pxl2 = picture.getPixel(i,j-1);
                Pixel pxl3 = picture.getPixel(i-1,j);
                Pixel pxln2 = pic2.getPixel(i/2,j/2);
                //Below, I am taking average of each colour.
                pxln2.setGreen((pxl.getGreen() + pxl1.getGreen() + pxl2.getGreen() + pxl3.getGreen())/4);
                pxln2.setRed((pxl.getRed() + pxl1.getRed() + pxl2.getRed() + pxl3.getRed())/4);
                pxln2.setBlue((pxl.getBlue() + pxl1.getBlue() + pxl2.getBlue() + pxl3.getBlue())/4);
                pic2.setPixel(i/2,j/2,pxln2);
            }
        }
        return pic2;
    }

    /**
     * Mirror the image vertically.  Thus if it's a person's head, the mirrored
     * image should have two heads, one facing up and once facing down.
     * @param picture
     */
    public static Picture mirrorVertical(Picture picture)
    {
        System.err.println("Mirror the image vertically");
        //We have already done mirroring in grids. So, the logic here and there is same. 
        //Since this is vertical mirroring, my width will go from start to end while height will go till half only.
        int h = picture.getHeight();
        int w = picture.getWidth();
        for(int i=0; i<h/2; i++){
            for(int j=0; j<w; j++){
                Pixel p1 = picture.getPixel(i,j);
                Pixel p2 = picture.getPixel(h-1-i, j);
                picture.setPixel(h -1-i, j, p1);

            }
        }
        return picture;
    }

    /**
     * Mirror the image horizontally.  Thus if it's a picture of a person's hands, they should have two left hands.
     * Use only mirrorVertical(), rotateLeft(), and rotateRight() for this one. Do not alter pixels directly in this function.
     * @param picture
     */
    public static Picture mirrorHorizontal(Picture picture)
    {
        System.err.println("Mirror the image horizontally");
        //No need to explain as it has already been explained above.
        picture = rotateRight(picture);
        picture = mirrorVertical(picture);
        picture = rotateLeft(picture);
        return picture;
    }

    /**
     * rotateRight: Rotate the image 90 degrees to the right
     * @param picture
     */
    public static Picture rotateRight(Picture picture)
    {
        System.err.println("rotateRight: Rotate the image 90 degrees to the right");
        //This is an interesting question. Since we are changing dimension of picture, we have to make a new picture
        //of height same as width of original picture and width same as height of previous picture.
        Picture pic2 = new Picture( picture.getHeight(), picture.getWidth());
        int h = picture.getHeight();
        int w = picture.getWidth();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Pixel p1 = picture.getPixel(i,j);
                pic2.setPixel(j,h-i-1,p1);

            }
        }
        return pic2;
    }

    /**
     * rotateLeft: Rotate the image 90 degrees to the left
     * Use only rotateRight() for this one. Do not alter pixels directly in this function.
     * @param picture
     */
    public static Picture rotateLeft(Picture picture)
    {
        System.err.println("rotateLeft: Rotate the image 90 degrees to the left");
        //If we rotate right 3 times, it will be equal to rotate left.
        for(int i=0; i<3; i++){
        picture = rotateRight(picture);    
        }
        return picture;
    }

    /**
     * Shift the image to the right by distance pixels.
     *
     * HINT: A really simple way to do this is to create a new image to copy pixels into,
     * use the mod operation to "wrap around", and then copy the new image over the old image at the end
     *
     * @param picture
     */
    public static Picture shiftRight(Picture picture, int distance)
    {
        System.err.println("Shift the image to the right by distance pixels.");
        Picture p2 = new Picture(picture.getWidth(), picture.getHeight());
        int h = picture.getHeight();
        int w = picture.getWidth();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Pixel p  = picture.getPixel(i,j);
                //From the slides, I got this idea and then I just implemented it.
                int newj = (j + distance)%w;
                p2.setPixel(i, newj, p);

            }
        }
        return p2;
    }

    /**
     * Shift the image to the left by distance pixels.
     *
     * Use only shiftRight() for this one. Think about how far you would need to
     * shift something to the right to make it look like it was actually
     * shifted to the left...
     *
     * Do not alter pixels directly in this function.
     *
     * @param picture
     */
    public static Picture shiftLeft(Picture picture, int distance)
    {
        System.err.println("Shift the image to the left by distance pixels.");
        int w = picture.getWidth();
        picture = shiftRight(picture, w - distance);
        return picture;
    }

    /**
     * Turn each pixel black, unless it has a high contrast with nearby pixels.
     *
     * @param picture
     */
    public static Picture detectEdges(Picture picture)
    {
        System.err.println("detectEdges: turn each pixel black unless it has a high contrast of the pixels around it");
        int threashold = 100;  
        int h = picture.getHeight();
        int w = picture.getWidth();
        //First, I was trying to detect edges without making new picture which made my code very complicated. Then, prof Vera suggested me
        //to make a new picture and then try with it. Hence, below is the solution which I did after failing to detect edges perfectly at first try :(
        Picture newpic = new Picture(w,h);
        for(int i=0; i<h-1; i++){
            for (int j=0; j<w-1; j++){
                Pixel current  = picture.getPixel(i,j);
                Pixel nextRowPixel = picture.getPixel(i, j+1); //to check what is in next row
                Pixel nextColPixel = picture.getPixel(i+1, j); // to check what is in next column
                int sumCurrent = current.getRed() + current.getBlue() + current.getRed(); 
                int sumNextRow = nextRowPixel.getRed() + nextRowPixel.getBlue() + nextRowPixel.getRed();
                int sumNextCol = nextColPixel.getRed() + nextColPixel.getBlue() + nextColPixel.getRed();    
                //Below, I am using a condition that if the difference is greater than threashold, then only change color
                // I do not have else condition because by default my new picture has all pixels black, so no need to say turn them black.
                if(Math.abs(sumCurrent - sumNextRow)>threashold){
                    current.setRed(255);
                    current.setGreen(255);
                    current.setBlue(255);
                    newpic.setPixel(i,j,current);
                }   
                if(Math.abs(sumCurrent - sumNextCol)>threashold){
                    current.setRed(255);
                    current.setGreen(255);
                    current.setBlue(255);
                    newpic.setPixel(i,j,current);
                }  
            }
        }
        return newpic;
    }

    /**
     * Turn each pixel black, unless it has a high contrast with nearby pixels.
     * 
     * @param picture
     */
    public static Picture blackAndWhiteComic(Picture picture)
    {
        System.err.println("blackAndWhiteComic: turn each pixel completely black or completely white depending on how much total color the pixel has");
        int height = picture.getHeight();
        int width = picture.getWidth();
        for(int i=0; i<height; i++){
            for(int j = 0; j<width; j++){
                Pixel px =  picture.getPixel(i,j);
                int total = px.getGreen() + px.getBlue() + px.getRed();
                //Just if else condition will be required here. If total is greater then turn black, if not, turn white.
                if(total>381){
                    px.setGreen(255);
                    px.setBlue(255);
                    px.setRed(255);
                    picture.setPixel(i,j,px);
                } else {
                    px.setGreen(0);
                    px.setBlue(0);
                    px.setRed(0);
                    picture.setPixel(i,j,px);
                }
            }
        }
        return picture;
    }

    /**
     * Pixelate image by scaling down 4 times, then scaling back up 4 times.
     * Do not alter pixels directly in this function.
     * @param picture
     */
    public static Picture pixelate(Picture picture)
    {
        System.err.println("Pixelate image by scaling down 4 times, then scaling back up 4 times");
        for(int i=0; i<4; i++){
            picture = scaleDown(picture);
        }
        for(int i=0; i<4; i++){
            picture = scaleUp(picture);
        }
        return picture;
    }

    /**
     * Implement Blur
     *
     * @param picture
     */
    public static Picture blur(Picture picture)
    {
        System.err.println("Blur the image");

        int height = picture.getHeight();
        int width = picture.getWidth();
        Picture p2 = new Picture(width, height);
        for(int i=1;i<height - 1; i++){
            for (int j = 1; j<width -1; j++){
                //So, I can do this by making a loop here and simply just writing down eight pixels that surrounds i and j;
                //First rule of programming: DO NOT REPEAT YOURSELF, So, I will use loops;
                //for this let me initialize totalRed, totalBlue, and totalGreen;
                int totalRed = 0;
                int totalGreen = 0; 
                int totalBlue = 0;
                for(int a=i-1; a<i+2; a++){
                    for(int b=j-1; b<j+2; b++){
                        Pixel px = picture.getPixel(a,b);
                        totalGreen= totalGreen+ px.getGreen();
                        totalBlue= totalBlue+ px.getBlue();
                        totalRed= totalRed+ px.getRed();
                    }
                }
                totalRed = totalRed/9;
                totalGreen = totalGreen/9;
                totalBlue = totalBlue/9;
                Pixel px2 = p2.getPixel(i,j);
                px2.setGreen(totalGreen);
                px2.setBlue(totalBlue);
                px2.setRed(totalRed);
                p2.setPixel(i,j,px2);

            }
        }
        return p2;
    }

    /**
     * Implement some other filter
     * @param picture
     */
    public static Picture button1(Picture picture)
    {
        System.err.println("Implement some other filter");
        //This filter will border the given image in white color
        int w=picture.getWidth();
        int h = picture.getHeight();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){

                if((i==0 || j==0) || (i==h-1 || j==w-1)){
                    Pixel px=picture.getPixel(i,j);
                    px.setRed(255);
                    px.setGreen(192);
                    px.setBlue(203);
                    picture.setPixel(i,j,px);
                    if(i==0){
                        picture.setPixel(i+1,j,px); 
                        picture.setPixel(i+2,j,px);
                        picture.setPixel(i+3,j,px);
                        picture.setPixel(i+4,j,px); 
                        picture.setPixel(i+5,j,px);
                        picture.setPixel(i+6,j,px);
                    }
                    if(j==0){
                        picture.setPixel(i,j+1,px); 
                        picture.setPixel(i,j+2,px);
                        picture.setPixel(i,j+3,px);
                        picture.setPixel(i,j+4,px); 
                        picture.setPixel(i,j+5,px);
                        picture.setPixel(i,j+6,px);
                    }
                    if(i==h-1){
                        picture.setPixel(i-1,j,px); 
                        picture.setPixel(i-2,j,px);
                        picture.setPixel(i-3,j,px);
                        picture.setPixel(i-4,j,px); 
                        picture.setPixel(i-5,j,px);
                        picture.setPixel(i-6,j,px);
                    }
                    if(j==w-1){
                        picture.setPixel(i,j-1,px); 
                        picture.setPixel(i,j-2,px);
                        picture.setPixel(i,j-3,px);
                        picture.setPixel(i,j-4,px); 
                        picture.setPixel(i,j-5,px);
                        picture.setPixel(i,j-6,px);
                    }
                }
            }
        }
        return picture;
    }

    /**
     * Implement some other filter
     * @param picture
     */
    public static Picture button2(Picture picture)
    {
        System.err.println("Implement some other filter");
        //This is magic color changer. It changes color magically.
        int w=picture.getWidth();
        int h = picture.getHeight();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Pixel px = picture.getPixel(i,j);
                int red = px.getRed();
                int green = px.getGreen();
                int blue = px.getBlue();
                px.setRed(green);
                px.setGreen(blue);
                px.setBlue(red);
                picture.setPixel(i,j,px);

            }
        }
        return picture;
    }

    /**
     * Implement some other filter
     * @param picture
     */
    public static Picture button3(Picture picture)
    {
        System.err.println("Implement some other filter");
        //This is magic color changer pro max ultra, whatever you want to say.
        int w=picture.getWidth();
        int h = picture.getHeight();
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                Pixel px = picture.getPixel(i,j);
                int red = px.getRed();
                int green = px.getGreen();
                int blue = px.getBlue();
                if(i<h/2 && j<w/2){
                    px.setRed(green);
                    px.setGreen(blue);
                    px.setBlue(red);
                    picture.setPixel(i,j,px);
                }
                if(i<h/2 && j>w/2){
                    px.setRed(blue);
                    px.setGreen(red);
                    px.setBlue(green);
                    picture.setPixel(i,j,px);
                }
                if(i>h/2 && j>w/2){
                    px.setRed(green);
                    px.setGreen(blue);
                    px.setBlue(red);
                    picture.setPixel(i,j,px);
                }
                if(i>h/2 && j<w/2){
                    px.setRed(blue);
                    px.setGreen(red);
                    px.setBlue(green);
                    picture.setPixel(i,j,px);
                }
            }
        }
        return picture;
    }

    /**
     * Save to a file. This has been implemented for you! Don't change it.
     * 
     * @param picture
     */
    public static void saveFile(Picture picture, File file)
    throws IOException
    {
        System.err.println("Save to a file");

        int width = picture.getWidth(); 
        int height = picture.getHeight();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = bi.getGraphics(); 
        g.drawImage(picture.getBufferedImage(), 0, 0, null);
        ImageIO.write(bi,"jpg",new File(file.getAbsolutePath())); 
        g.dispose();
    }

    /**
     * Restore the original image by copying the original picture over the
     * current picture. This has been implemented for you! Don't change it.
     * @param picture
     * @param originalPicture
     */
    public static void restore(Picture picture, Picture originalPicture)
    {
        picture.copy(originalPicture);
    }

    /**
     * Quit: Just calls System.exit(0) to shutdown the program.
     * @param picture
     */
    public static void quit(Picture picture)
    {
        System.exit(0);
    }

}
