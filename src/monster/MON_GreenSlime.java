package monster;

import java.util.Random;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity{

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		name = "Green Slime";
		type = type_monster;
		xp = 1;
		damage = 1;
		defaultSpeed = 1;
		speed = defaultSpeed;
		projectile = new OBJ_Rock(gp); 
		
		maxLife = 1;
		life = maxLife;
		attack = 4;
		defenseMult = 0;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		
	}
	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1");
		up2 = setup("/monster/greenslime_down_2");
		left1 = setup("/monster/greenslime_down_1");
		left2 = setup("/monster/greenslime_down_2");
		right1 = setup("/monster/greenslime_down_1");
		right2 = setup("/monster/greenslime_down_2");
		down1 = setup("/monster/greenslime_down_1");
		down2 = setup("/monster/greenslime_down_2");
		
	}
	public void setAction() {
		
		actionLockCounter++;
				
		if(actionLockCounter == 80) {
				
			Random random = new Random();
			int i = random.nextInt(100)+1; //Random number from 1 to 100
			
			if(i<=25)
				direction = "up";
			if(i>25 && i <= 50)
				direction = "down";
			if(i >50 && i <=75)
				direction = "left";
			if(i>75 && i <= 100)
				direction = "right";
		
			actionLockCounter = 0;
			
		
		}
		int i = new Random().nextInt(100)+1;
		if(i >99 && projectile.alive == false && projectileCooldown > 30) {
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
	public void damageReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
	public void checkDrop() {
		// RANDOM CHANCE
		int i = new Random().nextInt(100) + 1;
		
		// SET SLIME DROP
		if(i <= 100) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
	}

}
