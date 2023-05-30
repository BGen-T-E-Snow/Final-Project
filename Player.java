import java.awt.*;
public class Player{
	private final static int LEFT = 65,RIGHT = 68,JUMP = 87,ABILITY = 32;
	private int x,y,w,h,vx,vy,jp;
	private Sword sword;
	public Player(int xx,int yy,int ww,int hh,int vvx, int vvy, int jjpp){
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		vy = vvy;
		jp = jjpp;
		sword = null;
	}
	
	public void move(boolean []keys){	//moves player
		if(keys[BaseFrame.A] && x>=0){
			x-=vx;
		}
		if(keys[BaseFrame.D] && x <= BaseFrame.WIDTH-w){
			x+=vx;
		}
		if(keys[BaseFrame.W] && y+h >= BaseFrame.HEIGHT){
			vy -= jp;
		}
		y += vy;
		vy += BaseFrame.GRAVITY;
		if(y+h >= BaseFrame.HEIGHT){
			vy = 0;
			y = BaseFrame.HEIGHT-h;
		}
		if(keys[BaseFrame.SPACE]){
			sword = new Sword(x,y);
			sword.strike();
		}
	}
	
	public void draw(Graphics g){	//draws player
		g.setColor(Color.RED);
		g.fillRect(getX(),getY(),getW(),getH());
//		sword.draw(g);
	}    
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return w;}
	public int getH(){return h;}
	public int getVX(){return vx;}
	public int getVY(){return vy;}
	public int getJP(){return jp;}
	
}
