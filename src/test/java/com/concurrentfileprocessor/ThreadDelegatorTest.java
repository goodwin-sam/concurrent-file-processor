package com.concurrentfileprocessor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.fileStats;
import static com.concurrentfileprocessor.ConcurrentFileProcessor.inputFiles;
import com.concurrentfileprocessor.processor.ThreadDelegator;

/**
 * test class for ThreadDelegator.java file
 */
class ThreadDelegatorTest {
    private List<File> tempFiles;

    /**
     * sets up test environment before each test
     * @throws IOException if file creation fails
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() throws IOException {
        // create first test file with sample content
        tempFiles = new ArrayList<>();
        File file1 = File.createTempFile("threadtest1", ".txt");
        try (PrintWriter out = new PrintWriter(file1)) {
            out.println("apple banana apple");
        }
        tempFiles.add(file1);
        
        // create second test file with different content
        File file2 = File.createTempFile("threadtest2", ".txt");
        try (PrintWriter out = new PrintWriter(file2)) {
            out.println("banana orange apple");
        }
        tempFiles.add(file2);
        
        // initialize global state for testing
        inputFiles = tempFiles;
        fileStats = new FileStats(new ConcurrentHashMap<>(), new AtomicInteger(0), new AtomicInteger(0));
    }

    /**
     * cleans up test environment after each test
     */
    @AfterEach
    @SuppressWarnings("unused")
    void tearDown() {
        // delete all temporary test files
        for (File file : tempFiles) {
            if (file.exists()) file.delete();
        }
    }

    /**
     * tests the delegateTasks method
     */
    @Test
    void testDelegateTasksCountsWordsConcurrently() {
        // delegate tasks to concurrent threads
        ThreadDelegator.delegateTasks();
        
        // verify word counts from concurrent processing
        ConcurrentHashMap<String, Integer> wordCount = fileStats.wordCount;
        assertEquals(3, wordCount.size());
        assertEquals(3, wordCount.get("apple"));
        assertEquals(2, wordCount.get("banana"));
        assertEquals(1, wordCount.get("orange"));
    }
} 
