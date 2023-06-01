import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Game extends BaseFrame{
	public static final int INTRO=0, GAME=1, END=2, LEFT = 1, RIGHT = 2;
	private int screen = INTRO;
	
	public static ArrayList<Platform> platforms;

	public static Player player;
	private static Platform plat1;
	
    public Game() {
		super("Game",WIDTH, HEIGHT);
		back = new ImageIcon("intro.png").getImage();
		player = new Player(200,500,50,50,10,0,15);
		platforms = new ArrayList<Platform>();
		platforms.add(plat1 = new Platform(400,500,100,36));
    }
    

	public void move(){
		if(screen == INTRO){
			if(mb>0){
				screen = GAME;
			}
		}
		else if(screen == GAME){
			player.move(keys);
		}
	}

	
	@Override
	public void draw(Graphics g){
		if(screen == INTRO){
		}
		else if(screen == GAME){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			Font fnt = new Font("Arial",Font.PLAIN,32);
			g.setColor(Color.WHITE);
			g.setFont(fnt);
			player.draw(g);
			plat1.draw(g);
		}
    }
   
   
	public static Player getPlayer(){
		return player;
	}
	
	public static ArrayList<Platform> getPlats(){
		return platforms;
	}

    public static void main(String[] args) {
		new Game();
    }	
}
