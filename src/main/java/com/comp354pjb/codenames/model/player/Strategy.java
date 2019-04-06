/*
 * Strategy.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 * Michael Wilgus
 *
 * Description:
 * Represents a strategy that a Player might take to win the game.
 * Contains a single action method that is intended to be Overridden.
 * This method will implement the logic for giving/guessing clues/cards.
 * Separation from actual players means that new strategies can be added
 * without modifying the Player class directly.
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;

/**
 * Abstract class for the strategy of Player
 * (See above for full description)
 */
abstract public class Strategy
{
    //region Fields
    protected final Game game;
    protected final PlayerType team;
    //endregion

    //region Constructors
    /**
     * Creates a new Strategy, linked to the given Game
     * @param game Game linked to this Strategy
     */
    protected Strategy(Game game, PlayerType team)
    {
        this.game = game;
        this.team = team;
    }
    //endregion

    //region Abstract methods
    /**
     * Plays a given player's turn according to rules defined in the method
     * Modified by Michael Wilgus (Rename to clearly indicate that this conforms to Strategy Pattern)
     */
    protected abstract void executeStrategy();

    /**
     * Player type title
     * @return The title of this player
     */
    protected abstract String title();
    //endregion

    //region Methods
    /**
     * Pick a random card and check if it belonged to our team
     * @param clue Clue to get the cards from
     * @return If the card belongs to our team or not
     */
    protected boolean pickCard(Clue clue)
    {
        ArrayList<Card> cards = clue.getCards();
        int i = Game.RANDOM.nextInt(cards.size());
        Card card = cards.get(i);
        game.revealCard(card);

        return card.getType().equals(team.getCardType());
    }

    /**
     * Executes the Strategy's turn
     */
    public void execute()
    {
        game.setPhase(this.team.niceName() + " " + title());
        executeStrategy();
    }
    //endregion
}
