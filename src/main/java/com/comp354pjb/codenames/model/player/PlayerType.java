/*
 * FileName.java
 * Created by: Christophe Savard
 * Created on: 30/01/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.DatabaseHelper;
import com.comp354pjb.codenames.model.board.CardType;

/**
 * Enum for Player type, Red or Blue
 */
public enum PlayerType
{
    RED(CardType.RED),
    BLUE(CardType.BLUE);

    //region Properties
    private final CardType cardType;
    /**
     * Get the Card type associated to this Player type
     * @return The associated CardType
     */
    public CardType getCardType()
    {
        return this.cardType;
    }

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
     * Creates a new PlayerType wrapper around a CardType
     * @param cardType CardType this PlayerType represents
     */
    PlayerType(CardType cardType)
    {
        this.cardType = cardType;
        this.camelCasedName = DatabaseHelper.toCamelCase(name());
    }
    //endregion
}
