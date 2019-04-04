package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

public class HumanOperative extends Strategy
{
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
        this.ready = true;
    }

    public void registerInput(int x, int y)
    {
        if (this.ready)
        {
            game.revealCard(game.getBoard().getCard(x, y));// We used up our guesses so we are done
            if (game.getGuessesLeft() == 0)
            {
                this.ready = false;
                this.game.endCurrentTurn();
            }
        }
    }
}
