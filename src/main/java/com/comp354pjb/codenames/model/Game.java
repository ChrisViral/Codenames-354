/*
 * Game.java
 * Created by: Steven Zanga
 * Created on: 23/01/19
 *
 * Contributors:
 * Steven Zanga
 * Benjamin Therrien
 * Christophe Savard
 */

package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.Controller;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.player.IPlayer;
import com.comp354pjb.codenames.model.player.PlayerType;

import java.util.*;

public class Game
{
    // creating random object.
    private static final Random RANDOM = new Random();


    private Board board;
    public Board getBoard()
    {
        return this.board;
    }

    /**
     * Default constructor, selection of 25 words, sets main controller and creates a new board with the selected 25 cards
     */
    public Game()
    {
        String[] words = DatabaseHelper.selectWords(25);
        this.board = new Board(words, chooseStartingPlayer());
    }

    /**
     * decide what team starts first, red or blue
     * @return returns whether the starting player type will be red or blue.
     */
    private static PlayerType chooseStartingPlayer()
    {
        return RANDOM.nextBoolean() ? PlayerType.RED : PlayerType.BLUE;
    }

    public void decideFirstRoll(IPlayer red, IPlayer blue)
    {
        if (RANDOM.nextBoolean())
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
