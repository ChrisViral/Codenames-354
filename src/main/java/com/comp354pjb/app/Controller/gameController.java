package com.comp354pjb.app.Controller;
import com.comp354pjb.app.Model.*;


import java.util.*;

public class gameController {

    public gameController(String [] words){
        words = shuffleWords(words);
        Cards[][] chosenCards = createCards(words); //THIS IS WHAT IS HOLDING THE 25 CARDS
    }

    public Cards [][] createCards(String [] words){

        Cards[][] cards = new Cards[5][5];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                cards[i][j] = new Cards(words[(i * 5) + j]);
            }
        }

        System.out.println(cards[0][4].getWord());

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
