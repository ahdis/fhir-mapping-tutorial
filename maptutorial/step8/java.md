## [step8](http://hl8.org/fhir/mapping-tutorial.html#step8)

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step8.map -compile http://hl7.org/fhir/StructureMap/tutorial-step8 -version 5.0.0 -output map/step8.xml
java -jar ../../validator_cli.jar ./source/source8.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step8 -version 5.0.0 -ig ./logical -ig ./map/step8.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
    <d value="nach-da"/>
</TRight>
```
