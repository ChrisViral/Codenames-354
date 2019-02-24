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
    //region Methods
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
                //Reveal card
                game.getBoard().revealCard(card);

                //Take according actions
                switch (card.getType())
                {
                    //Actions for revealing an assassin card
                    case ASSASSIN:
                        game.setLoser(player.team);
                        game.setAssassinRevealed(true);
                    //Actions for revealing a civilian card
                    case CIVILIAN:
                        game.setGuessesLeft(0);
                        break;

                    //Actions for revealing a red card
                    case RED:
                        game.setRedCardsRevealed(game.getRedCardsRevealed() + 1);
                        game.setGuessesLeft(player.team != PlayerType.RED ? 0 : game.getGuessesLeft() - 1);
                        break;

                    //Actions for revealing a red card
                    case BLUE:
                        game.setBlueCardsRevealed(game.getBlueCardsRevealed() + 1);
                        game.setGuessesLeft(player.team != PlayerType.BLUE ? 0 : game.getGuessesLeft() - 1);
                        break;

                }
                isComplete = true;
            }
        }
    }
    //endregion
}



