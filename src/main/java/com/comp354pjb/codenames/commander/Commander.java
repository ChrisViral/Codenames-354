/*
 * Commander.java
 * Created by: Christophe Savard
 * Created on: 05/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.commander;

import com.comp354pjb.codenames.Controller;
import com.comp354pjb.codenames.commander.actions.Action;
import com.comp354pjb.codenames.commander.actions.CardFlipAction;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.observer.events.CardFlippedObserver;

import java.util.Stack;

/**
 * Commander class, takes care of Undo/Redo management and logging
 */
public class Commander implements CardFlippedObserver
{
    //region Fields
    private final Controller controller;
    private final Stack<Action> undoStack = new Stack<>();
    private final Stack<Action> redoStack = new Stack<>();
    //endregion

    //region Constructors
    /**
     * Creates a new Commander listening to the actions happening in the given Game
     * @param controller The Controller object associated to the Game and View
     * @param game       Game to listen to
     */
    public Commander(Controller controller, Game game)
    {
        //Set the controller
        this.controller = controller;

        //Listen to all events
        game.getBoard().onFlip.register(this);
    }
    //endregion

    //region Methods
    /**
     * Undoes the last action
     */
    public void undo()
    {
        Action action = this.undoStack.pop();
        action.undo();
        this.redoStack.push(action);
        checkButtonStates();
    }

    /**
     * Redoes the last undid action
     */
    public void redo()
    {
        Action action = this.redoStack.pop();
        action.redo();
        this.undoStack.push(action);
        checkButtonStates();
    }

    /**
     * Pushes a new Action to the Undo stack and does all the necessary adjustments
     * @param action New action being pushed
     */
    private void pushNewAction(Action action)
    {
        //Push the new action to the Undo stack
        this.undoStack.push(action);
        //Clear the redo stack
        if (!this.redoStack.empty())
        {
            this.redoStack.clear();
        }
        //Check the buttons
        checkButtonStates();
    }

    /**
     * Checks the Undo and Redo stacks to see if the buttons on the view need to be active or not
     */
    private void checkButtonStates()
    {
        this.controller.setUndoDisabled(this.undoStack.empty());
        this.controller.setRedoDisabled(this.redoStack.isEmpty());
    }

    /**
     * Card flip event listener
     * @param card The card being flipped
     */
    @Override
    public void onFlip(Card card)
    {
        pushNewAction(new CardFlipAction(this.controller, card));
    }
    //endregion
}
