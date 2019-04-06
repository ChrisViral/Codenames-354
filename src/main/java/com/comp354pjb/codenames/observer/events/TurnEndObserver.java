/*
 * TurnEndObserver.java
 * Created by: Christophe Savard
 * Created on: 06/04/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

/**
 * Game turn end observer
 */
public interface TurnEndObserver
{
    //region Methods
    /**
     * Turn end event observer
     * @param gameOver If the game is over or not
     */
    void updateTurn(boolean gameOver);
    //endregion
}
