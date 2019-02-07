/*
 * PhaseObserver.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

/**
 * Phase changed observer
 */
public interface PhaseObserver
{
    //region Methods
    /**
     * Gets the updated game phase
     * @param phase New phase
     */
    void updatePhase(String phase);
    //endregion
}
