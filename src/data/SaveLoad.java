package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_BlueShield;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_RedPotion;
import object.OBJ_Shield;
import object.OBJ_Sword;
import object.OBJ_Tent;

public class SaveLoad {
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
		
	
	}
	public Entity getObject(String itemName) {

		
		switch(itemName) {
		case "Woodcutters Axe":return new OBJ_Axe(gp);
		case "Blue Shield": return new OBJ_BlueShield(gp);
		case "boots": return new OBJ_Boots(gp);
		case "chest": return new OBJ_Chest(gp);
		case "key": return new OBJ_Key(gp);
		case "Lantern": return new OBJ_Lantern(gp);
		case "Red Potion": return new OBJ_RedPotion(gp);
		case "Wood Shield": return new OBJ_Shield(gp);
		case "Normal Sword": return new OBJ_Sword(gp);
		case "Tent": return new OBJ_Tent(gp);
		}
		System.out.println("error " + itemName + "not found in inventory");
		return null;
	}
	public void save() {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			
			// STATS
			ds.level = gp.player.level;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.mana = gp.player.mana;
			ds.maxMana = gp.player.maxMana;
			ds.strength = gp.player.strength;
			ds.stamina = gp.player.stamina;
			ds.dexterity = gp.player.dexterity;
			ds.xp = gp.player.xp;
			ds.nextLevelXp = gp.player.nextLevelXp;
			ds.coin = gp.player.coin;
			
			// INVENTORY
			for(int i =0; i < gp.player.inventory.size(); i++) {
				ds.itemNames.add(gp.player.inventory.get(i).name);
				ds.itemAmounts.add(gp.player.inventory.get(i).amount);
			}
			
			// WRITE DATA TO save.dat
			
			oos.writeObject(ds);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error with save");
		}
	}
	public void load() {
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			DataStorage ds = (DataStorage)ois.readObject();
			
			// STATS
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.mana = ds.mana;
			gp.player.maxMana = ds.maxMana;
			gp.player.strength = ds.strength;
			gp.player.stamina = ds.stamina;
			gp.player.dexterity = ds.dexterity;
			gp.player.xp = ds.xp;
			gp.player.nextLevelXp = ds.nextLevelXp;
			gp.player.coin = ds.coin;
			
			// INVENTORY
			gp.player.inventory.clear();
			for(int i = 0; i < ds.itemNames.size(); i++) {
				 gp.player.inventory.add(getObject(ds.itemNames.get(i)));
				 gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("error with save");
		}
	}
}
