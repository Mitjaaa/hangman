package hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Gameboard {
	private String word = "";
	private String chars;

	private List<String> guessed = new LinkedList<String>();
	private char emptyChar = '_';

	public char getemptyChar() {
		return this.emptyChar;
	}

	public void setemptyChar(final char emptyChar) {
		this.emptyChar = emptyChar;
	}

	public String getWord() {
		return this.word;
	}

	public String getChars() {
		return this.chars;
	}

	public List<String> getGuessed() {
		return this.guessed;
	}

	public void searchWord() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			// Filereader und BufferedReader f??r die Wortliste
			fr = new FileReader("wordlist.txt");
			br = new BufferedReader(fr);

			// Random f??r das Wort, welches zuf??llig ausgew??hlt werden soll.
			Random r = new Random();
			// lineCounter z??hlt die Linien / W??rter
			int lineCounter = (int) br.lines().count();
			// Random mit der Anzahl der W??rter wird erstellt.
			int rC = r.nextInt(lineCounter) + 1;

			br.close();
			fr.close();

			fr = new FileReader("wordlist.txt");
			String currentLine;

			br = new BufferedReader(fr);
			int counter = 0;

			// In dieser While-Schleife geht das Programm durch alle Lines bis
			// der counter (von den Zeilen) mit dem Random ??bereinstimmt.
			while ((currentLine = br.readLine()) != null) {
				// Counter wird um eins erh??ht.
				counter++;
				// Wenn der Counter mit rC ??bereinstimmt, dann soll er dieses Wort ausw??hlen.
				if (counter == rC) {
					// Wort wird zu Kleinbuchstaben konvertiert.
					this.word = currentLine.toLowerCase();

					// Legt die l??nge der Chars f??r das Spielfeld fest und setzt die Chars auf
					// standard.
					this.chars = "";
					for (int i = 0; i < this.word.length(); i++) {
						this.chars += "_";
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean guess(final String letterOrWord) {
		// Wenn das Wort das geratene Wort oder den Buchstaben enth??lt, dann ...
		if (this.word.contains(letterOrWord) && !this.guessed.contains(letterOrWord)) {

			// Das Wort wird gesplitted, damit man jeden einzelnen Buchstaben hat.
			String[] Letters = this.word.split("");

			for (int i = 0; i < this.word.length(); i++) {
				// Wenn der geratene Buchstabe oder das Wort den Buchstabe an der Stelle i vom
				// Wort enth??lt
				// und der geratene Buchstabe oder das Wort eine l??nge von 1 hat, dann wird
				// chars an der Stellzum Buchstaben gesetzt.
				if (letterOrWord.contains(Letters[i]) && letterOrWord.length() == 1) {
					char[] charArray = this.chars.toCharArray();
					charArray[i] = Letters[i].charAt(0);
					String outputString = new String(charArray);
					this.chars = outputString;
				}
			}

			// Wenn das Wort richtig ist, dann setzt das Programm alle Buchstaben ein.
			if (this.word.equals(letterOrWord)) {
				for (int i = 0; i < this.word.length(); i++) {
					char[] charArray = this.chars.toCharArray();
					charArray[i] = Letters[i].charAt(0);
					String outputString = new String(charArray);
					this.chars = outputString;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean isFinished() {
		int cCounter = 0; // emptyChar Counter
		for (int i = 0; i < this.chars.length(); i++) {
			if (!this.chars.split("")[i].contains("_")) {
				cCounter++;
			}
		}
		// Guckt ob es keine Unterstriche mehr gibt
		// Wenn es keine mehr gibt, dann gibt er true zur??ck.
		if (cCounter == this.chars.length()) {
			return true;
		} else {
			return false;
		}
	}
}