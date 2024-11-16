package main;

import entity.NPC_OldMan;
import entity.NPC_Wizard;
import monster.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {

	}
	
	public void setMonster() {
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster [0].worldY  = gp.tileSize*40;
	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY  = gp.tileSize*21;
		
		gp.npc[1] = new NPC_Wizard(gp);
		gp.npc[1].worldX = gp.tileSize*23;
		gp.npc[1].worldY  = gp.tileSize*21;
	}
}
