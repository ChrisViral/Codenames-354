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

import com.comp354pjb.codenames.commander.Commander;
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
    private int playerIndex, round = 1;
    private ArrayList<Player> players = new ArrayList<>();
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

    private PlayerType startTeam;
    /**
     * Gets the starting team colour
     */
    public PlayerType getStartTeam()
    {
        return this.startTeam;
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
    public void setRedCardsRevealed(int redCardsRevealed)
    {
        this.redCardsRevealed = redCardsRevealed;
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
    public void setBlueCardsRevealed(int blueCardsRevealed)
    {
        this.blueCardsRevealed = blueCardsRevealed;
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
     * Gets the winning player
     */
    public PlayerType getWinner()
    {
        return this.winner;
    }
    /**
     * Sets the winning player
     */
    public void setWinner(PlayerType winner)
    {
        this.winner = winner;
        switch (this.winner)
        {
            case RED:
                this.loser = PlayerType.BLUE;
                break;
            case BLUE:
                this.loser = PlayerType.RED;
        }
    }

    private PlayerType loser;
    /**
     * Sets the game's loser
     */
    public void setLoser(PlayerType loser)
    {
        this.loser = loser;
        switch (this.loser)
        {
            case RED:
                this.winner = PlayerType.BLUE;
                break;
            case BLUE:
                this.winner = PlayerType.RED;
        }
    }
    //endregion

    //region Constructors
    /**
     * Creates a new Game object and correctly sets up the board and cards, as well as players
     */
    public Game()
    {
        chooseStartingPlayer();
        this.board = new Board(DatabaseHelper.getRandomCodenames(25), this.startTeam);
    }
    //endregion

    //region Methods
    /**
     * Decides starting team and creates the necessary players for the game
     */
    private void chooseStartingPlayer()
    {
        PlayerType second;
        if (RANDOM.nextBoolean())
        {
            this.startTeam = PlayerType.BLUE;
            second = PlayerType.RED;
        }
        else
        {
            this.startTeam = PlayerType.RED;
            second = PlayerType.BLUE;
        }

        Commander.log(this.startTeam.niceName() + " Team will start, which means they must guess 9 cards");
        Commander.log(second.niceName() + " Team will go second, which means they must guess 8 cards");
        this.players.add(new Player(this, this.startTeam, new SpyMasterAI()));
        this.players.add(new Player(this, this.startTeam, new OperativeAI()));
        this.players.add(new Player(this, second, new SpyMasterAI()));
        this.players.add(new Player(this, second, new OperativeAI()));
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
            int redTarget = 8, blueTarget = 8;
            switch (this.startTeam)
            {
                case RED:
                    redTarget++;
                    break;
                case BLUE:
                    blueTarget++;
                    break;
            }

            //Check for a winner
            if (this.redCardsRevealed == redTarget)
            {
                setWinner(PlayerType.RED);
                return true;
            }
            if (this.blueCardsRevealed == blueTarget)
            {
                setWinner(PlayerType.BLUE);
                return true;
            }
            return false;
        }
    }

    /**
     * This method plays the next turn of the game
     */
    public void enterNextGameTurn()
    {
        //Play the current player's turn
        Player current = this.players.get(this.playerIndex);
        current.play();

        //If a SpyMaster, pass the next turn to the Operative
        //If an Operative, pass the next turn only if you have no more guesses
        if (current.getStrategy() instanceof SpyMasterAI || this.guessesLeft == 0)
        {
            this.playerIndex = (this.playerIndex + 1) % this.players.size();

            if (this.playerIndex == 0)
            {
                this.onRoundChange.invoke(++this.round);
            }
        }

    }

    /**
     * Sets the current clue
     * @param clue New clue
     */
    public void setCurrentClue(Clue clue)
    {
        this.guessesLeft = clue.value;
        this.onClueGiven.invoke(clue);
    }

    /**
     * Sets the game's phase
     * @param phase New game phase
     */
    public void setPhase(String phase)
    {
        this.onPhaseChange.invoke(phase);
    }
    //endregion
}
