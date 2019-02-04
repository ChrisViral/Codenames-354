package com.comp354pjb.codenames.model.board;
import com.comp354pjb.codenames.model.Observer;
import com.comp354pjb.codenames.model.Subject;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.player.PlayerType;
import java.util.*;

public class Board implements Subject
{
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

    public Card getCard(int x, int y)
    {
        return this.cards[x][y];
    }

    public void revealAt(int x, int y)
    {
        Card card = getCard(x, y);
        if (!card.isRevealed())
        {
            card.setRevealed(true);
            this.game.getController().flip(x, y, card.getType());
        }
    }

    /**
     * The following methods are all related to the subject interface
     */

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public void register(Observer obs)
    {
        observers.add(obs);
    }
    public void unregister(Observer obs)
    {
        for(int i=0; i<observers.size(); i++)
        {
            if(observers.get(i).equals(obs))
            {
                observers.remove(i);
                i--;
            }

        }
    }

    public void notifyObservers()
    {
        for(int i=0; i<observers.size(); i++)
        {
            observers.get(i).update();
        }
    }

    //public Object getUpdate(Observer obs){}


}