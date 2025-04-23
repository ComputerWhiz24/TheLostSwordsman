package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{
	
	GamePanel gp;
	public static final String objName = "Fireball";
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		speed = 5;
		maxLife = 180;
		life = maxLife;
		attack = 1;
		spellCost = 10;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectile/fireball_up_1");
		up2 = setup("/projectile/fireball_up_2");
		down1 = setup("/projectile/fireball_down_1");
		down2 = setup("/projectile/fireball_down_2");
		left1 = setup("/projectile/fireball_left_1");
		left2 = setup("/projectile/fireball_left_2");
		right1 = setup("/projectile/fireball_right_1");
		right2  = setup("/projectile/fireball_right_2");
	}
	public boolean hasMana(Entity user) {
		 
		boolean haveMana;
		if(user.mana>=spellCost) {
			haveMana = true;
		}
		else {
			haveMana = false;
		}
		return haveMana;
	}
	public void subtractMana(Entity user) {
		user.mana -= spellCost;
	}
	public Color getParticleColor() {
		Color color = new Color(240,50,0);
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
