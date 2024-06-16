import java.util.Arrays;

public class Guess {
	private final Mastermind.Color[] guess;
	private Score score;
	private final int ordinal;

	public Guess(Mastermind.Color[] guess) {
		this.guess = guess;
		String ordinalString = "";
		for (int i = 0; i<guess.length; i++) {
			ordinalString += guess[i].ordinal();
		}
		this.ordinal = Integer.parseInt(ordinalString, Mastermind.Color.values().length);
	}

	// Add a constructor that takes an int as a parameter
	public Guess(int ordinal) {
		this.ordinal = ordinal;
		String guess = Integer.toString(ordinal, Mastermind.Color.values().length);

		// Pad with 0s
		while (guess.length() < Mastermind.CODE_LENGTH) {
			guess = "0" + guess;
		}

		// Convert to Mastermind.Color array
		Mastermind.Color[] guessArray = new Mastermind.Color[Mastermind.CODE_LENGTH];
		for (int j = 0; j<Mastermind.CODE_LENGTH; j++) {
			guessArray[j] = Mastermind.Color.values()[Integer.parseInt(String.valueOf(guess.charAt(j)))];
		}

		this.guess = guessArray;
	}

	public Mastermind.Color[] getGuess() {
		return guess;
	}

	@Override
	public String toString() {
		char[] guessChars = new char[guess.length];
		for (int i = 0; i<guess.length; i++) {
			guessChars[i] = Mastermind.COLOR_CHARS[guess[i].ordinal()];
		}
		return new String(guessChars);
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Guess other = (Guess) obj;
		return Arrays.equals(guess, other.guess);
	}

	public int hashCode() {
		return Arrays.hashCode(guess);
	}
}