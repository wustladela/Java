package kwic;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.beans.PropertyChangeSupport;
import java.io.*;
/**
 * Author: Adela Gao
 * email: gao.adela@wustl.edu
 * Lab: cse132 Lab 2a
 * File: KWIC.java
 * To read words from a file and create a mapping between words and phrases
 */

/** 
 * Key Word in Context
 */

public class KWIC {
	public Map<Word, HashSet<Phrase>> theMap = new HashMap<Word, HashSet<Phrase>>();
	protected PropertyChangeSupport pcs;

	public KWIC() { 
		pcs = (new PropertyChangeSupport(this)); 
	}

	/** 
	 * Required for part (b) of this lab.
	 * Accessor for the {@link PropertyChangeSuppport} 
	 */

	public PropertyChangeSupport getPCS() { return pcs; }

	/** 
	 * Convenient interface, accepts a standrd Java {@link String}
	 * @param s String to be added
	 */
	public void addPhrase(String s) {
		addPhrase(new Phrase(s));
	}
	
	/**
	 * Add each line in the file as a phrase.
	 * For each line in the file, call {@link addPhrase(String)} to
	 *   add the line as a phrase.
	 * @param file the file whose lines should be loaded as phrases
	 * @throws FileNotFoundException 
	 */
	public void addPhrases(File file) {
		//
		// FILL ME IN after you have everything else working
		//
		FileReader fr;
		try {
			fr = new FileReader(file);
			try {
				BufferedReader br = new BufferedReader(fr);
				String read="";
				while((read = br.readLine())!=null){
					addPhrase(read);
				}
			} catch (IOException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// FIXME Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	//add one single word to the Hash map
	public void addWord(Word word){
		if(theMap.containsKey(word)){
			System.out.println("word already exists");
		}
		else{
			theMap.put(word, null);
		}
	}

	/** 
	 * For each {@link Word} in the {@link Phrase}, 
	 * add the {@link Word}
	 * to the association.
	 * Use reduction to {@link #forceAssoc(Word, Phrase)}.
	 * @param p Phrase to be added
	 */
	public void addPhrase(Phrase p) {
		//scrambling here in the for loop! HOW DO I FIX THIS? 
		for (Word w : p.getWords()) {
			forceAssoc(w, p);
		}
		pcs.firePropertyChange("Phrase Added",false, true);
	}


	/** For each word in the {@link Phrase}, delete the association between
      the word and the phrase.
      Use reduction to {@link #dropAssoc(Word, Phrase)}.
	 */
	public void deletePhrase(Phrase p) {
		for (Word w : p.getWords()) {
			dropAssoc(w,p);
//			HashSet<Phrase> toUpdate = theMap.get(w);
//			toUpdate.remove(p.cleanUp(p.phrase));
//			theMap.put(w, toUpdate);
		}
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}

	/** Force a mapping between the specified {@link Word} and {@link Phrase} */
	public void forceAssoc(Word w, Phrase p) {
		//
		// FILL ME IN
		// Leave the following line as the last line of this method
		//
	
		//check if word is in the map already
		if(theMap.containsKey(new Word(w.getMatchWord()))){
				
				HashSet<Phrase> wPhrases = theMap.get(w);
				Phrase cleanedPhrase = new Phrase(p.cleanUp(p.phrase));
				wPhrases.add(cleanedPhrase);			
				theMap.put(w, wPhrases);
		}
		//word is not in the map yet. add new entry.
		else {
			HashSet<Phrase> phraseSet = new HashSet<Phrase>();//the first set of phrase associated with this word
			String cleanedP=Phrase.cleanUp(p.phrase);
			phraseSet.add(new Phrase(cleanedP));
			theMap.put(new Word(w.getMatchWord()), phraseSet);	
		}
		pcs.firePropertyChange("Phrase Added",false,true);
	}


	/** 
	 * Drop the association between the 
	 * specified {@link Word} and {@link Phrase}, if any
	 */
	public void dropAssoc(Word w, Phrase p) {
		//
		// FILL ME IN
		// Leave the following line as the last line of this method
		//
		System.out.println("calling it here");
		Phrase cleanedP = new Phrase(p.cleanUp(p.phrase));
		if(theMap.containsKey(w) && theMap.get(w).contains(cleanedP)){
			
			theMap.get(w).remove(cleanedP);
			HashSet<Phrase> toUpdate = theMap.get(w);
			toUpdate.remove(cleanedP);
			theMap.put(w, toUpdate);
			
			System.out.println("160");
		
			pcs.firePropertyChange("Phrase Deleted",false,true);
}
		
	}


	/** Return a Set that provides each {@link Phrase} associated with
    the specified {@link Word}.
	 */
	public Set<Phrase> getPhrases(Word w) {
		//
		// FILL ME IN
		// This method should never return null
		if(theMap.containsKey(w)) return theMap.get(w) ;
		else return new HashSet<Phrase>();
		
	}
	
	/** 
	 * Drop a word completely from the KWIC 
	 * 
	 * @param w Word to be dropped
	 */
	public void deleteWord(Word w) {
		//
		// FILL ME IN
		// Leave the following line as the last line
		//
		if(theMap.containsKey(w)){
			theMap.remove(w);
		}
		pcs.firePropertyChange("Word Deleted",false,true);
	}

	/** Rerturn a Set of all words */
	public Set<Word> getWords() {
		//
		// FILL ME IN
		// This method should never return null
		
		return theMap.keySet();
	}
	public void refresh(){
		
	}

}
