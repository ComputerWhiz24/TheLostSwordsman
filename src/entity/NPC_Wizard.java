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
			up1 = setupAlternate("/npc/wizard_up1",4,4);
			up2 = setupAlternate("/npc/wizard_up2",4,4);
			down1 = setupAlternate("/npc/wizard_down1",4,4);
			down2 = setupAlternate("/npc/wizard_down2",4,4);
			left1 = setupAlternate("/npc/wizard_left1",4,4);
			left2 = setupAlternate("/npc/wizard_left2",4,4);
			right1 = setupAlternate("/npc/wizard_right1",4,4);
			right2  = setupAlternate("/npc/wizard_right2",4,4);
			}catch(Exception e) {
				e.printStackTrace();
		
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

	
}
