package main;

import entity.Entity;
import entity.NPC_BigRock;
import entity.NPC_OldMan;
import entity.NPC_Shopkeeper;
import entity.NPC_Wizard;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import object.OBJ_Axe;
import object.OBJ_BlueShield;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_IronDoor;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Pickaxe;
import object.OBJ_RedPotion;
import object.OBJ_Tent;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSetter {
	// X = 23 IS MIDDLE OF MAP... ASETTER STARTS AT 0 BUT MAP STARTS AT 1
	// Y STARTS AT 0
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		// MAP 0
		
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*20;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Key(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Key(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*33;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_BlueShield(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*35;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_RedPotion(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*37;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*14;
		gp.obj[mapNum][i].worldY  = gp.tileSize*28;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*12;
		gp.obj[mapNum][i].worldY  = gp.tileSize*12;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Axe(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*13;
		gp.obj[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Lantern(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*18;
		gp.obj[mapNum][i].worldY  = gp.tileSize*20;
		i++;

		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*19;
		gp.obj[mapNum][i].worldY  = gp.tileSize*20;
		i++;
		
		// MAP 1
		i = 0;
		mapNum = 1;
		
		
		i = 0;
		mapNum = 2;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*40;
		gp.obj[mapNum][i].worldY  = gp.tileSize*41;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_RedPotion(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*13;
		gp.obj[mapNum][i].worldY  = gp.tileSize*16;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_RedPotion(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*26;
		gp.obj[mapNum][i].worldY  = gp.tileSize*34;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_RedPotion(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*27;
		gp.obj[mapNum][i].worldY  = gp.tileSize*15;
		i++;
		gp.obj[mapNum][i] = new OBJ_IronDoor(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*18;
		gp.obj[mapNum][i].worldY  = gp.tileSize*23;
		i++;
		
	}
	public void setMonster() {
		int i = 0;
		int mapNum = 0;
		
		int randX = (int) (Math.random() * 9) + 20;
		int randY = (int) (Math.random() * 5)+ 37;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*randX;
		gp.monster[mapNum][i].worldY  = gp.tileSize*randY;
		
		randX = (int) (Math.random() * 9) + 20;
		randY = (int) (Math.random() * 5)+ 37;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*randX;
		gp.monster[mapNum][i].worldY  = gp.tileSize*randY;
		
		randX = (int) (Math.random() * 9) + 20;
		randY = (int) (Math.random() * 5)+ 37;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*randX;
		gp.monster[mapNum][i].worldY  = gp.tileSize*randY;
		
		randX = (int) (Math.random() * 9) + 20;
		randY = (int) (Math.random() * 5)+ 37;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*randX;
		gp.monster[mapNum][i].worldY  = gp.tileSize*randY;
		
		randX = (int) (Math.random() * 9) + 20;
		randY = (int) (Math.random() * 5)+ 37;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*randX;
		gp.monster[mapNum][i].worldY  = gp.tileSize*randY;
		
		randX = (int) (Math.random() * 9) + 20;
		randY = (int) (Math.random() * 5)+ 37;
		i++;
		
		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*12;
		gp.monster[mapNum][i].worldY  = gp.tileSize*33;
		i++;
	}
	public void respawnMonster(Entity monster, int i, int mapNum, int x, int y) {
		
		if(monster instanceof MON_Orc) {
			gp.monster[mapNum][i] = new MON_Orc(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize * x;
			gp.monster[mapNum][i].worldY  = gp.tileSize * y;
		}
		else if (monster instanceof MON_GreenSlime) {
	
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize * x;
			gp.monster[mapNum][i].worldY  = gp.tileSize * y;
		}
//		gp.monster[mapNum][i] = monster;
//		gp.monster[mapNum][i].worldX = x;
//		gp.monster[mapNum][i].worldY  = y;
		
	}
	public void setNPC() {
		// MAP 0
		int i = 0;
		int mapNum = 0;
		gp.npc[mapNum][i] = new NPC_Wizard(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*23;
		gp.npc[mapNum][i].worldY  = gp.tileSize*21;
		i++;
		
		// MAP 1
		i = 0;
		mapNum = 1;
		gp.npc[mapNum][i] = new NPC_Shopkeeper(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*12;
		gp.npc[mapNum][i].worldY  = gp.tileSize*7;
		i++;
		
		i = 0;
		mapNum = 2;
		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*20;
		gp.npc[mapNum][i].worldY  = gp.tileSize*25;
		i++;
		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*11;
		gp.npc[mapNum][i].worldY  = gp.tileSize*18 ;
		i++;
		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*23;
		gp.npc[mapNum][i].worldY  = gp.tileSize*14 ;
		i++;
	}
	public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,26,7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,27,7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,28,7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,29,7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,30,7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,31,7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,32,7);
		i++;
		
		mapNum = 2;
		i = 0;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,30);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,31);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,32);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,34);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,34);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,33);i++;
		
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,10,22);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,10,24);i++;
		
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,18);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,19);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,20);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,21);i++;
		
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,13);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,14);i++;
		 
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,22,28);i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,30,28);i++;
		
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,32,38);i++;
		
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp,20,22);i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp,8,17);i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp,39,31);i++;
		
	}
}
