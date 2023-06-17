import java.awt.*;
import javax.swing.ImageIcon;

//Exists to make managing the bosses fireball easier. An object of this class will fly towards the player and
//damage the player when they intersect.
class Fireball {
    private int x,y,offsetX,w,h; //offsetX accounts for the players movements and moves it in the opposite direction
    //acordingly
    private int velocity; //The speed of the fireball
    private double radians,relX,relY,vx,vy; //relX and relY is the coordinates of the player relative to itself
    private boolean hitPlayer; //If it has hit the player yet

    private Rectangle hitbox;

    private Image firePic;

    //Returns nothing and has coordinate parameters for where it is created. Creates a fireball.
    public Fireball(int xx, int yy){
        x = xx;
        y = yy;
        w = 80;
        h = 80;
        hitPlayer = false; //If it has hit the player yet
        offsetX = x - Game.getPlayer().getRelX();
        
        velocity = 20;
        relX = Game.getPlayer().getX() - offsetX;
        relY = y - Game.getPlayer().getY();
        radians = Math.atan(relY/relX);

        vx = velocity * Math.cos(radians);
        vy = Math.abs(velocity * Math.sin(radians));

        hitbox = new Rectangle(offsetX,y,w,h);
        firePic = new ImageIcon("Fireball.png").getImage();

        if(Game.getPlayer().getX() <= offsetX){
            vx *= -1; //For when the fireball is to the right of the player
        }
        if(Game.getPlayer().getY() <= y){
            vy *= -1; //Makes the fireball fly in the correct y direction
        }
    }

    //Returns nothing and has a player as the parameter. Moves the fireball towards the player.
    public void move(Player player){
        x += vx;
        y += vy;
        offsetX = x - Game.getPlayer().getRelX();

        hitbox = new Rectangle(offsetX,y,w,h);
        if(hitbox.intersects(player.getPlayerRect())){
            hitPlayer = true; //Makes the player take damage if they intersect
            player.takeDamage(5);
        }
    }

    //Returns nothing and a Graphics parameter to draw. Draws the fireball.
    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(firePic,offsetX,y,null);
    }

    public int getX(){return offsetX;} //Returns the x coordinate of the fireball. No parameters.
    public int getY(){return y;} //Returns the y coordinate of the fireball. No parameters.
    public Rectangle getRect(){return hitbox;} //Returns the hitbox of the fireball. No parameters.
    public boolean hitPlayer(){return hitPlayer;} //Returns true if it has hit the player. False other wise and no parameters.
}
