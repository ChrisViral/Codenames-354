package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.Controller;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.CardType;
import com.comp354pjb.codenames.model.player.*;

import java.util.*;

public class Game
{
    private static final Random RANDOM = new Random();

    /**
     * The following are fields for statistics
     */
    private String currentHint = "";
    private int hintNum = 0, roundCount=1, redTilesRevealed=0, blueTilesRevealed=0;
    private CardType startTeam, winner, loser;


    private boolean assassinRevealed =false;


    private final Controller controller;
    public Controller getController()
    {
        return this.controller;
    }

    private Board board;
    public Board getBoard()
    {
        return this.board;
    }


    public Game(Controller controller)
    {
        this.controller = controller;
        String[] words = DatabaseHelper.selectWords(25);
        this.board = new Board(this, words, chooseStartingPlayer());
    }

    private static CardType chooseStartingPlayer()
    {
        return RANDOM.nextBoolean() ? CardType.RED : CardType.BLUE;
    }

    public void decideFirstRoll(CardType red, CardType blue)
    {
        ArrayList<IPlayer> bluePlayers = new ArrayList<IPlayer>();
        bluePlayers.add(new SpyMasterAI(CardType.BLUE), new OperativeAI(CardType.BLUE));
        ArrayList<IPlayer> redPlayers = new ArrayList<IPlayer>();
        redPlayers.add(new SpyMasterAI(CardType.RED), new OperativeAI(CardType.RED));

        if (RANDOM.nextBoolean())
        {
            startTeam = CardType.BLUE;
            System.out.println("Blue Team will start, which means they must guess 9 cards");
            System.out.println("Red Team will go second, which means they must guess 8 cards");
            enterGameLoop(bluePlayers, redPlayers);
        }
        else
        {
            startTeam = CardType.RED;
            System.out.println("Red Team will start, which means they must guess 9 cards");
            System.out.println("Blue Team will go second, which means they must guess 8 cards");
            enterGameLoop(redPlayers, bluePlayers);
        }
    }

    public boolean checkWinner()
    {
        if(startTeam == CardType.BLUE)
        {
            if(redTilesRevealed == 8 || blueTilesRevealed==9 || assassinRevealed==true )
                return true;
        }
        else
        {
            if(redTilesRevealed == 9 || blueTilesRevealed==8 || assassinRevealed==true )
                return true;
        }
        return false;

    }

    private void enterGameLoop(ArrayList<IPlayer> players1,ArrayList<IPlayer> players2)
    {
        //create Players
        boolean play =true;
        while(play)
        {
            for(int i=0;i<players1.size(); i++)
            {
                players1.get(i).playTurn(this );
            }
            if(checkWinner())
                play=false;

            for(int i=0;i<players2.size(); i++)
            {
                players2.get(i).playTurn(this );
            }
            if(checkWinner())
                play=false;
        }
    }

    public void setHintNum(int hintNum)
    {
        this.hintNum = hintNum;
    }

    public void setRoundCount(int roundCount)
    {
        this.roundCount = roundCount;
    }

    public void setRedTilesRevealed(int redTilesRevealed)
    {
        this.redTilesRevealed = redTilesRevealed;
    }

    public void setBlueTilesRevealed(int blueTilesRevealed)
    {
        this.blueTilesRevealed = blueTilesRevealed;
    }


    public int getRoundCount()
    {
        return roundCount;
    }

    public int getRedTilesRevealed()
    {
        return redTilesRevealed;
    }

    public int getBlueTilesRevealed()
    {
        return blueTilesRevealed;
    }

    public String getCurrentHint()
    {
        return currentHint;
    }

    public int getHintNum()
    {
        return hintNum;
    }

    public void setCurrentHint(String currentHint)
    {
        this.currentHint = currentHint;
    }

    public boolean isAssassinRevealed()
    {
        return assassinRevealed;
    }

    public void setAssassinRevealed(boolean assassinRevealed)
    {
        this.assassinRevealed = assassinRevealed;
    }

    public CardType getWinner()
    {
        return winner;
    }

    public void setWinner(CardType winner)
    {
        this.winner = winner;
    }

    public CardType getLoser()
    {
        return loser;
    }

    public void setLoser(CardType loser)
    {
        this.loser = loser;
    }




}
