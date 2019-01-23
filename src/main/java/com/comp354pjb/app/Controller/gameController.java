package com.comp354pjb.app.Controller;
import com.comp354pjb.app.Model.*;

import java.util.Random;

public class gameController {

    public gameController(){
        Players red = new Players("red");
        Players blue = new Players("blue");
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

    public void enterGameLoop(Players firstTeam, Players secondTeam){

    }


}
