# FHIR tutorial mapping example
FHIR has a mapping language to convert between resources and logical models and features a [tutorial](http://build.fhir.org/mapping-tutorial.html) how use it. This projects tries to build the structure definitions and mappings for it and adds as well additional FHIR mapping language examples.

See the [FHIR Mapping Language confluence page](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Mapping+Language) for additional information. 

To run the transforms directly from the command line you can use the FHIR Java Validator, [download here](https://github.com/hapifhir/org.hl7.fhir.core/releases/latest/download/validator_cli.jar).
For more information about the .NET Implementation, [see here](https://github.com/brianpos/fhir-net-mappinglanguage).

If you want to use it with a public test server, you can use https://test.ahdis.ch/matchboxv3/fhir/. Install [Visual Studio Code](https://code.visualstudio.com/) with the [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension. in each step there is a test.ahdis.ch.http file which you can upload the StructureDefinition and StructureMap and perform the transformation. E.g. see http file for [step1](https://github.com/ahdis/fhir-mapping-tutorial/blob/master/maptutorial/step1/test.ahdis.ch.http).

The mapping infrastructure runs internally on r5, meaning that you have to specify the version 5.0.0 that the right "StructureMapUtilities" are used. However note, that this will also generate then a R5 StructureMapResource:


## run the tutorial
for each step there is a directory below the maptutorial directory

logical: structurdefinitions for tleft, tright

map: Mapping files in FHIR Mapping Language

source: XML files used to transform

output.xml: result of map transform

## additional examples
- fhir transforms from STU3 NHS Digital extensions to NHS Digital UK Core R4 examples by [declankieran-nhsd](https://github.com/ahdis/fhir-mapping-tutorial/commits?author=declankieran-nhsd), see folder [careconnect-to-ukcore](https://github.com/ahdis/fhir-mapping-tutorial/tree/master/careconnect-to-ukcore)

## Java Reference Implementation features

|          | Java |
|----------|------|
| Step 1   |  ✓   |
| Step 1b  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/20)   |
| Step 2   |  ✓   |
| Step 3a  |  ✓   |
| Step 3b  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21)  |
| Step 3c  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21)  |
| [Step 4a](https://jira.hl7.org/browse/FHIR-27140)  |  [x](https://github.com/ahdis/fhir-mapping-tutorial/issues/22)  |
| [Step 4b](https://jira.hl7.org/browse/FHIR-28465)  |  ✓  |
| Step 4b2 |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21)  |
| Step 4b3 |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21) |
| Step 4c  |  [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/21)  |
| Step 5a  |  ✓   |
| Step 5b  |  ✓   |
| Step 6a  |  ✓   |
| Step 6b  |  ✓   |
| Step 6c  |  ✓   |
| Step 6d  |  ✓   |
| Step 7a  |  ✓   |
| Step 7b  |  ✓   |
| Step 8   |  ✓   |
| Step 9   |  ✓   |
| Step 10  |  ✓   |
| Step 11  |  ✓   |
| Step 12  |  ✓   |
| Step 13  |  ✓ [✗](https://github.com/ahdis/fhir-mapping-tutorial/issues/31)    |
| Step 14  |      |
| Step 15  |      |
