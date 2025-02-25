package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			// FULLSCREEN SETTING
			if(gp.fullScreen) {
				bw.write("Fullscreen on");
			} else {
				bw.write("Fullscreen off");
			}
			bw.newLine();
			
			// MUSIC VOLUME
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			// SOUND EFFECT VOLUME
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		
		BufferedReader br = new 
	}
}
