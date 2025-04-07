package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{

	GamePanel gp;
	
	public OBJ_Key (GamePanel gp ) {
		super(gp);
		this.gp = gp;
		collision = false;
		name = "key";
		stackable = true;
		down1 = setup("/objects/key");
		description = "[" + name + "]\nA weird, random key..\nI wonder what\nit's used for.";
		}
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		if(gp.player.inventory.contains(new OBJ_Key(gp))) {
			gp.ui.currentDialogue = "You use a key to open the door";
			gp.playSE(3);
			gp.obj[gp.currentMap][gp.player.inventory.indexOf(new OBJ_Key(gp))] = null;
		} else {
			gp.ui.currentDialogue = "You need a key to open this";
		}
	}
}


