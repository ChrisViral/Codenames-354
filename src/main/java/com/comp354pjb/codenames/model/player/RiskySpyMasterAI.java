/*
 * RiskySpyMasterAI.java
 * Created by: Michael Wilgus
 * Created on: 14/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.SuggestionGraph;

import java.util.Comparator;

/**
 * Implements risky AI strategy which always picks the clue that suggests the most cards.
 * Intelligent but brazen implementation of a SpyMaster AI. Will always give the clue that suggests
 * the most correctly colored cards no matter how many of the opposing teams or civilian cards it suggests
 * or regardless of if it suggests the assassin card.
 */
public class RiskySpyMasterAI extends Strategy
{
    /**
     * This comparator will be used to sort all the clues in the game according to the strategy
     */
    private class ClueComparator implements Comparator<Clue>
    {
        //region Fields
        private final PlayerType team;
        //endregion

        //region Constructors
        /**
         * Creates a new ClueComparator for the given Team
         * @param team Team to compare for
         */
        private ClueComparator(PlayerType team)
        {
            this.team = team;
        }
        //endregion

        //region Methods
        /**
         * Compares two clues
         * @param clue1 First clue to compare
         * @param clue2 Second clue to compare
         * @return The comparison result
         */
        @Override
        public int compare(Clue clue1, Clue clue2)
        {
            // Lower scores are better
            int score1 = -clue1.getNumberOfCardsSuggestedForTeam(team);
            int score2 = -clue2.getNumberOfCardsSuggestedForTeam(team);

            return Integer.compare(score1, score2);
        }
        //endregion
    }

    //region Fields
    private final ClueComparator comparator;
    //endregion

    //region Constructors
    /**
     * Creates a new RiskySpyMasterAI
     * @param game Game this AI is linked to
     * @param team Team this AI is linked to
     */
    public RiskySpyMasterAI(Game game, PlayerType team)
    {
        super(game, team);
        this.comparator = new ClueComparator(team);
    }
    //endregion

    //region Methods
    /**
     * SpyMaster title
     * @return "SpyMaster"
     */
    @Override
    protected String title()
    {
        return "SpyMaster";
    }

    /**
     * Gets the suggestion graph for the current game and passes it a comparator so that it can find
     * some clue that fits this strategies criteria
     */
    @Override
    public void executeStrategy()
    {
        // Consult the game clue/card association information
        SuggestionGraph map = game.getSuggestionGraph();
        Clue clue = map.getBestClue(this.comparator);
        clue.value = clue.getNumberOfCardsSuggestedForTeam(this.team);

        // Give the clue
        Commander.log(name() + " gave the clue " + clue);
        game.setCurrentClue(clue);

        // We are done
        this.game.endCurrentTurn();
    }
    //endregion
}
