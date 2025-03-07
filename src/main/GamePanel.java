
package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

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
	
	// FULLSCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreen = false;
	
	// MAP
	public final int maxMap = 10;
	public int currentMap = 1;
	
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
	public Entity obj[][] = new Entity[maxMap][10];
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public ArrayList<Entity> particleList = new ArrayList<>(); 
	public ArrayList<Entity> projectileList = new ArrayList<>();
	ArrayList<Entity> entityList  = new ArrayList<>();
	Thread gameThread;
	public Config config = new Config(this);
	 
	
	// GAME STATE 
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int gameOverState = 4;
	public final int transitionState = 5;
	public final int tradeState = 6;
	
	
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
		aSetter.setInteractiveTile();
		gameState = titleState;
		tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
	}
	public void retry() {
		player.setDefaultPosition();
		player.restoreHealthAndMana();
		aSetter.setNPC();
		aSetter.addSlimes(5);
	}
	public void restart() {
		player.setDefaultPosition();
		player.setDefaultValues();
		player.restoreHealthAndMana();
		player.setItems(); 
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();   
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
//				drawToTempScreen();
//				drawToScreen();
				
				delta--;
			}
		}	
	}
	
	public void update() {
		
		if(gameState == playState) {
			// PLAYER
			player.update();
			// UPDATES NPCS
			for(int i = 0; i<npc[1].length;i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			// UPDATE MONSTERS 
			for(int i = 0;  i<monster[1].length;i++) {
				if(monster[currentMap][i] != null) {
					if(monster[currentMap][i].alive && !monster[currentMap][i].dying) 
						monster[currentMap][i].update();
					if(!monster[currentMap][i].alive) {
						monster[i] = null;
					}
				}
			}
			// UPDATE PROJECTILES
			for(int i = 0; i<projectileList.size();i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive) 
						projectileList.get(i).update();
					if(!projectileList.get(i).alive)
						projectileList.remove(i);
				} 
			}
			for(int i = 0; i<particleList.size();i++) {
				if(particleList.get(i) != null) {
					if(particleList.get(i).alive)  
						particleList.get(i).update();
					if(!particleList.get(i).alive)
						particleList.remove(i);
				} 
			}
			// UPDATE STATUS OF INTERACTIVE TILES
			for(int i = 0; i < iTile[1].length; i++) {
				if(iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
		}
		if(gameState == pauseState) {
			 
		}
		
		
	}
	public void drawToTempScreen() {
		//TITLE SCREEN
				if(gameState == titleState) {
					ui.draw(g2);
					
				} else {
				
					tileM.draw(g2);
					
					// DRAWING INTERACTIVE TILES (BREAKABLE)
					for(int i = 0; i < iTile[1].length; i++) {
						if(iTile[currentMap][i] != null) {
							iTile[currentMap][i].draw(g2);
						}
					}
					
					//ADD ALL ENTITIES TO ENTITYLIST
					entityList.add(player);
					
					for(int i = 0; i < npc[1].length;i++) {
						if(npc[currentMap][i] != null) {
							entityList.add(npc[currentMap][i]);
						}
					}
					for(int i = 0; i < obj[1].length;i++) {
						if(obj[currentMap][i] != null) {
							entityList.add(obj[currentMap][i]);
						}
					}
					for(int i = 0; i < monster[1].length;i++) {
						if(monster[currentMap][i] != null) {
							entityList.add(monster[currentMap][i]);
						}
					}
					for(int i = 0; i < projectileList.size();i++) {
						if(projectileList.get(i)!= null) {
							entityList.add(projectileList.get(i));
						}
					}
					for(int i = 0; i < particleList.size();i++) {
						if(particleList.get(i)!= null) {
							entityList.add(particleList.get(i));
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
	}
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
		
	}
	public void paintComponent(Graphics g) {
		 
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; // DIFFERENT G2 THAN THE FULLSCREEN G2
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
			
			// DRAWING INTERACTIVE TILES (BREAKABLE)
			for(int i = 0; i < iTile[1].length; i++) {
				if(iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
				}
			}
			
			//ADD ALL ENTITIES TO ENTITYLIST
			entityList.add(player);
			
			for(int i = 0; i < npc[1].length;i++) {
				if(npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}
			for(int i = 0; i < obj[1].length;i++) {
				if(obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}
			for(int i = 0; i < monster[1].length;i++) {
				if(monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}
			for(int i = 0; i < projectileList.size();i++) {
				if(projectileList.get(i)!= null) {
					entityList.add(projectileList.get(i));
				}
			}
			for(int i = 0; i < particleList.size();i++) {
				if(particleList.get(i)!= null) {
					entityList.add(particleList.get(i));
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
