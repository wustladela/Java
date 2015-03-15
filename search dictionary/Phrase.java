package kwic;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
/**
 * Author: Adela Gao
 * email: gao.adela@wustl.edu
 * Lab: cse132 Lab 2a
 * File: Phrase.java
 * To read and clean up phrases from a file. Clean up details see below.
 */

/**
 * Represent a phrase.
 *
 */
public class Phrase {

	final protected String phrase;

	public Phrase(String s){
		phrase = s;
	}

	/** 
	 * Provide the words of a phrase.  
	 * Each word may have to be cleaned up:  
	 * punctuation removed, put into lower case
	 */

	public Set<Word> getWords() {
		// FILL ME IN
		// Use StringTokenizer to break the
		//  phrase into words
		// Return a Set of such words
		// This should never return null
		Set<Word> ans = new HashSet<Word>();
		StringTokenizer st = new StringTokenizer(phrase);
		while(st.hasMoreTokens()){
			Word temp = new Word(st.nextToken());
			ans.add(new Word(temp.getMatchWord()));
		}
		return ans;
	}

	/** The behavior of this lab depends on how you view this method.
      Are two phrases the same because they have the same words?
      Or are they the same because they are string-equlivalent.
      <UL>
       <LI> What song,  Is that Becky
       <LI> What song is that, Becky
      </UL>
      The above phrases have the same words but are different strings.
	 */


	/** This method must also be properly defined, or else your {@link HashSet}
      structure won't operate properly.
	 */

	/** Filter the supplied {@link String} (which is the String of
      a {@link Phrase} presumably) into a canonical form
      for subsequent matching.
      The actual filtering depends on what you consider to be
      insignificant in terms of matching.  
      <UL> <LI> If punctuation is
      irrelevant, remove puncutation.
           <LI> If case does not matter, than convert to lower (or upper)
	        case.
      </UL>
	 */
	protected static String cleanUp(String s){
		// FIX ME
		// Don't just return s, but return a cleaned up version of s
		//   as described above
		String ans = WordFilter.instance().makeCanonical(s);
		return ans;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		String cleaned = "";
		Set<Word> original = this.getWords();
		for(Word i : original){
			i=new Word(i.getMatchWord());
			cleaned+=i;
		}
		Word cleanedWord = new Word(cleaned);
		result = prime * result + ((cleanedWord == null) ? 0 : cleanedWord.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phrase other = (Phrase) obj;
		
		//compare two hashsets		
		if (phrase == null) {
			if (other.phrase != null)
				return false;
		} else if (!(this.hashCode()==other.hashCode()))
			return false;
		return true;

	}

	public String toString(){
		return phrase;
	}
	
	/**
	 * the given makeCanonical keeps giving me string out of bounds error, and error traces back to the method makeCanonical
	 * I consulted more than 5 TAs for more than three hours combined but cannot find error from my code
	 * so I added "&& temp.length()>0" in the makeCanonical method to fix this, according to the error message
	 * and right after fixing this, all tests passed
	 * @param temp
	 * @return
	 */
	public String makeCanonical(String temp) {
		temp = temp.toLowerCase();
		if(temp.lastIndexOf(".") == temp.length()-1 && temp.length()>0)
			temp = temp.substring(0,temp.length()-1);
		if(temp.lastIndexOf(",") == temp.length()-1 && temp.length()>0)
			temp = temp.substring(0,temp.length()-1);
		if(temp.lastIndexOf("!") == temp.length()-1 && temp.length()>0)
			temp = temp.substring(0,temp.length()-1);
		if(temp.length() > 2 && temp.lastIndexOf("\'") == temp.length()-2){
			System.out.println(""+temp);
			temp = temp.substring(0,temp.length()-2);
		}
		return temp;
	}

}
