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

public class MemoryOperativeAI implements IPlayer {
    private String previousClue;

    //region Methods

    /**
     * TODO
     * @param player Player who's using this strategy
     */
    @Override
    public void playTurn(Player player) {
        Game game = player.game;
        player.game.setPhase(player.teamName + " Operative");
        Clue clue = game.getCurrentClue();
        ArrayList<Card> cards = clue.getCards();
        int i = Game.RANDOM.nextInt(cards.size());
        Card card = cards.get(i);
        game.revealCard(card);
        // We've made our choice so we can look at the card now
        if(!player.getTeam().getCardType().equals(card.getType()))
        {
            // Our turn is over and we didn't succeed in guessing all the right codenames;
            previousClue = clue.word;
        }
        else
        {
            previousClue = null;
        }

        if(previousClue != null && game.getGuessesLeft() == clue.value)
        {
            clue = player.game.getSuggestionMap().getClue(previousClue);
            cards = clue.getCards();
            i = Game.RANDOM.nextInt(cards.size());
            card = cards.get(i);
            game.revealCard(card);
        }
    }
}
