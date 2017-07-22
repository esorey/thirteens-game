package game;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private List<Card> cards;
	
	// Initialize to a full deck of 52 cards
	public Deck() {
		cards = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
			for (CardValue val : CardValue.values()) {
				cards.add(new Card(suit, val));
			}
		}
	}
	
	// Shuffle the cards
	private void shuffle() {
		Collections.shuffle(cards);
	}
	
	// Randomly deal a given number of cards
	public ArrayList<Card> deal(int numCards) {
		this.shuffle();
		// Remove numCards cards from the ends of the array
		// (to prevent array shifting)
		ArrayList<Card> res = new ArrayList<Card>();
		for (int i = 0; i < numCards; i++) {
			res.add(cards.remove(cards.size() - 1));
		}
		return res;
	}
}
