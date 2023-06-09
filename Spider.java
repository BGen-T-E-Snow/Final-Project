import java.awt.*;
import javax.swing.*;
import java.util.*;

class Spider {
    public static final int LRUN=0, RRUN=1, IDLE=2;
    private int x,y,vx,vy,h,offsetX,jp,inertia,hp,attackCooldown;
    private boolean jumpingLeft,jumping,attacked,facingLeft;
    ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
    private int row;
    double col;
    Rectangle hitbox;

    public Spider(int xx, int yy){
        x = xx;
        y = yy;
        vx = 2;
        h = 40;
        jp = 10;
        inertia = 1;
        offsetX = x - Game.getPlayer().getRelX();
        hp = 100;
        jumpingLeft = false;
        jumping = false;
        pics.add(addPics("LRun",8));
        pics.add(addPics("RRun",8));
        col = 0;
        row = RRUN;
        attacked = false;
        attackCooldown = 0;
        facingLeft = false;
    }

    public void move(){
        if(offsetX < Game.player.getX() && y+h >= Game.HEIGHT && Math.abs(offsetX - Game.player.getX()) <= 350){
            x+=vx;
            row = RRUN;
        }
        else if(!jumpingLeft && jumping){
            x+=vx;
            row = RRUN;
        }
        else if(offsetX > Game.player.getX() && y+h >= Game.HEIGHT && Math.abs(offsetX - Game.player.getX()) <= 350){
            x-=vx;
            row = LRUN;
        }
        else if(jumpingLeft && jumping){
            x-=vx;
            row = LRUN;
        }
        //else{row = IDLE;}
        if(Math.abs(offsetX-Game.player.getX()) < 100 && y+h >= Game.HEIGHT){
            vy -= jp;
            vx +=15;
            jumpingLeft = offsetX-Game.player.getX() > 0 ? true : false;
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
    }

    public void attack(Player p){
        hitbox = new Rectangle(offsetX,y,h,h);
        if(attacked){
            if(attackCooldown < 50){attackCooldown++;}
            else{
                attacked = false;
                attackCooldown = 0;
            }
        }
        else{
            if(hitbox.intersects(Game.player.getPlayerRect())){
                attacked = true;
                Game.player.takeDamage();
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
}
