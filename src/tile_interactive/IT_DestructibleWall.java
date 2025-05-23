package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DestructibleWall extends InteractiveTile{
		
	public static final String itName = "Destructible Wall";
	public IT_DestructibleWall(GamePanel gp, int col, int row) {
	super(gp,col,row);
	this.gp = gp;
	name = itName;
	this.worldX = gp.tileSize * col;
	this.worldY = gp.tileSize*row;
	life = 1;
	image = setup("/tiles_interactive/destructiblewall");
	down1 = image;
	destructible = true;
	}
	
	public boolean isCorrectItem(Entity entity) {
		 boolean isCorrectItem = false;
		 if(entity.currentWeapon.type == type_pickaxe) {
			 isCorrectItem = true;
		 }
		 return isCorrectItem;
	}
	public void playSE() {
		gp.playSE(18);
	}
	public Color getParticleColor() {
		Color color = new Color(65,65,65);
		return color;
	}
	public int getParticleSize() {
		int size = 6; // 6 PIXELS
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 10;
		return maxLife;
	}

}