/*
 * RoundObserver.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

/**
 * Round changed observer
 */
public interface RoundObserver
{
    //region Methods
    /**
     * Gets the new updated game round
     * @param round New game round
     */
    void updateRound(Integer round);
    //endregion
}
