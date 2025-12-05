package com.concurrentfileprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.concurrentfileprocessor.processor.FileMetricsCollector;

/**
 * test class for FileMetricsCollector.java file
 */
class FileMetricsCollectorTest {
    // temporary test file
    private File tempFile;
    
    // word count map
    private ConcurrentHashMap<String, Integer> wordCount;
    
    // total character count
    private AtomicInteger totalCharacterCount;

    /**
     * sets up test environment before each test
     * creates temporary test file with sample content
     * @throws IOException if file creation fails
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() throws IOException {
        // create temporary test file with sample content
        tempFile = File.createTempFile("testfile", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Hello world hello");
        }
        
        // initialize collections
        wordCount = new ConcurrentHashMap<>();
        totalCharacterCount = new AtomicInteger(0);
    }

    /**
     * cleans up test environment after each test
     */
    @AfterEach
    @SuppressWarnings("unused")
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * tests the countFileComponents method
     * verifies word counting, character counting, and line counting functionality
     * @throws FileNotFoundException if the test file cannot be read
     */
    @Test
    void testCountFileComponents() throws FileNotFoundException {
        // process the test file and collect metrics
        FileStats fileStats = new FileStats(wordCount, totalCharacterCount, new AtomicInteger(0));
        FileMetricsCollector.countFileComponents(tempFile, fileStats);
        
        // verify word counts are correct
        assertEquals(2, wordCount.size());
        assertEquals(2, wordCount.get("hello"));
        assertEquals(1, wordCount.get("world"));
        
        // verify total character count is correct
        assertEquals(15, totalCharacterCount.get()); // "hello" (5) + "world" (5) + "hello" (5)
        
        // verify line count is correct
        assertEquals(1, fileStats.lineCount.get());
    }

    /**
     * tests thread safety of character counting across multiple files
     * verifies that concurrent processing produces correct total character count
     * @throws Exception if test execution fails
     */
    @Test
    void testTotalCharacterCountThreadSafety() throws Exception {
        // create two temporary test files with different content
        File tempFile1 = File.createTempFile("testfile1", ".txt");
        File tempFile2 = File.createTempFile("testfile2", ".txt");
        try (PrintWriter out1 = new PrintWriter(tempFile1); PrintWriter out2 = new PrintWriter(tempFile2)) {
            out1.println("Alpha beta gamma"); // 5 + 4 + 5 = 14
            out2.println("Delta epsilon");    // 5 + 7 = 12
        }
        
        // initialize shared collections for concurrent testing
        ConcurrentHashMap<String, Integer> wc = new ConcurrentHashMap<>();
        AtomicInteger tc = new AtomicInteger(0);
        
        // create and start two threads to process files concurrently
        Thread t1 = new Thread(() -> {
            try { 
                FileStats stats1 = new FileStats(wc, tc, new AtomicInteger(0));
                FileMetricsCollector.countFileComponents(tempFile1, stats1); 
            } catch (FileNotFoundException ignored) {}
        });
        Thread t2 = new Thread(() -> {
            try { 
                FileStats stats2 = new FileStats(wc, tc, new AtomicInteger(0));
                FileMetricsCollector.countFileComponents(tempFile2, stats2); 
            } catch (FileNotFoundException ignored) {}
        });
        
        // start both threads and wait for completion
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        // verify total character count is correct after concurrent processing
        assertEquals(26, tc.get()); // 14 + 12
        
        tempFile1.delete();
        tempFile2.delete();
    }
}
