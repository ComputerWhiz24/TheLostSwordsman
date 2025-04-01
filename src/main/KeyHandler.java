package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import entity.Entity;

public class KeyHandler implements KeyListener,MouseListener, MouseMotionListener{
	public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed,attackPressed,showDesc,shootSpell, enterPressed,openPressed, 
	qPressed;
	GamePanel gp;
	boolean checkDrawTime = false;
	
	
	public KeyHandler(GamePanel gp) {
		 this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		else if(gp.gameState == gp.playState) {
			playState(code);
		}
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		else if(gp.gameState == gp.tradeState) {
			tradeState(code);
		}
		else if(gp.gameState == gp.mapState) {
			mapState(code);
		}
	}
	
	public void titleState(int code) {
		
		if(gp.ui.titleSubState == 0) {
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 3)
					gp.ui.commandNum = 0;
			}
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum == -1)
					gp.ui.commandNum = 3;
			}
			if(code == KeyEvent.VK_ENTER || code == MouseEvent.BUTTON1) { //ADDED MOUSE EVENT
				switch(gp.ui.commandNum) {
				case 0:
					gp.gameState = gp.playState;
					gp.playMusic(0);
					break;
				case 1:
					// add later
					break;
				case 2:
					gp.ui.titleSubState = 1;
					break;
				case 3:
					System.exit(0); 
					break;
				}
			}
		}
		else if(gp.ui.titleSubState== 1) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.titleSubState = 0;
			}
		}
	}
	public void playState(int code) {

		
		if(gp.ui.playSubState == 0) {	// PLAYING GAME
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.pauseState;
			}
			if(code == KeyEvent.VK_T) {
		 		talkPressed = true;
			}
			if(code == KeyEvent.VK_E) {
				openPressed = true;
			}
			if(code == MouseEvent.BUTTON1) {
				attackPressed = true;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			if(code == KeyEvent.VK_C) {
				gp.gameState = gp.ui.playSubState = 1;
			}
			if(code == KeyEvent.VK_I || code == KeyEvent.VK_TAB) { //INVENTORY
				 gp.ui.playSubState = 2;
			}
			if(code == KeyEvent.VK_H) {
				gp.eHandler.heal();
			}
			if(code == KeyEvent.VK_R) {
				shootSpell = true;
			}
			if(code == KeyEvent.VK_M) {
				gp.gameState = gp.mapState;
			}
			if(code == KeyEvent.VK_L) {
				if(gp.map.miniMapOn) {
					gp.map.miniMapOn = false;
				}
				else {
					gp.map.miniMapOn = true;
				}
			}
			if(code == KeyEvent.VK_Q) {
				gp.player.guarding = true;
			}
		}
		else if(gp.ui.playSubState == 1) { //Character info
			if(code == KeyEvent.VK_C  || code == KeyEvent.VK_ESCAPE) 
				gp.ui.playSubState = 0;
		}
		else if(gp.ui.playSubState == 2) { //Inventory
			if(code == KeyEvent.VK_I  || code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_TAB) 
				gp.ui.playSubState = 0;
			if(code == KeyEvent.VK_UP){
				if(gp.ui.slotRow!=0) {
				gp.ui.slotRow--;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_DOWN){
				if(gp.ui.slotRow!=9) {
				gp.ui.slotRow++;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_RIGHT){
				if(gp.ui.slotCol!=20) {
				gp.ui.slotCol++;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_LEFT){
				if(gp.ui.slotCol!=0) {
				gp.ui.slotCol--;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_E) {
				gp.player.selectItem();
			}
			if(code == KeyEvent.VK_F) {
				if(showDesc == true) {
					showDesc = false;
					gp.ui.pauseSubState = 0;
				}
				else if(showDesc == false) {
					showDesc = true;
					gp.gameState = gp.pauseState;
					gp.ui.pauseSubState = 1;
				}
				
			}
		}
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
	}
	public void pauseState(int code) {
		if (gp.ui.pauseSubState == 0) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
				gp.ui.pauseSubState = 0;
				gp.ui.optionsSubState = 0;
				gp.ui.commandNum = 0;
			}
			if(code == KeyEvent.VK_ENTER || code == MouseEvent.BUTTON1) {
				enterPressed = true;
			}
			int maxCommandNum = 4;

				if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
					if(gp.ui.optionsSubState == 0) {
						if(gp.music.volumeScale > 0 && gp.ui.commandNum == 1) {
							gp.music.volumeScale--;
							gp.music.checkVolume();
							gp.playSE(8);
						}
						else if(gp.se.volumeScale>0 && gp.ui.commandNum == 2)  {
							gp.se.volumeScale--;
							gp.playSE(8);
						}
					}
					else if(gp.ui.optionsSubState == 1) {
						gp.ui.commandNum = 0;
					}
				}
				if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
					if(gp.ui.optionsSubState == 0) {
						if(gp.music.volumeScale<5 && gp.ui.commandNum == 1)  {
							gp.music.volumeScale++;
							gp.music.checkVolume();
							gp.playSE(8);
						}
						if(gp.se.volumeScale<5 && gp.ui.commandNum == 2)  {
							gp.se.volumeScale++;  
							gp.playSE(8);
						}
					}
					else if(gp.ui.optionsSubState == 1) {
						gp.ui.commandNum = 1;
					}
				}
		
			if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				gp.playSE(8);
				if(gp.ui.commandNum > maxCommandNum) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_UP|| code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				gp.playSE(8);
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = maxCommandNum ;
				}
			}
		}
		if (gp.ui.pauseSubState == 1) {
			if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_F ) {
				gp.gameState = gp.playState;
				gp.ui.pauseSubState = 0;
				showDesc=false;
			}
		}
	}
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
				// PRESS ENTER TO CONTINUE CONVERSATION 
			if(gp.ui.talkWorld){
				gp.gameState = gp.playState;
				gp.ui.talkWorld = false;
				gp.ui.talkNPC = false;
				} else {
					Entity.dialogueIndex++;
					if(Entity.dialogueIndex >= Entity.currentDialogue.length) {
						Entity.dialogueIndex = 0;
						gp.gameState = gp.playState;
						gp.ui.talkWorld = false;
						gp.ui.talkNPC = false;
					}
				}
			}
	}
	public void gameOverState(int code) {
		int maxCommandNum = 1;
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(8);
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_UP|| code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(8);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum ;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry(); 
				gp.playMusic(0);
			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.restart();
			}
		}
	}
	public void tradeState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(gp.ui.tradeSubState == 0) {
			int maxCommandNum = 2;
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
				if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
					gp.ui.commandNum--;
					gp.playSE(8);
					if(gp.ui.commandNum < 0) {
						gp.ui.commandNum = maxCommandNum;
					}
				}
				if(code == KeyEvent.VK_RIGHT|| code == KeyEvent.VK_D) {
					gp.ui.commandNum++;
					gp.playSE(8);
					if(gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0 ;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					switch(gp.ui.commandNum) {
						case 0: gp.ui.tradeSubState = 1; break;
						case 1: gp.ui.tradeSubState = 2; break;
						case 2: gp.gameState = gp.playState; break;
					}
					enterPressed = false;
				} 
				
		}
		if(gp.ui.tradeSubState == 1 || gp.ui.tradeSubState == 2) { //IF BUYING OR SELLING
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.tradeSubState = 0;
			}
		}
		if(gp.ui.tradeSubState == 1) { // SELL
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W){
				if(gp.ui.slotRow!=0) {
				gp.ui.slotRow--;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){
				if(gp.ui.slotRow < 9)
				gp.ui.slotRow++;
				showDesc = false;
				gp.playSE(8);
			}
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){
				if(gp.ui.slotCol < 20) {
				gp.ui.slotCol++;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){
				if(gp.ui.slotCol > 0) {
				gp.ui.slotCol--;
				showDesc = false;
				gp.playSE(8);
				}
			}
		}
		if(gp.ui.tradeSubState == 2) { // BUY 
			if(code == KeyEvent.VK_F) {
				gp.ui.tradeSubState = 3;
			}
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W){
				if(gp.ui.slotRow!=0) {
				gp.ui.slotRow--;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){
				if(gp.ui.slotRow < 3)
				gp.ui.slotRow++;
				showDesc = false;
				gp.playSE(8);
			}
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){
				if(gp.ui.slotCol < 3) {
				gp.ui.slotCol++;
				showDesc = false;
				gp.playSE(8);
				}
			}
			if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){
				if(gp.ui.slotCol > 0) {
				gp.ui.slotCol--;
				showDesc = false;
				gp.playSE(8);
				}
			}
		}
		if(gp.ui.tradeSubState == 3) { // BUY ITEM DESCRIPTION
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.tradeSubState = 2;
			}
		}
		
	}
	public void mapState(int code) {
		 
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_R) {
			shootSpell = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if(code == KeyEvent.VK_R) {
			openPressed = false;
		}
		if(code == KeyEvent.VK_T) {
			talkPressed = false;
		}
		if(code == KeyEvent.VK_Q) {
			gp.player.guarding = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(gp.gameState == gp.titleState) {
			titleState(e.getButton());
		}
		else if(gp.gameState == gp.playState) {
			playState(e.getButton());
		}
		else if(gp.gameState == gp.pauseState) {
			pauseState(e.getButton());
		}
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(e.getButton());
		}
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(e.getButton());
		}
		else if(gp.gameState == gp.tradeState) {
			tradeState(e.getButton());
		}
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		int mouseX = e.getX();
//	    int mouseY = e.getY();
//	    updateCursorHighlight(mouseX, mouseY);
	}
//	private void updateCursorHighlight(int mouseX, int mouseY) {
//	    // Logic to move cursor highlight based on mouse position
//	    // Update the cursor's row and column based on the mouse position
//	    int slotCol = (mouseX - gp.ui.slotXStart) / gp.tileSize;
//	    int slotRow = (mouseY - gp.ui.slotYStart) / gp.tileSize;
//
//	    if (slotCol >= 0 && slotCol < gp.ui.cols && slotRow >= 0 && slotRow < gp.ui.rows) {
//	        gp.ui.slotCol = slotCol;
//	        gp.ui.slotRow = slotRow;
//	    }
//	}

}
