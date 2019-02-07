package cs301.up.edu;

import cs301.up.edu.xmlObjects.BoardEdge;
import cs301.up.edu.xmlObjects.BoardSpot;
import cs301.up.edu.xmlObjects.BoardTile;

public class Board {

    private static final int LENGTH = 10;
    private BoardSpot[][] gameBoard = new BoardSpot[LENGTH][LENGTH];

    /**
     * Constructor for our board. Our board is a 9x9 grid. All tiles on the outside of the grid are
     * of type BoardEdge, all other cells are of type BoardTile. BoardTiles and BoardEdges have
     * different properties based on their position. The below logic initializes the properties of
     * cells to the correct values.
     * In the below diagram, cell status can been determined based on the following legend.
     *
     * LEGEND
     * |   | - Denotes BoardTiles
     * | S | - Denotes stationary BoardTiles
     * {   } - Denotes BoardEdges
     * { C } - Denotes clickable BoardEdges
     *
     *  (0,0)
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      {   } {   } { C } {   } { C } {   } { C } {   } {   }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      {   } | S | |   | | S | |   | | S | |   | | S | {   }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      { C } |   | |   | |   | |   | |   | |   | |   | { C }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      {   } | S | |   | | S | |   | | S | |   | | S | {   }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      { C } |   | |   | |   | |   | |   | |   | |   | { C }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      {   } | S | |   | | S | |   | | S | |   | | S | {   }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      { C } |   | |   | |   | |   | |   | |   | |   | { C }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      {   } | S | |   | | S | |   | | S | |   | | S | {   }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *      {   } {   } { C } {   } { C } {   } { C } {   } {   }
     *      ----- ----- ----- ----- ----- ----- ----- ----- -----
     *                                                          (9,9)
     */
    public Board() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (i == 1 || i == 9) {
                    /*Make appropriate top and bottom rows BoardEdge clickable*/
                    switch (j) {
                        case 3:
                            gameBoard[i][j] = new BoardEdge(i, j, true);
                        case 5:
                            gameBoard[i][j] = new BoardEdge(i, j, true);
                        case 7:
                            gameBoard[i][j] = new BoardEdge(i, j, true);
                        default:
                            gameBoard[i][j] = new BoardEdge(i, j, false);
                    }
                } else if (j == 1 || j == 9) {
                    switch (i) {
                        /*Make appropriate left and right columns BoardEdge clickable*/
                        case 3:
                            gameBoard[i][j] = new BoardEdge(i, j, true);
                        case 5:
                            gameBoard[i][j] = new BoardEdge(i, j, true);
                        case 7:
                            gameBoard[i][j] = new BoardEdge(i, j, true);
                        default:
                            gameBoard[i][j] = new BoardEdge(i, j, false);
                    }
                }
                else {
                    /*Make appropriate BoardTiles fixed*/
                    if ((i % 2 == 0) && (j % 2 == 0)) {
                        gameBoard[i][j] = new BoardTile(i, j, true);
                    } else {
                        gameBoard[i][j] = new BoardTile(i, j, false);
                    }
                }

            }
        }
    }

    public BoardSpot getBoardSpot(int locX, int locY) {
        return gameBoard[locX][locY];
    }
}
