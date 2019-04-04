/*
 * SafeSpyMasterAI.java
 * Created by: Michael Wilgus
 * Created on: 12/03/19
 *
 * Contributors:
 * Michael Wilgus
 *
 * Description:
 * The safest implementation of a SpyMaster AI. Considers not only how many of the correct
 * colored cards a clue suggests but also penalizes a clue if it suggests the opposing teams cards,
 * the assassin or a civilian card.
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.SuggestionGraph;

import java.util.Comparator;

/**
 * The safest implementation of a SpyMaster AI. Considers not only how many of the correct
 * colored cards a clue suggests but also penalizes a clue if it suggests incorrect cards
 * (See full description above)
 */
public class SafeSpyMasterAI extends Strategy
{
    public static final PlayerIntelligence STRATEGY_CLASS = PlayerIntelligence.SMART;

    private Game game;

    public SafeSpyMasterAI(Game game)
    {
        this.game = game;
    }

    @Override
    public void execute()
    {
        game.setPhase(this.team.niceName() + " SpyMaster");

        // Consult the game clue/card association information
        SuggestionGraph map = game.getSuggestionGraph();

        ClueComparator comparator = new ClueComparator(this.team);

        Clue clue = map.getBestClue(comparator);

        // We should be careful how many Cards we will let an Operative pick
        // If to many incorrectly colored cards are suggested penalize the count
        int guesses = clue.getNumberOfCardsSuggestedForTeam(team) - clue.getComplementOfCardsSuggestedForTeam(team);
        // We should let an operative pick at least one card though
        clue.value = Math.max(guesses, 1);

        // Give the clue
        game.setCurrentClue(clue);

        // We are done
        this.game.endCurrentTurn();
    }

    // This comparator will be used to sort all the clues in the game according to the strategy
    private static class ClueComparator implements Comparator<Clue>
    {
        private PlayerType team;

        public ClueComparator(PlayerType team)
        {
            this.team = team;
        }

        /**
         * Gets the suggestion graph for the current game and passes it a comparator so that it can find
         * some clue that fits this strategies critera
         */
        @Override
        public int compare(Clue clue1, Clue clue2)
        {
            // Penalties are based on how many incorrectly colored cards a clue suggests
            int penalty1 = clue1.getComplementOfCardsSuggestedForTeam(team);
            int penalty2 = clue2.getComplementOfCardsSuggestedForTeam(team);

            // Lower scores are better so we add the penalty
            int score1 = -clue1.getNumberOfCardsSuggestedForTeam(team) + penalty1;
            int score2 = -clue2.getNumberOfCardsSuggestedForTeam(team) + penalty2;

            return Integer.compare(score1, score2);
        }
    }
}
