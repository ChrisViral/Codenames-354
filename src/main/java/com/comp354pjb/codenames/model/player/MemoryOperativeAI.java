/*
 * MemoryOperativeAI.java
 * Created by: Michael Wilgus
 * Created on: 15/03/19
 *
 * Contributors:
 * Michael Wilgus
 *
 * Description:
 * Continually guesses until it either picks a wrong colored card or it picks as many cards as
 * was indicated by the SpyMaster. This strategy can remember a previous clue if it failed to
 * guess all the correct cards. It will use the memory on subsequent turns if it guesses all of
 * the current cards correctly. It will proceed to use the previous clue and its extra guess to
 * to attempt to get at most one extra card.
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;

/**
 * Smartest implementation of an Operative AI. Has a memory of previous clues.
 * (See above for full description)
 */
public class MemoryOperativeAI extends Strategy
{
    public static final PlayerIntelligence STRATEGY_CLASS = PlayerIntelligence.SMART;

    private Game game;

    private String previousClue = null;
    private boolean useExtraTurn = false;

    public MemoryOperativeAI(Game game)
    {
        this.game = game;
    }

    //region Methods

    @Override
    public void execute()
    {
        game.setPhase(this.team.niceName() + " Operative");

        Clue clue = game.getCurrentClue();

        // We are going to use our extra turn
        if (useExtraTurn)
        {
            useExtraTurn = false;
            this.game.endCurrentTurn();
            // Remember what cards are suggested by the clue
            clue = game.getSuggestionGraph().getClue(previousClue);
            boolean foundExtraCard = pickCard(clue);
            if (foundExtraCard)
            {
                previousClue = null;
            }
            // We still didn't find the suggested card so hold on to our memory
            return;
        }

        boolean foundAGoodCard = pickCard(clue);

        // We've made our choice so we can look at the card now
        if (foundAGoodCard)
        {
            if (game.getGuessesLeft() == 0)
            {
                // Check to see if we missed a codename previously
                if (previousClue != null)
                {
                    // Can we use the extra turn with the previous clue?
                    if (previousClue.equals(clue.word) || game.getSuggestionGraph().getClue(previousClue) == null)
                    {
                        // We can't so forget the clue and end the turn
                        previousClue = null;
                        this.game.endCurrentTurn();
                        return;
                    }
                    // We can so indicate that
                    useExtraTurn = true;
                }
                else
                {
                    // We got everything last turn so we can end this turn now
                    this.game.endCurrentTurn();
                }
            }
        }
        else
        {
            // Our turn is over and we didn't succeed in guessing all of the right codenames;
            previousClue = clue.word;
            this.game.endCurrentTurn();
            return;
        }
    }
    //endregion

    //region Helpers

    // pick a card and check if it belonged to our team
    private boolean pickCard(Clue clue)
    {
        ArrayList<Card> cards = clue.getCards();
        cards = clue.getCards();
        int i = Game.RANDOM.nextInt(cards.size());
        Card card = cards.get(i);
        game.revealCard(card);

        return card.getType().equals(team.getCardType());
    }
    //endregion
}
