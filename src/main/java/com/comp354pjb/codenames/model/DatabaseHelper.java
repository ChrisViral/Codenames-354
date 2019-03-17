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
 *
 * This is the database API, which means it's an important part of the Model of our MVC. The camelCase method is sometimes called by other classes to format strings.
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
     * Run queries that return a 1D array of strings.
     * @param  q The query you want to run on the database. Expected to be in SQLite3.
     * @param  colName The name of the column you're accessing.
     * @return Words stored in the DB
     * -----------
     * Revised by Mordechai Zirkind
     */
    public static String[] runSingleValQuery(String q, String colName)
    {
        // Get database address and create the array to return in the correct scope.
        String url = getURL();
        String[] toReturn;

        // Attempt to connect and create an SQL statement
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            //Getting the size of the array
            ResultSet size = stmt.executeQuery("select count(*) as size from ("+q+");");
            toReturn = new String[size.getInt("size")];
            //Run the query and store the result in query
            ResultSet query = stmt.executeQuery(q+";");

            int i = 0; // counter to iterate through the to return array
            while (query.next())
            {
                toReturn[i++] = toCamelCase(query.getString(colName)); // add everything
            }
        }
        catch (SQLException e)
        {
            // if there's an error print it and return an empty array
            Commander.log(e.getMessage());
            toReturn = new String[0];
        }
        //Return the query results formatted as an array of strings.
        return toReturn;
    }
    //endregion

    /**
     * Returns a single random codename from the database
     * @return One word picked from the Database
     * -----------
     * Created by Mordechai Zirkind
     */
    public static String getRandomCodename()
    {
        return runSingleValQuery("SELECT * FROM Codenames ORDER BY Random() LIMIT 1", "Codename")[0];
    }

    /**
     * Returns n codenames to be created into cards, they are randomly selected.
     * @param n the number of codenames you want.
     * @return selected words
     * -----------
     * Created by Mordechai Zirkind
     */
    public static String[] getRandomCodenames(int n)
    {
        String query = String.format("SELECT * FROM Codenames ORDER BY Random() LIMIT %d", n);
        return runSingleValQuery(query, "Codename");
    }

    /**
     * Returns a single random clue from the database
     * @return One word picked from the Database
     * -----------
     * Created by Mordechai Zirkind
     */
    public static String getRandomClue()
    {
        return runSingleValQuery("SELECT * FROM Clues ORDER BY Random() LIMIT 1", "Clue")[0];
    }

    /**
     * Returns all of the clues for a given codename.
     * @param codename the codename you want the clues to.
     * @return related clues.
     * -----------
     * Created by Mordechai Zirkind
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
     * -----------
     * Created by Mordechai Zirkind
     */
    public static String[] getCodenamesForClue(String clue)
    {
        String query = String.format("SELECT codename FROM Suggest WHERE clue='%s'", clue);
        return runSingleValQuery(query, "codename");
    }

    /**
     * Returns the board layout.
     * @return An array of strings of length two. The first is either R or B representing the first team. The second is a string of made up of the letters C, A, R, and B of length 25 each representing one card on the board.
     * -----------
     * Created by Mordechai Zirkind
     */
    public static String[] getBoardLayout() {

        // Get database address and create the array to return in the correct scope.
        String url = getURL();
        String[] toReturn = new String[2];

        // Attempt to connect and create an SQL statement
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            //Run the query and store the result in query
            ResultSet query = stmt.executeQuery("SELECT firstTeam, layout FROM BoardLayouts ORDER BY random() LIMIT 1;");
            //Put the query results where we need them.
            toReturn[0] = query.getString("firstTeam");
            toReturn[1] = query.getString("layout");
        }
        catch (SQLException e)
        {
            // if there's an error print it and return an empty array
            Commander.log(e.getMessage());
            toReturn = new String[0];
        }
        //Return the board layout
        return toReturn;
    }

    /**
     * Add the record of a completed game to the database.
     * @param redTeam The name of the red team. Intended to track humans by name.
     * @param blueTeam The name of the blue team. Intended to track humans by name.
     * @param numOfRounds The number of rounds it took to finish the game.
     * @param winner The team that one either red or blue.
     * @param assassinRevealed True if the assassin was revealed, otherwise false.
     * @param civilianRevealed The number of civilian cards revealed over the course of the game.
     * @param redTilesRevealed The number of red team cards revealed over the course of the game.
     * @param blueTilesRevealed The number of blue team cards revealed over the course of the game.
     * @return False if there's an SQL error, otherwise true.
     * -----------
     * Created by Mordechai Zirkind
     */
    public static boolean addGameToStats(String redTeam, String blueTeam, int numOfRounds, String winner, boolean assassinRevealed, int civilianRevealed, int redTilesRevealed, int blueTilesRevealed)
    {
        // Get database address and create the array to return in the correct scope.
        String url = getURL();
        String sql = "INSERT INTO GameHistory(blueTeamName, redTeamName, numberOfRounds, winner, civilianRevealed, assassinRevealed, redTilesRevealed, blueTilesRevealed) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        // Attempt to connect and create an SQL statement
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
     * Removes all entries from the game history that have a number of Rounds equal to -25.
     * Exists exclusively for testing purposes.
     * -----------
     * Created by Benjamin Therrien
     * Revised by Mordechai Zirkind
     * */
    public static boolean deleteTestEntry()
    {
        //Get the database URL and write the SQL query statement.
        String url = getURL();
        String sql = "DELETE FROM GameHistory WHERE GameHistory.numberOfRounds = ?;";

        //Attempt to creat the information and prepare the statement
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            //Add the values to the prepared statement.
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