import java.awt.*;
import javax.swing.*;
import java.util.*;

class Golem{
	private final int LRUN = 0, RRUN = 1, RIDLE = 2, LIDLE = 3, RDEATH = 4, LDEATH = 5, RSMASH = 6, LSMASH = 7;
	private int x,y,w,h,vx,vy,jp,health,offsetX,attackCooldown;
	private boolean isRight, idling, dead, attacked, smashing;//
	private double col;
	private int row;
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
	private Image image;
	private Rectangle golemRect;

    public Golem(int xx, int yy, int ww, int hh, int vvx, int hp){
        x = xx;
        y = yy;
		w = ww;
		h = hh;
		vx = vvx;
		health = hp;
        offsetX = x - Game.getPlayer().getRelX();
        isRight = true;
		idling = true;
        attacked = false;
		smashing = false;
        dead = false;
		pics.add(addPics("Run/RunLeft",9));
		pics.add(addPics("Run/RunRight",9));
		pics.add(addPics("Idle/IdleLeft",4));
		pics.add(addPics("Idle/IdleRight",4));
		pics.add(addPics("Death/DeathRight",10));
		pics.add(addPics("Death/DeathLeft",10));
		pics.add(addPics("Attack/AttackRight",8));
		pics.add(addPics("Attack/AttackLeft",8));
        col = 0;
        row = RRUN;
		image = pics.get(row).get((int)col);
        attackCooldown = 0;
		golemRect = new Rectangle(offsetX,y,w,h);
    }

    public void move(Player player){
		if(health <= 0){//
			row = isRight ? RDEATH:LDEATH;
		}
		else if(golemRect.intersects(player.getPlayerRect())){
			if(!smashing){
				col = 0;
			}
			smashing = true;
		}
		else if(offsetX < player.getX() && Math.abs(offsetX - player.getX()) <= 350){
			isRight = true;
			row = RRUN;
			x+=vx;
		}
		else if(offsetX > player.getX() && Math.abs(offsetX - player.getX()) <= 350){
			isRight = false;
			row = LRUN;
			x-=vx;
		}
		else{
			row = isRight ? RIDLE:LIDLE;
		}//
		if(smashing){
			row = isRight ? RSMASH:LSMASH;
		}
		if((row == RDEATH || row==LDEATH) && col>=9){
			col = 9;
			dead = true;
		}
		else if(smashing && col>=7){
			col = 0;
			smashing = false;
		}
		else if(col >= pics.get(row).size()-1){
			col = 0;
		}
		col += 0.2;
		if(y+h != Game.HEIGHT){
			vy = 0;
			y = Game.HEIGHT-h;
		}
    }

    public void attack(Player player, int damage){
        if(attacked){
            if(attackCooldown < 50){attackCooldown++;}
            else{
                attacked = false;
                attackCooldown = 0;
            }
        }
        else{
            if(golemRect.intersects(player.getPlayerRect())){
                attacked = true;
                player.takeDamage(damage);
            }
        }
    }
	
	public void takeDamage(Player player, int damage){
		if(player.getSword().swordHit(golemRect) && health>0){
			health-=damage;
		}
	}
    
    public void draw(Graphics g){
		image = pics.get(row).get((int)col);
		w = image.getWidth(null);
		h = image.getHeight(null);		
		offsetX = x - Game.getPlayer().getRelX();
        golemRect = new Rectangle(offsetX,y,w,h);
        g.fillRect(offsetX,y,w,h);
		g.drawImage(image,offsetX,y,null);
    }

    public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=1; i<=end; i++){
			picType.add(new ImageIcon(String.format("Golem Images/%s%03d.png",name,i)).getImage());
		}
		return picType;
	}

    public int getX(){return offsetX;}
    public int getHealth(){return health;}//
    public boolean isDead(){return dead;}//
}