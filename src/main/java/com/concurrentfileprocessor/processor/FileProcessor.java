package com.concurrentfileprocessor.processor;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.fileStats;
import static com.concurrentfileprocessor.ConcurrentFileProcessor.outputFilePath;
import static com.concurrentfileprocessor.ConcurrentFileProcessor.outputFilename;

/**
 * handles the complete file processing workflow
 */
public class FileProcessor {
    /**
     * processes all input files concurrently and generates output file
     */
    public static void processFiles(){
        ThreadDelegator.delegateTasks();
        OutputWriter.outputStatsToFile(outputFilePath, outputFilename,fileStats);
    }
}
