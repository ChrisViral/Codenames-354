/*
 * DatabaseHelper.java
 * Created by: Benjamin Therrien
 * Created on: 28/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * Christophe Savard
 * Mordechai Zirkind
 */

package com.comp354pjb.codenames.model;

import com.comp354pjb.codenames.commander.Commander;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

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

    //region Constructors
    /**
     * Prevents class instantiation
     */
    private DatabaseHelper() { }
    //endregion

    //region Static methods
    //region Support methods
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
     * Run queries that return an array of a single value.
     * @param  q The query you want to run on the database. Expected to be in SQLite3.
     * @param  colName The name of the column you're accessing.
     * @return Words stored in the DB
     */
    public static String[] runSingleValQuery(String q, String colName)
    {
        String url = getURL();
        String[] toReturn;

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            //Getting the size of the array
            ResultSet size = stmt.executeQuery("select count(*) as size from ("+q+");");
            toReturn = new String[size.getInt("size")];
            //Getting all the words in the database
            ResultSet query = stmt.executeQuery(q+";");
            int i = 0;
            while (query.next())
            {
                toReturn[i++] = toCamelCase(query.getString(colName));
            }
        }
        catch (SQLException e)
        {
            Commander.log(e.getMessage());
            toReturn = new String[0];
        }
        //Return the database
        return toReturn;
    }
    //endregion

    /**
     * Returns a single random codename from the database
     * @return One word picked from the Database
     */
    public static String getRandomCodename()
    {
        return runSingleValQuery("SELECT * FROM Codenames ORDER BY Random() LIMIT 1", "Codename")[0];
    }

    /**
     * Returns n codenames to be created into cards, they are randomly selected.
     * @param n the number of codenames you want.
     * @return selected words
     */
    public static String[] getRandomCodenames(int n)
    {
        String query = String.format("SELECT * FROM Codenames ORDER BY Random() LIMIT %d", n);
        return runSingleValQuery(query, "Codename");
    }

    /**
     * Returns a single random clue from the database
     * @return One word picked from the Database
     */
    public static String getRandomClue()
    {
        return runSingleValQuery("SELECT * FROM Clues ORDER BY Random() LIMIT 1", "Clue")[0];
    }

    /**
     * Returns all of the clues for a given codename.
     * @param codename the codename you want the clues to.
     * @return related clues.
     */
    public static String[] getCluesForCodename(String codename)
    {
        String query = String.format("SELECT clue FROM Suggest WHERE codename='%s'", codename);
        return runSingleValQuery(query, "clue");
    }

    /**
     * Returns all of the codenames for a given clue.
     * @param clue Clue to test against
     * @return related codenames.
     */
    public static String[] getCodenamesForClue(String clue)
    {
        String query = String.format("SELECT codename FROM Suggest WHERE clue='%s'", clue);
        return runSingleValQuery(query, "codename");
    }

    /**
     * Function to give the board layout.
     * @return An array of strings of length two. The first is effectively a char either R or B representing the first team. The second is a string of the chars CARB of length 25 each representing one card on the board.
     */
    public static String[] getBoardLayout() {

        String url = getURL();
        String[] toReturn;

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            //Getting the size of the array
            toReturn = new String[2];
            //Getting all the words in the database
            ResultSet query = stmt.executeQuery("SELECT firstTeam, layout FROM BoardLayouts ORDER BY random() LIMIT 1;");
            int i = 0;
            while (query.next())
            {
                toReturn[0] = query.getString("firstTeam");
                toReturn[1] = query.getString("layout");
            }
        }
        catch (SQLException e)
        {
            Commander.log(e.getMessage());
            toReturn = new String[0];
        }
        //Return the database
        return toReturn;
    }

    public static boolean addGameToStats(String redTeam, String blueTeam, int numOfRounds, String winner, boolean assassinRevealed, int civilianRevealed, int redTilesRevealed, int blueTilesRevealed)
    {
        String url = getURL();
        //raw sql
        String sql = "INSERT INTO GameHistory(blueTeamName, redTeamName, numberOfRounds, winner, civilianRevealed, assassinRevealed, redTilesRevealed, blueTilesRevealed) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            //Prepare the statement
            stmt.setString(1, blueTeam);
            stmt.setString(2, redTeam);
            stmt.setInt(3, numOfRounds);
            stmt.setString(4, winner);
            stmt.setInt(5, civilianRevealed);
            stmt.setBoolean(6, assassinRevealed);
            stmt.setInt(7, redTilesRevealed);
            stmt.setInt(8, blueTilesRevealed);

            //Execute
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            //False if there's an error
            Commander.log(e.getMessage());
            return false;
        }

        //True if it worked.
        return true;
    }

    /**
     * Removes all entries from the game history that have a number of Rounds equal to -25
     * */
    public static boolean deleteTestEntry()
    {
    String url = getURL();
    //raw sql
    String sql = "DELETE FROM GameHistory WHERE GameHistory.numberOfRounds = ?;";

    try (Connection conn = DriverManager.getConnection(url); PreparedStatement stmt = conn.prepareStatement(sql))
    {
        stmt.setInt(1,-25);
        stmt.executeUpdate();
    }
    catch (SQLException e)
    {
        //False if there's an error
        Commander.log(e.getMessage());
        return false;
    }

    //True if it worked.
    return true;
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