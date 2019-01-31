package com.comp354pjb.codenames;

import com.comp354pjb.codenames.Model.DatabaseHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseHelperTest
{

    @Test
    public void shouldConnectToDB()
    {
        assertTrue(DatabaseHelper.checkConnection());
    }

    @Test
    public void getWordsShouldReturnAllWords()
    {
        assertEquals(260, DatabaseHelper.fetchDatabase().length);
    }
}
