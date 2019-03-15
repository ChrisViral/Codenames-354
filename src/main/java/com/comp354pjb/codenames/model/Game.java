/*
 * Game.java
 * Created by: Steven Zanga
 * Created on: 23/01/19
 *
 * Contributors:
 * Steven Zanga
 * Benjamin Therien
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.Card;
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

    private Player currentPlayer;

    //score keeping members
    private int guessesLeft;

    private int redCardsRevealed;

    private int blueCardsRevealed;

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

    private SuggestionGraph graph;
    /**
     *
     * @return
     */
    public SuggestionGraph getSuggestionMap()
    {
        return graph;
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
        this.graph = createSuggestionMap();
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
        this.players.add(new SpyMaster(this, this.startTeam, new RiskySpyMasterAI()));
        this.players.add(new Player(this, this.startTeam, new ReasonableOperativeAI()));
        this.players.add(new SpyMaster(this, second, new SafeSpyMasterAI()));
        this.players.add(new Player(this, second, new ReasonableOperativeAI()));
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
        this.currentPlayer = this.players.get(this.playerIndex);

        this.currentPlayer.play();

        //If a SpyMaster, pass the next turn to the Operative
        //If an Operative, pass the next turn only if you have no more guesses
        if (this.currentPlayer.getClass().equals(SpyMaster.class) || this.guessesLeft == 0)
        {
            this.playerIndex = (this.playerIndex + 1) % this.players.size();

            if (this.playerIndex == 0)
            {
                this.onRoundChange.invoke(++this.round);
            }
        }

    }

    private Clue currentClue;

    public Clue getCurrentClue()
    {
        return this.currentClue;
    }

    /**
     * Sets the current clue
     * @param clue New clue
     */
    public void setCurrentClue(Clue clue)
    {
        this.currentClue = clue;
        this.guessesLeft = clue.value;
        this.onClueGiven.invoke(clue);
    }

    /**
     * Reveal a card on this games board
     * @param card The card to reveal
     */
    public void revealCard(Card card)
    {
        this.board.revealCard(card);
        this.graph.pickCard(card.getWord());

        switch (card.getType()) {
            //Actions for revealing an assassin card
            case ASSASSIN:
                this.setLoser(this.currentPlayer.getTeam());
                this.setAssassinRevealed(true);
                //Actions for revealing a civilian card
            case CIVILIAN:
                this.guessesLeft = 0;
                return;

            //Actions for revealing a red card
            case RED:
                this.redCardsRevealed++;
                break;

            //Actions for revealing a red card
            case BLUE:
                this.blueCardsRevealed++;
                break;

        }
        //Take according actions
        this.guessesLeft = (this.currentPlayer.getTeam().getCardType().equals(card.getType())) ? this.guessesLeft - 1 : 0;
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

    //region Helpers
    //
    private SuggestionGraph createSuggestionMap()
    {
        ArrayList<Card> names = board.getCards();
        for(Card c : names)
        {
            String[] clues = DatabaseHelper.getCluesForCodename(c.getWord().toLowerCase());
            for(int i = 0; i < clues.length; i++)
            {
                String clue = DatabaseHelper.toCamelCase(clues[i]);
                c.addClue(clue);
            }
        }

        HashMap<String, Clue> clues = new HashMap<>();
        HashMap<String, Card> cards = new HashMap<>();
        for(Card c : names)
        {
            cards.put(c.getWord(), c);
            HashSet<String> suggestions = c.getClues();
            for(String s : suggestions)
            {
                Clue clue = clues.getOrDefault(s, new Clue(s, 0));
                clue.addCard(c);
                clues.put(s, clue);
            }
        }

        return new SuggestionGraph(clues, cards);
    }
    //endregion
}
