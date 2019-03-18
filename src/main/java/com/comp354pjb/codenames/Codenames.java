/*
 * Codenames.java
 * Created by: Christophe Savard
 * Created on: 17/01/19
 *
 * Contributors:
 * Christophe Savard
 * Steven Zanga
 * Benjamin Therrien
 * Rezza-Zairan Zaharin
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application entry point class
 */
public class Codenames extends Application
{
    //region Constants
    /**
     * Width of the App's window
     */
    private static final int WIDTH = 1280;
    /**
     * Height of the App's window
     */
    private static final int HEIGHT = 800;
    /**
     * Board FXML file location
     */
    private static final String START_MENU_FXML = "view/startMenu.fxml";
    private static final String BOARD_FXML = "view/board.fxml";
    //endregion

    //region Initialization

    /**
     * Application entry point
     * @param args Application arguments
     */
    public static void main(String[] args)
    {
        //Check for Database connection
        if (!DatabaseHelper.checkConnection())
        {
            System.out.println("Could not connect to the database, aborting...");
            System.exit(1);
        }

        //Launches JavaFX
        launch(args);
    }

    /**
     * Starts the JavaFX GUI
     * @param stage JavaFX Stage
     * @throws IOException FXML file not found
     *                     <p>
     *                     Update by Rezza-Zairan
     *                     ----------------------
     *                     With refactoring, this function is modified to accommodate the new FXML file
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        //Main controller for the two FXML files below
        Controller controller = new Controller();

        //Loading FXML file
        //Loading GUI for the Start Menu
        FXMLLoader startMenuLoader = new FXMLLoader(getClass().getResource(START_MENU_FXML));
        startMenuLoader.setController(controller);
        Scene startMenuScene = new Scene(startMenuLoader.load(), WIDTH, HEIGHT);

        //Loading GUI for the Game Board
        FXMLLoader boardLoader = new FXMLLoader(getClass().getResource(BOARD_FXML));
        boardLoader.setController(controller);
        Scene boardScene = new Scene(boardLoader.load(), WIDTH, HEIGHT);

        //Showing GUI (user Interface)
        stage.setTitle(getClass().getSimpleName());
        stage.setScene(startMenuScene);
        stage.show();
    }

    /**
     * Terminates the App
     */
    @Override
    public void stop()
    {
        Commander.instance().close();
    }
    //endregion
}
