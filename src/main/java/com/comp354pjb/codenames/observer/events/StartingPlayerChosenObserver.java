/*
 * StartingPlayerChosenObserver.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.player.PlayerType;

/**
 * Starting player chosen observer
 */
public interface StartingPlayerChosenObserver
{
    //region Methods
    /**
     * Gets the set starting player type
     * @param type Starting player type
     */
    void setStartingPlayer(PlayerType type);
    //endregion
}
