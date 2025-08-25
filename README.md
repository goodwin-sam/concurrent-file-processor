# Concurrent File Processor

Java application that processes multiple text files concurrently to analyze word frequency, total word count, character counts, and line counts. Built with JavaFX for an intuitive GUI and designed for efficient concurrent processing.

## 🚀 Features

- **Concurrent Processing**: Utilizes work-stealing thread pools for optimal performance
- **Two Interface Modes**: 
  - **GUI Mode**: Modern JavaFX interface with intuitive file selection and configuration
  - **Headless Mode**: Command-line interface for automation and server environments
- **Comprehensive Analysis**: 
  - Word frequency counting
  - Total character count across all files
  - Total line count across all files
  - File count tracking
- **Flexible Output**: Customizable output filename and directory
- **Cross-Platform**: Works on Windows, macOS, and Linux

## 🏗️ Architecture

The application follows clean architecture principles with clear separation of concerns:

- `ConcurrentFileProcessor`: the main entry point into the program
- `FileStats`: the class that contains the stastics the program collects

- **Runner**: handles the different ways the program can run
    - `GuiRunner` handles running the program via GUI window
    - `HeadlessRunner` handles running the program via terminal
- **Processor**: handles the file processing capabilities of the program
    - `FileProcessor` handles file processing workflow
    - `ThreadDelegator` manages thread pools for parallel processing
    - `FileMetricsCollector` handles individual file analysis
    - `OutputWriter` creates formatted output file for file statistics
- **Gui**: handles the gui portion of the program
    - `JavaFxApp` is the entry point for the gui window
    - **Window**: contains the gui windows and components
        - `Controller` manages window navigation
        - `StartWindow`, `MainWindow`, `OutputWindow` handle the layout of each window
        - **Components**: handles the creation of the individul components for all windows
            - `StartWindowComponents`, `MainWindowComponents`, `OutputWindowComponents` creates the compenents for each window

## 📋 Requirements

- **Java 17** or higher
- **Maven 3.6+** for building
- **JavaFX 21** (included in dependencies)

## 🛠️ Installation & Build

### Prerequisites
```bash
# Ensure you have Java 17+ installed
java -version

# Ensure you have Maven installed
mvn -version
```

### Build the Project
```bash
# Clone the repository
git clone https://github.com/yourusername/concurrent-file-processor.git
cd concurrent-file-processor

# Build with Maven
mvn clean package

# Run the application
mvn javafx:run
```

### Create Executable JAR
```bash
# Creates a JAR with all dependencies
mvn clean package assembly:single

# Run the JAR
java -jar target/concurrent-file-processor-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## 🎯 Usage

### GUI Mode (Default)
```bash
# Launch in GUI application mode
mvn javafx:run
```

The GUI provides a three-window workflow:
1. **Start Window**: Welcome screen with navigation
2. **Main Window**: File selection and output configuration
3. **Output Window**: Results display and navigation to restart or go back to main window

### Headless Mode
```bash
# Process files in current directory
java -jar target/concurrent-file-processor-1.0-SNAPSHOT-jar-with-dependencies.jar --headless
```

In headless mode, the application:
- Prompts for output filename or uses default
- Scans the current directory for `.txt` files
- Processes them concurrently
- Displays results in the console
- Writes results in an output file

## 📁 Input & Output

### Supported Input
- **File Types**: `.txt` files only
- **File Size**: No practical limits, distributes pieces of files across other threads if other threads are waiting

### Output Format
The application generates a comprehensive report with:
```
Number of files: [count]
Total character count: [count]
Total line count: [count]
Total word count: [count]
[word1]: [frequency]
[word2]: [frequency]
...
```

### Output Location
- **Default**: Downloads directory
- **Customizable**: User can specify any directory and filename

## 🔧 Configuration

### Default Settings
- **Output Filename**: `processed_file_stats.txt`
- **Output Directory**: User's Downloads folder
- **Thread Pool**: Work-stealing pool (automatically sized)

### Customization
- Select one or multiple input files via file chooser
- Modify or use default output filename
- Choose custom or use default output directory
- Process files with these options

## 🧪 Testing

Run the comprehensive test suite:
```bash
# Run all tests
mvn test
```

The test suite covers:
- Core functionality
- Thread safety
- File processing
- Error handling
- GUI components

## 📊 Performance

- **Concurrent Processing**: Multiple files processed simultaneously
- **Scalable**: Performance improves with additional CPU cores
- **Optimized I/O**: Uses efficient file reading with proper resource management

## 🏛️ Project Structure

```
concurrent-file-processor/
├── src/main/java/com/concurrentfileprocessor/
│   ├── ConcurrentFileProcessor.java    # Main entry point
│   ├── FileStats.java                  # Data model for statistics
│   ├── processor/                      # Core processing logic
│   ├── runner/                         # Application launchers
│   └── gui/                            # JavaFX user interface
│       └── window/compnents            # Window management and UI components
├── src/test/java/                      # Comprehensive test suite
├── demo_input_files/                   # Sample text files
└── pom.xml                             # Maven and CI configuration
```

## 🚀 Future Enhancements

- Support for additional file formats (PDF, DOCX)
- Real-time processing progress indicators
- Export to multiple output formats (CSV, JSON, XML)
- Advanced text analysis

---
