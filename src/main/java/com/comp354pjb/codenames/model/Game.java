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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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
    //region Properties
    private final Board board;
    //region Fields
    private int playerIndex, round = 1;
    //endregion
    private ArrayList<Player> players = new ArrayList<>();
    private PlayerType startTeam;
    private Player currentPlayer;
    //score keeping members
    private int guessesLeft;
    private int redCardsRevealed;
    private int blueCardsRevealed;
    private int civilianCardsRevealed;
    private boolean assassinRevealed;
    private PlayerType winner;
    private PlayerType loser;
    private SuggestionGraph graph;
    private Clue currentClue;

    /**
     * Creates a new Game object and correctly sets up the board and cards, as well as players
     * <p>
     * Update by Rezza-Zairan
     * ----------------------
     * @param passInt is passed by the controller to hold an array of PlayerIntelligence chosen by the user.
     */
    public Game(PlayerIntelligence[] passInt)
    {
        String[] setup = DatabaseHelper.getBoardLayout();
        setPlayers(setup[0], passInt);
        this.board = new Board(DatabaseHelper.getRandomCodenames(25), setup[1]);
        this.graph = createSuggestionGraph();
    }

    /**
     * Gets this game's Board
     */
    public Board getBoard()
    {
        return this.board;
    }

    /**
     * Gets the starting team colour
     */
    public PlayerType getStartTeam()
    {
        return this.startTeam;
    }

    /**
     * Set the player that is currently giving clues or guessing cards
     * @param player The Player associated with the game whose turn it is
     */
    public void setCurrentPlayer(Player player) { this.currentPlayer = player; }

    /**
     * Gets the number of guesses left given the current clue
     */
    public int getGuessesLeft() { return this.guessesLeft; }

    /**
     * Sets if the assassin card has been revealed
     */
    public void setAssassinRevealed(boolean assassinRevealed)
    {
        this.assassinRevealed = assassinRevealed;
    }

    /**
     * Gets the winning player
     */
    public PlayerType getWinner()
    {
        return this.winner;
    }

    /**
     * Sets the winning player and records a win in the database.
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
    //endregion

    //region Constructors

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

    //region Methods

    /**
     * Gets the graph structure that associates clues to words for this game
     * @return A SuggestionGraph that has the current clue to card relationship information for this game
     */
    public SuggestionGraph getSuggestionGraph()
    {
        return graph;
    }

    /**
     * Sets the starting player for the game and initializes the AIs correctly
     * @param startingPlayer Starting team name
     *                       <p>
     *                       Update by Rezza-Zairan
     *                       ----------------------
     * @param passInt        is passed by the controller to hold an array of PlayerIntelligence chosen by the user.
     */
    private void setPlayers(String startingPlayer, PlayerIntelligence[] passInt)
    {

        this.startTeam = PlayerType.parse(startingPlayer);
        PlayerType second = this.startTeam == PlayerType.RED ? PlayerType.BLUE : PlayerType.RED;

        //Rearranging AI according to who starts first
        PlayerIntelligence[] arrangedInt = new PlayerIntelligence[4];
        arrangedInt = passInt;

        if (this.startTeam == PlayerType.BLUE)
        {
            arrangedInt[0] = passInt[2];
            arrangedInt[1] = passInt[3];
            arrangedInt[2] = passInt[0];
            arrangedInt[3] = passInt[1];
        }

        Strategy startSpyMasterStrategy = StrategyFactory.makeStrategy("spymaster", this, arrangedInt[0]);
        Strategy startOperativeStrategy = StrategyFactory.makeStrategy("operative", this, arrangedInt[1]);
        Strategy secondSpyMasterStrategy = StrategyFactory.makeStrategy("spymaster", this, arrangedInt[2]);
        Strategy secondOperativeStrategy = StrategyFactory.makeStrategy("operative", this, arrangedInt[3]);

        Commander.log(this.startTeam.niceName() + " Team will start, which means they must guess 9 cards");
        Commander.log(second.niceName() + " Team will go second, which means they must guess 8 cards");
        this.players.add(new Player(this.startTeam, startSpyMasterStrategy));
        this.players.add(new Player(this.startTeam, startOperativeStrategy));
        this.players.add(new Player(second, secondSpyMasterStrategy));
        this.players.add(new Player(second, secondOperativeStrategy));
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
            recordGame();
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
                recordGame();
                return true;
            }
            if (this.blueCardsRevealed == blueTarget)
            {
                setWinner(PlayerType.BLUE);
                recordGame();
                return true;
            }
            return false;
        }
    }

    /**
     * Records the game stats in the database.
     * -----------
     * Created by Mordechai Zirkind
     */
    private void recordGame()
    {
        DatabaseHelper.addGameToStats("Reds", "Blues", this.round, this.winner.niceName(), this.assassinRevealed, this.civilianCardsRevealed, this.redCardsRevealed, this.redCardsRevealed);
    }

    /**
     * This method plays the next turn of the game
     */
    public void enterNextGameTurn()
    {
        //Play the current player's turn
        this.currentPlayer = this.players.get(this.playerIndex);

        this.currentPlayer.play();

        if (currentPlayer.isFinished())
        {
            currentPlayer.setFinished(false);
            this.playerIndex = (this.playerIndex + 1) % this.players.size();

            if (this.playerIndex == 0)
            {
                this.onRoundChange.invoke(++this.round);
            }
        }

    }

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

        switch (card.getType())
        {
            //Actions for revealing an assassin card
            case ASSASSIN:
                this.setLoser(this.currentPlayer.getTeam());
                this.setAssassinRevealed(true);
                this.guessesLeft = 0;
                return;
            //Actions for revealing a civilian card
            case CIVILIAN:
                this.civilianCardsRevealed++;
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
        if (this.currentPlayer.getTeam().getCardType().equals(card.getType()))
        {
            this.guessesLeft--;
        }
        else
        {
            this.guessesLeft = 0;
        }
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
    private SuggestionGraph createSuggestionGraph()
    {
        // Get all the cards on the board
        ArrayList<Card> codenames = board.getCards();

        // Get all the clues for each card and add them to the cards
        for (Card c : codenames)
        {
            String[] clues = DatabaseHelper.getCluesForCodename(c.getWord().toLowerCase());
            for (int i = 0; i < clues.length; i++)
            {
                String clue = DatabaseHelper.toCamelCase(clues[i]);
                c.addClue(clue);
            }
        }

        // These variables will represent the graph structure from clues to cards (and vise versa)
        HashMap<String, Clue> clues = new HashMap<>();
        HashMap<String, Card> cards = new HashMap<>();

        // Add Clues to the graph
        for (Card c : codenames)
        {
            cards.put(c.getWord(), c);
            HashSet<String> suggestions = c.getClues();
            for (String s : suggestions)
            {
                Clue clue = clues.getOrDefault(s, new Clue(s));
                clue.addCard(c);
                clues.put(s, clue);
            }
        }

        // Remove clues that only suggest the assassin or civilians
        // NOTE: This must be done here because this information is only
        // available after all clues have been added
        ArrayList<String> badClues = new ArrayList<>();
        for (Clue clue : clues.values())
        {
            if (clue.onlySuggestsAssassinOrCivilian())
            {
                badClues.add(clue.word);

            }
            if (cards.containsKey(clue.word))
            {
                clue.isActiveCodename = true;
            }
        }

        for (String key : badClues)
        {
            clues.remove(key);
        }

        return new SuggestionGraph(clues, cards, Game.RANDOM);
    }
    //endregion
}
