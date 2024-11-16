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

public class Player extends Entity{

	
	public KeyHandler keyH;
	public final int screenX,screenY;
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(22,30,4,6); 		//x,y,width,height
		// 48 - 22*2 = 4
		// 48 - 30 - 12 = 10
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
		
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize*24;
		worldY = gp.tileSize*24;
		speed = 4;
		direction = "down";
		
	//PLAYER STATUS
		maxLife = 2;
		life = maxLife;
		
	}
	
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2  = setup("/player/boy_right_2");

	}
	 
	public void update() {
		
		if(keyH.upPressed==true || keyH.downPressed==true || keyH.leftPressed==true || keyH.rightPressed==true) {
			
							
			if(keyH.upPressed == true) {
				direction = "up";
				
				if(keyH.leftPressed == true) 
					direction = "upLeft";
				if(keyH.rightPressed == true) 
					direction = "upRight";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			
				if(keyH.leftPressed == true) 
					direction = "downLeft";
				if(keyH.rightPressed == true) 
					direction = "downRight";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
				
				if(keyH.upPressed == true) 
					direction = "upLeft";
				if(keyH.downPressed == true) 
					direction = "downLeft";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
				
				if(keyH.upPressed == true) 
					direction = "upRight";
				if(keyH.downPressed == true) 
					direction = "downRight";	
			}
		
			
			// TILE COLLISION
			collisionOn=false;
			gp.cChecker.checkTile(this);
			
			//OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex != 999) {
			hitMonster(gp.monster[monsterIndex], monsterIndex);
			}
			
			 // CHECK EVENT
			gp.eHandler.checkEvent();
			
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
				case"upLeft":
					worldY -= speed;
					worldX -= speed; 
					break;
				case"upRight":
					worldY -= speed;
					worldX += speed; 
					break;
				case"downLeft":
					worldY += speed;
					worldX -= speed; 
					break;
				case"downRight":
					worldY += speed;
					worldX += speed; 
					break;	
				}
			}
			 if(collisionOn==true) {
				if(direction.equals("upLeft")) {
					direction = "up";
					gp.cChecker.checkTile(this);
					if(collisionOn==false) {
						worldY -= speed;
					}
					
			 
				}			//TESTING FOR MOVING ONE DIRECTION IF CAN'T MOVE DIAGNAL
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
		
		if(hitCooldown == true) {
			hitCooldownCounter++;
			if(hitCooldownCounter >= 95) {
				hitCooldown = false;
				hitCooldownCounter = 0;
			}
			
		}

	}
	
	public void pickUpObject(int i) {
		
		if(i != 999) { 
			
		}
		
	}
	public void interactNPC(int i) {
		if(i != 999) { 
			if (keyH.talkPressed == true) {
		        gp.gameState = gp.dialogueState;
		        gp.npc[i].speak(); // Initial dialogue trigger
			}
			keyH.talkPressed = false;
			}
	} 
	public void hitMonster(Entity monster, int monsterIndex) {
		if(hitCooldown == false ) {
			life -= monster.damage;
			hitCooldown = true;
		}
	}
	
	public void draw(Graphics2D g2) { //DRAWING PLAYER
		//g2.setColor(Color.white);
		//g2.fillRect(x,y,gp.tileSize,gp.tileSize);
		
		BufferedImage image = null;
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
		
		//SET 70% TRANSPARENT IF PLAYER IS HIT
		if(hitCooldown == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			if(hitCooldownCounter % 30  == 0)
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		g2.drawImage(image,screenX,screenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
