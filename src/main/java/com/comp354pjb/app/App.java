package com.comp354pjb.app;
import com.comp354pjb.app.Model.DatabaseHelpers;

import com.comp354pjb.app.Controller.ViewController;
import com.comp354pjb.app.Controller.gameController;
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
        DatabaseHelpers.connect();
        String[] words = DatabaseHelpers.getWords();
        gameController game = new gameController(words);

        DatabaseHelpers.connect();

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/board.fxml"));
        Scene scene = new Scene(loader.<Parent>load(), 1280, 800);
        view = loader.getController();
        view.setApp(this);

        //Showing GUI
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
    //endregion
}
