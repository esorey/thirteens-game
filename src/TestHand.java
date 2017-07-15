import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class TestHand {
	private Hand hand;
	private List<Card> cards;
	
	@Before
	public void setup() {
		cards = new ArrayList<Card>();
		cards.add(new Card(Suit.CLUBS, CardValue.ACE));
		cards.add(new Card(Suit.HEARTS, CardValue.ACE));
		cards.add(new Card(Suit.SPADES, CardValue.ACE));
		cards.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		cards.add(new Card(Suit.CLUBS, CardValue.EIGHT));
		cards.add(new Card(Suit.HEARTS, CardValue.EIGHT));
		cards.add(new Card(Suit.SPADES, CardValue.EIGHT));
		cards.add(new Card(Suit.DIAMONDS, CardValue.SIX));
		cards.add(new Card(Suit.SPADES, CardValue.SIX));
		hand = new Hand(cards);
	}
	
	@Test
	public void testGetLowestCard() {
		assertEquals(hand.getLowestCard(), new Card(Suit.SPADES, CardValue.SIX));
	}


	@Test
	public void testGetLegalSingles() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.SEVEN);
		Set<Playable> correct = new HashSet<Playable>();
		for (Card c : cards.subList(0, 7)) {
			Playable move = new Playable(Arrays.asList(c), PlayMode.SINGLES, c);
			correct.add(move);
		}
		Set<Playable> singles = hand.getLegalNOfAKind(1, toBeat);
		assertEquals(correct, singles);
	}
	
	
	// TODO: finish this test and make others for triples and quads
	@Test
	public void testGetLegalDoubles() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.NINE);
		Set<Playable> correct = new HashSet<Playable>();
		
	}
}
