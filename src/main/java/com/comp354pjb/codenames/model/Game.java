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
import com.comp354pjb.codenames.model.player.StrategyFactory.StrategyType;
import com.comp354pjb.codenames.observer.events.ClueGivenEvent;
import com.comp354pjb.codenames.observer.events.PhaseEvent;
import com.comp354pjb.codenames.observer.events.RoundEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Game class, contains most of the information of the game and central hub of the Model
 */
public class Game
{
    //region Constants
    /**
     * Random number generator for the Game
     */
    public static final Random RANDOM = new Random();
    /**
     * Amount of players in the game
     */
    private static final int PLAYER_COUNT = 4;
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
    private final Player[] players = new Player[PLAYER_COUNT];

    //Score keeping members
    private int redCardsRevealed;
    private int blueCardsRevealed;
    private int civilianCardsRevealed;
    //endregion

    //region Constructors
    /**
     * Creates a new Game object and correctly sets up the board and cards, as well as players
     */
    public Game()
    {
        String[] setup = DatabaseHelper.getBoardLayout();
        this.startTeam = PlayerType.parse(setup[0]);
        this.board = new Board(DatabaseHelper.getRandomCodenames(25), setup[1]);
        this.graph = createSuggestionGraph();
    }
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

    private final PlayerType startTeam;
    /**
     * Gets the starting team colour
     */
    public PlayerType getStartTeam()
    {
        return this.startTeam;
    }

    private int guessesLeft;
    /**
     * Gets the number of guesses left given the current clue
     */
    public int getGuessesLeft() { return this.guessesLeft; }

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

    private Clue currentClue;
    /**
     * Current clue given to the Operatives
     */
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

    private final SuggestionGraph graph;
    /**
     * Gets the graph structure that associates clues to words for this game
     * @return A SuggestionGraph that has the current clue to card relationship information for this game
     */
    public SuggestionGraph getSuggestionGraph()
    {
        return graph;
    }
    //endregion

    //region Methods
    /**
     * Sets the starting player for the game and initializes the AIs correctly
     * @param passInt Is passed by the controller to hold an array of PlayerIntelligence chosen by the user.
     *
     * ===================
     * Updated by Christophe Savard 02/04/19
     * Refactored to initialize starting team outside of the method to allow creating players at a later stage
     */
    public void setPlayers(PlayerIntelligence[] passInt)
    {
        //Invert strategies if blue is starting
        if (this.startTeam == PlayerType.BLUE)
        {
            PlayerIntelligence temp = passInt[0];
            passInt[0] = passInt[2];
            passInt[2] = temp;
            temp = passInt[1];
            passInt[1] = passInt[3];
            passInt[3] = temp;
        }

        //Get the second team
        PlayerType second = this.startTeam == PlayerType.RED ? PlayerType.BLUE : PlayerType.RED;

        //Log the starting team
        Commander.log(this.startTeam.niceName() + " Team will start, which means they must guess 9 cards");
        Commander.log(second.niceName() + " Team will go second, which means they must guess 8 cards");

        //Create the players
        this.players[0] = new Player(this.startTeam, StrategyFactory.makeStrategy(this, StrategyType.SPYMASTER,  passInt[0]));
        this.players[1] = new Player(this.startTeam, StrategyFactory.makeStrategy(this, StrategyType.OPERATIVE, passInt[1]));
        this.players[2] = new Player(second, StrategyFactory.makeStrategy(this, StrategyType.SPYMASTER, passInt[2]));
        this.players[3] = new Player(second, StrategyFactory.makeStrategy(this, StrategyType.OPERATIVE, passInt[3]));
    }

    /**
     * Gets the player currently playing it's turn
     * =================
     * Added by Christophe Savard
     * @return The current player
     */
    public Player getCurrentPlayer()
    {
        return this.players[this.playerIndex];
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
        this.getCurrentPlayer().play();

    }

    public void endCurrentTurn()
    {
        this.playerIndex = (this.playerIndex + 1) % PLAYER_COUNT;

        if (this.playerIndex == 0)
        {
            this.onRoundChange.invoke(++this.round);
        }
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
                this.setLoser(getCurrentPlayer().getTeam());
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
        if (getCurrentPlayer().getTeam().getCardType().equals(card.getType()))
        {
            this.guessesLeft--;
        }
        else
        {
            this.guessesLeft = 0;
        }
    }

    /**
     * Reveals the card at the given location
     * =========
     * Added by Christophe Savard 04/04/19
     * @param x X coordinate of the card
     * @param y Y coordinate of the card
     */
    public void revealCard(int x, int y)
    {
        revealCard(this.board.getCard(x, y));
    }

    /**
     * Informs a potential human player of the clicked card's location
     * ==========
     * Added by Christophe Savard 04/04/19
     * @param x X coordinate of the card
     * @param y Y coordinate of the card
     */
    public void informPlayer(int x, int y)
    {
        Strategy currentStrategy = getCurrentPlayer().getStrategy();
        if (currentStrategy instanceof HumanOperative)
        {
            ((HumanOperative)currentStrategy).registerInput(x, y);
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
    private SuggestionGraph createSuggestionGraph()
    {
        // Get all the cards on the board
        ArrayList<Card> codenames = board.getCards();

        // Get all the clues for each card and add them to the cards
        for (Card c : codenames)
        {
            String[] clues = DatabaseHelper.getCluesForCodename(c.getWord().toLowerCase());
            for (String clue : clues)
            {
                String value = DatabaseHelper.toCamelCase(clue);
                c.addClue(value);
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
