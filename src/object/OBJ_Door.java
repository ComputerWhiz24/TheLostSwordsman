package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

	GamePanel gp;
	
	public OBJ_Door (GamePanel gp) {
		super(gp);
		
		name = "door";
		down1 = setup("/objects/door");
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
		
}

