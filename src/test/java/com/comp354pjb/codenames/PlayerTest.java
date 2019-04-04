/*
 * PlayerTest.java
 * Created by: Michael Wilgus
 * Created on: 09/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.player.OperativeAI;
import com.comp354pjb.codenames.model.player.Player;
import com.comp354pjb.codenames.model.player.PlayerIntelligence;
import com.comp354pjb.codenames.model.player.PlayerType;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;


public class PlayerTest
{

    @Test
    public void operativePlayTurnShouldRevealACard()
    {
        PlayerIntelligence[] intelligence = { PlayerIntelligence.SMART, PlayerIntelligence.SMART, PlayerIntelligence.SMART, PlayerIntelligence.SMART };
        Game game = new Game();
        game.setPlayers(intelligence);

        //Play the first SpyMaster turn, then an Operative turn
        game.enterNextGameTurn();
        game.enterNextGameTurn();

        // Get a collection of the revealed status of all cards in the game
        ArrayList<Boolean> cardStatus = new ArrayList<>();
        Board board = game.getBoard();
        for (int i = 0; i < 5; i++)
        {
            int stride = i * 5;
            for (int j = 0; j < 5; j++)
            {
                cardStatus.add(board.getCard(i, j).isRevealed());
            }
        }

        assertThat(cardStatus, hasItem(true));
    }

    @Test
    public void cautiousAIShouldAvoidASS()
    {

    }

    @Test
    public void cautiousAIShouldAvoidOpposingTeam()
    {

    }


}
