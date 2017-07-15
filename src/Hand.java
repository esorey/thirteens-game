import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Hand {
	private List<Card> cards;
	
	public Hand(List<Card> cards) {
		this.cards = cards;
	}
	
	// Get a list of all Playables for an "X of a kind" playmode (including singles).
	
	// TODO: don't iterate over all CardValues; just need to consider those >= highCard
	// TODO: test this
	public Set<Playable> getLegalNOfAKind(int n, Card highCard) throws Exception {
		Set<Playable> res = new HashSet<Playable>();
		for (CardValue val : CardValue.values()) {

			// Only want values >= the high card's value
			if (val.compareTo(highCard.getValue()) >= 0) {
				List<Card> valMatches = cards.stream()
												.filter(c -> c.getValue().equals(val))
												.collect(Collectors.toList());
				
				// Get all the different subset possibilities
				HashSet<Playable> playables = getSubsetsOfSizeN(valMatches, n);

				// This takes care of suit ordering
				List<Playable> legalPlayables = playables.stream()
												.filter(p -> p.getHighCard().compareTo(highCard) >= 0)
												.collect(Collectors.toList());
				
				// Add them to our result
				res.addAll(legalPlayables);	
			}
		}
		return res;
	}
	
	// Return the lowest card in the hand
	public Card getLowestCard() {
		
		Card lowestCard = cards.get(0);
		for (int i = 1; i < cards.size(); i++) {
			Card currCard = cards.get(i);
			if (currCard.compareTo(lowestCard) < 0) {
				lowestCard = currCard;
			}
		}
		return lowestCard;
	}
	
	// Check if the hand is empty
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	
	//TODO: Rewrite to be cleaner -> recursion?
	// Handles getting X of a kind type Playables. For doubles and triples, returns all combinations
	// possible. Eg, having four threes in a hand gives six ways to play double threes.
	public HashSet<Playable> getSubsetsOfSizeN(List<Card> list, int n) throws Exception {

		HashSet<Playable> moves = new HashSet<Playable>();
		int fixedIdx = 0;
		
		// Singles case
		if (n == 1) {
			for (Card c : cards) {
				Playable move = new Playable(Arrays.asList(c), PlayMode.SINGLES, c);
				moves.add(move);
			}
		}
		
		// Doubles case
		else if (n == 2) {
			while (fixedIdx <= list.size() - n) {
				for (Card elem : list.subList(fixedIdx + 1, list.size())) {
					HashSet<Card> moveCards = new HashSet<Card>();
					moveCards.add(list.get(fixedIdx));
					moveCards.add(elem);
					Playable move = new Playable(moveCards, PlayMode.DOUBLES, Collections.max(moveCards));
					moves.add(move);
					
				}
				fixedIdx++;
			}
		}
		
		// Triples case
		else if (n == 3) {
			int fixedIdx2 = 1;
			while (fixedIdx <= list.size() - n) {
				while (fixedIdx2 <= list.size() - n + 1) {
					for (Card elem2: list.subList(fixedIdx2 + 1, list.size())) {
						HashSet<Card> moveCards = new HashSet<Card>();
						moveCards.add(list.get(fixedIdx));
						moveCards.add(list.get(fixedIdx2));
						moveCards.add(elem2);
						Playable move = new Playable(moveCards, PlayMode.TRIPLES, Collections.max(moveCards));
						moves.add(move);
					}
					fixedIdx2++;
				}
				fixedIdx++;
				fixedIdx2 = fixedIdx + 1;
			}
		}
		
		
		// Quadruples case
		else if (n == 4) {
			Playable move = new Playable(list, PlayMode.QUADRUPLES, Collections.max(list));
			moves.add(move);
		}
		
		
		// Otherwise it's an error
		else {
			throw new Exception("invalid subset size");
		}
		return moves;
	}


}
