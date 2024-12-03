package entity;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_OldMan extends Entity{

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		getImage();
		setDialogue();
	}
		
	public void getImage() {
			
			up1 = setup("/npc/oldman_up_1");
			up2 = setup("/npc/oldman_up_2");
			down1 = setup("/npc/oldman_right_1");
			down2 = setup("/npc/oldman_right_2");
			left1 = setup("/npc/oldman_left_1");
			left2 = setup("/npc/oldman_left_2");
			right1 = setup("/npc/oldman_right_1");
			right2  = setup("/npc/oldman_right_2");

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
	
	public void setDialogue() {
		dialogues[0] = "Hello there, young sir";
		dialogues[1] = "Looking for the island treasures?";
		dialogues[2] = "I used to be a great wizard but now.... I'm a little too\nold for adventures";
		dialogues[3] = "Well, good luck";
	}
	public void speak() {
		super.speak();
	}
}
