package com.comp354pjb.app;

import com.comp354pjb.app.Controller.ViewController;
import com.comp354pjb.app.Controller.gameController;
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
    //region Controllers
    private static ViewController view;
    /**
     * Gets the View controller
     * @return Current ViewController
     */
    public static ViewController getView()
    {
        return view;
    }

    private static gameController game;
    /**
     * Gets the Game controller
     * @return Current GameController
     */
    public static gameController getGame()
    {
        return game;
    }
    //endregion

    //region Initialization
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

        //Create Game Controller
        game = new gameController();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/board.fxml"));
        view = loader.getController();
        Scene scene = new Scene(loader.<Parent>load(), 500, 400);

        //Showing GUI
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
    //endregion
}
