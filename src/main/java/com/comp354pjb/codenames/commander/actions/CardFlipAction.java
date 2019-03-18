/*
 * CardFlipAction.java
 * Created by: Christophe Savard
 * Created on: 05/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.commander.actions;

import com.comp354pjb.codenames.Controller;
import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.board.Card;

/**
 * Action enabling Undo/Redo on card flip events
 */
public class CardFlipAction implements Action
{
    //region Fields
    private final Controller controller;
    private final Card card;
    //endregion

    //region Constructors

    /**
     * Creates a new CardFlipAction to enable undo and redo
     * @param controller Controller on which to apply the action
     * @param card       Card to undo or redo
     */
    public CardFlipAction(Controller controller, Card card)
    {
        this.controller = controller;
        this.card = card;
    }
    //endregion

    //region Methods

    /**
     * Undoes the card flip
     */
    @Override
    public void undo()
    {
        card.setRevealed(false);
        this.controller.unFlip(card);
    }

    /**
     * Redoes the card flip
     */
    @Override
    public void redo()
    {
        card.setRevealed(true);
        this.controller.onFlip(card);
    }

    /**
     * Informative message to be printed to the log related to the Card being flipped
     * @return String info of the action
     */
    @Override
    public String info()
    {
        return String.format("%s card \"%s\" flipped at (%d, %d)",
                DatabaseHelper.toCamelCase(card.getType().name()),
                card.getWord(),
                card.getX(),
                card.getY());
    }
    //endregion
}
