import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Game extends BaseFrame{
	public static final int INTRO=0, GAME=1, DEATH=2, LEFT = 1, RIGHT = 2;//
	public int offset;
	private static int screen = INTRO;//

	private static ArrayList<Platform> platforms;
	private static ArrayList<Spider> spiders;
	private static ArrayList<Bat> bats = new ArrayList<Bat>();
	private static Boss boss;

	private Image intro;//

	private static Player player;
	
    public Game(){
		super("Game",WIDTH, HEIGHT);
		player = new Player(200,545,32,55,8,0,20,500);
		boss = new Boss(800,400);

		platforms = new ArrayList<Platform>();
		spiders = new ArrayList<Spider>();
		spiders.add(new Spider(150,545,90,60,2,10,3));
		bats.add(new Bat(400, 80));

		
		platforms.add(new Platform(400,500,91,25));
		platforms.add(new Platform(850,450,91,25));
		back = new ImageIcon("BackGround/BackGround.png").getImage();
		intro = new ImageIcon("intro.jpg").getImage();//
	}
	
	@Override
	public void move(){
		if(screen == INTRO){
			if(mb>0){
				screen = GAME;
			}
		}
		else if(screen == GAME){
			for(int i=0; i<spiders.size(); i++){
				Spider spidey = spiders.get(i);
				spidey.move(player);
				spidey.attack(player);
				spidey.takeDamage(player);
				if(spidey.isDead()){
					spiders.remove(i);
				}
			}
			
			for(int i=0; i<bats.size(); i++){
				Bat bat = bats.get(i);
				bat.move(player);
			}
			boss.move(player);
			player.move(keys);
		}
	}

	
	@Override
	public void draw(Graphics g){
		if(screen == INTRO){
			g.drawImage(intro,-100,0,null);//
		}
		else if(screen == GAME){
			offset = -player.getRelX();
			g.drawImage(back,offset,0,null);
//			g.setColor(Color.BLACK);
//			g.fillRect(0,0,getWidth(),getHeight());
			Font fnt = new Font("Arial",Font.PLAIN,32);
			g.setColor(Color.WHITE);
			g.setFont(fnt);
			
			boss.draw(g);
			for(Platform plat:platforms){
				plat.draw(g);
			}
			for(Spider spidey:spiders){
				spidey.draw(g);
			}
			for(int i=0; i<bats.size(); i++){
				Bat bat = bats.get(i);
				bat.draw(g);
			}
			player.draw(g);
		}
		else if(screen == DEATH){//
		}
    }
   
   
	public static Player getPlayer(){
		return player;
	}
	
	public static ArrayList<Platform> getPlats(){
		return platforms;
	}
	
	public static void setScreen(int newScreen){//
		screen = newScreen;
	}

    public static void main(String[] args) {
		new Game();
    }	
}
