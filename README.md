# sterling-map-split-file
Splits IBM Sterling 856 map file.

Build
```
> mvn clean install
```
Jar output is present in target/stersplit.jar


Run
```
> java -jar strsplit.jar <filename> <lines-per-file> [optional output directory]
```

Output files are written to the working directory by default. Specifying an optional output directory is possible.
