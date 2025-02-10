package object;

import java.awt.Color;

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
		attack = 1;
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
	public Color getParticleColor() {
		Color color = new Color(165,42,42);
		return color;
	}
	public int getParticleSize() {
		int size = 10; // 6 PIXELS
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
