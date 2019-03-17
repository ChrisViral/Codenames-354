/*
 * Card.java
 * Created by: Steven Zanga
 * Created on: 23/01/19
 *
 * Contributors:
 * Steven Zanga
 * Benjamin Therrien
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.board;

import com.comp354pjb.codenames.model.player.Clue;

import java.util.HashSet;

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

    public HashSet<String> clues;

    public HashSet<String> getClues() { return clues; }

    public boolean addClue(String clue) { return clues.add(clue); }

    public boolean removeClue(String clue) { return clues.remove(clue); }

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
        this.clues = new HashSet<>();
    }
    //endregion

    //region Methods
    /**
     * Tests if a given object is a card equal to this one
     * @param o Object to compare to
     * @return  True if the object is a card equal to this one
     */
    @Override
    public boolean equals(Object o)
    {
        return o instanceof Card && equals((Card)o);
    }

    /**
     * Cards are considered equal if they have the same word on them
     * @param card Card to compare to
     * @return True if both cards are equal, false otherwise
     */
    public boolean equals(Card card)
    {
        return this.word.equals(card.word);
    }

    /**
     * Hashcode of the card, returns the hashcode of it's word, as cards should be classed by word
     * @return The hashcode of the word of this card
     */
    public int hashCode()
    {
        return this.word.hashCode();
    }
    //endregion
}


