mapp a dummy medicastionrquest to the logcal model and serialize it as json:

```
java -jar validator_cli.jar ./source.json -ig https://build.fhir.org/ig/hl7-be/referral/package.tgz -transform https://www.ehealth.fgov.be/standards/fhir/referral/StructureMap/be-map-model-referral-prescription -version 5.0.0 -ig ./map -output ./output.json
```

validate logical model:

```
java -jar validator_cli.jar ./output.json -ig https://build.fhir.org/ig/hl7-be/referral/package.tgz -profile https://www.ehealth.fgov.be/standards/fhir/referral/StructureDefinition/be-model-referralprescription -version 5.0.0
```

note: xml is not working due to not correct namespace
```
java -jar validator_cli.jar ./output.xml -ig https://build.fhir.org/ig/hl7-be/referral/package.tgz -profile https://www.ehealth.fgov.be/standards/fhir/referral/StructureDefinition/be-model-referralprescription -version 5.0.0
```