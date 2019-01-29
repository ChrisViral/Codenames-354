package com.comp354pjb.app;
import com.comp354pjb.app.Model.databaseHelpers;
import com.comp354pjb.app.Controller.gameController;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

        try{
            Class.forName("org.sqlite.JDBC");
        }catch(Exception e){
            System.out.println(e);
        }

        System.out.println( "Hello World!" );
        databaseHelpers.connect();
    @Override
    public void start(Stage stage)
    {

    }
}
