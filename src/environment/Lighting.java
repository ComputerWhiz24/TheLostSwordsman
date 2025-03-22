package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	
	public Lighting(GamePanel gp, int circleSize) {
		this.gp = gp;
		
		// CREATE BUFFERED IMAGE
		darknessFilter = new BufferedImage(gp.screenWidth,gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		// SCREEN SIZED RECTANGLE
		Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.screenWidth, gp.screenHeight)); 
		
		// GET CENTER X AND Y OF THE CIRCLE OF LIGHT
		int centerX = gp.player.screenX + (gp.tileSize / 2);
		int centerY = gp.player.screenY + (gp.tileSize / 2);
		
		// GET TOP LEFT X AND Y OF CIRCLE OF LIGHT
		double x = centerX - (circleSize / 2);
		double y = centerY - (circleSize / 2);
		
		// CIRCLE SHAPE
		Shape circleShape = new Ellipse2D.Double(x,y,circleSize,circleSize);
		
		// CREATE CIRCLE OF LIGHT AREA
		Area lightArea = new Area(circleShape);
		
		// SUBTRACT LIGHT AREA FROM SCREEN RECTANGLE
		screenArea.subtract(lightArea);
		
		g2.setColor(new Color(0,0,0,0.95f));
		g2.fill(screenArea);
		
		g2.dispose();
	}
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
	}
}
