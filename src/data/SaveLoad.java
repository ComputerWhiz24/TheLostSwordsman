package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

public class SaveLoad {
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
		
	
	}
	public void save() {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			
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
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("error with save");
		}
	}
}
