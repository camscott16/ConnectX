package cpsc2150.extendedConnectX.models;

/*
 * Sam Wolfe - samwvlfe
 * Trey Larkins - tlark13
 * Jake Eklund - jake-eklund
 * Cam Scott - camscott16
 */

import cpsc2150.extendedConnectX.models.AbsGameBoard;
import cpsc2150.extendedConnectX.models.BoardPosition;

/**
 * 2-dimensional array of characters to represent the Game Board
 *
 * @invariant (board[][] = [unique user-defined character] OR board[][] = ' ') AND
 * 
 *
 * @invariant numRow > 0
 *
 * @invariant numColumns > 0
 *
 * @invariant numToWin > 0
 *
 *            Correspondence:
 *            number_of_rows = numRow
 *            number_of_columns = numColumns
 *            number_to_win = numToWin
 */

public class GameBoard extends AbsGameBoard {

    int numRows;
    int numColumns;
    int numToWin;
    char[][] board; // create board

    /**
     * constructor for the GameBoard object
     *
     * @pre None
     * @post [GameBoard object will be initialized] AND [each board space contains
     *       empty char ' '] AND numRows = rows AND numColumns = columns AND
     *       numToWin = inARow
     *
     * @param rows number of rows the game board will have
     * @param columns number of columns the game board will have
     * @param inARow number of tile in a row to win the game
     */

    public GameBoard(int rows, int columns, int inARow) {

        this.numRows = rows;
        this.numColumns = columns;
        this.numToWin = inARow;

        board = new char[this.numRows][this.numColumns];

        for (int i = 0; i < getNumRows(); i++) { // iterate through rows
            for (int j = 0; j < getNumColumns(); j++) { // iterate through columns
                board[i][j] = ' '; // init all board values to ' '
            }
        }

    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumColumns() {
        return this.numColumns;
    }

    @Override
    public int getNumToWin() {
        return this.numToWin;
    }

    public void dropToken(char p, int c) {
        // places the character p in column c. The token will be placed in the lowest
        // available row in column c.

        int i = 0; // int to iterate through each row

        while (board[i][c] != ' ') { // while row detected as occupied
            i++; // go to next row
        }

        board[i][c] = p; // put the player token in the first available row of the given column

    }

    public char whatsAtPos(BoardPosition pos) {
        // returns what is in the GameBoard at position pos If no marker is there, it
        // returns a blank space char.
        return board[pos.getRow()][pos.getColumn()];
    }
}
