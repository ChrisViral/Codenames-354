package com.comp354pjb.codenames;

import com.comp354pjb.codenames.Model.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application entry point class
 */
public class App extends Application
{
    //region Controllers
    private static Controller controller;
    /**
     * Gets the View controller
     * @return Current Controller
     */
    public static Controller getController()
    {
        return controller;
    }
    //endregion

    //region Initialization
    /**
     * Application entry point
     * @param args App arguments
     */
    public static void main( String[] args )
    {
        //Check for Database connection
        if (!DatabaseHelper.checkConnection())
        {
            System.out.println("Could not connect to the database, aborting...");
            return;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/board.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 1280, 800);
        controller = loader.getController();

        //Showing GUI
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();

        //Setup board
        controller.setup();
    }
    //endregion
}
