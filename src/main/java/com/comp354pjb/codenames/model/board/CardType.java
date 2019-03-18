/*
 * CardType.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.board;

/**
 * Type of card
 */
public enum CardType
{
    RED,
    BLUE,
    CIVILIAN,
    ASSASSIN;

    //region Static methods

    /**
     * Parses a char value to the corresponding CardType member
     * @param value Char to parse, must be R, B, C, or A
     * @return The corresponding CardType
     * @throws EnumConstantNotPresentException If the supplied character is invalid
     */
    public static CardType parse(char value)
    {
        //Switch over the parameter, case insensitive
        switch (Character.toUpperCase(value))
        {
            case 'R':
                return RED;
            case 'B':
                return BLUE;
            case 'C':
                return CIVILIAN;
            case 'A':
                return ASSASSIN;
        }

        //If nothing is found, throw
        throw new EnumConstantNotPresentException(CardType.class, Character.toString(value));
    }
    //endregion
}