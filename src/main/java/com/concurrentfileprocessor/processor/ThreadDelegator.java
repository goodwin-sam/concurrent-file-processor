package com.concurrentfileprocessor.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.concurrentfileprocessor.ConcurrentFileProcessor.fileStats;
import static com.concurrentfileprocessor.ConcurrentFileProcessor.inputFiles;

/**
 * handles the delegation of file processing tasks to a thread pool
 */
public class ThreadDelegator {
    /**
     * creates thread pool and submits file processing tasks for concurrent execution
     */
    public static void delegateTasks() {
        int numberOfFiles = inputFiles.size();
        fileStats.numberOfFiles = numberOfFiles;
        
        // create work-stealing thread pool
        ExecutorService executor = Executors.newWorkStealingPool();
        
        // submit each file for processing in separate thread
        for (int i = 0; i < numberOfFiles; i++) {
            executor.submit(new FileMetricsCollector(inputFiles.get(i), fileStats));
        }
    
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
