package test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.Card;
import game.CardValue;
import game.Player;
import game.Suit;

public class TestPlayer {
	private Player player;
	
	@Before
	public void setUp() {
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.DIAMONDS, CardValue.EIGHT));
		cards.add(new Card(Suit.SPADES, CardValue.TWO));
		cards.add(new Card(Suit.HEARTS, CardValue.SEVEN));
		cards.add(new Card(Suit.CLUBS, CardValue.JACK));
		cards.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
		this.player = new Player(cards);
	}

}
