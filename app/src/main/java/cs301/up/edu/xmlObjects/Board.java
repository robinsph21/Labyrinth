package cs301.up.edu.xmlObjects;

import cs301.up.edu.xmlObjects.BoardSpot;

public class Board {

    private static final int LENGTH = 10;
    private BoardSpot[][] gameBoard = new BoardSpot[LENGTH][LENGTH];

    public Board() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                gameBoard[i][j] = new BoardSpot(i, j);
            }
        }
    }

    public BoardSpot getBoardSpot(int locX, int locY) {
        return gameBoard[locX][locY];
    }
}
