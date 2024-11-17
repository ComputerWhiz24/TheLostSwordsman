package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	public int worldX,worldY;
	public int speed;
	
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
	public String direction = "down";
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public static String dialogues[] = new String[20];
	public static int dialogueIndex = 0;
	
	public BufferedImage image,image2,image3;
	public String name;
	public boolean collision = false; 
	public int type; // 0 = player, 1 = NPC, 2 = Monster
	public double damage;
	//CHARACTER STATUS
	public int maxLife;
	public int life;
	public boolean hitCooldown = false;
	public int hitCooldownCounter = 0;
	boolean attacking = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
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
			if(gp.player.hitCooldown == false) {
				gp.player.life--;
				gp.player.hitCooldown = true;
			}
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
	}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);
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

	public BufferedImage setupAlternate(String imagePath, int widthMult, int heightMult) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image =  uTool.scaledImage(image, gp.tileSize * widthMult, gp.tileSize * heightMult);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	
	
	
	}
	/*
	 public boolean collisionOnUp = false;
	 public boolean collisionOnDown = false;
	 public boolean collisionOnLeft = false;
	 public boolean collisionOnRight = false;

	 */
	
}
