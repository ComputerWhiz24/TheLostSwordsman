package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Entity {

	public static GamePanel gp;
	
	public BufferedImage image,image2,image3;
	public BufferedImage up1,up2,down1, down2,left1,left2,right1,right2;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2, guardUp, guardDown,
	guardLeft, guardRight;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;  
	
	// NPC
	public static Entity currentSpeaker = new Entity(gp);
	public static String[] currentDialogue = new String[20];
	public boolean tradable =false;
	public Entity attacker;
	
		//STATE
	public int worldX,worldY;
	public String direction = "down";
	public int spriteNum = 1;
	public static int dialogueIndex = 0;
	public boolean collisionOn = false;
	protected boolean attacking = false; 
	public boolean alive = true; 
 	public boolean dying = false;
	public boolean hitCooldown = false;
	public boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockback = false;
	public String knockbackDirection; 
	public boolean guarding = false;
	public boolean transparent = false; 
	public boolean offBalance;
		//COUNTERS 
	public int actionLockCounter = 0;
	public int hitCooldownCounter = 0;
	public int spriteCounter = 0;
	int dyingCounter = 0; 
	public int hpBarCounter = 0;  
	public int projectileCooldown;
	int knockbackCounter;
	public int guardCounter;
	public int offBalanceCounter;
		// INVENTORY
	public ArrayList<Entity> inventory = new ArrayList<>();
	public int inventorySize = 222;
	public int cols = 5;
	public int rows = 5;
		//ATTRIBUTES
	public int defaultSpeed; 
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
	public Entity currentLight;
	public int defaultX;
	public int defaultY;
	public int motion1_duration;
	public int motion2_duration;
	
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
	public int knockbackPower = 0;
	public boolean stackable = false;
	public int amount = 1;
	public int lightRadius;
	
	// ITEM TYPE
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_collectible = 7;
	public final int type_obstacle = 8;
	public final int type_light = 9;

	//TESTING NEW MOVEMENT
	public boolean collisionOnUp = false;
	public boolean collisionOnLeft = false;
	public boolean collisionOnRight = false;
	public boolean collisionOnDown = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public int getLeftX() {
		return worldX + solidArea.x;
	}
	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}
	public int getTopY() {
		return worldY + solidArea.y;
	}
	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}
	public int getCol() {
		return (worldX + solidArea.x) / gp.tileSize;
	}
	public int getRow() {
		return (worldY + solidArea.y) / gp.tileSize;
	}
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(worldX - target.worldX);
		return xDistance;
	}
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(worldY - target.worldY);
		return yDistance;
	}
	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) +  getYDistance(target)) / gp.tileSize;
		return tileDistance;
	}
	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
		return goalRow;
	}
	public void setAction() {}
	public void damageReaction() {}
	public void speak() {} // Will override in NPC subclasses
	public void interact(int i) {
		
	}
	public void interact() {
		
	}
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

		if(knockback) {
			checkCollision();
			
			if(collisionOn) { // IF ENTITY HITS SOMETHING, STOP KNOCKBACK EFFECT
				knockbackCounter = 0;
				knockback = false;
				speed = defaultSpeed;
			} else {
				switch(knockbackDirection) {
				case "up":
					worldY -= speed; break;
				case"down": 
					worldY += speed; break;
				case"left":
					worldX -= speed; break;
				case"right":
					worldX += speed; break;	
				case"upLeft":
					worldX -= speed; break;
				case"upRight":
					worldX += speed; break;
				case"downLeft":
					worldX -= speed; break;
				case"downRight":
					worldX += speed; break;	
				}
				knockbackCounter++;
				if(knockbackCounter == 10) { // BIG NUMBER IS MORE KNOCKBACK 
					knockbackCounter = 0;
					knockback = false;
					speed = defaultSpeed;
				}
			}
		}
		else if(attacking) {
			attack();
		}
		else {
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
				if(spriteNum==1) {
					spriteNum=2;
				}
				else if(spriteNum==2) {
					spriteNum=1;	
				}
				spriteCounter=0;	
			}
		}

		setAction();
		checkCollision();

		if(projectileCooldown < 180) {
			projectileCooldown++;
		}
	}
	public void checkStartChasing(Entity target, int distance, int odds) {
		
		if(getTileDistance(target) < distance) {
			int i = new Random().nextInt(odds);
			if(i == 0) {
				onPath = true;
			}
		}
	}
	public void checkStopChasing(Entity target, int distance, int odds) {
		
		if(getTileDistance(target) > distance) {
			int i = new Random().nextInt(odds);
			if(i == 0) {
				onPath = false;
			}
		}
	}
	public void getRandomDirection() {
		
		actionLockCounter++;	
		if(actionLockCounter == 80) {
				
			Random random = new Random();
			int i = random.nextInt(100)+1; //Random number from 1 to 100
			
			if(i<=25) direction = "up";
			if(i>25 && i <= 50) direction = "down";
			if(i >50 && i <=75) direction = "left";
			if(i>75 && i <= 100) direction = "right";
		
			actionLockCounter = 0;	
		}
	}
	public void checkAttack(int odds, int straight, int horizontal) {
		
		boolean targetInRange = false;
		int xDis = getXDistance(gp.player);
		int yDis = getYDistance(gp.player);
		
		switch(direction) {
		case "up": 
			if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			} break;
		case "down":
			if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			} break;
		case "left": 
			if(gp.player.worldX < worldX && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			} break;
		case "right":
			if(gp.player.worldX > worldX && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			} break;
		}
		
		if(targetInRange) {
			
			int i = new Random().nextInt(odds);
			if(i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				hitCooldownCounter = 0;
			}
		}
	}
	public void shootOrNot(int odds, int shotInterval) {
		
		int i = new Random().nextInt(odds);
		if(i == 0 && projectile.alive == false && projectileCooldown >= shotInterval) {
			projectile.set(worldX,worldY,direction,true,this);
			// ADD PROJECTILE TO END OF ARRAY
			for(int idx = 0; idx < gp.projectileList.length; idx++) {
				if(gp.projectileList[gp.currentMap][idx] == null) {
					gp.projectileList[gp.currentMap][idx] = projectile;
					break; 
				}
			}
			projectileCooldown = 0;
		}
	}
	public String getOppositeDirection(String direction) {
		String oppositeDirection = "";
		
		switch(direction) {
		
		case "up":
			oppositeDirection = "down";break;
		case "down": 
			oppositeDirection = "up"; break;
		case "left": 
			oppositeDirection = "right"; break;
		case "right": 
			oppositeDirection = "left"; break;
		}
		return oppositeDirection;
	}
	public void attack() {

		attacking = true;
		spriteCounter++;
		if(spriteCounter == 1)
			gp.playSE(7);
		if(spriteCounter <= motion1_duration) //SHORT BUFFER
			spriteNum = 1;
		if(spriteCounter  > motion1_duration && spriteCounter <= motion2_duration) { // ATTACK ANIMATION
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
			
			if(type == type_monster) { //MONSTER
				if(gp.cChecker.checkPlayer(this)) {
					damagePlayer(this);
				}
			}
			else { //PLAYER
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				if(monsterIndex != 999) {
					gp.player.damageMonster(monsterIndex,this, attack,currentWeapon.knockbackPower);
				}
				int iTileIdx = gp.cChecker.checkEntity(this,gp.iTile);
				gp.player.damageTile(iTileIdx);
				
				int projectileIndex = gp.cChecker.checkEntity(this,gp.projectileList);
				gp.player.damageProjectile(projectileIndex);
			}
			worldX = currentWorldX;
			worldY =  currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > motion2_duration) { //FINISH ANIMATION 
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
			if(type != type_monster) {
				gp.player.keyH.attackPressed = false;
				gp.player.swinging = false; 
			}	
		}
	} 
	public void damagePlayer(Entity mon) {
		
		if(gp.player.hitCooldown == false) {
		double monDmg = mon.damage - 0.05*gp.player.defense; //Defense shields damage from monster with some multiplier
		
		 String canGuardDirection = getOppositeDirection(direction);
		 if(gp.player.guarding && gp.player.direction.equals(canGuardDirection)) {
			 monDmg /= 4;
			 gp.playSE(13);
			knockback(gp.player,this,this.knockbackPower/2);
		 }
		 else {
			gp.playSE(6);
			gp.player.transparent = true;
			knockback(gp.player,this,this.knockbackPower);
		 }
		
			gp.player.life -= monDmg;
			gp.player.hitCooldown = true;
		}
	}
	public void knockback(Entity target, Entity attacker, int knockbackPower) {
		
		this.attacker = attacker;
		target.knockbackDirection = attacker.direction;
		target.speed += knockbackPower;
		target.knockback = true;
	}
	public void shootPlayer(double attack) {
		gp.playSE(6);
		double monDmg = attack;
		gp.player.life -= monDmg;
		gp.player.hitCooldown = true;
		if(gp.player.guarding && gp.player.direction.equals(getOppositeDirection(direction))) {
			gp.player.transparent = false;
		} else {
			gp.player.transparent = true;
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX  + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY  + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
		
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
				System.out.println("Collision Detected: " + collisionOn);
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
			 } else {
					System.out.println("Collision Detected: " + collisionOn);
				 System.out.println("BUG");
				 
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
