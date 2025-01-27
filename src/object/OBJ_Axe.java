package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		name = "Woodcutters Axe";
		down1 = setup("/objects/axe");
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 20;
		description = "[Woodercutter's Axe]\nA fine beginner axe";
		type = type_axe;
	}

	
}
