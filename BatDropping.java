import java.awt.*;
import javax.swing.ImageIcon;

//Exists to reduce the amount of code in Bat.java and make it more convinient to create bat poop. Has functions to
//move and draw itself. Damages player upon contact.
class BatDropping{
    private int x,y,vy,w,h;
    private boolean hitGround; //If it has hit the ground yet
    Rectangle hitbox;
    Image poop;
    private int offsetX;

    //Returns nothing and has parameters for the x and y coordinates. Creates a new BatDropping.
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

    //Returns nothing and no parameters. Makes the poop fall as though there were gravity.
    public void move(Player player){
        y += vy;
		vy += Game.GRAVITY;
        if(y+h >= Game.HEIGHT){ //Detects when it has hit the ground
            vy = 0;
			y = Game.HEIGHT-h;
            hitGround = true;
        }
        offsetX = x - player.getRelX();
        hitbox.x = offsetX; //Changes the coordinates in hitbox correspondingly
        hitbox.y = y;
    }

    //Returns nothing and has a Graphics parameter to draw things
    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        g.fillRect(offsetX,y,w,h);
        g.drawImage(poop, offsetX, y, null);
    }

    public int getX(){return offsetX;} //Returns the x coordinate of poop and no parameters
    public int getY(){return y;} //Returns the y and no parameters
    public boolean hitGround(){return hitGround;} //Returns true if it has hit the ground. No parameters.
    public Rectangle getRect(){return hitbox;} //Returns the hitbox of the poop and no parameters
}
