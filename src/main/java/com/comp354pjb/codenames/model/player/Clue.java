/*
 * Game.java
 * Created by: Christophe Savard
 * Created on: 06/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

/**
 * Wrapper around word/number pair for a clue
 */
public class Clue
{
    //region Fields
    public final String word;
    public final int value;
    //endregion

    //region Constructors
    /**
     * Creates a new Clue
     * @param word  Word of the clue
     * @param value Numeric value of the clue
     */
    public Clue(String word, int value)
    {
        this.word = word;
        this.value = value;
    }
    //endregion

    //region Methods
    /**
     * String representation of the whole clue
     * @return String of the clue, combining the word and value
     */
    @Override
    public String toString()
    {
        return this.word + " " + this.value;
    }
    //endregion
}
