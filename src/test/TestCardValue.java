package test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import game.CardValue;

public class TestCardValue {

	@Test
	public void testGetValidRunValues() throws Exception {
		CardValue[] correctArr = new CardValue[] {CardValue.THREE, 
		                                       	  CardValue.FOUR,
		                                       	  CardValue.FIVE, 
		                                       	  CardValue.SIX, 
		                                       	  CardValue.SEVEN};
		List<CardValue> correct = Arrays.asList(correctArr);
		assertEquals(correct, CardValue.getRunValues(5, CardValue.SEVEN));
		
	}
	
	@Test
	public void testGetInvalidRunValues() {
		// Malformed runs should return an empty list of card values
		assertTrue(CardValue.getRunValues(5, CardValue.FOUR).isEmpty());
	}

}
