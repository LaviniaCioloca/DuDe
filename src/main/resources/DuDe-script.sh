rm -f DuDe.jar

wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v1.0/DuDe.jar

cat <<EOF > dude-config.txt
project.folder=.
min.chunk=10
max.linebias=30
min.length=10
file.extensions=java,sql
consider.comments=false
consider.test.files=false
EOF

java -jar DuDe.jar dude-config.txt > DuDe_analysis_report.txt

echo "DuDe analysis finished! The analysis report is:"

cat DuDe_analysis_report.txt
