/*
 * BoardTest.java
 * Created by: Michael Wilgus
 * Created on: 03/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.board.*;
import com.comp354pjb.codenames.model.player.PlayerType;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;

public class BoardTest
{
    private static final Random RAND = new Random();

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createCardsShouldNotAcceptSmallArray() {
        String[] words = DatabaseHelper.selectWords(RAND.nextInt(24));
        Card[][] cards = Board.createCards(words, getRandomPlayerType(), new HashSet<>());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void revealAtShouldNotAcceptBadCoords() {
        Board board = new Board(DatabaseHelper.selectWords(25), getRandomPlayerType());
        int x = RAND.nextInt();
        int y = RAND.nextInt();
        board.revealAt(x, y);
    }

    @Test
    public void boardShouldNotContainNullOrEmpty() {
        Board board = new Board(DatabaseHelper.selectWords(25), getRandomPlayerType());
        assertFalse(board.hasWord(null));
        assertFalse(board.hasWord(""));
    }

    private PlayerType getRandomPlayerType() {
        if(RAND.nextInt() % 2 == 0) {
            return PlayerType.RED;
        }
        return PlayerType.BLUE;
    }
}
