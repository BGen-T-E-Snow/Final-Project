import java.awt.*;
public class Sword{
	private int x,y,w,h;
	private Rectangle swordRect;
	
    public Sword(int xx, int yy) {
    	x = xx;
    	y = yy;
		swordRect = null;
    }
    
    public void strike(boolean isRight){
		swordRect = Game.getPlayer().getPlayerRect();
    }
	
    public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect((int)swordRect.getX(),(int)swordRect.getY(),(int)swordRect.getWidth(),(int)swordRect.getHeight());
    }    
}