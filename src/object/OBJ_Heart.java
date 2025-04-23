package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

	GamePanel gp; 
	public static final String objName = "heart";
	
	public OBJ_Heart (GamePanel gp) {
		super(gp);
		
		name = objName;
		image = setup("/objects/heart_full");
		image2 = setup("/objects/heart_half");
		image3 = setup("/objects/heart_blank");

	}
	
}
