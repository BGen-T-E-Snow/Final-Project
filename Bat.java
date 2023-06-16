import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

class Bat {
    public static final int TWOSECONDS=100, IDLE=0, LFLY=1, RFLY=2, DEATH=3;
    private int x,y,w,h,vx,health;
    public boolean dead;
    private double col;
    ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
    private int offsetX,frame,row;
	private Rectangle batRect;

    BatDropping poop;

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

        pics.add(addPics("Idle", 4));
        pics.add(addPics("LFly", 4));
        pics.add(addPics("RFly", 4));
        pics.add(addPics("Poof", 20));

        row = IDLE;
        col = 0;
		batRect = new Rectangle(offsetX,y,w,h);
    }

    public void move(Player player, int damage){
		if(health <= 0){
             row = DEATH;
		}
        else if(player.getX() < offsetX && Math.abs(player.getX() - offsetX) < 300){
            x -= vx;
            row = LFLY;
        }
        else if(player.getX() > offsetX && Math.abs(player.getX() - offsetX) < 300){
            x += vx;
            row = RFLY;
        }
        else if(offsetX+w >= player.getX() && offsetX+w<=player.getX()+player.getW()){row = IDLE;}
		else{
			row = IDLE;
		}
        offsetX = x - player.getRelX();
        col += 0.2;
		if(row==DEATH && col>=19){
			col = 0;
			dead = true;
		}
        else if(col >= pics.get(row).size()-1){
			col=0;
		}
        //Code for the bat pooping
        if(poop != null){
			poop.move(player);
            if(poop.getRect().intersects(player.getPlayerRect())){
				player.takeDamage(damage);
				poop = null;
            }
        }
        poop();
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

	public void takeDamage(Player player, int damage){
		if(player.getSword().swordHit(batRect)){
			health-=damage;
		}
	}

    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Image image = pics.get(row).get((int)col);
        batRect = new Rectangle(offsetX,y,w,h);
//		g.fillRect(offsetX,y,w,h);
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
    public Rectangle getRect(){return batRect;}
    public boolean isDead(){return dead;}
}
