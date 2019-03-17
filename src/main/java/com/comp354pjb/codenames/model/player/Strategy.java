/*
 * Strategy.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 * Michael Wilgus
 *
 * Description:
 * Represents a strategy that a Player might take to win the game.
 * Contains a single action method that is intended to be Overridden.
 * This method will implement the logic for giving/guessing clues/cards.
 * Separation from actual players means that new strategies can be added
 * without modifying the Player class directly.
 */

package com.comp354pjb.codenames.model.player;

/**
 * Abstract class for the strategy of Player
 */
abstract public class Strategy {
    // To be effective a strategy will need to know what team is being palyed
    protected PlayerType team;

    // Added by Michael Wilgus
    // Indicates whether a strategy is done with its turn
    protected boolean finished = false;

    //region Methods
    /**
     * Plays a given player's turn according to rules defined in the method
     * Modified by Michael Wilgus (Rename to clearly indicate that this conforms to Strategy Pattern)
     */
    abstract void execute();

    public void setTeam(PlayerType team)
    {
        this.team = team;
    }

    // Added by Michael Wilgus
    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }
    //endregion
}
