package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Hand {
	private List<Card> cards;
	
	public Hand(List<Card> cards) {
		this.cards = cards;
	}
	
	// Mainly for testing; can probably remove later
	public List<Card> getCards() {
		return this.cards;
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
	

	// Get a list of CardValues in this hand. Useful for computing runs
	private List<CardValue> getCardValues() {
		List<CardValue> vals = this.cards.stream().map(c -> c.getValue()).collect(Collectors.toList());
		return vals;
	}
	
	
	private int getValueCount(CardValue val) {
		return getCardsByValue(val).size();
	}
	
	
	// Return all playables in this hand
	public Set<Playable> getFreeChoicePlayables() throws Exception {
		Set<Playable> results = new HashSet<Playable>();
		Card toBeat = Card.lowestCard(); // Free choice means anything goes
		// Add n of a kind type playables
		for (PlayMode m : PlayMode.getNOfAKindModes()) {
			results.addAll(getLegalNOfAKind(m, toBeat));
		}
		// Add run type playables
		for (PlayMode m : PlayMode.getRunModes()) {
			results.addAll(getLegalRuns(m, toBeat));
		}
		return results;
	}
	
	
	// Return all playables for as specified playmode and card to beat
	public Set<Playable> getPlayables(PlayMode playMode, Card toBeat) throws Exception {
		if (playMode.isNOfAKind()) {
			return getLegalNOfAKind(playMode, toBeat);
		}
		else if (playMode.isRun()) {
			return getLegalRuns(playMode, toBeat);
		}
		else {
			throw new Exception("Invalid playmode passed to getPlayables");
		}
	}
	

	private List<Card> getCardsByValue(CardValue val) {
		List<Card> valMatches = this.cards.stream().filter(c -> c.getValue().equals(val)).collect(Collectors.toList());
		return valMatches;
	}
	
	
	// Get a list of all Playables for an "X of a kind" playmode (including singles).
	public Set<Playable> getLegalNOfAKind(PlayMode playMode, Card highCard) throws Exception {
		int n = playMode.getNOfAKindCount();
		Set<Playable> res = new HashSet<Playable>();
		for (CardValue val : CardValue.values()) {

			// Only want values >= the high card's value
			if (val.compareTo(highCard.getValue()) >= 0) {
				List<Card> valMatches = cards.stream()
												.filter(c -> c.getValue().equals(val))
												.collect(Collectors.toList());
				
				// Get all the different subset possibilities
				Set<Playable> playables = getSubsetsOfSizeN(valMatches, n);

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
	
	
	//TODO: Rewrite to be cleaner -> recursion? Could probably follow approach used for runs
	// Handles getting X of a kind type Playables. For doubles and triples, returns all combinations
	// possible. Eg, having four threes in a hand gives six ways to play double threes.
	private Set<Playable> getSubsetsOfSizeN(List<Card> cardList, int n) throws Exception {
		
		HashSet<Playable> moves = new HashSet<Playable>();

		if (cardList.isEmpty()) {
			return moves;
		}

		int fixedIdx = 0;
		
		// Singles case
		if (n == 1) {
			for (Card c : cardList) {
				Playable move = new Playable(Arrays.asList(c), PlayMode.SINGLES, c);
				moves.add(move);
			}
		}
		
		// Doubles case
		else if (n == 2) {
			while (fixedIdx <= cardList.size() - n) {
				for (Card elem : cardList.subList(fixedIdx + 1, cardList.size())) {
					HashSet<Card> moveCards = new HashSet<Card>();
					moveCards.add(cardList.get(fixedIdx));
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
			while (fixedIdx <= cardList.size() - n) {
				while (fixedIdx2 <= cardList.size() - n + 1) {
					for (Card elem2: cardList.subList(fixedIdx2 + 1, cardList.size())) {
						HashSet<Card> moveCards = new HashSet<Card>();
						moveCards.add(cardList.get(fixedIdx));
						moveCards.add(cardList.get(fixedIdx2));
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
			// only make a valid Playable if there are 4 cards of the same value
			if (cardList.size() == 4) {
				Playable move = new Playable(cardList, PlayMode.QUADRUPLES, Collections.max(cardList));
				moves.add(move);
			}
		}
		
		// Otherwise it's an error
		else {
			throw new Exception("invalid subset size");
		}
		return moves;
	}
	
	
	public Set<Playable> getLegalRuns(PlayMode playMode, Card highCard) throws Exception {
		Set<Playable> legalRuns = new HashSet<Playable>();
		int runLength = playMode.getRunLength();
		for (CardValue val : CardValue.cardValues) {
			
			// Only care about values that are >= the highCard's value
			if (val.compareTo(highCard.getValue()) >= 0) {
				List<CardValue> runVals = CardValue.getRunValues(runLength, val);
				
				// Check if the hand contains this particular run
				if (this.getCardValues().containsAll(runVals)) {
					// Get all runs of these values that are in the hand
					Set<Playable> runs = getRunVariations(runVals, playMode);
					
					// Filter one more time to handle suit issues
					runs = runs.stream().filter(p -> p.getHighCard().compareTo(highCard) >= 0).collect(Collectors.toSet());
					
					// Add these runs to the final result
					legalRuns.addAll(runs);
				}
			}
		}
		return legalRuns;
	}
	
	
	// For the run specified by the given list of CardValues, return a set containing
	// all such runs playable from this hand. If the hand contains multiple cards with the same value,
	// any one of these cards can be used in the run.
	// Approach: compute number of combos -> make that many empty lists -> for each value, iterate over cards with
	// that value and add them to the combo lists
	
	
	
	private Set<Playable> getRunVariations(List<CardValue> runVals, PlayMode playMode) {
		// First we compute the total number of combos we'll have. This is just the product
		// of frequencies.
		int numCombos = 1;
		for (CardValue val : runVals) {
			numCombos *= getValueCount(val);
		}
		
		// Initialize this many empty lists
		ArrayList<ArrayList<Card>> allCombos = new ArrayList<ArrayList<Card>>(numCombos);
		for (int i = 0; i < numCombos; i++) {
			allCombos.add(new ArrayList<Card>());
		}
		
		boolean lastAlternating = true;
		// For a given value, add each of the different cards of that value (differing in suit) to a combo list
		for (CardValue currentVal : runVals) {
			List<Card> valMatches = getCardsByValue(currentVal);
			
			if (lastAlternating) {
				// Add each card to repeatSize of the combos
				int repeatSize = numCombos / valMatches.size();
				for (int i = 0; i < allCombos.size(); i++) {
					ArrayList<Card> currCombo = allCombos.get(i);

					//integer division maps the i (index over allCombos) to the correct index over valMatches

					currCombo.add(valMatches.get(i / repeatSize)); 
				}
				lastAlternating = valMatches.size() > 1 ? false : true;
			}
			else {
				for (int i = 0; i < allCombos.size(); i++) {
					ArrayList<Card> currCombo = allCombos.get(i);

					//modulo maps the i (index over allCombos) to the correct index over valMatches

					currCombo.add(valMatches.get(i % valMatches.size())); 
				}
				lastAlternating = valMatches.size() > 1 ?  true : false;
			}
		}
		
		// If there are no combos, just return the empty set
		int comboCount = allCombos.stream().mapToInt(ArrayList::size).sum(); // total number of combos
		if (comboCount == 0) {
			return new HashSet<Playable>();
		}
		
		// Convert to a set of Playables and return
		Set<Playable> allPlayables = allCombos.stream()
											  .map(cards -> new Playable(cards, playMode, Collections.max(cards)))
											  .collect(Collectors.toSet());
		return allPlayables;
	}
	
	
	public void playMove(Playable move) {
		Collection<Card> moveCards = move.getCards();
		this.cards.removeAll(moveCards);
	}
	
	
	public static void main(String[] args) throws Exception {
		List<Card> handCards = new ArrayList<Card>();
		handCards.add(new Card(Suit.CLUBS, CardValue.THREE));
		handCards.add(new Card(Suit.HEARTS, CardValue.FOUR));
		handCards.add(new Card(Suit.CLUBS, CardValue.SIX));
		handCards.add(new Card(Suit.HEARTS, CardValue.EIGHT));
		handCards.add(new Card(Suit.CLUBS, CardValue.NINE));
		handCards.add(new Card(Suit.CLUBS, CardValue.TEN));
		handCards.add(new Card(Suit.DIAMONDS, CardValue.TEN));
		handCards.add(new Card(Suit.SPADES, CardValue.JACK));
		handCards.add(new Card(Suit.CLUBS, CardValue.QUEEN));
		handCards.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
		handCards.add(new Card(Suit.CLUBS, CardValue.ACE));
		handCards.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		handCards.add(new Card(Suit.HEARTS, CardValue.TWO));
		Hand hand = new Hand(handCards);
		Card lowCard = new Card(Suit.SPADES, CardValue.THREE);
		for (Playable p : hand.getLegalRuns(PlayMode.RUN5, lowCard)) {
			System.out.println(p);
		}
	}
}
