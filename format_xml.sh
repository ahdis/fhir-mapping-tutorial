#!/bin/bash

# Directory to search (maptutorial)
base_directory="maptutorial"

# Find and format all XML files
find "$base_directory" -type f -name "*.xml" | while read -r file; do
    echo "Formatting $file"
    xmllint --format "$file" --output "$file"
done

echo "All XML files have been formatted."
