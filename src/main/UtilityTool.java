package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {

	public BufferedImage scaledImage(BufferedImage original, int width, int height ) {
		 if (width <= 0 || height <= 0) {
		       System.err.println("Invalid width or height for scaled image: Width=" + width + ", Height=" + height);
		        return original; // Return the original image as a fallback
		   }
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height ,null);
		g2.dispose();
		
		return scaledImage;
	}
			
}
