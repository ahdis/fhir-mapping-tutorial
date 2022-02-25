# fhir-transforms  

This folder contains examples of NHS Digital extensions defined here

https://simplifier.net/hl7fhircareconnectbaselineforstu3

to NHS Digital UK Core examples, defined here

https://simplifier.net/hl7fhirukcorer4

## Run examples with validator_cli  

Download the [validator_cli](https://github.com/hapifhir/org.hl7.fhir.core/releases/latest/download/validator_cli.jar).

From this directory, run

```shell  
java -jar validator_cli.jar ./careconnect-to-ukcore/medicationrequest/input/[file_name.json] -transform [map url in ./careconnect-to-ukcore/medicationrequest/maps/[file_name.map]] -version 4.0.1 -ig ./careconnect-to-ukcore/medicationrequest/maps/[file_name.map] -output /tmp/output.json
```

The output file should match the file in ./careconnect-to-ukcore/medicationrequest/expected/[file_name.json]

e.g.

```shell  
java -jar validator_cli.jar ./careconnect-to-ukcore/medicationrequest/input/MedicationRequestPrescriptionType-Extension-3to4_000.json -transform http://fhir.nhs.uk/StructureMap/MedicationRequestPrescriptionType-Extension-3to4 -version 4.0.1 -ig ./careconnect-to-ukcore/medicationrequest/maps/MedicationRequestPrescriptionType-Extension-3to4.map -output /tmp/output.json
```

The output file should match the file in ./careconnect-to-ukcore/medicationrequest/expected/MedicationRequestPrescriptionType-Extension-3to4_000.json

