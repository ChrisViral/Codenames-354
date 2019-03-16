/*
 * OperativeAI.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

import java.lang.*;

/**
 * Implementation  of Strategy for the AI class
 */
public class OperativeAI implements Strategy
{
    //region Methods
    /**
     * Plays the dumb Operative AI's turn
     * Randomly determine which card to pick, checking that that card has not been revealed before it is chosen
     * @param player The player to play this turn on
     */
    @Override
    public void execute(Player player)
    {
        Game game = player.game;
        player.game.setPhase(player.teamName + " Operative");
        while(true) {
            int row = Game.RANDOM.nextInt(5), col = Game.RANDOM.nextInt(5);
            Card card = game.getBoard().getCard(row, col);
            if (!card.isRevealed()) {
                //Reveal card
                game.revealCard(card);
                break;
            }
        }
    }
    //endregion
}



