/*
 * Game.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;

/**
 * Wrapper around word/number pair for a clue
 */
public class Clue
{
    //region Fields
    public final String word;
    public int value;
    public int redSuggested = 0;
    public int blueSuggested = 0;
    public int civilianSuggested = 0;
    public boolean assassinSuggested = false;
    private ArrayList<Card> cards;
    //endregion

    //region Constructors
    /**
     * Creates a new Clue
     * @param word  Word of the clue
     * @param value Numeric value of the clue
     */
    public Clue(String word, int value)
    {
        this.word = word;
        this.value = value;
        this.cards = new ArrayList<>();
    }
    //endregion

    //region Methods
    /**
     * Get the cards on the board that are suggested by this clue
     * @return A list of cards associated with the clue
     */
    public ArrayList<Card> getCards()
    {
        return cards;
    }

    /**
     * Method to associate a card object with this clue
     * Indicates that this clue suggests the codename on the card
     * @param card Card to be suggested
     * @return True if the card is successfully added and false otherwise
     */
    public boolean addCard(Card card)
    {
        boolean added = cards.add(card);

        if(!added) return false;

        switch(card.getType())
        {
            case RED:
                redSuggested++;
                break;
            case BLUE:
                blueSuggested++;
                break;
            case CIVILIAN:
                civilianSuggested++;
                break;
            case ASSASSIN:
                assassinSuggested = true;
                break;
        }

        return true;
    }

    /**
     * Disassociate a card with this clue
     * @param card Card to be removed
     * @return True if the card is successfully removed and false otherwise
     */
    public boolean removeCard(Card card)
    {
        boolean removed = cards.remove(card);

        if(!removed) return false;

        switch(card.getType())
        {
            case RED:
                redSuggested--;
                break;
            case BLUE:
                blueSuggested--;
                break;
            case CIVILIAN:
                civilianSuggested--;
                break;
            case ASSASSIN:
                assassinSuggested = false;
                break;
        }

        return true;
    }

    /**
     * Answers the question: Does this clue suggest any cards currently in play
     * @return True if this clue suggests at least one card and false otherwise
     */
    public boolean suggestsSomeCard()
    {
        return redSuggested > 0 || blueSuggested > 0 || civilianSuggested > 0 || assassinSuggested == true;
    }

    /**
     * String representation of the whole clue
     * @return String of the clue, combining the word and value
     */
    @Override
    public String toString()
    {
        return this.word + " " + this.value;
    }

    //endregion
}
