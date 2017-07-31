package game;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
	private Hand hand;
	
	public Player(Deck deck, int nCards) {
		hand = new Hand(deck.deal(nCards));
	}
	
	// Mainly for testing; lets us make a player with a specific hand.
	public Player(List<Card> cards) {
		hand = new Hand(cards);
	}
	
	// Mainly for testing; can probably remove later
	public List<Card> getCards() {
		List<Card> cards = hand.getCards();
		Collections.sort(cards);
		return cards;
	}
	
	public boolean isOutOfCards() {
		return hand.isEmpty();
	}
	
	
	public Card getLowestCard() {
		return hand.getLowestCard();
	}
	
	
	// Get all playables this player can play for a given playmode and card to beat
	public Set<Playable> getLegalPlayables(PlayMode playMode, Card toBeat) throws Exception {
		if (playMode.equals(PlayMode.FREE_CHOICE)) {
			return hand.getFreeChoicePlayables();
		}
		else {
			return hand.getPlayables(playMode, toBeat);
		}
	}
	
	// Play a given playable from the player's hand
	public void playMove(Playable move) {
		hand.playMove(move);
	}
}