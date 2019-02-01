/*
 * Card.java
 * Created by: Steven Zanga
 * Created on: 17/01/19
 *
 * Contributors:
 * Benjamin Therrien
 *
 * ...
 */
package com.comp354pjb.codenames.model.board;

public class Card
{

    private CardType type; //type of Card
    private String word; //word associated with Card
    private boolean isRevealed; //boolean to check if card has been revealed

    /**
     * Card default Constructor
     */
    public Card()
    {
        this.word = " ";
        isRevealed = false;
    }

    /**
     * Card Parameterized Constructor
     * @param word
     * @param type
     */
    public Card(String word, CardType type)
    {
        this.word = word;
        this.type = type;
        this.isRevealed = false;
    }

    /**
     * get specific word from a Card
     * @return returns word associated with card
     */
    public String getWord()
    {
        return word;
    }

    /**
     * set a word associated with a card
     * @param word
     */
    public void setWord(String word)
    {
        this.word = word;
    }

    /**
     * check if a card has been revealed
     * @return returns boolean if card has been revealed
     */
    public boolean isRevealed()
    {
        return isRevealed;
    }

    /**
     * sets the boolean value of a cards status
     * @param revealed
     */
    public void setRevealed(boolean revealed)
    {
        isRevealed = revealed;
    }

    /**
     * allows user to retrieve the type of card.
     * @return type of card
     */
    public CardType getType()
    {
        return type;
    }

    /**
     * sets the type of card
     * @param tp
     */
    public void setType(CardType tp)
    {
        type = tp;
    }
}


