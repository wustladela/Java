package cse245ALab2;
/*
 @authors: Adam Sandor, Adela Gao
 @course: cse245A, wustl
 *
 *An algorithm implementing Austin's Algorithm and adjusted winner, concepts in Fair Division theories; test case: partition plan for Israeli and Palestine conflict.
 
 */
import java.util.ArrayList;

public class AustinAlgo {

	int cellCut = 0;
	ArrayList<Double> p2Slice, p1Slice = new ArrayList<Double>();



		public Preferences randomCut(ArrayList<Double> dataP1, ArrayList<Double> dataP2) {
			double p1Sum = 0, p2Sum = 0;
			for (int c = 0; c < dataP1.size(); ++c) {
				p1Sum += dataP1.get(c);
				p2Sum += dataP2.get(c);
				if (Math.abs(p1Sum-0.5)<0.05) { //p1 calls stop first 
					cellCut = c;
					p1StopFirst = true;
				}
				//call otehr method
				if ( Math.abs(p2Sum-0.5) < 0.05) { //p2 calls stop first
					cellCut = c;
					p1StopFirst = false;
				}
				//call other method
			}
		}


		public Preferences 2Knives(int cellCut, ArrayList<Double> dataP1, ArrayList<Double> dataP2) {
			Knife k1 = new Knife(cellCut, dataP1.size());
			Knife k2 = new Knife(cellCut, dataP2.size());
			if (p1StopFirst) {
				//use player2's data to determine where the best slice for them is. 
				
				while () {
					
				}
			}
			else {
				
			}
		}

		
		public Preferences rand2ndKnife(ArrayList<Double> dataP1, ArrayList<Double> dataP2, int cellCut) {
			double innerSlice =0, outerSlice = 0;
			double p1Sum = 0, p2Sum = 0;
			int secondKnife = 0;
			int firstKnife = cellCut;
			double maxP2 = 0;
			if (p1StopFirst) {
				while ((secondKnife<dataP1.size()) || (secondKnife==dataP1.size())){
					//check if the second knife is back to beginn
	
	
	
	
					if(firstKnife>secondKnife){//while the first knife is still on the right side of second knife
	
	
						//before start moving the second knife
						for (int i=secondKnife; i<firstKnife; ++i){
							double lastStep = innerSlice;
							innerSlice+=dataP2.get(i);
							if(lastStep>innerSlice){
								maxP2=lastStep;//the first local maximum for P2
	
							}
	
						}
						++firstKnife;
						++secondKnife;
					}
					else{
						//now inner slice becomes outer slice
						//for the right side of the cake
						for(int i=secondKnife; i<dataP2.size();++i){
							double lastStep = innerSlice;
							innerSlice +=dataP2.get(i);
						}
						++secondKnife;
	
					}
	
					//for the left part of the "innerSlice"
					for(int i=0; i<firstKnife;++i){
						double lastStep = innerSlice;
						innerSlice +=dataP2.get(i);
						if(lastStep>innerSlice){
							maxP2=lastStep;//the first local maximum for P2
	
						}
						++firstKnife;	
					}
	
	
	
	
				}
			}
	
	
	
		}


	public static int insertKnife2(int size, ArrayList<Double> data) {
		double tot = 0;
		int call2ndK = 0;
		for(int c = 0; c < size; ++c) {
			tot += data.get(c);
			if (Math.abs(tot - 0.5) < 0.05) {
				call2ndK = c;
				break;
			}
		}
		return call2ndK;
	}

	public static boolean p1StopFirst(ArrayList<Double> dataP1, ArrayList<Double> dataP2) {
		double p1Tot = 0, p2Tot = 0;
		for (int c = 0; c < dataP1.size(); ++c) {
			p1Tot += dataP1.get(c);
			p2Tot += dataP2.get(c);
			if (Math.abs(p1Tot - 0.5) < 0.05) {
				return true;
			}
			if (Math.abs(p2Tot - 0.5) < 0.05) {
				return false;
			}
		}
		System.out.println("Reached the supposedly unreachable part of determineing if p1stopfirst");
		return true; 
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

		ReadDavesData d = new ReadDavesData();
		double[][] datp1 = d.getData("Data/randp1lab2.csv", 100, 100);
		ArrayList<Double> dataP1 = d.makeArrayList(datp1);
		double[][] datp2 = d.getData("Data/randp2lab2.csv", 100, 100);
		ArrayList<Double> dataP2 = d.makeArrayList(datp2);
		boolean p1StopFirst = p1StopFirst(dataP1, dataP2);

		int loc1stK = 0;
		if (p1StopFirst) {
			loc1stK = insertKnife2(dataP1.size(), dataP1);
		}
		else {
			loc1stK = insertKnife2(dataP2.size(), dataP2);
		}
		Knife k1 = new Knife(loc1stK, dataP1.size());
		Knife k2 = new Knife(0, dataP1.size());
		KnivesMover km = new KnivesMover(k1, k2, dataP1, dataP2, p1StopFirst);
		ArrayList<Integer> maxSliceBetweenThese = km.findMaxSlice();
		double prefTot = 0;
		if (p1StopFirst) {
			prefTot = km.getPref(maxSliceBetweenThese.get(0), maxSliceBetweenThese.get(1), dataP2);
		}
		else {
			prefTot = km.getPref(maxSliceBetweenThese.get(0), maxSliceBetweenThese.get(1), dataP1);
		}

		MaxPrefSlice mps = new MaxPrefSlice(maxSliceBetweenThese.get(0), maxSliceBetweenThese.get(1), prefTot);
		//find pref of other player for the other slices.
		double otherplayerTot = 0;
		if (p1StopFirst) {
			for (int c = 0; c < maxSliceBetweenThese.get(0); ++c) {
				otherplayerTot += dataP2.get(c);
			}
			for (int c = maxSliceBetweenThese.get(1); c < dataP2.size(); ++c) {
				otherplayerTot += dataP2.get(c);
			}
		}
		else {
			for (int c = 0; c < maxSliceBetweenThese.get(0); ++c) {
				otherplayerTot += dataP1.get(c);
			}
			for (int c = maxSliceBetweenThese.get(1); c < dataP2.size(); ++c) {
				otherplayerTot += dataP1.get(c);
			}
		}

		if (p1StopFirst) {
			System.out.println("P1 stopped first");
			System.out.println("P2 gets pref of " + mps.prefTot);
			System.out.println("P1 gets pref of " + otherplayerTot);
		}
		else {
			System.out.println("P2 stopped first"); //true
			System.out.println("P1 gets pref of " + mps.prefTot); //0.5441938703999989
			System.out.println("P2 gets pref of " + otherplayerTot); //0.4558058698500007
		}
		
		
//Israel Palestine test		
		
		ReadDavesData d = new ReadDavesData();
		double[][] datp1 = d.getData("Data/israelilab2.csv", 1000, 1000);
		ArrayList<Double> dataP1 = d.makeArrayList(datp1);
		double[][] datp2 = d.getData("Data/palestinianlab2.csv", 1000, 1000);
		ArrayList<Double> dataP2 = d.makeArrayList(datp2);
		boolean p1StopFirst = p1StopFirst(dataP1, dataP2);
		System.out.println("Data made 217");
		int loc1stK = 0;
		if (p1StopFirst) {
			loc1stK = insertKnife2(dataP1.size(), dataP1);
		}
		else {
			loc1stK = insertKnife2(dataP2.size(), dataP2);
		}
		System.out.println("p1StopFirst made 225");
		Knife k1 = new Knife(loc1stK, dataP1.size());
		Knife k2 = new Knife(0, dataP1.size());
		KnivesMover km = new KnivesMover(k1, k2, dataP1, dataP2, p1StopFirst);
		ArrayList<Integer> maxSliceBetweenThese = km.findMaxSlice();
		System.out.println("Made knives and found maxslice 230");
		double prefTot = 0;
		if (p1StopFirst) {
			prefTot = km.getPref(maxSliceBetweenThese.get(0), maxSliceBetweenThese.get(1), dataP2);
		}
		else {
			prefTot = km.getPref(maxSliceBetweenThese.get(0), maxSliceBetweenThese.get(1), dataP1);
		}
		System.out.println("found prefTot 238");

		MaxPrefSlice mps = new MaxPrefSlice(maxSliceBetweenThese.get(0), maxSliceBetweenThese.get(1), prefTot);
		System.out.println("Made mps 241");
		//find pref of other player for the other slices.
		double otherplayerTot = 0;
		if (p1StopFirst) {
			for (int c = 0; c < maxSliceBetweenThese.get(0); ++c) {
				otherplayerTot += dataP2.get(c);
			}
			for (int c = maxSliceBetweenThese.get(1); c < dataP2.size(); ++c) {
				otherplayerTot += dataP2.get(c);
			}
		}
		else {
			for (int c = 0; c < maxSliceBetweenThese.get(0); ++c) {
				otherplayerTot += dataP1.get(c);
			}
			for (int c = maxSliceBetweenThese.get(1); c < dataP2.size(); ++c) {
				otherplayerTot += dataP1.get(c);
			}
		}
		System.out.println("otherplayerTot made 260");
		
		if (p1StopFirst) {
			//for getting the first 200,000 cells in the data sheet
			System.out.println("Israel stopped first"); //true
			System.out.println("Palestine gets pref of " + mps.prefTot); //0.5348632719994282
			System.out.println("Israel gets pref of " + otherplayerTot); //0.46512149999856783
		}
		else {
			System.out.println("Palestine stopped first"); 
			System.out.println("Israel gets pref of " + mps.prefTot); 
			System.out.println("Palestine gets pref of " + otherplayerTot); 
		}
		
		
	}

}
	
