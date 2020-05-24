# DuDe - Duplication Detector project reloaded

## About
Project and documentation: https://wettel.github.io/dude.html

## Continuous Integration
To have DuDe analyze the duplications on your repository in the CI flow, add in the Jenkins build confiiguration the following **Execute shell** build step:

```sh
#!/bin/sh

cd /var/lib/jenkins/workspace/<YOUR_PROJECT_NAME>

rm -f DuDe.jar

wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v1.0/DuDe.jar

cat <<EOF > config.txt
project.folder=.
min.chunk=10
max.linebias=30
min.length=10
file.extensions=java,sql
consider.comments=false
consider.test.files=false
EOF

java -jar DuDe.jar ./config.txt > DuDe_analysis_report.txt

echo "DuDe analysis finished! The analysis report is:"

cat DuDe_analysis_report.txt
```
