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

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

/**
 * Commander class, takes care of Undo/Redo management and logging
 */
public class Commander implements CardFlippedObserver
{
    //region Constants
    /**
     * Timestamp formatter
     */
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * OS specific line separator
     */
    private static final String NEWLINE = System.lineSeparator();
    //endregion

    //region Instance
    private final static Commander INSTANCE = new Commander();
    /**
     * Current instance of the Commander Singleton
     * @return The current instance
     */
    public static Commander instance()
    {
        return INSTANCE;
    }
    //endregion

    //region Fields
    private Controller controller;
    private FileWriter writer;
    private final Stack<Action> undoStack = new Stack<>();
    private final Stack<Action> redoStack = new Stack<>();
    //endregion

    //region Constructors
    /**
     * Creates a new Commander, limits instantiation to Singleton
     */
    private Commander()
    {
        //Create the Log file
        FileWriter writer = null;
        try
        {
            writer = new FileWriter("log.txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.writer = writer;
        log("=== Codenames Log ===");
    }
    //endregion

    //region Methods
    /**
     * Sets up the Commander to work with the given Controller and Game
     * @param controller Controller to use
     * @param game       Game to listen to
     */
    public void setup(Controller controller, Game game)
    {
        //Set the controller
        this.controller = controller;

        //Listen to all events
        game.getBoard().onFlip.register(this);
    }

    /**
     * Undoes the last action
     */
    public void undo()
    {
        Action action = this.undoStack.pop();
        action.undo();
        this.redoStack.push(action);
        checkButtonStates();
        log("Undo " + action.info());
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
        log("Redo " + action.info());
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
        Action action = new CardFlipAction(this.controller, card);
        pushNewAction(action);
        log(action.info());
    }

    public void log(String message)
    {
        //Get time and format message, then print to standard output
        String logMessage = String.format("[%s] %s", LocalDateTime.now().format(FORMAT), message);
        System.out.println(logMessage);

        //Print to log file
        try
        {
            writer.write(logMessage + NEWLINE);
            writer.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Closes the handle to the file and writes the final messages
     */
    @Override
    public void finalize()
    {
        try
        {
            log("=== Terminating Application ===");
            this.writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //endregion
}
