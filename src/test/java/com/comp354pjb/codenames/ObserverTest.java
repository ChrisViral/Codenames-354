/*
 * ObserverTest.java
 * Created by: Christophe Savard
 * Created on: 07/04/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.player.Clue;
import com.comp354pjb.codenames.model.player.PlayerIntelligence;
import com.comp354pjb.codenames.observer.events.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Observer firing patterns tests
 * All these tests verify if the Observers are firing at the appropriate and expected moments
 */
public class ObserverTest implements ButtonStateChangedObserver, CardFlippedObserver, ClueGivenObserver, PhaseObserver, RoundObserver, TurnEndObserver
{
    //region Constants
    /**
     * Dummy board layout
     */
    private static final String[] LAYOUT = { "R", "ACCCCCCCBBBBBBBBRRRRRRRRR" };
    //endregion

    //region Fields
    private boolean fired;
    //endregion

    //region Tests
    /**
     * Creates a new Game with randomized info and dumb AIs for testing purposes
     * @return The created Game
     */
    private static Game createGame()
    {
        //Create a randomized game with dumb AIs for testing
        Game game = new Game();
        game.setPlayers(new PlayerIntelligence[] { PlayerIntelligence.DUMB, PlayerIntelligence.DUMB, PlayerIntelligence.DUMB, PlayerIntelligence.DUMB});
        return game;
    }

    /**
     * Tests if the ButtonStateChangedObserver fires at the appropriate moments
     */
    @Test
    public void  buttonStateObserverShouldFire()
    {
        //Create a blank game with a predefined layout to make testing easier
        Game game = new Game(LAYOUT, DatabaseHelper.getRandomCodenames(25));
        //Human AIs to test the observer since they are the only ones calling it
        game.setPlayers(new PlayerIntelligence[] { PlayerIntelligence.DUMB, PlayerIntelligence.HUMAN, PlayerIntelligence.DUMB, PlayerIntelligence.HUMAN});
        //Register tested observer
        game.onButtonStateChanged.register(this);

        //SpyMaster plays turn, observer should not have fired
        game.enterNextGameTurn();
        assertFalse(this.fired);

        //Human Operative starts turn, observer should have fired
        game.enterNextGameTurn();
        assertTrue(this.fired);

        //Human operative turn ends, observer should fire again
        this.fired = false;
        game.informPlayer(1, 0);    //Turns a Civilian card and ends turn
        assertTrue(this.fired);
    }

    /**
     * Tests if the CardFlippedObserver fires at the appropriate moments
     */
    @Test
    public void cardFlipObserverShouldFire()
    {
        //Create a game and register to observer
        Game game = createGame();
        game.getBoard().onFlip.register(this);

        //Play a SpyMaster and Operative turn, which should flip at least one card
        game.enterNextGameTurn();
        game.enterNextGameTurn();

        //Make sure the observer has fired
        assertTrue(this.fired);
    }

    /**
     * Tests if the ClueGivenObserver fires at the appropriate moments
     */
    @Test
    public void clueUpdateObserverShouldFire()
    {
        //Create a game and register to observer
        Game game = createGame();
        game.onClueGiven.register(this);

        //Play a SpyMaster turn, which should give out a clue
        game.enterNextGameTurn();

        //Make sure the observer has fired
        assertTrue(this.fired);
    }

    /**
     * Tests if the PhaseObserver fires at the appropriate moments
     */
    @Test
    public void phaseUpdateObserverShouldFire()
    {
        //Create a game and register to observer
        Game game = createGame();
        game.onPhaseChange.register(this);

        for (int i = 0; i < 2; i++)
        {
            //Play the SpyMaster's turn and verify that the phase change observer has fired
            this.fired = false;
            game.enterNextGameTurn();
            assertTrue(this.fired);

            //Play the operative's first turn and verify that the phase change observer has fired
            this.fired = false;
            game.enterNextGameTurn();
            assertTrue(this.fired);

            //Drain out the operative's turns
            while (game.getGuessesLeft() > 0)
            {
                game.enterNextGameTurn();
            }
        }
    }

    /**
     * Tests if the RoundObserver fires at the appropriate moments
     */
    @Test
    public void roundUpdateObserverShouldFire()
    {
        //Create a game and register to observer
        Game game = createGame();
        game.onRoundChange.register(this);

        //Play the first teams turns for both SpyMaster and Operative
        do
        {
            game.enterNextGameTurn();
        }
        while (game.getGuessesLeft() > 0);

        //Then the second team's to pass to the next round
        do
        {
            game.enterNextGameTurn();
        }
        while (game.getGuessesLeft() > 0);

        //Make sure the observer has fired
        assertTrue(this.fired);
    }

    /**
     * Tests if the TurnEndObserver fires at the appropriate moments
     */
    @Test
    public void  turnUpdateObserverShouldFire()
    {
        //Create a blank game with a predefined layout to make testing easier
        Game game = new Game(LAYOUT, DatabaseHelper.getRandomCodenames(25));
        //Human AIs to test the observer since they are the only ones calling it
        game.setPlayers(new PlayerIntelligence[] { PlayerIntelligence.DUMB, PlayerIntelligence.HUMAN, PlayerIntelligence.DUMB, PlayerIntelligence.HUMAN});
        //Register tested observer
        game.onTurnEnd.register(this);

        //SpyMaster plays turn, observer should fire since it finishes immediately
        game.enterNextGameTurn();
        assertTrue(this.fired);

        //Human Operative starts turn, observer should not fire yet
        this.fired = false;
        game.enterNextGameTurn();
        assertFalse(this.fired);

        //Human operative turn ends, observer should fire again
        game.informPlayer(1, 0);    //Turns a Civilian card and ends turn
        assertTrue(this.fired);

        //Create a new randomized game to test operative AIs
        game = createGame();
        game.onTurnEnd.register(this);

        //Play SpyMaster turn
        game.enterNextGameTurn();

        //Operative turn should fire observer once all guesses are exhausted
        this.fired = false;
        while (game.getGuessesLeft() > 0)
        {
            game.enterNextGameTurn();
        }
        assertTrue(this.fired);
    }
    //endregion

    //region Observer methods
    /**
     * "Next Turn" button state observer
     * @param disabled If the button is disabled or not
     */
    @Override
    public void updateState(boolean disabled)
    {
        this.fired = true;
    }

    /**
     * Card flipped event update method
     * @param card The card being flipped
     */
    @Override
    public void onFlip(Card card)
    {
        this.fired = true;
    }

    /**
     * Gets the new given clue
     * @param clue Clue given
     */
    @Override
    public void updateClue(Clue clue)
    {
        this.fired = true;
    }

    /**
     * Gets the updated game phase
     * @param phase New phase
     */
    @Override
    public void updatePhase(String phase)
    {
        this.fired = true;
    }

    /**
     * Gets the new updated game round
     * @param round New game round
     */
    @Override
    public void updateRound(Integer round)
    {
        this.fired = true;
    }

    /**
     * Turn end event observer
     * @param gameOver If the game is over or not
     */
    @Override
    public void updateTurn(boolean gameOver)
    {
        this.fired = true;
    }
    //endregion
}
