package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class SuggestionMap {
    HashMap<String, Clue> clues;
    HashMap<String, Card> cards;

    public SuggestionMap(HashMap<String, Clue> clues, HashMap<String, Card> cards)
    {
        this.clues = clues;
        this.cards = cards;
    }

    public Clue getBestClue(Comparator<Clue> comparator)
    {
        ArrayList<Clue> list = new ArrayList<>(clues.values());
        list.sort(comparator);
        return list.get(0);
    }

    public boolean pickCard(String codename)
    {
        Card card;
        if(this.cards.containsKey(codename)) {
            card = this.cards.get(codename);
        } else {
            return false;
        }

        for(String relatedClue : card.getClues())
        {
            Clue clue = clues.get(relatedClue);
            clue.removeCard(card);
            if(!clue.suggestsSomeCard()) {
                clues.remove(clue.word);
            }
        }
        return true;
    }
}
