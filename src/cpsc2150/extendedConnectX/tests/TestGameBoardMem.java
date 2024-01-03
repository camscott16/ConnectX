package cpsc2150.extendedConnectX.tests;

import cpsc2150.extendedConnectX.models.BoardPosition;
import cpsc2150.extendedConnectX.models.GameBoardMem;
import cpsc2150.extendedConnectX.models.IGameBoard;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestGameBoardMem {

    // helper method to return a new IGameBoard object and returns it
    private IGameBoard IGameBoardMemFactory(int r, int c, int numToWin) {
        return new GameBoardMem(r, c, numToWin);
    }

    // additional helper function to produce an "expected" version of the GameBoard
    private String gameBoardToString(char[][] board) {
        StringBuilder expected = new StringBuilder();
        expected.append("|");
        for (int i = 0; i < board[0].length; i++) {
            if (i < 10) {
                expected.append(" ");
                expected.append(i);
                expected.append("|");
            }
            else{
                expected.append(i);
                expected.append("|");
            }
        }
        expected.append("\n");
        for (int i = 0; i < board.length; i++) {
            expected.append("|");
            for (int j = 0; j < board[i].length; j++) {

                expected.append(board[i][j]).append(" |");
            }
            expected.append("\n");
        }
        return expected.toString();
    }


    // 3 cpsc2150.extendedConnectX.tests for constructor

    // checks for correct initialization of the gameboard instance variables
    @Test
    public void constructor_validInitialization() {
        int row = 6;
        int col = 7;
        int numToWin = 4;

        // create an IGameBoard object using the IGameBoardMemFactory
        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        // ensure that the GameBoard was constructed with correct values
        assertEquals(row, gb.getNumRows());
        assertEquals(col, gb.getNumColumns());
        assertEquals(numToWin, gb.getNumToWin());

    }

    // checks correct initialization of gamebaord with large values
    @Test
    public void constructor_emptyBoardCheck() {
        int row = 50;
        int col = 38;
        int numToWin = 3;

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);
        char board[][] = new char[row][col];

        for (int i = 0; i < row; i++) {
            for (int x = 0; x < col; x++) {
                board[i][x] = ' ';
            }
        }

        // ensure that the gameboard produces looks like the expected gameboard
        assertEquals(gameBoardToString(board), gb.toString());

    }

    // cpsc2150.extendedConnectX.tests for the construction of the gameboard with the minimum values possible
    @Test
    public void constructor_boundaryValues() {
        int row = 3;
        int col = 3;
        int numToWin = 3;

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        assertEquals(row, gb.getNumRows());
        assertEquals(col, gb.getNumColumns());
        assertEquals(numToWin, gb.getNumToWin());
    }

    // 3 cpsc2150.extendedConnectX.tests for checkIfFree

    // cpsc2150.extendedConnectX.tests for correct initialization of gameboard given larger values
    @Test
    public void checkIfFreeTest1_empty_board() {
        int row = 50;
        int col = 38;
        int numToWin = 2;

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);
        char board[][] = new char [row][col];

        for (int i = 0; i < row; i++) {
            for (int x = 0; x < col; x++) {
                board[i][x] = ' ';
            }
        }

        //checks every col
        for (int i = 0; i < col; i++){
            assertTrue(gb.checkIfFree(i));
        }
        assertEquals(gameBoardToString(board), gb.toString());

    }

    // cpsc2150.extendedConnectX.tests checkIfFree method on a full gameboard
    @Test
    public void checkIfFreeTest2_full_board() {
        int row = 50;
        int col = 38;
        int numToWin = 2;

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        for (int i = 0; i < row; i++) {
            for (int x = 0; x < col; x++) {
                if ((i + x) % 2 == 0) {
                    gb.dropToken('X', x);
                }
                else {
                    gb.dropToken('O', x);
                }
            }
        }

        for (int i = 0; i < col; i++){
            assertFalse(gb.checkIfFree(i));
        }
    }

    // test for checkIfFree using a partially filled board
    @Test
    public void checkIfFree_partialBoard() {
        int row = 7;
        int col = 7;
        int numToWin = 4;

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        // fill some columns, leaving some others empty
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (i % 2 == 0) {
                    gb.dropToken('X', i); // fill even columns
                }
            }
        }
    }

    // 4 cpsc2150.extendedConnectX.tests for checkHorizWin

    // cpsc2150.extendedConnectX.tests for horizontal win with all rows being full
    @Test
    public void checkHorizWinAllRowsAllFull() {
        char tempToken = 'X';
        // factory call to create obj
        IGameBoard gb = IGameBoardMemFactory(5, 5, 5);
        //fill all rows full of X's then check for win
        for(int j = 0; j < gb.getNumRows(); j++) {
            for (int i = 0; i < gb.getNumColumns(); i++) {
                gb.dropToken(tempToken, i);
            }
            BoardPosition bp = new BoardPosition(j,0);
            assertTrue(gb.checkHorizWin(bp,'X'));
        }
    }

    // checks for no horizontal win when there is no winning combination of tokens
    @Test
    public void checkHorizWinTestWithOpponentOnSameRow() {
        char tempToken = 'X';
        char tempOtherToken = 'O';
        // factory call to create obj
        IGameBoard gb = IGameBoardMemFactory(5, 8, 3);
        for(int i = 0; i < 8; i++){
            if(i < 5){
                if(i % 2 == 0){
                    gb.dropToken(tempToken, i);
                }
                else{
                    gb.dropToken(tempOtherToken, i);
                }
            }
            else{
                gb.dropToken(tempOtherToken, i);
            }
        }
        //String s = gb.toString();
        //System.out.println(s);
        BoardPosition bp = new BoardPosition(0,0);
        assertTrue(gb.checkHorizWin(bp,'O'));
    }

    // checks horizontal win when the board is full and no win is present
    @Test
    public void checkHorizWinTestFullBoardNoWin() {
        char tempToken = 'X';
        char tempOtherToken = 'O';
        // factory call to create obj
        IGameBoard gb = IGameBoardMemFactory(5, 5, 3);
        for(int j = 0; j < 5; j++){
            for(int i = 0; i < 5; i++) {
                if (i % 2 == 0) {
                    gb.dropToken(tempToken, i);
                } else {
                    gb.dropToken(tempOtherToken, i);
                }
            }
        }
        //String s = gb.toString();
        //System.out.println(s);
        for(int i = 0; i < 5; i++){
            BoardPosition bp = new BoardPosition(i,0);
            assertFalse(gb.checkHorizWin(bp,'O'));
        }
    }

    // checks for horizontal win with an empty board
    @Test
    public void checkHorizWinTestWithEmptyBoard() {
        IGameBoard gb = IGameBoardMemFactory(5, 5, 3);
        BoardPosition bp = new BoardPosition(0,0);
        assertFalse(gb.checkHorizWin(bp,'O'));
    }

    // 4 cpsc2150.extendedConnectX.tests for checkVertWin

    // check for a vertical win on a full column
    @Test
    public void checkVertWin_VerticalWinFullColumn() {
        int row = 6;
        int col = 7;
        int numToWin = 4;
        char token = 'X'; // player token for test

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        //fill a column with the players token
        int colToFill = 4;
        for (int i = 0; i < numToWin; i++) {
            gb.dropToken(token, colToFill);
        }

        // ensure that the program identifies a vertical win
        BoardPosition bp = new BoardPosition(row - numToWin, colToFill);
        assertTrue(gb.checkVertWin(bp, token));

    }

    // checks for no win being detected when board is empty
    @Test
    public void checkVertWin_noVerticalWinEmptyBoard() {
        int row = 7;
        int col = 7;
        int numToWin = 5;
        char token = 'O';

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        BoardPosition bp = new BoardPosition(row - 1, col - 1);
        assertFalse(gb.checkVertWin(bp, token));
    }

    // check for no win being detected with insufficient tokens in a column
    @Test
    public void checkVertWin_noVerticalWinInsufficientTokens() {
        int row = 7;
        int col = 7;
        int numToWin = 4;
        char token = 'X';

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        int colToFill = 3;
        for (int i = 0; i < numToWin - 1; i++) {
            gb.dropToken(token, colToFill);
        }

        BoardPosition bp = new BoardPosition(row - (numToWin - 1), colToFill);
        assertFalse(gb.checkVertWin(bp, token));

    }

    // check for a vertical win with a large board size
    @Test
    public void checkVertWin_largeBoard() {
        int row = 20;
        int col = 20;
        int numToWin = 6;
        char token = 'X';

        IGameBoard gb = IGameBoardMemFactory(row, col, numToWin);

        int colToFill = 10;
        for (int i = 0; i < numToWin - 1; i++) {
            gb.dropToken(token, colToFill);
        }

        BoardPosition bp = new BoardPosition(row - (numToWin - 1), colToFill);
        assertFalse(gb.checkVertWin(bp, token));
    }

    // 7 test for checkDiagWin

    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from top-left to bottom-right
    @Test
    public void test_checkDiagWin_NW_SE_EndToEnd() {
        final char[][] EXPECTED_BOARD =
                {{'x', ' ', ' '},
                        {'o', 'x', ' '},
                        {'o', 'o', 'x'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char WINNING_CHAR = 'x';
        BoardPosition bp = new BoardPosition(0,2);

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken('o',0);
        gb.dropToken('o',0);
        gb.dropToken(WINNING_CHAR,0);
        gb.dropToken('o',1);
        gb.dropToken(WINNING_CHAR,1);
        gb.dropToken(WINNING_CHAR,2);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }

    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from top-left to bottom-right in the middle of the board
    @Test
    public void test_checkDiagWin_NW_SE_Middle() {
        final char[][] EXPECTED_BOARD =
                {{' ', 'x', ' ',' ', ' ', ' '},
                        {' ', 'o', 'x',' ', ' ', ' '},
                        {' ', 'o', 'o','x', ' ', ' '},
                        {' ', 'o', 'o','o', 'x', ' '},
                        {' ', 'o', 'o','o', 'o', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 4;
        final char WINNING_CHAR = 'x';
        final char LOSING_CHAR = 'o';
        int columnCount = 1;
        BoardPosition bp = new BoardPosition(2,3);

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }

    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from top-right to bottom-left
    @Test
    public void test_checkDiagWin_NE_SW_EndToEnd() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ','x'},
                        {' ', ' ', 'x','o'},
                        {' ', 'x', 'o','o'},
                        {'x', 'o', 'o','o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 4;
        final char WINNING_CHAR = 'x';
        final char LOSING_CHAR = 'o';
        int columnCount = 3;
        BoardPosition bp = new BoardPosition(0,0);

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(WINNING_CHAR,columnCount);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }


    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from top-right to bottom-left in teh middle of the board
    @Test
    public void test_checkDiagWin_NE_SW_Middle() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ',' ',' '},
                        {' ', ' ', ' ','x',' '},
                        {' ', ' ', 'x','o',' '},
                        {' ', 'x', 'o','o',' '},
                        {'x', 'o', 'o','o',' '},
                        {'o', 'o', 'o','o',' '},
                        {'o', 'o', 'o','o',' '},
                        {'o', 'o', 'o','o',' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 4;
        final char WINNING_CHAR = 'x';
        final char LOSING_CHAR = 'o';
        int columnCount = 3;
        BoardPosition bp = new BoardPosition(5,2);

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }

    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from bottom-left to top-right when tokens are
    // filling the board diagonally
    @Test
    public void test_checkDiagWin_SW_NE_EndToEnd() {
        final char[][] EXPECTED_BOARD =
                {{'o', 'o', 'x'},
                        {'o', 'x', 'o'},
                        {'x', 'o', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char WINNING_CHAR = 'x';
        final char LOSING_CHAR = 'o';
        int columnCount = 0;
        BoardPosition bp = new BoardPosition(2,2);

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken(WINNING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }


    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from bottom-left to top-right in the middle of the board
    @Test
    public void test_checkDiagWin_SW_NE_Middle() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ',' ',' '},
                        {' ', ' ', ' ',' ',' '},
                        {' ', ' ', ' ',' ',' '},
                        {' ', ' ', ' ',' ','x'},
                        {' ', ' ', ' ','x','o'},
                        {' ', ' ', 'x','o','o'},
                        {' ', 'x', 'o','o','o'},
                        {'x', 'o', 'o','o','o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 5;
        final char WINNING_CHAR = 'x';
        final char LOSING_CHAR = 'o';
        int columnCount = 0;
        BoardPosition bp = new BoardPosition(1,1);


        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        ++columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }

    // cpsc2150.extendedConnectX.tests for a diagonal win when there is a winning combination from bottom-right to top-left
    @Test
    public void test_checkDiagWin_SE_NW_Middle() {
        final char[][] EXPECTED_BOARD =
                {{' ','x', ' ', ' '},
                        {' ','o', 'x', ' '},
                        {' ','o', 'o', 'x'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char WINNING_CHAR = 'x';
        final char LOSING_CHAR = 'o';

        int columnCount = 3;
        BoardPosition bp = new BoardPosition(2,1);

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);
        --columnCount;
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(LOSING_CHAR,columnCount);
        gb.dropToken(WINNING_CHAR,columnCount);

        assertTrue(gb.checkDiagWin(bp, WINNING_CHAR));
    }

    // 4 cpsc2150.extendedConnectX.tests for checkTie


    @Test
    public void test_checkTie_Full_SingleToken() {

        final char[][] EXPECTED_BOARD =
                {{'o', 'o', 'o', 'o', 'o', 'o'},
                        {'o', 'o', 'o', 'o', 'o', 'o'},
                        {'o', 'o', 'o', 'o', 'o', 'o'},
                        {'o', 'o', 'o', 'o', 'o', 'o'},
                        {'o', 'o', 'o', 'o', 'o', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char CHAR_TO_DROP = 'o';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);


        for (int i = 0; i < COLUMNS; i++){
            for(int j = 0; j < ROWS; j++) {
                gb.dropToken(CHAR_TO_DROP, i);
            }
        }

        assertTrue(gb.checkTie());

    }

    @Test
    public void test_checkTie_Full_MixedTokens() {
        final char[][] EXPECTED_BOARD =
                {{'x', 'x', 'x', 'x', 'x', 'x'},
                        {'o', 'o', 'o', 'o', 'o', 'o'},
                        {'x', 'x', 'x', 'x', 'x', 'x'},
                        {'o', 'o', 'o', 'o', 'o', 'o'},
                        {'x', 'x', 'x', 'x', 'x', 'x'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char CHAR_TO_DROP_1 = 'o';
        final char CHAR_TO_DROP_2 = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);


        for (int i = 0; i < COLUMNS; i++){
            for(int j = 0; j < ROWS; j++) {
                if(j % 2 == 1)
                    gb.dropToken(CHAR_TO_DROP_1, i);
                else
                    gb.dropToken(CHAR_TO_DROP_2, i);
            }
        }

        assertTrue(gb.checkTie());

    }

    @Test
    public void test_checkTie_NotFull_MissingOne() {
        final char[][] EXPECTED_BOARD =
                {{'x', 'x', ' '},
                        {'x', 'x', 'x'},
                        {'x', 'x', 'x'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char DEFAULT_CHAR = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        for(int i = 0; i < COLUMNS; i++)
            gb.dropToken(DEFAULT_CHAR, 0);

        for(int i = 0; i < COLUMNS; i++)
            gb.dropToken(DEFAULT_CHAR, 1);

        for(int i = 0; i < COLUMNS-1; i++)
            gb.dropToken(DEFAULT_CHAR, 2);


        assertFalse(gb.checkTie());
    }

    @Test
    public void test_checkTie_NotFull_CompletelyEmpty() {

        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {' ', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        assertFalse(gb.checkTie());

    }

    // 5 cpsc2150.extendedConnectX.tests for whatsAtPos


    @Test
    public void test_whatsAtPos_NW() {
        final char[][] EXPECTED_BOARD =
                {{'x', ' ', ' ', ' ', ' '},
                        {'o', ' ', ' ', ' ', ' '},
                        {'o', ' ', ' ', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = 0;
        final int NUM_DEFAULT_TOKENS_TO_DROP = ROWS-1;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(NUM_DEFAULT_TOKENS_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gb.whatsAtPos(bp), CHAR_TO_DROP);


    }

    @Test
    public void test_whatsAtPos_SW() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {'x', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = 0;
        final int NUM_DEFAULT_TOKENS_TO_DROP = 0;
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(NUM_DEFAULT_TOKENS_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gb.whatsAtPos(bp), CHAR_TO_DROP);

    }

    @Test
    public void test_whatsAtPos_NE() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' ', ' ', 'x'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = COLUMNS-1;
        final int NUM_DEFAULT_TOKENS_TO_DROP = ROWS-1;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(NUM_DEFAULT_TOKENS_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gb.whatsAtPos(bp), CHAR_TO_DROP);
    }

    @Test
    public void test_whatsAtPos_SE() {

        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = COLUMNS-1;
        final char CHAR_TO_DROP = 'o';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(0, COLUMN_TO_DROP);

        assertEquals(gb.whatsAtPos(bp), CHAR_TO_DROP);
    }

    @Test
    public void test_whatsAtPos_EmptyPosition() {
        final char[][] EXPECTED_BOARD =
                {{'x', 'x', ' '},
                        {'x', 'x', 'x'},
                        {'x', 'x', 'x'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char DEFAULT_CHAR = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        for(int i = 0; i < COLUMNS; i++)
            gb.dropToken(DEFAULT_CHAR, 0);

        for(int i = 0; i < COLUMNS; i++)
            gb.dropToken(DEFAULT_CHAR, 1);

        for(int i = 0; i < COLUMNS-1; i++)
            gb.dropToken(DEFAULT_CHAR, 2);

        BoardPosition bp = new BoardPosition(ROWS-1, COLUMNS-1);

        assertEquals(gb.whatsAtPos(bp), ' ');
    }

    // 5 cpsc2150.extendedConnectX.tests for isPlayerAtPos
    @Test
    public void test_isPlayerAtPos_NW() {
        final char[][] EXPECTED_BOARD =
                {{'x', ' ', ' ', ' ', ' '},
                        {'o', ' ', ' ', ' ', ' '},
                        {'o', ' ', ' ', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = 0;
        final int NUM_DEFAULT_TOKENS_TO_DROP = ROWS-1;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(NUM_DEFAULT_TOKENS_TO_DROP, COLUMN_TO_DROP);

        assertTrue(gb.isPlayerAtPos(bp, CHAR_TO_DROP));


    }

    @Test
    public void test_isPlayerAtPos_SW() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {' ', ' ', ' '},
                        {'x', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = 0;
        final int NUM_DEFAULT_TOKENS_TO_DROP = 0;
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(NUM_DEFAULT_TOKENS_TO_DROP, COLUMN_TO_DROP);

        assertTrue(gb.isPlayerAtPos(bp, CHAR_TO_DROP));

    }

    @Test
    public void test_isPlayerAtPos_NE() {
        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' ', ' ', 'x'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = COLUMNS-1;
        final int NUM_DEFAULT_TOKENS_TO_DROP = ROWS-1;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(NUM_DEFAULT_TOKENS_TO_DROP, COLUMN_TO_DROP);

        assertTrue(gb.isPlayerAtPos(bp, CHAR_TO_DROP));
    }

    //checks to ensure that the function can differentiate between the different player's tokens
    @Test
    public void test_isPlayerAtPos_SE_WrongToken() {

        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = COLUMNS-1;
        final char CHAR_TO_DROP = 'o';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        BoardPosition bp = new BoardPosition(0, COLUMN_TO_DROP);

        assertFalse(gb.isPlayerAtPos(bp, 'x'));

    }

    // checks that the specified board position is empty
    @Test
    public void test_isPlayerAtPos_EmptyPosition() {
        final char[][] EXPECTED_BOARD =
                {{'x', 'x', ' '},
                        {'x', 'x', 'x'},
                        {'x', 'x', 'x'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char DEFAULT_CHAR = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        for(int i = 0; i < COLUMNS; i++)
            gb.dropToken(DEFAULT_CHAR, 0);

        for(int i = 0; i < COLUMNS; i++)
            gb.dropToken(DEFAULT_CHAR, 1);

        for(int i = 0; i < COLUMNS-1; i++)
            gb.dropToken(DEFAULT_CHAR, 2);

        BoardPosition bp = new BoardPosition(ROWS-1, COLUMNS-1);

        assertFalse(gb.isPlayerAtPos(bp, ' '));

    }

    // 5 cpsc2150.extendedConnectX.tests for dropToken

    @Test
    public void test_dropToken_x_2_EmptyColumn() {

        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'x', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = 2;
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gameBoardToString(EXPECTED_BOARD),gb.toString());

    }

    @Test
    public void test_dropToken_x_0_LastSpotInColumn() {

        final char[][] EXPECTED_BOARD =
                {{'x', ' ', ' '},
                        {'o', ' ', ' '},
                        {'o', ' ', ' '},
                        {'o', ' ', ' '},
                        {'o', ' ', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final int COLUMN_TO_DROP = 0;
        final int NUM_DEFAULT_TOKENS_TO_DROP = ROWS - 1;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';


        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gameBoardToString(EXPECTED_BOARD),gb.toString());
    }

    @Test
    public void test_dropToken_x_6_LastColumnOnBoard() {

        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'x'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 5;
        final int COLUMN_TO_DROP = 6;
        final int NUM_DEFAULT_TOKENS_TO_DROP = 2;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gameBoardToString(EXPECTED_BOARD),gb.toString());
    }

    //already full column
    @Test
    public void test_dropToken_x_4_ColumnPartiallyFull() {

        final char[][] EXPECTED_BOARD =
                {{' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', 'x', ' '},
                        {' ', ' ', ' ', ' ', 'o', ' '}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 5;
        final int COLUMN_TO_DROP = 4;
        final int NUM_DEFAULT_TOKENS_TO_DROP = 1;
        final char DEFAULT_CHAR = 'o';
        final char CHAR_TO_DROP = 'x';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to set up test
        for(int i = 0; i < NUM_DEFAULT_TOKENS_TO_DROP; i++) {
            gb.dropToken(DEFAULT_CHAR, COLUMN_TO_DROP);
        }

        //dropped token to be tested
        gb.dropToken(CHAR_TO_DROP, COLUMN_TO_DROP);

        assertEquals(gameBoardToString(EXPECTED_BOARD),gb.toString());
    }
    //fill board
    @Test
    public void test_dropToken_FillBoard() {
        final char[][] EXPECTED_BOARD =
                {{'o', 'o', 'o'},
                        {'o', 'o', 'o'},
                        {'o', 'o', 'o'}};

        final int ROWS = EXPECTED_BOARD.length;
        final int COLUMNS = EXPECTED_BOARD[0].length;
        final int NUM_TO_WIN = 3;
        final char CHAR_TO_DROP = 'o';

        IGameBoard gb = IGameBoardMemFactory(ROWS, COLUMNS, NUM_TO_WIN);

        //drop the tokens to test
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                gb.dropToken(CHAR_TO_DROP, j);
            }
        }

        assertEquals(gameBoardToString(EXPECTED_BOARD),gb.toString());
    }


}
