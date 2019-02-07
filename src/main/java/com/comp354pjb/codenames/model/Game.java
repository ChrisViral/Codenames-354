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
import com.comp354pjb.codenames.model.player.*;
import com.comp354pjb.codenames.observer.events.ClueGivenEvent;
import com.comp354pjb.codenames.observer.events.PhaseEvent;
import com.comp354pjb.codenames.observer.events.RoundEvent;

import java.util.*;

public class Game
{
    //region Constants
    /**
     * Random number generator for the Game
     */
    public static final Random RANDOM = new Random();
    //endregion

    //region Events
    /**
     * Clue given event
     */
    public final ClueGivenEvent onClueGiven = new ClueGivenEvent();
    /**
     * Phase change event
     */
    public final PhaseEvent onPhaseChange = new PhaseEvent();
    /**
     * Round change event
     */
    public final RoundEvent onRoundChange = new RoundEvent();
    //endregion

    //region Fields
    private PlayerType startTeam;
    private int playerIndex, round;
    private ArrayList<IPlayer> players = new ArrayList<>();
    //endregion

    //region Properties
    private final Board board;
    /**
     * Gets this game's Board
     */
    public Board getBoard()
    {
        return this.board;
    }

    private int guessesLeft;
    /**
     * The amount of guesses left
     */
    public int getGuessesLeft()
    {
        return this.guessesLeft;
    }
    /**
     * Sets the amount of guesses left
     */
    public void setGuessesLeft(int guessesLeft)
    {
        this.guessesLeft = guessesLeft;
    }

    private int redCardsRevealed;
    /**
     * Gets the amount of red cards revealed
     */
    public int getRedCardsRevealed()
    {
        return this.redCardsRevealed;
    }
    /**
     * Sets the amount of red cards revealed
     */
    public void setRedCardsRevealed(int redTilesRevealed)
    {
        this.redCardsRevealed = redTilesRevealed;
    }

    private int blueCardsRevealed;
    /**
     * Gets the amount of blue cards revealed
     */
    public int getBlueCardsRevealed()
    {
        return this.blueCardsRevealed;
    }
    /**
     * Sets the amount of blue cards revealed
     */
    public void setBlueCardsRevealed(int blueTilesRevealed)
    {
        this.blueCardsRevealed = blueTilesRevealed;
    }

    private Clue clue;
    /**
     * Sets the current clue
     */
    public void setCurrentClue(Clue clue)
    {
        this.clue = clue;
        this.onClueGiven.invoke(clue);
    }

    private boolean assassinRevealed;
    /**
     * Sets if the assassin card has been revealed
     */
    public void setAssassinRevealed(boolean assassinRevealed)
    {
        this.assassinRevealed = assassinRevealed;
    }

    private PlayerType winner;
    /**
     * Gets the game's winner
     */
    public PlayerType getWinner()
    {
        return this.winner;
    }
    /**
     * Sets the game's winner
     */
    public void setWinner(PlayerType winner)
    {
        this.winner = winner;
    }

    private PlayerType loser;
    /**
     * Gets the game's loser
     */
    public PlayerType getLoser()
    {
        return loser;
    }
    /**
     * Sets the game's loser
     */
    public void setLoser(PlayerType loser)
    {
        this.loser = loser;
    }

    private String phase;
    /**
     * Gets the game's phase
     */
    public String getPhase()
    {
        return this.phase;
    }
    /**
     * Sets the game's phase
     */
    public void setPhase(String phase)
    {
        this.phase = phase;
        this.onPhaseChange.invoke(phase);
    }
    //endregion

    //region Constructors
    /**
     * Creates a new Game object and correctly sets up the board and cards, as well as players
     */
    public Game()
    {
        chooseStartingPlayer();
        this.board = new Board(DatabaseHelper.selectWords(25), this.startTeam);
    }
    //endregion

    //region Methods
    /**
     * Decides starting team and creates the necessary players for the game
     */
    private void chooseStartingPlayer()
    {
        if (RANDOM.nextBoolean())
        {
            this.startTeam = PlayerType.BLUE;
            System.out.println("Blue Team will start, which means they must guess 9 cards");
            System.out.println("Red Team will go second, which means they must guess 8 cards");
            this.players.add(new SpyMasterAI(PlayerType.BLUE));
            this.players.add(new OperativeAI(PlayerType.BLUE));
            this.players.add(new SpyMasterAI(PlayerType.RED));
            this.players.add(new OperativeAI(PlayerType.RED));
        }
        else
        {
            this.startTeam = PlayerType.RED;
            System.out.println("Red Team will start, which means they must guess 9 cards");
            System.out.println("Blue Team will go second, which means they must guess 8 cards");
            this.players.add(new SpyMasterAI(PlayerType.RED));
            this.players.add(new OperativeAI(PlayerType.RED));
            this.players.add(new SpyMasterAI(PlayerType.BLUE));
            this.players.add( new OperativeAI(PlayerType.BLUE));
        }
    }

    /**
     * Checks if the game must end
     * @return True if a winning game condition has been met
     */
    public boolean checkWinner()
    {
        //Game ends as soon as the Assassin is revealed
        if (this.assassinRevealed)
        {
            return true;
        }
        else
        {
             //Check the starting team for correct card numbers
            switch (this.startTeam)
            {
                case RED:
                    return this.redCardsRevealed == 9 || this.blueCardsRevealed == 8;

                case BLUE:
                    return this.redCardsRevealed == 8 || this.blueCardsRevealed == 9;

                default:
                    return false;
            }
        }
    }

    /**
     * This method plays the next turn of the game
     */
    public void enterNextGameTurn()
    {
        //Play the current player's turn
        IPlayer current = this.players.get(this.playerIndex);
        current.playTurn(this);

        //If a SpyMaster, pass the next turn to the Operative
        //If an Operative, pass the next turn only if you have no more guesses
        if (current instanceof SpyMasterAI || this.guessesLeft == 0)
        {
            this.playerIndex = (this.playerIndex + 1) % this.players.size();

            if (this.playerIndex == 0)
            {
                this.round++;
                this.onRoundChange.invoke(this.round);
            }
        }

    }
    //endregion
}
