import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select an option:");
		System.out.println("1. Run computer player");
		System.out.println("2. Run human player");
		System.out.println("3. Follow decision tree");
		System.out.print("> ");
		int option = scanner.nextInt();
		switch (option) {
			case 1:
				runComputerPlayer();
				break;
			case 2:
				runHumanPlayer();
				break;
			case 3:
				followDecisionTree();
				break;
			default:
				System.out.println("Invalid option");
		}
	}

	public static void runComputerPlayer() {
		Game game = new Game();
		Player player = new ComputerPlayer(game, true);
		player.play();
	}

	public static void runHumanPlayer() {
		Game game = new Game();
		Player player = new HumanPlayer(game);
		player.play();
	}

	public static void followDecisionTree() {
		DecisionTree tree = new DecisionTree();
		Scanner scanner = new Scanner(System.in);
		Score lastScore = null;
		while (lastScore == null || lastScore.getExacts() != Mastermind.CODE_LENGTH) {
			System.out.println(tree.getCurrentStepString());
			System.out.print("Enter score (space-separated: exact(s), color(s)):\n> ");
			String input = scanner.nextLine();
			String[] parts = input.split(" ");
			lastScore = new Score(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			tree.getNextGuess(lastScore);
		}
	}

}