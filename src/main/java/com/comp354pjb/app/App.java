package com.comp354pjb.app;

import com.comp354pjb.app.Model.databaseHelpers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
    public static void main( String[] args )
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        System.out.println( "Hello World!" );
        databaseHelpers.connect();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        Scene scene = new Scene(FXMLLoader.<Parent>load(getClass().getResource("Resources/board.fxml")), 500, 400);

        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
}
