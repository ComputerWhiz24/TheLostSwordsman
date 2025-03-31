package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Rock;

public class MON_Orc extends Entity{

	GamePanel gp;
	public MON_Orc(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Orc";
		type = type_monster;
		xp = 4;
		damage = 1.5;
		defaultSpeed = 0.5;
		speed = defaultSpeed;
		
		maxLife = 10;
		life = maxLife;
		attack = 4;
		defenseMult = 0;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = gp.tileSize;
		attackArea.height = 48;
		
		defaultX = 12;
		defaultY = 33;
		getImage();
		getAttackImage();
	}
	public void getImage() {
		
		up1 = setup("/monster/orc_down_1");
		up2 = setup("/monster/orc_down_2");
		left1 = setup("/monster/orc_down_1");
		left2 = setup("/monster/orc_down_2");
		right1 = setup("/monster/orc_down_1");
		right2 = setup("/monster/orc_down_2");
		down1 = setup("/monster/orc_down_1");
		down2 = setup("/monster/orc_down_2");
		
	}
	public void getAttackImage() {
		
		attackUp1 = setupAlternate("/monster/orc_attack_up_1",1,2);
		attackUp2 = setupAlternate("/monster/orc_attack_up_2",1,2);
		attackDown1 = setupAlternate("/monster/orc_attack_down_1",1,2);
		attackDown2 = setupAlternate("/monster/orc_attack_down_2",1,2);
		attackLeft1 = setupAlternate("/monster/orc_attack_left_1",2,1);
		attackLeft2 = setupAlternate("/monster/orc_attack_left_2",2,1);
		attackRight1 = setupAlternate("/monster/orc_attack_right_1",2,1);
		attackRight2 = setupAlternate("/monster/orc_attack_right_2",2,1);
	}
	public void setAction() {
	
		if(onPath) {
			
			// CHECK IF IT STOPS CHASING
			checkStopChasing(gp.player,15,100);
			
			// MOVE
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			
		} else {
			
			checkStartChasing(gp.player,5,100);
			
			getRandomDirection();
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
