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

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;


public class UI {

	GamePanel gp;
	Font maruMonica  ;
	Graphics2D g2;
	public boolean messageOn;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue;
	public int commandNum = 0;
	public int titleSubState = 0;
	
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
	 
	public void showMessage(String text) {
		message = text;
		messageOn = true;
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white); 
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
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
	
	public void drawTitleScreen() {
		if(titleSubState == 0) {
			
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
		
		else if(titleSubState == 1) {
			
			//CONTROLS SCREEN
			
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
}
