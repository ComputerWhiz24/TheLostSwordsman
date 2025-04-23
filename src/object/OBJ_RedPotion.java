package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RedPotion extends Entity{
	GamePanel gp;
	public static final String objName = "Red Potion";
			
	public OBJ_RedPotion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		value = 5; 
		name = objName;
		type = type_consumable;
		down1 = setup("/objects/potion_red");
		description = "[Red Potion]\nA yummy elixir";
		buyPrice = 20;
		sellPrice = 0;
	}
	public void use(Entity entity) {
		if(gp.player.life != gp.player.maxLife) {
			entity.life += value;
			gp.playSE(2);
			if(gp.player.life > gp.player.maxLife) {
				gp.player.life = gp.player.maxLife;
			}
		}
	}
}
