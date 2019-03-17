package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

public class StrategyFactory
{
    public static Strategy makeStrategy(String type, Game game, PlayerIntelligence level)
    {
        switch(type)
        {
            case "spymaster":
                switch(level)
                {
                    case SMART:
                        return new SafeSpyMasterAI(game);
                    case MEDIUM:
                        return new RiskySpyMasterAI(game);
                    case DUMB:
                        return new SpyMasterAI(game);
                }
            case "operative":
                switch(level)
                {
                    case SMART:
                        return new MemoryOperativeAI(game);
                    case MEDIUM:
                        return new ReasonableOperativeAI(game);
                    case DUMB:
                        return new OperativeAI(game);
                }
        }
        return new OperativeAI(game); // Keep the compiler happy
    }
}
