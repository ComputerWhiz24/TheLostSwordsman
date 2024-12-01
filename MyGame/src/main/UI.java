package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;


public class UI {

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
	
	BufferedImage heart_full,heart_half,heart_blank;
	
	
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
<<<<<<< HEAD
			if(playSubState == 1) {
=======
			if(playSubState == 1)
>>>>>>> 5f49b0fdfb4cbcb0fa02d0264aa3eb12690f1e2b
				drawCharacterInfo();
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
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y,null);
			i++;
			if(i<gp.player.life)
				g2.drawImage(heart_full, x, y,null);
			i++;
			x+= gp.tileSize;
		}
		//LIFE
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
<<<<<<< HEAD
		
		int textX = frameX + (int) frameWidth/2 + gp.tileSize/2;
		int textY = frameY + gp.tileSize + (int) frameHeight/6 ;
		final float lineHeight = (40);
		
		//STATS
		g2.drawString("Level: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Life: ",textX,textY);
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
=======
		
		int textX = frameX + (int) frameWidth/2 + gp.tileSize/2;
		int textY = frameY + gp.tileSize + (int) frameHeight/6 ;
		final float lineHeight = (40);
		
		//STATS
		g2.drawString("Level: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Life: ",textX,textY);
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
		g2.drawString("XP Until Next Level: ",textX,textY);
		textY += lineHeight;
		g2.drawString("Damage Power: ",textX,textY);
		textY += lineHeight;
		
		// STAT VALUES
		int tailX = (frameX + frameWidth - 30);
		textY = frameY + gp.tileSize + (int) frameHeight/6;
		String value = "";
		value = String.valueOf(gp.player.level);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
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
		
		value = String.valueOf(gp.player.xp);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelXp);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY); 
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY); 
		textY += lineHeight;
	}
>>>>>>> 5f49b0fdfb4cbcb0fa02d0264aa3eb12690f1e2b
	
		
		
		value = String.valueOf(gp.player.level);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		 
		value = df.format(gp.player.life) + "/" + df.format(gp.player.maxLife);
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
		
		value = String.valueOf(gp.player.attack);
		textX = getXForAlignRight(value,tailX);
		g2.drawString(value, textX, textY); 
		textY += lineHeight;
	}
	public void drawInventory() {
		int frameWidth = gp.tileSize*9;
		int frameHeight = gp.tileSize;
		int frameX = (int) gp.screenWidth/2 - frameWidth/2 + gp.tileSize * 9; 
		int frameY = (int) (gp.tileSize*1.5) + gp.tileSize*5;
		
		drawWindow(frameX,frameY,frameWidth,frameHeight);
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
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
		String text = "PAUSED";
		int x,y;
		x = getXForCenteredText(text);
		y = gp.screenHeight/2;
		g2.drawString(text, x, y);
		
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
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+= 40;
		}
		// PRESS ENTER TO CONTINUE CONVERSATION 
		if(gp.keyH.continueConversation == true) {
			Entity.dialogueIndex++;
			if(Entity.dialogues[Entity.dialogueIndex] == null)
				gp.gameState = gp.playState; 
			gp.ui.currentDialogue = Entity.dialogues[Entity.dialogueIndex];
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
}
