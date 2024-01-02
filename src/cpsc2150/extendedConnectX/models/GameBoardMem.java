package cpsc2150.extendedConnectX.models;
import cpsc2150.extendedConnectX.models.AbsGameBoard;
import cpsc2150.extendedConnectX.models.BoardPosition;

import java.util.*;

/*
 * Sam Wolfe - samwvlfe
 * Trey Larkins - tlark13
 * Jake Eklund - jake-eklund
 * Cam Scott - camscott16
 */


/**
 * Hash map table representing the gameboard
 * 
 * @invariant board != null
 * @invariant numRows > 0
 * @invariant numColumns > 0
 * @invariant numToWin > 0
 * 
 *            Correspondence:
 *            number_of_rows = numRows
 *            number_of_columns = numColumns
 *            number_to_win = numToWin
 */
public class GameBoardMem extends AbsGameBoard {
    int numRows;
    int numColumns;
    int numToWin;

    Map<Character, List<BoardPosition> > board;

    /**
     * constructor for GameBoardMem object
     * 
     * @pre rows > 0 && columns > 0 && inARow > 0
     * 
     * @post [initializes a game-board with the set number of rows, columns,
     *       and number of tokens needed to win] AND [all spaces on
     *       game-board will be empty] AND numRows = rows AND
     *       numColumns = columns AND numToWin = inARow
     *
     * @param rows number of rows the game board will have
     * @param columns number of columns the game board will have
     * @param inARow number of tile in a row to win the game
     */

    public GameBoardMem(int rows, int columns, int inARow) {
        this.numRows = rows;
        this.numColumns = columns;
        this.numToWin = inARow;

        board = new HashMap<>();
    }

    /**
     * gets the number of rows in the board
     *
     * @return value stored for number_of_rows
     *
     * @pre None
     *
     * @post getNumRows = number_of_rows AND number_of_rows = #number_of_rows AND
     *       number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    @Override
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * gets the number of columns in the board
     *
     * @return value stored for number_of_columns
     *
     * @pre None
     *
     * @post getNumColumns = number_of_columns AND number_of_rows = #number_of_rows AND
     *       number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    @Override
    public int getNumColumns() {
        return this.numColumns;
    }

    /**
     * gets the number of tokens in a row needed to win the game
     *
     * @return value stored for number_to_win
     *
     * @pre None
     *
     * @post getNumToWin = number_to_win AND number_of_rows = #number_of_rows AND
     *       number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    @Override
    public int getNumToWin() {
        return this.numToWin;
    }

    /**
     * places player token in the user-selected column with the token falling to the first unoccupied space
     *
     * @param p string representing the player token
     *
     * @param c integer representing column number
     *
     * @pre (p = 'X' OR p = 'O') AND (c >= 0 AND c < number_of_columns)
     *
     * @post [self = #self with char p in the lowest unoccupied row of column c] AND number_of_rows = #number_of_rows
     *       AND number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    public void dropToken(char p, int c) {
        if (!board.containsKey(p)) { // finish
            board.put(p, new ArrayList<>());
        }

        for (int i = 0; i < getNumRows(); i++) {
            if (whatsAtPos(new BoardPosition(i, c)) == ' ') {
                board.get(p).add(new BoardPosition(i, c));
                break;
            }
        }
    }

     /**
     * detects what character is within the specified board position
     *
     * @param pos position on the game board to be checked
     * 
     * @return character detected in the position taken in, blank if no token
     *
     * @pre [corresponding row of pos < number_of_rows] AND [corresponding column of pos < number_of_columns]
     *
     * @post self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns
     *       AND number_to_win = #number_to_win
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        for (HashMap.Entry<Character, List<BoardPosition> > x : board.entrySet()) {
            if ((x.getValue()).contains(pos)) {
                return x.getKey();
            }
        }
        return ' ';
    }

    /**
     * checks if a specified player is in the specified position on the Game Board
     *
     * @param pos position of the last played token on the board
     *
     * @param player token to be checked if they are in the position
     *
     * @return true if the specified player is in the given Game Board position, false OW
     * 
     * @pre ([corresponding row of pos < number_of_rows] AND [corresponding column of pos < number_of_columns])
     *      AND (player = 'X' OR player = 'O')
     * 
     * @post [isPlayerAtPosition = true if whatsAtPos(pos) == player, false OW] AND self = #self
     *       AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        return board.containsKey(player) && board.get(player).contains(pos);
    }
}