/*
 * TurnEndEvent.java
 * Created by: Christophe Savard
 * Created on: 06/04/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.observer.Subject;

/**
 * Game turn end event
 */
public class TurnEndEvent extends Subject<TurnEndObserver, Boolean>
{
    //region Methods
    /**
     * Notifies a single listener of the changed data
     * Inheriting subjects should override this class and make it interact with the Observer it is using
     * @param listener Listener to notify
     * @param gameOver If the game is over or not
     */
    @Override
    protected void update(TurnEndObserver listener, Boolean gameOver)
    {
        listener.updateTurn(gameOver);
    }
    //endregion
}
