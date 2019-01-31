package com.comp354pjb.codenames.Model;

import com.comp354pjb.codenames.Controller;
import com.comp354pjb.codenames.Model.Board.Board;
import com.comp354pjb.codenames.Model.Player.IPlayer;
import com.comp354pjb.codenames.Model.Player.PlayerType;

import java.util.*;

public class Game
{
    private static final Random random = new Random();

    private final Controller controller;
    private Board board;
    public Board getBoard()
    {
        return this.board;
    }

    public Game(Controller controller)
    {
        this.controller = controller;
        String[] words = DatabaseHelper.getWords();
        words = shuffleWords(words);
        this.board = new Board(words, Game.chooseStartingPlayer());
    }

    private static PlayerType chooseStartingPlayer()
    {
        return random.nextBoolean() ? PlayerType.RED : PlayerType.BLUE;
    }

    private String[] shuffleWords(String[] words)
    {
        List<String> listOfWords = Arrays.asList(words);
        Collections.shuffle(listOfWords);

        return listOfWords.toArray(new String[listOfWords.size()]);
    }

    public void decideFirstRoll(IPlayer red, IPlayer blue)
    {
        boolean flipResult = random.nextBoolean();

        if (flipResult)
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
