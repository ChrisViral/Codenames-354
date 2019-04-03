/*
 * PlayerIntelligence.java
 * Created by: Rezza-Zairan Zaharin
 * Created on: 15/03/19
 *
 * Contributors:
 * Michael Wilgus
 * Christophe Savard
 */

package com.comp354pjb.codenames.model.player;

/**
 * Simple designations for the different AIs
 */
public enum PlayerIntelligence
{
    DUMB,
    MEDIUM,
    SMART,
    HUMAN;

    //region Static methods
    /**
     * Parses a string value into a given PlayerIntelligence
     * @param value Value to parse
     * @return The parsed PlayerIntelligence, or null if invalid
     */
    public static PlayerIntelligence parse(String value)
    {
        //Switch the value
        switch (value)
        {
            case "DUMB":
                return DUMB;

            case "MEDIUM":
            case "RISKY":
                return MEDIUM;

            case "SMART":
            case "SAFE":
                return SMART;

            case "HUMAN":
                return HUMAN;
        }

        //If none is found, return null
        return null;
    }
    //endregion
}
