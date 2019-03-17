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

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.Game;

public class SpyMasterAI extends Strategy
{
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
        String hint;
        do
        {
            hint = DatabaseHelper.getRandomClue();
        }
        while(game.getBoard().hasWord(hint));
        //Give out the clue
        game.setCurrentClue(new Clue(hint, Game.RANDOM.nextInt(3) + 1));
        finished = true;
    }
    //endregion
}
