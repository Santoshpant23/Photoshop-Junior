package photoshopJr;



/**
 * Staring Point Code for Image Processing Project
 * @author Richard Wicentowski and Tia Newhall (2005)
 * 
 * 2019 version
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class SimpleButton extends JButton {
    public static final long serialVersionUID=1L;
    static Font defaultFont = new Font("Dialog",Font.PLAIN,24);
    static Color defaultColor = Color.yellow;
    static int counter = 0;
    static ActionListener defaultAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.err.println("This button has no assigned action.");
        }
    };

    public SimpleButton() {
        this("Button #" + counter, defaultAction, defaultColor, defaultFont);
    }

    public SimpleButton(String label, ActionListener listener) {
        this(label, listener, defaultColor, defaultFont);
    }

    public SimpleButton(String label, ActionListener listener, Color color) {
        this(label, listener, color, defaultFont);
    }

    SimpleButton(String label, ActionListener listener, 
            Color color, Font font) {
        setFont(font);
        setBackground(color);
        setText(label);
        addActionListener(listener);
        counter++;
    }
}

