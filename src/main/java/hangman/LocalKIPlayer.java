package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalKIPlayer extends APlayer {

	private List<String> wordlist = new ArrayList<String>();

	// line
	private String l = "";

	// Counter f??r Buchstaben. Bsp.: Wenn der Counter auf 1 ist,
	// dann gibt die KI einen Buchstaben aus und danach versucht sie W??rter
	// auszugeben.
	private int abcd = 0, counter = 0, charCount = 0, oldCharCount = 0;
	private boolean fWord = false;

	// Konstruktor LocalKIPlayer
	public LocalKIPlayer() {
		super();

		BufferedReader br = null;
		FileReader fr = null;

		try {
			// Setzt den Namen der Textdatei
			String fileName = "wordlist.txt";
			// Erstellt eine neue File welche die Textdatei l??dt.
			File file = new File(fileName);

			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String line = "";
			// Diese While schleife gibt der ArrayList "wordlist", jedes Wort.
			while ((line = br.readLine()) != null) {
				line = line.toLowerCase();
				this.wordlist.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Play Methode
	@Override
	public String play(final List<String> guessed, final String chars, final char emptyChar) {
		// Setzt den CharCount auf 0.
		this.charCount = 0;
		// Setzt den CharCount.
		for (int c = 0; c < chars.length(); c++) {
			if (!chars.split("")[c].contains(Character.toString(emptyChar))) {
				this.charCount++;
			}
		}
		// Guckt, wenn der CharCount auf 0 ist und das Wort kleiner,
		// als 5 Buchstaben ist, dann setze den Counter auf 0.
		if (this.charCount == 0 && chars.length() < 5) {
			this.counter = 1;
		}
		// Wenn der CharCount kleiner oder gleich 4 ist und gleichzeitig gr????er oder
		// gleich 2 ist und das Wort l??nger als 5 Buchstaben ist, dann setze den
		// Buchstaben Index (also 0 w??re 'e', 1 w??re 'n' etc.) auf 0 und den Counter auf
		// 1.
		if (this.charCount <= 4 && this.charCount >= 2 && chars.length() > 5) {
			this.abcd = 0;
			this.counter = 1;

			// Wenn der CharCounte gr????er oder gleich 5 ist, dann setze den Counter auf 0.
		} else if (this.charCount >= 5) {
			this.counter = 0;

			// Wenn das Wort ??ber 9 Buchstaben lang ist und der CharCount kleiner oder
			// gleich 4 ist, dann setze Counter = 1.
		} else if (chars.length() > 9 && this.charCount <= 4) {
			this.counter = 1;
		}

		// Wenn das Wort falsch war, dann setze Counter auf 1 und fWord auf false.
		if (this.fWord) {
			if (this.charCount == this.oldCharCount) {
				this.counter = 1;
				this.fWord = false;
			}
		}

		// Wenn der Counter nicht gleich 0 ist, dann suche einen Buchstaben aus.
		if (this.counter != 0) {
			String guessC = "";

			// Setzt den Integer "counter" um eine Zahl runter.
			this.counter--;

			try {
				// Geht in guess und ratet einen Buchstaben
				guessC = this.guess(guessed, chars);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print(guessC);
			return guessC;

			// Wenn der Counter auf 0 ist, dann suche ein Wort.
		} else {
			String line = "";
			try {
				// Do-While schleife. F??hrt als erstes den Teil im do { aus
				// und danach solange guessed den String line (Das Wort) enth??lt
				// f??hrt die Schleife den do { Teil weiter aus.
				do {
					// Geht in this.search
					line = this.search(chars, guessed, emptyChar);
					// macht line zu klein Buchstaben.
					line = line.toLowerCase();
				} while (guessed.contains(line));

				// Line wird wieder auf "" gesetzt.
				this.l = "";
				// fWord wird auf true gestellt
				this.fWord = true;

				// Integer oldCharCount wird zu oldCharCount gesetzt.
				this.oldCharCount = this.charCount;

				// Wenn die line leer ist
				if (line.isEmpty()) {
					// Dann geht das Programm wieder in guess.
					line = this.guess(guessed, chars);
				}

				// Gibt das Wort / den Buchstaben aus.
				System.out.print(line);

				// Gibt das Wort / den Buchstaben zur??ck.
				return line;

			} catch (IOException e) {
				e.printStackTrace();

				// fWord wird auf true gestellt
				this.fWord = true;

				// Integer oldCharCount wird zu oldCharCount gesetzt.
				this.oldCharCount = this.charCount;

				// Gibt das Wort / den Buchstaben aus.
				System.out.print(line);

				// Gibt das Wort / den Buchstaben zur??ck.
				return line;
			}
		}
	}

	private String search(final String chars, final List<String> guessed, final char emptyChar) throws IOException {
		String[] lineChars;
		boolean rightLine = false;
		int cCount, lCount;

		for (String line : this.wordlist) {

			// Splitted die line, damit es einzele Buchstaben sind.
			lineChars = line.split("");
			// Wenn die l??nge der line, mit der l??nge der chars ??bereinstimmt und guessed
			// die Line nicht enth??lt, dann ...
			if (line.length() == chars.length() && !guessed.contains(line)) {

				for (int i = 0; i < chars.length(); i++) {

					// chars[i] nicht "_" ist, dann ...
					if (!chars.split("")[i].equals(Character.toString(emptyChar))) {

						// Wenn lineChars[i] mit chars[i] ??bereinstimmt, dann ...
						if (chars.contains(lineChars[i])) {

							// Wenn guessed nicht lineChars[i] enth??lt und wenn chars[i], lineChars[i]
							// enth??lt,
							// dann rightLine = false und die Schleife wird abgebrochen.
							if (!guessed.contains(lineChars[i]) && chars.contains(lineChars[i])) {
								rightLine = false;
								break;

								// Wenn nicht, dann ...
							} else {
								// Z??hlt wie viele (chars[i]) es in der Line gibt.
								lCount = line.length() - line.replace(lineChars[i], "").length();
								// Z??hlt wie viel (lineChars[i]) es in den Chars gibt.
								cCount = chars.length() - chars.replace(lineChars[i], "").length();

								// Wenn der erste Buchstabe von chars mit dem von LineChars nicht ??bereinstimmt
								// und der erste Buchstabe von Chars nicht "_" ist, dann rightline = false und
								// die Schleife wird abgebrochen.
								if (!chars.startsWith(lineChars[0])
										&& !chars.startsWith(Character.toString(emptyChar))) {
									rightLine = false;
									break;
								}

								boolean wrongLetter = false;

								for (int r = 0; r < guessed.size(); r++) {
									// Wenn die L??nge von guessed an der Stelle r gleich 1 ist, dann ...
									if (guessed.get(r).length() == 1) {
										// Wenn die line, guessed an der Stelle r enth??lt, dann ...
										if (line.contains(guessed.get(r))) {
											// Wenn c nicht guessed an der Stelle r enth??lt, dann wrongLetter = true.
											if (!chars.contains(guessed.get(r))) {
												wrongLetter = true;
											}
										}
									}
								}
								// Wenn wrongLetter == true ist, dann rightLine = false und Schleife wird
								// abgebrochen.
								if (wrongLetter) {
									rightLine = false;
									break;
								}

								// Wenn guessed lineChars an der Stelle i enth??lt und c
								// (chars.Arrays.toString())
								// nicht lineChars an der Stelle i enth??lt, dann rightLine = false und die
								// Schleife wird abgebrochen.
								if (guessed.contains(lineChars[i]) && !chars.contains(lineChars[i])) {
									rightLine = false;
									break;
								}

								// Wenn der lCount mit dem cCount ??bereinstimmt, dann wird rightLine auf true
								// gestellt.
								if (lCount == cCount) {
									rightLine = true;

									// Wenn nicht, dann wird rightLine auf false gestellt und die Schleife wird
									// abgebrochen.
								} else {
									rightLine = false;
									break;
								}
							}
							// Wenn die lineChars an der Stelle i nicht mit den chars an der Stelle i
							// ??bereinstimmen,
							// dann wird rightLine auf false gestellt und die Schleife wird abgebrochen.
						} else {
							rightLine = false;
							break;
						}

					}
				}
				// Wenn rightLine am Ende der Schleife noch true ist, dann this.l = line und die
				// Schleife wird abgebrochen.
				if (rightLine) {
					this.l = line;
					break;
				}
			}
		}
		return this.l;
	}

	private String guess(final List<String> guessed, final String chars) {
		// String abc mit Buchstaben. Diese sind nach haufigkeit in Deutschen W??rtern
		// sortiert.
		String abc = "enirsathdulcgmobwfkzvp??????jyxq-";
		// c wird zu dem Buchstaben aus abc an der Stelle abcd.
		char c = abc.charAt(this.abcd);
		// abcd wird um eins erh??ht.
		this.abcd++;

		// Wenn abcd gleich 30 ist, dann setzt er abcd auf 0 und geht nochmal in Guess.
		if (this.abcd == 30) {
			this.abcd = 0;
			return this.guess(guessed, chars);
		}

		if (guessed.contains(Character.toString(c))) {
			return this.guess(guessed, chars);
		}
		// Gibt c zur??ck.
		return Character.toString(c);
	}
}