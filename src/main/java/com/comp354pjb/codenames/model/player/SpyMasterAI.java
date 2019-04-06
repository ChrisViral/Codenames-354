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

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.Game;

/**
 * Plays the dumb SpyMaster AI turn.
 * Simply randomly consults the graph of clues/cards.
 */
public class SpyMasterAI extends Strategy
{
    //region Constructors
    /**
     * Creates a new SpyMasterAI
     * @param game Game this AI is linked to
     * @param team Team this AI is linked to
     */
    public SpyMasterAI(Game game, PlayerType team)
    {
        super(game, team);
    }
    //endregion

    //region Methods
    /**
     * SpyMaster title
     * @return "SpyMaster"
     */
    @Override
    protected String title()
    {
        return "SpyMaster";
    }

    /**
     * Gets the suggestion graph for the current game and passes it a comparator so that it can find
     * some clue that fits this strategies criteria
     */
    @Override
    public void executeStrategy()
    {
        // Get a random hint that is *not* a word in the board
        Clue clue = game.getSuggestionGraph().getRandomClue();
        clue.value = Game.RANDOM.nextInt(clue.getCards().size()) + 1;

        // Give the clue
        Commander.log(name() + " gave the clue " + clue);
        game.setCurrentClue(clue);

        // We are done
        this.game.endCurrentTurn();
    }
    //endregion
}
