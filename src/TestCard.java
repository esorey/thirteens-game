import static org.junit.Assert.*;

import org.junit.Test;

public class TestCard {

	@Test
	public void testCardComparison() {
		Card diamonds3 = new Card(Suit.DIAMONDS, CardValue.THREE);
		Card spades2 = new Card(Suit.SPADES, CardValue.TWO);
		assertTrue(diamonds3.compareTo(spades2) < 0);
		assertTrue(spades2.compareTo(diamonds3) > 0);
		assertTrue(spades2.compareTo(spades2) == 0);
	}

}
