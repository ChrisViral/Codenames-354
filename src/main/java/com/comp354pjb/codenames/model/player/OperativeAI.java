/*
 * OperativeAI.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.board.CardType;
import java.lang.*;

/**
 * Implementation  of IPlayer for the AI class
 */
public class OperativeAI implements IPlayer
{
    /**
     * Plays the dumb Operative AI's turn
     * Randomly determine which card to pick, checking that that card has not been revealed before it is chosen
     * @param player The player to play this turn on
     */
    @Override
    public void playTurn(Player player)
    {
        Game game = player.game;
        player.game.setPhase(player.teamName + " Operative");
        boolean isComplete = false;
        while (!isComplete)
        {
            int row = Game.RANDOM.nextInt(5), col = Game.RANDOM.nextInt(5);
            Card card = game.getBoard().getCard(row, col);
            if (!card.isRevealed())
            {
                game.getBoard().revealCard(card);
                //actions for revealing a civilian card
                if(card.getType() == CardType.CIVILIAN)
                {
                    game.setGuessesLeft(0);
                }
                //actions for revealing an assassin card
                else if(card.getType() == CardType.ASSASSIN)
                {
                    game.setGuessesLeft(0);
                    game.setLoser(player.team);
                    game.setAssassinRevealed(true);
                }
                //actions for revealing a blue or red card
                else if(card.getType() != player.team.getCardType())
                {
                    if(player.team == PlayerType.BLUE)
                    {
                        game.setRedCardsRevealed(game.getRedCardsRevealed() + 1);
                        game.setGuessesLeft(game.getGuessesLeft() - 1);
                    }
                    else
                    {
                        game.setBlueCardsRevealed(game.getBlueCardsRevealed() + 1);
                        game.setGuessesLeft(game.getGuessesLeft() - 1);
                    }
                }
                isComplete = true;
            }
        }
    }
}



