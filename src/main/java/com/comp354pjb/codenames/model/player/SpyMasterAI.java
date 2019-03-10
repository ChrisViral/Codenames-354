/*
 * SpyMasterAI.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.Game;

public class SpyMasterAI implements IPlayer
{
    //region Methods
    /**
     * Plays the dumb SpyMaster AI turn
     * @param player Player to play the turn on
     */
    public void playTurn(Player player)
    {
        player.game.setPhase(player.teamName + " SpyMaster");
        //Get a random hint that is *not* a word in the board
        String hint;
        do
        {
            hint = DatabaseHelper.getRandomClue();
        }
        while(player.game.getBoard().hasWord(hint));
        //Give out the clue
        player.game.setCurrentClue(new Clue(hint, Game.RANDOM.nextInt(3) + 1));


    }
    //endregion
}
