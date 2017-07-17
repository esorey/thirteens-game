import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestCardValue {

	// TODO: figure out how to make sure that an invalid run
	// throws an error
	@Test
	public void testGetRunValues() throws Exception {
		CardValue[] correctArr = new CardValue[] {CardValue.THREE, 
		                                       	  CardValue.FOUR,
		                                       	  CardValue.FIVE, 
		                                       	  CardValue.SIX, 
		                                       	  CardValue.SEVEN};
		List<CardValue> correct = Arrays.asList(correctArr);
		assertEquals(correct, CardValue.getRunValues(5, CardValue.SEVEN));
	}

}
