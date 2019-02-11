/*
 * DatabaseHelper.java
 * Created by: Benjamin Therrien
 * Created on: 28/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * Christophe Savard
 */

package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.commander.Commander;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SQLite Database helper class, cannot be instantiated
 */
public final class DatabaseHelper
{
    //region Constants
    /**
     * SQLite Header ID
     */
    private static final String SQLITE_HEADER = "jdbc:sqlite:";
    //endregion

    //region Static fields
    private static String[] database;
    //endregion

    //region Constructors
    /**
     * Prevents class instantiation
     */
    private DatabaseHelper() { }
    //endregion

    //region Static methods
    /**
     * get URL of Database
     * @return returns the header + absolute path to DB
     */
    private static String getURL()
    {
        //Get path to you current root directory, then appends db/codenames.db at the back, and
        //jdbc:sqlite at the front to complete a correct
        Path currentDir = Paths.get("db/codenames.db").toAbsolutePath();
        return SQLITE_HEADER + currentDir.toString();
    }

    /**
     * Establishes connection between program and database.
     * @return boolean to whether the connection has been established
     */
    public static boolean checkConnection()
    {
        String url = getURL();
        Connection conn = null;
        boolean success = true;
        try
        {
            //Create a connection to the database
            conn = DriverManager.getConnection(url);
            Commander.log("Connection to SQLite has been established.");
        }
        catch (SQLException e)
        {
            Commander.log(e.getMessage());
            e.printStackTrace();
            success = false;
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                Commander.log(e.getMessage());
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    /**
     * Fetches information from database. Specifically the words and hints.
     * The results from this operation are cached and will be returned again on next method call
     * @return Words stored in the DB
     */
    public static String[] fetchDatabase()
    {
        //Only fetch the database if it isn't already cached
        if (database == null)
        {
            String url = getURL();
            try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
            {
                //Getting the size of the array
                ResultSet size = stmt.executeQuery("select count(wordValue) as size from word;");
                database = new String[size.getInt("size")];
                //Getting all the words in the database
                ResultSet query = stmt.executeQuery("select * from word;");
                int i = 0;
                while (query.next())
                {
                    database[i++] = toCamelCase(query.getString("wordValue"));
                }
            }
            catch (SQLException e)
            {
                Commander.log(e.getMessage());
                database = new String[0];
            }
        }
        //Return the database
        return database;
    }

    /**
     * Returns a single random word from the database
     * @return One word picked from the Database
     */
    public static String getRandomWord()
    {
        String[] database = fetchDatabase();
        return database[(int)(Math.random() * database.length)];
    }

    /**
     * Select 25 words to be created into cards, they are randomly selected.
     * @param n
     * @return selected words
     */
    public static String[] selectWords(int n)
    {
        List<String> words = Arrays.asList(fetchDatabase());
        if (words.size() < n)
        {
            return new String[0];
        }

        Collections.shuffle(words);
        String[] result = new String[n];
        for (int i = 0; i < n; i++)
        {
            result[i] = words.get(i);
        }
        return result;
    }

    /**
     * Transforms any dash, underscore, or space separated string into a CamelCased string
     * @param s String to transform
     * @return  CamelCased string
     */
    public static String toCamelCase(String s)
    {
        //Split on dashes, underscores, or spaces
        String[] splits = s.split("[-_ ]");
        for (int i = 0; i < splits.length; i++)
        {
            String split = splits[i];
            splits[i] = Character.toUpperCase(split.charAt(0)) + split.substring(1).toLowerCase();
        }

        return String.join(" ", splits);
    }
    //endregion
}