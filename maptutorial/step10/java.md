## [step10](http://hl10.org/fhir/mapping-tutorial.html#step10)

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step10.map -compile http://hl7.org/fhir/StructureMap/tutorial-step10 -version 5.0.0 -output map/step10.xml
java -jar ../../validator_cli.jar ./source/source10.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step10 -version 5.0.0 -ig ./logical -ig ./map/step10.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
    <aa>
        <ab value="test"/>
    </aa>
    <aa>
        <ab value="test2"/>
    </aa>
</TRight>
```
