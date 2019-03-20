package hangman;

import java.util.Scanner;

public class Controller {

	public static APlayer activePlayer;

	public static APlayer nextPlayer;

	public static Gameboard gameboard;
	public static int x = 0;

	public static void main(final String[] args) throws InterruptedException {
		if (x < 1) {
			x++;
			welcome();
			gameboard = new Gameboard();
			gameboard.searchWord();
			print();
		}
		String letter = "";

		while (!activePlayer.isDeath() && !gameboard.isFinished()) {
			switchPlayers();
			Thread.sleep(55);
			System.out.print(activePlayer.getName() + ", mach deinen Zug:");
			Thread.sleep(1000);

			letter = activePlayer.play(gameboard.getGuessed(), gameboard.getChars(), gameboard.getemptyChar());
			boolean right = gameboard.guess(letter);

			if (!gameboard.getGuessed().contains(letter)) {
				gameboard.getGuessed().add(letter);
			}

			if (!right) {
				activePlayer.reduceLife();
				System.out.println("");
				Thread.sleep(20);
				System.err.println("Das war leider falsch!");
				Thread.sleep(20);
				System.err
						.println(activePlayer.getName() + ", du hast jetzt noch " + activePlayer.getLife() + " Leben.");

			} else {
				Thread.sleep(30);
				System.out.println("");
				System.out.println("Richtig!");
			}

			if (activePlayer.isDeath()) {
				System.out.println("");
				System.out.println("GAME OVER!\n");
				System.err.println("Keiner hat gewonnen!");
				System.out.println("Das Wort war: " + gameboard.getWord());
				playAgain();
			} else if (gameboard.isFinished()) {
				System.out.println("");
				System.out.println("GEWONNEN!\n");
				System.out.println(activePlayer.getName() + " hat gewonnen! Glückwunsch!");
				System.out.print("Das Wort war: " + gameboard.getWord());
				System.out.println("");
				playAgain();
			} else {
				print();
			}
		}
	}

	private static void welcome() {
		System.out.println("     HANGMAN\n");
		System.out.println("1 - Einzelspieler");
		System.out.println("2 - Gegner: Local KI");
		System.out.println("");
		System.out.print("Deine Auswahl: ");
		Scanner s = new Scanner(System.in);
		String gamemode = s.nextLine();

		// Einzelspieler
		if (gamemode.equals("1")) {
			int life = getLifes(s);
			System.out.print("Lege deinen Namen fest: ");
			String name = s.nextLine();
			APlayer player = new HumanPlayer();
			player.setName(name);
			player.setLife(life);
			activePlayer = player;
			nextPlayer = player;

			// Spieler gegen LocalKI
		} else if (gamemode.equals("2")) {
			int life = getLifes(s);
			System.out.print("Lege deinen Namen fest: ");
			String name = s.nextLine();
			APlayer player1 = new HumanPlayer();
			player1.setName(name);
			player1.setLife(life);
			activePlayer = player1;

			APlayer player2 = new LocalKIPlayer();
			player2.setName("LokaleKI");
			player2.setLife(life);
			nextPlayer = player2;

			// LocalKI gegen RemoteKI
		} else {
			System.err.println("Bitte gebe eine der drei M??glichkeiten (1,2,3) ein!");
			welcome();
		}
	}

	private static int getLifes(final Scanner s) {
		int life = 0;
		System.out.print("Lege fest, mit wie vielen Leben du spielen willst: ");
		try {
			life = Integer.parseInt(s.nextLine());
		} catch (Exception e) {
			System.err.println("Du hast keine Zahl eingegeben! Probier's nochmal!");
			life = getLifes(s);
		}
		return life;
	}

	private static void switchPlayers() {
		APlayer bufferPlayer = activePlayer;
		activePlayer = nextPlayer;
		nextPlayer = bufferPlayer;
	}

	public static void print() {
		System.out.println("");

		String[] splitChars = gameboard.getChars().split("");
		for (int i = 0; i < gameboard.getChars().length(); i++) {
			System.out.print(splitChars[i] + " ");
		}
		System.out.println("");
	}

	private static void playAgain() throws InterruptedException {
		System.out.println("Willst du nochmal spielen?");
		Scanner s = new Scanner(System.in);
		String answer = s.nextLine();
		if (answer.equalsIgnoreCase("ja")) {
			for (int i = 0; i < 7; i++) {
				System.out.println("\n");
			}
			x = 0;
			main(null);
		} else if (answer.equalsIgnoreCase("nein")) {
			System.exit(0);
		}
	}

}
