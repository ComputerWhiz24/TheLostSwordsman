package entity;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_Wizard extends Entity{

	public static String[][] wizardDialogue = new String[4][4];
	public NPC_Wizard(GamePanel gp) {
		super(gp);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 30;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		direction = "down";
		speed = 1;
		getImage();
		setDialogue();
	}
		
	public void getImage() {
			try {
			up1 = setupAlternate("/npc/wizard_up1",1,1);
			up2 = setupAlternate("/npc/wizard_up2",1,1);
			down1 = setupAlternate("/npc/wizard_down1",1,1);
			down2 = setupAlternate("/npc/wizard_down2",1,1);
			left1 = setupAlternate("/npc/wizard_left1",1,1);
			left2 = setupAlternate("/npc/wizard_left2",1,1);
			right1 = setupAlternate("/npc/wizard_right1",1,1);
			right2  = setupAlternate("/npc/wizard_right2",1,1);
			}catch(Exception e) {
				e.printStackTrace();
		
			}
	}
	
	public void setAction() {
		
			
			if(onPath) {
				
				int goalCol = 12;
				int goalRow = 9;
				
				searchPath(goalCol, goalRow);

				
			} else {
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
	public void setDialogue() {
		wizardDialogue[0][0] = "Hello there, young sir";
		wizardDialogue[0][1] = "Looking for the island treasures?";
		wizardDialogue[0][2] = "I used to be a great wizard but now.... I'm a little too\nold for adventures";
		wizardDialogue[0][3] = "Well, good luck";
	}
	public void speak() {
		
		dialogueIndex = 0;
		currentDialogue = wizardDialogue;
		onPath = true;
		super.speak();
	}
}
