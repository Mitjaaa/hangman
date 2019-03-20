package hangman;

import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends APlayer {

	@Override
	public String play(final List<String> guessed, final String chars, final char emptyChar) {
		Scanner s = new Scanner(System.in);
		return s.nextLine();
	}

}
