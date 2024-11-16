package entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_Wizard extends Entity{

	public NPC_Wizard(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		getImage();
	}
		
	public void getImage() {
			try {
			up1 = setup("/npc/wizard_up1");
			up2 = setup("/npc/wizard_up2");
			down1 = setup("/npc/wizard_down1");
			down2 = setup("/npc/wizard_down2");
			left1 = setup("/npc/wizard_left1");
			left2 = setup("/npc/wizard_left2");
			right1 = setup("/npc/wizard_right1");
			right2  = setup("/npc/wizard_right2");
			}catch(Exception e) {
				System.out.println("Lil bro u messed up");
		
			}
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 80) {
			
			Random random = new Random();
			int i = random.nextInt(100)+1; //Random number from 1 to 100
			
			if(i<=25)
				direction = "up";
			if(i>25 && i <= 50)
				direction = "down";
			if(i >50 && i <=75)
				direction = "left";
			if(i>75 && i <= 100)
				direction = "right";
		
			actionLockCounter = 0;
		}
	}

	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image =  uTool.scaledImage(image, gp.tileSize, gp.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	
}
	
}
