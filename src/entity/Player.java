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
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield;
import object.OBJ_Sword;
import tile_interactive.InteractiveTile;

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
		//ATTACK AREA
//		attackArea.width = 36;
//		attackArea.height = 20;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
 
		
//		worldX = gp.tileSize*24;
//		worldY = gp.tileSize*24;
		worldX = gp.tileSize*12;
		worldY = gp.tileSize*13;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		
	//PLAYER STATUS
		maxLife = 2;
		maxMana = 100;
		mana = maxMana;
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
		projectile = new OBJ_Fireball(gp);
		getAttack();
		getDefense();
		
	}
	public void setDefaultPosition() {
		worldX = gp.tileSize*24;
		worldY = gp.tileSize*24;
		direction = "down";
	}
	public void restoreHealthAndMana() {
		mana = maxMana;
		life = maxLife;
	}
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
	}
	public double getAttack() {

		attackArea = currentWeapon.attackArea;
		return attack = damage * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = vitality * currentShield.defenseValue;
	}
	
	public void getPlayerAttackImage() {
		if(currentWeapon.type == type_sword) {
			attackUp1 = setupAlternate("/player/boy_attack_up_1",1,2);
			attackUp2 = setupAlternate("/player/boy_attack_up_2",1,2);
			attackDown1 = setupAlternate("/player/boy_attack_down_1",1,2);
			attackDown2 = setupAlternate("/player/boy_attack_down_2",1,2);
			attackLeft1 = setupAlternate("/player/boy_attack_left_1",2,1);
			attackLeft2 = setupAlternate("/player/boy_attack_left_2",2,1);
			attackRight1 = setupAlternate("/player/boy_attack_right_1",2,1);
			attackRight2 = setupAlternate("/player/boy_attack_right_2",2,1);
		}else if(currentWeapon.type == type_axe) {
			attackUp1 = setupAlternate("/player/boy_axe_up_1",1,2);
			attackUp2 = setupAlternate("/player/boy_axe_up_2",1,2);
			attackDown1 = setupAlternate("/player/boy_axe_down_1",1,2);
			attackDown2 = setupAlternate("/player/boy_axe_down_2",1,2);
			attackLeft1 = setupAlternate("/player/boy_axe_left_1",2,1);
			attackLeft2 = setupAlternate("/player/boy_axe_left_2",2,1);
			attackRight1 = setupAlternate("/player/boy_axe_right_1",2,1);
			attackRight2 = setupAlternate("/player/boy_axe_right_2",2,1);
		}
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
			if(monsterIndex != 999) {
				hitByMonster(monsterIndex);
			}
			// CHECK INTERACTIVE TILE COLLISION
			
			gp.cChecker.checkEntity(this, gp.iTile);
			
			 // CHECK EVENT
			gp.eHandler.checkEvent();
			
			if(collisionOn == false && keyH.talkPressed==false) { // if not colliding and in another state
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
		if(gp.keyH.shootSpell && projectile.alive == false && projectileCooldown == 180 && projectile.hasMana(this)) { //If shoot spell is true, shoot fireball
			
			// SET COORDINATES, DIRECTION, AND USER
			
			projectile.set(worldX,worldY,direction,true,this);
			
			//SUBTRACT MAMA
			projectile.subtractMana(this);
			
			// ADD TO PROJECTILE LIST 
			for(int i = 0; i < gp.projectileList.length; i++) {
				if(gp.projectileList[gp.currentMap][i] == null) {
					gp.projectileList[gp.currentMap][i] = projectile;
					break;
				}
			}
			projectileCooldown = 30; 
			gp.playSE(9);
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
		if(projectileCooldown < 180) {
			projectileCooldown++;
		}
		
		if(hitCooldown == true) {
			hitCooldownCounter++;
			if(hitCooldownCounter >= 95) {
				hitCooldown = false;
				hitCooldownCounter = 0;
			}
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
			gp.playSE(11);
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
				damageMonster(monsterIndex,attack,currentWeapon.knockbackPower);
			}
			int iTileIdx = gp.cChecker.checkEntity(this,gp.iTile);
			damageTile(iTileIdx);
			
			int projectileIndex = gp.cChecker.checkEntity(this,gp.projectileList);
			damageProjectile(projectileIndex);
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
			//	COLLECTIBLE ITEMS
			if(gp.obj[gp.currentMap][i].type == type_collectible) {
				 gp.obj[gp.currentMap][i].use(this);
				 gp.obj[gp.currentMap][i] = null; 
			}
			else if(gp.obj[gp.currentMap][i].type == type_obstacle) { // OBSTACLE
				if(keyH.openPressed) {
					if(gp.obj[gp.currentMap][i] instanceof OBJ_Door) { // IF OBJECT IS A DOOR
						gp.obj[gp.currentMap][i].interact(i);
					} else { // IF OBJECT IS NOT A DOOR
						gp.obj[gp.currentMap][i].interact();
					}
					keyH.openPressed = false;
				}
			}
			else {
				// INVENTORY ITEMS
				String text;
				if(canObtainItem(gp.obj[gp.currentMap][i])) {
					obtainItem(gp.obj[gp.currentMap][i]);
					gp.playSE(1);
					
				}
				else {
					text = "Inventory full";
					gp.ui.addMessage(text);
				}
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	public void interactNPC(int i) {

		if(i != 999) { 
			if (keyH.talkPressed == true) { 
				gp.ui.talkNPC = true;
		        gp.gameState = gp.dialogueState;
		        currentSpeaker = gp.npc[gp.currentMap] [i];
		        gp.npc[gp.currentMap][i].speak(); // Initial dialogue trigger
			}
			keyH.talkPressed = false;
			}
	} 
	public void hitByMonster(int idx) {
		if(hitCooldown == false &&  gp.monster[gp.currentMap][idx].dying == false) {
			gp.playSE(6);
			double monDmg = gp.monster[gp.currentMap][idx].damage - 0.05*defense; //Defense shields damage from monster
			life -= monDmg;
			hitCooldown = true;
		}
	}
	public void damageMonster(int idx, double attack, int knockbackPower) {

		if(swinging == false && gp.monster[gp.currentMap][idx].life > 0) { // If not already swinging and monster is alive 
			gp.playSE(5);
			if(knockbackPower > 0) {
				knockback(gp.monster[gp.currentMap][idx], knockbackPower);
			}
			Entity mon = gp.monster[gp.currentMap][idx];
			double playerDmg = this.attack - 1.0*mon.defenseMult;
			mon.life -= playerDmg; //Monsters can have armor which decreases player damage
			mon.hpBarOn = true;
			mon.hpBarCounter = 0;
			mon.damageReaction(); 
			if(mon.life <= 0) {
				mon.dying = true; 
				mon.checkDrop();
				respawnMonster(idx);
				int gold = (int) ((Math.random() *5) + 1);
				this.xp+=mon.xp;
				this.coin += gold;
				gp.ui.addMessage(gold + " Gold Earned");
				gp.ui.addMessage(mon.xp + " XP Earned");
				gp.ui.addMessage("Foe Vanquished");
				levelUp();	
			}
			swinging = true;
			}
	}
	public void knockback(Entity entity, int knockbackPower) {
		
		entity.direction = this.direction;
		entity.speed += knockbackPower;
		entity.knockback = true;
	}
	public void damageProjectile(int idx){
		if(idx != 999) {
			Entity projectile = gp.projectileList[gp.currentMap][idx];
			projectile.alive = false;
			generateParticle(projectile,projectile);
		}
	}
	public void damageTile(int tileIdx) {
		// IF TILE IS DESTRUCTIBLE AND THE WEAPON TYPE IS COMPATIBLE, DECREASE LIFE BY 1
			if(tileIdx != 999 && gp.iTile[gp.currentMap][tileIdx].destructible && gp.iTile[gp.currentMap][tileIdx].isCorrectItem(this)) {
				if(!swinging) {
					System.out.println(gp.iTile[gp.currentMap][tileIdx].life);
					gp.iTile[gp.currentMap][tileIdx].life--;
					generateParticle(gp.iTile[gp.currentMap][tileIdx], gp.iTile[gp.currentMap][tileIdx]);
					gp.playSE(10);
					damageTileHelper(tileIdx);
				}
				// CHANGE IMAGE BASED ON TILE'S CURRENT LIFE
			swinging = true;
			}
	}
	public void damageTileHelper(int tileIdx) {
		if(gp.iTile[gp.currentMap][tileIdx].life == 2) {
			gp.iTile[gp.currentMap][tileIdx].down1 = gp.iTile[gp.currentMap][tileIdx].image;
		}
		else if(gp.iTile[gp.currentMap][tileIdx].life == 1) {
			gp.iTile[gp.currentMap][tileIdx].down1 = gp.iTile[gp.currentMap][tileIdx].image2;
		}else if(gp.iTile[gp.currentMap][tileIdx].life == 0) {
			gp.iTile[gp.currentMap][tileIdx]= null;
		}
	}
	public void shootMonster(int idx, double attack, int knockbackPower) {
		
		if(gp.monster[gp.currentMap][idx].life > 0) { // If not already swinging and monster is alive 
			gp.playSE(5);
			if(knockbackPower > 0) {
			knockback(gp.monster[gp.currentMap][idx], knockbackPower);
			}
			Entity mon = gp.monster[gp.currentMap][idx];
			double playerDmg = attack - 1.0*mon.defenseMult;
			mon.life -= playerDmg; //Monsters can have armor which decreases player damage
			mon.hpBarOn = true;
			mon.hpBarCounter = 0;
			mon.damageReaction(); 
			if(mon.life <= 0) {
				mon.dying = true; 
				mon.checkDrop();
				respawnMonster(idx);
				int gold = (int) ((Math.random() *5) + 1);
				this.coin += gold;
				gp.ui.addMessage(gold + " Gold Earned");
				this.xp+=mon.xp;
				gp.ui.addMessage(mon.xp + " XP Earned");
				gp.ui.addMessage("Foe Vanquished");
				levelUp();
			}else {
				System.out.println(mon.life);
			}
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
	public void selectItem() {

		int idx = gp.ui.getItemIndex();
		if(idx < inventory.size()) {
			Entity selectedItem = inventory.get(idx);
			switch(selectedItem.type) {
			case type_sword:
			case type_axe:
				if(currentWeapon == selectedItem) {
					currentWeapon = null;
				} else {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
				}
				break;
			case type_shield:
				if(currentShield == selectedItem) {
					currentShield = null;
				} else {
				currentShield = selectedItem;
				defense = getDefense();
				}
				break;
			case type_consumable:
				if(selectedItem.amount > 1) {
					selectedItem.amount--;
					System.out.println("amount -");
				}
				else {
					selectedItem.use(this);
					inventory.remove(idx);
					System.out.println("item used");
				}
				break;
			}
		}
		
	}
	public int searchItemInInventory(String itenName) {
		
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).name.equalsIgnoreCase(itenName)) {
				return i;
			}
		}
		return 999; 
	}
	public boolean canObtainItem(Entity item) {
		boolean canObtain = false;
		if(item.stackable) { // CHECK IF ITEM IS STACKABLE
			
			int index = searchItemInInventory(item.name);
			
			if(index != 999) { // IF ALREADY HAVE ITEM IN INVENTORY
				canObtain = true;
			}
			else { // IF NOT ALREADY IN IVENTORY
				if(inventory.size() != inventorySize) { // CHECK IF INVENTORY IS FULL
				canObtain = true;
				}
			}
		}
		else { // NOT STACKABLE, SO CHECK INVENTORY
			if(inventory.size() != inventorySize) { // CHECK IF INVENTORY IS FULL
				canObtain = true;
			}
		}
		return canObtain;
	}
	public void obtainItem(Entity item) {
		int index = searchItemInInventory(item.name);
		if(item.stackable) { // CHECK IF ITEM IS STACKABLE		
			inventory.get(index).amount++;
		}
		else { // NOT STACKABLE, SO ADD TO A SLOT
			inventory.add(item);
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
}
