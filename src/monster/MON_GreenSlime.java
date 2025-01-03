package monster;

import java.util.Random;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class MON_GreenSlime extends Entity{

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		name = "Green Slime";
		type = 2;
		xp = 1;
		damage = 1;
		speed = 1;
		 
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
	}
	public void damageReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
	 
	
		
	}

}
