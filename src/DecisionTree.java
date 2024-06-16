import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

public class DecisionTree {

	public static final int numPossibleGuesses = (int) Math.pow(Mastermind.COLOR_CHARS.length, Mastermind.CODE_LENGTH);
	public static final Guess[] allPossibleGuesses = getAllPossibleGuesses();
	public static final Score[][] allPossibleScores = getAllPossibleScores();

	private static Guess[] getAllPossibleGuesses() {
		Guess[] allPossibleGuesses = new Guess[numPossibleGuesses];
		for (int i = 0; i < numPossibleGuesses; i++) {
			allPossibleGuesses[i] = new Guess(i);
		}
		return allPossibleGuesses;
	}

	private static Score[][] getAllPossibleScores() {
		Score[][] allPossibleScores = new Score[numPossibleGuesses][numPossibleGuesses];
		for (int truthOrdinal = 0; truthOrdinal < numPossibleGuesses; truthOrdinal++) {
			for (int guessOrdinal = 0; guessOrdinal < numPossibleGuesses; guessOrdinal++) {
				Truth truth = new Truth(allPossibleGuesses[truthOrdinal].getGuess());
				Guess guess = new Guess(allPossibleGuesses[guessOrdinal].getGuess());
				allPossibleScores[truthOrdinal][guessOrdinal] = truth.evaluate(guess);
			}
		}
		return allPossibleScores;
	}

	private static final DecisionTreeNode root = new DecisionTreeNode(new HashSet<Guess>(Arrays.asList(allPossibleGuesses))) {{build();}};

	private DecisionTreeNode currentStep;
	public DecisionTree() {
		this.currentStep = DecisionTree.root;
	}

	public Guess getGuess() {
		return currentStep.getGuess();
	}

	public String getCurrentStepString() {
		return currentStep.toString();
	}

	public Guess getNextGuess(Score score) {
		if (score != null) {
			currentStep = currentStep.getNextDecision(score);
		}
		return getGuess();
	}

	static class DecisionTreeNode {

		private HashSet<Guess> remainingPossibilities;
		private HashMap<Score, DecisionTreeNode> children;
		private Guess guess;

		public DecisionTreeNode(HashSet<Guess> remainingPossibilities) {
			this.remainingPossibilities = remainingPossibilities;
			this.children = new HashMap<Score, DecisionTreeNode>();
		}

		public void build() {

			if (remainingPossibilities.size() == 1) {
				guess = remainingPossibilities.stream().iterator().next();
				return;
			}

			double lowestEntropy = Double.POSITIVE_INFINITY;
			Guess bestGuess = null;
			for (Guess possibleGuess : allPossibleGuesses) {
				for (Guess remainingPossibility : remainingPossibilities) {
					Score remainingPossibilityScore = allPossibleScores[possibleGuess.getOrdinal()][remainingPossibility.getOrdinal()];
					if (children.containsKey(remainingPossibilityScore)) {
						children.get(remainingPossibilityScore).remainingPossibilities.add(remainingPossibility);
					} else {
						HashSet<Guess> remainingPossibilitiesForThatScore = new HashSet<Guess>();
						remainingPossibilitiesForThatScore.add(remainingPossibility);
						children.put(remainingPossibilityScore, new DecisionTreeNode(remainingPossibilitiesForThatScore));
					}
				}
				double entropy = 0;
				for (Score score : children.keySet()) {
					double probability = (double) children.get(score).remainingPossibilities.size() / remainingPossibilities.size();
					entropy -= probability * Math.log(1.0 / children.get(score).remainingPossibilities.size());
				}
				if (entropy < lowestEntropy || (entropy == lowestEntropy && remainingPossibilities.contains(possibleGuess))) {
					lowestEntropy = entropy;
					bestGuess = possibleGuess;
				}
				this.children.clear();
			}
			this.guess = bestGuess;
			for (Guess remainingPossibility : remainingPossibilities) {
				Score remainingPossibilityScore = allPossibleScores[bestGuess.getOrdinal()][remainingPossibility.getOrdinal()];
				if (children.containsKey(remainingPossibilityScore)) {
					children.get(remainingPossibilityScore).remainingPossibilities.add(remainingPossibility);
				} else {
					HashSet<Guess> remainingPossibilitiesForThatScore = new HashSet<Guess>();
					remainingPossibilitiesForThatScore.add(remainingPossibility);
					children.put(remainingPossibilityScore, new DecisionTreeNode(remainingPossibilitiesForThatScore));
				}
			}
			for (DecisionTreeNode child : children.values()) {
				child.build();
			}
		}

		public Guess getGuess() {
			return guess;
		}

		public DecisionTreeNode getNextDecision(Score score) {
			DecisionTreeNode nextDecision = children.get(score);
			return nextDecision == null ? this : nextDecision;
		}

		public String toString() {
			return "Possibilities: " + remainingPossibilities.size() + "\t" +
					(guess == null ?
						"Final answer: " + remainingPossibilities.stream().iterator().next() : "Guess: " + guess);
		}
	}
}