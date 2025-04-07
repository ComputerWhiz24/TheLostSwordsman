package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

	GamePanel gp;
	
	public OBJ_Chest (GamePanel gp) {
		super(gp);
		this.gp = gp;
		
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
	public void setLoot(Entity loot) {
		this.loot = loot;
	}
	public void interact() {
		
		gp.gameState = gp.dialogueState;

		if(!opened) {
			gp.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			sb.append( "You found a chest and found a " + loot.name + "!");
			
			if(!gp.player.canObtainItem(loot)) {
				sb.append("\n... But you can't carry any more.");
			}
			else { 
				gp.player.obtainItem(loot);;
				down1 = image2;
				opened = true;
			}
			gp.ui.talkWorld = true;
			gp.ui.talkNPC = false;
			gp.ui.currentDialogue = sb.toString();
		}
		else {
			gp.ui.talkWorld = true;
			gp.ui.talkNPC = false;
			gp.ui.currentDialogue = "Chest already opened!";
		}
	}
	
}

