import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Player{
	private final static int LEFT = 65,RIGHT = 68, JUMP = 87,ABILITY = 32, RJUMP = 0, LJUMP = 1, RFALL = 2, LFALL = 3, RRUN = 4, LRUN = 5, RIDLE = 6, LIDLE = 7;
	private int x,y,w,h,vx,vy,jp;
	boolean isRight;
	boolean jumping;
	boolean falling;
	int max;
	double col;
	int row;
	ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
	
	public Player(int xx,int yy,int ww,int hh,int vvx, int vvy, int jjpp){
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		vy = vvy;
		jp = jjpp;
		pics.add(addPics("JumpRight",2));
		pics.add(addPics("JumpLeft",2));
		pics.add(addPics("FallRight",2));
		pics.add(addPics("FallLeft",2));
		pics.add(addPics("RunRight",8));
		pics.add(addPics("RunLeft",8));
		pics.add(addPics("IdleRight",8));
		pics.add(addPics("IdleLeft",8));
		isRight = true;
		jumping = false;
		falling = false;
		row = RIDLE;
		col = 0;
		max = BaseFrame.HEIGHT-h-(jp*(jp+1))/2;
	}
	
	public void move(boolean []keys){	//moves player
		System.out.println(max + " " + y);
		if(keys[LEFT] && x>=0){
			x-=vx;
			row = LRUN;
			isRight = false;
		}
		else if(keys[RIGHT] && x<=Game.WIDTH-w){
			x+=vx;
			row = RRUN;
			isRight = true;
		}
		else{
			row = isRight ? RIDLE:LIDLE;
		}
		if(keys[JUMP] && y+h >= Game.HEIGHT){
			jumping = true;
			vy -= jp;
		}
		y += vy;
		if(y<=max && jumping){
			jumping = false;
			falling = true;
		}
		vy += BaseFrame.GRAVITY;
		if(y+h >= Game.HEIGHT){
			jumping = false;
			falling = false;
			vy = 0;
			y = Game.HEIGHT-h;
		}
		for(Platform plat : Game.getPlats()){
			int platX, platY, platW;
			platX = plat.getX();
			platY = plat.getY();
			platW = plat.getW();
			if(x <= platX + platW && x + w >= platX){
				if(y+h+vy >= platY && y+h <= platY){
					jumping = false;
					falling = false;
					vy = 0;
					y = platY - h;
					max = platY-h-(jp*(jp+1))/2;
					if(keys[JUMP]){
						jumping = true;
						vy -= jp;
					}
					if(y<=max && jumping){
						jumping = false;
						falling = true;
					}
				}
			}
			if((x+w<platX && y<=platY) || (x>platX+platW && y<=platY)){
				falling = true;
			}
		}
		if(jumping){
			row = isRight ? RJUMP:LJUMP;
		}
		else if(falling){
			row = isRight ? RFALL:LFALL;
		}
		else{
			jumping = false;
			falling = false;
		}
		col+=0.2;
		if(row>=4 && col>=6){
			col = 0;
		}
		else if(row<=3 && col>=2){
			col = 0;
		}
	}
	
	public void draw(Graphics g){	//draws player
		Image image = pics.get(row).get((int)col);
		g.drawImage(image,x,y,null);
	}    
	
	public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return w;}
	public int getH(){return h;}
	public int getVX(){return vx;}
	public int getVY(){return vy;}
	public int getJP(){return jp;}
	
}
