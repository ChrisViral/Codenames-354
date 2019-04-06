/*
 * Clue.java
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
 * Represents the notion of a clue in the Codenames game.
 * Clues suggest codenames on the board. For bookkeeping sake,
 * This object stores information about how many of each type
 * of card a particular clue suggests.
 */
public class Clue
{
    //region Fields
    /**
     * The clue word
     */
    public final String word;
    /**
     * The number of cards on the board associated with that clue word
     */
    public int value;
    // Added by Michael Wilgus
    // Bookkeeping variables for AI descision making
    public boolean isActiveCodename = false;
    private int redSuggested = 0;
    private int blueSuggested = 0;
    private int civilianSuggested = 0;
    private boolean assassinSuggested = false;
    //endregion

    //region Properties
    private final ArrayList<Card> cards = new ArrayList<>();
    /**
     * Get the cards on the board that are suggested by this clue
     * Added by Michael Wilgus
     * @return A list of cards associated with the clue
     */
    public ArrayList<Card> getCards()
    {
        return cards;
    }
    //endregion

    //region Constructors
    /**
     * Creates a new Clue
     * @param word Word of the clue
     */
    public Clue(String word)
    {
        this.word = word;
    }
    //endregion

    //region Methods
    /**
     * Method to associate a card object with this clue
     * Indicates that this clue suggests the codename on the card
     * Added by Michael Wilgus
     * @param card Card to be suggested
     * @return True if the card is successfully added and false otherwise
     */
    public boolean addCard(Card card)
    {
        boolean added = cards.add(card);

        if (!added) { return false; }

        switch (card.getType())
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
     * Added by Michael Wilgus
     * @param card Card to be removed
     * @return True if the card is successfully removed and false otherwise
     */
    public boolean removeCard(Card card)
    {
        boolean removed = cards.remove(card);

        if (!removed) { return false; }

        switch (card.getType())
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
     * Added by Michael Wilgus
     * @return True if this clue suggests at least one unrevealed card and false otherwise
     */
    public boolean suggestsSomeCard()
    {
        return redSuggested > 0 || blueSuggested > 0 || civilianSuggested > 0 || assassinSuggested == true;
    }

    /**
     * Answers the question: Does this clue suggest any card besides any civilian or the assassin
     * Added by Michael Wilgus
     * @return True if this clue only suggests civilians and/or the assassin and false otherwise
     */
    public boolean onlySuggestsAssassinOrCivilian()
    {
        return redSuggested == 0 && blueSuggested == 0;
    }

    /**
     * Gets the number of cards that are suggested for a particular team
     * Added by Michael Wilgus
     * @param team The PlayerType to get the number of suggested cards for
     * @return Either the number of RED cards suggested or BLUE cards suggested
     */
    public int getNumberOfCardsSuggestedForTeam(PlayerType team)
    {
        switch (team)
        {
            case RED:
                return redSuggested;
            case BLUE:
                return blueSuggested;
            default:
                return 0; // Keep the compiler happy
        }
    }

    /**
     * Gets the number of cards that are suggested other than cards for a particular team
     * Added by Michael Wilgus
     * @param team The PlayerType to get the number of suggested cards for
     * @return Either the number BLUE+CIVILIAN+ASSASSIN or RED+CIVILIAN+ASSASSIN cards suggested
     */
    public int getComplementOfCardsSuggestedForTeam(PlayerType team)
    {
        int answer = 0;
        switch (team)
        {
            case RED:
                answer += blueSuggested;
            case BLUE:
                answer += redSuggested;
        }
        answer += civilianSuggested + (assassinSuggested ? 1 : 0);
        return answer;
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
