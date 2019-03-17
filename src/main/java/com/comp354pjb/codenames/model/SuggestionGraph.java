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

/**
 * Represents the clue to card relationship with a bipartite graph. Uses an adjacency list scheme.
 * That is there are two sets of vertices (Clue and Card) and for each vertex there is a list of its
 * neighbors. This structure is dynamic; it is updated and pruned as the game is played. For instance,
 * when a card is revealed on the board any clue that suggested that card is updated to reflect that it
 * no longer suggests that card so that it might be a better clue for some strategies in the future.
 */
public class SuggestionGraph {
    //region Fileds
    private HashMap<String, Clue> clues;
    private HashMap<String, Card> cards;

    private Random rand;
    //endregion

    //region Constructors
    public SuggestionGraph(HashMap<String, Clue> clues, HashMap<String, Card> cards, Random rand)
    {
        this.clues = clues;
        this.cards = cards;
        this.rand = rand;
    }
    //endregion

    /**
     * Get a particular clue from the graph
     * @param clue The clue word associated with the object to be returned
     * @return The Clue that has the word given
     */
    public Clue getClue(String clue)
    {
        return clues.get(clue);
    }

    /**
     * Gets the "best" clue according to some criteria
     * @param comparator An object that can sort Clue objects according to some criteria
     * @return The Clue that best fits the critera
     */
    public Clue getBestClue(Comparator<Clue> comparator)
    {
        HashMap<String, Clue> useableClues = getUseableClues();
        ArrayList<Clue> list = new ArrayList<>(useableClues.values());
        list.sort(comparator);

        // Best clue should be at the front
        Clue bestClue = list.get(0);
        // But there might be ties
        int ties = 1;
        while(comparator.compare(bestClue, list.get(ties)) == 0)
        {
            ties++;
            if(ties == list.size()) break;
        }
        // Pick randomly among the ties
        int i = rand.nextInt(ties);
        return list.get(i);
    }

    /**
     * Get a random Clue from the graph
     * @return Any Clue that the graph records
     */
    public Clue getRandomClue()
    {
        HashMap<String, Clue> useableClues = getUseableClues();
        ArrayList<Clue> list = new ArrayList<>(useableClues.values());
        int i = rand.nextInt(useableClues.size());
        return list.get(i);
    }

    /**
     * Updates the graph when a Card is picked in the game
     * @param codename The word on the card that was picked
     * @return True if any update to the graph takes place and false otherwise
     */
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
                if(clue.word.equals(codename)) clue.isActiveCodename = false;
                clue.removeCard(card);
                if (!clue.suggestsSomeCard() || clue.onlySuggestsAssassinOrCivilian()) {
                    clues.remove(clue.word);
                }
            }
        }
        return true;
    }

    //region Helpers

    // We can only give out clues that are not also codenames. Since it is possible that a clue word
    // is also on a card we must exclude these clues until this cards are picked.
    // NOTE: Strictly speaking this means the graph is not really bipartite in one sense. However Clues,
    // and Cards are distinct so it is in another
    private HashMap<String, Clue> getUseableClues()
    {
        HashMap<String, Clue> useableClues = new HashMap<>();
        for(Clue clue : clues.values())
        {
            if(!clue.isActiveCodename)
            {
                useableClues.put(clue.word, clue);
            }
        }

        return useableClues;
    }
}
