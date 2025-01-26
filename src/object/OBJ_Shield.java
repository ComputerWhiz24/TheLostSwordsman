package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		
		name = "Wood Shield";
		down1 = setup("/objects/shield_wood");
		defenseValue = 1;
		description = "[" + name + "]\nA basic, yet trusty\nshield.";
		type = type_shield;
	}

}
