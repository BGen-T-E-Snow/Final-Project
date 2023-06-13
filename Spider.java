import java.awt.*;
import javax.swing.*;
import java.util.*;

class Spider {
    public static final int LRUN=0, RRUN=1, IDLE=2, DEATH=3;
    private int x,y,vx,vy,w,h,offsetX,jp,inertia,health,attackCooldown;
    private boolean jumpingLeft,jumping,attacked;
    ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
    private int row;
    double col;
    Rectangle hitbox;
    private boolean dead;

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
        pics.add(addPics("LRun",10));
        pics.add(addPics("RRun",10));
        pics.add(addPics("Idle",10));//
        pics.add(addPics("Death",9));//
        col = 0;
        row = RRUN;
        attacked = false;
        attackCooldown = 0;
        dead = false;
    }

    public void move(Player player){
        if(row != DEATH){//
        if(offsetX < player.getX() && y+h >= Game.HEIGHT && Math.abs(offsetX - player.getX()) <= 350){
            x+=vx;
            row = RRUN;
        }
        else if(!jumpingLeft && jumping){
            x+=vx;
            row = RRUN;
        }
        else if(offsetX > player.getX() && y+h >= Game.HEIGHT && Math.abs(offsetX - player.getX()) <= 350){
            x-=vx;
            row = LRUN;
        }
        else if(jumpingLeft && jumping){
            x-=vx;
            row = LRUN;
        }
        else{row = IDLE;}//
        if(Math.abs(offsetX-player.getX()) < 100 && y+h >= Game.HEIGHT){
            vy -= jp;
            vx +=15;
            jumpingLeft = offsetX-player.getX() > 0 ? true : false;
            jumping = true;
        }
        y += vy;
        vy += BaseFrame.GRAVITY;
        if(y+h >= Game.HEIGHT){
			vy = 0;
			y = Game.HEIGHT-h;
            jumping = false;
		}
        if(y+h < Game.HEIGHT && vx != 2){
            vx -= inertia;
        }
        col += 0.2;
        if(col >= pics.get(row).size()){
            col = 0;
        }

        if(health <= 0){
            row = DEATH;
            if(col != 0){col=0;}
        }
    }
    else{
        if(col >= 8){
            dead = true;
            col = 0;
        }
        else if(!dead){col += 0.2;}
    }
    //System.out.println(player.getX() + "   " + player.getY() + "   " + player.getH());
    }

    public void attack(Player player){
        hitbox = new Rectangle(offsetX,y,h,h);
        if(attacked){
            if(attackCooldown < 50){attackCooldown++;}
            else{
                attacked = false;
                attackCooldown = 0;
            }
        }
        else{
            if(hitbox.intersects(player.getPlayerRect())){
                attacked = true;
                player.takeDamage();
            }
        }
    }

    public void takeDamage(Player player){
		if(player.getSword().swordHit(hitbox)){
			if(health>0){
				health--;
			}
		}
	}
    
    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Image image = pics.get(row).get((int)col);
        g.fillRect(offsetX,y,h,h);
		g.drawImage(image,offsetX,y-30,null);
    }

    public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Spider Images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}

    public int getX(){return offsetX;}
    public boolean isDead(){return dead;}//
}
