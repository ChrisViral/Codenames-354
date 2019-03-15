/*
 * SafeSpyMasterAI.java
 * Created by: Michael Wilgus
 * Created on: 12/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.SuggestionGraph;

import java.util.Comparator;

public class SafeSpyMasterAI implements IPlayer {
    private class ClueComparator implements Comparator<Clue>
    {
        private PlayerType type;

        public ClueComparator(PlayerType type)
        {
            this.type = type;
        }

        @Override
        public int compare(Clue clue1, Clue clue2) {
            int penalty1, penalty2;
            int score1, score2;
            if(type.equals(PlayerType.RED))
            {
                penalty1 = clue1.blueSuggested + clue1.civilianSuggested + (clue1.assassinSuggested ? 1 : 0);
                penalty2 = clue2.blueSuggested + clue2.civilianSuggested + (clue2.assassinSuggested ? 1 : 0);
                score1 = -clue1.redSuggested + penalty1;
                score2 = -clue2.redSuggested + penalty2;
            }
            else
            {
                penalty1 = clue1.redSuggested + clue1.civilianSuggested + (clue1.assassinSuggested ? 1 : 0);
                penalty2 = clue2.redSuggested + clue2.civilianSuggested + (clue2.assassinSuggested ? 1 : 0);
                score1 = -clue1.blueSuggested + penalty1;
                score2 = -clue2.blueSuggested + penalty2;
            }

            return Integer.compare(score1, score2);
        }
    }

    @Override
    public void playTurn(Player player) {
        player.game.setPhase(player.teamName + " SpyMaster");

        SuggestionGraph map = player.game.getSuggestionMap();

        ClueComparator comparator = new ClueComparator(player.team);

        Clue clue = map.getBestClue(comparator);
        int guesses;
        if(player.team == PlayerType.RED)
        {
            guesses = clue.redSuggested - (clue.blueSuggested + clue.civilianSuggested + (clue.assassinSuggested ? 1 : 0));
        }
        else
        {
            guesses = clue.blueSuggested - (clue.redSuggested + clue.civilianSuggested + (clue.assassinSuggested ? 1 : 0));
        }
        clue.value = Math.max(guesses, 1);
        player.game.setCurrentClue(clue);
    }
}
