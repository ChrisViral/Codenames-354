/*
 * IPLayer.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

/**
 * Interface for the type of Player
 */
public interface Strategy
{
    //region Methods
    /**
     * Plays a given player's turn
     * @param player Player who's using this strategy
     */
    void execute(Player player);
    //endregion
}
