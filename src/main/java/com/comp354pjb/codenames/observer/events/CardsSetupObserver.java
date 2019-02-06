/*
 * CardsSetupObserver.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.board.Card;

/**
 * Game board setup observer
 */
public interface CardsSetupObserver
{
    //region Methods
    /**
     * Cards changed and need setup
     * @param cards New cards
     */
    void setup(Card[][] cards);
    //endregion
}
