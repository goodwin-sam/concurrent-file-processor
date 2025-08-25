package com.concurrentfileprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.outputFilename;
import com.concurrentfileprocessor.processor.OutputWriter;

/**
 * test class for OutputWriter.java file
 */
public class OutputWriterTest {
    private File tempFile;
    private ConcurrentHashMap<String, Integer> wordCount;
    private AtomicInteger totalCharacterCount;
    
    // file stats object for testing
    private FileStats fileStats;

    /**
     * sets up test environment before each test
     * @throws IOException if file creation fails
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() throws IOException {
        // create temporary test file
        tempFile = File.createTempFile("outputtest", ".txt");
        
        // initialize test data
        wordCount = new ConcurrentHashMap<>();
        wordCount.put("hello", 2);
        wordCount.put("world", 1);
        totalCharacterCount = new AtomicInteger(15);
        outputFilename = "test_output_file.txt";
    }

    /**
     * cleans up test environment after each test
     */
    @AfterEach
    @SuppressWarnings("unused")
    void tearDown() {
        // delete temporary test file
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
        
        // delete output file
        if (tempFile != null) {
            File outputFile = new File(tempFile.getParent(), outputFilename);
            if (outputFile.exists()) {
                outputFile.delete();
            }
        }
    }

    /**
     * tests the outputStatsToFile method
     * @throws IOException if file reading fails
     */
    @Test
    void testOutputWordsToFile() throws IOException {
        // create file stats with test data
        fileStats = new FileStats(wordCount, totalCharacterCount, new AtomicInteger(0));
        fileStats.numberOfFiles = 2;
        
        // write stats to output file
        String outputDir = tempFile.getParent();
        OutputWriter.outputStatsToFile(outputDir, outputFilename, fileStats);
        
        // read output file contents
        File actualOutputFile = new File(outputDir, outputFilename);
        List<String> lines = Files.readAllLines(actualOutputFile.toPath());
        
        // verify output structure and content
        assertEquals(6, lines.size());
        assertTrue(lines.get(0).startsWith("Number of files:"));
        assertEquals("Total character count: 15", lines.get(1));
        assertEquals("Total line count: 0", lines.get(2));
        assertTrue(lines.get(3).startsWith("Total word count: 2"));
        assertTrue(lines.contains("hello: 2"));
        assertTrue(lines.contains("world: 1"));
    }

    /**
     * tests exception handling in outputStatsToFile method
     */
    @Test
    void testOutputStatsToFileExceptionHandling() {
        // invalid directory path to trigger exception
        String invalidPath = "/invalid/path/that/does/not/exist";
        OutputWriter.outputStatsToFile(invalidPath, outputFilename, fileStats);
    }
} 
