package com.comp354pjb.app.Model;

public class Board
{
    private Card[][] board;

    public Board(Card[][] arr)
    {
        board = arr;
    }

    public Card[][] getBoard()
    {
        return board;
    }

    public void setWordAt(int i, int j, String word)
    {
        board[i][j].setWord(word);
    }
    public void setRevealedAt(int i, int j, boolean revealed)
    {
        board[i][j].setRevealed(revealed);
    }
    public void setRevealedAt(int i, int j, String word)
    {
        board[i][j].setWord(word);
    }

    public void setTypeAt(int i, int j, CardType tp)
    {
        board[i][j].setType(tp);
    }

}
