import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CardValue {
	THREE, 
	FOUR, 
	FIVE, 
	SIX, 
	SEVEN, 
	EIGHT, 
	NINE, 
	TEN, 
	JACK, 
	QUEEN, 
	KING, 
	ACE, 
	TWO;

	public static List<CardValue> cardValues = Arrays.asList(CardValue.values());
	
	// Return a list of CardValues for a specified run format
	public static List<CardValue> getRunValues(int runLength, CardValue highVal) throws Exception {
		int highValIdx = cardValues.indexOf(highVal); // get the index of the high value
		int startIdx = highValIdx - (runLength - 1); // compute the index of the low value
		if (startIdx < 0) { // invalid run length such as "run of length 4, with high value 5"
			throw new Exception("Invalid run parameters");
		}
		return cardValues.subList(startIdx, highValIdx + 1); // need the +1 because sublist is exclusive in 2nd arg
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(getRunValues(5, CardValue.EIGHT));
	}
	
}
