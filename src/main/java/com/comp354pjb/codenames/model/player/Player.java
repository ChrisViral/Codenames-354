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
    protected final PlayerType team;
    public final String teamName;
    private boolean finished = false;
    //endregion

    //region Properties
    protected final Strategy strategy;
    /**
     * Gets the strategy associated to this player
     * @return The currently used Strategy
     */
    public Strategy getStrategy()
    {
        return this.strategy;
    }
    //endregion

    //region Constructors
    /**
     * Creates a new player for a Codenames game
     * @param team     Team this player is on
     * @param strategy Strategy and type of player
     *
     * Update by Rezza-Zairan
     * ----------------------
     */
    public Player(PlayerType team, Strategy strategy)
    {
        this.team = team;
        this.strategy = strategy;
        this.strategy.setTeam(team);
        this.teamName = this.team.niceName();
    }
    //endregion

    //region Methods
    /**
     * Plays on of the players turns according to it's type and strategy
     */
    public void play()
    {
        this.strategy.execute();
        this.finished = this.strategy.isFinished();
    }

    public PlayerType getTeam()
    {
        return team;
    }

    public boolean isFinished() { return this.strategy.isFinished(); }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
        this.strategy.setFinished(finished);
    }

    //endregion
}
