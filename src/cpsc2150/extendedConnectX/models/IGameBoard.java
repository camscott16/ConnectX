package cpsc2150.extendedConnectX.models;

/*
 * Sam Wolfe - samwvlfe
 * Trey Larkins - tlark13
 * Jake Eklund - jake-eklund
 * Cam Scott - camscott16
 */

import cpsc2150.extendedConnectX.models.BoardPosition;

/**
 * GameBoard represents a board of characters, specifically keeping track of the
 * location of the pieces and determining game status
 *
 * Initialization ensures:
 * Grid contains only blank characters AND
 * is MAX_SIZE x MAX_SIZE or smaller
 *
 * Defines:
 * number_of_rows: Z
 * number_of_columns: Z
 * number_to_win: Z
 *
 * Constraints:
 * 0 < number_of_rows <= MAX_SIZE
 * 0 < number_of_columns <= MAX_SIZE
 * 0 < number_to_win <= MAX_SIZE
 */

public interface IGameBoard {

    /**
     * gets the number of rows in the GameBoard
     *
     * @return value stored for number_of_rows
     *
     * @pre None
     *
     * @post getNumRows = number_of_rows AND self = #self
     */
    int getNumRows();

    /**
     * gets the number of columns in the GameBoard
     *
     * @return value stored for number_of_columns
     *
     * @pre None
     *
     * @post getNumColumns = number_of_columns AND self = #self
     */
    int getNumColumns();

    /**
     * gets the number of tokens in a row needed to win the game
     *
     * @return value stored for number_to_win
     *
     * @pre None
     *
     * @post getNumToWin = number_to_win AND self = #self
     */
    int getNumToWin();

    /**
     * places player token in the user-selected column with the token falling to the
     * first unoccupied space
     *
     * @param p string representing the player token
     *
     * @param c integer representing column number
     *
     * @pre (c >= 0 AND c < number_of_columns)
     *
     * @post [self = #self with char p in the lowest unoccupied row of column c] AND
     *       self = #self
     */
    void dropToken(char p, int c);

    /**
     * detects what character is within the specified board position
     *
     * @param pos position on the game board to be checked
     * 
     * @return character detected in the position taken in, blank if no token
     *
     * @pre [corresponding row of pos < number_of_rows] AND [corresponding column of
     *      pos < number_of_columns]
     *
     * @post self = #self
     */
    char whatsAtPos(BoardPosition pos);

    /**
     * checks if there are available spaces left within a column
     *
     * @param c column number
     *
     * @return true if column can accept another token, false OW
     *
     * @pre c >= 0 AND c < number_of_columns
     *
     * @post [checkIfFree = true if the top-most row in column c = ' ', false OW]
     *       AND self = #self
     */
    default boolean checkIfFree(int c) {
        return (whatsAtPos(new BoardPosition(getNumRows() - 1, c)) == ' ');
    }

    /**
     * checks to see if the last token placed in column c resulted in the player
     * winning the game
     *
     * @param c column number
     *
     * @return true if the most recent token placed has met the required number of
     *         tokens in a row to win in all directions, false OW
     *
     * @pre c >= 0 AND c < number_of_columns
     * 
     * @post [checkForWin = true if top-most token in column c created consecutive
     *       number_to_win of tokens in all directions, false OW] AND
     *       self = #self
     */
    default boolean checkForWin(int c) {
        int recentRow = -1;
        char recentPlayer = ' ';

        // iterate through the given column and find w
        for (int row = getNumRows() - 1; row >= 0; row--) {
            char player = whatsAtPos(new BoardPosition(row, c));
            if (player != ' ') {
                recentPlayer = player;
                recentRow = row;
                break;
            }
        }

        if (recentRow == -1) {
            return false; // return false if column is empty;
        }

        // check all directions for a win
        if (checkHorizWin(new BoardPosition(recentRow, c), recentPlayer)
                || checkVertWin(new BoardPosition(recentRow, c), recentPlayer) ||
                checkDiagWin(new BoardPosition(recentRow, c), recentPlayer)) {
            return true;
        }

        return false; // no win detected, return false

    }

    /**
     * checks to see if all possible positions are filled with tokens
     *
     * @return true if Game Board is full, false OW
     *
     * @pre None
     *
     * @post [checkForTie = true if checkIfFree == false for every column, false OW]
     *       AND self = #self
     */
    default boolean checkTie() {
        for (int col = 0; col < getNumColumns(); col++) {
            if (checkIfFree(col)) {
                return false; // table is not full, so no tie
            }
        }
        return true; // all columns are full and no player has won yet
    }

    /**
     * checks to see if a given player has won horizontally
     *
     * @param pos position of the last played token on the board
     *
     * @param p   player who placed the respective most recent token
     *
     * @return true if the required number of same tokens are horizontally aligned,
     *         false OW
     *
     * @pre ([corresponding row of pos < number_of_rows] AND [corresponding column
     *      of pos < number_of_columns])
     *      AND (p = 'X' OR p = 'O')
     * 
     * @post [checkHorizWin = true if consecutive number_to_win tokens are in the
     *       same row, false OW] AND self = #self
     */
    default boolean checkHorizWin(BoardPosition pos, char p) {
        int counter = 0;
        for (int col = 0; col < getNumColumns(); col++) {
            BoardPosition currentPosition = new BoardPosition(pos.getRow(), col);
            if (isPlayerAtPos(currentPosition, p)) {
                counter++; // update counter
                if (counter == getNumToWin()) {
                    return true; // horizontal win is present, return true
                }
            } else {
                counter = 0; // reset the counter
            }
        }
        return false; // no horizontal win, return false
    }

    /**
     * checks to see if a given player has won vertically
     *
     * @param pos position of the last played token on the board
     *
     * @param p   player who placed the respective most recent token
     *
     * @return true if the required number of same tokens are vertically aligned,
     *         false OW
     *
     * @pre ([corresponding row of pos < number_of_rows] AND [corresponding column
     *      of pos < number_of_columns])
     *      AND (p = 'X' OR p = 'O')
     *
     * @post [checkVertWin = true if consecutive number_to_win tokens are in the
     *       same column, false OW] AND self = #self
     */
    default boolean checkVertWin(BoardPosition pos, char p) {
        int counter = 0;
        for (int row = 0; row < getNumRows(); row++) {
            BoardPosition currentPosition = new BoardPosition(row, pos.getColumn());
            if (isPlayerAtPos(currentPosition, p)) {
                counter++;
                if (counter == getNumToWin()) {
                    return true; // return true if vertical win is present
                }
            } else {
                counter = 0; // reset the counter if no win
            }
        }
        return false;
    }

    /**
     * checks to see if a given player has won diagonally
     *
     * @param pos position of the last played token on the board
     *
     * @param p   player who placed the respective most recent token
     *
     * @return true if the required number of same tokens are diagonally aligned
     *         from any direction, false OW
     *
     * @pre ([corresponding row of pos < number_of_rows] AND [corresponding column
     *      of pos < number_of_columns])
     *      AND (p = 'X' OR p = 'O')
     *
     * @post [checkDiagWin = true if consecutive number_to_win tokens are diagonally
     *       aligned from any possible direction, false OW] AND self = #self
     */
    default boolean checkDiagWin(BoardPosition pos, char p) {
        // check for bottom-left to top-right diagonal win
        int count1 = 0;
        for (int check = -getNumToWin() + 1; check < getNumToWin(); check++) {
            int row = pos.getRow() + check;
            int col = pos.getColumn() - check;
            if (row >= 0 && row < getNumRows() && col >= 0 && col < getNumColumns()) {
                if (whatsAtPos(new BoardPosition(row, col)) == p) {
                    count1++;
                    if (count1 == getNumToWin()) {
                        return true; // return true if diagonal win is present
                    }
                } else {
                    count1 = 0; // reset the counter if no win is present
                }
            }
        }

        // check for top-left to bottom-right diagonal win
        int count2 = 0;
        for (int check = -getNumToWin() + 1; check < getNumToWin(); check++) {
            int row = pos.getRow() - check;
            int col = pos.getColumn() - check;
            if (row >= 0 && row < getNumRows() && col >= 0 && col < getNumColumns()) {
                if (whatsAtPos(new BoardPosition(row, col)) == p) {
                    count2++;
                    if (count2 == getNumToWin()) {
                        return true; // return true if diagonal win is present
                    }
                } else {
                    count2 = 0; // reset the counter if no diagonal win is present
                }
            }
        }

        // check for bottom-right to top-left diagonal win
        int count3 = 0;
        for (int check = -getNumToWin() + 1; check < getNumToWin(); check++) {
            int row = pos.getRow() + check;
            int col = pos.getColumn() + check;
            if (row >= 0 && row < getNumRows() && col >= 0 && col < getNumColumns()) {
                if (whatsAtPos(new BoardPosition(row, col)) == p) {
                    count3++;
                    if (count3 == getNumToWin()) {
                        return true; // return true if diagonal win is present
                    }
                } else {
                    count3 = 0; // reset the counter if no diagonal win is present
                }
            }
        }

        // check for top-right to bottom-left diagonal win
        int count4 = 0;
        for (int check = -getNumToWin() + 1; check < getNumToWin(); check++) {
            int row = pos.getRow() - check;
            int col = pos.getColumn() + check;
            if (row >= 0 && row < getNumRows() && col >= 0 && col < getNumColumns()) {
                if (whatsAtPos(new BoardPosition(row, col)) == p) {
                    count4++;
                    if (count4 == getNumToWin()) {
                        return true; // return true if diagonal win is present
                    }
                } else {
                    count4 = 0; // reset the counter if no diagonal win is present
                }
            }
        }

        return false; // return false if no diagonal win is present
    }

    /**
     * checks if a specified player is in the specified position on the Game Board
     *
     * @param pos    position of the last played token on the board
     *
     * @param player token to be checked if they are in the position
     *
     * @return true if the specified player is in the given Game Board position,
     *         false OW
     * 
     * @pre ([corresponding row of pos < number_of_rows] AND [corresponding column
     *      of pos < number_of_columns])
     *      AND (player = 'X' OR player = 'O')
     * 
     * @post [isPlayerAtPosition = true if whatsAtPos(pos) == player, false OW] AND
     *       self = #self
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return whatsAtPos(pos) == player;
    }

}
