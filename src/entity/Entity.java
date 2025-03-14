package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Entity {

	public static GamePanel gp;
	
	public BufferedImage image,image2,image3;
	public BufferedImage up1,up2,down1, down2,left1,left2,right1,right2;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;  
	
	// NPC
	public static Entity currentSpeaker = new Entity(gp);
	public static String[] currentDialogue = new String[20];
	public boolean tradable =false;
	
		//STATE
	public int worldX,worldY;
	public String direction = "down";
	public int spriteNum = 1;
	public static int dialogueIndex = 0;
	public boolean collisionOn = false;
	boolean attacking = false; 
	public boolean alive = true; 
 	public boolean dying = false;
	public boolean hitCooldown = false;
	public boolean hpBarOn = false;
	public boolean onPath = false;
		//COUNTERS 
	public int actionLockCounter = 0;
	public int hitCooldownCounter = 0;
	public int spriteCounter = 0;
	int dyingCounter = 0; 
	public int hpBarCounter = 0;  
	public int projectileCooldown;
	
		// INVENTORY
	public ArrayList<Entity> inventory = new ArrayList<>();
	public int inventorySize = 222;
	public int cols = 5;
	public int rows = 5;
		//ATTRIBUTES
	public int speed;
	public String name;
	public double damage;
	public double maxLife;
	public double life;
	public int level;
	public double xp;
	public double nextLevelXp;
	public int strength;
	public int dexterity; 
	public int vitality;
	public int defense;
	public double defenseMult;
	public int intelligence;
	public int stamina;
	public double mana;
	public int maxMana;
	public int coin;
	public double attack;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentSpell;
	public Projectile projectile;
	
	// ITEM ATTRIBUTES
	public int value;
	public int buyPrice;
	public int sellPrice;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int spellCost;
	public int type; // 0 = player, 1 = NPC, 2 = Monster
	public int hand;
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_collectible = 7;

	//TESTING NEW MOVEMENT
	public boolean collisionOnUp = false;
	public boolean collisionOnLeft = false;
	public boolean collisionOnRight = false;
	public boolean collisionOnDown = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() {}
	public void damageReaction() {}
	public void speak() {} // Will override in NPC subclasses
	public void use(Entity entity) {} // Will override in player class
	public void checkDrop() {}
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	public Color getParticleColor() {
		Color color = null; //PLACEHOLDER
		return color;
	}
	public int getParticleSize() {
		int size = 0;  
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp,target,color,size,speed,maxLife,-2,-1); // -1,-1 MEANS PARTICLE WILL MOVE TOP LEFT
		Particle p2 = new Particle(gp,target,color,size,speed,maxLife,2,-1); // -1,-1 MEANS PARTICLE WILL MOVE TOP LEFT
		Particle p3 = new Particle(gp,target,color,size,speed,maxLife,-2,1); // -1,-1 MEANS PARTICLE WILL MOVE TOP LEFT
		Particle p4 = new Particle(gp,target,color,size,speed,maxLife,2,1); // -1,-1 MEANS PARTICLE WILL MOVE TOP LEFT
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	public void checkCollision() {
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this,false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster); 
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean hitPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == type_monster && hitPlayer == true) {
			damagePlayer(this);
		}
	}
	public void update() {


		setAction();
		checkCollision();

		if(collisionOn == false) { 
			switch(direction) {
			case "up":
				worldY -= speed; break;
			case"down": 
				worldY += speed; break;
			case"left":
				worldX -= speed; break;
			case"right":
				worldX += speed; break;	
			}
		}
		spriteCounter++;
		if(spriteCounter > 11) {
			if(spriteNum==1)
				spriteNum=2;
			else if(spriteNum==2)
				spriteNum=1;	
			spriteCounter=0;
				
		}
		if(projectileCooldown < 180) {
			projectileCooldown++;
		}
	}
	public void damagePlayer(Entity mon) {

		if(gp.player.hitCooldown == false) {
			gp.playSE(6);
			double monDmg = mon.damage - 0.05*gp.player.defense; //Defense shields damage from monster with some multiplier
			gp.player.life -= monDmg;
			gp.player.hitCooldown = true;
		}
	}
	public void shootPlayer(double attack) {
		gp.playSE(6);
		double monDmg = attack;
		gp.player.life -= monDmg;
		gp.player.hitCooldown = true;
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX  + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY  + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
		
		switch(direction) {
		case "up":
			if(spriteNum==1)
				image = up1;
			if(spriteNum==2)
				image=up2;
			break;
		case "down":
			if(spriteNum==1)
				image = down1;
			if(spriteNum==2)
				image=down2;
			break;
		case "left":
			if(spriteNum==1)
				image = left1;
			if(spriteNum==2)
				image=left2;
			break;
		case "right":
			if(spriteNum==1)
				image = right1;
			if(spriteNum==2)
				image=right2;
			break;
		case"upLeft":
			if(spriteNum==1)
				image = left1;
			if(spriteNum==2)
				image = left2;
			break;
		case"upRight":
			if(spriteNum==1)
				image = right1;
			if(spriteNum==2)
				image = right2;
			break;
		case"downLeft":
			if(spriteNum==1)
				image = left1;
			if(spriteNum==2)
				image = left2;
			break;
		case"downRight":
			if(spriteNum==1)
				image = right1;
			if(spriteNum==2)
				image = right2;
			break;
		}
		
		// MONSTER HP BAR
		if(type == 2 && hpBarOn == true) {  
			double oneScale = (double) gp.tileSize/maxLife;
			double hpBarValue = oneScale * life;
			
			g2.setColor(new Color(35,35,35));
			g2.fillRect(screenX-1, screenY-11, gp.tileSize + 2, 12);
			
			g2.setColor(Color.red);
			g2.fillRect(screenX, screenY - 10, (int) hpBarValue, 10);
			
			hpBarCounter++;
			
			if(hpBarCounter >= 600) {
				hpBarCounter = 0;
				hpBarOn = false;
			}
		}
	}
		if(dying == true) {
			death(g2);
		}
		g2.drawImage(image, screenX, screenY,null);
	}
	public void death(Graphics2D g2) {
		int mult = 20;
		dyingCounter++;
		if(dyingCounter <= mult) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > mult && dyingCounter <=mult*2) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > mult*2 && dyingCounter <=mult*3) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > mult*3 && dyingCounter <=mult*4) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > mult*4 && dyingCounter <=mult*5) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > mult*5 && dyingCounter <=mult*6) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > mult*6 && dyingCounter <=mult*7) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > mult*7 && dyingCounter <=mult*8) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > mult*8) {
			dying = false;
			alive = false; 
		}
		
	}
	public void respawnMonster(int idx) {
		gp.aSetter.addSlime(idx);
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image =  uTool.scaledImage(image, gp.tileSize, gp.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	
	}
	public BufferedImage setupAlternate(String imagePath, double widthMult, double heightMult) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image =  uTool.scaledImage(image,(int) (gp.tileSize * widthMult), (int) (gp.tileSize * heightMult));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (worldX + solidArea.x) / gp.tileSize;
		int startRow = (worldY + solidArea.y) / gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow,  this);
		
		if(gp.pFinder.search()) {
			
			// NEXT WORLD X AND WORLD Y
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			System.out.println(nextX / gp.tileSize);
			System.out.println(nextY / gp.tileSize);
			// ENTITY'S SOLID AREA POSITION
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) { // GO UP
				direction = "up";
				System.out.println(1);
			} 
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) { // DOWN
				direction = "down";
				System.out.println(2);
			} 
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) { // LEFT OR RIGHT
				if(enLeftX > nextX) {
					direction = "left";
					System.out.println(3);
				} 
				if (enLeftX < nextX) {
					direction = "right";
					System.out.println(4);
				}
			}
			else if (enTopY > nextY && enLeftX > nextX) { // UP OR LEFT
				System.out.println(5);
				direction = "up";
				checkCollision();
				if(collisionOn) {
					System.out.println(6);
					direction = "left";
				}
			}
			else if (enTopY > nextY && enLeftX < nextX) { // UP OR RIGHT
				direction = "up";
				System.out.println(7);
				checkCollision();
				if(collisionOn) {
					System.out.println(8);
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) { // DOWN OR LEFT
				direction = "down";
				System.out.println(9);
				checkCollision();
				if(collisionOn) {
					direction = "left";
					System.out.println(10);
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) { // DOWN OR RIGHT
				direction = "down";
				System.out.println(11);
				checkCollision();
				if(collisionOn) {
					direction = "right";
					System.out.println(12);
				}
			 }
		
			// WHEN REACHES GOAL, STOPS NPC FROM MOVING
			int nextCol = gp.pFinder.pathList.get(0).col;
			int nextRow = gp.pFinder.pathList.get(0).row;
			if(nextCol == goalCol && nextRow == goalRow) {
				onPath = false;
			}
		}
	}
}
