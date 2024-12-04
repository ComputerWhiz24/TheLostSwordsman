package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Entity {

	public GamePanel gp;
	

	public BufferedImage image,image2,image3;
	public BufferedImage up1,up2,down1, down2,left1,left2,right1,right2;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;  
	public static String dialogues[] = new String[20];
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
	
		//COUNTERS 
	public int actionLockCounter = 0;
	public int hitCooldownCounter = 0;
	public int spriteCounter = 0;
	int dyingCounter = 0; 
	public int hpBarCounter = 0;  
	
		//ATTRIBUTES
	public int speed;
	public String name;
	public int type; // 0 = player, 1 = NPC, 2 = Monster
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
	
	public int coin;
	public double attack;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentSpell;
	
	// ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public String description = "";
	
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
	public void speak() {
		if(dialogues[dialogueIndex] == null) 
			dialogueIndex = 0; 
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		switch(gp.player.direction) {
			case"up":
				direction = "down";
				break;
			case"down":
				direction = "up";
				break;
			case"left":
				direction = "right";
				break;
			case"right":
				direction = "left";
				break;
				 
		}
	}
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this,false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean hitPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == 2 && hitPlayer == true) {
			damagePlayer(this);
		}
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
		}
	public void damagePlayer(Entity mon) {
		if(gp.player.hitCooldown == false) {
			gp.playSE(6);
			double monDmg = mon.damage - 0.05*gp.player.defense; //Defense shields damage from monster with some multiplier
			gp.player.life -= monDmg;
			gp.player.hitCooldown = true;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX  + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY  + gp.tileSize> gp.player.worldY - gp.player.screenY &&
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
		g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);
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

	
}
