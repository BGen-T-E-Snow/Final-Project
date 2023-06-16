import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Player{
	private final static int LEFT = Game.A ,RIGHT = Game.D, JUMP = Game.W , STRIKE = Game.SPACE, RJUMP = 0, LJUMP = 1, RFALL = 2, LFALL = 3, RSTRIKE = 4, LSTRIKE = 5, RRUN = 6, LRUN = 7, RIDLE = 8, LIDLE = 9, RDEATH = 10, LDEATH = 11, RTAKEHIT = 12, LTAKEHIT = 13, RIGHTEDGE = 650, LEFTEDGE = 150;
	private int x,y,w,h,vx,vy,jp,health,relX;
	private boolean isRight, jumping, striking, idling, death, isHit;
	private int idleW,idleH;
	private double col;
	private int row;
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
	private Sword sword;
	private int swordW,swordH;
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
		health = hp;
		relX = 0;
		sword = new Sword(-10,-10,1,1);
		swordW = 61;
		swordH = 68;
		pics.add(addPics("Jump/JumpRight",2));
		pics.add(addPics("Jump/JumpLeft",2));
		pics.add(addPics("Fall/FallRight",2));
		pics.add(addPics("Fall/FallLeft",2));
		pics.add(addPics("Attack/AttackRight1",4));
		pics.add(addPics("Attack/AttackLeft1",4));
		pics.add(addPics("Run/RunRight",8));
		pics.add(addPics("Run/RunLeft",8));
		pics.add(addPics("Idle/IdleRight",8));
		pics.add(addPics("Idle/IdleLeft",8));
		pics.add(addPics("Death/DeathRight",6));
		pics.add(addPics("Death/DeathLeft",6));
		pics.add(addPics("TakeHit/TakeHitWhiteRight",4));
		pics.add(addPics("TakeHit/TakeHitWhiteLeft",4));
		isRight = true;
		jumping = false;
		striking = false;
		idling = true;
		isHit = false;
		row = RIDLE;
		col = 0;
		image = pics.get(row).get((int)col);
		idleW = image.getWidth(null);
		idleH = image.getHeight(null);
		playerRect = new Rectangle(x,y,w,h);
	}
	
	public void move(boolean []keys){	//moves player	
		if(health<=0){
			death = true;
		}
		//Movement
		else if(keys[STRIKE] && !striking){//
			striking = true;
			col = 0;
		}
		else if(keys[LEFT]){
			isRight = false;
			if(!striking){
				row = LRUN;
				if(x>=LEFTEDGE){//
					x-=vx;
				}
				else if(relX > 0){//
					relX-=vx;
				}
			}
		}
		else if(keys[RIGHT]){
			isRight = true;
			if(!striking){
				row = RRUN;
				if(x<=RIGHTEDGE-w){//
					x+=vx;
				}
				else if(relX < 4350-w){//
					relX+=vx;
				}
			}
		}
		else{
			row = isRight ? RIDLE:LIDLE;
		}
		if(keys[JUMP] && y+h >= Game.HEIGHT){
			vy -= jp;
		}
		y += vy;
		vy += Game.GRAVITY;
		if(y+h >= Game.HEIGHT){
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
					vy = 0;
					h = image.getHeight(null);//
					y = platY - h;
					if(keys[JUMP]){
						vy -= jp;
					}
				}
			}
		}
		if(vy>0){//
			row = isRight ? RFALL:LFALL;
		}
		else if(vy<0){
			row = isRight ? RJUMP:LJUMP;
		}
		
		//Start animation
		h = image.getHeight(null);//
		if(death){//
			row = isRight ? RDEATH:LDEATH;
		}
		else if(striking){//
			row = isRight ? RSTRIKE:LSTRIKE;
			sword = new Sword(x,y,swordW,swordH);
			sword.strike(isRight);//
		}
		else if(isHit){
			row = isRight ? RTAKEHIT:LTAKEHIT;
		}
		
		//Cycle Animation to 0
		if(striking && col>=3){//
			striking = false;
			sword = new Sword(-10,-10,1,1);
			col = 0;
		}
		else if(death && col>=5){//
			Game.setScreen(Game.DEATH);
			col = 5;
		}
		else if(isHit && col>=3){
			col = 0;
			isHit = false;
		}
		else if(col >= pics.get(row).size()-1){//
			col = 0;
		}
		//Iterate frames
		col += 0.2;
	}
	
	public void takeDamage(int damage){
		health-=damage;
		isHit = true;
		col = 0;
	}
		
	public void draw(Graphics g){	//draws player
		if(sword!=null){				//uncomment if you wanna see the sword's hitbox
			g.setColor(Color.RED);
			sword.draw(g);
		}
		g.setColor(Color.YELLOW);
		image = pics.get(row).get((int)col);
		w = row==LSTRIKE || row==RSTRIKE ? idleW:image.getWidth(null);//
		int xVal = row==LSTRIKE ? x-w-idleW:x;
		playerRect = new Rectangle(x,y,w,h);
//		g.fillRect(x,y,w,h);//
		g.drawImage(image,xVal,y,null);
	}    
	
	public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Player Images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}
	
	public boolean playerCollides(Rectangle rect){
		return playerRect.intersects(rect);
	}
	
	public Sword getSword(){
		return sword;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public void setX(int newX){x=newX;}
	public void setY(int newY){y=newY;}
	public int getW(){return w;}
	public int getIdleW(){return idleW;}
	public int getH(){return h;}
	public int getVX(){return vx;}
	public int getVY(){return vy;}
	public int getJP(){return jp;}
	public int getHP(){return health;}
	public int getRelX(){return relX;}
	public boolean isDead(){return health<=0;}//
	public Rectangle getPlayerRect(){return playerRect;}
}
