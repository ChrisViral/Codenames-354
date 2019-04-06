/*
 * MemoryOperativeAITest.java
 * Created by: Shereece A.A. Victor
 * Created on: 03/02/19
 *
 * Contributors:
 * Shereece A.A. Victor
 *
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.*;

import com.comp354pjb.codenames.model.player.*;
import com.comp354pjb.codenames.model.player.StrategyFactory.StrategyType;
import org.junit.Test;


import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;


public class MemoryOperativeAITest {
    public Game game = new Game();
    public PlayerType pt = PlayerType.BLUE;
    Player player = new Player(pt, StrategyFactory.makeStrategy(game, StrategyType.OPERATIVE, PlayerIntelligence.DUMB, pt));

    private boolean useExtraTurn = false;
    MemoryOperativeAI memory = new MemoryOperativeAI(game, pt);
//TODO Write Test to see if Extra Turn becomes true when all cards aren't guessed by MemoryOpAI
   /* @Test
    public void MissingCardShouldTriggerExtraTurn()
    {
        Game game = new Game();
        MemoryOperativeAI memory = new MemoryOperativeAI(game);

        assertTrue(memory.isUseExtraTurn());
    }
    */
   /*
    @Test
    public void exec(){
        game.setPhase("Test.");
        game.setCurrentClue(new Clue("testing"));
        Clue clue = game.getCurrentClue();
        memory.setPreviousClue("test");
        useExtraTurn=true;
        memory.execute();
        assertTrue(memory.getPreviousClue()==null);
    }
    */
}
