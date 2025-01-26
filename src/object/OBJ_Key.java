package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{


	
	public OBJ_Key (GamePanel gp ) {
		super(gp);
		collision = false;
		name = "key";
		down1 = setup("/objects/key");
		description = "[" + name + "]\nA weird, random key..\nI wonder what\nit's used for.";
		}
	}

