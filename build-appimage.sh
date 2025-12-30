#!/bin/bash
set -e

# prompt for version number
read -p "Enter version number (e.g., v1.0.0): " VERSION
if [ -z "$VERSION" ]; then
    echo "Error: Version number is required"
    exit 1
fi

# detect system architecture
ARCH=$(uname -m)
# normalize architecture names to AppImage standard
case "$ARCH" in
    x86_64)
        ARCH="x86_64"
        ;;
    aarch64|arm64)
        ARCH="aarch64"
        ;;
    armv7l|armhf)
        ARCH="armhf"
        ;;
    *)
        echo "Warning: Unknown architecture $ARCH, using as-is"
        ;;
esac
echo "Detected architecture: $ARCH"

# build the jar file
mvn clean package

# clean and create AppDir structure
APP_DIR="AppDir"
rm -rf "$APP_DIR"
mkdir -p "$APP_DIR/usr/bin"
mkdir -p "$APP_DIR/usr/lib"
mkdir -p "$APP_DIR/usr/share/applications"
mkdir -p "$APP_DIR/usr/share/icons/hicolor/256x256/apps"

# create AppRun script
cat > "$APP_DIR/AppRun" << 'EOF'
#!/bin/bash
HERE="$(dirname "$(readlink -f "${0}")")"
JRE_DIR="$HERE/usr/lib/jvm/jre"
JAR_FILE="$HERE/usr/bin/concurrent-file-processor-1.0-SNAPSHOT-jar-with-dependencies.jar"

# check if bundled JRE exists
if [ -d "$JRE_DIR" ] && [ -f "$JRE_DIR/bin/java" ]; then
    JAVA_BIN="$JRE_DIR/bin/java"
else
    echo "Error: Bundled JRE not found at $JRE_DIR"
    exit 1
fi

# check if JAR exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    exit 1
fi

# check if JavaFX is bundled
JAVAFX_DIR="$HERE/usr/lib/javafx"
if [ ! -d "$JAVAFX_DIR/lib" ]; then
    echo "Error: JavaFX not found at $JAVAFX_DIR/lib"
    exit 1
fi

# launch application with JavaFX module path
exec "$JAVA_BIN" \
    --module-path "$JAVAFX_DIR/lib" \
    --add-modules javafx.controls,javafx.fxml \
    -jar "$JAR_FILE" \
    "$@"
EOF

chmod +x "$APP_DIR/AppRun"

# copy JAR file to AppDir
JAR_FILE=$(find target -name "*-jar-with-dependencies.jar" -type f | head -n 1)
if [ -z "$JAR_FILE" ]; then
    echo "Error: JAR file with dependencies not found in target directory"
    exit 1
fi
cp "$JAR_FILE" "$APP_DIR/usr/bin/concurrent-file-processor-1.0-SNAPSHOT-jar-with-dependencies.jar"
echo "Copied JAR file to AppDir/usr/bin/"

# download and bundle JRE
JRE_DIR="$APP_DIR/usr/lib/jvm"
mkdir -p "$JRE_DIR"
JRE_TAR="jre-17-linux-x64.tar.gz"

if [ ! -d "$JRE_DIR/jre" ]; then
    echo "Downloading JRE 17..."
    if [ ! -f "$JRE_TAR" ]; then
        # Adoptium API endpoint - follows redirects to actual download
        DOWNLOAD_URL="https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jre/hotspot/normal/eclipse"
        echo "Downloading JRE from Adoptium..."
        curl -L -o "$JRE_TAR" "$DOWNLOAD_URL"
        if [ ! -f "$JRE_TAR" ] || [ ! -s "$JRE_TAR" ]; then
            echo "Error: JRE download failed"
            exit 1
        fi
    fi
    echo "Extracting JRE..."
    tar -xzf "$JRE_TAR" -C "$JRE_DIR"
    # find and rename extracted directory to 'jre'
    EXTRACTED_DIR=$(find "$JRE_DIR" -maxdepth 1 -type d -name "jdk*" | head -n 1)
    if [ -n "$EXTRACTED_DIR" ] && [ "$EXTRACTED_DIR" != "$JRE_DIR/jre" ]; then
        mv "$EXTRACTED_DIR" "$JRE_DIR/jre"
    fi
    if [ ! -f "$JRE_DIR/jre/bin/java" ]; then
        echo "Error: JRE extraction failed - java binary not found"
        exit 1
    fi
    echo "JRE bundled successfully"
else
    echo "JRE already bundled, skipping download"
fi

# download and bundle JavaFX SDK
JAVAFX_DIR="$APP_DIR/usr/lib/javafx"
JAVAFX_VERSION="21"
JAVAFX_TAR="javafx-sdk-${JAVAFX_VERSION}-linux.tar.gz"

if [ ! -d "$JAVAFX_DIR/lib" ]; then
    echo "Downloading JavaFX SDK ${JAVAFX_VERSION}..."
    if [ ! -f "$JAVAFX_TAR" ]; then
        JAVAFX_URL="https://download2.gluonhq.com/openjfx/${JAVAFX_VERSION}/openjfx-${JAVAFX_VERSION}_linux-x64_bin-sdk.zip"
        # try zip first (Gluon provides zip)
        curl -L -o "javafx-temp.zip" "$JAVAFX_URL" 2>/dev/null || {
            # fallback to Maven Central
            JAVAFX_URL="https://repo1.maven.org/maven2/org/openjfx/javafx-sdk/${JAVAFX_VERSION}/javafx-sdk-${JAVAFX_VERSION}-linux.zip"
            curl -L -o "javafx-temp.zip" "$JAVAFX_URL"
        }
        if [ ! -f "javafx-temp.zip" ] || [ ! -s "javafx-temp.zip" ]; then
            echo "Error: JavaFX download failed"
            exit 1
        fi
        unzip -q "javafx-temp.zip" -d "$APP_DIR/usr/lib/"
        rm "javafx-temp.zip"
        # rename extracted directory to 'javafx'
        EXTRACTED_DIR=$(find "$APP_DIR/usr/lib" -maxdepth 1 -type d -name "javafx-sdk*" | head -n 1)
        if [ -n "$EXTRACTED_DIR" ] && [ "$EXTRACTED_DIR" != "$JAVAFX_DIR" ]; then
            mv "$EXTRACTED_DIR" "$JAVAFX_DIR"
        fi
    else
        echo "JavaFX archive already exists, extracting..."
        tar -xzf "$JAVAFX_TAR" -C "$APP_DIR/usr/lib/"
        EXTRACTED_DIR=$(find "$APP_DIR/usr/lib" -maxdepth 1 -type d -name "javafx-sdk*" | head -n 1)
        if [ -n "$EXTRACTED_DIR" ] && [ "$EXTRACTED_DIR" != "$JAVAFX_DIR" ]; then
            mv "$EXTRACTED_DIR" "$JAVAFX_DIR"
        fi
    fi
    if [ ! -d "$JAVAFX_DIR/lib" ]; then
        echo "Error: JavaFX extraction failed - lib directory not found"
        exit 1
    fi
    echo "JavaFX SDK bundled successfully"
else
    echo "JavaFX already bundled, skipping download"
fi

# create .desktop file (in both locations)
cat > "$APP_DIR/usr/share/applications/concurrent-file-processor.desktop" << 'EOF'
[Desktop Entry]
Type=Application
Name=Concurrent File Processor
Comment=Process multiple text files concurrently to analyze word frequency and file statistics
Exec=AppRun
Icon=concurrent-file-processor
Categories=Utility;TextTools;
Terminal=false
EOF
# appimagetool expects desktop file in AppDir root
cp "$APP_DIR/usr/share/applications/concurrent-file-processor.desktop" "$APP_DIR/concurrent-file-processor.desktop"
echo "Created .desktop file"

# copy icon file (in both locations)
if [ -f "cfp-logo.png" ]; then
    cp "cfp-logo.png" "$APP_DIR/usr/share/icons/hicolor/256x256/apps/concurrent-file-processor.png"
    # appimagetool expects icon in AppDir root
    cp "cfp-logo.png" "$APP_DIR/concurrent-file-processor.png"
    echo "Copied icon file"
else
    echo "Warning: Icon file cfp-logo.png not found, skipping icon setup"
fi

# create AppImage
APPIMAGE_NAME="concurrent-file-processor-${VERSION}-${ARCH}.AppImage"
echo "Creating AppImage: $APPIMAGE_NAME"
if [ -f "appimagetool.AppImage" ]; then
    ./appimagetool.AppImage "$APP_DIR" "$APPIMAGE_NAME"
    chmod +x "$APPIMAGE_NAME"
    echo "AppImage created successfully: $APPIMAGE_NAME"
else
    echo "Error: appimagetool.AppImage not found"
    exit 1
fi

