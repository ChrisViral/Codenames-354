/*
 * StrategyFactoryTest.java
 * Created by: Shereece A. A. Victor
 * Created on: 03/02/19
 *
 * Contributors:
 * Shereece A. A. Victor
 *
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.Game;

import com.comp354pjb.codenames.model.player.*;
import com.comp354pjb.codenames.model.player.StrategyFactory.StrategyType;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrategyFactoryTest {

    // Picking the right combination of Player and intelligence should call the right strategy
    public Game game = new Game();
    public PlayerType pt = PlayerType.BLUE;
    public PlayerType pt1 = PlayerType.RED;

    @Test
    public void PickingDumbOperativeShouldCreateOpAI(){

        Player player = new Player(pt,StrategyFactory.makeStrategy(game, StrategyType.OPERATIVE, PlayerIntelligence.DUMB, pt));
        assertTrue(player.getStrategy() instanceof OperativeAI);
    }

    @Test
    public void PickingMediumOperativeShouldCreateReasonableOpAI(){

        Player player = new Player(pt1,StrategyFactory.makeStrategy(game, StrategyType.OPERATIVE, PlayerIntelligence.MEDIUM, pt1));
        assertTrue(player.getStrategy() instanceof ReasonableOperativeAI);
    }

    @Test
    public void PickingSmartOperativeShouldCreateMemoryOpAI(){

        Player player = new Player(pt,StrategyFactory.makeStrategy(game, StrategyType.OPERATIVE, PlayerIntelligence.SMART, pt));
        assertTrue(player.getStrategy() instanceof MemoryOperativeAI);
    }

    @Test
    public void PickingHumanOperativeShouldCreateHumanOp(){
        Player player = new Player(pt1,StrategyFactory.makeStrategy(game, StrategyType.OPERATIVE, PlayerIntelligence.HUMAN, pt1));
        assertTrue(player.getStrategy() instanceof HumanOperative);
    }

    @Test
    public void PickingDumbSpyMasterShouldCreateSpyAI(){
        Player player = new Player(pt,StrategyFactory.makeStrategy(game, StrategyType.SPYMASTER, PlayerIntelligence.DUMB, pt));
        assertTrue(player.getStrategy() instanceof SpyMasterAI);
    }

    @Test
    public void PickingMediumSpyMasterShouldCreateRiskySpyAI(){
        Player player = new Player(pt1,StrategyFactory.makeStrategy(game, StrategyType.SPYMASTER, PlayerIntelligence.MEDIUM, pt1));
        assertTrue(player.getStrategy() instanceof RiskySpyMasterAI);
    }

    @Test
    public void PickingSmartSpyMasterShouldCreateSafeSpyMasterAI(){
        Player player = new Player(pt,StrategyFactory.makeStrategy(game, StrategyType.SPYMASTER, PlayerIntelligence.SMART, pt));
        assertTrue(player.getStrategy() instanceof SafeSpyMasterAI);
    }
}
