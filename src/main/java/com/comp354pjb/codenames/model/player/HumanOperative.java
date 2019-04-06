/*
 * HumanOperative.java
 * Created by: Christophe Savard
 * Created on: 05/04/19
 *
 * Contributors:
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

/**
 * Human interface for operatives
 */
public class HumanOperative extends Strategy
{
    //region fields
    private boolean ready;
    //endregion

    //region Constructors
    /**
     * Creates a new HumanOperative
     * @param game Game this human is linked to
     * @param team Team this human is linked to
     */
    public HumanOperative(Game game, PlayerType team)
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
     * Ready's a human player's turn
     */
    @Override
    protected void executeStrategy()
    {
        game.onButtonStateChanged.invoke(true);
        this.ready = true;
    }

    /**
     * Registers a human input at the given coordinates
     * @param x X coordinate of the input
     * @param y Y coordinate of the input
     */
    public void registerInput(int x, int y)
    {
        if (this.ready)
        {
            game.revealCard(game.getBoard().getCard(x, y));
            // We used up our guesses so we are done
            if (game.getGuessesLeft() == 0)
            {
                this.ready = false;
                this.game.endCurrentTurn();
                game.onButtonStateChanged.invoke(false);
            }
        }
    }
    //endregion
}