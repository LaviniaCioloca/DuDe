# Clean the workspace by deleting previous DuDe files, if present
rm -f DuDe.jar
rm -f DuDe-analysis.sh
rm -f dude-*

# Download DuDe jar file from DuDe's GitHub release
wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v2.0/DuDe.jar

# Download DuDe analysis script that will be executed next as this script ends
wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v2.0/DuDe-analysis.sh

# Create the configuration file from the environment variables set in build's shell
cat <<EOF > dude-config.txt
project.folder=.
min.chunk=$minimum_line_size_of_exact_duplication
max.linebias=$maximum_line_size_of_gap_between_duplication
min.length=$minimum_line_size_of_duplication_chain
file.extensions=$file_extensions_for_duplication_search
consider.comments=$consider_code_comments
consider.test.files=$consider_test_files
EOF

# Run DuDe with the configuration parameters and save the logs in a file
java -jar DuDe.jar dude-config.txt > dude-analysis-report.txt

# Print the success message that will be searched by the post-build action
printf "DuDe analysis finished!\nThe analysis report:\n"

# Show DuDe's analysis report
cat dude-analysis-report.txt
