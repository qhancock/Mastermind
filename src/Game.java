public class Game {

	private final Truth truth;
	private boolean verbose = false;
	private int guesses = 0;
	private boolean won = false;

	public static final int MAX_GUESSES = 10;

	private Mastermind.Color randomColor() {
		return Mastermind.Color.values()[(int) (Math.random() * Mastermind.Color.values().length)];
	}

	private Truth randomTruth() {
		Mastermind.Color[] truth = new Mastermind.Color[Mastermind.CODE_LENGTH];
		for (int i = 0; i < Mastermind.CODE_LENGTH; i++) {
			truth[i] = randomColor();
		}
		return new Truth(truth);
	}

	public Game() {
		this.truth = randomTruth();
	}
	public Game(boolean verbose) {
		this();
		this.verbose = verbose;
	}
	public Game(Truth truth) {
		this(truth, false);
	}
	public Game(Truth truth, boolean verbose) {
		if (truth.getGuess().length != Mastermind.CODE_LENGTH) {
			throw new IllegalArgumentException("Invalid truth length");
		}
		this.truth = truth;
		this.verbose = verbose;
	}
	public boolean isOver() {
		return guesses >= MAX_GUESSES || won;
	}
	public Score guess(Guess guess) {
		guesses++;
		Score eval = truth.evaluate(guess);
		System.out.println(guess + " -> " + eval);
		if (eval.isWinner()) {
			System.out.println("You win!");
			won = true;
		} else if (isOver()) {
			System.out.println("You lose! The correct answer was " + truth);
		}
		return eval;
	}

}