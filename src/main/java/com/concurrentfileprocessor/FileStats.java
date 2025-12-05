package com.concurrentfileprocessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * data model class that holds processed file statistics
 * uses thread-safe collections for concurrent access
 */
public class FileStats {
    // store word counts for each word in the files
    public ConcurrentHashMap<String, Integer> wordCount;
    
    // counter for total characters across all files
    public AtomicInteger characterCount;
    
    // counter for total lines across all files
    public AtomicInteger lineCount;
    
    // total number of files processed
    public int numberOfFiles;
    

    /**
     * constructor
     * @param wordCount map of words and their counts
     * @param characterCount total character count
     * @param lineCount total line count
     */
    public FileStats(ConcurrentHashMap<String, Integer> wordCount, AtomicInteger characterCount, AtomicInteger lineCount) {
        this.wordCount = wordCount;
        this.characterCount = characterCount;
        this.lineCount = lineCount;
    }

    /**
     * resets all statistics to zero
     * @return the reset FileStats object
     */
    public FileStats refreshFileStats(){
        // create new instance with null parameters
        FileStats stats = new FileStats(null, null, null);
        
        // initialize all collections to empty/zero state
        stats.wordCount = new ConcurrentHashMap<>();
        stats.characterCount = new AtomicInteger(0);
        stats.lineCount = new AtomicInteger(0);
        
        return stats;
    }
}
