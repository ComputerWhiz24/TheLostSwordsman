package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile{
	GamePanel gp;
	
	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp,col,row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize*row;
		life = 2;
		down1 = setup("/tiles_interactive/drytree");
		down2 = setup("/tiles_interactive/trunk");
		
		destructible = true;
	}

	public boolean isCorrectItem(Entity entity) {
		 boolean isCorrectItem = false;
		 if(entity.currentWeapon.type == type_axe) {
			 isCorrectItem = true;
		 }
		 return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(65,50,30);
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
