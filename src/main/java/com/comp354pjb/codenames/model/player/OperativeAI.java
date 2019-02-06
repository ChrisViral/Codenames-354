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
        boolean isComplete= false;
        while (!isComplete)
        {
            int row = (int)(5 * Math.random()), col = (int)(5 * Math.random());
            if (!game.getBoard().getCard(row, col).isRevealed())
            {
                //actions for revealing a civilian card
                if(game.getBoard().getCard(row, col).getType() == CardType.CIVILIAN)
                {
                    game.getBoard().revealAt(row,col);
                    game.setGuessesLeft(0);
                }
                //actions for revealing an assassin card
                else if(game.getBoard().getCard(row, col).getType() == CardType.ASSASSIN)
                {
                    game.getBoard().revealAt(row,col);
                    game.setGuessesLeft(0);
                    game.setLoser(teamColor);
                    game.setAssassinRevealed(true);
                }
                //actions for revealing a blue or red card
                else if(game.getBoard().getCard(row, col).getType() != teamColor.getCardType())
                {
                    if(teamColor==PlayerType.BLUE)
                    {
                        game.getBoard().revealAt(row,col);
                        game.setRedCardsRevealed(game.getRedCardsRevealed()+1);
                        game.setGuessesLeft(game.getGuessesLeft()-1);
                    }
                    else
                    {
                        game.getBoard().revealAt(row,col);
                        game.setBlueCardsRevealed(game.getBlueCardsRevealed()+1);
                        game.setGuessesLeft(game.getGuessesLeft()-1);
                    }
                }
                isComplete = true;
            }
        }
    }

    }



