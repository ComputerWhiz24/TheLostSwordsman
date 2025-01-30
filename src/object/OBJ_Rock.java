package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{
	GamePanel gp;
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Rock";
		speed = 8;
		maxLife = 180;
		life = maxLife;
		attack = 2;
		spellCost = 10;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectile/rock_down_1");
		up2 = setup("/projectile/rock_down_1");
		down1 = setup("/projectile/rock_down_1");
		down2 = setup("/projectile/rock_down_1");
		left1 = setup("/projectile/rock_down_1");
		left2 = setup("/projectile/rock_down_1");
		right1 = setup("/projectile/rock_down_1");
		right2  = setup("/projectile/rock_down_1");
	}
}
