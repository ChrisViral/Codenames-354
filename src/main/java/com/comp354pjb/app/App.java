package com.comp354pjb.app;

import com.comp354pjb.app.Model.databaseHelpers;
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
    /**
     * Application entry point
     * @param args App arguments
     */
    public static void main( String[] args )
    {
        //Connect to SQLite database
        databaseHelpers.connect();

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
        Scene scene = new Scene(FXMLLoader.<Parent>load(getClass().getResource("Resources/board.fxml")), 500, 400);

        //Showing GUI
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
}
