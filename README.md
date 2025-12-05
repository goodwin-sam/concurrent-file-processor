# Concurrent File Processor

Java application that processes multiple text files concurrently to analyze word frequency, total word count, character counts, and line counts. Built with JavaFX for GUI and designed for efficient concurrent processing.

## 📑 Table of Contents

- [Features](#-features)
- [Quickstart](#-quickstart)
- [Download and Run](#-download-and-run)
- [Usage](#-usage)
- [Input & Output](#-input--output)
- [Code Structure](#-code-structure)
- [Requirements](#-requirements)
- [Testing](#-testing)

## 🚀 Features

- **Concurrent Processing**: Utilizes work-stealing thread pools for optimal performance
- **Two Interface Modes**: 
  - **GUI Mode**: Modern JavaFX interface with intuitive file selection and configuration
  - **Headless Mode**: Command-line interface for automation and server environments
- **File Processing Features**: 
  - Word frequency counting
  - Total character count across all files
  - Total line count across all files
  - File count tracking
- **Customizable Output**: Customizable output filename and directory
- **Cross-Platform**: Works on Windows and Linux

## ⚡ Quickstart

### 🐧 Linux

1. **Download** the latest AppImage from [Releases](https://github.com/samg111/concurrent-file-processor/releases)
2. **Make it executable**: `chmod +x concurrent-file-processor-vX.X.X-x86_64.AppImage`
3. **Run**:
   - Double-click the `.AppImage` file
   - Or run from terminal: `./concurrent-file-processor-vX.X.X-x86_64.AppImage` or `./concurrent-file-processor-vX.X.X-x86_64.AppImage --headless`
4. **Process your data**:
   - **If in GUI**: Select your text files and output directory, then click "Process Files" to begin processing
   - **If in headless**: The application will automatically process all text files in the current directory, and prompt user for output file name

### 🪟 Windows

1. **Download** the latest `.exe` file from [Releases](https://github.com/samg111/concurrent-file-processor/releases)
2. **Run**:
   - Double-click the `.exe` file
   - Or run from PowerShell/command prompt: `./concurrent-file-processor-vX.X.X-windows-amd64.exe` or `./concurrent-file-processor-vX.X.X-windows-amd64.exe --headless`
3. **Process your data**:
   - **If in GUI**: Select your text files and output directory, then click "Process Files" to begin processing
   - **If in headless**: The application will automatically process all text files in the current directory, and prompt user for output file name


### Developers
```bash
# Clone and build
git clone https://github.com/samg111/concurrent-file-processor.git
cd concurrent-file-processor
mvn clean package

# Run GUI mode
mvn javafx:run

# Or run headless mode
mvn javafx:run -Djavafx.args="--headless"
```

## 📥 Download and Run

> **💡 Note**: No installation required - these are portable executables that run directly.

### Linux AppImage
Download the latest Linux AppImage from [Releases](https://github.com/samg111/concurrent-file-processor/releases):

- **concurrent-file-processor-X.X.X-x86_64.AppImage** - Self-contained Linux executable
- **Includes Java runtime** - Works on any Linux distribution without Java installation

#### Running the AppImage

```bash
# Double click the appimage file

# or Run directly with GUI
./concurrent-file-processor-X.X.X-x86_64.AppImage

# or Run directly headless
./concurrent-file-processor-X.X.X-x86_64.AppImage --headless
```

### Windows Executable
- **Coming Soon** - Windows .exe will be available in future releases
- **No Java installation required** - Self-contained Windows application

## 🎯 Usage

### GUI Mode (Default)

The GUI provides a three-window workflow:
1. **Start Window**: Welcome screen with navigation
2. **Main Window**: File selection and output configuration
3. **Output Window**: Results display and navigation to restart or go back to main window

### Headless Mode

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

## 🏛️ Code Structure

The application follows clean architecture principles with clear separation of concerns:

### Code Architecture

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

### Directory Structure

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

## 📋 Requirements

- **Java 17** or higher
- **Maven 3.6+** for building
- **JavaFX 21** (included in dependencies)


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


