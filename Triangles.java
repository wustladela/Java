package lab6;
import cse131.ArgsProcessor;
import sedgewick.*;
//Name: Adela Gao
//course: cse131
//Class description: Create a recursive triangle pattern.


public class Triangles {
	/**
	 * This method draws the triangles recursively as indicated on lab 6 page
	 */	
	public static void draw (int n, double length, double x, double y) { //TA you do a lot of the computations in this meathod more than once
		if (n==0) return;	
		// plot a white triangle, with (x, y) as the center of the equilateral triangle with given side length
	        // compute the coordinates of the 3 tips of the triangle
	        double x0 = x - length/2;
	        double x1 = x;
	        double x2 = x + length/2;
	        double y0 = y + length/(2*1.732);
	        double y1 = y - length/1.732;
	        double y2 = y + length/(2*1.732);
	        // create the arrays for drawing the triangles
	        double tx[] = {x0, x1, x2};
	        double ty[] = {y0, y1, y2};
	        //Draw the triangles recursively
	        StdDraw.setPenColor(StdDraw.WHITE);
	        StdDraw.filledPolygon(tx, ty);  
	        draw(n-1, length/2, x, y+(length/1.732));//draw a triangle half-size on the top
	        draw(n-1, length/2, x-0.866*(length/1.732), y-0.5*(length/1.732));//draw a triangle half-size on the left
	        draw(n-1, length/2, x+0.866*(length/1.732), y-0.5*(length/1.732));//draw a triangle half-size on the right     
	    }

	    public static void main(String[] args) {
	    	ArgsProcessor ap = new ArgsProcessor(args);
	    	int N = 6;
	    	double length = 0.5; // side length of the biggest triangle; this can also be changed into user input
	        double x = 0.5;
	    	double y = 0.5;         
	        //create the array for drawing the background first
	        double []backX = {0, length, 2*length};
	        double []backY = {x-length/1.732, length*2.15, x-length/1.732};
	        //set canvas and begin drawing
	        StdDraw.setXscale(0, 1.1);       
		    StdDraw.setYscale(0, 1.1);
		    //draw background
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.filledPolygon(backX, backY);
	        StdDraw.setPenColor(StdDraw.WHITE);
	        draw(N, x, y, length);//draw the triangles
	            
	    }
	
	}


