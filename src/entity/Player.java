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
import object.OBJ_Key;
import object.OBJ_Shield;
import object.OBJ_Sword;

public class Player extends Entity{

	public int spriteAttackCounter = 0;
	public boolean swinging = false;
	public KeyHandler keyH;
	public final int screenX,screenY;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public int inventorySize = 222;
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
		
		attackArea.width = 36;
		attackArea.height = 20;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize*24;
		worldY = gp.tileSize*24;
		speed = 4;
		direction = "down";
		
	//PLAYER STATUS
		maxLife = 2;
		life = maxLife;
		damage = 0.25;
		level = 0;
		strength = 0;
		vitality = 0;
		dexterity = 0;
		xp = 0;
		nextLevelXp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword(gp);
		currentShield = new OBJ_Shield(gp);
		getAttack();
		getDefense();
		
	}
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
	}
	public double getAttack() {
		return attack = damage * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = vitality * currentShield.defenseValue;
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
	public void getPlayerAttackImage() {
		
		attackUp1 = setupAlternate("/player/boy_attack_up_1",1,2);
		attackUp2 = setupAlternate("/player/boy_attack_up_2",1,2);
		attackDown1 = setupAlternate("/player/boy_attack_down_1",1,2);
		attackDown2 = setupAlternate("/player/boy_attack_down_2",1,2);
		attackLeft1 = setupAlternate("/player/boy_attack_left_1",2,1);
		attackLeft2 = setupAlternate("/player/boy_attack_left_2",2,1);
		attackRight1 = setupAlternate("/player/boy_attack_right_1",2,1);
		attackRight2 = setupAlternate("/player/boy_attack_right_2",2,1);
	}
	
	public void update() { 
		
		if(keyH.attackPressed) {
			playerAttacking();
		}
		if(keyH.upPressed==true || keyH.downPressed==true || keyH.leftPressed==true || keyH.rightPressed==true || keyH.talkPressed==true) {
			
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
			if(monsterIndex != 999) 
				hitByMonster(monsterIndex);
			
			
			 // CHECK EVENT
			gp.eHandler.checkEvent();
			
			if(collisionOn == false && keyH.talkPressed==false) {
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
			else if(collisionOn==true) {
				gp.cChecker.checkTile(this);
				 switch(direction) {
				 case"upLeft":
						if(collisionOnLeft == false) {
							worldX-=speed;
						}
						else if(collisionOnUp == false) {
							worldY -= speed;
						}
						break;
					case"upRight":
						if(collisionOnRight == false) {
							worldX+=speed;
						}
						else if(collisionOnUp == false) {
							worldY -= speed;
						}
						break;
					case"downLeft":
						if(collisionOnLeft == false) {
							worldX-=speed;
						}
						else if(collisionOnDown == false) {
							worldY += speed;
						}
						break;
					case"downRight":
						if(collisionOnRight == false) {
							worldX+=speed;
						}
						else if(collisionOnDown == false) {
							worldY += speed;
						}
						break;	
				 }
					
					
					
				}			//TESTING FOR MOVING ONE DIRECTION IF CAN'T MOVE DIAGNAL
			}
		else {
			if(!keyH.attackPressed) { // fixed bug where sprite was always walking even if not moving
			spriteNum = 1;
			spriteCounter = 0;
			}
		}
			
			keyH.talkPressed = false; // Talk pressed turns off after checking for collision
			
			if(keyH.attackPressed == false) {
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
	
	public void playerAttacking() {
		attacking = true;
		spriteAttackCounter++;
		if(spriteAttackCounter == 1)
			gp.playSE(7);
		if(spriteAttackCounter <= 2) //SHORT BUFFER
			spriteNum = 1;
		if(spriteAttackCounter  > 2 && spriteAttackCounter <= 25) { // ATTACK ANIMATION
			spriteNum = 2;
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width; 
			int solidAreaHeight = solidArea.height;
			
			switch(direction) {
			case "up":   worldY-= attackArea.height; break;
			case "down": worldY+= attackArea.height; break;
			case "left": case"downLeft": case "upLeft": worldX-= attackArea.width; break;
			case "right": case"downRight": case "upRight": worldX+= attackArea.width; break;
			}
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex != 999) {
				damageMonster(monsterIndex);
			}
			worldX = currentWorldX;
			worldY =  currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
			
		}
		if(spriteAttackCounter > 25) { //FINISH ANIMATION 
			spriteNum = 1;
			spriteAttackCounter = 0;
			attacking = false;
			keyH.attackPressed = false;
			swinging = false; 
	
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
	public void hitByMonster(int idx) {
		if(hitCooldown == false &&  gp.monster[idx].dying == false) {
			gp.playSE(6);
			double monDmg = gp.monster[idx].damage - 0.05*defense; //Defense shields damage from monster
			life -= monDmg;
			hitCooldown = true;
			 
		}
	}
	public void damageMonster(int idx) {
		if(swinging == false && gp.monster[idx].life > 0) { // If not already swinging and monster is alive 
			gp.playSE(5);
			Entity mon = gp.monster[idx];
			double playerDmg = this.damage - 1.0*mon.defenseMult;
			mon.life -= playerDmg; //Monsters can have armor which decreases player damage
			mon.hpBarOn = true;
			mon.hpBarCounter = 0;
			mon.damageReaction(); 
			if(mon.life <= 0) {
				mon.dying = true; 
				respawnMonster(idx);
				this.xp+=mon.xp;
				gp.ui.addMessage(mon.xp + " XP Earned");
				gp.ui.addMessage("Foe Vanquished");
				levelUp();
				
			}else
				System.out.println(mon.life);
			swinging = true;

			}
		
	
	}
	public void levelUp() {
		if(this.xp >=this.nextLevelXp) {
			gp.ui.addMessage("Level Up!");
			this.level++; 
			nextLevelXp = nextLevelXp+5;
			maxLife+= 0.05;
			damage += 0.01;
			getAttack();
			gp.playSE(4);
		
		}
	}
	public void draw(Graphics2D g2) { //DRAWING PLAYER
		//g2.setColor(Color.white);
		//g2.fillRect(x,y,gp.tileSize,gp.tileSize);
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum==1) image = up1;
				if(spriteNum==2) image= up2;
			}
			else if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum==1) image = attackUp1;
				if(spriteNum==2) image= attackUp2;
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum==1) image = down1;
				if(spriteNum==2) image= down2;
			}
			else if(attacking == true) {
				if(spriteNum==1) image = attackDown1;
				if(spriteNum==2) image= attackDown2;
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum==1) image = left1;
				if(spriteNum==2) image= left2;
			}
			else if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum==1) image = attackLeft1;
				if(spriteNum==2) image= attackLeft2;
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum==1) image = right1;
				if(spriteNum==2) image= right2;
			}
			else if(attacking == true) {
				if(spriteNum==1) image = attackRight1;
				if(spriteNum==2) image= attackRight2;
			}
			break;
		case"upLeft": case"downLeft":
			if(attacking == false) {
				if(spriteNum==1) image = left1;
				if(spriteNum==2) image= left2;
			}
			else if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum==1) image = attackLeft1;
				if(spriteNum==2) image= attackLeft2;
			}
			break;
		case"downRight": case"upRight":
			if(attacking == false) {
				if(spriteNum==1) image = right1;
				if(spriteNum==2) image= right2;
			}
			else if(attacking == true) {
				if(spriteNum==1) image = attackRight1;
				if(spriteNum==2) image= attackRight2;
			}
			break;
		
		}
		
		//SET 70% TRANSPARENT IF PLAYER IS HIT
		if(hitCooldown == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			if(hitCooldownCounter % 30  == 0)
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		if(hitCooldown == false) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		g2.drawImage(image,tempScreenX,tempScreenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
