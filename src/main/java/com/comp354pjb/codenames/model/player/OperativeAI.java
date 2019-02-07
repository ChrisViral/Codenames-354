/*
 * OperativeAI.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
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
    private PlayerType teamColor;
    private String name;

    /**
     * initializes OperativeAI
     * @param team is the team association for this player
     */
    public OperativeAI(PlayerType team)
    {
        if(team == PlayerType.BLUE)
        {
            name = "Blue Operative";
        }
        else
        {
            name = "Red Operative";
        }
        teamColor = team;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    /**
     * The following method uses a randomizer to determine which card to pick
     * checking that that card has not been revealed before it is chosen
     * @param the game in which this turn us being played
     */
    public void playTurn(Game game)
    {
        game.setPhase(name);
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
                    game.setLoser(teamColor);
                    game.setAssassinRevealed(true);
                }
                //actions for revealing a blue or red card
                else if(card.getType() != teamColor.getCardType())
                {
                    if(teamColor == PlayerType.BLUE)
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



