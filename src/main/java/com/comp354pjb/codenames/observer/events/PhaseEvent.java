/*
 * PhaseEvent.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.observer.Subject;

/**
 * Phase changed event
 */
public class PhaseEvent extends Subject<PhaseObserver, String>
{
    //region Methods
    /**
     * Notifies of a phase change
     * @param listener Listener to notify
     * @param phase    New game phase
     */
    @Override
    protected void update(PhaseObserver listener, String phase)
    {
        listener.updatePhase(phase);
    }
    //endregion
}
