import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

class Boss {
    public static final int ATTACKLEFT=0, ATTACKRIGHT=1, IDLELEFT=2, IDLERIGHT=3, LRUN=4, RRUN=5, LTAKEHIT=7, RTAKEHIT=8;
    public static final int LDEATH=9, RDEATH=10, FIREBALLYSPAWN=70, FIREBALLXSPAWN=120;

    private int x,y,vx,vy,w,h,health,offsetX;
    private Rectangle hitbox;
    private double col;
    private int row,meleeCooldown,rangedCooldown,distFromPlayer;
    private int range,attackRange;
    private Fireball fireball;

    private boolean facingLeft,attacking,takingDamage,dead;

    private ArrayList<ArrayList<Image>> pics = new ArrayList<ArrayList<Image>>();

    public Boss(int xx, int yy){
        x = xx;
        y = yy;
        vx = 4;
        vy = 0;
        w = 300;
        h = 200;
        col = 0;
        row = 0;
        health = 1000;
        offsetX = x;
        meleeCooldown = 0;
        rangedCooldown = 0;
        distFromPlayer = Math.abs(offsetX - Game.getPlayer().getX());
        hitbox = new Rectangle(offsetX, y, w, h);

        range = 600;
        attackRange = 80;

        facingLeft = true;
        attacking = false;
        takingDamage = false;

        pics.add(addPics("Attack1/AttackLeft1", 10));
        pics.add(addPics("Attack1/AttackRight1", 10));
        pics.add(addPics("Idle/IdleLeft", 5));
        pics.add(addPics("Idle/IdleRight", 5));
        pics.add(addPics("Run/RunLeft", 6));
        pics.add(addPics("Run/RunRight", 6));
        pics.add(addPics("TakeHit/TakeHitLeft", 4));
        pics.add(addPics("TakeHit/TakeHitRight", 4));
        pics.add(addPics("Death/DeathLeft", 10));
        pics.add(addPics("Death/DeathRight", 10));
    }

    public void move(Player player){
        distFromPlayer = Math.abs(offsetX - player.getX());//
        if(row != LDEATH && row != RDEATH){
            if(!attacking && !takingDamage){
                if(player.getX() < offsetX && distFromPlayer < range){
                    //System.out.print("running left ");
                    x -= vx;
                    row = LRUN;
                    facingLeft = true;
                }
                else if(player.getX() > offsetX && distFromPlayer < range){
                    x += vx;
                    row = RRUN;
                    facingLeft = false;
                }
                else{
                    //System.out.print("idle");
                    row = facingLeft ? IDLELEFT : IDLERIGHT;
                }
            }
            if(health <= 0){
                row = facingLeft ? LDEATH : RDEATH;
                col = 0;
            }
        }
        else{
            if(col >= pics.get(row).size()){
                dead = true;
            }
        }
        offsetX = x - Game.getPlayer().getRelX();
        
        attack(player);
        col += 0.2;
        if(col >= pics.get(row).size()){
            col = 0;
            attacking = false;
            takingDamage = false;
        }
        hitbox = new Rectangle(offsetX, y, w, h);
        //if((row == ATTACKLEFT || ATTACKRIGHT) && col == 9){
            //player.takeDamamge();
        //}
    }

    public void attack(Player player){
        if(meleeCooldown >= 50){
            if(player.getX() - offsetX <= attackRange+w && player.getX() - offsetX > 0 && player.getY() + player.getH() >= y){
                attacking = true;
                col = 0;
                row = ATTACKRIGHT;
            }
            if(offsetX - player.getX() <= attackRange && offsetX - player.getX() >= 0 && player.getY() + player.getH() >= y){
                attacking = true;
                col = 0;
                row = ATTACKLEFT;
            }
            meleeCooldown = 0;
        }
        else{
            meleeCooldown++;
        }
        //Makes the boss shoot fireballs
        if(rangedCooldown >= 50){ //Resets the fireball cooldown and creates new fireball
            fireball = new Fireball(x + FIREBALLXSPAWN,y + FIREBALLYSPAWN);
            rangedCooldown = 0;
        }
        else{rangedCooldown++;}
        if(fireball != null){
            fireball.move(player);
            if(fireball.hitPlayer()){fireball = null;} //Removes fireball if it hits the player
        }
    }

    public void takeDamage(int damage){
        health -= damage;
        col = 0;
        takingDamage = true;
        row = facingLeft ? LTAKEHIT : RTAKEHIT;
    }

    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        g.fillRect(offsetX,y,w,h);
        offsetX = x - Game.getPlayer().getRelX();

        Image image = pics.get(row).get((int)col);
        if(attacking){
            g.drawImage(image,offsetX,y-100,null);
        }
        else{
            g.drawImage(image,offsetX,y,null);
        }
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
    public int getY(){return y;}
    public Rectangle getRect(){return hitbox;}
    public boolean isDead(){return dead;}
}
