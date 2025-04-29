package main;

import entity.Entity;
import object.OBJ_Axe;
import object.OBJ_BlueShield;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_IronDoor;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Pickaxe;
import object.OBJ_RedPotion;
import object.OBJ_Shield;
import object.OBJ_Sword;
import object.OBJ_Tent;

public class EntityGenerator {
	GamePanel gp;
	
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	public Entity getObject(String itemName) {

		
		switch(itemName) {
		case OBJ_Axe.objName:return new OBJ_Axe(gp);
		case OBJ_BlueShield.objName: return new OBJ_BlueShield(gp);
		case OBJ_Boots.objName: return new OBJ_Boots(gp);
		case OBJ_Chest.objName: return new OBJ_Chest(gp);
		case OBJ_Key.objName: return new OBJ_Key(gp);
		case OBJ_Lantern.objName: return new OBJ_Lantern(gp);
		case OBJ_RedPotion.objName: return new OBJ_RedPotion(gp);
		case OBJ_Shield.objName: return new OBJ_Shield(gp);
		case OBJ_Sword.objName: return new OBJ_Sword(gp);
		case OBJ_Tent.objName: return new OBJ_Tent(gp);
		case OBJ_Door.objName: return new OBJ_Door(gp);  // error without this line
		case OBJ_IronDoor.objName: return new OBJ_IronDoor(gp);
		case OBJ_Pickaxe.objName: return new OBJ_Pickaxe(gp);

		}
		System.out.println("error " + itemName + " not found in inventory");
		return null;
	}
}
