package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s = br.readLine();
			
			// FULLSCREEN
			
			if(s.equalsIgnoreCase("Fullscreen on")) {
				gp.fullScreen = true;
			} else {
				gp.fullScreen = false;
			}
		
			// MUSIC VOLUME
			s= br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			
			// SOUND EFFECT VOLUME
			s= br.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
