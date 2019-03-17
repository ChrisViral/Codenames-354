/*
 * ReasonableOperativeAI.java
 * Created by: Michael Wilgus
 * Created on: 15/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;

public class MemoryOperativeAI extends Strategy {
    private Game game;

    private String previousClue = null;
    private  boolean useExtraTurn = false;

    public MemoryOperativeAI(Game game)
    {
        this.game = game;
    }

    //region Methods

    /**
     * TODO
     */
    @Override
    public void execute() {
        game.setPhase(this.team.niceName() + " Operative");

        Clue clue = game.getCurrentClue();

        if(useExtraTurn) {
            useExtraTurn = false;
            finished = true;
            clue = game.getSuggestionMap().getClue(previousClue);
            System.out.println("Using " + previousClue);
            boolean foundExtraCard = pickCard(clue);
            if(foundExtraCard)
            {
                previousClue = null;
            }
            return;
        }

        boolean foundAGoodCard = pickCard(clue);

        // We've made our choice so we can look at the card now
        if(foundAGoodCard)
        {
            if(game.getGuessesLeft() == 0)
            {
                // Check to see if we missed a codename previously
                if(previousClue != null)
                {
                    // Can we use the extra turn with the previous clue?
                    if(previousClue.equals(clue.word) || game.getSuggestionMap().getClue(previousClue) == null)
                    {
                        // We can't so forget the clue and end the turn
                        previousClue = null;
                        finished = true;
                        return;
                    }
                    // We can so indicate that
                    useExtraTurn = true;
                }
                else
                {
                    // We got everything last turn so we can end this turn now
                    finished = true;
                }
            }
        }
        else
        {
            // Our turn is over and we didn't succeed in guessing all of the right codenames;
            previousClue = clue.word;
            finished = true;
            return;
        }
    }

    private boolean pickCard(Clue clue)
    {
        ArrayList<Card> cards = clue.getCards();
        cards = clue.getCards();
        int i = Game.RANDOM.nextInt(cards.size());
        Card card = cards.get(i);
        game.revealCard(card);

        return  card.getType().equals(team.getCardType());
    }
}
