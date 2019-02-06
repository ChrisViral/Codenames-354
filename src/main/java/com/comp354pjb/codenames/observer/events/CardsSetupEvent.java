/*
 * CardsSetupEvent.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.observer.Subject;

/**
 * Game board setup event
 */
public class CardsSetupEvent extends Subject<CardsSetupObserver, Card[][]>
{
    //region Methods
    /**
     * Notifies the changes to the board/cards
     * @param listener Listener to notify
     * @param cards    New changed cards
     */
    @Override
    protected void update(CardsSetupObserver listener, Card[][] cards)
    {
        listener.setup(cards);
    }
    //endregion
}
