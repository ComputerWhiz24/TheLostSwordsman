package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
	public OBJ_Chest (GamePanel gp, Entity loot) {
		super(gp);
		this.gp = gp;
		this.loot = loot;
		
		type = type_obstacle;
		collision = true;
		solidArea.x = 4;
		solidArea.x = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		name = "chest";
		image =  setup("/objects/chest");
		image2 = setup("/objects/chest_opened");
		
		down1 = image;
		down2 = image;
		
	}
	public void interact() {
		
		gp.gameState = gp.dialogueState;

		if(!opened) {
			gp.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			sb.append( "You found a chest and found a " + loot.name + "!");
			
			if(gp.player.inventory.size() == gp.player.inventorySize) {
				sb.append("\n... But you can't carry any more.");
			}
			else {
				gp.player.inventory.add(loot);
				down1 = image2;
				opened = true;
			}
			gp.ui.currentDialogue = sb.toString();
		}
		else {
			gp.ui.currentDialogue = "Chest already opened!";
		}
	}
	
}

