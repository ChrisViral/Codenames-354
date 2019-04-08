/*
 * ButtonStateChangedObserver.java
 * Created by: Christophe Savard
 * Created on: 04/04/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

/**
 * Button state observer
 */
public interface ButtonStateChangedObserver
{
    //region Methods
    /**
     * "Next Turn" button state observer
     * @param disabled If the button is disabled or not
     */
    void updateState(boolean disabled);
    //endregion
}
