package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RedPotion extends Entity{
	GamePanel gp;
	int value = 5;
	public OBJ_RedPotion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Red Potion";
		type = type_consumable;
		down1 = setup("/objects/potion_red");
		description = "[Red Potion]\nA yummy elixir";
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
