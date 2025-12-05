package com.concurrentfileprocessor.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.concurrentfileprocessor.FileStats;

/**
 * task class for writing file processing results to output file
 * handles formatting and writing of statistics to text files
 */
public class OutputWriter {
    /**
     * writes all collected file statistics to an output file
     * creates a formatted report with file counts, character counts, line counts and word frequencies
     * @param outputFilePath directory where output file will be created
     * @param outputFilename name of the output file
     * @param fileStats statistics object containing all collected data
     */
    public static void outputStatsToFile(String outputFilePath, String outputFilename, FileStats fileStats) {
        try (PrintWriter writer = new PrintWriter(outputFilePath + File.separator + outputFilename)) {
            writer.println("Number of files: " + fileStats.numberOfFiles);
            writer.println("Total character count: " + fileStats.characterCount.get());
            writer.println("Total line count: " + fileStats.lineCount.get());    
            writer.println("Total word count: " + fileStats.wordCount.size());

            for (String word : fileStats.wordCount.keySet()) {
                writer.println(word + ": " + fileStats.wordCount.get(word));
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
