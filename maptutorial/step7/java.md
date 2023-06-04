## [step7](http://hl7.org/fhir/mapping-tutorial.html#step7)

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step7.map -compile http://hl7.org/fhir/StructureMap/tutorial-step7 -version 5.0.0 -output map/step7a.xml
java -jar ../../validator_cli.jar ./source/source7.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step7 -version 5.0.0 -ig ./logical -ig ./map/step7a.xml -output ./output.xml
```

and

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step7b.map -compile http://hl7.org/fhir/StructureMap/tutorial-step7b -version 5.0.0 -output map/step7b.xml
java -jar ../../validator_cli.jar ./source/source7.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step7b -version 5.0.0 -ig ./logical -ig ./map/step7b.xml -output ./output.xml
```

both give:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <aa>
    <ab value="12345"/>
  </aa>
  <aa>
    <ab value="6789"/>
  </aa>
</TRight>
```
