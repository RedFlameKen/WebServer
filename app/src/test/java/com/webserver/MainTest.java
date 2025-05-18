package com.webserver;

import org.junit.Test;

import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

import static org.junit.Assert.*;

public class MainTest {

    @Test 
    public void testLog() {
        assertNotNull("app should have a greeting", Logger.getLog("What the shit man!", LogLevel.CRITICAL));
    }
}
