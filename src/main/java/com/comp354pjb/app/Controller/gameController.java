package com.comp354pjb.app.Controller;
import com.comp354pjb.app.Model.*;

import java.util.Random;

public class gameController {

    public gameController(){
        Players red = new Players("Red");
        Players blue = new Players("Blue");
        Cards gameboard [] = new Cards[25];
        //initializeCards(); //TODO, cards need to be pulled from DB and initialized
        decideFirstRoll(red, blue);
    }

    public void decideFirstRoll(Players red, Players blue){
        int flipResult = flipCoin();

        if (flipResult == 1) {
            System.out.println("Blue Team will start, which means they must guess 9 cards");
            System.out.println("Red Team will go second, which means they must guess 8 cards");
            enterGameLoop(blue, red);
        } else {
            System.out.println("Red Team will start, which means they must guess 9 cards");
            System.out.println("Blue Team will go second, which means they must guess 8 cards");
            enterGameLoop(red, blue);
        }
    }

    public int flipCoin(){
        Random rand = new Random();
        return rand.nextInt(2);
    }

    public void giveHint(Players team){
        System.out.println(team.type + " will now give a hint...");
        System.out.print("Hint: ");
    }

    public void enterGameLoop(Players firstTeam, Players secondTeam){
        //in order to do this, we need the hints setup and be able to pull cards from the database, which ben is setting up


        while(true) {


            giveHint(firstTeam);
            // attemptGuess(firstTeam); //TODO
            // checkWinner(firstTeam);


            giveHint(secondTeam); //TODO
            // attemptGuess(secondTeam); //TODO
            // checkWinner(secondTeam); //TODO

        }





    }


}
