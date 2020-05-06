package com.company.game;

public class Player {

    Game game;
    int order; // ordinea playerului in joc

    public int createGame() {
        if (game != null) // player deja apartine unui game
            return -1;
        game = new Game(this);
        order = 1; // pentru ca el a creat jocul el va fi primul
        return 1; // reusita
    }

    public int joinGame() {
        if (game != null) //  player deja apartine unui game
            return -1;
        game = Game.joinPlayer(this); // se cauta joc disponibil
        if (game == null)  // nu s a gasit joc
            return 0;
        order = 2; // va fi al 2-lea
        return 1;  // reusita
    }

    public Board getBoardGame()
    {
        return game.board;
    }

    public int submitMove(int i, int j) {
        if (game == null) // not in any game
            return -3;
        if (game.player2 == null) // second player not found yet
            return -2;
        if (game.turn != order) // is not you turn
            return -1;

        // you lost
        if (game.isOver())
            return 2;

        if(game.board.board[i][j]!=0) // invalid move
            return 0;

        // make move
        game.board.board[i][j]=order;
        game.turn = 3 - game.turn;

        if (game.isOver(i, j))  // you won
        {
            game.setOver();
            return 3;}

        return 1; // valid move
    }

    public boolean gameIsActive()
    {
        if (game == null || game.player2 == null)
            return false;
        return true;
    }

    public void setGameNull() {
        game = null;
    }
}
