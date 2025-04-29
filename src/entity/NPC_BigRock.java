package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_BigRock extends Entity{
	public static final String npcName = "Big Rock";
	public String[][] rockDialogue = new String[1][1];
	public NPC_BigRock(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 4;
		getImage();
		setDialogue();
		name = npcName;
		
		solidArea = new Rectangle();
		solidArea.x = 2;
		solidArea.y = 6;
		solidArea.width = 44;
		solidArea.height = 40;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
		
	public void getImage() {
			
			up1 = setup("/npc/bigrock");
			up2 = setup("/npc/bigrock");
			down1 = setup("/npc/bigrock");
			down2 = setup("/npc/bigrock");
			left1 = setup("/npc/bigrock");
			left2 = setup("/npc/bigrock");
			right1 = setup("/npc/bigrock");
			right2  = setup("/npc/bigrock");

	}
	
	public void setAction() {}
	public void setDialogue() {
		rockDialogue[0][0] = "Big rock... I wonder if it moves";
	}
	public void speak() {

		dialogueIndex = 0;
		currentDialogue = rockDialogue;	
		super.speak();
	}
//	public void setDialogue() {
//		
//		facePlayer();
//		startDialogue(this, dialogueSet);
//	}
}
