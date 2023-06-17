import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//Exists for flying bat enemies to be easier to make. Has many functions that makes controling the bat easy
//such as moving it or making it take damage.
class Bat {
    public static final int TWOSECONDS=100, IDLE=0, LFLY=1, RFLY=2, DEATH=3;
    private int x,y,w,h,vx,health,range;
    public boolean dead; //If it is dead
    private double col;
    ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>(); //ArrayList of all the images for animations
    private int offsetX,frame,row; //frame keeps track of when it is 2 seconds and is time to poop again
	private Rectangle batRect; //hitbox

    BatDropping poop;

    //Returns nothing and creates a new bat. Parameters for the coordinates the bat spawns at.
    public Bat(int xx, int yy){
        x = xx;
        y = yy;
		w = 32;
		h = 32;
        vx = 2;
        health = 3;
        offsetX = x - Game.getPlayer().getRelX();
        frame = 0;
        dead = false;

        //Adds all the animation pictures to pics for easier access
        pics.add(addPics("Idle", 4));
        pics.add(addPics("LFly", 4));
        pics.add(addPics("RFly", 4));
        pics.add(addPics("Poof", 20));

        row = IDLE;
        col = 0;
        range = 300;
		batRect = new Rectangle(offsetX,y,w,h);
    }

    //Returns nothing and takes a Player parameter for easy access to the player and an int that is its damage
    public void move(Player player, int damage){
		if(health <= 0){
             row = DEATH; //Sets row to death animation when no hp left
		}
        else if(player.getX() < offsetX && Math.abs(player.getX() - offsetX) < range){
            x -= vx; //Flyes towards the player when they are within a range of 300 pixels. subtracts
            row = LFLY; //Sets the animation to flying elft animation
        }
        else if(player.getX() > offsetX && Math.abs(player.getX() - offsetX) < range){
            x += vx; //for flying right
            row = RFLY;
        }
        else if(offsetX+w >= player.getX() && offsetX+w<=player.getX()+player.getW()){row = IDLE;} //Idles when it is above player
		else{
			row = IDLE; //Idles if none of the other conditions are met
		}
        offsetX = x - player.getRelX(); //Sets the offset for the way the player moves
        col += 0.2;
		if(row==DEATH && col>=19){
			col = 0;
			dead = true; //Sets dead to true after it has gone through all of its death animation
		}
        else if(col >= pics.get(row).size()-1){
			col=0; //Resets column when it has reached the end of the row
		}
        //Code for the bat pooping
        if(poop != null){ //Will only move the poop if there is poop on screen
			poop.move(player);
            if(poop.getRect().intersects(player.getPlayerRect())){
				player.takeDamage(damage); //makes the player take damage and removes poop after it intersects player
				poop = null;
            }
        }
        poop();
    }

    //Returns nothing and no parameters. Causes the bat to poop every two seconds.
    public void poop(){
        if(frame >= TWOSECONDS){
            poop = new BatDropping(x,y); //Creates a new BatDropping once two seconds have passed
            frame = 0; //Resets frame after it has pooped
        }
        if(poop != null){ //To prevent a null error when using .hitGround
            if(poop.hitGround()){
                poop = null; //Deletes the poop if it has hit the ground
            }
        }
        frame++; //Keeps adding to frame
    }

    //Returns nothing and has player parameter for easy access and damage parameter to know how much dmg to take.
    //Used to make the bat take damage.
	public void takeDamage(Player player, int damage){
		if(player.getSword().swordHit(batRect)){
			health-=damage;
		}
	}

    //Returns nothing and has a Graphics parameter to draw the bat. Used to draw bat.
    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Image image = pics.get(row).get((int)col); //Gets the type of animation and current image it is on
        batRect = new Rectangle(offsetX,y,w,h);
//		g.fillRect(offsetX,y,w,h);
        g.drawImage(image,offsetX,y,null);
        
        if(poop != null){poop.draw(g);} //Prevents null errors
    }

    public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Bat Images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}

    public int getX(){return offsetX;} //Returns the x of the bat and no parameters
    public int getY(){return y;} //Used to get y coordinate of the bat and no parameters
    public int getHealth(){return health;} //Used to get the health of the bat and no parameters
    public Rectangle getRect(){return batRect;} //Used to get the hitbox as a Rectangle of the bat and no parameters
    public boolean isDead(){return dead;} //Returns true if it is dead. False otherwise. No parameters and used to check if
    //bat is dead.
}
