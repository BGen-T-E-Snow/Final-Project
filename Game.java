import java.awt.*;
import javax.imageio.*;
import java.io.IOException;

import javax.swing.*;
import java.util.ArrayList;

public class Game extends BaseFrame{
	public static final int INTRO=0, GAME=1, DEATH=2, LEFT = 1, RIGHT = 2;//
	public static final int LEVELSELECT=3, LEVEL1=4, LEVEL2=5, LEVEL3=6, CONTROLS=7;//
	public int offset;
	private static int screen = INTRO;//
	private int levelsUnlocked;//

	private static ArrayList<Platform> platforms;
	private static ArrayList<Spider> spiders;
	private static ArrayList<Bat> bats = new ArrayList<Bat>();
	private static Boss boss;//

	private Image intro;//
	private Font arial;//
	Button playButton,controlsButton;//
	Button lvl1,lvl2,lvl3;
	Button exitButton;

	private static Player player;
	
    public Game(){
		super("Game",WIDTH, HEIGHT);

		arial = new Font("Arial", Font.PLAIN, 60);
		playButton = new Button("Arial", Font.PLAIN, "Play", 40, 350, 40);//
		controlsButton = new Button("Arial",Font.PLAIN,"Controls",40,390,40);//
		lvl1 = new Button("Arial", Font.PLAIN, "1",50,250,120);//
		lvl2 = new Button("Arial", Font.PLAIN, "2",400,250,120);//
		lvl3 = new Button("Arial", Font.PLAIN, "3",680,250,120);//
		exitButton = new Button("Arial",Font.PLAIN,"Exit",690,540,60);//
		player = new Player(200,545,32,55,8,0,20,500);
		boss = new Boss(800,400);//

		platforms = new ArrayList<Platform>();
		spiders = new ArrayList<Spider>();
		spiders.add(new Spider(150,545,90,60,2,10,3));
		bats.add(new Bat(400, 80));

		
		platforms.add(new Platform(400,500,91,25));
		platforms.add(new Platform(850,450,91,25));
		back = new ImageIcon("BackGround/BackGround.png").getImage();
		intro = new ImageIcon("intro.jpg").getImage();//

		levelsUnlocked = 1;
	}
	
	@Override
	public void move(){
		if(screen == INTRO){
			if(mb>0){
				//screen = GAME;
			}
			buttonPressed(playButton, GAME);//
			buttonPressed(controlsButton, CONTROLS);//
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
		else if(screen == CONTROLS){
			buttonPressed(exitButton, INTRO);
		}
	}

	public void buttonPressed(Button butt, int scr){//
		if(mb == 1 && butt.getRect().contains(mx,my)){screen = scr;}//
	}//
	
	@Override
	public void draw(Graphics g){
		if(screen == INTRO){
			g.drawImage(intro,-100,0,null);//

			g.setFont(arial);//
			g.setColor(Color.WHITE);//
			FontMetrics fm = g.getFontMetrics(arial);//
			int wid = fm.stringWidth("The Black Labyrinth");//
			g.drawString("The Black Labyrinth",400-wid/2,150);//

			g.setColor(Color.WHITE);//
			if(playButton.getRect().contains(mx,my)){g.setColor(Color.GREEN);}
			playButton.draw(g);//
			g.setColor(Color.WHITE);//
			if(controlsButton.getRect().contains(mx,my)){g.setColor(Color.GREEN);}
			controlsButton.draw(g);//
			
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
		else if(screen == DEATH){}
		else if(screen == CONTROLS){//
			g.drawImage(intro,-100,0,null);
			g.setFont(arial);
			g.setColor(Color.WHITE);//
			FontMetrics fm = g.getFontMetrics(arial);//
			int wid = fm.stringWidth("Controls");//
			g.drawString("Controls",420-wid/2,150);//
			g.drawString(String.format("W  		 -  %20s","Jump"),100,250);//
			g.drawString(String.format("A 		 -  %20s","Move Left"),100,310);//
			g.drawString(String.format("D 		 -  %19s","Move Right"),100,370);//
			g.drawString(String.format("Space bar -  %10s","Attack"),100,430);//
			exitButton.draw(g);
		}//
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
