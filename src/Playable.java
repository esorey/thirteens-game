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
	
	public PlayMode getPlayMode() {
		return this.playmode;
	}
	
	@Override
	public String toString() {
		return playmode.toString() + ": " + cards.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Playable)) {
			return false;
		}
		Playable other = (Playable) o;
		return other.getCards().equals(this.cards) && 
			   other.getPlayMode().equals(this.playmode) &&
		       other.getHighCard().equals(this.highCard);
	}
	
	
	@Override
	public int hashCode() {
		return 31 * playmode.hashCode() + 17 * cards.hashCode() + highCard.hashCode(); 
	}
	
	
}
