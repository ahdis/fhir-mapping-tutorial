## [step3](http://hl7.org/fhir/mapping-tutorial.html#step3)

### Step 3a

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step3a.map -compile http://hl7.org/fhir/StructureMap/tutorial-step3a -version 5.0.0 -output map/step3a.xml
java -jar ../../validator_cli.jar ./source/source3.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step3a -version 5.0.0 -ig ./logical -ig ./map/step3a.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a2 value="01234567890123456789"/>
</TRight>
```

### Step 3b

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step3b.map -compile http://hl7.org/fhir/StructureMap/tutorial-step3b -version 5.0.0 -output map/step3b.xml
java -jar ../../validator_cli.jar ./source/source3.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step3b -version 5.0.0 -ig ./logical -ig ./map/step3b.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial"/>
```

### Step 3c

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step3c.map -compile http://hl7.org/fhir/StructureMap/tutorial-step3c -version 5.0.0 -output map/step3c.xml
java -jar ../../validator_cli.jar ./source/source3.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step3c -version 5.0.0 -ig ./logical -ig ./map/step3c.xml -output ./output.xml
```

gives an error: _Failure: Rule "rule_a20c": Check condition failed_.
