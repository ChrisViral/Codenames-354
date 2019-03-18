/*
 * CardFlippedEvent.java
 * Created by: Christophe Savard
 * Created on: 04/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.observer.Subject;

/**
 * Card flipped Event subject implementation
 */
public class CardFlippedEvent extends Subject<CardFlippedObserver, Card>
{
    //region Methods

    /**
     * Updates the Card flip event listener with the card being flipped
     * @param listener Listener to update
     * @param card     Card being flipped
     */
    @Override
    protected void update(CardFlippedObserver listener, Card card)
    {
        listener.onFlip(card);
    }
    //endregion
}
