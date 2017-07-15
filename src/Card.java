
public class Card implements Comparable<Card>{
	
	private final Suit suit;
	private final CardValue value;
	
	public Card(Suit suit, CardValue val) {
		this.suit = suit;
		this.value = val;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public CardValue getValue() {
		return this.value;
	}
	
	@Override
	public int compareTo(Card other) {
		if (this.value.compareTo(other.value) > 0) {
			return 1;
		}
		else if (this.value.compareTo(other.value) < 0) {
			return -1;
		}
		else if (this.suit.compareTo(other.suit) > 0) {
			return 1;
		}
		else if (this.suit.compareTo(other.suit) < 0) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return value.toString().toLowerCase() + " of " + suit.toString().toLowerCase();
	}

}
