
package lab4;
import java.util.Scanner;


import cse131.ArgsProcessor;
import sedgewick.*;
import java.io.*;
// Name: Adela Gao
// Class description: Simulate multiple bouncing balls (with different speeds) together.

public class Bouncingballs {
	public static void main(String[] args) throws Exception {
		// FIXME Auto-generated method stub
		/**
		 * double rx[],ry[],vx[],vy[]: Create 4 1D arrays to describe 4 parameters of a ball (x and y position; x and y velocity coordinate)
	     * length of array is number of balls +1 because: create an extra ball outside the coordinate system; 
	     * this extra ball is array bounds - to avoid out of bound error
		 */		
        //ArgsProcessor ap = new ArgsProcessor(args);
        //int numberOfBalls = ap.nextInt("Please put the number of balls");
        
        
        System.out.println("Please put the number of bouncing balls");
        Scanner scanner = new Scanner(System.in);
        //String a = scanner.nextLine();
        int b = scanner.nextInt();
        int numberOfBalls = b;
        double radius = 0.03;
        double rx[] = new double [numberOfBalls+1]; //this is the length of array! +1 for easier comparison of the last ball
        double ry[] = new double [numberOfBalls+1];
        double vx[] = new double [numberOfBalls+1];
        double vy[] = new double [numberOfBalls+1];
        
        // set the scale of the coordinate system:
        StdDraw.setXscale(-1.0, 1.0); 
        StdDraw.setYscale(-1.0, 1.0);
        
        //initial values:
        rx[numberOfBalls]= -2;
        ry[numberOfBalls]= -2;
        vx[numberOfBalls]=0;
        vy[numberOfBalls]=0;     		
        //initialize random position and velocity for every ball
        for (int i=0; i<numberOfBalls; i++) {
        	rx[i] = Math.random();
        	ry[i] = Math.random();
        	vx[i] = 0.05*Math.random();
        	vy[i] = 0.05*Math.random();
        }     
        	
        //animation loop
        while (true) {
        	StdDraw.show(20);//pause for 20 ms for each display
            StdDraw.clear(); //clear the previous display
        	for (int i=0; i<numberOfBalls; i++) {   		 
        		// draw ball on the screen:
        		StdDraw.setPenColor(StdDraw.BLACK); 
            	StdDraw.filledCircle(rx[i], ry[i], radius);           	
            	//bounce off wall:
            	if (Math.abs(rx[i] + vx[i]) > 1.1 - radius) vx[i]=-vx[i];
            	if (Math.abs(ry[i] + vy[i]) > 1.1 - radius) vy[i]=-vy[i];
            	//bounce off each other:
            	for (int j=0; j<numberOfBalls && j!=i; j++) { //for all balls to compare with one another
            		if (Math.sqrt((rx[i]-rx[j])*(rx[i]-rx[j])+(ry[i]-ry[j])*(ry[i]-ry[j]))<=2*radius) {	
            		vx[i]=-1.0*vx[i];
            		vy[i]=-1.0*vy[i];
            		vx[j]=-1.0*vx[j];
            		vy[j]=-1.0*vy[j];        		
            		}          	
            	}
            	// update position
            	rx[i] = rx[i] + vx[i];
            	ry[i] = ry[i] + vy[i];		 		
        }	
        }  
	}

}
