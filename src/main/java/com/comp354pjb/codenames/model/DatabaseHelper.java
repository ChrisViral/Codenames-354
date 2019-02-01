package com.comp354pjb.codenames.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper
{
    private static final String SQLITE_HEADER = "jdbc:sqlite:";

    private static String getURL()
    {
        //get path to you current root directory, then appends db/codenames.db at the back, and
        //jbdc:sqlite at the front to complete a correct
        Path currentDir = Paths.get("db/codenames.db").toAbsolutePath();
        return SQLITE_HEADER + currentDir.toString();
    }

    public static boolean checkConnection()
    {
        String url = getURL();
        Connection conn = null;
        boolean success = true;
        try
        {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
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
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
                success = false;
            }
        }
        return success;
    }

    public static String[] fetchDatabase()
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
        return new String[0];
    }

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
            String word = words.get(i);
            result[i] = Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }
        return result;
    }
}