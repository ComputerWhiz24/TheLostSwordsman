package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueShield extends Entity{
	
	public OBJ_BlueShield(GamePanel gp) {
		super(gp);
		
		name = "Blue Shield";
		down1 = setup("/objects/shield_blue");
		defenseValue = 2;
		description = "[" + name + "]\nA heavy, blue shield.";
		type = type_shield;
		buyPrice = 100;
		sellPrice = 50;
		hand = 1;
		knockbackPower = 10;
	}
}
