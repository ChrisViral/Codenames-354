/*
 * MemoryOperativeAI.java
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

/**
 * Smartest implementation of an Operative AI. Has a memory of previous clues.
 * Continually guesses until it either picks a wrong colored card or it picks as many cards as
 * was indicated by the SpyMaster. This strategy can remember a previous clue if it failed to
 * guess all the correct cards. It will use the memory on subsequent turns if it guesses all of
 * the current cards correctly. It will proceed to use the previous clue and its extra guess to
 * to attempt to get at most one extra card.
 */
public class MemoryOperativeAI extends Strategy
{
    //region Fields
    private String previousClue = null;
    private boolean useExtraTurn = false;
    //endregion

    //region Constructors
    /**
     * Creates a new MemoryOperativeAI
     * @param game Game this AI is linked to
     * @param team Team this AI is linked to
     */
    public MemoryOperativeAI(Game game, PlayerType team)
    {
        super(game, team);
    }
    //endregion

    //region Methods
    /**
     * Operative title
     * @return "Operative"
     */
    @Override
    protected String title()
    {
        return "Operative";
    }

    /**
     * Executes this AI's turn
     */
    @Override
    protected void executeStrategy()
    {
        Clue clue = game.getCurrentClue();

        // We are going to use our extra turn
        if (useExtraTurn)
        {
            useExtraTurn = false;
            // Remember what cards are suggested by the clue
            clue = game.getSuggestionGraph().getClue(previousClue);
            boolean foundExtraCard = pickCard(clue);
            if (foundExtraCard)
            {
                previousClue = null;
            }
            // We still didn't find the suggested card so hold on to our memory
            this.game.endCurrentTurn();
        }
        // We've made our choice so we can look at the card now
        else if (pickCard(clue))
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
        }
    }
    //endregion
}
