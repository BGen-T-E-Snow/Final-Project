import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.ImageIcon;

class Fireball {
    private int x,y,offsetX,w,h;
    private int velocity;
    private double radians,relX,relY,vx,vy; //Player cordinates relative to itself
    private double col;
    private boolean hitPlayer;

    private Rectangle hitbox;

    private ArrayList<Image> pics = new ArrayList<Image>();
    private Image firePic;

    public Fireball(int xx, int yy){
        try {
            for(int i=1; i<=10; i++){
                pics.add(ImageIO.read(new File(String.format("Fireball Images/%s%03d.png","Fireball",i))));
            }
        }
		catch (IOException e) {
			System.out.println(e);
		}
        x = xx;
        y = yy;
        w = 80;
        h = 80;
        hitPlayer = false;
        offsetX = x - Game.getPlayer().getRelX();
        
        velocity = 20;
        relX = Game.getPlayer().getX() - offsetX;
        relY = y - Game.getPlayer().getY();
        radians = Math.atan(relY/relX);

        vx = velocity * Math.cos(radians);
        vy = Math.abs(velocity * Math.sin(radians));

        hitbox = new Rectangle(offsetX,y,w,h);
        firePic = new ImageIcon("Fireball.png").getImage();

        col = 0;

        if(Game.getPlayer().getX() <= offsetX){
            vx *= -1;
        }
        if(Game.getPlayer().getY() <= y){
            vy *= -1;
            //System.out.println("above");
        }
    }

    public void move(Player player){
        x += vx;
        y += vy;
        //System.out.println(vy);
        offsetX = x - Game.getPlayer().getRelX();
        col += 0.5;
        if(col >= 10){col = 0;}

        hitbox = new Rectangle(offsetX,y,w,h);
        if(hitbox.intersects(player.getPlayerRect())){
            hitPlayer = true;
            player.takeDamage();
        }
    }

    public void draw(Graphics g){
        offsetX = x - Game.getPlayer().getRelX();
        Image image = pics.get((int)col);

        g.setColor(Color.RED);
        g.fillRect(offsetX,y,w,h);
        Graphics2D g2d = (Graphics2D)g;

        AffineTransform rot = new AffineTransform();
		rot.rotate(Math.toDegrees(radians),image.getWidth(null),image.getHeight(null));
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);

        //g2d.drawImage(image,rotOp,offsetX,y);
        g2d.drawImage(firePic,offsetX,y,null);
    }

    public int getX(){return offsetX;}
    public int getY(){return y;}
    public Rectangle getRect(){return hitbox;}
    public boolean hitPlayer(){return hitPlayer;}
}