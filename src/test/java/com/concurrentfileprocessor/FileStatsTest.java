package com.concurrentfileprocessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 * test class for FileStats.java file
 */
class FileStatsTest {

    /**
     * tests the refreshFileStats method
     * verifies that the method correctly resets all statistics
     * and returns a valid FileStats object
     */
    @Test
    void testRefreshFileStats() {
        // initialize test data with non-zero values
        ConcurrentHashMap<String, Integer> initialWordCount = new ConcurrentHashMap<>();
        initialWordCount.put("test", 5);
        AtomicInteger initialCharacterCount = new AtomicInteger(100);
        AtomicInteger initialLineCount = new AtomicInteger(10); 
        
        // create FileStats instance with initial test data
        FileStats stats = new FileStats(initialWordCount, initialCharacterCount, initialLineCount);
        
        // refresh the stats
        FileStats refreshedStats = stats.refreshFileStats();
        
        // verify the stats are refreshed
        assertEquals(0, refreshedStats.wordCount.size());
        assertEquals(0, refreshedStats.characterCount.get());
        assertEquals(0, refreshedStats.lineCount.get());
        
        // verify the refreshed object exists
        assertNotNull(refreshedStats);
    }
}
