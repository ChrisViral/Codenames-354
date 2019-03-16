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
import com.comp354pjb.codenames.model.player.PlayerType;

import org.junit.Test;

import javax.print.attribute.standard.DateTimeAtCompleted;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;

public class BoardTest
{
    private static final Random RAND = new Random();

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createCardsShouldNotAcceptSmallArray()
    {
        String[] words = DatabaseHelper.getRandomCodenames(RAND.nextInt(24));
        Card[][] cards = Board.createCards(words, getRandomLayout(), new HashSet<>());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void revealAtShouldNotAcceptBadCoords()
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

    @Test
    public void boardShouldNotContainNullOrEmpty()
    {
        Board board = new Board(DatabaseHelper.getRandomCodenames(25), getRandomLayout());
        assertFalse(board.hasWord(null));
        assertFalse(board.hasWord(""));
    }

    @Test
    public void addStatsShouldActuallyAddStats(){
        Boolean answer = DatabaseHelper.addGameToStats("rt", "bt", -25, "Bt", true, 2, 1, 0 );
        DatabaseHelper.deleteTestEntry();
        assertTrue(answer);

    }
    private String getRandomLayout()
    {
        return DatabaseHelper.getBoardLayout()[1];
    }
}
