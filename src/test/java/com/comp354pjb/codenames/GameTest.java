/*
 * GameTest.java
 * Created by: Michael Wilgus
 * Created on: 07/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.Game;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameTest {
    @Test
    public void PickingAssassinShouldEndTheGame()
    {
        Game game = new Game();
        game.setAssassinRevealed(true);
        assertTrue(game.checkWinner());
    }
}
