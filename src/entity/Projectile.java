package entity;

import main.GamePanel;

public class Projectile extends Entity{
	
	Entity user;
	public Projectile(GamePanel gp) {
		super(gp);
		
	}
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
	}
	public void update() {
		if(user == gp.player) {
			int monsterIdx = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIdx != 999) {
				generateParticle(user.projectile,gp.monster[monsterIdx]);
				gp.player.shootMonster(monsterIdx,attack);
				alive = false;
			}
		} else {
			boolean hitPlayer = gp.cChecker.checkPlayer(this);
			if(gp.player.hitCooldown == false && hitPlayer == true) {
				shootPlayer(this.attack);
				generateParticle(user.projectile,gp.player);
				alive = false; 
			}
		}
		
		switch(direction) {
		case"up":
			worldY-= speed;
			break;
		case "down":
			worldY+= speed;
			break;
		case"left":
			worldX-= speed;
			break;
		case"right":
			worldX+=speed;
			break;
		case"upLeft":
			worldX-=speed; worldY-=speed;
			break;
		case"upRight":
			worldX+=speed; worldY-=speed;
			break;
		case"downLeft":
			worldX-=speed; worldY+=speed;
			break;
		case"downRight":
			worldX+=speed; worldY+=speed;
			break;
		}
		
		life--;
		if(life<= 0) {
			alive = false;
		}
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1)
				spriteNum = 2;
			else if(spriteNum == 2)
				spriteNum = 1;
		}
		spriteCounter = 0;
	}
	public boolean hasMana(Entity user) {
		// WILL BE OVERRIDEN BY CHILDREN
		boolean haveMana = false;
		return haveMana;
	}
	public void subtractMana(Entity user) {}
}
