package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.board.*;
import com.comp354pjb.codenames.model.player.PlayerType;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BoardTest
{
    @Test
    public void createCardsShouldGiveAllCardsAWord() {
        String[] words = new String[25];
        for(int i = 0; i < words.length; i++)
        {
            words[i] = "test";
        }

        // Created a mock board
        Card[][] cards = Board.createCards(words, PlayerType.RED);

        // Do all the cards have a word?
        for(int i = 0; i < cards.length; i++) {
            for(int j = 0; j < cards[i].length; j++) {
                assertEquals("test", cards[i][j].getWord());
            }
        }
    }
}
