 package entity;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Axe;
import object.OBJ_BlueShield;
import object.OBJ_RedPotion;
	
public class NPC_Shopkeeper extends Entity{
	public static String[] shopkeeperDialogue = new String[1];
		public NPC_Shopkeeper(GamePanel gp) {
			super(gp);
			
			tradable = true;
			direction = "down";
			speed = 1;
			getImage();
			setDialogue();
			setItems();
			cols = 5;
			rows = 5;
			inventorySize = 16;
		}
			
		public void getImage() {
				try {
				up1 = setupAlternate("/npc/shopkeeper5",1.5,1.5);
				up2 = setupAlternate("/npc/shopkeeper6",1.5,1.5);
				down1 = setupAlternate("/npc/shopkeeper1",1.5,1.5);
				down2 = setupAlternate("/npc/shopkeeper2",1.5,1.5);
				left1 = setupAlternate("/npc/shopkeeper3",1.5,1.5);
				left2 = setupAlternate("/npc/shopkeeper4",1.5,1.5);
				right1 = setupAlternate("/npc/shopkeeper7",1.5,1.5);
				right2  = setupAlternate("/npc/shopkeeper8",1.5,1.5);
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
		public void setDialogue() {
			shopkeeperDialogue[0] = "Looking to buy or sell somethin' little man?";
		}
		public void speak() {

			dialogueIndex = 0;
			currentDialogue = shopkeeperDialogue;
			
	
			switch(gp.player.direction) {
				case"up":
					direction = "down";
					break;
				case"down":
					direction = "up";
					break;
				case"left":
					direction = "right";
					break;
				case"right":
					direction = "left";
					break;
					 
			}
			gp.ui.npc = this;
			tradable = true;
		}
		public void setItems() {
			inventory.add(new OBJ_RedPotion(gp));
			inventory.add(new OBJ_BlueShield(gp));
			inventory.add(new OBJ_Axe(gp));
			
		}
		
	}
