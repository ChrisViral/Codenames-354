package com.comp354pjb.app.Model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseHelper
{

    private static String getURL()
    {
        //get path to you current root directory, then appends db/codenames.db at the back, and
        //jbdc:sqlite at the front to complete a correct
        Path currentDir = Paths.get("db/codenames.db");
        String path = currentDir.toAbsolutePath() + "";
        return "jdbc:sqlite:" + path;
    }

    public static void checkConnection()
    {

        String url = getURL();
        Connection conn = null;
        try
        {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
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
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String[] getWords()
    {
        String url = getURL();
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            //getting the size of the array
            ResultSet size = stmt.executeQuery("select count(wordValue) as size from word;");
            String[] words = new String[size.getInt("size")];
            //getting all the words in the database
            ResultSet query = stmt.executeQuery("select * from word;");
            int count = 0;
            while (query.next())
            {
                words[count] = query.getString("wordValue");
                count++;
            }
            return words;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return new String[]{ "error" };
    }

}