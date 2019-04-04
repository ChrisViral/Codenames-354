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
 *
 * Description:
 * Plays the dumb Operative AI's turn
 * Randomly determine which card to pick, checking that that card has not been revealed before it is chosen
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

/**
 * Implementation  of Strategy for the AI class
 * (See above for full description)
 */
public class OperativeAI extends Strategy
{
    /**
     * Changes the intelligence level of this operative to dumb
     */
    public static final PlayerIntelligence STRATEGY_CLASS = PlayerIntelligence.DUMB;

    /**
     *
     */
    private Game game;

    public OperativeAI(Game game)
    {
        this.game = game;
    }

    //region Methods
    // Modified by Michael Wilgus
    @Override
    public void execute()
    {
        game.setPhase(this.team.niceName() + " Operative");
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



