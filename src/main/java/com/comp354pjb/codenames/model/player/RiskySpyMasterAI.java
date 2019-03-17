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

public class RiskySpyMasterAI extends Strategy {
    private Game game;

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

    @Override
    public void execute() {
        this.game.setPhase(this.team.niceName() + " SpyMaster");

        SuggestionGraph map = game.getSuggestionMap();

        RiskySpyMasterAI.ClueComparator comparator = new RiskySpyMasterAI.ClueComparator(this.team);

        Clue clue = map.getBestClue(comparator);
        clue.value = clue.getNumberOfCardsSuggestedForTeam(this.team);
        game.setCurrentClue(clue);
        finished = true;
    }
}
