package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable{
	
	// PLAYER STATS
	int level;
	int maxLife;
	int life;
	int mana;
	int maxMana;
	int strength;
	int dexterity;
	int xp;
	int nextLevelXp;
	int coin;
	int stamina;
	
	// PLAYER INVENTORY
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	int currentWeaponSlot;
	int currentShieldSlot;
	int currentLightSlot;
	
}
