//Player.java
//Spencer Trepanier (mostly)
//Defines a player, which has a position, a size, speed, jump power, health, a sword and a rectangle


import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Player{
	private final static int LEFT = Game.A ,RIGHT = Game.D, JUMP = Game.W , STRIKE = Game.SPACE, RJUMP = 0, LJUMP = 1, RFALL = 2, LFALL = 3, RSTRIKE = 4, LSTRIKE = 5, RRUN = 6, LRUN = 7, RIDLE = 8, LIDLE = 9, RDEATH = 10, LDEATH = 11, RTAKEHIT = 12, LTAKEHIT = 13, RIGHTEDGE = 650, LEFTEDGE = 150;
	private int x,y,w,h,vx,vy,jp,health,relX;	//initializes int variables for the player
	private boolean isRight, striking, death, isHit;	//initializes the toggles of what the player is doing
	private int idleW,idleH;	//idle size for when the player is striking, the sword is everything outside of these values
	private double col;	//2D list row and col for frames in each row of the 2D list
	private int row;	//
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();	//2D list of frames
	private Sword sword;	//player's sword
	private int swordW,swordH;	//sword hitbox variables
	private Image image;	//current frame of animation
	private Rectangle playerRect;	//player's hitbox
	
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
		sword = null;	//sets sword to null until the player strikes
		swordW = 61;	//swords width and height
		swordH = 68;    //                                   
		pics.add(addPics("Jump/JumpRight",2));             //adds the player's frames to the 2D list
		pics.add(addPics("Jump/JumpLeft",2));              //
		pics.add(addPics("Fall/FallRight",2));             //
		pics.add(addPics("Fall/FallLeft",2));              //
		pics.add(addPics("Attack/AttackRight1",4));        //
		pics.add(addPics("Attack/AttackLeft1",4));         //
		pics.add(addPics("Run/RunRight",8));               //
		pics.add(addPics("Run/RunLeft",8));                //
		pics.add(addPics("Idle/IdleRight",8));             //
		pics.add(addPics("Idle/IdleLeft",8));              //
		pics.add(addPics("Death/DeathRight",6));           //
		pics.add(addPics("Death/DeathLeft",6));            //
		pics.add(addPics("TakeHit/TakeHitWhiteRight",4));  //
		pics.add(addPics("TakeHit/TakeHitWhiteLeft",4));   //
		isRight = true;		//player starts facing right
		striking = false;	//player isn't striking at first
		isHit = false;		//player is not being hit at first
		row = RIDLE;	//player starts his animation by idling
		col = 0;		//sets first frame of idling
		image = pics.get(row).get((int)col);	//sets the current frame
		idleW = image.getWidth(null);	//gets the idle dimensions
		idleH = image.getHeight(null);	//
		playerRect = new Rectangle(x,y,w,h);	//sets the players hitbox
	}
	
	public void move(boolean []keys){	//moves player	
		if(health<=0){
			death = true;	//starts death
		}
		//Movement
		else if(keys[STRIKE] && !striking){
			striking = true;	//sets striking to true
			col = 0;	//sets the column to zero
		}
		else if(keys[LEFT]){
			isRight = false;	//faces left
			if(!striking){
				row = LRUN;		//sets row to left running frames if not striking
				if(x>=LEFTEDGE){   //////George's work/////
					x-=vx;	       //
				}                  //EXPLAIN THIS PART GEORGE ///////////////////////////////////////////////////
				else if(relX > 0){ //
					relX-=vx;      //
				}                  //
			}
		}
		else if(keys[RIGHT]){
			isRight = true;	//faces right
			if(!striking){
				row = RRUN;	//sets row to right running frames if not striking
				if(x<=RIGHTEDGE-w){//                                //
					x+=vx;                                           //////George's work/////
				}                                                    //
				else if(relX < 4200-w){//                            //
					relX+=vx;                                        //EXPLAIN THIS GEORGE //////////////////////////
				}                                                    //
				else if(relX >= 4200-w  && x <= BaseFrame.WIDTH-w){  //
					x+=vx;                                           //
				}                                                    //
			}
		}
		else{
			row = isRight ? RIDLE:LIDLE;	//if not doing anything sets the row to idle frames based on if the player is facing right or left
		}
		if(keys[JUMP] && y+h >= Game.HEIGHT){	//if on the ground and w is pressed
			vy -= jp;	//subtract the jump power to the y velocity (goes up)
		}
		y += vy;	//add the y velocity to the y position
		vy += Game.GRAVITY;	//adds gravity to the y velocity (goes down)
		if(y+h >= Game.HEIGHT){               //if player is on the ground it sets the velocity to zero
			vy = 0;                           //
			h = image.getHeight(null);		  //this line is to make sure the players height doesn't increase accross frames making the player fall through the ground (height of images is not the same in different animations)
			y = Game.HEIGHT-h;                //
		}                                     //
		for(Platform plat : Game.getPlats()){
			int platX, platY, platW;                    //essentially same thing as with ground but for platforms
			platX = plat.getX();                        //
			platY = plat.getY();                        //
			platW = plat.getW();                        //
			if(x <= platX + platW && x + w >= platX){   //checks if player's x is on the plat
				if(y+h+vy >= platY && y+h <= platY){    //checks if player's y is on the plat
					vy = 0;                             //
					h = image.getHeight(null);//        //
					y = platY - h;                      //
					if(keys[JUMP]){                     //all the rest is the same
						vy -= jp;                       //
					}                                   //
				}                                       //
			}                                           //
		}												//
		if(vy>0){							//if falling
			row = isRight ? RFALL:LFALL;    //fall in the direction faced
		}                                   //
		else if(vy<0){                      //if jumping
			row = isRight ? RJUMP:LJUMP;    //jump in the direction faced
		}
		
		//Start animation
//		h = image.getHeight(null);	//
		if(death){//
			row = isRight ? RDEATH:LDEATH;	//sets the row of the 2D list to the list of death frames
		}
		else if(striking){							//if striking 
			row = isRight ? RSTRIKE:LSTRIKE;		//sets the row to striking frames based on the direction faced
			sword = new Sword(x,y,swordW,swordH);	//creates the sword
			sword.strike(isRight);					//sets the swords x based on the direction given
		}
		else if(isHit){								//if taking hit
			row = isRight ? RTAKEHIT:LTAKEHIT;		//sets the row to taking damage frames based on the direction faced
		}
		
		//Cycle Animation to 0
		if(striking && col>=3){						//if the number of frames has been exceeded for strike
			striking = false;						//sets striking to false
			sword = null;							//sets sword to null
			col = 0;								//resets column of frames to zero
		}
		else if(death && col>=5){					//if the number of frames has been exceeded for death
			Game.setScreen(Game.DEATH);				//sets the game's screen to death
			col = 5;								//keeps the column at 5 (player takes a nap)
		}
		else if(isHit && col>=3){					//if the number of frames has been exceeded for taking damage
			isHit = false;							//sets taking damage to false
			col = 0;								//resets column of frames to zero
		}
		else if(col >= pics.get(row).size()-1){		//everthing else has a frame cycle
			col = 0;								//so when it's exceeded it sets the column number to zero and starts again
		}
		//Iterate frames
		col += 0.2;	//every 100ms the frame changes (5 ticks to change frame, 20ms per tick works out to 100ms)
	}
	
	public void takeDamage(int damage){	//makes player take damage
		health-=damage;	//substracts damage given from the health
		isHit = true;	//sets taking damage to true
		col = 0;		//sets the column to zero
	}
		
	public void draw(Graphics g){	//draws player
//		if(sword!=null){				//uncomment if you wanna see the sword's hitbox
//			g.setColor(Color.RED);
//			sword.draw(g);
//		}
		image = pics.get(row).get((int)col);	//sets the current frame 
		w = row==LSTRIKE || row==RSTRIKE ? idleW:image.getWidth(null);	//sets the width if the row is striking 
		int xVal = row==LSTRIKE ? x-w-idleW:x;	//sets the player's x val if striking left (strike image is thick and gets drawn more to the left when striking left to maintain players original position)
		playerRect = new Rectangle(x,y,w,h);	//sets players hitbox
//		g.setColor(Color.YELLOW);
//		g.fillRect(x,y,w,h);//
		g.drawImage(image,xVal,y,null);	//draws the player's current frame
	}    
	
	public ArrayList<Image> addPics(String name,int end){                                                //common amon any classes with frames
		ArrayList<Image> picType = new ArrayList<Image>();                                               //
		for(int i=1; i<=end; i++){                                                                       //
			picType.add(new ImageIcon(String.format("Player Images/%s%03d.png",name,i)).getImage());     //adds the picture to the 2D list given the string name of the images and the number of frames
		}                                                                                                //
		return picType;                                                                                  //
	}
	
	public boolean playerCollides(Rectangle rect){	//checks if player's hitbox is touching the given rectangle
		return playerRect.intersects(rect);         //
	}                                               //
	
	public Sword getSword(){									//getter for the sword, gives a position offscreen if the sword is null
		return sword==null ? new Sword(-10,-10,1,1):sword;      //
	}                                                           //
	
	public int getX(){return x;}								//a bunch of getters used in various classes
	public int getY(){return y;}								//
	public void setX(int newX){x=newX;}							//
	public void setY(int newY){y=newY;}							//
	public int getW(){return w;}								//
	public int getIdleW(){return idleW;}						//
	public int getH(){return h;}								//
	public int getVX(){return vx;}								//
	public int getVY(){return vy;}								//
	public int getJP(){return jp;}								//
	public int getHP(){return health;}							//
	public int getRelX(){return relX;}							//
	public boolean isDead(){return health<=0;}//				//
	public Rectangle getPlayerRect(){return playerRect;}		//
}
