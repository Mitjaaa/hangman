package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class KITest {
	private static String text = "v _ e _ _ _ g";
	private static String[] chars, lineChars;
	private static List<String> guessed = new LinkedList<String>();

	public static void main(final String[] args) throws IOException {
		String guessing = guess();
		System.out.println(guessing);
		search();
	}

	private static void search() throws IOException {
		chars = text.split(" ");

		String fileName = "wordlist.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		boolean rightLine = true, x = false;

		while ((line = br.readLine()) != null && x == false) {
			line = line.toLowerCase();
			if (line.length() == chars.length && !guessed.contains(line)) {
				lineChars = line.split("");

				for (int i = 0; i < line.length(); i++) {
					if (!chars[i].equals("_")) {
						if (chars[i].equals(lineChars[i])) {
							rightLine = true;

						} else {
							rightLine = false;
							i = 99;
						}
					}
				}

				if (rightLine) {
					System.out.println(line);
					guessed.add(line);
					x = true;
				}

			}
		}

	}

	private static String guess() {
		Random r = new Random();
		String alph = "abcdefghijklmnopqrstuvwxyz";
		char c = alph.charAt(r.nextInt(26));
		if (guessed.contains(Character.toString(c))) {
			guess();
		}
		guessed.add(Character.toString(c));
		return Character.toString(c);
	}
}
