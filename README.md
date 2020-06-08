# DuDe - Duplication Detector project reloaded

## About
Project and documentation: https://wettel.github.io/dude.html

## Continuous Integration
To have DuDe analyze the duplications on your repository in the CI flow, add in the Jenkins build confiiguration the following **Execute shell** build step:

```sh
#!/bin/sh

wget --no-check-certificate --content-disposition https://github.com/LaviniaCioloca/DuDe/releases/download/v2.0/DuDe-script.sh

export minimum_line_size_of_exact_duplication=3
export maximum_line_size_of_gap_between_duplication=2
export minimum_line_size_of_duplication_chain=8
export file_extensions_for_duplication_search=java,sql
export consider_code_comments=false
export consider_test_files=false

sh DuDe-script.sh
```
