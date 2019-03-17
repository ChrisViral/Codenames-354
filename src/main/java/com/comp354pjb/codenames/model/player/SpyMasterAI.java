/*
 * SpyMasterAI.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

/**
 * Basic implementation of a SpyMaster AI. Gets a random clue from the suggestion graph to give.
 */
public class SpyMasterAI extends Strategy
{
    public static final PlayerIntelligence STRATEGY_CLASS = PlayerIntelligence.DUMB;

    private Game game;

    public SpyMasterAI(Game game)
    {
        this.game = game;
    }

    //region Methods

    /**
     * Plays the dumb SpyMaster AI turn
     */
    public void execute()
    {
        game.setPhase(this.team.niceName() + " SpyMaster");
        //Get a random hint that is *not* a word in the board
        Clue clue = game.getSuggestionGraph().getRandomClue();
        clue.value = Game.RANDOM.nextInt(clue.getCards().size()) + 1;

        //Give out the clue
        game.setCurrentClue(clue);
        finished = true;
    }
    //endregion
}
