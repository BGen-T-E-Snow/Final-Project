import java.awt.*;
import javax.swing.*;

class Platform{
    private int x,y,w,h;
    Image plat;

    public Platform(int xx, int yy, int ww, int hh){
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        plat = new ImageIcon("PlatImage.png").getImage();
    }

    public void draw(Graphics g){
        g.drawImage(plat,x,y,null);
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public int getW(){return w;}
    public int getH(){return h;}
}
