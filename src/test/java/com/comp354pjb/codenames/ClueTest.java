package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Board;

import org.junit.Test;
import static org.junit.Assert.*;

/*
 * ClueTest.java
 * Created by: Shereece A. A. Victor
 * Created on: 12th/03/19
 *
 * Contributors:
 * Shereece A. A. Victor
 */



public class ClueTest {

	/**
	 * Tests that each card has one clue
	 *
	 * by Shereece A.A. Victor
	 */
	@Test
	public void eachCardHasOneClue() {

		Game game = new Game();
		Board board = game.getBoard();

		int hascluecount=0;

		for(int i = 0; i < 5; i++) {

			for(int j = 0; j < 5; j++) {

				if(!board.getCard(i, j).getClues().isEmpty()){
					hascluecount++;
				}
				else{
					System.out.println("The word "+board.getCard(i,j).getWord().toUpperCase()+" doesn't have an associated clue.\n");
				}

			}
		}

		assertEquals(hascluecount, 25);

	}

	/**
	 * Tests that each card has more than one clue.
	 *
	 * by Shereece A.A. Victor
	 */
	@Test
	public void eachCardHasTwoClues() {

		Game game = new Game();
		Board board = game.getBoard();

		// Get a collection of the revealed status of all cards in the game
		int hastwoclues=0;

		for(int i = 0; i < 5; i++) {

			for(int j = 0; j < 5; j++) {

				if(board.getCard(i,j).getClues().size() >=2){
					hastwoclues++;
				}
				else{
					System.out.println("The word "+board.getCard(i,j).getWord().toUpperCase()+" doesn't have at least two associated clues.\n");
				}
			}
		}

		assertTrue(hastwoclues>=25);

	}



}


