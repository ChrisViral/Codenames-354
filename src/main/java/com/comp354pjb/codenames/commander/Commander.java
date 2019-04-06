/*
 * Commander.java
 * Created by: Christophe Savard
 * Created on: 05/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.commander;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Commander class, takes care of Undo/Redo management and logging
 */
public class Commander
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
    private final FileWriter writer;
    private boolean closed;
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
        logMessage("=== Codenames Log ===");
    }
    //endregion

    //region Static methods
    /**
     * Logs a message to the log file
     * @param message Object to log, uses the built in toString() function
     */
    public static void log(Object message)
    {
        log(message.toString());
    }

    /**
     * Logs a message to the log file
     * @param message Message to log
     */
    public static void log(String message)
    {
        INSTANCE.logMessage(message);
    }
    //endregion

    //region Methods
    public void logMessage(String message)
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
     * Closes all handles to files held by this object
     */
    public void close()
    {
        if (!this.closed)
        {
            try
            {
                logMessage("=== Terminating Application ===");
                this.writer.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            this.closed = true;
        }
    }

    /**
     * Finalizes this object for garbage collection
     */
    @Override
    public void finalize()
    {
        close();
    }
    //endregion
}
