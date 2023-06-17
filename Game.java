//Game.java
//Spencer Trepanier & George Zhang
//The Black Labyrinth
//A scroller game with a player, some spikes, bats, spiders, golems and a boss with fireballs.

import java.awt.*;
import javax.imageio.*;
import java.io.IOException;

import javax.swing.*;
import java.util.ArrayList;

public class Game extends BaseFrame{
	public static final int INTRO=0, GAME=1, DEATH=2;//
	public static final int LEVELSELECT=3, CONTROLS=7, WIN=8;//
	public int offset;
	private static int screen = INTRO;//

	private static ArrayList<Platform> platforms;                //arraylists of entities
	private static ArrayList<Spider> spiders;                    //
	private static ArrayList<Spikes> spikes;                    //
	private static ArrayList<Bat> bats = new ArrayList<Bat>();   //
	private static Boss boss;//                                  //the boss

	private Image intro, gameOver;//bg images for intro and game over screens
	private Font arial;//
	Button playButton,controlsButton,mainMenuButton;//buttons
	Button exitButton;

	private static Player player;	//the player
	
    public Game(){
		super("Game",WIDTH, HEIGHT);

		arial = new Font("Arial", Font.PLAIN, 60);									//all the buttons	
		playButton = new Button("Arial", Font.PLAIN, "Play", 40, 350, 40);//        //
		controlsButton = new Button("Arial",Font.PLAIN,"Controls",40,390,40);//     //
		mainMenuButton = new Button("Arial",Font.PLAIN,"Main Menu",310,460,40);//   //
		exitButton = new Button("Arial",Font.PLAIN,"Exit",690,540,60);//            //
		player = new Player(200,545,32,55,5,0,20,100);                             //
		boss = new Boss(3900,HEIGHT-200,300,200,5,200);//                           //

		platforms = new ArrayList<Platform>();	//array list of platforms
		spiders = new ArrayList<Spider>();		//array list of spiders
		spiders.add(new Spider(150,545,90,60,1,9,3));	//adds spiders
		spiders.add(new Spider(900,545,90,60,1,9,3));	//adds spiders
		spiders.add(new Spider(1650,545,90,60,1,9,3));	//adds spiders
		spiders.add(new Spider(2400,545,90,60,1,9,3));	//adds spiders
		spiders.add(new Spider(3150,545,90,60,1,9,3));	//adds spiders
		bats.add(new Bat(500, 80));	//adds bats
		bats.add(new Bat(1000, 80));	//adds bats
		bats.add(new Bat(1500, 80));	//adds bats
		bats.add(new Bat(2000, 80));	//adds bats
		bats.add(new Bat(2500, 80));	//adds bats

		
		platforms.add(new Platform(400,500,91,25));		//adds platforms
		platforms.add(new Platform(850,450,91,25));		//
		platforms.add(new Platform(900,400,91,25));		//
		platforms.add(new Platform(1000,350,91,25));	//
		platforms.add(new Platform(1500,300,91,25));		//
		platforms.add(new Platform(1300,400,91,25));		//
		platforms.add(new Platform(1500,450,91,25));		//
		platforms.add(new Platform(1500,475,91,25));		//
		platforms.add(new Platform(1600,450,91,25));		//
		platforms.add(new Platform(1750,450,91,25));		//
		platforms.add(new Platform(1200,520,91,25));		//
		platforms.add(new Platform(2500,450,91,25));		//
		platforms.add(new Platform(2800,450,91,25));		//
		back = new ImageIcon("BackGround/BackGround.png").getImage();	//gets background image
		intro = new ImageIcon("intro.jpg").getImage();					//gets intro image
		gameOver = new ImageIcon("Game Over.jpg").getImage();			//gets game over image
	}
	
	@Override
	public void move(){	//tackles all the movement in the game
		if(screen == INTRO){
			buttonPressed(playButton, GAME);		//if play button is pressed set screen to game
			buttonPressed(controlsButton, CONTROLS);//if controls button is pressed set screen to controls
		}
		else if(screen == GAME){
			for(int i=0; i<spiders.size(); i++){	//moves spiders
				Spider spidey = spiders.get(i);		//gets a spider from arraylist
				spidey.move(player);				//moves spider
				spidey.attack(player);				//spider attacks player
				spidey.takeDamage(player);			//spider checks if player is attacking
				if(spidey.isDead()){				//if spider is dead, remove it from arraylist
					spiders.remove(i);
				}
			}
			
			for(int i=0; i<bats.size(); i++){    //moves bats
				Bat bat = bats.get(i);           //gets a bat from arraylist
				bat.move(player,2);              //moves bat
				bat.takeDamage(player, 1);		 //checks if player is attacking
				if(bat.isDead()){			 //if bat is dead, remove it from arraylist
					bats.remove(i);
				}
			}                                   
			if(boss!=null){                     //if boss is not null
				boss.move(player);              //moves boss
				boss.attack(player, 2);         //boss attacks player
				boss.takeDamage(player, 1);     //checks if player is attacking
				if(boss.isDead()){				//if boss is dead, set boss to null
					boss = null;                
				}
			}
			player.move(keys);	//moves player
		}
		else if(screen == CONTROLS){
			buttonPressed(exitButton, INTRO);	//sets screen to intro if exit button is pressed while on controls screen
		}
		else if(screen == DEATH){
			if(buttonPressed(mainMenuButton, INTRO)){
				player = null;         //clears everything
				boss = null;           //
				platforms.clear();     //
				spiders.clear();       //
				bats.clear();          //
				
				player = new Player(200,545,32,55,5,0,20,3);   	//resets player and boss
				boss = new Boss(1200,HEIGHT-200,300,200,5,200);	//                         
				
				spiders.add(new Spider(150,545,90,60,1,9,3));	//adds spiders
				spiders.add(new Spider(900,545,90,60,1,9,3));	//adds spiders
				spiders.add(new Spider(1650,545,90,60,1,9,3));	//adds spiders
				spiders.add(new Spider(2400,545,90,60,1,9,3));	//adds spiders
				spiders.add(new Spider(3150,545,90,60,1,9,3));	//adds spiders
				bats.add(new Bat(500, 80));	//adds bats
				bats.add(new Bat(1000, 80));	//adds bats
				bats.add(new Bat(1500, 80));	//adds bats
				bats.add(new Bat(2000, 80));	//adds bats
				bats.add(new Bat(2500, 80));	//adds bats
		
				
				platforms.add(new Platform(400,500,91,25));		//adds platforms
				platforms.add(new Platform(850,450,91,25));		//
				platforms.add(new Platform(900,400,91,25));		//
				platforms.add(new Platform(1000,350,91,25));	//
				platforms.add(new Platform(1500,300,91,25));		//
				platforms.add(new Platform(1300,400,91,25));		//
				platforms.add(new Platform(1500,450,91,25));		//
				platforms.add(new Platform(1500,475,91,25));		//
				platforms.add(new Platform(1600,450,91,25));		//
				platforms.add(new Platform(1750,450,91,25));		//
				platforms.add(new Platform(1200,520,91,25));		//
				platforms.add(new Platform(2500,450,91,25));		//
				platforms.add(new Platform(2800,450,91,25));		//
			}
		}
	}

	public boolean buttonPressed(Button butt, int scr){	//checks if the button is pressed and sets the screen to whatever the button leads to.
		if(mb == 1 && butt.getRect().contains(mx,my)){
			screen = scr;
			return true;
		}//
		else{return false;}
	}//
	
	@Override
	public void draw(Graphics g){	//draws the game
		if(screen == INTRO){
			g.drawImage(intro,-100,0,null);	//draws intro screen

			g.setFont(arial);//                                        //sets fonts
			g.setColor(Color.WHITE);//                                 //
			FontMetrics fm = g.getFontMetrics(arial);//                //
			int wid = fm.stringWidth("The Black Labyrinth");//
			g.drawString("The Black Labyrinth",400-wid/2,150);//

			g.setColor(Color.WHITE);//sets color to white
			if(playButton.getRect().contains(mx,my)){g.setColor(Color.GREEN);}	//if hover, set color to green
			playButton.draw(g);//draws play button
			g.setColor(Color.WHITE);//sets color to white
			if(controlsButton.getRect().contains(mx,my)){g.setColor(Color.GREEN);} //if hover, set color to green
			controlsButton.draw(g);//draws control button
			
		}
		else if(screen == GAME){
			offset = -player.getRelX();	//sets game offset to negative player x
			g.drawImage(back,offset,0,null);	//draws background
//			g.setColor(Color.BLACK);
//			g.fillRect(0,0,getWidth(),getHeight());
			
			if(boss!=null){                       //draws the boss if not null
				boss.draw(g);                     //
			}                                     //
			for(Platform plat:platforms){         //draws all the entities with loops through arraylists
				plat.draw(g);                     //draws platforms
			}                                     //
			for(Spider spidey:spiders){           //
				spidey.draw(g);                   //draws spiders
			}                                     //
			for(Bat bat:bats){     //
				bat.draw(g);                      //draws bats
			}                                     //
			player.draw(g);                       //draws player
		}
		else if(screen == DEATH){
			g.drawImage(gameOver,-130,0,null);//draws game over screen
			g.setColor(Color.WHITE);
			if(mainMenuButton.getRect().contains(mx,my)){g.setColor(Color.RED);}//set color to red if hovered
			mainMenuButton.draw(g);//draws main menu button
		}
		else if(screen == CONTROLS){//draws controls
			g.drawImage(intro,-100,0,null);	//draw background
			g.setFont(arial);							//set font
			g.setColor(Color.WHITE);					//
			FontMetrics fm = g.getFontMetrics(arial);	//
			int wid = fm.stringWidth("Controls");// finds width of the string
			g.drawString("Controls",420-wid/2,150);//draws the string
			g.drawString(String.format("W  		 -  %20s","Jump"),100,250);//          draws each control string
			g.drawString(String.format("A 		 -  %20s","Move Left"),100,310);//     
			g.drawString(String.format("D 		 -  %19s","Move Right"),100,370);//    
			g.drawString(String.format("Space bar -  %10s","Attack"),100,430);//       
			exitButton.draw(g);
		}//
    }
   
   
	public static Player getPlayer(){                          //various getters and setters for the entities and the screen
		return player;                                         //
	}                                                          //
															   //
	public static ArrayList<Platform> getPlats(){              //
		return platforms;                                      //
	}                                                          //
															   //
	public static void setScreen(int newScreen){//             //
		screen = newScreen;                                    //
	}                                                          //
															   
    public static void main(String[] args) {                   
		Game game = new Game();                                
    }	
}
