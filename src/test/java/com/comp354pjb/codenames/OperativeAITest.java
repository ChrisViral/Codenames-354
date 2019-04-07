/*
 * HumanOperativeTest.java
 * Created by: Rezza-Zairan Zaharin
 * Created on: 04/06/19
 *
 * Contributors:
 * Rezza-Zairan Zaharin
 *
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.player.OperativeAI;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.player.PlayerIntelligence;
import com.comp354pjb.codenames.model.board.Card;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class OperativeAITest
{
    /*
     * Added by Rezza-Zairan Zaharin
     *
     * Tests to see if OperativeAI reveals a card
     */
    @Test
    public void getRandomCard()
    {
        //Creates game and board
        Game game = new Game();
        Board board = game.getBoard();


        //Instantiates players
        PlayerIntelligence intelligence[] = {PlayerIntelligence.DUMB, PlayerIntelligence.DUMB, PlayerIntelligence.DUMB, PlayerIntelligence.DUMB};
        game.setPlayers(intelligence);

        //Runs the game
        game.endCurrentTurn();
        game.enterNextGameTurn();

        //Boolean for testing
        Boolean TestFailsIfFalse = false;

        for (int i=0; i < 5; i++)
        {
            for (int j=0; j < 5; j++)
            {
                if (board.getCard(i,j).isRevealed())
                {
                    TestFailsIfFalse = true;
                    break;
                }
            }
        }

        assertTrue(TestFailsIfFalse);
    }


}
