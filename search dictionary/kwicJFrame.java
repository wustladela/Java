package kwic;
/*
 * Author: Adela Gao
 * A recent project in cse132, wustl
 * Implementing a catalog GUI for search engine.
 * when you start, show all the phrases as well
 * be able to click on a phrase and show all the words associated with that phrase
 * also, buttons for force association and delete association
 * 
 * 
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;
import javax.swing.JTextField;

public class kwicJFrame extends JFrame {
	//public static Map<Word, HashSet<Phrase>> theMap = new HashMap<Word, HashSet<Phrase>>();
	private JPanel contentPane;
	public static KWIC kwic;
	protected static PropertyChangeSupport pcs;
	public Word currentWord;
	private JTextField addPhraseField;
	private JTextField addWordField;
	private JTextField forceWord;
	private JTextField forcePhrase;
	private JList wordList;
	private JList phraseList;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					kwicJFrame frame = new kwicJFrame();
					frame.setVisible(true);
					kwic = new KWIC();
					pcs = kwic.getPCS();
					kwic.addPhrases(new File("datafiles/kwic/fortunes.txt"));
					Set wordset = kwic.getWords();
					System.out.println("wordset: " + wordset.toString());
//					for (Object w : wordset) {
//						Word temp = new Word(w.toString());
//						HashSet<Phrase> phraseset = (HashSet<Phrase>) kwic
//								.getPhrases(temp);
//						theMap.put(temp, phraseset);
//					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	/**
	 * Create the frame.
	 */

	public kwicJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 500, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 25, 196, 179);
		contentPane.add(scrollPane);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(229, 25, 300, 200);
		contentPane.add(scrollPane2);

		JList phraseList = new JList();
		//TODO
//		phraseList.addMouseListener(new MouseAdapter(){
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
		scrollPane2.setViewportView(phraseList);
		
		wordList = new JList();
		wordList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("clicked! " + wordList.getSelectedValue());
				if(wordList.isSelectionEmpty()==false){
					currentWord = new Word(wordList.getSelectedValue().toString());
					Set phraseset = kwic.getPhrases(currentWord);
					if (phraseset != null){
						Object[] labels_phrase = phraseset.toArray();
						phraseList.setListData(labels_phrase); // will use the toString
																// of each array element
					}
					else{
						//no phrase to display
						Set empty = new HashSet<Phrase>();
						Phrase msg = new Phrase("There is currently no phrase related to this word.");
						empty.add(msg);
						Object[] display = empty.toArray(); 
						phraseList.setListData(display);
						
					}
				}
			}
		});
		
		scrollPane.setViewportView(wordList);
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Set wordset = kwic.getWords();
				Object[] labels_word = wordset.toArray();
				wordList.setListData(labels_word); // will use the toString of
													// each array element

			}
		});
		btnNewButton.setBounds(26, 259, 134, 48);
		contentPane.add(btnNewButton);

		addWordField = new JTextField();
		addWordField.setColumns(10);
		addWordField.setBounds(16, 322, 202, 48);
		contentPane.add(addWordField);

		addPhraseField = new JTextField();
		addPhraseField.setBounds(16, 397, 202, 48);
		contentPane.add(addPhraseField);
		addPhraseField.setColumns(10);

		JButton btnAddWord = new JButton("Add Word");
		btnAddWord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				kwic.addWord(new Word(addWordField.getText()));
				//TODO
				refresh();
			}
		});
		btnAddWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				Word newWord = new Word(addWordField.getText());
//				if (theMap.containsKey(newWord)) {
//					System.out.println("word already exists");
//				} else {
//					theMap.put(newWord, null);
//				}
				
				
			}

		});
		btnAddWord.setBounds(233, 333, 117, 29);
		contentPane.add(btnAddWord);

		JButton btnAddPhrase = new JButton("Add Phrase");
		btnAddPhrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Phrase phrase = new Phrase(addPhraseField.getText());
				Set wordSet = phrase.getWords();
				kwic.addPhrase(phrase);
				Set phraseset = kwic.getPhrases(currentWord);
				Object[] labels_phrase = phraseset.toArray();
				phraseList.setListData(labels_phrase); // will use the toString
														// of each array element
			}
		});
		btnAddPhrase.setBounds(233, 408, 117, 29);
		contentPane.add(btnAddPhrase);

		JButton btnDeleteWord = new JButton("Delete word");
		btnDeleteWord.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// FIXME Auto-generated method stub

				kwic.deleteWord(new Word(wordList.getSelectedValue().toString()));
				Set wordset = kwic.getWords();
				Object[] labels_word = wordset.toArray();
				wordList.setListData(labels_word);
				refresh();
			}
			
			
			
		});
		btnDeleteWord.setBounds(85, 216, 117, 29);
		contentPane.add(btnDeleteWord);

		JButton btnDeletePhrase = new JButton("Delete Phrase");
		btnDeletePhrase.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// FIXME Auto-generated method stub
				Phrase toDelete = new Phrase(phraseList.getSelectedValue().toString());
				phraseList.clearSelection();
				kwic.deletePhrase(toDelete);
				refresh();
			}
			
		});
		btnDeletePhrase.setBounds(412, 233, 117, 29);
		contentPane.add(btnDeletePhrase);
		
		forceWord = new JTextField();
		forceWord.setBounds(404, 286, 196, 35);
		contentPane.add(forceWord);
		forceWord.setColumns(10);
		
		forcePhrase = new JTextField();
		forcePhrase.setBounds(404, 333, 278, 83);
		contentPane.add(forcePhrase);
		forcePhrase.setColumns(10);
		
		JButton forceAsso = new JButton("Force Association");
		forceAsso.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Word forceW = new Word(forceWord.getText());
				Phrase forceP = new Phrase(forcePhrase.getText());
				kwic.forceAssoc(forceW, forceP);
			}
		});
		forceAsso.setBounds(412, 428, 162, 29);
		contentPane.add(forceAsso);

		
		
	}
	/**
	 * refresh the display
	 */
	public void refresh(){
		if(kwic.getWords()==null)return;//empty hashmap, no need to do anything.
		
		//update the word list; if there is a word selected (see around line 268), then update phrase list
		Set wordset = kwic.getWords();
		Object[] labels_word = wordset.toArray();
		//if user selected the wordList
		if(wordList.isSelectionEmpty()==false && phraseList!=null){
			//if there is at least one phrase associated with this currently selected word
			if(kwic.getPhrases((new Word(wordList.getSelectedValue().toString())))!=null){
				Set phraseset = kwic.getPhrases((new Word(wordList.getSelectedValue().toString())));
				Object[] labels_phrase = phraseset.toArray();
				phraseList.setListData(labels_phrase);
			}
			//if the currently selected word has no phrases
			else{
				Set empty = new HashSet<Phrase>();
				Phrase msg = new Phrase("");
				empty.add(msg);
				Object[] display = empty.toArray(); 
				phraseList.setListData(display);
			}
		}
		wordList.setListData(labels_word);
		
	}
	
}
