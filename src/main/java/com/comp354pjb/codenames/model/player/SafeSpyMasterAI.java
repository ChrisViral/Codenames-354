/*
 * SafeSpyMasterAI.java
 * Created by: Michael Wilgus
 * Created on: 12/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.SuggestionGraph;

import java.util.Comparator;

public class SafeSpyMasterAI extends Strategy {
    private Game game;

    private static class ClueComparator implements Comparator<Clue>
    {
        private PlayerType team;

        public ClueComparator(PlayerType team)
        {
            this.team = team;
        }

        @Override
        public int compare(Clue clue1, Clue clue2) {
            int penalty1 = clue1.getComplementOfCardsSuggestedForTeam(team);
            int penalty2 = clue2.getComplementOfCardsSuggestedForTeam(team);
            int score1 = -clue1.getNumberOfCardsSuggestedForTeam(team) + penalty1;
            int score2 = -clue2.getNumberOfCardsSuggestedForTeam(team) + penalty2;

            return Integer.compare(score1, score2);
        }
    }

    public SafeSpyMasterAI(Game game)
    {
        this.game = game;
    }

    @Override
    public void execute() {
        game.setPhase(this.team.niceName() + " SpyMaster");

        SuggestionGraph map = game.getSuggestionMap();

        ClueComparator comparator = new ClueComparator(this.team);

        Clue clue = map.getBestClue(comparator);
        int guesses = clue.getNumberOfCardsSuggestedForTeam(team) - clue.getComplementOfCardsSuggestedForTeam(team);
        clue.value = Math.max(guesses, 1);
        game.setCurrentClue(clue);
        finished = true;
    }
}
