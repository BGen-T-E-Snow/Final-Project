import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Game extends BaseFrame{
	public static final int INTRO=0, GAME=1, END=2, LEFT = 1, RIGHT = 2;
	private int screen = INTRO;
	
	private Player player;

    public Game() {
		super("Game", 800, 600);
		back = new ImageIcon("intro.png").getImage();
		player = new Player(200,500,50,50,10,0,10);
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
			g.fillRect(0,0,800,600);
			Font fnt = new Font("Arial",Font.PLAIN,32);
			g.setColor(Color.WHITE);
			g.setFont(fnt);
			player.draw(g);
		}
    }
    
    public static void main(String[] args) {
		new Game();
    }	
}
