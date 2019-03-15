package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.player.OperativeAI;
import com.comp354pjb.codenames.model.player.Player;
import com.comp354pjb.codenames.model.player.PlayerType;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

import java.util.*;

/*
 * HasClueTest.java
 * Created by: Shereece A. A. Victor
 * Created on: 12th/03/19
 *
 * Contributors:
 * Shereece A. A. Victor
 */



public class HasClueTest {

	    @Test
	    public void eachCardshouldHaveClue() {
	        Game game = new Game();
	        Board board = game.getBoard();
	       
	        
	        // Get a collection of the revealed status of all cards in the game
	        ArrayList<Boolean> clues = new ArrayList<>();
	
	        for(int i = 0; i < 5; i++) {
	        
	            for(int j = 0; j < 5; j++) {
	               
	                clues.add(board.getCard(i, j).hasClue());
	            }
	        }

	        assertThat(clues, hasItem(true));
	    }
	}


