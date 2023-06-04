## [step6](http://hl7.org/fhir/mapping-tutorial.html#step6)

### Step 6a

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step6a.map -compile http://hl7.org/fhir/StructureMap/tutorial-step6a -version 5.0.0 -output map/step6a.xml
java -jar ../../validator_cli.jar ./source/source6.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step6a -version 5.0.0 -ig ./logical -ig ./map/step6a.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
    <a23 value="12345"/>
</TRight>
```

### Step 6b

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step6b.map -compile http://hl7.org/fhir/StructureMap/tutorial-step6b -version 5.0.0 -output map/step6b.xml
java -jar ../../validator_cli.jar ./source/source6b.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step6b -version 5.0.0 -ig ./logical -ig ./map/step6b.xml -output ./output.xml
```

gives: _Failure: Rule "rule_a23b": Check condition failed: the collection has more than one item_.

### Step 6c

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step6c.map -compile http://hl7.org/fhir/StructureMap/tutorial-step6c -version 5.0.0 -output map/step6c.xml
java -jar ../../validator_cli.jar ./source/source6.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step6c -version 5.0.0 -ig ./logical -ig ./map/step6c.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a23 value="12345"/>
</TRight>
```

### Step 6d

```bash
java -jar ../../validator_cli.jar -ig logical -ig map/step6d.map -compile http://hl7.org/fhir/StructureMap/tutorial-step6d -version 5.0.0 -output map/step6d.xml
java -jar ../../validator_cli.jar ./source/source6.xml -transform http://hl7.org/fhir/StructureMap/tutorial-step6d -version 5.0.0 -ig ./logical -ig ./map/step6d.xml -output ./output.xml
```

gives:

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a23 value="12345"/>
</TRight>
```
