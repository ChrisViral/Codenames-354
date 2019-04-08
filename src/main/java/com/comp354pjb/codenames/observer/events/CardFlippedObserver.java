/*
 * CardFlippedObserver.java
 * Created by: Christophe Savard
 * Created on: 04/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.board.Card;

/**
 * Card flipped event observer
 */
public interface CardFlippedObserver
{
    //region Methods
    /**
     * Card flipped event update method
     * @param card The card being flipped
     */
    void onFlip(Card card);
    //endregion
}
