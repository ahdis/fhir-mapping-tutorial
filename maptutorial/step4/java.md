## [step4](http://hl7.org/fhir/mapping-tutorial.html#step4)

### Step 4a

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step4a.map -compile http://hl7.org/fhir/StructureMap/tutorial-step4a -version 5.0.0 -output map/step4a.xml
java -jar ../../validator_cli.jar ./source/source4.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step4a -version 5.0.0 -ig ./logical -ig ./map/step4a.xml -output ./output.xml
```

gives: _org.hl7.fhir.exceptions.FHIRException: cast to FHIR.integer not yet supported_.

### Step 4b

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step4b.map -compile http://hl7.org/fhir/StructureMap/tutorial-step4b -version 5.0.0 -output map/step4b.xml
java -jar ../../validator_cli.jar ./source/source4.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step4b -version 5.0.0 -ig ./logical -ig ./map/step4b.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a21 value="12345"/>
</TRight>
```

### Step 4c

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step4c.map -compile http://hl7.org/fhir/StructureMap/tutorial-step4c -version 5.0.0 -output map/step4c.xml
java -jar ../../validator_cli.jar ./source/source4.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step4c -version 5.0.0 -ig ./logical -ig ./map/step4c.xml -output ./output.xml
```

gives an error: _java.lang.IndexOutOfBoundsException: Index -9 out of bounds for length 1_.
