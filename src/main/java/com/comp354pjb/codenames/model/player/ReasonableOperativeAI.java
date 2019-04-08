/*
 * ReasonableOperativeAI.java
 * Created by: Michael Wilgus
 * Created on: 14/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;

/**
 * Medium level implementation of an Operative AI. Makes reasonable guesses given a clue
 * Gets the current clue given by the SpyMaster and inspects it to see what cards it suggests.
 * Randomly pick one and reveal it.
 */
public class ReasonableOperativeAI extends Strategy
{
    //region Constructors
    /**
     * Creates a new ReasonableOperativeAI
     * @param game Game this AI is linked to
     * @param team Team this AI is linked to
     */
    public ReasonableOperativeAI(Game game, PlayerType team)
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
     * Execute's this AI's turn
     */
    @Override
    protected void executeStrategy()
    {
        //Get the latest clue given by the SpyMaster
        Clue clue = game.getCurrentClue();
        pickCard(clue);

        // We used up our guesses so we are done
        if (game.getGuessesLeft() == 0) { this.game.endCurrentTurn(); }
    }
    //endregion
}
