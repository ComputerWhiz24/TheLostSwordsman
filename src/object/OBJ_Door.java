package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

	GamePanel gp;
	public static final String objName = "door";
	
	public OBJ_Door (GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		down1 = setup("/objects/door");
		collision = true;
		type = type_obstacle;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	public void interact(int doorIdx) {
		gp.gameState = gp.dialogueState;
		boolean hasKey = false;
		int keyIndex = 0;
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			 if (gp.player.inventory.get(i) instanceof OBJ_Key) {
			        hasKey = true;
			        keyIndex = i;
			        break; // Stop once we find a key
			    }
		}	
		if (hasKey) {
			gp.ui.talkWorld = true;
			gp.ui.talkNPC = false;
		    gp.ui.currentDialogue = "You use a key to open the door";
		    gp.obj[gp.currentMap][doorIdx] = null;
//		    // Find and remove the first key
		    if(gp.player.inventory.get(keyIndex).amount > 1) {
	        	gp.player.inventory.get(keyIndex).amount--;
	        }
		    else {
		    	gp.player.inventory.remove(keyIndex); 
		    }
		}
		else {
			gp.ui.currentDialogue = "you need a key to open this";
		}
	
	}
}

