package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity{

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		 
		name = "Normal Sword";
		down1 = setup("/objects/sword_normal");
		attackValue = 1;
		description = "[" + name + "]\nA basic, yet trusty\nsword.";
		attackArea.width = 36;
		attackArea.height = 25;
		type = type_sword;
		buyPrice = 5;
		sellPrice = 1;
		hand = 1;
		knockbackPower = 5;
		motion1_duration = 5;
		motion2_duration = 25;
	}
}
