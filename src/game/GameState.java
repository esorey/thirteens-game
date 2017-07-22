package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GameState {
	private Player[] players;
	private PlayMode activePlayMode;
	private int activePlayerIdx;
	private int lastPlayerToPlayIdx;
	private Deck deck;
	private Card lastPlayedHighCard;
	
	// Sets up a new game
	public GameState(int nPlayers) throws IllegalArgumentException {
		
		if (nPlayers < 2 || nPlayers > 4) {
			throw new IllegalArgumentException("invalid number of players");
		}
		
		deck = new Deck();
		players = new Player[nPlayers];
		
		for (int i = 0; i < nPlayers; i++) {
			players[i] = new Player(deck, 13); // deal 13 cards to each player
		}
		
		activePlayMode = PlayMode.FREE_CHOICE;
		activePlayerIdx = getFirstPlayerIdx();
		lastPlayerToPlayIdx = getFirstPlayerIdx(); // Probably a more efficient way to do this
		lastPlayedHighCard = null;
	}
	
	// Returns the index of the player to go first. This is the player with the 
	// lowest card in play.
	private int getFirstPlayerIdx() {
		
		int lowestIdx = 0;
		Card lowestCard = players[0].getLowestCard();
		
		for (int i = 1; i < players.length; i++) {
			
			Card lowCard = players[i].getLowestCard();
			
			if (lowCard.compareTo(lowestCard) < 0) {
				lowestCard = lowCard;
				lowestIdx = i;
			} 
			
		}
		return lowestIdx;
	}
	
	private boolean hasWinner() {
		for (Player p : players) {
			if (p.isOutOfCards()) {
				return true;
			}
		}
		return false;
	}
	
	
	private void takeTurn() throws Exception {
		Player activePlayer = players[activePlayerIdx];
		System.out.println("Active: Player " + Integer.toString(activePlayerIdx));
		System.out.println("Play Mode: " + activePlayMode.toString());
		System.out.println("Hand: " + activePlayer.getCards());
		System.out.println("Legal Playables:");
		List<Playable> legalPlayables = new ArrayList<Playable>(activePlayer.getLegalPlayables(activePlayMode, lastPlayedHighCard));
		Collections.sort(legalPlayables, new SortPlayables());
		for (int i = 0; i < legalPlayables.size(); i++) {
			System.out.println(String.format("[%d] : %s", i, legalPlayables.get(i).toString()));
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		// Initialize the game
		int nPlayers = 2;//Integer.valueOf(args[0]);
		GameState game = new GameState(nPlayers);
		game.takeTurn();
		
		// Keep having turns until there's a winner
		/*while (!game.hasWinner()) {
			game.takeTurn();
		}*/
		
		
	}
}
