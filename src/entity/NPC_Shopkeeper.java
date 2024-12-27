package entity;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
	
public class NPC_Shopkeeper extends Entity{

		public NPC_Shopkeeper(GamePanel gp) {
			super(gp);
			
			direction = "down";
			speed = 1;
			getImage();
		}
			
		public void getImage() {
				try {
				up1 = setupAlternate("/npc/shopkeeper5",1,1);
				up2 = setupAlternate("/npc/shopkeeper6",1,1);
				down1 = setupAlternate("/npc/shopkeeper1",1,1);
				down2 = setupAlternate("/npc/shopkeeper2",1,1);
				left1 = setupAlternate("/npc/shopkeeper3",1,1);
				left2 = setupAlternate("/npc/shopkeeper4",1,1);
				right1 = setupAlternate("/npc/shopkeeper7",1,1);
				right2  = setupAlternate("/npc/shopkeeper8",1,1);
				}catch(Exception e) {
					e.printStackTrace();
			
				}
		}
		
		public void setAction() {
			
			actionLockCounter++;
			
			if(actionLockCounter == 80) {
				
				Random random = new Random();
				int i = random.nextInt(100)+1; //Random number from 1 to 100
				
				if(i<=25)
					direction = "up";
				if(i>25 && i <= 50)
					direction = "down";
				if(i >50 && i <=75)
					direction = "left";
				if(i>75 && i <= 100)
					direction = "right";
			
				actionLockCounter = 0;
			}
		}

		
	}
