package game;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Models a group of cards that a player can play for a given playmode.
public class Playable implements Comparable<Playable> {
	
	private PlayMode playmode;
	private Set<Card> cards;
	private Card highCard; // highest valued card for this Playable
	
	public Playable(Collection<Card> cards, PlayMode mode, Card highCard) {
		this.playmode = mode;
		this.cards = new HashSet<Card>(cards);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + ((highCard == null) ? 0 : highCard.hashCode());
		result = prime * result + ((playmode == null) ? 0 : playmode.hashCode());
		return result;
	}
	
	@Override
	public int compareTo(Playable other) {
		return this.highCard.compareTo(other.getHighCard());
	}
	
	@Override
	// TODO: fix this egregious hack with sorting
	public String toString() {
		List<Card> cardsList = new ArrayList<Card>(cards);
		Collections.sort(cardsList);
		return playmode.toString() + ": " + cardsList.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Playable)) {
			return false;
		}
		Playable other = (Playable) o;
		return other.getCards().containsAll(this.cards) &&
			   this.cards.containsAll(other.getCards()) &&
			   other.getPlayMode().equals(this.playmode) &&
		       other.getHighCard().equals(this.highCard);
	}
	
}
