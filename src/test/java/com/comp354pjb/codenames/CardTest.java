/*
 * CardTest.java
 * Created by: Michael Wilgus
 * Created on: 09/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.board.CardType;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    private static Card card = new Card("table", CardType.RED, 0, 0);

    @Test
    public void getCardTypeShouldReturnRed() {
        assertEquals(CardType.RED, card.getType());
    }

    @Test
    public void getWordShouldReturnTable() {
        assertEquals("table", card.getWord());
    }

    @Test
    public void isRevealedShouldReturnFalse() {
        assertFalse(card.isRevealed());
    }

    @Test
    public void setRevealedShouldRevealCard() {
        // Precondition: card is not revealed
        assertFalse(card.isRevealed());

        card.setRevealed(true);
        assertTrue(card.isRevealed());
    }

    @After
    public void resetRevealed() {
        card.setRevealed(false);
    }

    @Test
    public void coordinatesShouldBeZero() {
        assertEquals(0, card.getX());
        assertEquals(0, card.getY());
    }

    @Test
    public void equalsShouldDetectDifferentWords() {
        assertNotEquals(new Card("plum", CardType.RED, 0, 0), card);
    }
}
