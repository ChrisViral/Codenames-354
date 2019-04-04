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
abstract public class Strategy
{
    //region Properties
    /**
     * The team being played
     * To be effective a strategy will need to know what team is being palyed
     */
    protected PlayerType team;
    /**
     * Mutator for team
     * @param team, Either red or blue
     */
    public void setTeam(PlayerType team)
    {
        this.team = team;
    }
    //endregion

    //region Abstract methods
    /**
     * Plays a given player's turn according to rules defined in the method
     * Modified by Michael Wilgus (Rename to clearly indicate that this conforms to Strategy Pattern)
     */
    public abstract void execute();
    //endregion
}
