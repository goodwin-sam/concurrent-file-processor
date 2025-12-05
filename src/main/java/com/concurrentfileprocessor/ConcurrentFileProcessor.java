package com.concurrentfileprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.concurrentfileprocessor.runner.GuiRunner;
import com.concurrentfileprocessor.runner.HeadlessRunner;

/**
 * main entry point for the concurrent file processor application
 * processes multiple text files concurrently to analyze word frequency and file statistics
 */
public class ConcurrentFileProcessor {
    // global file statistics shared across the application
    public static FileStats fileStats;

    // list of input files to be processed
    public static List<File> inputFiles;

    // name of the output file for results
    public static String outputFilename;

    // directory path where output file will be saved
    public static String outputFilePath;
    
    /**
     * main method that initializes the application and launches either GUI or headless mode
     * @param args command line arguments use --headless for terminal mode or nothing for gui mode
     */
    public static void main(String[] args) {
        fileStats = new FileStats(null, null, null);
        fileStats = fileStats.refreshFileStats();
        initFileDetails();
        if (args.length > 0 && args[0].equals("--headless")) {
            HeadlessRunner.run();
        } else {
            GuiRunner.launch();
        }
    }

    /**
     * initializes global variables for file processing
     */
    public static void initFileDetails(){
        inputFiles = new ArrayList<>();
        outputFilename = "processed_file_stats.txt";
        outputFilePath = getDefaultDownloadsPath();
    }

    /**
     * gets the default downloads directory path for either mac or windows
     * @return path to downloads directory, or home directory if downloads doesn't exist
     */
    public static String getDefaultDownloadsPath() {
        String home = System.getProperty("user.home");
        String downloads = home + File.separator + "Downloads";
        File downloadsDir = new File(downloads);

        return downloadsDir.exists() ? downloads : home;
    }
}
