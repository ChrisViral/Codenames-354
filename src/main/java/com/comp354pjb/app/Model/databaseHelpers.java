package com.comp354pjb.app.Model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseHelpers {

public static void connect() {
    Connection conn = null;
    try {
    	
    	Path currentDir = Paths.get("db/database.db");
    	System.out.println(currentDir.toAbsolutePath());
    	String path= currentDir.toAbsolutePath()+ "";
    	
        String url = "jdbc:sqlite:"+path;
        // create a connection to the database
        conn = DriverManager.getConnection(url);
        
        System.out.println("Connection to SQLite has been established.");
        
    }  catch (SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

public static void query(String url, String query)
{	
	try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {
    stmt.execute(query);
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
}

}
