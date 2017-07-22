package game;

import java.util.Comparator;

public class SortPlayables implements Comparator<Playable> {
	
	public int compare(Playable p1, Playable p2) {
		PlayMode m1 = p1.getPlayMode();
		PlayMode m2 = p2.getPlayMode();
		if (m1.compareTo(m2) == 0) {
			Card highCard1 = p1.getHighCard();
			Card highCard2 = p2.getHighCard();
			return (highCard1.compareTo(highCard2));
		}
		return m1.compareTo(m2);
	}

}
