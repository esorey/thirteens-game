import java.util.Collection;

// Models a group of cards that a player can play for a given playmode.
public class Playable {
	
	private PlayMode playmode;
	private Collection<Card> cards;
	private Card highCard; // highest valued card for this Playable
	
	public Playable(Collection<Card> cards, PlayMode mode, Card highCard) {
		this.playmode = mode;
		this.cards = cards;
		this.highCard = highCard;
	}
	
	public Collection<Card> getCards() {
		return this.cards;
	}
	
	public Card getHighCard() {
		return this.highCard;
	}
	
	@Override
	public String toString() {
		return playmode.toString() + ": " + cards.toString();
	}
	
	
}
