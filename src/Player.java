import java.util.List;

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
	
	// Given the last played Playable and a PlayMode, return a Playable to be played. Return null
	// if there is no legal move.
	public Playable makeMove(Playable lastPlayed, PlayMode mode) {
		
	}
	
	// Return a list of Playables that represents all legal moves at the time
	public List<Playable> getAllLegalMoves(Playable lastPlayed, PlayMode mode) {
		
	}
	
	private List<Playable> getSingles(){};
	private List<Playable> getDoubles(Card cardToBeat) throws Exception{
		return hand.getLegalNOfAKind(2, cardToBeat);
	}
	private List<Playable> getTriples(){};
	private List<Playable> getQuadruples(){};
	private List<Playable> getRuns(int length){};
}
