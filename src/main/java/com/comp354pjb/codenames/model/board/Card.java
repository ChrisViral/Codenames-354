/*
 * Card.java
 * Created by: Steven Zanga
 * Created on: 23/01/19
 *
 * Contributors:
 * Steven Zanga
 * Benjamin Therrien
 * Michael Wilgus
 *
 * Description:
 * Represents a card in the Codenames game.
 * Additionally it stores information about clues that can
 * be used to suggest the card so that AI players can give
 * intelligent suggestions.
 */

package com.comp354pjb.codenames.model.board;

import java.util.HashSet;

public class Card
{
    //region Properties
    /**
     * Type of this card
     */
    private CardType type;
    /**
     * The word displayed on this card
     */
    private String word;
    /**
     * If this card is revealed on the board or not
     */
    private boolean isRevealed;
    /**
     * Clue words that suggest this card
     * Added by Michael Wilgus
     */
    private HashSet<String> clues;
    private int x, y;

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

    /**
     * Accessor for Card Type
     * @return type, The type of this card
     */
    public CardType getType()
    {
        return this.type;
    }

    /**
     * Accessor for word
     * @return word, The word displayed by the card
     */
    public String getWord()
    {
        return this.word;
    }

    /**
     * Accessor for isRevealed
     * @return isRevealed, whether the card type has been revealed or not
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

    /**
     * X coordinate on the grid of this card
     */
    public int getX()
    {
        return this.x;
    }
    //endregion

    //region Constructors

    /**
     * Y coordinate on the grid of this card
     */
    public int getY()
    {
        return this.y;
    }
    //endregion

    //region Methods

    /**
     * Get the clue words that suggest this card
     * Added by Michael Wilgus
     * @return A set of clue words that a SpyMaster can choose from
     */
    public HashSet<String> getClues() { return clues; }

    /**
     * Add a word to the clue word set
     * Added by Michael Wilgus
     * @param clue The clue to be added
     * @return True if the clue is added and false otherwise
     */
    public boolean addClue(String clue) { return clues.add(clue); }

    /**
     * Remove a word from the clue word set
     * Added by Michael Wilgus
     * @param clue The clue to be removed
     * @return True if the clue is removed and false otherwise
     */
    public boolean removeClue(String clue) { return clues.remove(clue); }

    /**
     * Tests if a given object is a card equal to this one
     * @param o Object to compare to
     * @return True if the object is a card equal to this one
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


