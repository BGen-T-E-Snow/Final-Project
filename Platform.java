import java.awt.*;
import javax.swing.*;

class Platform{
    private int x,y,w,h,offsetX,offsetY;
    Image plat;

    public Platform(int xx, int yy, int ww, int hh){
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        offsetY = y+3;//
        plat = new ImageIcon("PlatImage.png").getImage();
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        offsetX = x - Game.player.getRelX();
        g2d.drawImage(plat,offsetX,y,null);
    }

    public int getX(){return offsetX;}
    public int getY(){return offsetY;}
    public int getW(){return w;}
    public int getH(){return h;}
}
