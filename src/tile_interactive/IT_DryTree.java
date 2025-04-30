package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile{
	GamePanel gp;
	public static final String itName = "Dry Tree";
	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp,col,row);
		this.gp = gp;
		name = itName;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize*row;
		life = 2;
		image = setup("/tiles_interactive/drytree");
		image2 = setup("/tiles_interactive/trunk");
		down1 = image;
		destructible = true;
	}
	public void damageReaction(int tileIdx) {
		if(gp.iTile[gp.currentMap][tileIdx].life == 2) {
			gp.iTile[gp.currentMap][tileIdx].down1 = gp.iTile[gp.currentMap][tileIdx].image;
		}
		else if(gp.iTile[gp.currentMap][tileIdx].life == 1) {
			gp.iTile[gp.currentMap][tileIdx].down1 = gp.iTile[gp.currentMap][tileIdx].image2;
		}else if(gp.iTile[gp.currentMap][tileIdx].life == 0) {
			gp.iTile[gp.currentMap][tileIdx]= null;
		}

	}
	public boolean isCorrectItem(Entity entity) {
		 boolean isCorrectItem = false;
		 if(entity.currentWeapon.type == type_axe) {
			 isCorrectItem = true;
		 }
		 return isCorrectItem;
	}
	public void playSE() {
		gp.playSE(10);
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
