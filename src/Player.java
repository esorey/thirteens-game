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
	
	public boolean isOutOfCards() {
		return hand.isEmpty();
	}
	
	// Get all playables this player can play for a given playmode and card to beat
	public Set<Playable> getLegalPlayables(Card toBeat, PlayMode playMode) throws Exception {
		if (playMode.equals(PlayMode.FREE_CHOICE)) {
			return hand.getFreeChoicePlayables();
		}
		else {
			return hand.getPlayables(toBeat, playMode);
		}
	}
}