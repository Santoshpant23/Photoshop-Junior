package photoshopJr;



/**
 * Staring Point Code for Image Processing Project
 * @author Richard Wicentowski and Tia Newhall (2005)
 * 
 * 2019 version
 */
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PictureFrame extends JFrame {
    public static final long serialVersionUID=1L;
    private JLabel imageLabel;

    public PictureFrame(String name, Picture picture) {
        super(name);
        framePicture(picture);
    }

    private void framePicture (Picture picture) {

        /* Put the Picture in an ImageIcon */
        ImageIcon icon = new ImageIcon();
        icon.setImage(picture.getBufferedImage());

        /* Put the Image Icon in a JLabel */
        imageLabel = new JLabel(icon);
        imageLabel.setIcon(icon);

        /* Put the JLabel in a JPanel */
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.black);
        imagePanel.add(imageLabel);

        /* Add the JPanel to the PictureFrame */
        getContentPane().add(imagePanel, BorderLayout.CENTER);

    }

    /**
     * Redraw the picture in the picture frame
     * @param picture
     */
    public void refresh (Picture picture) {
        ImageIcon icon = new ImageIcon();
        icon.setImage(picture.getBufferedImage());
        imageLabel.setIcon(icon);
        pack();
    }

}
