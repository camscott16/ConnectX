package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 *
 */
public class ConnectXController {

	/**
	 * The current game that is being played
	 */
	private IGameBoard curGame;

	/**
	 * The screen that provides our view
	 */
	private ConnectXView screen;

	/**
	 * Constant for the maximum number of players.
	 */
	public static final int MAX_PLAYERS = 10;

	/**
	 * The number of players for this game. Note that our player tokens are hard coded.
	 */
	private int numPlayers;

	private int curPlayer;

	private char[] playerTokens = {'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

	/**
	 * This creates a controller for running the Extended ConnectX game
	 * 
	 * @param model
	 *      The board implementation
	 * @param view
	 *      The screen that is shown
	 * @param np
	 *      The number of players for this game.
	 * 
	 * @post [ the controller will respond to actions on the view using the model. ]
	 */
	public ConnectXController(IGameBoard model, ConnectXView view, int np) {
		this.curGame = model;
		this.screen = view;
		this.numPlayers = np;

	}

	/**
	 * This processes a button click from the view.
	 * 
	 * @param col 
	 *      The column of the activated button
	 * 
	 * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
	 * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
	 * game hitting any button ]
	 */
	public void processButtonClick(int col) {
		if(curGame.checkForWin(col))
		{
			newGame();
			return;
		}
		// Checks for tie
		if(curGame.checkTie())
		{
			newGame();
			return;
		}
		// Prompt current player to move
		screen.setMessage("Player " + playerTokens[(curPlayer+1)%numPlayers] + ", it is your turn");
		if(!curGame.checkIfFree(col)){
			screen.setMessage("Pick a different column.");
		}
		// Actual placing code
		else if(curGame.checkIfFree(col) && !curGame.checkForWin(col) )
		{
			// loop that handles finding the correct row position
			int r = -1;
			for(int i = 0; i < curGame.getNumRows() && r == -1; i++) {
				BoardPosition pos = new BoardPosition(i, col);
				if(curGame.whatsAtPos(pos) == ' ')
					r = i;

			}

			// places token on model and view
			curGame.dropToken(playerTokens[curPlayer%numPlayers], col);
			screen.setMarker(r, col, playerTokens[curPlayer%numPlayers]);
			curPlayer++;
		}
		// Checks tie condition
		if(curGame.checkTie())
		{
			screen.setMessage("You Tied. Click any button for a new game.");
		}
		// checks for win condition
		if(curGame.checkForWin(col))
		{
			curPlayer--; // decrements current player
			screen.setMessage("Player " + playerTokens[curPlayer % numPlayers] + " Wins! Click any Button to start a new game");
		}

	}

	/**
	 * This method will start a new game by returning to the setup screen and controller
	 * 
	 * @post [ a new game gets started ]
	 */
	private void newGame() {

		//close the current screen
		screen.dispose();

		//start back at the set up menu
		SetupView screen = new SetupView();
		SetupController controller = new SetupController(screen);
		screen.registerObserver(controller);
	}
}
