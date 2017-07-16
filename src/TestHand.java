import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
		cards.add(new Card(Suit.HEARTS, CardValue.ACE));
		cards.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		cards.add(new Card(Suit.CLUBS, CardValue.ACE));
		cards.add(new Card(Suit.SPADES, CardValue.ACE));
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
		singles = hand.getLegalNOfAKind(1, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, singles.size());
	}
	
	
	// TODO: finish this test and make others for triples and quads
	@Test
	public void testGetLegalDoubles() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.NINE);
		Set<Playable> correct = new HashSet<Playable>();
		Collection<Card> doubles1 = Arrays.asList(new Card[] {cards.get(0), cards.get(1)});
		Collection<Card> doubles2 = Arrays.asList(new Card[] {cards.get(0), cards.get(2)});
		Collection<Card> doubles3 = Arrays.asList(new Card[] {cards.get(0), cards.get(3)});
		Collection<Card> doubles4 = Arrays.asList(new Card[] {cards.get(1), cards.get(2)});
		Collection<Card> doubles5 = Arrays.asList(new Card[] {cards.get(1), cards.get(3)});
		Collection<Card> doubles6 = Arrays.asList(new Card[] {cards.get(2), cards.get(3)});
		Playable move1 = new Playable(doubles1, PlayMode.DOUBLES, cards.get(0));
		Playable move2 = new Playable(doubles2, PlayMode.DOUBLES, cards.get(0));
		Playable move3 = new Playable(doubles3, PlayMode.DOUBLES, cards.get(0));
		Playable move4 = new Playable(doubles4, PlayMode.DOUBLES, cards.get(1));
		Playable move5 = new Playable(doubles5, PlayMode.DOUBLES, cards.get(1));
		Playable move6 = new Playable(doubles6, PlayMode.DOUBLES, cards.get(2));
		correct.add(move1);
		correct.add(move2);
		correct.add(move3);
		correct.add(move4);
		correct.add(move5);
		correct.add(move6);
		Set<Playable> doubles = hand.getLegalNOfAKind(2, toBeat);
		assertEquals(doubles, correct);
		doubles = hand.getLegalNOfAKind(2, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, doubles.size());
	}
	
	@Test
	public void testGetLegalTriples() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.TEN);
		Set<Playable> correct = new HashSet<Playable>();
		Collection<Card> trip1 = Arrays.asList(new Card[] {cards.get(0), cards.get(1), cards.get(2)});
		Collection<Card> trip2 = Arrays.asList(new Card[] {cards.get(0), cards.get(1), cards.get(3)});
		Collection<Card> trip3 = Arrays.asList(new Card[] {cards.get(0), cards.get(2), cards.get(3)});
		Collection<Card> trip4 = Arrays.asList(new Card[] {cards.get(1), cards.get(2), cards.get(3)});
		Playable move1 = new Playable(trip1, PlayMode.TRIPLES, cards.get(0));
		Playable move2 = new Playable(trip2, PlayMode.TRIPLES, cards.get(0));
		Playable move3 = new Playable(trip3, PlayMode.TRIPLES, cards.get(0));
		Playable move4 = new Playable(trip4, PlayMode.TRIPLES, cards.get(1));
		correct.add(move1);
		correct.add(move2);
		correct.add(move3);
		correct.add(move4);
		Set<Playable> triples = hand.getLegalNOfAKind(3, toBeat);
		assertEquals(triples, correct);
		triples = hand.getLegalNOfAKind(3, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, triples.size());
	}
	
	@Test
	public void getLegalQuadruples() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.FIVE);
		Set<Playable> correct = new HashSet<Playable>();
		Collection<Card> quad = cards.subList(0, 4);
		correct.add(new Playable(quad, PlayMode.QUADRUPLES, cards.get(0)));
		Set<Playable> quads = hand.getLegalNOfAKind(4, toBeat);
		assertEquals(quads, correct);
		quads = hand.getLegalNOfAKind(4, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, quads.size());
	}
}