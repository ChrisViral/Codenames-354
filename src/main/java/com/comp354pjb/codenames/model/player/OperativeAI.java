/*
 * OperativeAI.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

/**
 * Implementation  of Strategy for the AI class
 * Plays the dumb Operative AI's turn
 * Randomly determine which card to pick, checking that that card has not been revealed before it is chosen
 */
public class OperativeAI extends Strategy
{
    //region Constructors
    /**
     * Creates a new OperativeAI
     * @param game Game this AI is linked to
     * @param team Team this AI is linked to
     */
    public OperativeAI(Game game, PlayerType team)
    {
        super(game, team);
    }
    //endregion

    //region Methods
    /**
     * Operative title
     * @return "Operative"
     */
    @Override
    protected String title()
    {
        return "Operative";
    }

    /**
     * Execute's the AI's strategy
     */
    @Override
    protected void executeStrategy()
    {
        // Keep getting random positions until we get an unrevealed card
        while (true)
        {
            int row = Game.RANDOM.nextInt(5), col = Game.RANDOM.nextInt(5);
            Card card = game.getBoard().getCard(row, col);
            if (!card.isRevealed())
            {
                //Reveal card
                game.revealCard(card);
                break;
            }
        }
        if (game.getGuessesLeft() == 0) { this.game.endCurrentTurn(); }
    }
    //endregion
}



