/*
 * ClueGivenEvent.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.player.Clue;
import com.comp354pjb.codenames.observer.Subject;

/**
 * Clue announced event
 */
public class ClueGivenEvent extends Subject<ClueGivenObserver, Clue>
{
    //region Methods

    /**
     * Notifies of a new clue being given by a SpyMaster
     * @param listener Listener to notify
     * @param clue     Clue given
     */
    @Override
    protected void update(ClueGivenObserver listener, Clue clue)
    {
        listener.getClue(clue);
    }
    //endregion
}
