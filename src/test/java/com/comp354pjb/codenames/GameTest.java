/*
 * GameTest.java
 * Created by: Michael Wilgus
 * Created on: 07/02/19
 *
 * Contributors:
 * Michael Wilgus
 * Shereece A. A. Victor
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.board.CardType;
import com.comp354pjb.codenames.model.player.Player;
import com.comp354pjb.codenames.model.player.PlayerIntelligence;
import org.junit.Test;

import java.util.Random;


import static org.junit.Assert.assertTrue;

public class GameTest {
    /**
     * Checks to see if choosing the assassin card results in a winner being chosen and that the winner belongs to the opposing team
     *
     * edited  by Shereece A. A. Victor
     */
    @Test
    public void PickingAssassinShouldResultInWinner()
    {
        //Setting up new Game
        Game game = new Game();
        Board board = game.getBoard();

        PlayerIntelligence intelligence[] = {PlayerIntelligence.SMART, PlayerIntelligence.SMART, PlayerIntelligence.SMART, PlayerIntelligence.SMART};
        game.setPlayers(intelligence);

       //Searching for the assassin card on the board
        Card assassin = null;

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(board.getCard(i, j).getType().equals(CardType.ASSASSIN)){
                    assassin=board.getCard(i, j);
                }
            }
        }
        //Revealing the assassin card
        game.revealCard(assassin);

        assertTrue(game.checkWinner());//Checks that a winner was declared
        assertTrue(game.getLoser() == game.getStartTeam());
        assertTrue(game.getLoser() != game.getWinner());
    }



}
