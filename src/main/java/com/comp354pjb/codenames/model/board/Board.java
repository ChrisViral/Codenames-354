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

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.player.PlayerType;

import java.util.*;

public class Board
{
    /**
     * Sets the preset of card types on the board. All 25 cards are defaulted to a specific type of card, either
     * RED, BLUE, ASSASSIN or CIVILIAN.
     */
    private static final CardType[] preset =
    {
        CardType.RED, CardType.RED, CardType.RED, CardType.RED, CardType.RED, CardType.RED, CardType.RED, CardType.RED,                      //8 Red cards
        CardType.BLUE, CardType.BLUE, CardType.BLUE, CardType.BLUE, CardType.BLUE, CardType.BLUE, CardType.BLUE, CardType.BLUE,              //8 Blue cards
        CardType.CIVILIAN, CardType.CIVILIAN, CardType.CIVILIAN, CardType.CIVILIAN, CardType.CIVILIAN, CardType.CIVILIAN, CardType.CIVILIAN, //7 Civilian cards
        CardType.ASSASSIN,  //1 Assassin card
    };

    private Game game;
    private Card[][] cards;

    public Board(Game game, String[] words, PlayerType startingPlayer)
    {
        this.game = game;
        this.cards = Board.createCards(words, startingPlayer);
    }

    /**
     * Creates an array of card with the correct amount of types with the given words
     * @param words Words to put on the cards
     * @param startingPlayer Starting player (will have extra card of it's colour)
     * @return The created 5 by 5 array of cards
     */
    public static Card[][] createCards(String[] words, PlayerType startingPlayer)
    {
        Card[][] cards = new Card[5][5];
        ArrayList<CardType> types = new ArrayList<>(Arrays.asList(preset));
        switch (startingPlayer)
        {
            case RED:
                types.add(CardType.RED);
                break;

            case BLUE:
                types.add(CardType.BLUE);
                break;
        }
        Collections.shuffle(types);

        for (int i = 0; i < 5; i++)
        {
            int stride = i * 5;
            for (int j = 0; j < 5; j++)
            {
                int index = stride + j;
                cards[i][j] = new Card(words[index], types.get(index));
            }
        }
        return cards;
    }

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

    /**
     * Reveals the card at a specific index, checks if it was flipped already, if not, it will flip the card.
     * @param x Index horizontal
     * @param y Index vertical
     *
     */
    public void revealAt(int x, int y)
    {
        Card card = getCard(x, y);
        if (!card.isRevealed())
        {
            card.setRevealed(true);
            this.game.getController().flip(x, y, card.getType());
        }
    }
}