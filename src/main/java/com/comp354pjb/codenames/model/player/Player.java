/*
 * PLayer.java
 * Created by: Christophe Savard
 * Created on: 09/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

/**
 * Game player object
 */
public class Player
{
    //region Fields
    protected final Game game;
    protected final PlayerType team;
    public final String teamName;
    //endregion

    //region Properties
    protected final IPlayer strategy;
    /**
     * Gets the strategy associated to this player
     * @return The currently used Strategy
     */
    public IPlayer getStrategy()
    {
        return this.strategy;
    }
    //endregion

    //region Constructors
    /**
     * Creates a new player for a Codenames game
     * @param game     Game this player evolves within
     * @param team     Team this player is on
     * @param strategy Strategy and type of player
     */
    public Player(Game game, PlayerType team, IPlayer strategy)
    {
        this.game = game;
        this.team = team;
        this.strategy = strategy;
        this.teamName = this.team.niceName();
    }
    //endregion

    //region Methods
    /**
     * Plays on of the players turns according to it's type and strategy
     */
    public void play()
    {
        this.strategy.playTurn(this);
    }

    public PlayerType getTeam()
    {
        return team;
    }
    //endregion
}
