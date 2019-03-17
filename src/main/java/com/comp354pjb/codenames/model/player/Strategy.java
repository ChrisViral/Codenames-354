/*
 * Strategy.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

/**
 * Abstract class for the strategy of Player
 */
abstract public class Strategy {
    // To be effective a strategy will need to know what team is being palyed
    protected PlayerType team;
    // Indicates whether a strategy is done with its turn
    protected boolean finished = false;

    //region Methods
    /**
     * Plays a given player's turn according to rules defined in the method
     */
    abstract void execute();

    public void setTeam(PlayerType team)
    {
        this.team = team;
    }

    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }
    //endregion
}
