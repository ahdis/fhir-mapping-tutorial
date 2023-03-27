## [step2](http://hl7.org/fhir/mapping-tutorial.html#step2) 

The mapping infrastructure runs internally on r5, meaning that you have to specify the version 5.0.0 that the right "StructureMapUtilities" are used. However note, that this will also generate then a R5 StructureMapResource:

```
java -jar validator_cli.jar -ig logical -ig map/step2.map -compile http://hl7.org/fhir/StructureMap/tutorial-step2 -version 5.0.0 -output map/step2.xml
```

afterwards you can run the transform with

```
java -jar org.hl7.fhir.validation.cli.jar ./source/source2.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step2 -version 5.0.0 -ig ./logical -ig ./map -output ./output.xml
```


TLeft --> TRight

```xml
<TRight 
  xmlns="http://hl7.org/fhir/tutorial">
  <a value="step1"/>
</TRight>
```