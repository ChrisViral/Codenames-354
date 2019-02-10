/*
 * DatabaseHelperTest.java
 * Created by: Michael Wilgus
 * Created on: 30/01/19
 *
 * Contributors:
 * Michael Wilgus
 * Christophe Savard
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.DatabaseHelper;
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
