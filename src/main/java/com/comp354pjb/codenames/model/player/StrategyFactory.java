/*
 * StrategyFactory.java
 * Created by: Michael Wilgus
 * Created on: 15/03/19
 *
 * Contributors:
 * Michael Wilgus
 * Christophe Savard
 *
 * Description:
 * This is intended to separate creation of strategies for use with
 * Players out of the Game module. This is desirable because there
 * are many combinations (3*3=9 for now but maybe more later...) of
 * strategies to be used. Keeping this logic here helps future aid
 * ease of future additions/modifications.
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

/**
 * Creates a new Strategy given parameters
 * (See above for full description)
 */
public final class StrategyFactory
{
    /**
     * Strategy type selection
     */
    public enum StrategyType
    {
        SPYMASTER,
        OPERATIVE
    }

    //region Constructors
    /**
     * Prevents class instantiation
     */
    private StrategyFactory() { }
    //endregion

    //region Static methods
    /**
     * Get a Strategy to be used with a Player
     * @param game  The Game the Strategy will be used with
     * @param type  Either "spymaster" or "operative"
     * @param level The strategy class to be created
     * @return A Strategy with the given constraints
     */
    public static Strategy makeStrategy(Game game, StrategyType type, PlayerIntelligence level)
    {
        //Are we a SpyMaster or an Operative?
        switch (type)
        {
            case SPYMASTER:
                // Are we smart or... not?
                switch (level)
                {
                    case SMART:
                        return new SafeSpyMasterAI(game);
                    case MEDIUM:
                        return new RiskySpyMasterAI(game);
                    case DUMB:
                        return new SpyMasterAI(game);
                }
            case OPERATIVE:
                switch (level)
                {
                    case SMART:
                        return new MemoryOperativeAI(game);
                    case MEDIUM:
                        return new ReasonableOperativeAI(game);
                    case DUMB:
                        return new OperativeAI(game);

                    case HUMAN:
                        return new HumanOperative(game);
                }
        }
        return null; // Keep the compiler happy
    }
    //endregion
}
