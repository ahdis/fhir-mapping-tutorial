# Experimental

Michael Lawley: @Oliver Egger and others, you can find my branch here: https://github.com/aehrc/org.hl7.fhir.core/tree/feature/20230627-csv-parser-for-fml

You can use the (implicit) Resource http://hl7.org/fhir/StructureDefinition/CSV which has a single multi-values BackboneElement record with a set of optional string-typed elements named according to the header of the CSV.
Alternatively, you can define your own Resource with the same single multi-values BackboneElement record, but with your own (order-sensitive) set of primitive-typed elements named as you wish and these will be matched column-wise with the CSV data.
In both cases a header row is assumed to exist.

This is an example of such a Resource (with URI http://mrff-difference.org.au/StructureDefinition/bioc-patient-demographics):
[image.png](https://chat.fhir.org/user_uploads/10155/A2uQj5rnq86urom9_MCzwFps/image.png)

The advantage of this approach is control over the element names, a mechanism to document the input, and (hopefully) some increased element of type-safety/checking (note, this is my first real foray into the "elementmodel" API, so I'm unclear on how much type checking/validation is going on but initial tests suggest at least the basics).


Source CSV is from [Apotheken mit Betriebsbewilligung oder Impfberechtigung nach Standort (September 2022)](https://opendata.swiss/dataset/apotheken-mit-betriebsbewilligung-oder-impfberechtigung-nach-standort-september-2022) 
Identifier: 10320@kanton-basel-landschaft, Publikationsdatum 5. September 2022, Änderungsdatum 15. Juni 2023, Publisher kanton-basel-landschaft


1. you need to build the future build branch and then copy the validator jar to this direcoty

cp /Users/oegger/Documents/github/feature/org.hl7.fhir.core/org.hl7.fhir.validation.cli/target/org.hl7.fhir.validation.cli-6.0.19-SNAPSHOT.jar validator_cli.jar

2. you can test the logical model 

java -jar validator_cli.jar 10320.csv -version 4.0.1 -profile http://hl7.org/fhir/tools/StructureDefinition/CSV -convert -output 10320.json

3. map the cvs example to a logical model

java -jar validator_cli.jar 10320.csv -transform http://ahdis.ch/matchbox/fml/csv10320 -version 4.0.1 -ig ./10320.map -log test.txt -output ./10320-bundle.xml