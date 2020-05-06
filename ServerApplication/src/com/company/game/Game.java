package com.company.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    int indexOfGame;
    Board board;
    Player player1, player2;
    public int turn = 1;
    private boolean over = false;

    static List<Game> gameList = new ArrayList<>();


    public Game(Player player1) {
        // setez player1 si board-ul
        board = new Board();
        this.player1 = player1;

        // adaug instanta in lista de game-uri
        indexOfGame = gameList.size();
        gameList.add(this);
    }

    public static Game joinPlayer(Player player2) {
        //caut un game disponibil
        for (Game game : gameList)
            if (game.player2 == null) { // returnez primul game fara player2
                game.player2 = player2;
                return game;
            }
        return null;
    }


    public boolean isOver() {
        return over;
    }

    public boolean isOver(int i, int j) {

        int length;
        int jVar, iVar;

        // i , -j+ , V -> E
        jVar = Integer.max(0, j - 4);
        length = 0;
        while (board.board[i][j] == board.board[i][jVar]
                && jVar < Integer.min(9, j + 4) && length < 5) {
            length++;
            jVar++;
        }
        if (length == 5)
            return true;

        // -i+ , j , N -> S
        iVar = Integer.max(0, i - 4);
        length = 0;
        while (board.board[i][j] == board.board[iVar][j]
                && iVar < Integer.min(9, i + 4) && length < 5) {
            length++;
            iVar++;
        }
        if (length == 5)
            return true;

        // -i+ , -j+ , NV -> SE
        iVar = Integer.max(0, i - 4);
        jVar = Integer.max(0, j - 4);
        length = 0;
        while (board.board[i][j] == board.board[iVar][jVar] && length < 5
                && iVar < Integer.min(9, i + 4)
                && jVar < Integer.min(9, j + 4)) {
            length++;
            iVar++;
            jVar++;
        }
        if (length == 5)
            return true;
        // -i+ , +j- , NE -> SV
        iVar = Integer.max(0, i - 4);
        jVar = Integer.min(9, j + 4);
        length = 0;
        while (board.board[i][j] == board.board[iVar][jVar] && length < 5
                && iVar < Integer.min(9, i + 4)
                && jVar > Integer.max(0, j - 4) ) {
            length++;
            iVar++;
            jVar--;
        }
        return length == 5;
    }

    public void setOver() {
        over = true;
    }
}
