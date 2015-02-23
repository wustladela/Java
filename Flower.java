package recursivepatterns;
//Name:Adela Gao

//Class description: Create infinite number of random colored flower petals using recursion.
import java.awt.Color;

import sedgewick.StdDraw;


public class Flower {
	
	/**
	 * 
	 * @param x x coordinate of the center of this ellipse
	 * @param y y coordinate of the center of this ellipse
	 * @param halfWidth half the width of this ellipse
	 * @param halfHeight half the height of this ellipse
	 * @param count depth of the recursion, initial call passes 0
	 */
	private static void flower(Color[] palette, double x, double y, double halfWidth, double halfHeight, int count){
		//
		// FIXME Your code below here
		//
		//StdDraw.setCanvasSize((int)(halfWidth*2), (int)(halfHeight*2));
		if (count==5) return;
		else {
		
		Color penColor = palette[(int)(Math.random()*12)];//get random color
		StdDraw.setPenColor(penColor);
		//StdDraw.filledEllipse(x, y, halfWidth, halfHeight);//draw the biggest ellipse background
		StdDraw.filledEllipse(x, y, halfWidth/2.0, halfHeight/2.0);//the first recursion;middle ellipse
		
		penColor = palette[(int)(Math.random()*12)];
		StdDraw.setPenColor(penColor);
		StdDraw.filledEllipse(x-halfWidth/2.0, y, halfWidth/2.0, halfHeight/2.0);//the first left ellipse
		penColor = palette[(int)(Math.random()*12)];
		StdDraw.setPenColor(penColor);
		StdDraw.filledEllipse(x+halfWidth/2.0, y, halfWidth/2.0, halfHeight/2.0);//the first right ellipse
		penColor = palette[(int)(Math.random()*12)];
		StdDraw.setPenColor(penColor);
		StdDraw.filledEllipse(x, y-halfHeight/2.0, halfWidth/2.0, halfHeight/2.0);//the first lower ellipse
		penColor = palette[(int)(Math.random()*12)];
		StdDraw.setPenColor(penColor);
		StdDraw.filledEllipse(x, y+halfHeight/2.0, halfWidth/2.0, halfHeight/2.0);//the first upper ellipse
		
		//recursion:
		flower(palette, x-halfWidth/2.0, y, halfWidth/2.0, halfHeight/2.0, count+1);//recursion in the middle ellipse: half the size, same spot
		flower(palette, x, y,               halfWidth/2.0, halfHeight/2.0, count+1);//recursion, still in the middle ellipse: 
		flower(palette, x+halfWidth/2.0, y, halfWidth/2.0, halfHeight/2.0, count+1);
		flower(palette, x, y-halfHeight/2.0, halfWidth/2.0, halfHeight/2.0, count+1);
		flower(palette, x, y+halfHeight/2.0, halfWidth/2.0, halfHeight/2.0, count+1);
		
		
		}
	}
	
		
	public static void main(String args[]) {
		//
		// Create a palette of colors
		//
		Color[] palette = { StdDraw.BLACK, StdDraw.BLUE, StdDraw.CYAN,
				StdDraw.DARK_GRAY, StdDraw.GRAY, StdDraw.GREEN,
				StdDraw.LIGHT_GRAY, StdDraw.MAGENTA, StdDraw.ORANGE,
				StdDraw.PINK, StdDraw.RED, StdDraw.WHITE, StdDraw.YELLOW };
		//
		// Modify the palette so each color is somewhat transparent
		//   This allows the colors below to bleed through the colors
		//   on top.
		//
		for (int i=0; i < palette.length; ++i) {
			palette[i] = TransparentColor.transparentColor(palette[i], 70);
		}

		//
		// Kick off the recursion
		// Center is at (0.5, 0.5), half-width .3, half-height .5, depth is 0
		//
		StdDraw.setPenColor(palette[(int)(Math.random()*12)]);
		StdDraw.filledEllipse(.5, .5, .3, .5);
		flower(palette, .5, .5, .3, .5, 0);
	}

}
