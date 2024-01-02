package cpsc2150.extendedConnectX.models;

/*
 * Sam Wolfe - samwvlfe
 * Trey Larkins - tlark13
 * Jake Eklund - jake-eklund
 * Cam Scott - camscott16
 */

abstract class AbsGameBoard implements IGameBoard {

    /**
     * overrides the toString method to provide a string of the game board
     *
     * @return a string representation of the game board, includes a number-labeled
     *         row at the top and each game board space in format "|<character> |"
     *
     * @pre None
     *
     * @post [toString = string containing the game board laid out in a grid format]
     *       AND self = #self AND number_of_rows = #number_of_rows
     *       AND number_of_columns = #number_of_columns AND number_to_win =
     *       #number_to_win
     */
    @Override
    public String toString() {

        int DOUBLE_CHAR_SLOT = 10;

        String labeledNumberRow = "";
        String playerBoard = "";

        //add number row at the top
        for (int i = 0; i < getNumColumns(); i++) {
            if (i < DOUBLE_CHAR_SLOT)
                labeledNumberRow += "| " + i; //single-digit
            else
                labeledNumberRow += "|" + i; //double-digit
        }
        labeledNumberRow += "|\n"; //finish with newline

        //add player-intractable board
        for (int i = getNumRows() - 1; i >= 0; i--) { // done in reverse order to reflect higher rows printed on top
            for (int j = 0; j < getNumColumns(); j++) {
                BoardPosition currentPosition = new BoardPosition(i, j); // temp board position for current location
                playerBoard += "|" + whatsAtPos(currentPosition) + " "; // add "|<player> " for each position
            }
            playerBoard += "|\n"; // end with the row-finishing "|" and go to new line for all of the rows

        }
        return labeledNumberRow + playerBoard; // return combined string
    }

}