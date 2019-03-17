/*
 * ReasonableOperativeAI.java
 * Created by: Michael Wilgus
 * Created on: 14/03/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Card;

import java.util.ArrayList;

/**
 * Medium level implementation of an Operative AI. Makes reasonable guesses given a clue
 */
public class ReasonableOperativeAI extends Strategy {
    public static final PlayerIntelligence STRATEGY_CLASS = PlayerIntelligence.MEDIUM;

    private Game game;

    public ReasonableOperativeAI(Game game)
    {
        this.game = game;
    }
    //region Methods

    /**
     * Gets the current clue given by the SpyMaster and inspects it to see what cards it suggests.
     * Randomly pick one and reveal it.
     */
    @Override
    public void execute() {
        game.setPhase(this.team.niceName() + " Operative");
        Clue clue = game.getCurrentClue();
        ArrayList<Card> cards = clue.getCards();
        int i = Game.RANDOM.nextInt(cards.size());
        Card card = cards.get(i);
        game.revealCard(card);
        if(game.getGuessesLeft() == 0) finished = true;
    }
}
