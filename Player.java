import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Player{
	private final static int LEFT = Game.A ,RIGHT = Game.D, JUMP = Game.W , STRIKE = Game.SPACE, RJUMP = 0, LJUMP = 1, RFALL = 2, LFALL = 3, RSTRIKE = 4, LSTRIKE = 5, RRUN = 6, LRUN = 7, RIDLE = 8, LIDLE = 9, RIGHTEDGE = 650, LEFTEDGE = 150;
	private int x,y,w,h,vx,vy,jp,health,relX;
	private boolean isRight, jumping, falling, striking, idling, running;
	private int idleW,idleH;
	private double col;
	private int row;
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
	private Sword sword;
	private Image image;
	private Rectangle playerRect;
	
	public Player(int xx,int yy,int ww,int hh,int vvx, int vvy, int jjpp, int hp){
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		vy = vvy;
		jp = jjpp;
		health = hp;//
		relX = 0;
		sword = null;//
		pics.add(addPics("JumpRight",2));
		pics.add(addPics("JumpLeft",2));
		pics.add(addPics("FallRight",2));
		pics.add(addPics("FallLeft",2));
		pics.add(addPics("AttackRight1",4));
		pics.add(addPics("AttackLeft1",4));
		pics.add(addPics("RunRight",8));
		pics.add(addPics("RunLeft",8));
		pics.add(addPics("IdleRight",8));
		pics.add(addPics("IdleLeft",8));
		isRight = true;
		jumping = false;
		falling = false;
		striking = false;//
		idling = true;//
		running = false;//
		row = RIDLE;
		col = 0;
		image = pics.get(row).get((int)col);//
		idleW = image.getWidth(null);//
		idleH = image.getHeight(null);//
		playerRect = new Rectangle(x,y,w,h);//
	}
	
	public void move(boolean []keys){	//moves player	
		if(keys[LEFT]){
			isRight = false;
			running = true;
			if(x>=LEFTEDGE){
				x-=vx;
			}
			else if(relX > 0){
				relX-=vx;
			}
		}
		else if(keys[RIGHT]){
			isRight = true;
			running = true;
			if(x<=RIGHTEDGE-w){
				x+=vx;
			}
			else{
				relX+=vx;
			}
		}
		else if(keys[STRIKE] && !jumping && !falling && !striking){
			striking = true;
			col = 0;
		}
		else{
			idling = true;
			running = false;
		}
		if(keys[JUMP] && y+h >= Game.HEIGHT){
			jumping = true;
			vy -= jp;
		}
		y += vy;
		vy += Game.GRAVITY;
		if(y+h >= Game.HEIGHT){
			jumping = false;
			falling = false;
			vy = 0;
			h = image.getHeight(null);//
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
					h = image.getHeight(null);//
					y = platY - h;
					if(keys[JUMP]){
						jumping = true;
						vy -= jp;
					}
				}
			}
		}
		if(vy>0){//
			jumping = false;
			falling = true;
		}
		if(striking){//
			sword = new Sword(x,y);
			sword.strike(isRight);
			row = isRight ? RSTRIKE:LSTRIKE;
		}
		else if(jumping){//
			row = isRight ? RJUMP:LJUMP;
		}
		else if(falling){//
			row = isRight ? RFALL:LFALL;
		}
		else if(running){//
			row = isRight ? RRUN:LRUN;
		}
		else if(idling){//
			row = isRight ? RIDLE:LIDLE;
		}
		if((row==RJUMP || row==LJUMP || row==RFALL || row==LFALL) && col>=1){//
			col = 0;
		}
		if((row==RSTRIKE || row==LSTRIKE) && col>=3){//
			col = 0;
			striking = false;
			sword = null;
		}
		if((row==RIDLE || row==LIDLE || row==RRUN || row==LRUN) && col>=7){//
			col = 0;
		}
		col += 0.2;
	}
		
	public void draw(Graphics g){	//draws player
		g.setColor(Color.YELLOW);
		image = pics.get(row).get((int)col);
		w = image.getWidth(null);
		if(row!=LSTRIKE){//
			g.fillRect(x,y,w,h);
			playerRect = new Rectangle(x,y,w,h);//
			g.drawImage(image,x,y,null);
		}
		else{//
			g.fillRect(x-w+idleW,y,w,h);//
			playerRect = new Rectangle(x-w+idleW,y,w,h);//
			g.drawImage(image,x-w+idleW,y,null);//
		}//
//		if(sword!=null){				//uncomment if you wanna draw the swords hitbox
//			g.setColor(Color.RED);
//			sword.draw(g);
//		}
	}    
	
	public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Player Images/%s%03d.png",name,i)).getImage());
			System.out.println(String.format("added Player Images/%s%03d.png",name,i));
		}
		return picType;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return w;}
	public int getIdleW(){return idleW;}//
	public int getH(){return h;}
	public int getVX(){return vx;}
	public int getVY(){return vy;}
	public int getJP(){return jp;}
	public int getHP(){return health;}//
	public int getRelX(){return relX;}
	public Rectangle getPlayerRect(){return playerRect;}//
	
}
