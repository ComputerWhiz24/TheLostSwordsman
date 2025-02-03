package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;


public class UI extends JFrame implements MouseListener{

	GamePanel gp;
	Font maruMonica  ;
	Graphics2D g2;
	public boolean messageOn;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue;
	public int commandNum = 0;
	public int titleSubState = 0;
	public int playSubState = 0;
	public int pauseSubState = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	
	public int cols = 22;
	public int rows = 11;
	public int slotXStart;
	public int slotYStart;
	
	BufferedImage heart_full,heart_half,heart_blank,crystal_full,crystal_blank;
	
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00"); 
	public UI(GamePanel gp) {
		this.gp = gp;
		

		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3; 
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
	}   
	 
	public void addMessage(String text) {
		 
		message.add(text);
		messageCounter.add(0);
		
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white); 
		
		if(gp.gameState == gp.titleState) {
			if(titleSubState == 0)
				drawOpeningScreen();
			else if(titleSubState == 1) 
				drawControlScreen();
		}
		
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
			if(playSubState == 1) {
				drawCharacterInfo();
				
			}else if (playSubState == 2){
				drawInventory();
			}
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
			drawPlayerLife();
		}
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
			drawPlayerLife();
		}

	}
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		//MAXLIFE
		while(i < gp.player.maxLife/2) {
			
			g2.drawImage(heart_blank, x, y,null);
			i++;
			x+= gp.tileSize;
		}
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		// CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y,null);
			i++;
			if(i<gp.player.life)
				g2.drawImage(heart_full, x, y,null);
			i++;
			x+= gp.tileSize;
		}
		// MAX MANA
		 x = gp.tileSize/2;
		 y = gp.tileSize*2;
		 i = 0;
		 while(i < gp.player.maxMana/20) {
			 g2.drawImage(crystal_blank, x, y, null);
			 i++;
			 x += 35;
			 }
		 // CURRENT MANA
		 x = gp.tileSize/2;
		 y = gp.tileSize*2;
		 i = 0;
		 while(i < gp.player.mana/20) {
			 g2.drawImage(crystal_full, x, y, null);
			 i++;
			 x += 35;
			 }
	}
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < message.size(); i++) {
			
		
			if(message.get(i)!=null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2 );
				g2.setColor(Color.white);
			 	g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i)+1; //messageCounter++
				messageCounter.set(i, counter);
				messageY+=50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
					
				}
			}
		}
	}
	public void drawCharacterInfo() {
		// CREATING THE BACKGROUND
		final int frameWidth = gp.tileSize*13;
		final int frameHeight = gp.tileSize * 14;
		final int frameX = (int) gp.screenWidth/2 - frameWidth/2;
		final int frameY = (int) (gp.tileSize*1.5);
		
		drawWindow(frameX,frameY,frameWidth,frameHeight);
		
		//DRAWING CHARACTER SPRITE
		int spriteWidth = gp.tileSize * 5;  // Example: scale the sprite to 2x the tile size
		int spriteHeight = gp.tileSize * 5;

		// Draw the character sprite with scaling
		g2.drawImage(
		    gp.player.down1,                  // The image to draw
		    frameX + gp.tileSize,             // X position of the top-left corner
		    frameY + gp.tileSize*4,             // Y position of the top-left corner
		    frameX + gp.tileSize + spriteWidth,  // X position of the bottom-right corner
		    frameY + gp.tileSize*4 + spriteHeight, // Y position of the bottom-right corner
		    0, 0,                            // Source image's top-left corner
		    gp.player.down1.getWidth(null),  // Source image's width
		    gp.player.down1.getHeight(null), // Source image's height
		    null                             // Observer
		);
		
		//WRITING TEXT
	
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + (int) frameWidth/2 + gp.tileSize/2;
		int textY = frameY + gp.tileSize + (int) frameHeight/6 ;
		final float lineHeight = (40);
		
		//STATS
		g2.drawString("Level: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Life: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Mana: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Strength: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Vitality: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Dexterity: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Defense: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Gold: ",textX,textY);
		textY += lineHeight;
		g2.drawString("XP: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Damage Power: ",textX,textY);
		textY += lineHeight;
		
		// STAT VALUES
		int tailX = (frameX + frameWidth - 30);
		textY = frameY + gp.tileSize + (int) frameHeight/6;
		String value = "";
		DecimalFormat df = new DecimalFormat("#.00");
		String roundedAttack = df.format(gp.player.attack);
		
		
		value = String.valueOf(gp.player.level);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		 
		value = df.format(gp.player.life) + "/" + df.format(gp.player.maxLife);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = df.format(gp.player.mana) + "/" + df.format(gp.player.maxMana);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.vitality);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.xp + "/" + gp.player.nextLevelXp);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY); 
		textY += lineHeight;
		
		value = roundedAttack;
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY); 
		textY += lineHeight;
	}
	public void drawInventory() {
		cols = 22;
		rows = 11;
	
		//FRAME
		int frameWidth = gp.tileSize*cols;
		int frameHeight = gp.tileSize*rows;
		int frameX = (int) gp.screenWidth/2 - frameWidth/2; 
		int frameY = (int) gp.screenHeight/2 - frameHeight/2;
		drawWindow(frameX,frameY,frameWidth,frameHeight);
		
		//SLOT
		slotXStart = frameX + 20;
		slotYStart = frameY + 20;
		
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize;
		JLabel label = new JLabel();
		label.setBounds(getBounds());
		//DRAW INVENTORY ITEMS
		int split = cols - 1;
		for(int i = 0; i <gp.player.inventory.size(); i++) {
			
			//EQUIP CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize,gp.tileSize,10,10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
			slotX+=slotSize;
			
			if((i + 1) % split == 0) {
				slotX = slotXStart;
				slotY+= gp.tileSize;
			}
		}
		
		int cursorX = slotXStart + slotSize * slotCol;
		int cursorY = slotYStart + slotSize * slotRow;
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		//DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(1));
		g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);
		
		
		//DESC FRAME IF USER PRESSES F
		if(gp.keyH.showDesc) {
		
			drawPauseScreen();
		}else if(!gp.keyH.showDesc){	//SHOW OPTIONS
			if (gp.player.inventory.get(getItemIndex()) != null) {
			    int dFrameX = cursorX + gp.tileSize;
			    int dFrameY = cursorY;
			    int dFrameWidth = (int) (gp.tileSize * 2);
			    int dFrameHeight = (int) (gp.tileSize);

			 
			    g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
			    g2.fillRect(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

			    // Draw the text inside the rectangle
			    int textX = dFrameX + 10; // Adjust padding
			    int textY = dFrameY + 10;
			    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18F));
			    g2.setColor(Color.WHITE); // Set text color
			    g2.drawString("Show more: F\n", textX, textY);
			    Entity currentItem = gp.player.inventory.get(getItemIndex());
			    // if a sword,axe, or shield, display press e to equip
			    if(currentItem.type == gp.player.type_sword ||
			    		currentItem.type == gp.player.type_axe ||
			    				currentItem.type == gp.player.type_shield) {
				    if(currentItem == gp.player.currentWeapon ||
				    		currentItem == gp.player.currentShield) {
				    	g2.drawString("Unequip: E\n", textX, textY+30);
				    } else {
				    	g2.drawString("Equip: E\n", textX, textY+30);
				    }
			    } else if (currentItem.type == gp.player.type_consumable) {
			     	g2.drawString("Consume: E\n", textX, textY+30);
			    }
			}
			
			
		}
		
	}
	public int getItemIndex() {
		int itemIndex = slotCol + (slotRow * 21);
		return itemIndex;
	}
	public void drawOpeningScreen() {
		
			
			//BACKGROUND COLOR
			g2.setColor(Color.blue);
			g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
			
			//TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,96));
			String text = "The Lost Swordsman";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;
			
			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+5,y+5);
			
			//MAIN COLOR 
			g2.setColor(Color.white);
			g2.drawString(text,x,y);
			
			//BLUE BOY IMAGE 
			x = gp.screenWidth / 2 - (gp.tileSize *4) / 2;
			y += gp.tileSize*3;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*4,gp.tileSize*4, null);
			
			//MENU
			text = "NEW GAME";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			x = getXForCenteredText(text);
			y += gp.tileSize * 7;
			g2.drawString(text,x,y);
			if(commandNum == 0) {
				g2.drawString(">",x-gp.tileSize,y);
			}
			
			text = "LOAD GAME";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 1) {
				g2.drawString(">",x-gp.tileSize,y);
			}
			
			text = "CONTROLS";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 2) {
				g2.drawString(">",x-gp.tileSize,y);
			}
			
			
			text = "QUIT";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 3) {
				g2.drawString(">",x-gp.tileSize,y);
			}
			
	}
	public void drawControlScreen() {
			
			g2.setColor(Color.blue);
			g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
			
			
			g2.setColor(Color.white);
			String text = "CONTROLS";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			int x = getXForCenteredText(text);
			int y = gp.tileSize*2;
			g2.drawString(text,x,y);
			
			text = "UP:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			x = gp.tileSize*8;
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			
			text = "W";
			g2.drawString(text,x+gp.tileSize*7,y);
			
			text = "DOWN:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			text = "S";
			g2.drawString(text,x+gp.tileSize*7,y);
			
			text = "LEFT:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			text = "A";
			g2.drawString(text,x+gp.tileSize*7,y);
			
			text = "RIGHT:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			text = "D";
			g2.drawString(text,x+gp.tileSize*7,y);
			
			text = "TALK:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			text = "T";
			g2.drawString(text,x+gp.tileSize*7,y);
			
			text = "CANCEL:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			text = "ESC";
			g2.drawString(text,x+gp.tileSize*7,y);
			
			text = "NEXT:";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			y += gp.tileSize*2;
			g2.drawString(text,x,y);
			text = "ENTER";
			g2.drawString(text,x+gp.tileSize*7,y);
		}
	public void drawPauseScreen() {
		if(pauseSubState == 0) {
		//NORMAL PAUSE STATE
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
		String text = "PAUSED";
		int x,y;
		x = getXForCenteredText(text);
		y = gp.screenHeight/2;
		g2.drawString(text, x, y);
		}
		else if(pauseSubState == 1) { //SHOW ITEM DESCRIPTION
			cols = 22;
			rows = 11;
		
			//FRAME
			int frameWidth = gp.tileSize*cols;
			int frameHeight = gp.tileSize*rows;
			int frameX = (int) gp.screenWidth/2 - frameWidth/2; 
			int frameY = (int) gp.screenHeight/2 - frameHeight/2;
			drawWindow(frameX,frameY,frameWidth,frameHeight);
			
			//SLOT
			slotXStart = frameX + 20;
			slotYStart = frameY + 20;
			
			int slotX = slotXStart;
			int slotY = slotYStart;
			int slotSize = gp.tileSize;
			JLabel label = new JLabel();
			label.setBounds(getBounds());
			//DRAW INVENTORY ITEMS
			int split = cols - 1;
			for(int i = 0; i <gp.player.inventory.size(); i++) {
				
				g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
				slotX+=slotSize;
				
				if((i + 1) % split == 0) {
					slotX = slotXStart;
					slotY+= gp.tileSize;
				}
			}
			
			int cursorX = slotXStart + slotSize * slotCol;
			int cursorY = slotYStart + slotSize * slotRow;
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			//DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(1));
			g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);
			
			//DESCRIPTION FRAME
			int dFrameX = cursorX + gp.tileSize;
			int dFrameY = cursorY;
			int dFrameWidth = gp.tileSize*6;
			int dFrameHeight = gp.tileSize*3+30;
			drawWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
		//DRAW TEXT
			int textX = dFrameX + 2;
			int textY = dFrameY + 40;
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
			
			int itemIdx = getItemIndex();
			if(itemIdx < gp.player.inventory.size()) {
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
				for(String line: gp.player.inventory.get(itemIdx).description.split("\n")) {
					g2.drawString(line, textX+10, textY);
					textY +=38;
				}
			}
		}
		
	}
	public void drawDialogueScreen() {
		
		int x = gp.tileSize * 2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height =  gp.tileSize * 4;
		
		drawWindow(x,y,width,height);
		
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x += gp.tileSize;
		y +=  gp.tileSize;
		currentDialogue = Entity.currentDialogue[Entity.dialogueIndex];
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+= 40;
		}
		// PRESS ENTER TO CONTINUE CONVERSATION 
		if(gp.keyH.continueConversation == true && Entity.dialogueIndex<Entity.currentDialogue.length) {
			Entity.dialogueIndex++;
		}
		gp.keyH.continueConversation = false;

	}
	public void drawWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,210);
		
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
		
	}
	public int getXForCenteredText(String text) {
		
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth /2 - length/2;
		return x;
	}
	public int getXForAlignRight(String text, int tailX) {
		
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
