/*
 * ClueGivenObserver.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.player.Clue;

/**
 * Clue announced observer
 */
public interface ClueGivenObserver
{
    //region Methods
    /**
     * Gets the new given clue
     * @param clue Clue given
     */
    void getClue(Clue clue);
    //endregion
}
