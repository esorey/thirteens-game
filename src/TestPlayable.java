import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestPlayable {
	
	private List<Card> cards;

	@Before
	public void setup() {
		cards = new ArrayList<Card>();
		cards.add(new Card(Suit.HEARTS, CardValue.ACE));
		cards.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		cards.add(new Card(Suit.CLUBS, CardValue.ACE));
		cards.add(new Card(Suit.SPADES, CardValue.ACE));
	}
	
	
	@Test
	public void testEquality() {
		Playable p1 = new Playable(Arrays.asList(new Card[] {cards.get(1), cards.get(0)}), PlayMode.DOUBLES, cards.get(0));
		Playable p2 = new Playable(Arrays.asList(new Card[] {new Card(Suit.HEARTS, CardValue.ACE), new Card(Suit.DIAMONDS, CardValue.ACE)}), PlayMode.DOUBLES, new Card(Suit.HEARTS, CardValue.ACE));
		assertEquals(p1, p2);
		HashSet<Playable> s1 = new HashSet<Playable>();
		s1.add(p1);
		HashSet<Playable> s2 = new HashSet<Playable>();
		s2.add(p2);
		assertTrue(s1.equals(s2));
	}
	
}
