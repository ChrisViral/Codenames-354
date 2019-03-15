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

public class ReasonableOperativeAI implements IPlayer {
    //region Methods
    /**
     * Plays the dumb Operative AI's turn
     * Randomly determine which card to pick, checking that that card has not been revealed before it is chosen
     * @param player The player to play this turn on
     */
    @Override
    public void playTurn(Player player) {
        Game game = player.game;
        player.game.setPhase(player.teamName + " Operative");
        Clue clue = game.getCurrentClue();
        ArrayList<Card> cards = clue.getCards();
        int i = Game.RANDOM.nextInt(cards.size());
        int x = cards.get(i).getX();
        int y = cards.get(i).getY();
        Card card = game.getBoard().getCard(x, y);
        game.revealCard(card);
    }
}
