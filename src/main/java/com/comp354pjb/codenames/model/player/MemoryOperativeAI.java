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

public class MemoryOperativeAI implements Strategy {
    private String previousClue;

    //region Methods

    /**
     * TODO
     * @param player Player who's using this strategy
     */
    @Override
    public void execute(Player player) {
        Game game = player.game;
        player.game.setPhase(player.teamName + " Operative");

        Clue clue = game.getCurrentClue();
        ArrayList<Card> cards;
        int i;
        Card card;

        if(previousClue != null && game.getGuessesLeft() == 0) {
            if(previousClue.equals(clue.word))
            {
                game.setEndCurrentTurn(true);
                return;
            }
            System.out.println("Using " + previousClue + " if it makes sense");
            clue = player.game.getSuggestionMap().getClue(previousClue);
            if(clue == null)
            {
                game.setEndCurrentTurn(true);
                return;
            }
            cards = clue.getCards();
            i = Game.RANDOM.nextInt(cards.size());
            card = cards.get(i);
            previousClue = null;
            game.setEndCurrentTurn(true);
        } else {
            cards = clue.getCards();
            i = Game.RANDOM.nextInt(cards.size());
            card = cards.get(i);
        }

        game.revealCard(card);

        if(game.getGuessesLeft() <= 0 && previousClue == null) {
            game.setEndCurrentTurn(true);
        }

        // We've made our choice so we can look at the card now
        if(!player.getTeam().getCardType().equals(card.getType()))
        {
            // Our turn is over and we didn't succeed in guessing all of the right codenames;
            previousClue = clue.word;
            System.out.println("Remembering " + previousClue);
            game.setEndCurrentTurn(true);
        }
    }
}
