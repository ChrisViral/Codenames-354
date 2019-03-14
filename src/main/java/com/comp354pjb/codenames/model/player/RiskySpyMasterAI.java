package com.comp354pjb.codenames.model.player;

import java.util.Comparator;

public class RiskySpyMasterAI implements IPlayer {
    private class ClueComparator implements Comparator<Clue>
    {
        private PlayerType type;

        public ClueComparator(PlayerType type)
        {
            this.type = type;
        }

        @Override
        public int compare(Clue clue1, Clue clue2) {
            int score1, score2;
            if(type.equals(PlayerType.RED))
            {
                score1 = -clue1.redSuggested;
                score2 = -clue2.redSuggested;
            }
            else
            {
                score1 = -clue1.blueSuggested;
                score2 = -clue2.blueSuggested;
            }

            return Integer.compare(score1, score2);
        }
    }

    @Override
    public void playTurn(Player player) {
        player.game.setPhase(player.teamName + " SpyMaster");

        SuggestionMap map = player.game.getSuggestionMap();

        RiskySpyMasterAI.ClueComparator comparator = new RiskySpyMasterAI.ClueComparator(player.team);

        Clue clue = map.getBestClue(comparator);
        int guesses;
        if(player.team == PlayerType.RED)
        {
            guesses = clue.redSuggested;
        }
        else
        {
            guesses = clue.blueSuggested;
        }
        clue.value = guesses;
        player.game.setCurrentClue(clue);
    }
}
