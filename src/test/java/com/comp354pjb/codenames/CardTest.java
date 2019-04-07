/*
 * CardTest.java
 * Created by: Michael Wilgus
 * Created on: 09/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.board.CardType;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest
{
    private static final char[] TOKENS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                                           'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                                           'x', 'y', 'z' };

    private static final Random RAND = new Random();

    @Test
    public void onlyCardsShouldEqualCards()
    {
        Card card = generateCard();
        assertNotEquals(null, card);
        assertNotEquals(generateRandomString(), card);
        assertNotEquals(new Random().nextInt(), card);
    }

    @Test
    public void differentCardsShouldNotBeEqual()
    {
        Card card = generateCard();
        Card dummy = new Card(generateRandomString(), CardType.BLUE, RAND.nextInt(5), RAND.nextInt(5));
        assertNotEquals(dummy, card);
    }

    @Test
    public void cardsWithTheSameWordShouldBeEqual()
    {
        Card card = generateCard();
        Card dummy = new Card(card.getWord(), getRandomCardType(), RAND.nextInt(5), RAND.nextInt(5));
        assertEquals(dummy, card);
    }

    private Card generateCard()
    {
        int row, col;
        CardType type = getRandomCardType();
        row = RAND.nextInt(5);
        col = RAND.nextInt(5);
        String word = DatabaseHelper.getRandomCodename();

        return new Card(word, type, row, col);
    }

    // Guaranteed to not be in the database
    private String generateRandomString()
    {
        int length = RAND.nextInt(29) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append(TOKENS[RAND.nextInt(TOKENS.length)]);
        }
        sb.append(RAND.nextInt(9));
        return sb.toString();
    }

    private CardType getRandomCardType()
    {
        CardType type;
        switch (RAND.nextInt(4))
        {
            case 0:
                type = CardType.BLUE;
                break;
            case 1:
                type = CardType.RED;
                break;
            case 2:
                type = CardType.CIVILIAN;
                break;
            case 3:
                type = CardType.ASSASSIN;
                break;
            default: // Keep the compiler happy
                type = CardType.BLUE;
                break;
        }

        return type;
    }

}
