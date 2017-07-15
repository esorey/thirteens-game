import java.util.ArrayList;

public class GameState {
	private Player[] players;
	private PlayMode activePlayMode;
	private int activePlayerIdx;
	private int lastPlayerToPlayIdx;
	private Deck deck;
	private Playable lastPlayed;
	
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
		lastPlayed = null;
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
		boolean res = false;
		for (Player p : players) {
			if (p.isOutOfCards()) {
				return true;
			}
		}
		return false;
	}
	
	
	private void takeTurn() {
		Player activePlayer = players[activePlayerIdx];
		System.out.println("Active: Player " + Integer.toString(activePlayerIdx));
		System.out.println("Play Mode: " + activePlayMode.toString());
		System.out.println("Hand: " + activePlayer.hand.toString());
		Playable move = activePlayer.makeMove(lastPlayed, activePlayMode);
		
	}
	
	public static void main(String[] args) {
		
		// Initialize the game
		int nPlayers = Integer.valueOf(args[0]);
		GameState game = new GameState(nPlayers);
		game.takeTurn();
		
		// Keep having turns until there's a winner
		/*while (!game.hasWinner()) {
			game.takeTurn();
		}*/
		
		
	}
}
