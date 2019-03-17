/*
 * RiskySpyMasterAI.java
 * Created by: Michael Wilgus
 * Created on: 14/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.SuggestionGraph;

import java.util.Comparator;

/**
 * Intelligent but brazen implementation of a SpyMaster AI. Will always give the clue that suggests
 * the most correctly colored cards no matter how many other cards it also suggests.
 */
public class RiskySpyMasterAI extends Strategy {
    public static final PlayerIntelligence STRATEGY_CLASS = PlayerIntelligence.MEDIUM;

    private Game game;

    // This comparator will be used to sort all the clues in the game according to the strategy
    private class ClueComparator implements Comparator<Clue>
    {
        private PlayerType team;

        public ClueComparator(PlayerType team)
        {
            this.team = team;
        }

        @Override
        public int compare(Clue clue1, Clue clue2) {
            int score1 = -clue1.getNumberOfCardsSuggestedForTeam(team);
            int score2 = -clue2.getNumberOfCardsSuggestedForTeam(team);

            return Integer.compare(score1, score2);
        }
    }

    public RiskySpyMasterAI(Game game)
    {
        this.game = game;
    }

    /**
     * Gets the suggestion graph for the current game and passes it a comparator so that it can find
     * some clue that fits this strategies critera
     */
    @Override
    public void execute() {
        this.game.setPhase(this.team.niceName() + " SpyMaster");

        SuggestionGraph map = game.getSuggestionGraph();

        RiskySpyMasterAI.ClueComparator comparator = new RiskySpyMasterAI.ClueComparator(this.team);

        Clue clue = map.getBestClue(comparator);
        clue.value = clue.getNumberOfCardsSuggestedForTeam(this.team);
        game.setCurrentClue(clue);
        finished = true;
    }
}
