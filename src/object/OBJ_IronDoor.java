package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_IronDoor extends Entity{

	GamePanel gp;
	public static final String objName = "Iron Door";
	
	public OBJ_IronDoor(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = objName;
		down1 = setup("/objects/door_iron");
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
		
			gp.ui.talkWorld = true;
			gp.ui.talkNPC = false;
		    gp.ui.currentDialogue = "It doesn't look like/n this one will budge ";
	}
}
