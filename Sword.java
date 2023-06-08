import java.awt.*;
public class Sword{
	private int x,y,w,h;
	private Rectangle swordRect;
	
    public Sword(int xx, int yy, int ww, int hh) {
    	x = xx;
    	y = yy;
	w = ww;
	h = hh;
	swordRect = null;
    }
    
    public void strike(boolean isRight){//
	int pW = Game.getPlayer().getIdleW();
	if(isRight){
		swordRect = new Rectangle(x+pW,y,w,h);
	}
	else{
		swordRect = new Rectangle(x,y,w,h);
	}
    }
	
    public void draw(Graphics g){
	g.setColor(Color.RED);
	g.fillRect((int)swordRect.getX(),(int)swordRect.getY(),(int)swordRect.getWidth(),(int)swordRect.getHeight());
    }    
}
