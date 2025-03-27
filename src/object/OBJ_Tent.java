package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity{
	GamePanel gp;
	
	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Tent";
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
		gp.playSE(12);
	}

}
