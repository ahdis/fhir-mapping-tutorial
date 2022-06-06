## [step1](http://hl7.org/fhir/mapping-tutorial.html#step1) 


```
java -jar validator_cli.jar ./source/source1.xml -transform http://hl7.org/fhir/StructureMap/tutorial -version 4.0.1 -ig ./logical -ig ./map -output ./maptutorial/step1/output.xml
```

TLeft --> TRight

```xml
<TRight 
  xmlns="http://hl7.org/fhir/tutorial">
  <a value="step1"/>
</TRight>
```