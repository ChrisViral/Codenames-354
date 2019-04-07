/*
 * SuggestionGraphHelper.java
 * Created by: Mordechai Zirkind
 * Created on: 06/04/19
 *
 * Contributors:
 * Mordechai Zirkind
 */


package com.comp354pjb.codenames;

// region Imports
import com.comp354pjb.codenames.model.SuggestionGraph;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.board.CardType;
import com.comp354pjb.codenames.model.player.Clue;
import com.comp354pjb.codenames.model.DatabaseHelper;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
// endregion

public class SuggestionGraphTest
{
    // region Fields
    public static final Random RANDOM = new Random();
    // endregion

    // region Helper Methods

    /**
     * This is a test helper method to create the suggestion graph as needed.
     * Most of this method is a duplicate of #Game.createSuggestionGraph()
     * -----------
     * Created by Mordechai Zirkind
     */
    private SuggestionGraph createSuggestionGraph(String[] codenames)
    {
        ArrayList<Card> cards = new ArrayList<Card>(25);

        for (int i = 0; i < codenames.length; i++)
        {
            CardType ct;
            if (i < 9)
            {
                ct = CardType.BLUE;
            }
            else if (i < 17)
            {
                ct = CardType.RED;
            }
            else if (i < 24)
            {
                ct = CardType.CIVILIAN;
            }
            else
            {
                ct = CardType.ASSASSIN;
            }

            cards.add(new Card(codenames[i], ct, 0, 0));
            String[] clues = DatabaseHelper.getCluesForCodename(codenames[i]);

            for (int j = 0; j < clues.length; j++)
            {
                cards.get(i).addClue(clues[j]);
            }
        }

        // These variables will represent the graph structure from clues to cards (and vise versa)
        HashMap<String, Clue> cluesHash = new HashMap<>();
        HashMap<String, Card> cardsHash = new HashMap<>();

        // Add Clues to the graph
        for (Card c : cards)
        {
            cardsHash.put(c.getWord(), c);
            HashSet<String> suggestions = c.getClues();
            for (String s : suggestions)
            {
                Clue clue = cluesHash.getOrDefault(s, new Clue(s));
                clue.addCard(c);
                cluesHash.put(s, clue);
            }
        }


        // Remove clues that only suggest the assassin or civilians
        // NOTE: This must be done here because this information is only
        // available after all clues have been added
        ArrayList<String> badClues = new ArrayList<>();
        for (Clue clue : cluesHash.values())
        {
            if (clue.onlySuggestsAssassinOrCivilian())
            {
                badClues.add(clue.word);

            }
            if (cardsHash.containsKey(clue.word))
            {
                clue.isActiveCodename = true;
            }
        }

        for (String key : badClues)
        {
            cluesHash.remove(key);
        }

        return new SuggestionGraph(cluesHash, cardsHash, RANDOM);
    }

    // endregion

    // region Tests

    /**
     * Tests that the constructor actually works.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldCreateSuggestionGraph()
    {
        SuggestionGraph graph = createSuggestionGraph(DatabaseHelper.getRandomCodenames(25));
        assertNotNull(graph);
    }

    /**
     * Tests that getRandomClues() actually returns different clues.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void randomCluesShouldBeDifferent()
    {
        SuggestionGraph graph = createSuggestionGraph(DatabaseHelper.getRandomCodenames(25));

        // Using three clue to reduce odds of test failing by chance.
        String clue1 = graph.getRandomClue().word;
        String clue2 = graph.getRandomClue().word;
        String clue3 = graph.getRandomClue().word;


        assertFalse(clue1.equals(clue2) || clue2.equals(clue3));
    }

    // get clue should return a clue if it exists
    @Test
    public void shouldReturnCluesThatExist()
    {
        
    }

    // get clue should return nothing if the clue doesn't exist

    // pick card should return true for word in the game

    // pick card should return false for word not in the game

    // endregion
}
