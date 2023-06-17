//Sword.java
//Spencer Trepanier
//Defines the sword
//has a strike and a hit method, and an x,y, width and height, as well as a damagebox
import java.awt.*;
public class Sword{
	private int x,y,w,h;
	private Rectangle swordRect;
	
    public Sword(int xx, int yy, int ww, int hh) {
    	x = xx;
    	y = yy;
		w = ww;
		h = hh;
		swordRect = new Rectangle(x,y,w,h);	//damagebox
    }
    
    public void strike(boolean isRight, Player player){	//determines the sword rectangle based on which direction player is facing
		int pW = player.getIdleW();		//gets the players idle width
		swordRect = isRight ? new Rectangle(x+pW,y,w,h):new Rectangle(x-w,y,w,h); //determines which side of player to put the sword based on the direction
    }
	
	public boolean swordHit(Rectangle enemyRect){	//checks if sword is colliding with given rectangle
		return swordRect.intersects(enemyRect);
	}
	
    public void draw(Graphics g){	//draws sword (optional)
		g.setColor(Color.RED);
		g.fillRect((int)swordRect.getX(),(int)swordRect.getY(),(int)swordRect.getWidth(),(int)swordRect.getHeight());
    }    
}
