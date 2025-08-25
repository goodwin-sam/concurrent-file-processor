package com.concurrentfileprocessor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.concurrentfileprocessor.runner.HeadlessRunner;

/**
 * test class for HeadlessRunner.java file
 */
class HeadlessRunnerTest {
    private File tempDir;
    
    // test text files
    private File txtFile1;
    private File txtFile2;
    
    // test non-text file to verify filetype filtering
    private File nonTxtFile;

    /**
     * sets up test environment before each test
     * creates temporary directory and testing files
     * @throws IOException if file creation fails
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() throws IOException {
        // create temporary working directory
        tempDir = File.createTempFile("testdir", "");
        tempDir.delete();
        tempDir.mkdir();
        
        // create first test text file
        txtFile1 = new File(tempDir, "test1.txt");
        try (PrintWriter writer = new PrintWriter(txtFile1)) {
            writer.println("test content");
        }
        
        // create second test text file
        txtFile2 = new File(tempDir, "test2.txt");
        try (PrintWriter writer = new PrintWriter(txtFile2)) {
            writer.println("more content");
        }
        
        // create non-text file to test filtering
        nonTxtFile = new File(tempDir, "test.doc");
        try (PrintWriter writer = new PrintWriter(nonTxtFile)) {
            writer.println("document content");
        }
    }

    /**
     * cleans up test environment after each test
     */
    @AfterEach
    @SuppressWarnings("unused")
    void tearDown() {
        if (txtFile1 != null && txtFile1.exists()) txtFile1.delete();
        if (txtFile2 != null && txtFile2.exists()) txtFile2.delete();
        if (nonTxtFile != null && nonTxtFile.exists()) nonTxtFile.delete();
        if (tempDir != null && tempDir.exists()) tempDir.delete();
    }

    /**
     * tests the getInputFiles method
     * verifies text file discovery and filtering for non-txt files
     * @throws Exception if reflection or method invocation for private method fails
     */
    @Test
    void testGetInputFiles() throws Exception {
        // get private method
        java.lang.reflect.Method method = HeadlessRunner.class.getDeclaredMethod("getInputFiles", File.class);
        method.setAccessible(true);
        
        // invoke method
        @SuppressWarnings("unchecked")
        List<File> result = (List<File>) method.invoke(null, tempDir);
        
        // verify the amount of files and if they are txt files
        assertEquals(2, result.size());
        assertTrue(result.contains(txtFile1));
        assertTrue(result.contains(txtFile2));
    }

    /**
     * tests the getOutputFilename method
     * verifies custom and default filename handling
     * @throws Exception if reflection or method invocation fails
     */
    @Test
    void testGetOutputFilename() throws Exception {
        // get private method
        java.lang.reflect.Method method = HeadlessRunner.class.getDeclaredMethod("getOutputFilename");
        method.setAccessible(true);
        
        // test custom filename
        System.setIn(new java.io.ByteArrayInputStream("custom_output.txt\n".getBytes()));
        String customResult = (String) method.invoke(null);
        assertEquals("custom_output.txt", customResult);
        
        // test default filename (empty input)
        System.setIn(new java.io.ByteArrayInputStream("\n".getBytes()));
        String defaultResult = (String) method.invoke(null);
        assertEquals("processed_file_stats.txt", defaultResult);
    }
}
