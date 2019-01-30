package com.comp354pjb.app.Model.Board;

public class Card
{
    private CardType type;
    private String word;
    private boolean isRevealed;

    public Card()
    {
        this.word = " ";
        isRevealed = false;
    }

    public Card(String word, CardType type)
    {
        this.word = word;
        this.type = type;
        this.isRevealed = false;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public boolean isRevealed()
    {
        return isRevealed;
    }

    public void setRevealed(boolean revealed)
    {
        isRevealed = revealed;
    }

    public CardType getType()
    {
        return type;
    }

    public void setType(CardType tp)
    {
        type = tp;
    }
}


