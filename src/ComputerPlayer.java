import java.util.Arrays;
import java.util.HashSet;

public class ComputerPlayer extends Player {

	private final Game game;
	private DecisionTree decisionTree;
	private boolean verbose = false;
	public ComputerPlayer(Game game, boolean verbose) {
		super(game);
		this.game = game;
		this.verbose = verbose;
		this.decisionTree = new DecisionTree();
	}

	public ComputerPlayer(Game game) {
		this(game, false);
	}

	@Override
	public Guess nextGuess() {
		Guess guess = decisionTree.getGuess();

		if (verbose) {
			System.out.println(decisionTree.getCurrentStepString());
			System.out.println("Computer guesses [" + guess + "]");
		}
		return guess;
	}

	@Override
	public void play() {
		while (!game.isOver()) {
			Score eval = game.guess(nextGuess());
			decisionTree.getNextGuess(eval);
		}
	}
}
