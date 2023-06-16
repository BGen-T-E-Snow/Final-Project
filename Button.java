import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

//Exists to make creating buttons easier and matching font size to the size of the button hitbox without using
//trial and error
public class Button{
    private int x,y,size,length,height;
    private Rectangle hitbox;
    private Font font; //Font used by the button
    private String BName; //Name of the button and the text it will display when it is drawn

    //Sets the initial values of the button. Type is the font, style is plain, bold, or italic. Name is the text of
    //the button. s is the size, xx and yy is the position. Returns nothing.
    public Button(String type, int style, String name, int xx, int yy, int s){
        x = xx;
        y = yy;
        size = s;
        height = (size*3)/4;
        font = new Font(type, style, size);
        BName = name;
        hitbox = new Rectangle(x,y,length,height);//
    }

    //Returns nothing and is used to draw the button. Needs object of class Graphics to drawa it.
    public void draw(Graphics g){
        g.setFont(font);
        length = g.getFontMetrics().stringWidth(BName);
        hitbox = new Rectangle(x,y,length,height);
        g.drawString(BName,x,y+height);
    }

    public Rectangle getRect(){return hitbox;} //No parameters and returns the hitbox
    public int getX(){return x;} //No parameters and returns x position
    public int getY(){return y;} //No parameters and returns y position
    public int getWidth(){return length;} //No parameters and returns length of the button
    public int getHeight(){return height;} //No parameters and returns the height of the button

}
