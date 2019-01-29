package com.comp354pjb.app.Controller;
import com.comp354pjb.app.Model.*;


import java.util.*;

public class gameController {

    public gameController(String [] words){
        words = shuffleWords(words);
        createCards(words);
    }

    public Cards [][] createCards(String [] words){
        Cards Cards0 = new Cards(words[0]);
        Cards Cards1 = new Cards(words[1]);
        Cards Cards2 = new Cards(words[2]);
        Cards Cards3 = new Cards(words[3]);
        Cards Cards4 = new Cards(words[4]);
        Cards Cards5 = new Cards(words[5]);
        Cards Cards6 = new Cards(words[6]);
        Cards Cards7 = new Cards(words[7]);
        Cards Cards8 = new Cards(words[8]);
        Cards Cards9 = new Cards(words[9]);
        Cards Cards10 = new Cards(words[10]);
        Cards Cards11 = new Cards(words[11]);
        Cards Cards12 = new Cards(words[12]);
        Cards Cards13 = new Cards(words[13]);
        Cards Cards14 = new Cards(words[14]);
        Cards Cards15 = new Cards(words[15]);
        Cards Cards16 = new Cards(words[16]);
        Cards Cards17 = new Cards(words[17]);
        Cards Cards18 = new Cards(words[18]);
        Cards Cards19 = new Cards(words[19]);
        Cards Cards20 = new Cards(words[20]);
        Cards Cards21 = new Cards(words[21]);
        Cards Cards22 = new Cards(words[22]);
        Cards Cards23 = new Cards(words[23]);
        Cards Cards24 = new Cards(words[24]);

        Cards [][] cards = new Cards[5][5];

        cards [0][0] = Cards0;
        cards [0][1] = Cards1;
        cards [0][2] = Cards2;
        cards [0][3] = Cards3;
        cards [0][4] = Cards4;
        cards [1][0] = Cards5;
        cards [1][1] = Cards6;
        cards [1][2] = Cards7;
        cards [1][3] = Cards8;
        cards [1][4] = Cards9;
        cards [2][0] = Cards10;
        cards [2][1] = Cards11;
        cards [2][2] = Cards12;
        cards [2][3] = Cards13;
        cards [2][4] = Cards14;
        cards [3][0] = Cards15;
        cards [3][1] = Cards16;
        cards [3][2] = Cards17;
        cards [3][3] = Cards18;
        cards [3][4] = Cards19;
        cards [4][0] = Cards20;
        cards [4][1] = Cards21;
        cards [4][2] = Cards22;
        cards [4][3] = Cards23;
        cards [4][4] = Cards24;

        System.out.println("we chilling, cards are shuffled and set,  play ball");

        return cards;
    }

    public String [] shuffleWords(String [] words){

        List<String> listOfWords = Arrays.asList(words);
        Collections.shuffle(listOfWords);

        return listOfWords.toArray(new String [listOfWords.size()]);
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
        //in order to do this, we need the hints setup and be able to pull cards from the database, which ben is setting up


        while(true) {


            // giveHint(firstTeam);
            // attemptGuess(firstTeam); //TODO
            // checkWinner(firstTeam);


            // giveHint(secondTeam); //TODO
            // attemptGuess(secondTeam); //TODO
            // checkWinner(secondTeam); //TODO

        }





    }


}
