import java.awt.*;
import javax.swing.*;
import java.util.*;

class Boss{
	private final int LRUN = 0, RRUN = 1, LIDLE = 2, RIDLE = 3, LDEATH = 4, RDEATH = 5, LSTRIKE = 6, RSTRIKE = 7, LTAKEHIT = 8, RTAKEHIT = 9;
	private int x,y,w,h,vx,vy,jp,health,offsetX,attackCooldown,rangedCooldown,range;
	private final int FIREBALLYSPAWN=70, FIREBALLXSPAWN=120;
	private boolean isRight, idling, dead, attacked, striking, isHit;//

	private double col;
	private int row;
	private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();
	private Image image;
	private Rectangle bossRect;
	private Fireball fireball;

    public Boss(int xx, int yy, int ww, int hh, int vvx, int hp){
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
		striking = false;
        dead = false;
		isHit = false;
		fireball = null;
		pics.add(addPics("Run/RunLeft",6));
		pics.add(addPics("Run/RunRight",6));
		pics.add(addPics("Idle/IdleLeft",5));
		pics.add(addPics("Idle/IdleRight",5));
		pics.add(addPics("Death/DeathLeft",10));
		pics.add(addPics("Death/DeathRight",10));
		pics.add(addPics("Attack1/AttackLeft1",10));
		pics.add(addPics("Attack1/AttackRight1",10));
 		pics.add(addPics("TakeHit/TakeHitLeft",4));
		pics.add(addPics("TakeHit/TakeHitRight",4));
		col = 0;
        row = RRUN;
		image = pics.get(row).get((int)col);
        attackCooldown = 0;
        rangedCooldown = 0;
		range = 500;
		bossRect = new Rectangle(offsetX,y,w,h);
    }

    public void move(Player player){
		System.out.println(pics.get(row).size());
		if(health <= 0){//
			row = isRight ? RDEATH:LDEATH;
		}
		else if(bossRect.intersects(player.getPlayerRect())){
			if(!striking){
				col = 0;
			}
			striking = true;
		}
		else if(offsetX < player.getX() && Math.abs(offsetX - player.getX()) <= range){
			isRight = true;
			row = RRUN;
			x+=vx;
		}
		else if(offsetX > player.getX() && Math.abs(offsetX - player.getX()) <= range){
			isRight = false;
			row = LRUN;
			x-=vx;
		}
		else{
			row = isRight ? RIDLE:LIDLE;
		}//
		if(y+h != Game.HEIGHT){
			vy = 0;
			y = Game.HEIGHT-h;
		}
		if(striking){
			row = isRight ? RSTRIKE:LSTRIKE;
		}
		else if(isHit){
			row = isRight ? RTAKEHIT:LTAKEHIT;
		}
		if((row == RDEATH || row==LDEATH) && col>=9){
			col = 9;
			dead = true;
		}
		else if(striking && col>=7){
			col = 0;
			striking = false;
		}
		else if(isHit && col>=3){
			isHit = false;
			col = 0;
		}
		else if(col >= pics.get(row).size()-1){
			col = 0;
		}
		col += 0.2;
		if(rangedCooldown >= 50){ //Resets the fireball cooldown and creates new fireball
            fireball = new Fireball(x + FIREBALLXSPAWN,y + FIREBALLYSPAWN);
            rangedCooldown = 0;
        }
        else{
			rangedCooldown++;
		}
        if(fireball != null){
            fireball.move(player);
            if(fireball.hitPlayer()){fireball = null;} //Removes fireball if it hits the player
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
            if(bossRect.intersects(player.getPlayerRect())){
                attacked = true;
                player.takeDamage(damage);
            }
        }
    }
	
	public void takeDamage(Player player, int damage){
		if(player.getSword().swordHit(bossRect) && health>0){
			health-=damage;
			isHit = true;
			col = 0;
		}
	}
    
    public void draw(Graphics g){
		image = pics.get(row).get((int)col);
		w = image.getWidth(null);
		h = image.getHeight(null);		
		offsetX = x - Game.getPlayer().getRelX();
        bossRect = new Rectangle(offsetX,y,w,h);
//        g.fillRect(offsetX,y,w,h);
		g.drawImage(image,offsetX,y,null);
		if(fireball != null){
            fireball.draw(g);
        }
    }

    public ArrayList<Image> addPics(String name,int end){
		ArrayList<Image> picType = new ArrayList<Image>();
		for(int i=0; i<=end-1; i++){
			picType.add(new ImageIcon(String.format("Boss Images/%s-%d.png",name,i)).getImage());
		}
		return picType;
	}

    public int getX(){return offsetX;}
    public int getHealth(){return health;}//
    public boolean isDead(){return dead;}//
}
