import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

class Bat {
    public static final int TWOSECONDS=100, IDLE=0, LFLY=1, RFLY=2, DEATH=3;
    private int x,y,vx,health;
    public boolean dead;
    private double col;
    ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
    Rectangle hitbox;
    private int offsetX,frame,row;

    BatDropping poop;

    public Bat(int xx, int yy){
        health = 50;
        x = xx;
        y = yy;
        vx = 2;
        health = 75;
        offsetX = x - Game.getPlayer().getRelX();
        frame = 0;
        hitbox = new Rectangle(offsetX,y,32,32);
        dead = false;

        pics.add(addPics("Idle", 4));
        pics.add(addPics("LFly", 4));
        pics.add(addPics("RFly", 4));
        pics.add(addPics("Poof", 20));

        row = IDLE;
        col = 0;
    }

    public void move(Player player){
        if(row != DEATH){
            row = IDLE;
            if(player.getX() < offsetX && Math.abs(player.getX() - offsetX) < 300){
                x -= vx;
                row = LFLY;
            }
            else if(player.getX() > offsetX && Math.abs(player.getX() - offsetX) < 300){
                x += vx;
                row = RFLY;
            }
            else if(player.getX() == offsetX){row = IDLE;}
            offsetX = x - Game.getPlayer().getRelX();
            col += 0.2;
            if(col >= pics.get(row).size()){col=0;}
            //Code for the bat pooping
            if(poop != null){
             poop.move();
                if(poop.getRect().intersects(player.getPlayerRect())){
                player.takeDamage();
                poop = null;
                }
            }
            System.out.println(player.getHP());
            poop();

            if(health <= 0){
                row = DEATH;
                col = 0;
            }
        }
        else{
            if(col >= 20){
                dead = true;
                col = 0;
            }
            else if(!dead){col += 0.2;}
        }
    }

    public void poop(){
        if(frame >= TWOSECONDS){
            poop = new BatDropping(x,y);
            frame = 0;
        }
        if(poop != null){
            if(poop.hitGround()){
                poop = null;
            }
        }
        frame++;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Image image = pics.get(row).get((int)col);
        g.fillRect(offsetX,y,32,32);
        g.drawImage(image,offsetX,y,null);
        
        if(poop != null){poop.draw(g);}
    }

    public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Bat Images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}

    public int getX(){return offsetX;}
    public int getY(){return y;}
    public int getHealth(){return health;}
    public Rectangle getRect(){return hitbox;}
    public boolean isDead(){return dead;}
}
