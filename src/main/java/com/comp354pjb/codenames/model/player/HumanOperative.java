/*
 * HumanOperative.java
 * Created by: Christophe Savard
 * Created on: 05/04/19
 *
 * Contributors:
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

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
            Card card = game.getBoard().getCard(x, y);
            Commander.log(name() + " revealed the " + card.getType().niceName() + " card " + card.getWord() + " at location (" + x + ", " + y + ")");
            game.revealCard(card);
            // We used up our guesses so we are done
            if (game.getGuessesLeft() == 0)
            {
                this.ready = false;
                game.onButtonStateChanged.invoke(false);
                this.game.endCurrentTurn();
            }
        }
    }
    //endregion
}