package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class KeyHandler implements KeyListener,MouseListener, MouseMotionListener{
	public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed,continueConversation,attackPressed,showDesc; 
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
			if(code == KeyEvent.VK_ENTER) {
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
		
		if(gp.ui.playSubState == 0) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.pauseState;
			}
			if(code == KeyEvent.VK_T) {
		 		talkPressed = true;
			}
			if(code == KeyEvent.VK_C) {
				gp.gameState = gp.ui.playSubState = 1;
			}
			if(code == KeyEvent.VK_I) {
				 gp.ui.playSubState = 2;
			}
			if(code == KeyEvent.VK_Q) {
				gp.eHandler.heal();
			}
		}
		else if(gp.ui.playSubState == 1) { //Character info
			if(code == KeyEvent.VK_C  || code == KeyEvent.VK_ESCAPE) 
				gp.ui.playSubState = 0;
		}
		else if(gp.ui.playSubState == 2) { //Inventory
			if(code == KeyEvent.VK_I  || code == KeyEvent.VK_ESCAPE) 
				gp.ui.playSubState = 0;
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT)
				showDesc = false;
				gp.playSE(8);
			if(code == KeyEvent.VK_UP){
				if(gp.ui.slotRow!=0)
				gp.ui.slotRow--;
			}
			if(code == KeyEvent.VK_DOWN){
				if(gp.ui.slotRow!=11)
				gp.ui.slotRow++;
			}
			if(code == KeyEvent.VK_RIGHT){
				gp.ui.slotCol++;
			}
			if(code == KeyEvent.VK_LEFT){
				gp.ui.slotCol--;
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
			continueConversation = true;
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
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(gp.gameState == gp.playState) {
			if (e.getButton() == MouseEvent.BUTTON1)
				attackPressed = true;
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
		int mouseX = e.getX();
	    int mouseY = e.getY();
	    updateCursorHighlight(mouseX, mouseY);
	}
	private void updateCursorHighlight(int mouseX, int mouseY) {
	    // Logic to move cursor highlight based on mouse position
	    // Update the cursor's row and column based on the mouse position
	    int slotCol = (mouseX - gp.ui.slotXStart) / gp.tileSize;
	    int slotRow = (mouseY - gp.ui.slotYStart) / gp.tileSize;

	    if (slotCol >= 0 && slotCol < gp.ui.cols && slotRow >= 0 && slotRow < gp.ui.rows) {
	        gp.ui.slotCol = slotCol;
	        gp.ui.slotRow = slotRow;
	    }
	}

}
