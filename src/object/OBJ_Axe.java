package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public static final String objName = "Woodcutters Axe";
	public OBJ_Axe(GamePanel gp) {
		super(gp);
		name = objName;
		down1 = setup("/objects/axe");
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 20;
		description = "[Woodercutter's Axe]\nA fine beginner axe";
		type = type_axe;
		buyPrice = 50;
		sellPrice = 25;
		hand = 1;
		knockbackPower = 7;
		motion1_duration = 10;
		motion2_duration = 35;
	}

	
}
