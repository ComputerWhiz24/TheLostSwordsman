package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import object.OBJ_IronDoor;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

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
	public void push(String d) {
		
		this.direction = d;
		
		checkCollision();
		if(!collisionOn) {
			switch(direction) {
			case "up":
				worldY -= speed; break;
			case"down": 
				worldY += speed; break;
			case"left":
				worldX -= speed; break;
			case"right":
				worldX += speed; break;
			case"upLeft":
				worldY -= speed;
				worldX -= speed; 
				break;
			case"upRight":
				worldY -= speed;
				worldX += speed; 
				break;
			case"downLeft":
				worldY += speed;
				worldX -= speed; 
				break;
			case"downRight":
				worldY += speed;
				worldX += speed; 
				break;	
			}
		}
		detectPlate();
	}
	public void update() {} // NEED TO OVERRIDE SINCE SUPER CLASS MOVES OBJECT BASED ON DIRECTION
	public void detectPlate() {
		
		ArrayList<InteractiveTile> plateList = new ArrayList<>();
		ArrayList<Entity> rockList = new ArrayList<>();
		
		// PLATE LIST
		for(int i = 0; i < gp.iTile[1].length; i++) {
			
			if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null && gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName) ) {
				plateList.add(gp.iTile[gp.currentMap][i]);
			}
		}
		
		// ROCK LIST
		for(int i = 0; i < gp.npc[1].length; i++) {
			
			if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
				rockList.add(gp.npc[gp.currentMap][i]);
			}
		}
		
		int rocks = 0;
		// SCAN PLATE LIST
		for(int i = 0; i < plateList.size(); i++) {
			
			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);
			
			if(distance < 8) { // IF LESS THAN 8 PIXELS OFF PLATE, LINKED THEM
				if(linkedEntity == null) { // CHECK IF IT IS NOT ALREADY LINKED BECAUSE WE DON'T WANT SOUND EFFECT TO PLAY OVER AND OVER
					linkedEntity = plateList.get(i);
					gp.playSE(3);
				}
			} else {
				if(linkedEntity == plateList.get(i)) { // IF THE ROCK COMES OFF, UNLINK
					linkedEntity = null;
				}
			}
			
		}
		
		// SCAN ROCK LIST
		for(int i = 0; i < rockList.size(); i++) {
			
			if(rockList.get(i).linkedEntity != null) {
				rocks++;
			}
		}
		
		if(rocks == rockList.size()) { // EACH ROCK IS ON EACH PLATE
			for(int i = 0; i < gp.obj[1].length; i++) {
				if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_IronDoor.objName)) {
					gp.obj[gp.currentMap][i] = null;
					gp.playSE(19);
				}
			}
		}
		
	}
//	public void setDialogue() {
//		
//		facePlayer();
//		startDialogue(this, dialogueSet);
//	}
}
