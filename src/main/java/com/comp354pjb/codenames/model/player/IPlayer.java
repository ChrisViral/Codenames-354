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
public interface IPlayer
{
    //region Methods
    /**
     * Plays a given player's turn
     * @param player Player who's using this strategy
     */
    void playTurn(Player player);
    //endregion
}
