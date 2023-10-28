


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import photoshopJr.Picture;
import photoshopJr.PictureFrame;
import photoshopJr.SimpleButton;


public class RUN_THIS_FILE {

    private JFrame menuFrame;
    protected PictureFrame pictureFrame;
    protected SimpleButton[] buttons;
    protected Picture originalPicture, picture;

    public RUN_THIS_FILE() {}

    public void run(String filename) {
        setupMenuFrame();
        setupImageFrame(filename);
    }

    private void setupMenuFrame() {

        /* Create the buttons */
        createButtons();

        /* Create a JPanel to put the buttons on */
        JPanel menuPanel = new JPanel();

        /* Make the menu background black */
        menuPanel.setBackground(Color.black);

        /* Make sure the buttons line up nicely */
        menuPanel.setLayout(new GridLayout(buttons.length/2,2));

        /* Add buttons to the menu, skipping the Restore and Quit buttons */
        for (int i = 2; i < buttons.length; i++) {
            menuPanel.add(buttons[i]);
        }

        /* Add the Restore and Quit buttons at the end */
        menuPanel.add(buttons[0]);
        menuPanel.add(buttons[1]);

        /* Create the menu JFrame which will hold the menu JPanel */
        menuFrame = new JFrame("Image Manipulation Menu");

        /* The JPanel is now added to the menu JFrame */
        menuFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);

        /* If the user closes the window, our program should exit */
        menuFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        /* Optimally size the menuFrame */
        menuFrame.pack();

        /* Place the menuFrame near the top-left of the screen */
        menuFrame.setLocation(20,20);

        /* Make the menuFrame visible */
        menuFrame.setVisible(true);
    }

    private void setupImageFrame(String filename) {

        /* Read a color picture from a file */
        picture = new Picture(filename);

        /* Make a copy of the picture in case we need it again */
        originalPicture = new Picture();
        originalPicture.copy(picture);

        /* Put the picture in a PictureFrame called "Image" */
        pictureFrame = new PictureFrame("Image", picture);

        /* Quit the program if the user closes the PictureFrame */
        pictureFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        /* Make the PictureFrame the optimal size for this picture */
        pictureFrame.pack();

        /* Place the PictureFrame 25 pixels to right of the MenuFrame */
        Point p = menuFrame.getLocation();
        pictureFrame.setLocation(p.x+menuFrame.getSize().width+25, p.y);

        /* Make the PictureFrame visible */
        pictureFrame.setVisible(true);
        

    }


    /**
     * <b>HACK ALERT</b>
     * <p>
     * This is protected so that I can create a subclass that over-rides this method
     * and creates all its own buttons that call down into a different 
     * class full of static filters.
     * </p>
     * <p>
     * The sensible thing to do would have been to create an interface and have
     * two implementations, and use dynamic classloading to create an instance in the
     * driver program.
     * </p>
     * <p>
     * But, at this point in the semester, the students just learned static methods
     * and references, but haven't learned interfaces, so it makes the most
     * sense to structure the program this way, even though it's a brittle design pattern.
     * </p>
     */
    protected void createButtons() {

        /* How many buttons do you want? */
        int numButtons = 22;

        /* Create an array of SimpleButtons */
        buttons = new SimpleButton[numButtons];

        /* Define some basic buttons */
        buttons[0] = 
            new SimpleButton("Restore", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    ImageFilters.restore(picture, originalPicture);
                    pictureFrame.refresh(picture);
                }
            }, Color.CYAN);

        buttons[1] = 
            new SimpleButton("Quit", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    ImageFilters.quit(picture);
                    pictureFrame.refresh(picture);
                }
            }, Color.RED);

        /* Add your own button definitions here */

        int counter = 2;

        buttons[counter++] = 
            new SimpleButton("FlipHorizontal", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.flipHorizontal(picture);
                    pictureFrame.refresh(picture);
                }
            });

        buttons[counter++] =
            new SimpleButton("FlipVertical (*)", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.flipVertical(picture);
                    pictureFrame.refresh(picture);
                }
            });

        buttons[counter++] = 
            new SimpleButton("ToGrayscale", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.toGrayscale(picture);
                    pictureFrame.refresh(picture);
                }
            });
        
        buttons[counter++] = 
                new SimpleButton("ZombieVision", new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        picture=ImageFilters.zombieVision(picture);
                        pictureFrame.refresh(picture);
                    }
                });
                
        buttons[counter++] = 
            new SimpleButton("ScaleUp", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.scaleUp(picture);
                    pictureFrame.refresh(picture);
                }
            });

        buttons[counter++] = 
            new SimpleButton("ScaleDown", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.scaleDown(picture);
                    pictureFrame.refresh(picture);
                }
            });        
                
        buttons[counter++] = 
            new SimpleButton("MirrorVertical", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.mirrorVertical(picture);
                    pictureFrame.refresh(picture);
                }
            });

        buttons[counter++] = 
            new SimpleButton("MirrorHorizontal (*)", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.mirrorHorizontal(picture);
                    pictureFrame.refresh(picture);
                }
            });

        buttons[counter++] = 
            new SimpleButton("RotateRight", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.rotateRight(picture);
                    pictureFrame.refresh(picture);
                }
            });

        buttons[counter++] = 
            new SimpleButton("RotateLeft (*)", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.rotateLeft(picture);
                    pictureFrame.refresh(picture);
                }
            });


        buttons[counter++] = 
                new SimpleButton("ShiftRight", new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        String s = (String)JOptionPane.showInputDialog(
                                "Enter number of pixels to shift right: ");
                        try {
                            int distance=Integer.parseInt(s);
                            picture=ImageFilters.shiftRight(picture, distance);
                            pictureFrame.refresh(picture);
                        } catch (Exception ex) {
                            System.err.println("Shift canceled due to invalid shift distance: "+s);
                        }
                    }
                });
       buttons[counter++] = 
            new SimpleButton("ShiftLeft (*)", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    String s = (String)JOptionPane.showInputDialog(
                            "Enter number of pixels to shift right: ");
                    try {
                        int distance=Integer.parseInt(s);
                        picture=ImageFilters.shiftLeft(picture, distance);
                        pictureFrame.refresh(picture);
                    } catch (Exception ex) {
                        System.err.println("Shift canceled due to invalid shift distance: "+s);
                    }
                }
            });
            
//        buttons[counter++] = 
//                new SimpleButton("ColorSplash", new ActionListener() {
//                    public void actionPerformed(ActionEvent e)
//                    {
//                        picture=ImageFilters.colorSplash(picture);
//                        pictureFrame.refresh(picture);
//                    }
//                });
        
        buttons[counter++] = 
                new SimpleButton("DetectEdges", new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        picture=ImageFilters.detectEdges(picture);
                        pictureFrame.refresh(picture);
                    }
                });
        
        
        
        buttons[counter++] = 
                new SimpleButton("BlackAndWhiteComic", new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        picture=ImageFilters.blackAndWhiteComic(picture);
                        pictureFrame.refresh(picture);
                    }
                });
                
       
       buttons[counter++] = 
            new SimpleButton("Pixelate (*)", new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.pixelate(picture);
                    pictureFrame.refresh(picture);
                }
            });
            
        buttons[counter++] = 
                new SimpleButton("[BONUS] Blur", new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        picture=ImageFilters.blur(picture);
                        pictureFrame.refresh(picture);
                    }
                }, Color.ORANGE);
               
//        buttons[counter++] = 
//            new SimpleButton("DecreaseRed", new ActionListener() {
//                public void actionPerformed(ActionEvent e)
//                {
//                    ImageFilters.decreaseRed(picture);
//                    pictureFrame.refresh(picture);
//                }
//            });
//
//        buttons[counter++] = 
//            new SimpleButton("IncreaseRed", new ActionListener() {
//                public void actionPerformed(ActionEvent e)
//                {
//                    ImageFilters.increaseRed(picture);
//                    pictureFrame.refresh(picture);
//                }
//            });

//        buttons[counter++] = 
//            new SimpleButton("IncreaseBlue", new ActionListener() {
//                public void actionPerformed(ActionEvent e)
//                {
//                    ImageFilters.increaseBlue(picture);
//                    pictureFrame.refresh(picture);
//                }
//            });


        buttons[counter++] = 
            new SimpleButton(ImageFilters.YOUR_BUTTON_1, new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.button1(picture);
                    pictureFrame.refresh(picture);
                }
            }, Color.ORANGE);
            
        buttons[counter++] = 
            new SimpleButton(ImageFilters.YOUR_BUTTON_2, new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    picture=ImageFilters.button2(picture);
                    pictureFrame.refresh(picture);
                }
            }, Color.ORANGE);

       buttons[counter++] = 
           new SimpleButton(ImageFilters.YOUR_BUTTON_3, new ActionListener() {
               public void actionPerformed(ActionEvent e)
               {
                   picture=ImageFilters.button3(picture);
                   pictureFrame.refresh(picture);
               }
           }, Color.ORANGE);

//        buttons[counter++] = 
//            new SimpleButton(ImageFilters.YOUR_BUTTON_4, new ActionListener() {
//                public void actionPerformed(ActionEvent e)
//                {
//                    picture=ImageFilters.button4(picture);
//                    pictureFrame.refresh(picture);
//                }
//            });
        
        buttons[counter++] = 
                new SimpleButton("SaveFile", new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        File file=new File("images");
                        JFileChooser chooser=new JFileChooser(file);
                        int returnVal = chooser.showSaveDialog(null);
                        if (returnVal != JFileChooser.APPROVE_OPTION) {
                            System.err.println("Saving the file canceled");
                            return;
                        }
                        file=chooser.getSelectedFile();
                        try {
                            ImageFilters.saveFile(picture, file);
                        } catch (IOException ex) {
                            System.err.println("Cannot write image to file: "+ex.getMessage());
                        }
                    }
                }, Color.GREEN);

        /* For buttons you did not define, create default buttons */
        for (int i = 0; i < numButtons; i++) {
            if (buttons[i] == null) {
                buttons[i] = new SimpleButton();
            }
        }

    }

    public static void main (String[] args)
    throws Exception
    {
        //System.out.println("HELLO");
        RUN_THIS_FILE app = new RUN_THIS_FILE();
        //app.setupMenuFrame();
        
        File file=new File("./images");
        JFileChooser chooser=new JFileChooser(file);
        int returnVal = chooser.showOpenDialog(app.pictureFrame);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            System.err.println("Opening the file canceled");
            System.exit(-1);
        }
        file=chooser.getSelectedFile();
        String filename=file.getAbsolutePath();

        System.err.println(filename);

        if (!file.exists()) {
            throw new FileNotFoundException(filename+ " not found");
        }

        app.run(filename);
    }

}
