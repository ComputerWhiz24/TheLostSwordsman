package main;



public class EventHandler {

	GamePanel gp;
	EventRect[][][] eventRect;
	int previousEventX,previousEventY;
	boolean hitDisabled = false;
	int tempMap, tempCol,tempRow;
	public EventHandler(GamePanel gp) {
		this.gp = gp;  
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		
		
		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 48;
			eventRect[map][col][row].height  = 72;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}

	}
	
	public void checkEvent() {
		//CHECK IF PLAYER MOVES MORE THAN ONE TILE AWAY	
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		
		if(distance > gp.tileSize*1.25) {
			hitDisabled = false;
		}
		if(!hitDisabled) {
			if(hit(0,25,13,"right") == true) {loseHalfHeart(gp.dialogueState);
			}else if(hit(0,22,6,"up") == true) {fullHP(gp.dialogueState);
			}else if(hit(0,9,38,"any")) {teleport(1,12,13);System.out.println("hit");
			}else if(hit(1,12,13,"any")) {teleport(0,10,39);
			}
		}
		// FOR SOME REASON, 25 IS IN THE MIDDLE OF A BLOCK INSTEAD OF THE START
		
	}
	public boolean hit(int map, int eventCol, int eventRow, String reqDirection ) {

		// HIT METHOD IS DETECTING ONLY HALF THE TILE OF X AND Y
		
		boolean hit = false;
		if(map == gp.currentMap) {
			
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;	
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;	
			
			eventRect[map][eventCol][eventRow].x = (eventCol* gp.tileSize + eventRect[map][eventCol][eventRow].x);
			eventRect[map][eventCol][eventRow].y = (eventRow* gp.tileSize + eventRect[map][eventCol][eventRow].y);
			
			 		//CHECK IF EVENT IS ONE TIME ONLY  
			if(gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow]) && 
					eventRect[map][eventCol][eventRow].eventDone == false) {
					// CHECK IF DIRECTION IS CORRECT
				if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
			eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
			
		}
		return hit;
	}
	
	public void loseHalfHeart(int gameState) {

		gp.ui.talkWorld = true;
		gp.ui.currentDialogue = "OWW, what was that?? Weird?";
		gp.gameState = gameState;
		gp.player.life -= 1;
		hitDisabled = true;
		
	//	 eventRect[col][row].eventDone = true;  EVENT ONLY HAPPENS ONCE
	}
	public void fullHP(int gameState) {
		gp.ui.talkWorld = true;
		gp.ui.talkNPC = false;
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Suddenly you feel a lot better...?";
		gp.player.life = gp.player.maxLife;
		 
	}
	public void heal() {
		gp.player.life += 0.5;
	}
	
	 // TELEPORT METHOD:
	 	
	 public void teleport(int map, int col, int row){
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;

		hitDisabled  = true;
	 }
}
