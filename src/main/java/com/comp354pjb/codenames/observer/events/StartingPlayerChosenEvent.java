/*
 * StartingPlayerChosenEvent.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.player.PlayerType;
import com.comp354pjb.codenames.observer.Subject;

/**
 * Starting player chosen event
 */
public class StartingPlayerChosenEvent extends Subject<StartingPlayerChosenObserver, PlayerType>
{
    //region Methods
    /**
     * Sends a notification indicating that the starting player has been chosen
     * @param listener Listener to notify
     * @param type     New starting player type
     */
    @Override
    protected void update(StartingPlayerChosenObserver listener, PlayerType type)
    {
        listener.setStartingPlayer(type);
    }
    //endregion
}
