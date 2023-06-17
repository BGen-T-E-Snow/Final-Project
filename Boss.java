//Boss.java
//Spencer Trepanier
//defines the final boss, the mighty minotaur, has the same fields as player except with an attack range. Shoots fireballs at player.
import java.awt.*;
import javax.swing.*;
import java.util.*;

class Boss{
	private final int LRUN = 0, RRUN = 1, LIDLE = 2, RIDLE = 3, LDEATH = 4, RDEATH = 5, LSTRIKE = 6, RSTRIKE = 7, LTAKEHIT = 8, RTAKEHIT = 9;	//constants for rows of frames
	private final int FIREBALLYSPAWN=70, FIREBALLXSPAWN=120;
	private int x,y,w,h,vx,vy,jp,health,offsetX,attackCooldown,rangedCooldown,range;	//boss' values
	private boolean isRight, dead, attacked, striking, isHit;//toggles for the boss

	private double col;	//col & row of 2D arraylist of frames
	private int row;	//
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();	//2D array list of frames
	private Image image;	//current frame
	private Rectangle bossRect;	//boss' hitbox
	private Fireball fireball;	//boss' fireball
	
    public Boss(int xx, int yy, int ww, int hh, int vvx, int hp){
        x = xx;
        y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		health = hp;
        offsetX = x - Game.getPlayer().getRelX();	//sets offset
        isRight = false;		//boss faces left
        attacked = false;       //boss hasn't attacked yet so false
		striking = false;       //boss isn't striking yet so false
        dead = false;           //boss isn't dead yet so false
		isHit = false;          //boss hasn't been hit yet so false
		fireball = null;		//fireball is null since it hasn't been launched yet
		pics.add(addPics("Run/RunLeft",6));                 //adds all the frames to the 2D arraylist 
		pics.add(addPics("Run/RunRight",6));                //
		pics.add(addPics("Idle/IdleLeft",5));               //
		pics.add(addPics("Idle/IdleRight",5));              //
		pics.add(addPics("Death/DeathLeft",10));            //
		pics.add(addPics("Death/DeathRight",10));           //
		pics.add(addPics("Attack1/AttackLeft1",10));        //
		pics.add(addPics("Attack1/AttackRight1",10));       //
 		pics.add(addPics("TakeHit/TakeHitLeft",4));         //
		pics.add(addPics("TakeHit/TakeHitRight",4));        //
		col = 0;     //sets row & col to first frame in idle left
        row = LIDLE;  //
		image = pics.get(row).get((int)col);	//sets image to the current frame
        attackCooldown = 0;     //attack and range cooldown set to 0
        rangedCooldown = 0;     //
		range = 500;	//range set to 500
		bossRect = new Rectangle(offsetX,y,w,h);	//boss' hitbox
    }

    public void move(Player player){	//moves towards player
		if(health <= 0){ 
			row = isRight ? RDEATH:LDEATH;	//sets frame to dead when health is 0 (or less)
		}
		else if(bossRect.intersects(player.getPlayerRect())){	//attacks player when in proximity
			if(!striking){				
				col = 0;            //set column to zero if intersecting and not already striking
			}                       //
			striking = true;        //sets striking to true
		}                     
		else if(offsetX < player.getX() && Math.abs(offsetX - player.getX()) <= range){	//if boss is to the left of the player, golem goes right
			isRight = true;	//sets all the things required to go right accordingly
			row = RRUN;     //
			x+=vx;          //
		}
		else if(offsetX > player.getX() && Math.abs(offsetX - player.getX()) <= range){	//same as above for left
			isRight = false;	//sets all the things required to go left accordingly
			row = LRUN;     	//
			x-=vx;          	//
		}
		else{
			row = isRight ? RIDLE:LIDLE;	//if not doing anything sets row to idle according to the direction faced
		}//
		if(y+h != Game.HEIGHT){               //sets the y when the height of the frame changes
			vy = 0;                           //
			y = Game.HEIGHT-h;                //
		}                                     //
		if(striking){						
			row = isRight ? RSTRIKE:LSTRIKE;	//sets row to strike when striking based on direction
		}
		else if(isHit){
			row = isRight ? RTAKEHIT:LTAKEHIT;	//sets row to takinghit when taking damage based on direction
		}
		if((row == RDEATH || row==LDEATH) && col>=9){	//if death frames are exceeded
			col = 9;			//keep column at 9 until the golem disappears, golem takes a nap
			dead = true;        //nap is set to true
		}
		else if(striking && col>=7){	//if striking frames are exceeded
			col = 0;                    //frames column is set to 0
			striking = false;           //striking stops
		}
		else if(isHit && col>=3){		//if takehit frames are exceeded
			isHit = false;              //stops being hit
			col = 0;					//frames column is set to 0
		}
		else if(col >= pics.get(row).size()-1){	//everything else is cyclical in nature
			col = 0;                           	//resets to zero when exceeded
		}
		col += 0.2;
		if(rangedCooldown >= 50){ //Resets the fireball cooldown and creates new fireball
            fireball = new Fireball(x + FIREBALLXSPAWN,y + FIREBALLYSPAWN);	//creates fireball
            rangedCooldown = 0;	//resets fireball cooldown
        }
        else{
			rangedCooldown++;	//adds to cooldown when it's less than 50
		}
        if(fireball != null){
            fireball.move(player);	//moves fireball to player
            if(fireball.hitPlayer()){fireball = null;} //Removes fireball if it hits the player
        }
    }

    public void attack(Player player, int damage){
        if(attacked){                                             //same attack as with golem
            if(attackCooldown < 50){attackCooldown++;}            //
            else{                                                 //
                attacked = false;                                 //
                attackCooldown = 0;                               //
            }                                                     //
        }                                                         //
        else{                                                     //
            if(bossRect.intersects(player.getPlayerRect())){      //
                attacked = true;                                  //
                player.takeDamage(damage);                        //
            }                                                     //
        }                                                         //
    }
	
	public void takeDamage(Player player, int damage){           //checks if boss is taking damage
		if(player.getSword().swordHit(bossRect) && health>0){    //
			health-=damage;                                      //removes damage from health
			isHit = true;                                        //sets hit to true
			col = 0;                                             //sets frames column to zero
		}                                                        //
	}
    
    public void draw(Graphics g){		//draws minotaur
		image = pics.get(row).get((int)col);	//gets current frame from 2D arraylist list
		w = image.getWidth(null);				//sets width and height of frame
		h = image.getHeight(null);		        //
		offsetX = x - Game.getPlayer().getRelX();	//sets offset relative to player
        bossRect = new Rectangle(offsetX,y,w,h);	//sets hitbox
//        g.fillRect(offsetX,y,w,h);
		g.drawImage(image,offsetX,y,null);	//draws boss
		if(fireball != null){	//draws fireball if there is one
            fireball.draw(g);
        }
    }

    public ArrayList<Image> addPics(String name,int end){                                               //adds boss' frames 
		ArrayList<Image> picType = new ArrayList<Image>();                                              //
		for(int i=0; i<=end-1; i++){                                                                    //
			picType.add(new ImageIcon(String.format("Boss Images/%s-%d.png",name,i)).getImage());       //
		}                                                                                               //
		return picType;                                                                                 //
	}

    public boolean isDead(){return dead;}//gets death status of boss to detect whether it should set it to null
}
