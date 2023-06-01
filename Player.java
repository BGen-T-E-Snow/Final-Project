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
		if(keys[RIGHT] && x<=Game.WIDTH-w){
			x+=vx;
		}
		if(keys[JUMP] && y+h >= Game.HEIGHT){
			vy -= jp;
		}
		y += vy;
		vy += BaseFrame.GRAVITY;
		if(y+h >= Game.HEIGHT){
			vy = 0;
			y = Game.HEIGHT-h;
		}
		int platx, platy, platlen, platbot, playerbot;
		platx = Game.plat1.getx();
		platy = Game.plat1.gety();
		platlen = Game.plat1.getLength();
		if(x <= platx + platlen && x + w >= platx){
			if(y+h+vy >= platy && y+h <= platy){
				vy = 0;
				y = platy - h;
				if(keys[JUMP]){vy -= jp;}
			}
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
