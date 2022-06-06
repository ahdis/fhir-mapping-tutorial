# Java Validator Transform example with the Java Validator

## Adjust the path for the validator_cli.jar

```
java -jar validator_cli.jar ./qr.json -transform http://ahdis.ch/matchbox/fml/qr2patgender -version 4.0.1 -ig ./map -output ./output.xml

cat output.xml 
```

