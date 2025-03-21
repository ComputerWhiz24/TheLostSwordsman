package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
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
		
		// CREATE A GRADATION EFFECT WITHIN THE LIGHT CIRCLE
		Color color[] = new Color[12];
		float fraction[] = new float[12];
		
		color[0] = new Color(0,0,0,0.1f);
		color[1] = new Color(0,0,0,0.42f);
		color[2] = new Color(0,0,0,0.52f);
		color[3] = new Color(0,0,0,0.61f);
		color[4] = new Color(0,0,0,0.69f);
		color[5] = new Color(0,0,0,0.76f);
		color[6] = new Color(0,0,0,0.82f);
		color[7] = new Color(0,0,0,0.87f);
		color[8] = new Color(0,0,0,0.91f);
		color[9] = new Color(0,0,0,0.94f);
		color[10] = new Color(0,0,0,0.96f);
		color[11] = new Color(0,0,0,0.98f);


		
		fraction[0] = 0f;
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f;
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;

		
		// CREATE GRADATION PAINT SETTINGS FOR LIGHT CIRCLE
		RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,circleSize/2,fraction,color);
		
		// SET GRADIENT DATA ON G2
		g2.setPaint(gPaint);
		
		// DRAW THE LIGHT CIRCLE
		g2.fill(lightArea);
		
//		g2.setColor(new Color(0,0,0,0.95f));
		g2.fill(screenArea);
		
		g2.dispose();
	}
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
	}
}
