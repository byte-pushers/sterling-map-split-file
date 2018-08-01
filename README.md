# sterling-map-split-file
Splits IBM Sterling 856 map file.

Build
```
> mvn clean install
```
Jar output is present in target/stersplit.jar


Run
```
> java -jar strsplit.jar <filename> <lines-per-file> 
```

Output files are written to the working directory.
