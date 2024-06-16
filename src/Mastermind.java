public class Mastermind {

	public static enum Color {
		VIOLET, GREEN, YELLOW, ORANGE, PINK, WHITE;
	}

	public static final char[] COLOR_CHARS = new char[Color.values().length];
	static {
		for (int i = 0; i < Color.values().length; i++) {
			COLOR_CHARS[i] = Color.values()[i].name().charAt(0);
		}
	}

	public static final int CODE_LENGTH = 4;

}
