package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener,MouseListener{
	public boolean upPressed,downPressed,leftPressed,rightPressed,talkPressed,continueConversation,attackPressed; 
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
			if(code == KeyEvent.VK_I) {
				gp.gameState = gp.ui.playSubState = 1;
			}
		}
		else if(gp.ui.playSubState == 1) {
			if(code == KeyEvent.VK_I  || code == KeyEvent.VK_ESCAPE) 
				gp.ui.playSubState = 0;
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
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
			
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

}
