package com.comp354pjb.app;
import com.comp354pjb.app.Model.databaseHelpers;
import com.comp354pjb.app.Controller.gameController;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {

        try{
            Class.forName("org.sqlite.JDBC");
        }catch(Exception e){
            System.out.println(e);
        }

        System.out.println( "Hello World!" );
        databaseHelpers.connect();


        //at this point we need our pull method from database to retrieve all cards

    }
}
