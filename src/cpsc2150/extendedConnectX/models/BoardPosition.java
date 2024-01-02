package cpsc2150.extendedConnectX.models;

/*
 * Sam Wolfe - samwvlfe
 * Trey Larkins - tlark13
 * Jake Eklund - jake-eklund
 * Cam Scott - camscott16
 */

/**
 * This class is used to represent a position on the Board using a 2-D array
 *
 * @invariant getNumRows() >= Row AND Row >= 0 AND getNumColumn() >= Column AND
 *            Column >= 0
 */
public class BoardPosition {
    private int Row;
    private int Column;

    /**
     * constructs the BoardPosition object in order to hold where the player's token
     * goes
     *
     * @param aRow    parameter to be set to Row field
     *
     * @param aColumn parameter to be set to Column field
     *
     * @pre getNumRows() >= Row >= 0 AND getNumColumns() >= Column >= 0
     *
     * @post Row = aRow AND Column = aColumn
     */
    public BoardPosition(int aRow, int aColumn) {
        // parameterized constructor for BoardPosition
        this.Row = aRow;
        this.Column = aColumn;
    }

    /**
     * Method that returns the value in Row private field
     *
     * @return value in Row field
     *
     * @pre None
     *
     * @post getRow = Row AND Row = #Row AND Column = #Column
     */
    public int getRow() {
        return this.Row; // returns the row
    }

    /**
     * Method that returns the value in Column private field
     *
     * @return value in Column field
     *
     * @pre None
     *
     * @post getColumn = Column AND Row = #Row AND Column = #Column
     */
    public int getColumn() {
        return this.Column; // returns the column
    }

    /**
     * Checks to see if provided position is equivalent to the current position
     *
     * @param obj the object that holds a given position with a row and column
     *
     * @return true if the object is a BoardPosition that has the same row and
     *         column as the current position, returns false otherwise
     *
     * @pre None
     *
     * @post equals = true if obj.getRow() == Row && obj.getColumn() == Column,
     *       false OW AND row = #row AND col = #col
     */
    @Override
    public boolean equals(Object obj) {

        boolean boardPositionsEqual = false;
        if(obj instanceof BoardPosition temp)
            boardPositionsEqual = (temp.getColumn() == this.getColumn()) && (temp.getRow() == this.getRow());

        return boardPositionsEqual;
    }

    /**
     * overrides toString to return the position of a given space
     *
     * @return string representation of the position in the format: "<row>,<column>"
     *
     * @pre None
     *
     * @post row = #row AND col = #col AND toString = "<row>,<column>"
     */
    @Override
    public String toString() {
        return this.getRow() + "," + this.getColumn(); // format "<row>,<column>"

    }
}