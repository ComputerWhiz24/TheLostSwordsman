package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];
	int previousEventX,previousEventY;
	boolean inRange;
	
	boolean hitCooldown = false;
	public EventHandler(GamePanel gp) {
		this.gp = gp;  
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0;
		int row = 0;
		
		
		while(col < gp.maxWorldCol && row < gp.maxScreenRow) {
			
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 48;
			eventRect[col][row].height  = 72;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}

	}
	
	public void checkEvent() {
		//CHECK IF PLAYER MOVES MORE THAN ONE TILE AWAY	
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		
		if(distance > gp.tileSize * 2) {
			inRange = false;
		}
		if(inRange == false) {
			if(hit(25,13,"right") == true) {
				loseHalfHeart(25,13,gp.dialogueState);
			}
		}
		// FOR SOME REASON, 25 IS IN THE MIDDLE OF A BLOCK INSTEAD OF THE START
		
		if(hit(22,6,"up") == true) {
			fullHP(22,6, gp.dialogueState);
		}
		/*
		 TELEPORT CHECKER
		if(hit(2,2,"any") == true){
			teleport(gp.dialogueState);
		}
		 */
		
	}
	public boolean hit(int eventCol, int eventRow, String reqDirection ) {
		
		boolean hit = false;
		
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;	
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;	
		
		eventRect[eventCol][eventRow].x = (int) (eventCol* gp.tileSize + eventRect[eventCol][eventRow].x);
		eventRect[eventCol][eventRow].y = (int) (eventRow* gp.tileSize + eventRect[eventCol][eventRow].y);
		
		 		//CHECK IF EVENT IS ONE TIME ONLY  
		if(gp.player.solidArea.intersects(eventRect[eventCol][eventRow]) && eventRect[eventCol][eventRow].eventDone == false) {
			
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
		eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;
		
		return hit;
	}
	
	public void loseHalfHeart(int col, int row, int gameState) {
		gp.ui.talkWorld = true;
		gp.ui.currentDialogue = "OWW, what was that?? Weird?";
		gp.gameState = gameState;
		gp.player.life -= 1;
		inRange = true;
		
	//	 eventRect[col][row].eventDone = true;  EVENT ONLY HAPPENS ONCE
	}
	public void fullHP(int col, int row, int gameState) {
		gp.ui.talkWorld = true;
		gp.ui.talkNPC = false;
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Suddenly you feel a lot better...?";
		gp.player.life = gp.player.maxLife;
		 
	}
	public void heal() {
		gp.player.life += 0.5;
	}
	/*
	 	TELEPORT METHOD:
	 	
	 public void teleport(int gameState){
	 	gp.gameState = gameState;
	 	gp.ui.currentDialogue = "Woah, where am i?";
	 	gp.player.worldX = gp.tileSize * 2;
	 	gp.player.worldY = gp.tileSize * 3;
	 */
}

