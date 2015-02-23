package cse245ALab2;
/*
 @authors: Adam Sanfor, Adela Gao
 *One of the helper classes for the Austin's Algorithm
 */
import java.util.ArrayList;

public class KnivesMover {


	Knife k1, k2;
	ArrayList<Double> dataP1, dataP2;
	boolean p1StopFirst;
	double p1Tot, p2Tot = 0;
	int initialLocK1;
	MaxPrefSlice maxPref;
	double newTot;

	public KnivesMover(Knife k1, Knife k2, ArrayList<Double> dataP1,
			ArrayList<Double> dataP2, boolean p1StopFirst) {
		super();
		this.k1 = k1;
		this.k2 = k2;
		this.dataP1 = dataP1;
		this.dataP2 = dataP2;
		this.p1StopFirst = p1StopFirst;
		initialLocK1 = k1.cellCut;
	}

	public ArrayList<Integer> findMaxSlice() { //still need a way to find the max pref for the other player calling the second stop.
		//get current prefs.
		for (int c = 0; c < k1.cellCut; ++c) {
			p1Tot += dataP1.get(c);
			p2Tot += dataP2.get(c);
		}

		maxPref = new MaxPrefSlice(0, 0, 0);
		newTot = 0;
		int counter = 0;
		System.out.println("before while loop in findMaxSlice");
		while (counter < 200000) {
			if (k1.cellCut > k2.cellCut) {
				k2beforek1Move();
				
			}
			else {
				k1beforek2Move();
				
			}
			counter++;
			System.out.println(counter);
		}


		ArrayList<Integer> maxSliceLocs = new ArrayList<Integer>(); //max slice always calculateed between the knives.
		maxSliceLocs.add(maxPref.start);
		maxSliceLocs.add(maxPref.end);
		return maxSliceLocs;
	}

	
	
	
	public void k1beforek2Move() {
		k2.moveKnife();
		if (p1StopFirst) {	
			if (getPref(k1.cellCut, k2.cellCut, dataP1) > p1Tot) {
				k1.moveKnife();
				newTot = getPref(k1.cellCut, k2.cellCut, dataP2);
				p2Tot = newTot;
				if (newTot > maxPref.prefTot) {
					maxPref.makeNewSlice(k2.cellCut, k1.cellCut, newTot);
				}
			}
		}
		else {
			if (getPref(k1.cellCut, k2.cellCut, dataP2) > p2Tot) {
				k1.moveKnife();
				newTot = getPref(k1.cellCut, k2.cellCut, dataP1);
				p1Tot = newTot;
				if (newTot > maxPref.prefTot) {
					maxPref.makeNewSlice(k1.cellCut, k2.cellCut, newTot);
				}
			}
		}
		
	}
	
	
	public void k2beforek1Move() {
		k1.moveKnife();
		if (p1StopFirst) {	
			if (getPref(k2.cellCut, k1.cellCut, dataP1) > p1Tot) {
				k2.moveKnife();
				newTot = getPref(k2.cellCut, k1.cellCut, dataP1);
				p2Tot = newTot;
				if (newTot > maxPref.prefTot) {
					maxPref.makeNewSlice(k2.cellCut, k1.cellCut, newTot);
				}
			}
		}
		else {
			if (getPref(k2.cellCut, k1.cellCut, dataP2) > p2Tot) {
				k2.moveKnife();
				newTot = getPref(k2.cellCut, k1.cellCut, dataP2);
				p1Tot = newTot;
				if (newTot > maxPref.prefTot) {
					maxPref.makeNewSlice(k2.cellCut, k1.cellCut, newTot);
				}
			}
		}
	}
	
	
	
	public double getPref(int start , int end, ArrayList<Double> data) {
		double tot = 0;
		for (int c = start; c < end; ++c) {
			tot += data.get(c);
		}
		return tot;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub


		
		
		
		
	}

}
