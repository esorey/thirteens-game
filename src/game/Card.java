package game;

public class Card implements Comparable<Card>{
	
	private final Suit suit;
	private final CardValue value;
	
	public Card(Suit suit, CardValue val) {
		this.suit = suit;
		this.value = val;
	}
	
	public static Card lowestCard() {
		return new Card(Suit.SPADES, CardValue.THREE);
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
	
	/*@Override
	public String toString() {
		return value.toString().toLowerCase() + " of " + suit.toString().toLowerCase();
	}*/
	
	
	
	@Override
	public String toString() {
		return this.value + " " + this.suit;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	/*@Override
	public int hashCode() {
		return 31 * suit.hashCode() + value.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Card)) {
			return false;
		}
		Card other = (Card) o;
		return other.getSuit().equals(this.suit) &&
			   other.getValue().equals(this.value);
	}*/
	
	

}
