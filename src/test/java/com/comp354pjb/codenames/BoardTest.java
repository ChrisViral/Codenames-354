/*
 * BoardTest.java
 * Created by: Michael Wilgus
 * Created on: 03/02/19
 *
 * Contributors:
 * Michael Wilgus
 * Christophe Savard
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.board.*;

import org.junit.Test;


import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;

public class BoardTest
{
    /**
     * Test to see if the board contains the correct amount of each card type
     *
     * Written by: Shereece  A. A. Victor
     */
    @Test
    public void boardShouldContainEachCardType(){
        Board board = new Board(DatabaseHelper.getRandomCodenames(25), getRandomLayout());
        int assassin=0, spy=0, red=0, blue=0, civil=0;


        for(int i = 0; i < 5; i++) {

            for(int j = 0; j < 5; j++) {

                if(board.getCard(i, j).getType()==CardType.ASSASSIN){
                    assassin++;
                }
                if(board.getCard(i, j).getType()==CardType.RED){
                    spy++;
                    red++;
                }
                if(board.getCard(i, j).getType()==CardType.BLUE){
                    blue++;
                    spy++;
                }
                else if(board.getCard(i, j).getType()==CardType.CIVILIAN){
                    civil++;
                }

            }
        }

        assertEquals(1, assassin);
        assertEquals(17, spy);
        assertEquals(7, civil);
        assertEquals(8, red,1);
        assertEquals(8, blue,1);
    }


    private static final Random RAND = new Random();

    /**
     * The board should not initialize with less than teh expected number of cards
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createCardsShouldNotAcceptSmallArray()
    {
        String[] words = DatabaseHelper.getRandomCodenames(RAND.nextInt(24));
        Card[][] cards = Board.createCards(words, getRandomLayout(), new HashSet<>());
    }

    /**
     * The game should not be allowed to reveal cards that are not within the board
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void revealAtShouldNotAcceptBadCards()
    {
        Board board = new Board(DatabaseHelper.getRandomCodenames(25), getRandomLayout());

        // valid indices
        int x = RAND.nextInt(5);
        int y = RAND.nextInt(5);

        // make at least one dimension out of bounds
        switch(RAND.nextInt(2))
        {
            case 0:
                x += RAND.nextInt() + 5;
                break;
            case 1:
                y += RAND.nextInt() + 5;
            default:
                x += RAND.nextInt() + 5;
                y += RAND.nextInt() + 5;
        }
        board.revealAt(x, y);
    }

    /**
     * The board should not contain any null values
     */
    @Test
    public void boardShouldNotContainNullOrEmpty()
    {
        Board board = new Board(DatabaseHelper.getRandomCodenames(25), getRandomLayout());
        assertFalse(board.hasWord(null));
        assertFalse(board.hasWord(""));
    }

    /**
     * The statistics should be undated as necessary
     */
    @Test
    public void addStatsShouldActuallyAddStats(){
        boolean answer = DatabaseHelper.addGameToStats("rt", "bt", -25, "Bt", true, 2, 1, 0 );
        DatabaseHelper.deleteTestEntry();
        assertTrue(answer);

    }

    /**
     * Accessor for a random layout from the Database Helper
     * @return
     */
    private String getRandomLayout()
    {
        return DatabaseHelper.getBoardLayout()[1];
    }


}
