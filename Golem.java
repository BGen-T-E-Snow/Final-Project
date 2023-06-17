//Player.java
//Spencer Trepanier (mostly)
//Defines a golem, which has a position, a size, speed, health, attack (smash) and a rectangle
import java.awt.*;
import javax.swing.*;
import java.util.*;

class Golem{
	private final int LRUN = 0, RRUN = 1, RIDLE = 2, LIDLE = 3, RDEATH = 4, LDEATH = 5, RSMASH = 6, LSMASH = 7;	//final constants for rows
	private int x,y,w,h,vx,vy,jp,health,offsetX,attackCooldown;	//int vals of the golem
	private boolean isRight, dead, attacked, smashing;	//toggles for what the golem is doings
	private double col;	//frame row and col
	private int row;	//
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();	//frames
	private Image image;	//current image
	private Rectangle golemRect;	//golem's hitbox

    public Golem(int xx, int yy, int ww, int hh, int vvx, int hp){
        x = xx;
        y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		health = hp;
        offsetX = x - Game.getPlayer().getRelX();
        isRight = false;			//sets golem facing left
        attacked = false;           //this boolean is for when the golem has finished attacking, sets it to true in between
		smashing = false;           //not smashing 
        dead = false;               //not dead
		pics.add(addPics("Run/RunLeft",9));                 //loads frames
		pics.add(addPics("Run/RunRight",9));                //
		pics.add(addPics("Idle/IdleLeft",4));               //
		pics.add(addPics("Idle/IdleRight",4));              //
		pics.add(addPics("Death/DeathRight",10));           //
		pics.add(addPics("Death/DeathLeft",10));            //
		pics.add(addPics("Attack/AttackRight",8));          //
		pics.add(addPics("Attack/AttackLeft",8));           //
        col = 0;		//sets col to zero and row to idle
        row = RIDLE;	//
		image = pics.get(row).get((int)col);	//sets current frame to the first frame in idle
        attackCooldown = 0;						//sets attack cooldown to 0
		golemRect = new Rectangle(offsetX,y,w,h);	//makes hitbox
    }

    public void move(Player player){
		if(health <= 0){	//if health is 0 (or lower)
			row = isRight ? RDEATH:LDEATH;	//sets row to death based on direction
		}
		else if(golemRect.intersects(player.getPlayerRect())){ //attacks player when in proximity
			if(!smashing){                                     //
				col = 0;                                       //set column to zero if intersecting and not already smashing
			}                                                  //
			smashing = true;                                   //sets smashing to true
		}
		else if(offsetX < player.getX() && Math.abs(offsetX - player.getX()) <= 350){	//if golem is to the left of the player, golem goes right
			isRight = true;	//sets all the things required to go right accordingly
			row = RRUN;     //
			x+=vx;          //
		}
		else if(offsetX > player.getX() && Math.abs(offsetX - player.getX()) <= 350){	//same as above for left
			isRight = false;    //sets all the things required to go left accordingly
			row = LRUN;         //
			x-=vx;              //
		}
		else{
			row = isRight ? RIDLE:LIDLE;	//if not doing anything sets row to idle according to the direction faced
		}//
		if(y+h != Game.HEIGHT){	//sets the y when the height of the frame changes
			vy = 0;
			y = Game.HEIGHT-h;
		}
		if(smashing){
			row = isRight ? RSMASH:LSMASH;	//if smashing sets the row to smashing according to the direction
		}
		if((row == RDEATH || row==LDEATH) && col>=9){	//if death frames are exceeded
			col = 9;		//keep column at 9 until the golem disappears, golem takes a nap
			dead = true;	//nap is set to true
		}
		else if(smashing && col>=7){	//if smashing frames are exceeded
			col = 0;					//frames column is set to 0
			smashing = false;			//smashing stops
		}
		else if(col >= pics.get(row).size()-1){	//everything else is cyclical in nature
			col = 0;							//resets to zero when exceeded
		}
		col += 0.2;	//iterates through each frame once every 100ms
    }

    public void attack(Player player, int damage){		//George's work, see spider
        if(attacked){										//If it has already attacked once
            if(attackCooldown < 50){attackCooldown++;}		//Counts time until it can attack again
            else{				
                attacked = false;							//Makes attacked false because the cooldown is up
                attackCooldown = 0;
            }
        }
        else{
            if(golemRect.intersects(player.getPlayerRect())){
                attacked = true;							//Attacks when it intersects the player
                player.takeDamage(damage);
            }
        }
    }
	
	public void takeDamage(Player player, int damage){	//golem checks if damage should be taken
		if(player.getSword().swordHit(golemRect) && health>0){	//if players sword is intersecting with golem's hitbox 
			health-=damage;										//take given amount of damage
		}
	}
    
    public void draw(Graphics g){
		image = pics.get(row).get((int)col);	//sets current frame
		w = image.getWidth(null);				
		h = image.getHeight(null);		
		offsetX = x - Game.getPlayer().getRelX();	//sets the offset relative to the player
        golemRect = new Rectangle(offsetX,y,w,h);	//sets the hitbox
//        g.fillRect(offsetX,y,w,h);		
		g.drawImage(image,offsetX,y,null);	//draws the current frame of the golem
    }

    public ArrayList<Image> addPics(String name,int end){                                               //adds frames of golem to 2D list
		ArrayList<Image> picType = new ArrayList<Image>();                                              //
		for(int i=1; i<=end; i++){                                                                      //
			picType.add(new ImageIcon(String.format("Golem Images/%s%03d.png",name,i)).getImage());     //same as in every other entity class
		}                                                                                               //
		return picType;                                                                                 //
	}

    public int getX(){return offsetX;}		//various getters
    public int getHealth(){return health;}	//
    public boolean isDead(){return dead;}	//
}
