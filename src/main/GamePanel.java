 package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize= 16; //16X16 character pixels
	final int scale = 3; 
	
	public final int tileSize = originalTileSize*scale; //48x48 
	public final int maxScreenCol = 24;
	public final int maxScreenRow= 18;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize* maxScreenRow; //576 pixels
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS= 60;
	
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public Player player = new Player(this,keyH);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList  = new ArrayList<>();
	Thread gameThread;
	
	
	// GAME STATE 
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;

	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(keyH); 
		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.addSlimes(5);
		gameState = titleState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread!=null) {
		
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >=1) {
				// UPDATE CURRENT INFORMATION SUCH AS CHARACTER POSITIONS
				update();
				
				// DRAW THE SCREEN WITH THE UPDATED INFORMATION
				repaint();
				
				delta--;
			}
			
			
		}	
	}
	
	public void update() {
		
		if(gameState == playState) {
			//PLAYER
			player.update();
			//NPC
			for(int i = 0; i<npc.length;i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i = 0; i<monster.length;i++) {
				if(monster[i] != null) {
					if(monster[i].alive && !monster[i].dying) 
						monster[i].update();
					if(!monster[i].alive)
						monster[i] = null;
				} 
			}
		}
		if(gameState == pauseState) {
			 
		}
		
		
	}
	
	public void paintComponent(Graphics g) {
		 
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		long drawStart = 0;
		
		// DEBUG
		if(keyH.checkDrawTime == true) {
		drawStart = System.nanoTime();
		}
		
		//TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
			
		} else {
		
			tileM.draw(g2);
			
			//ADD ALL ENTITIES TO ENTITYLIST
			entityList.add(player);
			
			for(int i = 0; i < npc.length;i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			for(int i = 0; i < obj.length;i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i = 0; i < monster.length;i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			//SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
				
			
			//DRAW ENTITIES
			for(int i = 0; i<entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			//EMPTY LIST 
			entityList.clear();
				
			ui.draw(g2);
			
		}
		g2.dispose();
	}
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
		
	}
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}