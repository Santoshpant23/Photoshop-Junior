package photoshopJr;



/**
 * Staring Point Code for Image Processing Project
 * @author Richard Wicentowski and Tia Newhall (2005)
 
 * Pixel class represents a single pixel with a a red, green and blue components.
 * Each component can be between 0 and 255 (256 total values).  All three values of 0
 * is black (the absence of color) while all 3 values at 255 is white (all colors together).
 * 
 * @author Richard Wicentowski 
 * @author Tia Newhall
 * 
 * 2019 version
 *
 */
public class Pixel {

    int[] component;

    /**
     * Create a new pixel with RGB values of 0
     */
    public Pixel() { this (0,0,0); }

    Pixel(int gray) {
        component = new int[1];
        component[0] = gray;
    }

    /**
     * Create a new pixel with the given RGB value
     * @param r The red value for the pixel
     * @param g The green value for the pixel
     * @param b The blue value for the pixel
     */
    public Pixel(int r, int g, int b) {
        component = new int[3];
        component[0] = r;
        component[1] = g;
        component[2] = b;
    }

    Pixel(int[] c) {
        component = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            component[i] = c[i];
        }
    }

    void info() {
        for (int i = 0; i < component.length; i++) {
            System.out.print(component[i] + "\t");
        }
        System.out.println();
    }

    /**
     * Convert this pixel to black by setting the RGB values to 0
     */
    public void toBlack() {
        for (int i = 0; i < component.length; i++) { component[i] = 0; }
    }

    /**
     * Convert this pixel to white by setting the RGB values to 255
     */
    public void toWhite() {
        for (int i = 0; i < component.length; i++) { component[i] = 255; }
    }

    /**
     * Get the red component of this pixel
     * @return the red component
     */
    public int getRed() { 
        return component[0];
    }

    /**
     * Get the green component of this pixel
     * @return the green component
     */
    public int getGreen() { 
        return component[1];
    }

    /**
     * Get the blue component of this pixel
     * @return the blue component
     */
    public int getBlue() { 
        return component[2];
    }

    int getGray() {
        return component[0];
    }

    /**
     * Set the red component of this pixel.  Legal values are from 0 to 255, inclusive.
     * Values less than 0 will be set to 0 and values over 255 will be set to 255.
     * @param red
     */
    public void setRed(int red) { 
        if (red < 0) { red = 0; } else if (red > 255) { red = 255; }
        component[0] = red;
    }

    /**
     * Set the green component of this pixel.  Legal values are from 0 to 255, inclusive.
     * Values less than 0 will be set to 0 and values over 255 will be set to 255.
     * @param green
     */
    public void setGreen(int green) {
        if (green < 0) { green = 0; } else if (green > 255) { green = 255; }
        component[1] = green;
    }

    /**
     * Set the green component of this pixel.  Legal values are from 0 to 255, inclusive.
     * Values less than 0 will be set to 0 and values over 255 will be set to 255.
     * @param blue
     */
    public void setBlue(int blue) { 
        if (blue < 0) { blue = 0; } else if (blue > 255) { blue = 255; }
        component[2] = blue;
    }

    void setGray(int gray) {
        component[0] = gray;
    }

    int[] getComponents() {
        return component;
    }

    /**
     * Create a string-representation of this Pixel suitable for printing out.
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String s = "";
        for (int k = 0; k < component.length; k++) {
            if (k != 0) { s += "\t"; }
            s += component[k];
        }
        return s;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals (Object other) {
        if (other instanceof Pixel) {
            Pixel o = (Pixel)other;
            if (o.component.length != component.length) {
                return false; 
            } else {
                for (int k = 0; k < component.length; k++) {
                    if (o.component[k] != component[k]) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }
}
