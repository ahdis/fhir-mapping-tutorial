## [step5](http://hl7.org/fhir/mapping-tutorial.html#step5)


```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step5.map -compile http://hl7.org/fhir/StructureMap/tutorial-step5 -version 5.0.0 -output map/step5.xml
java -jar ../../validator_cli.jar ./source/source5.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step5 -version 5.0.0 -ig ./logical -ig ./map -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a22 value="12345"/>
</TRight>
```
