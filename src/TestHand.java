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

// TODO: make the testing setup cleaner; as it is, changing the 
// cards in the hands could mess up everything
public class TestHand {
	private Hand handNOfAKind;
	private List<Card> cardsNOfAKind;
	private Hand handRuns;
	private List<Card> cardsRuns;

	
	@Before
	public void setup() {
		// Setup for N of a kind testing
		cardsNOfAKind = new ArrayList<Card>();
		cardsNOfAKind.add(new Card(Suit.HEARTS, CardValue.ACE));
		cardsNOfAKind.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		cardsNOfAKind.add(new Card(Suit.CLUBS, CardValue.ACE));
		cardsNOfAKind.add(new Card(Suit.SPADES, CardValue.ACE));
		cardsNOfAKind.add(new Card(Suit.CLUBS, CardValue.EIGHT));
		cardsNOfAKind.add(new Card(Suit.HEARTS, CardValue.EIGHT));
		cardsNOfAKind.add(new Card(Suit.SPADES, CardValue.EIGHT));
		cardsNOfAKind.add(new Card(Suit.DIAMONDS, CardValue.SIX));
		cardsNOfAKind.add(new Card(Suit.SPADES, CardValue.SIX));
		handNOfAKind = new Hand(cardsNOfAKind);
		
		// Setup for runs testing
		cardsRuns = new ArrayList<Card>();
		cardsRuns.add(new Card(Suit.HEARTS, CardValue.THREE));
		cardsRuns.add(new Card(Suit.HEARTS, CardValue.FOUR));
		cardsRuns.add(new Card(Suit.DIAMONDS, CardValue.FOUR));
		cardsRuns.add(new Card(Suit.DIAMONDS, CardValue.FIVE));
		cardsRuns.add(new Card(Suit.CLUBS, CardValue.FIVE));
		cardsRuns.add(new Card(Suit.SPADES, CardValue.FIVE));
		cardsRuns.add(new Card(Suit.DIAMONDS, CardValue.SIX));
		handRuns = new Hand(cardsRuns);
	}
	
	@Test
	public void testGetLowestCard() {
		assertEquals(handNOfAKind.getLowestCard(), new Card(Suit.SPADES, CardValue.SIX));
	}


	@Test
	public void testGetLegalSingles() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.SEVEN);
		Set<Playable> correct = new HashSet<Playable>();
		for (Card c : cardsNOfAKind.subList(0, 7)) {
			Playable move = new Playable(Arrays.asList(c), PlayMode.SINGLES, c);
			correct.add(move);
		}
		Set<Playable> singles = handNOfAKind.getLegalNOfAKind(1, toBeat);
		assertEquals(correct, singles);
		singles = handNOfAKind.getLegalNOfAKind(1, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, singles.size());
	}
	
	
	@Test
	public void testGetLegalDoubles() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.NINE);
		Set<Playable> correct = new HashSet<Playable>();
		Collection<Card> doubles1 = Arrays.asList(new Card[] {cardsNOfAKind.get(0), cardsNOfAKind.get(1)});
		Collection<Card> doubles2 = Arrays.asList(new Card[] {cardsNOfAKind.get(0), cardsNOfAKind.get(2)});
		Collection<Card> doubles3 = Arrays.asList(new Card[] {cardsNOfAKind.get(0), cardsNOfAKind.get(3)});
		Collection<Card> doubles4 = Arrays.asList(new Card[] {cardsNOfAKind.get(1), cardsNOfAKind.get(2)});
		Collection<Card> doubles5 = Arrays.asList(new Card[] {cardsNOfAKind.get(1), cardsNOfAKind.get(3)});
		Collection<Card> doubles6 = Arrays.asList(new Card[] {cardsNOfAKind.get(2), cardsNOfAKind.get(3)});
		Playable move1 = new Playable(doubles1, PlayMode.DOUBLES, cardsNOfAKind.get(0));
		Playable move2 = new Playable(doubles2, PlayMode.DOUBLES, cardsNOfAKind.get(0));
		Playable move3 = new Playable(doubles3, PlayMode.DOUBLES, cardsNOfAKind.get(0));
		Playable move4 = new Playable(doubles4, PlayMode.DOUBLES, cardsNOfAKind.get(1));
		Playable move5 = new Playable(doubles5, PlayMode.DOUBLES, cardsNOfAKind.get(1));
		Playable move6 = new Playable(doubles6, PlayMode.DOUBLES, cardsNOfAKind.get(2));
		correct.add(move1);
		correct.add(move2);
		correct.add(move3);
		correct.add(move4);
		correct.add(move5);
		correct.add(move6);
		Set<Playable> doubles = handNOfAKind.getLegalNOfAKind(2, toBeat);
		assertEquals(doubles, correct);
		doubles = handNOfAKind.getLegalNOfAKind(2, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, doubles.size());
	}
	
	@Test
	public void testGetLegalTriples() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.TEN);
		Set<Playable> correct = new HashSet<Playable>();
		Collection<Card> trip1 = Arrays.asList(new Card[] {cardsNOfAKind.get(0), cardsNOfAKind.get(1), cardsNOfAKind.get(2)});
		Collection<Card> trip2 = Arrays.asList(new Card[] {cardsNOfAKind.get(0), cardsNOfAKind.get(1), cardsNOfAKind.get(3)});
		Collection<Card> trip3 = Arrays.asList(new Card[] {cardsNOfAKind.get(0), cardsNOfAKind.get(2), cardsNOfAKind.get(3)});
		Collection<Card> trip4 = Arrays.asList(new Card[] {cardsNOfAKind.get(1), cardsNOfAKind.get(2), cardsNOfAKind.get(3)});
		Playable move1 = new Playable(trip1, PlayMode.TRIPLES, cardsNOfAKind.get(0));
		Playable move2 = new Playable(trip2, PlayMode.TRIPLES, cardsNOfAKind.get(0));
		Playable move3 = new Playable(trip3, PlayMode.TRIPLES, cardsNOfAKind.get(0));
		Playable move4 = new Playable(trip4, PlayMode.TRIPLES, cardsNOfAKind.get(1));
		correct.add(move1);
		correct.add(move2);
		correct.add(move3);
		correct.add(move4);
		Set<Playable> triples = handNOfAKind.getLegalNOfAKind(3, toBeat);
		assertEquals(triples, correct);
		triples = handNOfAKind.getLegalNOfAKind(3, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, triples.size());
	}
	
	@Test
	public void testGetLegalQuadruples() throws Exception {
		Card toBeat = new Card(Suit.CLUBS, CardValue.FIVE);
		Set<Playable> correct = new HashSet<Playable>();
		Collection<Card> quad = cardsNOfAKind.subList(0, 4);
		correct.add(new Playable(quad, PlayMode.QUADRUPLES, cardsNOfAKind.get(0)));
		Set<Playable> quads = handNOfAKind.getLegalNOfAKind(4, toBeat);
		assertEquals(quads, correct);
		quads = handNOfAKind.getLegalNOfAKind(4, new Card(Suit.HEARTS, CardValue.TWO));
		assertEquals(0, quads.size());
	}
	
	
	@Test
	public void testGetLegalRuns() throws Exception {
	Set<Playable> correct = new HashSet<Playable>();
	Collection<Card> cards1 = Arrays.asList(new Card[] {cardsRuns.get(0), cardsRuns.get(1), cardsRuns.get(3), cardsRuns.get(6)});
	Collection<Card> cards2 = Arrays.asList(new Card[] {cardsRuns.get(0), cardsRuns.get(1), cardsRuns.get(4), cardsRuns.get(6)});
	Collection<Card> cards3 = Arrays.asList(new Card[] {cardsRuns.get(0), cardsRuns.get(1), cardsRuns.get(5), cardsRuns.get(6)});
	Collection<Card> cards4 = Arrays.asList(new Card[] {cardsRuns.get(0), cardsRuns.get(2), cardsRuns.get(3), cardsRuns.get(6)});
	Collection<Card> cards5 = Arrays.asList(new Card[] {cardsRuns.get(0), cardsRuns.get(2), cardsRuns.get(4), cardsRuns.get(6)});
	Collection<Card> cards6 = Arrays.asList(new Card[] {cardsRuns.get(0), cardsRuns.get(2), cardsRuns.get(5), cardsRuns.get(6)});
	Playable run1 = new Playable(cards1, PlayMode.RUN4, cardsRuns.get(6));
	Playable run2 = new Playable(cards2, PlayMode.RUN4, cardsRuns.get(6));
	Playable run3 = new Playable(cards3, PlayMode.RUN4, cardsRuns.get(6));
	Playable run4 = new Playable(cards4, PlayMode.RUN4, cardsRuns.get(6));
	Playable run5 = new Playable(cards5, PlayMode.RUN4, cardsRuns.get(6));
	Playable run6 = new Playable(cards6, PlayMode.RUN4, cardsRuns.get(6));
	correct.add(run1);
	correct.add(run2);
	correct.add(run3);
	correct.add(run4);
	correct.add(run5);
	correct.add(run6);
	Set<Playable> runs = handRuns.getLegalRuns(4, new Card(Suit.CLUBS, CardValue.SIX), PlayMode.RUN4);
	System.out.println(correct);
	System.out.println(runs);
	assertEquals(runs, correct);
	}
}