package tile_interactive;

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
}
