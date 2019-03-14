/*
 * Board.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.board;

import com.comp354pjb.codenames.model.player.PlayerType;
import com.comp354pjb.codenames.observer.events.CardFlippedEvent;
import java.util.*;

/**
 * Board class, represents the 5 by 5 grid of cards on which the game happens
 */
public class Board
{
    //region Fields
    private final Card[][] cards;
    private final HashSet<String> words = new HashSet<>();
    //endregion

    //region Events
    /**
     * Single card flipped event
     */
    public final CardFlippedEvent onFlip = new CardFlippedEvent();
    //endregion

    //region Constructors
    /**
     * Creates a new 5x5 board of cards with the supplied words
     * @param words  The layout string for the board (String of RBCA of length 25)
     * @param layout Player starting the game
     */
    public Board(String[] words, String layout)
    {
        //Create the cards
        this.cards = Board.createCards(words, layout, this.words);
    }
    //endregion

    //region Static methods
    /**
     * Creates an array of card with the correct amount of types with the given words
     * @param words  Words to put on the cards
     * @param layout The layout string for the board (String of RBCA of length 25)
     * @return The created 5 by 5 array of cards
     */
    public static Card[][] createCards(String[] words, String layout, HashSet<String> wordSet)
    {
        Card[][] cards = new Card[5][5];
        wordSet.addAll(Arrays.asList(words));

        //Add the cards to the set and array
        for (int i = 0; i < 5; i++)
        {
            int stride = i * 5;
            for (int j = 0; j < 5; j++)
            {
                int index = stride + j;
                cards[i][j] = new Card(words[index], CardType.parse(layout.charAt(index)), i, j);
            }
        }
        return cards;
    }
    //endregion

    //region Accessors
    /**
     * Getting a specific card at a specific index on the board.
     *
     * @param x Index of card on horizontal axis
     * @param y Index of card on vertical axis
     * @return returns specific card on board
     */
    public Card getCard(int x, int y)
    {
        return this.cards[x][y];
    }
    //endregion

    //region Methods
    /**
     * Reveals the card at a specific index, checks if it was flipped already, if not, it will flip the card.
     * @param x Index horizontal
     * @param y Index vertical
     *
     */
    public void revealAt(int x, int y)
    {
        revealCard(getCard(x, y));
    }

    /**
     * Reveals a given card
     * @param card Card to reveal
     */
    public void revealCard(Card card)
    {
        if (!card.isRevealed())
        {
            //Set card as revealed
            card.setRevealed(true);
            //Fire card flipped event
            this.onFlip.invoke(card);
        }
    }

    /**
     * Sees if the board contains a card with the given word
     * @param word Word to look for
     * @return True if the word is in the board, false otherwise
     */
    public boolean hasWord(String word)
    {
        return this.words.contains(word);
    }
    //endregion
}