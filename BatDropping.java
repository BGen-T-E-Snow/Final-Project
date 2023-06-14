import java.awt.*;

import javax.swing.ImageIcon;

class BatDropping{
    private int x,y,vy,w,h;
    private boolean hitGround;
    Rectangle hitbox;
    Image poop;
    private int offsetX;

    public BatDropping(int xx, int yy){
        x = xx;
        y = yy;
        vy = 0;
        w = 32;
        h = 32;
        hitGround = false;
        offsetX = x - Game.getPlayer().getRelX();
        hitbox  = new Rectangle(offsetX,y,w,h);

        poop = new ImageIcon("poop.png").getImage();
    }

    public void move(){
        y += vy;
		vy += Game.GRAVITY;
        if(y+h >= Game.HEIGHT){
            vy = 0;
			y = Game.HEIGHT-h;
            hitGround = true;
        }
        offsetX = x - Game.getPlayer().getRelX();
        hitbox.x = offsetX;
        hitbox.y = y;
    }

    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        g.fillRect(offsetX,y,w,h);
        g.drawImage(poop, offsetX, y, null);
    }

    public int getX(){return offsetX;}
    public int getY(){return y;}
    public boolean hitGround(){return hitGround;}
    public Rectangle getRect(){return hitbox;}
}