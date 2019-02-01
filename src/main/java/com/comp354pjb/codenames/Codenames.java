/*
 * Codenames.java
 * Created by: Christophe Savard
 * Created on: 17/01/19
 *
 * Contributors:
 * Christophe Savard
 * Steven Zanga
 * Benjamin Therrien
 */

package com.comp354pjb.codenames;

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
    public static final int WIDTH = 1280;
    /**
     * Height of the App's window
     */
    public static final int HEIGHT = 800;
    /**
     * Board FXML file location
     */
    private static final String BOARD_FXML = "view/board.fxml";
    //endregion

    //region Initialization
    /**
     * Application entry point
     * @param args Codenames arguments
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
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        //Loading FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BOARD_FXML));
        Scene scene = new Scene(loader.<Parent>load(), WIDTH, HEIGHT);

        //Showing GUI
        stage.setTitle(getClass().getSimpleName());
        stage.setScene(scene);
        stage.show();

        //Setup board
        ((Controller)loader.getController()).setup();
    }
    //endregion
}
