package com.concurrentfileprocessor.processor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.concurrentfileprocessor.FileStats;

/**
 * task class that processes individual files to collect file statistics
 * implements Runnable for concurrent execution in thread pools
 */
public class FileMetricsCollector implements Runnable {
    // file to be processed by this collector
    private final File file;
    
    // shared statistics object for results across threads
    private final FileStats fileStats;

    /**
     * constructor
     * @param file the file to process
     * @param fileStats shared statistics object
     */
    public FileMetricsCollector(File file, FileStats fileStats) {
        this.file = file;
        this.fileStats = fileStats;
    }

    /**
     * run method called by thread delegator to process the file
     * delegates to static method for actual processing
     */
    @Override
    public void run() {
        try {
            countFileComponents(file, fileStats);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * processes a file and updates statistics
     * reads file line by line, counts words, characters and lines
     * @param file the file to process
     * @param fileStats statistics object to update
     * @throws FileNotFoundException if file cannot be found or read
     */
    public static void countFileComponents(File file, FileStats fileStats) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            int fileLineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileLineCount++;
                
                String[] words = line.split("\\s+");
                for (String word : words) {
                    String cleanWord = word.toLowerCase().replaceAll("[^a-z]", "");
                    if (!cleanWord.isEmpty()) {
                        fileStats.wordCount.merge(cleanWord, 1, Integer::sum);
                        fileStats.characterCount.addAndGet(cleanWord.length());
                    }  
                }
            }
            fileStats.lineCount.addAndGet(fileLineCount);
        }
    }
}
