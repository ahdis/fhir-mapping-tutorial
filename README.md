# FHIR tutorial mapping example
FHIR has a mapping language to convert between resources and logical models and features a [tutorial](http://build.fhir.org/mapping-tutorial.html) how use it. This projects tries to build the structure definitions and mappings for it.

See the [FHIR Mapping Language confluence page](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Mapping+Language) for additional information. 

To run the transforms directly from the command line you can use the FHIR Java Validator, [download here](https://github.com/hapifhir/org.hl7.fhir.core/releases/latest/download/validator_cli.jar).
For more information about the .NET Implementation, [see here](https://docs.fire.ly/mappingengine/index.html).

If you want to use it with a public test server, you can use https://test.ahdis.ch/r4/. Install [Visual Studio Code](https://code.visualstudio.com/) with the [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension. in each step there is a test.ahdis.ch.http file which you can upload the StructureDefinition and StructureMap and perform the transformation. E.g. see http file for [step1](https://github.com/ahdis/fhir-mapping-tutorial/blob/master/maptutorial/step1/test.ahdis.ch.http).

## run the tutorial
for each step there is a directory below the maptutorial directory

logical: structurdefinitions for tleft, tright

map: Mapping files in FHIR Mapping Language

source: XML files used to transform

output.xml: result of map transform

## Comparison Java Reference Implementation / .NET Reference Implementation

|          | Java | .NET |
|----------|------|------|
| Step 1   |  ✓   |   ✓  |
| Step 1b  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/20)   |   ✓  |
| Step 2   |  ✓   |   ✓  |
| Step 3a  |  ✓   |   ✓  |
| Step 3b  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21)  |   ✓  |
| Step 3c  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21)  |   ✓  |
| Step 4a  |      |   ✓  |
| Step 4b  |      |   ✗  |
| Step 4b2 |      |   ✓  |
| Step 4b3 |      |   ✓  |
| Step 4c  |      |   ✓  |
| Step 5a  |      |   ✓  |
| Step 5b  |      |   ✓  |
| Step 6a  |      |   ✓  |
| Step 6b  |      |   ✓  |
| Step 6c  |      |   ✓  |
| Step 6d  |      |   ✓  |
| Step 7a  |      |   ✓  |
| Step 7b  |      |   ✓  |
| Step 8   |      |   ✓ (Not using embedded ConceptMaps)   |
| Step 9   |      |   ✓  |
| Step 10  |      |   ✗  |
| Step 11  |      |      |
| Step 12  |      |      |
| Step 13  |      |      |
| Step 14  |      |      |
| Step 15  |      |      |