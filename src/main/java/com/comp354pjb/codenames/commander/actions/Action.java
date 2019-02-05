/*
 * Action.java
 * Created by: Christophe Savard
 * Created on: 05/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.commander.actions;

/**
 * Undoable/redo-able action wrapper
 */
public interface Action
{
    //region
    /**
     * Undoes the action
     */
    void undo();

    /**
     * Redoes the action
     */
    void redo();

    /**
     * Informative message to be printed to the log related to the Action
     * @return String info of the action
     */
    String info();
    //endregion
}
