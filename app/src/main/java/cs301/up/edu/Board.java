package cs301.up.edu;

import cs301.up.edu.xmlObjects.BoardEdge;
import cs301.up.edu.xmlObjects.BoardSpot;
import cs301.up.edu.xmlObjects.BoardTile;

public class Board {

    private static final int LENGTH = 10;
    private BoardSpot[][] gameBoard = new BoardSpot[LENGTH][LENGTH];

    public Board() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (i == 1 || i == 9) {
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
                    //Check if tile should be fixed based on position
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
