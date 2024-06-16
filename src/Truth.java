import java.util.Arrays;

public class Truth extends Guess {
	public Truth(Mastermind.Color[] truth) {
		super(truth);
	}

	public Score evaluate(Guess guess) {
		Score score = new Score();
		Mastermind.Color[] guessArray = Arrays.copyOf(guess.getGuess(), guess.getGuess().length);
		Mastermind.Color[] comparator = Arrays.copyOf(this.getGuess(), this.getGuess().length);

		for (int i = 0; i < guess.getGuess().length; i++) {
			if (guessArray[i] == comparator[i] && guessArray[i] != null) {
				score.incExacts();
				comparator[i] = null;
				guessArray[i] = null;
			}
		}
		for (int i = 0; i < guess.getGuess().length; i++) {
			for (int j = 0; j < comparator.length; j++) {
				if (guessArray[i] == comparator[j] && guessArray[i] != null) {
					score.incColors();
					comparator[j] = null;
					guessArray[i] = null;
					break;
				}
			}
		}
		guess.setScore(score);
		return score;
	}
}
