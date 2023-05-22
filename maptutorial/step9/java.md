## [step9](http://hl9.org/fhir/mapping-tutorial.html#step9)

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step9.map -compile http://hl7.org/fhir/StructureMap/tutorial9 -version 5.0.0 -output map/step9.xml
java -jar ../../validator_cli.jar ./source/source9.xml -transform http://hl7.org/fhir/StructureMap/tutorial9 -version 5.0.0 -ig ./logical -ig ./map/step9.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
    <j value="mkleiner2maptoj"/>
</TRight>
```
