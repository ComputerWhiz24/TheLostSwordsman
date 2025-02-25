package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		//MyGame official setup
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.config.loadConfig();
		if(gamePanel.fullScreen) {
			// add logic to make full screen 
		}
		
		window.pack();
		
		window.setTitle("2D Adventure");
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
