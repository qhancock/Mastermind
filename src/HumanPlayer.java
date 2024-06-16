import java.util.Scanner;

public class HumanPlayer extends Player {

	private final Game game;
	public HumanPlayer(Game game) {
		super(game);
		this.game = game;
	}
	@Override
	public Guess nextGuess() {
		Mastermind.Color[] guess = new Mastermind.Color[Mastermind.CODE_LENGTH];
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter your guess:\n> ");
		String input = scanner.nextLine();
		if (input.length() != Mastermind.CODE_LENGTH) {
			System.out.println("Invalid guess length");
			return nextGuess();
		}

		// Build guess
		for (int i = 0; i < Mastermind.CODE_LENGTH; i++) {
			try {
				guess[i] = Mastermind.Color.values()[new String(Mastermind.COLOR_CHARS).indexOf(input.charAt(i))];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid color: " + input.charAt(i));
				return nextGuess();
			}
		}
		return new Guess(guess);
	}

	@Override
	public void play() {
		while (!game.isOver()) {
			game.guess(nextGuess());
		}
	}

}
