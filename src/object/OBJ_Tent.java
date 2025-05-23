package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity{
	GamePanel gp;
	public static final String objName = "Tent";
	
	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		type = type_consumable;
		down1 = setup("/objects/tent");
		description = "A warm and cozy \nplace to sleep";
		buyPrice = 300;
		sellPrice = 150;
		stackable = false;
	}
	public void use(Entity entity) {
		
		gp.gameState = gp.sleepState;
		gp.player.mana = gp.player.maxMana; 
		gp.player.getSleepingImage(down1);
		gp.playSE(12);
		gp.saveLoad.save();
	}

}
