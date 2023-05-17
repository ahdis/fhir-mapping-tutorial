## [step1](http://hl7.org/fhir/mapping-tutorial.html#step1) 

The mapping infrastructure runs internally on r5, meaning that you have to specify the version 5.0.0 that the right "StructureMapUtilities" are used. However note, that this will also generate then a R5 StructureMapResource:

```
java -jar ../../validator_cli.jar -ig logical -ig map/step1.map -compile http://hl7.org/fhir/StructureMap/tutorial-step1 -version 5.0.0 -output map/step1.xml
```

afterwards you can run the transform with

```
java -jar ../../validator_cli.jar ./source/source1.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step1 -version 5.0.0 -ig ./logical -ig ./map -output ./output.xml
```

TLeft --> TRight

```xml
<TRight 
  xmlns="http://hl7.org/fhir/tutorial">
  <a value="step1"/>
</TRight>
```