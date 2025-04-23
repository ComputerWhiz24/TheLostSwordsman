package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{

	GamePanel gp;
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		value = 1;
		type = type_collectible;
		down1 = setup("/objects/coin_bronze");
	}

	public void use(Entity entity) {
		gp.playSE(1);
		gp.ui.addMessage("+" + value +" GOLD");
		gp.player.coin += value;
	}
}
