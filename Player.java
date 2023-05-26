import java.awt.*;
public class Player{
	private final static int LEFT = 65,RIGHT = 68,JUMP = 87,ABILITY = 32;
	private int x,y,w,h,vx,vy,jp;
	public Player(int xx,int yy,int ww,int hh,int vvx, int vvy, int jjpp){
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		vy = vvy;
		jp = jjpp;
	}
	
	public void move(boolean []keys){	//moves player
		if(keys[LEFT] && x>=0){
			x-=vx;
		}
		if(keys[RIGHT] && x<=GamePanel.WIDTH-w){
			x+=vx;
		}
		if(keys[JUMP] && y+h >= GamePanel.HEIGHT){
			vy -= jp;
		}
		y += vy;
		vy += GamePanel.G;
		if(y+h >= GamePanel.HEIGHT){
			vy = 0;
			y = GamePanel.HEIGHT-h;
		}
	}
	
	public void draw(Graphics g){	//draws player
		g.setColor(Color.RED);
		g.fillRect(getX(),getY(),getW(),getH());
	}    
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return w;}
	public int getH(){return h;}
	public int getVX(){return vx;}
	public int getVY(){return vy;}
	public int getJP(){return jp;}
	
}
