import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Game extends JFrame{
	GamePanel game = new GamePanel();
	public Game(){
		super("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);	//adds game to the frame
		pack();  // set the size of my Frame exactly big enough to hold the contents
		setVisible(true);
	}
	
	public static void main(String[] arguments) {
		Game frame = new Game();
	}
}

class GamePanel extends JPanel implements KeyListener, ActionListener{
	public static final int INTRO = 0, GAME = 1, DEATH = 2;	//screens
	public static final int WIDTH = 800, HEIGHT = 600;	//width & height
	
	public static int screen = INTRO;
	
	private boolean []keys;
	
	javax.swing.Timer timer;
	
	public GamePanel(){ //sets all the elements of the game before it starts
		keys = new boolean[KeyEvent.KEY_LAST+1];
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void move(){
		if(screen == INTRO){
			
		}
		else if(screen == DEATH){
			
		}
		else if(screen == GAME){
    {
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		move(); // never draw in move
		repaint(); // only draw
	}
	
	@Override
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = false;
	}	
	
	@Override
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = true;
	}
	
	@Override
	public void keyTyped(KeyEvent ke){
		if(keys[32] && screen == INTRO){	//starts game
			screen = GAME;
		}
	}

	@Override
	public void paint(Graphics g){
		if(screen == INTRO){	//draws intro screen
		}
		else if(screen == DEATH){	//draws death screen
		}
		else if(screen == GAME){

		}
	}
}
