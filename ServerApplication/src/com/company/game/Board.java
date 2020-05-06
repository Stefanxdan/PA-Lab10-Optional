package com.company.game;

public class Board {

    int[][] board = new int[10][10];

    public Board() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                board[i][j] = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBoard = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                stringBoard.append(" ").append(board[i][j]);
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }
}
