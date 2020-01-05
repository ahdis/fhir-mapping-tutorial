# FHIR tutorial mapping example
FHIR has a mapping language to convert between logical models an a nice [tutorial](http://build.fhir.org/mapping-tutorial.html) how use it, this projects tries to build the structure definitions and mappings for it.

The [validator](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator) has  the possiblity to run the transforms, currently a [patched version](https://github.com/ahdis/org.hl7.fhir.core/releases/download/4.1.38-dev/org.hl7.fhir.validation.cli-4.1.38-SNAPSHOT.jar) is necessary to run the steps below:

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step1/source/source1.xml -transform http://hl7.org/fhir/StructureMap/tutorial -version 4.0.1 -ig ./maptutorial/step1/logical -ig ./maptutorial/step1/map -log test.txt -output ./maptutorial/step1/output.xml
```


## run the tutorial
for each step there is a directory below the maptutorial directory

logical: structurdefinitions for tleft, tright

map: maps

source: files used to transform

output.xml: result of map transform


## [step1](http://hl7.org/fhir/mapping-tutorial.html#step1) - ok


```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step1/source/source1.xml -transform http://hl7.org/fhir/StructureMap/tutorial -version 4.0.1 -ig ./maptutorial/step1/logical -ig ./maptutorial/step1/map -log test.txt -output ./maptutorial/step1/output.xml
```

TLeft --> TRight

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a>test</a>
</TRight>
```

## [step2](http://hl7.org/fhir/mapping-tutorial.html#step2) - ok

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step2/source/source2.xml -transform http://hl7.org/fhir/StructureMap/tutorial -version 4.0.1 -ig ./maptutorial/step2/logical -ig ./maptutorial/step2/map -log test.txt -output ./maptutorial/step2/output.xml
```

TLeft --> TRight

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a2>test</a2>
</TRight>
```

## [step3](http://hl7.org/fhir/mapping-tutorial.html#step3)

note: did not use the structure definition like in steps1 and steps2 but simplified that the value is an attribute and not the content of the element.


### step3a

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step3/source/source3.xml -transform http://hl7.org/fhir/StructureMap/tutorial3a -version 4.0.1 -ig ./maptutorial/step3/logical -ig ./maptutorial/step3/map -log test.txt -output ./maptutorial/step3/output.xml
```

just cut it off at 20 characters

```xml
<TRight xmlns="http://hl7.org/fhir/tutorial">
  <a2 value="01234567890123456789"/>
</TRight>
```

### step3b ignore it - fails

```
rule_a20b: for source.a2 as a where a2.length() <= 20 make target.a2 = a
```

change it to one of the following to run the map:

```
rule_a20b: for source.a2 as a where $this.length() <= 20 make target.a2 = a
rule_a20b: for source.a2 as a where length() <= 20 make target.a2 = a
rule_a20b: for source.a2 as a where source.a2.length() <= 20 make target.a2 = a
```

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step3/source/source3.xml -transform http://hl7.org/fhir/StructureMap/tutorial3b -version 4.0.1 -ig ./maptutorial/step3/logical -ig ./maptutorial/step3/map -log test.txt -output ./maptutorial/step3/output.xml
```

### step3c fails: error if it's longer than 20 characters

```
rule_a20c: for source.a2 as a check a2.length() <= 20 make target.a2 = a
```

change it to one of the following to run the map:

```
rule_a20c: for source.a2 as a check $this.length() <= 20 make target.a2 = a
rule_a20c: for source.a2 as a check length() <= 20 make target.a2 = a
rule_a20c: for source.a2 as a check source.a2.length() <= 20 make target.a2 = a
```

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step3/source/source3.xml -transform http://hl7.org/fhir/StructureMap/tutorial3c -version 4.0.1 -ig ./maptutorial/step3/logical -ig ./maptutorial/step3/map -log test.txt -output ./maptutorial/step3/output.xml
```


## [step4](http://hl7.org/fhir/mapping-tutorial.html#step4) - fails

```
src.a21 as a -> tgt.a21 = cast(a, "integer"); // error if it's not an integer
src.a21 as a where a.isInteger -> tgt.a21 = cast(a, "integer"); // ignore it
src.a21 as a where not at1.isInteger -> tgt.a21 = 0; // just assign it 0
```

- Rule "rule_a21a": cast to integer not yet supported
- Rule "rule_a21b": Error in step4b.map at 7, 8: The name isInteger is not a valid function name

changed it to:

```
  source.a21 as a where $this.matches('\\d+') -> target.a21 = a "rule_a21b";
```

## [step5](http://hl7.org/fhir/mapping-tutorial.html#step5) - ok 

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step5/source/source5b.xml -transform http://hl7.org/fhir/StructureMap/tutorial5 -version 4.0.1 -ig ./maptutorial/step5/logical -ig ./maptutorial/step5/map -log test.txt -output ./maptutorial/step5/output.xml
```
  
## [step6](http://hl7.org/fhir/mapping-tutorial.html#step6) - ok

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step6/source/source6b.xml -transform http://hl7.org/fhir/StructureMap/tutorial6d -version 4.0.1 -ig ./maptutorial/step6/logical -ig ./maptutorial/step6/map -log test.txt -output ./maptutorial/step6/output.xml
```

## [step7](http://hl7.org/fhir/mapping-tutorial.html#step7) - ok

```
./maptutorial/step7/source/source7.xml -transform http://hl7.org/fhir/StructureMap/tutorial7b -version 4.0.1 -ig ./maptutorial/step7/logical -ig ./maptutorial/step7/map -log test.txt -output ./maptutorial/step7/output.xml
```
 
## [step8](http://hl7.org/fhir/mapping-tutorial.html#step8) - ok
  
conceptmap can be embedded in map: 

```
map "http://hl7.org/fhir/StructureMap/tutorial8" = "tutorial"

conceptmap "tutorialmap" {
  prefix s = "http://hl7.org/fhir/tutorial8/codeleft"
  prefix t = "http://hl7.org/fhir/tutorial8/coderight"

  s:vonhier == t:"nach-da"
  s:test == t:test
}

uses "http://hl7.org/fhir/StructureDefinition/tutorial-left" as source
uses "http://hl7.org/fhir/StructureDefinition/tutorial-right" as target

group tutorial(source source : TLeft, target target : TRight) {
  source.d as d -> target.d = translate(d, '#tutorialmap', 'code') "rule_d";
}

```

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step8/source/source8.xml -transform http://hl7.org/fhir/StructureMap/tutorial8 -version 4.0.1 -ig ./maptutorial/step8/logical -ig ./maptutorial/step8/map -log test.txt -output ./maptutorial/step8/output.xml
```

##[step9](http://hl7.org/fhir/mapping-tutorial.html#step9) - fails

changed rule to

```
  src.i as i where src.m < 2 -> tgt.j = i;
  src.i as i where src.m >= 2 -> tgt.k = i;
```

```
java -jar org.hl7.fhir.validator.jar ./maptutorial/step9/source/source9b.xml -transform http://hl7.org/fhir/StructureMap/tutorial9 -version 4.0.1 -ig ./maptutorial/step9/logical -ig ./maptutorial/step9/map -log test.txt -output ./maptutorial/step9/output.xml
```
