package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{

	public static final String objName = "boots";
	GamePanel gp;
	
	public OBJ_Boots (GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/objects/boots.png");
	}
}