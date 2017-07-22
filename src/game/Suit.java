package game;

public enum Suit {
	SPADES, CLUBS, DIAMONDS, HEARTS;
	
	@Override
	public String toString() {
		return Character.toString(this.name().charAt(0));
	}
}