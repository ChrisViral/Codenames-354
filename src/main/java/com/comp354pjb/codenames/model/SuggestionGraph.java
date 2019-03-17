/*
 * SuggestionGraph.java
 * Created by: Michael Wilgus
 * Created on: 14/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.player.Clue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


public class SuggestionGraph {
    private HashMap<String, Clue> clues;
    private HashMap<String, Card> cards;

    public SuggestionGraph(HashMap<String, Clue> clues, HashMap<String, Card> cards)
    {
        this.clues = clues;
        this.cards = cards;
    }

    public Clue getClue(String clue)
    {
        return clues.get(clue);
    }

    public Clue getBestClue(Comparator<Clue> comparator)
    {
        Random rand = new Random();
        ArrayList<Clue> list = new ArrayList<>(clues.values());
        list.sort(comparator);
        Clue bestClue = list.get(0);
        int ties = 1;
        while(comparator.compare(bestClue, list.get(ties)) == 0)
        {
            ties++;
            if(ties == list.size()) break;
        }
        int i = rand.nextInt(ties);
        return list.get(i);
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
            if(clue != null) {
                if(clue.word == codename) clue.isActiveCodename = false;
                clue.removeCard(card);
                if (!clue.suggestsSomeCard() || clue.onlySuggestsAssassinOrCivilian()) {
                    clues.remove(clue.word);
                }
            }
        }
        return true;
    }
}
