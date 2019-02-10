/*
 * RoundEvent.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.observer.Subject;

/**
 * Round changed event
 */
public class RoundEvent extends Subject<RoundObserver, Integer>
{
    //region Methods
    /**
     * Notifies of the updated game round
     * @param listener Listener to notify
     * @param round    New game round
     */
    @Override
    protected void update(RoundObserver listener, Integer round)
    {
        listener.updateRound(round);
    }
    //endregion
}
