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
import com.comp354pjb.codenames.model.player.PlayerType;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

import java.util.*;



public class PlayerTest {

    @Test
    public void operativePlayTurnShouldRevealACard() {
        Game game = new Game();
        Player player = new Player(game, PlayerType.RED, new OperativeAI());
        game.setCurrentPlayer(player);

        // A card should be revealed when an operative plays
        player.play();

        // Get a collection of the revealed status of all cards in the game
        ArrayList<Boolean> cardStatus = new ArrayList<>();
        Board board = game.getBoard();
        for(int i = 0; i < 5; i++) {
            int stride = i * 5;
            for(int j = 0; j < 5; j++) {
                int index = stride + j;
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
