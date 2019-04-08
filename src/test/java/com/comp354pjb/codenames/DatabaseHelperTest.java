/*
 * DatabaseHelperTest.java
 * Created by: Michael Wilgus
 * Created on: 30/01/19
 *
 * Contributors:
 * Michael Wilgus
 * Christophe Savard
 * Mordechai Zirkind
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class DatabaseHelperTest
{

    /**
     * Tests the the database connects.
     */
    @Test
    public void shouldConnectToDB()
    {
        assertTrue(DatabaseHelper.checkConnection());
    }


    /**
     * Tests that the expected number of cards is returned.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldReturnRequestedNumOfCodenames()
    {
        String[] codenames = DatabaseHelper.getRandomCodenames(25);
        assertEquals(25, codenames.length);
    }

    /**
     * Verifies that a given set of 25 words has no repeats.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldReturnUniqueCodenames()
    {
        String[] codenames = DatabaseHelper.getRandomCodenames(25);
        boolean noMatches = true;


        for (int i = 0; i < codenames.length-1; i++)
        {
            for (int j = i+1; j < codenames.length; j++)
            {
                if (codenames[i] == codenames[j])
                {
                    noMatches = false;
                    // Here to give more detailed feedback in case the test fails.
                    System.out.println("Match found: " + codenames[i] + " " + codenames[j]);
                }
            }
        }

        assert(noMatches);
    }

    /**
     * Tests that an invalid codename returns an empty array.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldReturnEmptyForInvalidCodename()
    {
        assertEquals(0, DatabaseHelper.getCodenamesForClue("supercalifragilisticexpialidocious").length);
    }

    /**
     * Tests that an invalid clue returns an empty array.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldReturnEmptyForInvalidClue()
    {
        assertEquals(0, DatabaseHelper.getCluesForCodename("supercalifragilisticexpialidocious").length);
    }


    /**
     * Tests that a valid codename returns clues.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void validCodenameShouldReturnClues()
    {
        String codename = DatabaseHelper.getRandomCodename();
        assertTrue(DatabaseHelper.getCluesForCodename(codename).length > 0);
    }

    /**
     * Tests that a valid clue returns codenames.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void validClueShouldReturnCodenames()
    {
        String clue = DatabaseHelper.getRandomClue();
        assertTrue(DatabaseHelper.getCodenamesForClue(clue).length > 0);
    }

    /**
     * Tests that the clues for a codename return that codename.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void clueFromCodenameShouldReturnSaidCodename()
    {
        String codename = DatabaseHelper.getRandomCodename();
        String[] clues = DatabaseHelper.getCluesForCodename(codename);
        int errors = 0;

        for (int i = 0; i < clues.length; i++)
        {
            int index = Arrays.binarySearch(DatabaseHelper.getCodenamesForClue(clues[i]), codename);
            if (!(index >= 0))
            {
                errors++;
            }

        }

        assertEquals(0, errors);
    }

    /**
     * Tests that board layouts are valid.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldReturnValidBoardLayouts()
    {
        String[] layout = DatabaseHelper.getBoardLayout();
        int expectedRs = (layout[0].equals("R")) ? 9 : 8;
        int expectedBs = (layout[0].equals("B")) ? 9 : 8;
        int expectedCs = 7;
        int expectedA = 1;
        boolean validLayout = true;

        // Functional method to count instances of char in string take from: https://stackoverflow.com/a/23906674/1585599

        if(layout[1].chars().filter(num -> num == 'R').count() != expectedRs)
        {
            validLayout = false;
            System.out.println("Wrong number of Rs.");
        }

        if(layout[1].chars().filter(num -> num == 'B').count() != expectedBs)
        {
            validLayout = false;
            System.out.println("Wrong number of Bs.");
        }

        if(layout[1].chars().filter(num -> num == 'C').count() != expectedCs)
        {
            validLayout = false;
            System.out.println("Wrong number of Cs.");
        }

        if(layout[1].chars().filter(num -> num == 'A').count() != expectedA)
        {
            validLayout = false;
            System.out.println("Wrong number of A.");
        }

        assertTrue(validLayout);
    }

    /**
     * Tests that game history is added to the database.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldAddValidGameHistory()
    {
        boolean added = DatabaseHelper.addGameToStats("test", "test", -25, "test", false, 0, 5, 9);
        DatabaseHelper.deleteTestEntry();
        assertTrue(added);
    }

    /**
     * Tests that test game history is deleted from the database.
     * -----------
     * Created by Mordechai Zirkind
     */
    @Test
    public void shouldDeleteTestGameStats()
    {
        DatabaseHelper.addGameToStats("test", "test", -25, "test", false, 0, 5, 9);
        boolean removed = DatabaseHelper.deleteTestEntry();
        assertTrue(removed);
    }

}
