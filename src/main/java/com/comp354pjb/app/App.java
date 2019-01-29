package com.comp354pjb.app;
import com.comp354pjb.app.Model.DatabaseHelpers;
import com.comp354pjb.app.Controller.gameController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DatabaseHelpers.connect();
        String[] words = DatabaseHelpers.getWords();
        gameController game = new gameController(words);




//        int count=0;
//        while(count<250)
//        {
//            System.out.println(words[count]);
//            count++;
//        }





    }
}
