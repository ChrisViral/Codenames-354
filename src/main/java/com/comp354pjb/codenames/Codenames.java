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
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Application entry point class
 */
public class Codenames extends Application
{
    //region Constants
    /**
     * Width of the Game Board's window
     */
    private static final int BOARD_WIDTH = 1280;
    /**
     * Height of the Game Board's window
     */
    private static final int BOARD_HEIGHT = 800;
    /**
     * Width of the Start Menu's window
     */
    private static final int START_WIDTH = 560;
    /**
     * Height of the Start Menu's window
     */
    private static final int START_HEIGHT = 400;
    /**
     * Game Board FXML file location
     */
    private static final String BOARD_FXML = "view/board.fxml";
    /**
     * Start Menu FXML file location
     */
    private static final String START_MENU_FXML = "view/startMenu.fxml";
    //endregion

    //region Main
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
    //endregion

    //region Methods
    /**
     * Starts the JavaFX GUI
     * @param board JavaFX Stage
     * @throws IOException FXML file not found
     * <p>
     * Update by Rezza-Zairan
     * ----------------------
     * With refactoring, this function is modified to accommodate the new FXML file
     * ----------------------
     * Christophe Savard
     * Made the new start menu a popup window
     */
    @Override
    public void start(Stage board) throws IOException
    {
        //Create Controller for both stages
        Controller controller = new Controller();

        //Create game board scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BOARD_FXML));
        loader.setController(controller);
        Scene boardScene = new Scene(loader.load(), BOARD_WIDTH, BOARD_HEIGHT);

        //Setup game board stage
        board.setTitle(getClass().getSimpleName());
        board.setScene(boardScene);

        //Creating the popup start menu
        loader = new FXMLLoader(getClass().getResource(START_MENU_FXML));
        loader.setController(controller);
        Scene startMenuScene = new Scene(loader.load(), START_WIDTH, START_HEIGHT);

        //Setup the popup stage
        Stage startMenu = new Stage(StageStyle.UNDECORATED);
        startMenu.initOwner(board);
        startMenu.initModality(Modality.APPLICATION_MODAL);
        startMenu.setTitle("Select the players");
        startMenu.setScene(startMenuScene);

        //Show the board and start menu
        board.show();
        startMenu.show();
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
