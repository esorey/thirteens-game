package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GameState {
	private Player[] players;
	private PlayMode activePlayMode;
	private int activePlayerIdx;
	private int lastPlayerToPlayIdx;
	private Deck deck;
	private Playable moveToBeat;
	private boolean isDone;
	
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
		moveToBeat = null;
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
				isDone = true;
				return true;
			}
		}
		return false;
	}
	
	private int getWinnerId() throws Exception {
		if (!this.isDone) {
			throw new Exception("Game is not yet over!");
		}
		else {
			for (int i = 0; i < players.length; i++) {
				if (players[i].isOutOfCards()) {
					return i;
				}
			}
		}
		return -1;
	}
	
	
	private void takeTurn() throws Exception {
		// Display current game status
		Player activePlayer = players[activePlayerIdx];
		System.out.println("Active: Player " + Integer.toString(activePlayerIdx));
		String moveToBeatStr = "[]";
		if (moveToBeat != null) {
			moveToBeatStr = moveToBeat.toString();
		}
		System.out.println("Move to beat: " + moveToBeatStr);
		System.out.println("Hand: " + activePlayer.getCards());
		
		// Display legal playables
		System.out.println("Legal Playables:");
		Card lastPlayedHighCard = Card.lowestCard();
		if (moveToBeat != null) {
			lastPlayedHighCard = moveToBeat.getHighCard();
		}
		List<Playable> legalPlayables = new ArrayList<Playable>(activePlayer.getLegalPlayables(activePlayMode, lastPlayedHighCard));
		
		if (legalPlayables.isEmpty()) {
			System.out.println("No available moves - passing...\n\n");
			activePlayerIdx = (activePlayerIdx + 1) % players.length;
			if (activePlayerIdx == lastPlayerToPlayIdx) {
				activePlayMode = PlayMode.FREE_CHOICE;
				moveToBeat = null;
			}
		}
		
		else {
			// Provide the option to pass
			System.out.println("[0] : Pass");
			
			// Sort the options
			Collections.sort(legalPlayables, new SortPlayables());
			
			
			// Print the legal options
			for (int i = 0; i < legalPlayables.size(); i++) {
				System.out.println(String.format("[%d] : %s", i + 1, legalPlayables.get(i).toString()));
			}
			
			// Prompt the user to choose a playable
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Choose a move: ");
			int n = reader.nextInt(); // Scans the next token of the input as an int.
			
			if (n == 0) { // The player opts to pass
				activePlayerIdx = (activePlayerIdx + 1) % players.length;

				if (activePlayerIdx == lastPlayerToPlayIdx) {
					activePlayMode = PlayMode.FREE_CHOICE;
					moveToBeat = null;
				}
				System.out.println("\n\n");
				return;
			}
			Playable choice = legalPlayables.get(n-1);
			System.out.println("\n\n");
			// TODO: figure out how to close scanner without also closing System.in
			//reader.close();
			
			// Play the move
			activePlayer.playMove(choice);
			
			// Update the game state
			activePlayMode = choice.getPlayMode();
			moveToBeat = choice;
			lastPlayerToPlayIdx = activePlayerIdx;
			activePlayerIdx = (activePlayerIdx + 1) % players.length;
		}
		
	}

	public static void main(String[] args) throws Exception {
		
		// Initialize the game
		int nPlayers = 2;//Integer.valueOf(args[0]);
		GameState game = new GameState(nPlayers);
		
		// Keep having turns until there's a winner
		while (!game.hasWinner()) {
			game.takeTurn();
		}
		
		System.out.println(String.format("GAME OVER: Player %d wins!", game.getWinnerId()));

		
	}
}
