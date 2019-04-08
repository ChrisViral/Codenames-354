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

import com.comp354pjb.codenames.model.DatabaseHelper;

/**
 * Type of card
 */
public enum CardType
{
    RED,
    BLUE,
    CIVILIAN,
    ASSASSIN;

    //region Properties
    private final String camelCasedName;
    /**
     * Nicely formatted String version of the name of this enum member
     * @return Enum member name
     */
    public String niceName()
    {
        return this.camelCasedName;
    }
    //endregion

    //region Constructors
    /**
     * Creates a new CardType
     */
    CardType()
    {
        this.camelCasedName = DatabaseHelper.toCamelCase(name());
    }
    //endregion

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