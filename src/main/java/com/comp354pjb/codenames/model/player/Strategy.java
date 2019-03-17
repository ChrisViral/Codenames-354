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
 * (See above for full description)
 */
abstract public class Strategy {

    /**
     * The team being played
     * To be effective a strategy will need to know what team is being palyed
     */
    protected PlayerType team;

    /**
     * Indicates whether a strategy is done with its turn
     */
    // Added by Michael Wilgus
    protected boolean finished = false;

    //region Methods
    /**
     * Plays a given player's turn according to rules defined in the method
     * Modified by Michael Wilgus (Rename to clearly indicate that this conforms to Strategy Pattern)
     */
    abstract void execute();

    /**
     * Mutator for team
     * @param team, Either red or blue
     */
    public void setTeam(PlayerType team)
    {
        this.team = team;
    }

    // Added by Michael Wilgus

    /**
     * Whether or not the strategy is finished running
     * @return true, false, True if it is done, false otherwise
     */
    public boolean isFinished() { return finished; }

    /**
     * Mutator for isFinished
     * @param finished Whether or not the strategy is finished running
     */
    public void setFinished(boolean finished) { this.finished = finished; }
    //endregion
}
