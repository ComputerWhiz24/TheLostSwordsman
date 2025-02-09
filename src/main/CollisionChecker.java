package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1,tileNum2,tileNum3;
		
		switch(entity.direction) {
		
		case"up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
				
 			break;
		
		case"down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		
		case"left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		
		case"right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case"upLeft":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; 	//top left
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; //bottom left
			tileNum3 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // top right
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ||
					gp.tileM.tile[tileNum3].collision == true) {
				entity.collisionOn= true;
				entity.collisionOnLeft= true;
				entity.collisionOnUp= true;
			}
			if(gp.tileM.tile[tileNum2].collision == false) {
				entity.collisionOnLeft = false;
				entity.collisionOnUp = true;
				
			}
			if(gp.tileM.tile[tileNum3].collision == false) {
				entity.collisionOnUp = false;
				entity.collisionOnLeft = true;
			}
			break;
		case"upRight":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; //top right
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // bottom right
			tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // top left
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true || 
					gp.tileM.tile[tileNum3].collision == true){
				entity.collisionOn = true;
				entity.collisionOnRight= true;
				entity.collisionOnUp= true;
			}
			if(gp.tileM.tile[tileNum2].collision == false) {
				entity.collisionOnRight = false;
			}
			if(gp.tileM.tile[tileNum3].collision == false) {
				entity.collisionOnUp = false;
			}
			break;
		case"downLeft":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // bottom left
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // bottom right
			tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // top left
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true || 
					gp.tileM.tile[tileNum3].collision == true){
				entity.collisionOn = true;
				entity.collisionOnLeft= true;
				entity.collisionOnDown= true;
			}
			if(gp.tileM.tile[tileNum3].collision == false) {
				entity.collisionOnLeft = false;	
			}
			if(gp.tileM.tile[tileNum2].collision == false) {
				entity.collisionOnDown = false;
			}
			break;
		case"downRight":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // bottom right
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // top right
			tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // bottom left
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true || 
					gp.tileM.tile[tileNum3].collision == true ) {
				entity.collisionOn = true;
				entity.collisionOnRight= true;
				entity.collisionOnDown= true;
			}
			if(gp.tileM.tile[tileNum2].collision == false) {
				entity.collisionOnRight = false;	
			}
			if(gp.tileM.tile[tileNum3].collision == false) {
				entity.collisionOnDown = false;
			}
			break;

		}
		
	}
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			
			if(gp.obj[i] != null) {
				
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case"up":
					entity.solidArea.y -=entity.speed; break;
				case"down":
					entity.solidArea.y +=entity.speed; break;
				case"left":
					entity.solidArea.x -=entity.speed; break;
				case"right":
					entity.solidArea.x +=entity.speed; break;
				case"upLeft":
					entity.solidArea.y -=entity.speed;entity.solidArea.x -=entity.speed; break;
				case"upRight": 
					entity.solidArea.y -=entity.speed;	entity.solidArea.x +=entity.speed; break;
				case"downLeft":
					entity.solidArea.y +=entity.speed;	entity.solidArea.x -=entity.speed; break;
				case"downRight":
					entity.solidArea.y +=entity.speed; entity.solidArea.x +=entity.speed; break;
				
				}
				if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if(gp.obj[i].collision == true) {
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

			}
			
		}
		
		return index;
	}
	
	// NPC OR MONSTER COLLISION OR INTERACTIVE TILE COLLISION
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			
			if(target[i] != null) {
				
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
			
				switch(entity.direction) {
				
				case"up":
					entity.solidArea.y -=entity.speed; break;
				case"down":
					entity.solidArea.y +=entity.speed; break;
				case"left":
					entity.solidArea.x -=entity.speed; break;
				case"right":
					entity.solidArea.x +=entity.speed; break;
				case"upLeft":
					entity.solidArea.y -=entity.speed; entity.solidArea.x -=entity.speed; break;
				case"upRight": 
					entity.solidArea.y -=entity.speed; entity.solidArea.x +=entity.speed; break;
				case"downLeft":
					entity.solidArea.y +=entity.speed; entity.solidArea.x -=entity.speed; break;
				case"downRight":
					entity.solidArea.y +=entity.speed; entity.solidArea.x +=entity.speed; break;
			
				}
				if(entity.solidArea.intersects(target[i].solidArea) && target[i] != entity) {
					entity.collisionOn = true;
					
					index = i;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;

			}
			
		}
		
		return index;
	}
	public boolean checkPlayer(Entity entity) {
		
		boolean hitPlayer = false;
		
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(entity.direction) {
		
		case"up":
			entity.solidArea.y -=entity.speed; break;
		case"down":
			entity.solidArea.y +=entity.speed; break;
		case"left":
			entity.solidArea.x -=entity.speed; break;
		case"right":
			entity.solidArea.x +=entity.speed; break;
		case"upLeft":
			entity.solidArea.y -=entity.speed; entity.solidArea.x -=entity.speed; break;
		case"upRight": 
			entity.solidArea.y -=entity.speed; entity.solidArea.x +=entity.speed; break;
		case"downLeft":
			entity.solidArea.y +=entity.speed; entity.solidArea.x -=entity.speed; break;
		case"downRight":
			entity.solidArea.y +=entity.speed; entity.solidArea.x +=entity.speed; break;
		
		}
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			hitPlayer = true;
			entity.hpBarOn = true;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return hitPlayer; 
	}

}
