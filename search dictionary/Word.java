package kwic;

/**
 * Author: Adela Gao
 * email: gao.adela@wustl.edu
 * Lab: cse132 Lab 2a
 * File: Word.java
 * To read and clean up words from a file. Clean up details see below.
 */

/** Represents the original and matching forms of a word.  
 * You must implement 
 * {@link Object#hashCode()} correctly as well as
 * {@link Object#equals(Object)} 
 * for this to work.
 */

public class Word {
public String word;


	/** Represent a word of a {@link Phrase}
	 * @param w The original word
	 */
	public Word(String w){
		this.word=w;

	}

	/**
	 * The word used for matching is the original word run throgh
	 * the WordCanonical filter.
	 * @return the form of the word used for matching.
	 * 
	 */
	public String getMatchWord() {
		//
		// FILL ME IN
		// This should never return null
		String ans = WordFilter.instance().makeCanonical(word);
		return ans;
	}

	/**
	 * 
	 * @return the original word
	 */
	public String getOriginalWord() {
		//
		// FILL ME IN
		// This should never return null
		return word;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : getMatchWord().hashCode());
		return result;
	}


	/**
	 * You must implement this so that two words equal each
	 * other if their matched forms equal each other.
	 * You can let eclipse generate this method automatically,
	 * but you have to modify the resulting code to get the
	 * desired effect.
	 * 
	 * This method is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
//	public boolean equals(Object o) {	
//	}

	@Override
	public boolean equals(Object obj) {
		if(this.getClass()==null || obj.getClass() == null)return false;
		
		if (this.hashCode() == obj.hashCode())
			return true;
		else
			return false;
	}

	/**
	 * @return the word and its matching form, if different
	 */
	public String toString(){
		if (getOriginalWord().equals(getMatchWord()))
			return getOriginalWord();
		else
			return getOriginalWord() + " --> " + getMatchWord();
	}

}
