/*
 * Game.java
 * Created by: Steven Zanga
 * Created on: 23/01/19
 *
 * Contributors:
 * Steven Zanga
 * Benjamin Therien
 * Christophe Savard
 */

package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.CardType;
import com.comp354pjb.codenames.model.player.*;

import java.util.*;

public class Game
{
    // creating random object.
    private static final Random RANDOM = new Random();

    /**
     * The following are fields for statistics
     */
    private String currentClue = "", phase="";
    private int clueNum = 0, roundCount=1, redCardsRevealed=0, blueCardsRevealed=0, guessesLeft=0, index=0;
    private PlayerType startTeam, winner, loser;
    private boolean assassinRevealed =false;
    private ArrayList<IPlayer> players = new ArrayList<>();

    private Board board;
    public Board getBoard()
    {
        return this.board;
    }

    /**
     * Default constructor, selection of 25 words, sets main controller and creates a new board with the selected 25 cards
     */
    public Game()
    {
        String[] words = DatabaseHelper.selectWords(25);
        this.startTeam = chooseStartingPlayer();
        this.board = new Board(words, this.startTeam);
    }

    
    /**
     * decide what team starts first, red or blue
     * @return returns whether the starting player type will be red or blue.
     */
    private static PlayerType chooseStartingPlayer()
    {
        return RANDOM.nextBoolean() ? PlayerType.RED : PlayerType.BLUE;
    }

    /**
     * This method is used to start the game with the correct player order
     * by passing an array of player to the enterGameLoop
     */
    public void decideFirstRoll()
    {
        if (this.startTeam == PlayerType.BLUE)
        {
            System.out.println("Blue Team will start, which means they must guess 9 cards");
            System.out.println("Red Team will go second, which means they must guess 8 cards");
            ArrayList<IPlayer> bluePlayers = new ArrayList<IPlayer>();
            players.add(new SpyMasterAI(PlayerType.BLUE));
            players.add(new OperativeAI(PlayerType.BLUE));
            players.add(new SpyMasterAI(PlayerType.RED));
            players.add(new OperativeAI(PlayerType.RED));
            enterNextGameTurn();
        }
        else
        {
            System.out.println("Red Team will start, which means they must guess 9 cards");
            System.out.println("Blue Team will go second, which means they must guess 8 cards");
            ArrayList<IPlayer> redPlayers = new ArrayList<IPlayer>();
            players.add(new SpyMasterAI(PlayerType.RED));
            players.add(new OperativeAI(PlayerType.RED));
            players.add(new SpyMasterAI(PlayerType.BLUE));
            players.add( new OperativeAI(PlayerType.BLUE));
            enterNextGameTurn();
        }
    }

    /**
     * @return true if a winning game condition has been met
     */
    public boolean checkWinner()
    {
        if(startTeam == PlayerType.BLUE)
        {
            if(redCardsRevealed == 8 || blueCardsRevealed==9 || assassinRevealed==true )
                return true;
        }
        else
        {
            if(redCardsRevealed == 9 || blueCardsRevealed==8 || assassinRevealed==true )
                return true;
        }
        return false;

    }

    /**
     * This method plays the next turn of the game
     */
    private void enterNextGameTurn()
    {
        if(index % 2 ==0)
        {
            players.get(index).playTurn(this);
            index++;
        }
        else
        {
            players.get(index).playTurn(this);
            if(guessesLeft==0)
            {
                if(index ==3)
                    setRoundCount(roundCount+1);
                index= ++index % 4;
            }
        }
    }

    /**
     * The following lnes are all getters/setters
     */

    public int getGuessesLeft()
    {
        return guessesLeft;
    }

    public void setGuessesLeft(int guessesLeft)
    {
        this.guessesLeft = guessesLeft;
    }

    public void setClueNum(int clueNum)
    {
        this.clueNum = clueNum;
    }

    public void setRoundCount(int roundCount)
    {
        this.roundCount = roundCount;
    }

    public void setRedCardsRevealed(int redTilesRevealed)
    {
        this.redCardsRevealed = redTilesRevealed;
    }

    public void setBlueCardsRevealed(int blueTilesRevealed)
    {
        this.blueCardsRevealed = blueTilesRevealed;
    }


    public int getRoundCount()
    {
        return roundCount;
    }

    public int getRedCardsRevealed()
    {
        return redCardsRevealed;
    }

    public int getBlueCardsRevealed()
    {
        return blueCardsRevealed;
    }

    public String getCurrentClue()
    {
        return currentClue;
    }

    public int getClueNum()
    {
        return clueNum;
    }

    public void setCurrentClue(String currentClue)
    {
        this.currentClue = currentClue;
    }

    public boolean isAssassinRevealed()
    {
        return assassinRevealed;
    }

    public void setAssassinRevealed(boolean assassinRevealed)
    {
        this.assassinRevealed = assassinRevealed;
    }

    public PlayerType getWinner()
    {
        return winner;
    }

    public void setWinner(PlayerType winner)
    {
        this.winner = winner;
    }

    public PlayerType getLoser()
    {
        return loser;
    }

    public void setLoser(PlayerType loser)
    {
        this.loser = loser;
    }

    public String getPhase()
    {
        return phase;
    }

    public void setPhase(String phase)
    {
        this.phase = phase;
    }
}
