import java.awt.*;
import javax.swing.*;
import java.util.*;

class Spider {
    public static final int LRUN=0, RRUN=1, IDLE=2, DEATH=3; //Different states of the spider
    private int x,y,vx,vy,w,h,offsetX,jp,inertia,health,attackCooldown; //jp is jump power
    private boolean jumpingLeft,jumping,attacked; //jumping is different from jumping left because it is used to indicate
    //if it is jumping at all, not the direction it's jumping in
    ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>(); //All the pictures for the animations
    private int row; //Animation it is doing
    double col; //The column or the image it is currently on in the current animation. Running, idle, etc.
    Rectangle hitbox;
    private boolean dead; //If the spider is dead

    //Returns nothing and has the coordinates, width, height, velocity, jump power, and hp as parameters. Creates a new spider.
    public Spider(int xx, int yy, int ww, int hh, int vvx, int jjpp, int hp){
        x = xx;
        y = yy;
		w = ww;
		h = hh;
		vx = vvx;
        jp = jjpp;
		health = hp;
        inertia = 1;
        offsetX = x - Game.getPlayer().getRelX();
        hp = 100;
        jumpingLeft = false;
        jumping = false;
        //Adds the pictures of all the animations to pics so they can be accessed easier
        pics.add(addPics("LRun",10));
        pics.add(addPics("RRun",10));
        pics.add(addPics("Idle",10));
        pics.add(addPics("Death",9));
        col = 0;
        row = RRUN;
        attacked = false;
        attackCooldown = 0;
        dead = false;
    }

    //Returns nothing and one Player parameter to make accessing the player's fields easier. Moves the spider.
    public void move(Player player){
        if(row != DEATH){//Only does moving and jumping if it is not dead.
        if(offsetX < player.getX() && y+h >= Game.HEIGHT && Math.abs(offsetX - player.getX()) <= 350){
            x+=vx; //Moves it right if the player is to the right and is on the ground and is within range of 350
            row = RRUN;
        }
        else if(!jumpingLeft && jumping){
            x+=vx; //moves it to the right while not on the ground and jumping right
            row = RRUN;
        }
        else if(offsetX > player.getX() && y+h >= Game.HEIGHT && Math.abs(offsetX - player.getX()) <= 350){
            x-=vx; //moves to left
            row = LRUN;
        }
        else if(jumpingLeft && jumping){
            x-=vx; //Moves it to the left while it is jumping left
            row = LRUN;
        }
        else{row = IDLE;}//For when spider is idling
        if(Math.abs(offsetX-player.getX()) < 100 && y+h >= Game.HEIGHT){ //Allows spider to jump only while on the ground
            vy -= jp; //Adds the jump power to vy to make the spider start movign up
            vx +=15; //Increases vx because jumps should me faster than normal running
            jumpingLeft = offsetX-player.getX() > 0 ? true : false; //Checks which direction it is jumping
            jumping = true;
        }
        if(y+h >= Game.HEIGHT){ //if it has hit the ground
			vy = 0; //Sets vy back to 0 so it doesn't fall through the ground
			y = Game.HEIGHT-h; //Puts the spider on the ground
            jumping = false;
		}
        if(y+h < Game.HEIGHT && (vx >= 4)){ //Makes sure it doesnt decrease speed beyond a certain point
            vx -= inertia; //Decreases vx while it is above the ground to simulate air resistance
        }
        col += 0.2; //Cycles through the images of whichever animation it is on
        if(col >= pics.get(row).size()){
            col = 0; //Resets the animation back to the first image once it has reached the last one
        }

        if(health <= 0){
            row = DEATH; //Makes it go into the death animation
            if(col != 0){col=0;}
        }
    }
    else{
        if(col >= 8){
            dead = true; //Sets dead to true once it has reached the end of its death animation
            col = 0;
        }
        else if(!dead){col += 0.2;} //If it is not done with the death animation, continues to cycle through images
    }
        y += vy; //For jumping
        vy += BaseFrame.GRAVITY; //Decreases vertical velocity to simulate gravity
    }

    //Returns nothing and has a Player parameter to make accessing its fields easier. Causes spider to attack periodically
    public void attack(Player player){
        if(row != DEATH){ //Does not allow spider to attack during death animation
            hitbox = new Rectangle(offsetX,y-30,w,h); //Spider hit box
            if(attacked){ //If it has already attacked once
                if(attackCooldown < 50){attackCooldown++;} //Counts time until it can attack again
                else{
                    attacked = false; //Makes attacked false because the cooldown is up
                    attackCooldown = 0;
                }
            }
            else{
                if(hitbox.intersects(player.getPlayerRect())){
                    attacked = true; //Attacks when it intersects the player
                    player.takeDamage(1);
                }
            }
        }
    }

    //returns nothing and a player parameter to mae access easier. Makes the player take damage.
    public void takeDamage(Player player){
		if(player.getSword().swordHit(hitbox)){
			if(health>0){
				health--;
			}
		}
	}
    
    //Returns nothing and one Graphics parameter to draw the spider.
    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Image image = pics.get(row).get((int)col); //Gets the image in the current row it is on and the column
		g.drawImage(image,offsetX,y-30,null); //Draws the image in the correct position according to hitbox
    }

    public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Spider Images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}

    public int getX(){return offsetX;} //Returns x of spider and no parameters
    public boolean isDead(){return dead;} //Returns true if spider is dead and false otherwise. No parameters.
    public Rectangle getHitbox(){return hitbox;} //Returns the spider's hitbox and no parameters.
}
