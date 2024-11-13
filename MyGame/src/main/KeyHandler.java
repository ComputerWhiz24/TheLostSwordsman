package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed,continueConversation; 
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
		
		else if(gp.gameState == gp.playState) {
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
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.pauseState;
			}
			if(code == KeyEvent.VK_T) {
		 		talkPressed = true;
			}
		}
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
				
			}
		}
		else if(gp.gameState == gp.dialogueState) {
			
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
			if(code == KeyEvent.VK_ENTER) {
				continueConversation = true;
			}
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

}
