/*
 * PLayer.java
 * Created by: Christophe Savard
 * Created on: 09/02/19
 *
 * Contributors:
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

/**
 * Represents a player in the Codenames game.
 * Players can use different Strategies to either give or guess
 * clues/cards. This object assumes no particular strategy but can
 * be assigned one on creation.
 */
public class Player
{
    //region Properties
    protected final PlayerType team;
    /**
     * Player's team
     */
    public PlayerType getTeam()
    {
        return team;
    }

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
     *                 <p>
     *                 Update by Rezza-Zairan
     *                 Modified by Michael Wilgus (decouple from game this is now the responsibility of a Strategy)
     *                 ----------------------
     */
    public Player(PlayerType team, Strategy strategy)
    {
        this.team = team;
        this.strategy = strategy;
    }
    //endregion

    //region Methods
    /**
     * Plays on of the players turns according to it's type and strategy
     */
    public void play()
    {
        this.strategy.execute();
    }
    //endregion
}
