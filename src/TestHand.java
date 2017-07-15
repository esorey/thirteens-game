import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestHand {
	private List<Card> cards;
	
	@Before
	public void setup() {
		cards = new ArrayList<Card>();
		cards.add(new Card(Suit.CLUBS, CardValue.ACE));
		cards.add(new Card(Suit.HEARTS, CardValue.JACK));
		cards.add(new Card(Suit.SPADES, CardValue.THREE));
		cards.add(new Card(Suit.DIAMONDS, CardValue.EIGHT));
	}

	@Test
	// TODO: fix this test
	public void testGetSubsetsOfSizeTwo() throws Exception {
		// Set up correct result
		HashSet<HashSet<Card>> correct = new HashSet<HashSet<Card>>();
		HashSet<Card> subset = new HashSet<Card>();
		subset.add(new Card(Suit.CLUBS, CardValue.ACE));
		subset.add(new Card(Suit.HEARTS, CardValue.JACK));
		correct.add(subset);
		subset = new HashSet<Card>();
		subset.add(new Card(Suit.CLUBS, CardValue.ACE));
		subset.add(new Card(Suit.SPADES, CardValue.THREE));
		correct.add(subset);
		subset = new HashSet<Card>();
		subset.add(new Card(Suit.CLUBS, CardValue.ACE));
		subset.add(new Card(Suit.DIAMONDS, CardValue.EIGHT));
		correct.add(subset);
		subset = new HashSet<Card>();
		subset.add(new Card(Suit.HEARTS, CardValue.JACK));
		subset.add(new Card(Suit.SPADES, CardValue.THREE));
		correct.add(subset);
		subset = new HashSet<Card>();
		subset.add(new Card(Suit.HEARTS, CardValue.JACK));
		subset.add(new Card(Suit.DIAMONDS, CardValue.EIGHT));
		correct.add(subset);
		subset = new HashSet<Card>();
		subset.add(new Card(Suit.SPADES, CardValue.THREE));
		subset.add(new Card(Suit.DIAMONDS, CardValue.EIGHT));
		correct.add(subset);
		
		// Test the function
		HashSet<HashSet<Card>> res = SubsetMaker.getSubsetsOfSizeN(cards, 2);
		System.out.println(correct);
		System.out.println(res);
		assertTrue(res.equals(correct));
	}
	
	// Old test code; may be useful later
	/*public static void main(String[] args) throws Exception {
		ArrayList<Card> input = new ArrayList<Card>();
		input.add(new Card(Suit.CLUBS, CardValue.THREE));
		input.add(new Card(Suit.CLUBS, CardValue.FOUR));
		input.add(new Card(Suit.CLUBS, CardValue.FIVE));
		input.add(new Card(Suit.CLUBS, CardValue.SIX));
		HashSet<Playable> res = getSubsetsOfSizeN(input, 2);
		System.out.println(res);
		res = getSubsetsOfSizeN(input, 3);
		System.out.println(res);
	}*/


}
