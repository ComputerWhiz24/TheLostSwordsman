package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity{
	public final static String objName = "Pickaxe";
	public OBJ_Pickaxe(GamePanel gp) {
		
		super(gp);
		name = objName;
		
		down1 = setup("/objects/pickaxe");
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 20;
		description = "[Beginner Pickaxe]\nA fine beginner pickaxe";
		type = type_pickaxe;
		buyPrice = 100;
		sellPrice = 50;
		hand = 1;
		knockbackPower = 7;
		motion1_duration = 10;
		motion2_duration = 35;
	}

}
