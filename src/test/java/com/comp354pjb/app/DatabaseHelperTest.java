package com.comp354pjb.app;

import com.comp354pjb.app.Model.DatabaseHelper;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertEquals(260, DatabaseHelper.getWords().length);
    }
}
