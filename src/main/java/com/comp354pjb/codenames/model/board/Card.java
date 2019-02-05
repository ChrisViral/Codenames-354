/*
 * Card.java
 * Created by: Steven Zanga
 * Created on: 23/01/19
 *
 * Contributors:
 * Steven Zanga
 * Benjamin Therrien
 */

package com.comp354pjb.codenames.model.board;

public class Card
{
    //region Properties
    private CardType type;
    /**
     * Type of this card
     */
    public CardType getType()
    {
        return this.type;
    }

    private String word;
    /**
     * The word displayed on this card
     */
    public String getWord()
    {
        return this.word;
    }

    private boolean isRevealed;
    /**
     * If this card is revealed on the board or not
     */
    public boolean isRevealed()
    {
        return this.isRevealed;
    }
    /**
     * Sets the revealed info of this card
     */
    public void setRevealed(boolean revealed)
    {
        this.isRevealed = revealed;
    }

    private int x, y;
    /**
     * X coordinate on the grid of this card
     */
    public int getX()
    {
        return this.x;
    }
    /**
     * Y coordinate on the grid of this card
     * @return
     */
    public int getY() {
        return this.y;
    }
    //endregion

    //region Constructors
    /**
     * Card Parameterized Constructor
     * @param word The word displayed on the Card
     * @param type Type of card
     * @param x    X coordinate of the card in the grid
     * @param y    Y coordinate of the card in the grid
     */
    public Card(String word, CardType type, int x, int y)
    {
        this.word = word;
        this.type = type;
        this.x = x;
        this.y = y;
        this.isRevealed = false;
    }
    //endregion
}


