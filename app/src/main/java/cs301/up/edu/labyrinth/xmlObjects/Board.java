package cs301.up.edu.labyrinth.xmlObjects;

import android.widget.ImageView;

import cs301.up.edu.game.Game;
import cs301.up.edu.game.GameMainActivity;
import cs301.up.edu.game.GamePlayer;

public class Board {

    private static final int LENGTH = 9;
    private BoardSpot[][] gameBoard = new BoardSpot[LENGTH][LENGTH];
    private BoardSpot[][] gameBoardHighlights = new BoardSpot[LENGTH][LENGTH];
    private Game game;

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
     *                                                          (8,8)
     */
    public Board(GamePlayer player, Game game, GameMainActivity activity) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ImageView imageObj = activity.findViewById(
                        activity.getResources().getIdentifier
                                ("cell_" + i + j, "id",
                                        activity.getPackageName()));
                ImageView highlightObj = activity.findViewById(
                        activity.getResources().getIdentifier
                                ("cell_" + i + j + "x", "id",
                                        activity.getPackageName()));
                if (i != 0 && i != 8 && j != 0 && j!= 8) {
                    gameBoardHighlights[i][j] = new BoardSpot(highlightObj,i,j);
                }

                if (i == 0 || i == 8) {
                    /*Make appropriate top and bottom rows BoardEdge clickable*/
                    switch (j) {
                        case 2:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, true,
                                    player, game); break;
                        case 4:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, true,
                                    player, game); break;
                        case 6:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, true,
                                    player, game); break;
                        default:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, false,
                                    player, game); break;
                    }
                } else if (j == 0 || j == 8) {
                    switch (i) {
                        /*Make appropriate left and right columns BoardEdge clickable*/
                        case 2:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, true,
                                    player, game); break;
                        case 4:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, true,
                                    player, game); break;
                        case 6:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, true,
                                    player, game); break;
                        default:
                            gameBoard[i][j] = new BoardEdge(imageObj,
                                    i, j, false,
                                    player, game); break;
                    }
                }
                else {
                    /*Make appropriate BoardTiles fixed*/
                    if ((i % 2 == 1) && (j % 2 == 1)) {
                        gameBoard[i][j] = new BoardTile(imageObj,
                                i, j, true,
                                player, game);
                    } else {
                        gameBoard[i][j] = new BoardTile(imageObj,
                                i, j, false,
                                player, game);
                    }
                }

            }
        }
    }

    public BoardSpot getBoardSpot(int locX, int locY) {
        return gameBoard[locX][locY];
    }

    public BoardSpot getHighlightSpot(int locX, int locY) {
        return gameBoardHighlights[locX][locY];
    }

    public void setGame(Game game) {
        this.game = game;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameBoard[i][j].setGame(game);
            }
        }
    }

    public Game getGame() {
        return this.game;
    }
}
