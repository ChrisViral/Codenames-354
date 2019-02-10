/*
 * BoardTest.java
 * Created by: Michael Wilgus
 * Created on: 03/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.board.*;
import com.comp354pjb.codenames.model.player.PlayerType;
import com.comp354pjb.codenames.stubs.DatabaseHelperStub;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

public class BoardTest
{
    @Test
    public void createCardsShouldGiveAllCardsAWord() {
        // mock card set
        Card[][] cards = mockCardSet();

        // Do all the cards have a word?
        for(int i = 0; i < cards.length; i++)
        {
            for(int j = 0; j < cards[i].length; j++)
            {
                assertEquals("test", cards[i][j].getWord());
            }
        }
    }

    @Test
    public void getCardShouldNotReturnNull() {
        String[] words = DatabaseHelperStub.selectWords(25);
        Board board = mockBoard(words);
        assertNotNull(board.getCard(0, 0));
    }

    @Test
    public void revealAtShouldSetACardAsRevealed() {
        String[] words = DatabaseHelperStub.selectWords(25);
        Board board = mockBoard(words);

        // Precondition: card is not revealed.
        assertFalse(board.getCard(0,0).isRevealed());

        board.revealAt(0, 0);

        assertTrue(board.getCard(0, 0).isRevealed());
    }

    @Test
    public void hasWordShouldDetectWhenWordsArePresent() {
        String[] words = DatabaseHelperStub.selectWords(25);
        Board board = mockBoard(words);
        for(int i = 0; i < words.length; i++) {
            assertTrue(board.hasWord(words[i]));
        }

        String[] nonDBWords = {"bull", "database", "pear"};
        for(int i = 0; i < nonDBWords.length; i++) {
            assertFalse(board.hasWord(nonDBWords[i]));
        }
    }

    private static Card[][] mockCardSet() {
        String[] words = new String[25];
        Arrays.fill(words, "test");
        Card[][] cards = Board.createCards(words, PlayerType.RED, new HashSet<>());

        return cards;
    }

    private static Board mockBoard(String[] words) {
        Board board = new Board(words, PlayerType.RED);
        return board;
    }
}
