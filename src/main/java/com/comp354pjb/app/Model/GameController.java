package com.comp354pjb.app.Model;

import com.comp354pjb.app.Model.Board.Card;
import com.comp354pjb.app.Model.Board.CardType;
import com.comp354pjb.app.Model.Player.IPlayer;

import java.util.*;

public class GameController
{

    public GameController(String[] words)
    {
        words = shuffleWords(words);
        Card[][] chosenCards = createCards(words); //THIS IS WHAT IS HOLDING THE 25 CARDS

    }

    private Card[][] createCards(String[] words)
    {

        Card[][] cards = new Card[5][5];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                cards[i][j] = new Card(words[(i * 5) + j]);
            }
        }

        System.out.println(cards[0][4].getWord());

        System.out.println("we chilling, cards are shuffled and set,  play ball");

        return cards;
    }

    private String[] shuffleWords(String[] words)
    {
        List<String> listOfWords = Arrays.asList(words);
        Collections.shuffle(listOfWords);

        return listOfWords.toArray(new String[listOfWords.size()]);
    }

    public void decideFirstRoll(IPlayer red, IPlayer blue)
    {
        int flipResult = flipCoin();

        if (flipResult == 1)
        {
            System.out.println("Blue Team will start, which means they must guess 9 cards");
            System.out.println("Red Team will go second, which means they must guess 8 cards");
            enterGameLoop(blue, red);
        }
        else
        {
            System.out.println("Red Team will start, which means they must guess 9 cards");
            System.out.println("Blue Team will go second, which means they must guess 8 cards");
            enterGameLoop(red, blue);
        }
    }

    private int flipCoin()
    {
        Random rand = new Random();
        return rand.nextInt(2);
    }

    private void randomizeCards(Card[][] arr, CardType winner, CardType loser)
    {
        List<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < 25; i++)
        {
            indexList.add(i);
        }
        Collections.shuffle(indexList);
        for (int i = 0; i < 25; i++)
        {
            int current = indexList.get(i);
            current++;
            int row = current / 5;
            int coll = current % 5;
            if (i < 9)
            { arr[row][coll].setType(winner); }
            else if (i < 17)
            { arr[row][coll].setType(winner); }
            else if (i < 23)
            { arr[row][coll].setType(CardType.CIVILIAN); }
            else
            { arr[row][coll].setType(CardType.ASSASSIN); }
        }

    }

    public void setupGame(IPlayer firstTeam, IPlayer secondTeam)
    {
        //randomizeCards(); //Missing parameters right now

    }


    private void enterGameLoop(IPlayer firstTeam, IPlayer secondTeam)
    {
        //in order to do this, we need the hints setup and be able to pull cards from the database, which ben is setting up


        while (true)
        {


            // giveHint(firstTeam);
            // attemptGuess(firstTeam); //TODO
            // checkWinner(firstTeam);


            // giveHint(secondTeam); //TODO
            // attemptGuess(secondTeam); //TODO
            // checkWinner(secondTeam); //TODO

        }


    }


}
