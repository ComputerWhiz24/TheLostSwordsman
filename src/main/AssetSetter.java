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
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = gp.tileSize*20;
		gp.obj[i].worldY  = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY  = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*22;
		gp.obj[i].worldY  = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize*33;
		gp.obj[i].worldY  = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_BlueShield(gp);
		gp.obj[i].worldX = gp.tileSize*35;
		gp.obj[i].worldY  = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_RedPotion(gp);
		gp.obj[i].worldX = gp.tileSize*37;
		gp.obj[i].worldY  = gp.tileSize*21;
		i++;
	}
	public void addSlimes(int num) {
		
		for(int i = 0; i < num; i++) { // only works if slime is only monster
			gp.monster[i] = new MON_GreenSlime(gp);
			int randX = (int) (Math.random() * 9) + 20;
			int randY = (int) (Math.random() * 5)+ 37;
			gp.monster[i].worldX = gp.tileSize*randX;
			gp.monster[i].worldY  = gp.tileSize*randY;
		}
		
	}
	public void addSlime(int idx) {
		
			gp.monster[idx] = new MON_GreenSlime(gp);
			int randX = (int) (Math.random() * 9) + 20;
			int randY = (int) (Math.random() * 5)+ 37;
			gp.monster[idx].worldX = gp.tileSize*randX;
			gp.monster[idx].worldY  = gp.tileSize*randY;
	}
	public void setMonster() {
		addSlimes(5);
	}
	public void setNPC() {
		gp.npc[0] = new NPC_Shopkeeper(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY  = gp.tileSize*21;
		
		gp.npc[1] = new NPC_Wizard(gp);
		gp.npc[1].worldX = gp.tileSize*23;
		gp.npc[1].worldY  = gp.tileSize*21;
	}
	public void setInteractiveTile() {
		int i = 0;
		
		gp.iTile[i] = new IT_DryTree(gp,26,7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp,27,7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp,28,7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp,29,7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp,30,7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp,31,7);
		i++;
		gp.iTile[i] = new IT_DryTree(gp,32,7);
		i++;
	}
}
