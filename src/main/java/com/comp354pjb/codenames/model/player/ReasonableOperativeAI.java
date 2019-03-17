/*
 * ReasonableOperativeAI.java
 * Created by: Michael Wilgus
 * Created on: 14/03/19
 *
 * Contributors:
 * Michael Wilgus
 *
 * Description:
 * Gets the current clue given by the SpyMaster and inspects it to see what cards it suggests.
 * Randomly pick one and reveal it.
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

    @Override
    public void execute() {
        game.setPhase(this.team.niceName() + " Operative");

        //Get the latest clue given by the SpyMaster
        Clue clue = game.getCurrentClue();

        // Get all the cards that can possibly be associated with the given clue
        ArrayList<Card> cards = clue.getCards();

        // Pick randomly among the cards available
        // Note: It is up to the SpyMaster to give a good clue.
        // At this point we are free to assume that any card suggested
        // will be a good choice
        int i = Game.RANDOM.nextInt(cards.size());
        Card card = cards.get(i);

        // Pick a Card
        game.revealCard(card);

        // We used up our guesses so we are done
        if(game.getGuessesLeft() == 0) finished = true;
    }
}
