/*
 * PLayer.java
 * Created by: Christophe Savard
 * Created on: 09/02/19
 *
 * Contributors:
 * Christophe Savard
 * Michael Wilgus
 *
 * Description:
 * Represents a player in the Codenmaes game.
 * Players can use different Strategies to either give or guess
 * clues/cards. This object assumes no particular strategy but can
 * be assigned one on creation.
 */

package com.comp354pjb.codenames.model.player;

/**
 * Game player object
 */
public class Player
{
    //region Fields
    protected final PlayerType team;
    public final String teamName;

    // Added by Michael wilgus
    // Keep track of when a player is done or cannot play any longer for a given turn
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
     * Modified by Michael Wilgus (decouple from game this is now the responsibility of a Strategy)
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

        // Added by Michael Wilgus
        // Consult our strategy to know if we are done playing
        this.finished = this.strategy.isFinished();
    }

    public PlayerType getTeam()
    {
        return team;
    }

    // Added by Michael Wilgus
    public boolean isFinished() { return this.strategy.isFinished(); }

    // Added by Michael Wilgus
    public void setFinished(boolean finished)
    {
        this.finished = finished;
        this.strategy.setFinished(finished);
    }

    //endregion
}
