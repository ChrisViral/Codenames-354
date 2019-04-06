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
    //region 
    private final Game game;
    private boolean ready;

    public HumanOperative(Game game)
    {
        this.game = game;
    }

    /**
     * Plays a given player's turn according to rules defined in the method
     * Modified by Michael Wilgus (Rename to clearly indicate that this conforms to Strategy Pattern)
     */
    @Override
    public void execute()
    {
        game.setPhase(this.team.niceName() + " Operative");
        game.onButtonStateChanged.invoke(true);
        this.ready = true;
    }

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
}
