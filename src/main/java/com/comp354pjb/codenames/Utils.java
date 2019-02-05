/*
 * Utils.java
 * Created by: Christophe Savard
 * Created on: 05/02/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames;

/**
 * Collection of utility methods, this class cannot be instantiated
 * Any general use methods should go in here
 */
public final class Utils
{
    //region Constructors
    /**
     * Prevents instantiation
     */
    private Utils() { }
    //endregion

    //region Static methods
    /**
     * Transforms any underscore separated string into a CamelCased string
     * @param s String to transform
     * @return  CamelCased string
     */
    public static String toCamelCase(String s)
    {
        String[] splits = s.split("_");
        for (int i = 0; i < splits.length; i++)
        {
            String split = splits[i];
            splits[i] = Character.toUpperCase(split.charAt(0)) + split.substring(1).toLowerCase();
        }

        return String.join(" ", splits);
    }
    //endregion
}
