public class Score {
	private int exacts, colors;

	public Score(int exacts, int colors) {
		this.exacts = exacts;
		this.colors = colors;
	}
	public Score(){
		this(0,0);
	}
	public void incExacts() {
		exacts++;
	}
	public void incColors() {
		colors++;
	}
	public int getExacts() {
		return exacts;
	}
	public int getColors() {
		return colors;
	}

	@Override
	public String toString() {
		return exacts + " exacts, " + colors + " color(s)";
	}

	public boolean isWinner() {
		return exacts == Mastermind.CODE_LENGTH;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Score other = (Score) obj;
		return exacts == other.exacts && colors == other.colors;
	}

	public int hashCode() {
		return exacts * 31 + colors;
	}
}
