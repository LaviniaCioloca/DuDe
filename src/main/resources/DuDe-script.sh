rm -f DuDe.jar

rm -f DuDe-analysis.sh

rm -f dude-*

wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v1.0/DuDe.jar

echo "will print the values from the script"
echo $$minimum_line_size_of_exact_duplication
echo $maximum_line_size_of_gap_between_duplication

cat <<EOF > dude-config.txt
project.folder=.
min.chunk=$minimum_line_size_of_exact_duplication
max.linebias=$maximum_line_size_of_gap_between_duplication
min.length=$minimum_line_size_of_duplication_chain
file.extensions=$file_extensions_for_duplication_search
consider.comments=$consider_code_comments
consider.test.files=$consider_test_files
EOF

java -jar DuDe.jar dude-config.txt > dude-analysis-report.txt

echo "DuDe analysis finished! The analysis report is:"

cat dude-analysis-report.txt

wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v1.0/DuDe-analysis.sh
