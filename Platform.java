import java.awt.*;
import javax.swing.*;

//Exists to make creating platforms a lot easier. Takes in sets of variables and creates a platform in the position given
class Platform{
    private int x,y,w,h,offsetX,offsetY; //OffsetY is used to compensate for the imperfect image so it doesn't look like
    //the player is floating slightly above the platform
    Image plat;

    //Returns nothing and has parameters for the location and sixe of the platform. Creates the platform.
    public Platform(int xx, int yy, int ww, int hh){
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        offsetY = y+3;
        plat = new ImageIcon("PlatImage.png").getImage();
    }

    //Returns nothing and has a Graphics parameter used to draw. Draws the platform.
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        offsetX = x - Game.getPlayer().getRelX();
        g2d.drawImage(plat,offsetX,y,null);
    }

    public int getX(){return offsetX;} //Returns the offsetX for the platform and no parameters
    public int getY(){return offsetY;} //Returns the offsetY for the platform and no parameters
    public int getW(){return w;} //Returns the width of the platform and no parameters
    public int getH(){return h;} //Returns the height of the platform and no parameters
}
