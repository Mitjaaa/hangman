package hangman;

import java.util.List;

public abstract class APlayer {

	private String name = "";

	private int life = 0;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getLife() {
		return this.life;
	}

	public void setLife(final int life) {
		this.life = life;
	}

	public abstract String play(List<String> guessed, String chars, char emptyChar);

	public void reduceLife() {
		this.life--;
	}

	public boolean isDeath() {
		if (this.life == 0) {
			return true;
		} else {
			return false;
		}
	}
}
