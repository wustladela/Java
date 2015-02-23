package recursivepatterns;
import java.awt.Color;
import sedgewick.*;
//Name: Adela Gao
//Course: cse131, wustl
//Class description: Create Persian Rug patterns recursively.

public class PersianRug {

	/**
	 * 
	 * @param palette an array of Colors to choose from
	 * @param llx lower left x coordinate of this rug square
	 * @param lly lower left y coordinate of this rug square
	 * @param size width (and therefore also height) of this rug square
	 * @param north color index of the north side of this rug square
	 * @param east color index of the east side of this rug square
	 * @param south color index of the south side of this rug square
	 * @param west color index of the west side of this rug square
	 */
	private static void persianRug(
			Color[] palette, 
			double llx, double lly,
			double size, 
			int north, int east, int south, int west) {
		// FIXME Your code goes here

		if (size<0.006) return; 
		else {	
			int sum = 5+2*(north+east+south+west);
			int product = north*east*south*west;
			int index = (int)(sum%11.0);


			//if (sum==40) index = 10;//10white, 8pink, 7orange; 
			//	if (sum==34) index = 7;
			//	if (sum==36) index = 8;
			//	else index = 10;
			Color penColor = palette[index];
			StdDraw.setPenColor(penColor);
			StdDraw.line(llx+size/2, lly, llx+size/2, lly+size); //vertical line
			StdDraw.line(llx, lly+size/2, llx+size, lly+size/2); //horizontal line
			persianRug(palette,llx,lly,size/2,index,index,south,west);
			persianRug(palette,0.5*size+llx,lly,size/2,index,east,south,index);
			persianRug(palette,llx,0.5*size+lly,size/2,north,index,index,west);
			persianRug(palette,0.5*size+llx,0.5*size+lly,size/2,north,east,index,index);

		}
	}	



	public static void main(String args[]) {
		//
		// Generate a palette of colors
		//
		Color[] palette = { StdDraw.BLUE, StdDraw.CYAN, StdDraw.DARK_GRAY,
				StdDraw.GRAY, StdDraw.GREEN, StdDraw.LIGHT_GRAY,
				StdDraw.MAGENTA, StdDraw.ORANGE, StdDraw.PINK, StdDraw.RED,
				StdDraw.WHITE, StdDraw.YELLOW };
		//
		// Draw the outermost square as a special case
		// Use color 0 for that
		//
		StdDraw.setPenColor(palette[0]);
		StdDraw.line(0, 0, 1, 0);
		StdDraw.line(1, 0, 1, 1);
		StdDraw.line(1, 1, 0, 1);
		StdDraw.line(0, 1, 0, 0);

		//
		// Kick off the recursion
		// Lower left is point (0,0)
		// Size of the square is 1
		// The color index of each surrounding side is 0
		//

		persianRug(palette, 0, 0, 1, 0, 0, 0, 0);
	}

}
