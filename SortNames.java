
/**
 * @author Adela Gao
 * SortNames.java
 * This file reads text files from input arguments (each text file has one string on each line; if string is not purely alphabetical, it is skipped)
 * If the strings in the file are not sorted, it will detect and print a message
 * After reading all the files, it merges all strings and sort them (using merge sort)
 * Multithreaded for performance;
 * limited file size and string length by an arbituary number that can be changed according to execution machines
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SortNames {
	
	public static int numArgInvalid = -1;//invalid inputs, such as wrong number of arguments and/or too many arguments
	public static int fileNotFound = -2;
	public static int numTask;
	public static int numFinished;
	public static ArrayList<String> allFiles;// an arraylist of strings from all the files
	public static long startTime;
	public static int tooManyThreads = 20000;//can change according to what machine (machine specs) you're running this on. 
	public static int tooLongString = Integer.MAX_VALUE/100;//can be changed according to machine specs for best performance

	public static void main(String[] args) {
		System.out.println("Welcome to the Sort Text program!");
		
		// check arguments whether they are valid; check for number of arguments
		if(args.length>tooManyThreads){
			System.out.println("Please limit the number of files to process within "+tooManyThreads);
			System.exit(numArgInvalid);
		}
		
		if (args.length < 1) {
			System.out.println("Please run this program with path to filenames as arguments. For example, java SortNames path/to/sample.txt");
			System.exit(numArgInvalid);
		}
		Set<String> fileSet = new HashSet<String>(Arrays.asList(args));//prevent duplicate file names
		String[] filteredFiles = fileSet.toArray(new String[(fileSet.size())]);//convert back to array for index access
		numTask = filteredFiles.length;
		allFiles = new ArrayList<String>();//will contain the texts from all files
		startTime = System.currentTimeMillis();
		
		//run threads
		for (int i = 0; i < filteredFiles.length; i++) {
			int currentIndex = i;
			new Thread(new Runnable() {
				public void run() {
					try {
						ArrayList<String> temp = readFile(filteredFiles[currentIndex]);
						if (!isSorted(temp)) {
							System.out.println("Input file "
									+ filteredFiles[currentIndex] + " is not sorted.");
						}
						allFiles.addAll(temp);
						madeProgress();
					} catch (Exception e) {
						System.out.println("51: reading file error: file not found "+filteredFiles[currentIndex]);
						madeProgress();
					}

				}
			}).start();
			
		}
	}
	
	//record progress; if finished all tasks, then process final result
	public synchronized static void madeProgress() {
		numFinished++;
		if (numFinished ==numTask) {
			finalSortResult(allFiles);
		}
	}
	//process final result
	public static void finalSortResult(ArrayList<String> myArray) {
		mergeSort(myArray);
		//System.out.println("Final sorted result: "+myArray);
		System.out.println("=========================================");
		System.out.println("Final result displayed with one piece of string in each line: ");
		for(int i=0; i<myArray.size(); i++){
			System.out.println(myArray.get(i));
		}
		System.out.println("=================end of sorted result===================");
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Total elapsed time: " + elapsedTime);
	}

	// read file into lines; check file length.
	public static ArrayList<String> readFile(String filename) {
		ArrayList<String> lines = new ArrayList<String>();
		//String regex = "^[a-zA-Z]+$";// str.matched(regex) does not work for some reason.		
		Pattern p = Pattern.compile("^[a-zA-Z]+$");

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));//default buffer size will handle file size
			Scanner s = new Scanner(br);//better at parsing
			while (s.hasNextLine()) {
				String currLine = s.nextLine();
				Matcher m = p.matcher(currLine);
				if(currLine.length()<tooLongString && m.find()){//m.find is matched regex
					lines.add(currLine);
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid filepath: "+filename);
			System.exit(fileNotFound);
		}
		return lines;
	}

	// check if they are sorted
	public static boolean isSorted(ArrayList<String> list) {
		if (list.size() == 0) {//no valid strings
			return false;
		}
		
		// compare the first character of each line
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).compareToIgnoreCase(list.get(i + 1)) > 0) {
				return false;
			}
		}
		return true;
	}
	// merge sort
	public static void mergeSort(ArrayList<String> toSort) {
		mergeSort(toSort, 0, toSort.size() - 1);
	}
	//recursive call
	public static void mergeSort(ArrayList<String> toSort, int left, int right) {
		if (left >= right) {
			return;
		}
		int middle = (left + right) / 2;
		mergeSort(toSort, left, middle);
		mergeSort(toSort, middle + 1, right);
		ArrayList<String> ans = new ArrayList<String>();
		int lo = left;
		int mid = middle + 1;
		int i = 0;
		while (lo <= middle && mid <= right) {
			if(toSort.get(lo).compareToIgnoreCase(toSort.get(mid))<=0){
				ans.add(toSort.get(lo));
				lo++;
			}
			else {
				ans.add(toSort.get(mid));
				mid++;
			}
		}
		while (lo <= middle) {
			ans.add(toSort.get(lo));
			lo++;
		}
		while (mid <= right) {
			ans.add(toSort.get(mid));
			mid++;
		}
		i = left;
		for (int j = 0; j < ans.size(); j++) {
			toSort.set(i, ans.get(j));
			i++;
		}
	}
}
