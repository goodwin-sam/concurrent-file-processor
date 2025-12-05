package com.concurrentfileprocessor;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * test class for ConcurrentFileProcessor.java file
 */
class ConcurrentFileProcessorTest {
    
    /**
     * tests the getDefaultDownloadsPath method
     */
    @Test
    void testGetDefaultDownloadsPath() {
        // get the default downloads path from the processor
        String result = ConcurrentFileProcessor.getDefaultDownloadsPath();
        
        // verify a path is returned
        assertTrue(result.length() > 0);
        
        // verify path contains the home directory
        String home = System.getProperty("user.home");
        assertTrue(result.contains(home));
        
        // verify path is the downloads directory or home directory as fallback
        assertTrue(result.equals(home + File.separator + "Downloads") || result.equals(home));
    }
}
