package main;

import entity.Entity;
import entity.NPC_OldMan;
import entity.NPC_Shopkeeper;
import entity.NPC_Wizard;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_BlueShield;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_RedPotion;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	// X = 23 IS MIDDLE OF MAP... ASETTER STARTS AT 0 BUT MAP STARTS AT 1
	// Y STARTS AT 0
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
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
	}
	public void addSlimes(int num) {
		int mapNum = 0;
		for(int i = 0; i < num; i++) { // only works if slime is only monster
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			int randX = (int) (Math.random() * 9) + 20;
			int randY = (int) (Math.random() * 5)+ 37;
			gp.monster[mapNum][i].worldX = gp.tileSize*randX;
			gp.monster[mapNum][i].worldY  = gp.tileSize*randY;
		}
		
	}
	public void addSlime(int idx) {
		int mapNum = 0;
			gp.monster[mapNum][idx] = new MON_GreenSlime(gp);
			int randX = (int) (Math.random() * 9) + 20;
			int randY = (int) (Math.random() * 5)+ 37;
			gp.monster[mapNum][idx].worldX = gp.tileSize*randX;
			gp.monster[mapNum][idx].worldY  = gp.tileSize*randY;
	}
	public void setMonster() {
		addSlimes(5);
	}
	public void setNPC() {
		int mapNum = 0;
		gp.npc[mapNum][0] = new NPC_Shopkeeper(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize*21;
		gp.npc[mapNum][0].worldY  = gp.tileSize*21;
		
		gp.npc[mapNum][1] = new NPC_Wizard(gp);
		gp.npc[mapNum][1].worldX = gp.tileSize*23;
		gp.npc[mapNum][1].worldY  = gp.tileSize*21;
		
//		mapNum = 1;
//		gp.npc[mapNum][1] = new NPC_Wizard(gp);
//		gp.npc[mapNum][1].worldX = gp.tileSize*23;
//		gp.npc[mapNum][1].worldY  = gp.tileSize*21;
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
	}
}
