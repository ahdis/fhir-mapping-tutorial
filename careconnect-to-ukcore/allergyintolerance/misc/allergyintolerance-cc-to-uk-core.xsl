<?xml version="1.0" encoding="UTF-8"?>
<!-- 
MIT License, Copyright (c) 2022 NHS.UK

Ravi Natarajan , ravi.natarajan1@nsh.net

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs fhir"
    xmlns:fhir="http://hl7.org/fhir" version="2.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <xsl:element name="Bundle" xmlns="http://hl7.org/fhir">
            <xsl:copy-of select="child::fhir:Bundle/fhir:id"/>
            <xsl:element name="meta" xmlns="http://hl7.org/fhir">
                <xsl:element name="profile" xmlns="http://hl7.org/fhir">
                    <xsl:attribute name="value">https://fhir.hl7.org.uk/StructureDefinition/UKCore-Bundle</xsl:attribute>
                </xsl:element>
            </xsl:element>
            <xsl:copy-of select="child::fhir:Bundle/fhir:type"/>
            <xsl:apply-templates select="node()"/>
        </xsl:element>
    </xsl:template>
    <xsl:template match="fhir:AllergyIntolerance">
        <xsl:element name="entry" xmlns="http://hl7.org/fhir">
            <xsl:element name="resource" xmlns="http://hl7.org/fhir">
                <xsl:element name="AllergyIntolerance">
                    <xsl:copy-of select="./fhir:id" copy-namespaces="no"/>
                    <xsl:element name="meta" xmlns="http://hl7.org/fhir">
                        <xsl:element name="profile" xmlns="http://hl7.org/fhir">
                            <xsl:attribute name="value">https://fhir.hl7.org.uk/StructureDefinition/UKCore-AllergyIntolerance</xsl:attribute>
                        </xsl:element>
                    </xsl:element>
                    <xsl:if test="./fhir:extension[@url='https://fhir.nhs.uk/STU3/StructureDefinition/Extension-CareConnect-GPC-AllergyIntoleranceEnd-1']">
                        <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                            <xsl:attribute name="url">https://fhir.hl7.org.uk/StructureDefinition/Extension-UKCore-AllergyIntoleranceEnd</xsl:attribute>
                            <xsl:copy-of select="./fhir:extension/fhir:extension[@url='endDate']" copy-namespaces="no"/>
                            <xsl:copy-of select="./fhir:extension/fhir:extension[@url='reasonEnded']" copy-namespaces="no"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:copy-of select="./fhir:identifier" copy-namespaces="no"/>
                    <xsl:element name="clinicalStatus" xmlns="http://hl7.org/fhir">
                        <xsl:element name="coding" xmlns="http://hl7.org/fhir">
                            <xsl:element name="code" xmlns="http://hl7.org/fhir">
                                <xsl:attribute name="value"><xsl:value-of select="fhir:clinicalStatus/@value"/></xsl:attribute>
                            </xsl:element>
                        </xsl:element>
                    </xsl:element>
                    <xsl:element name="verificationStatus" xmlns="http://hl7.org/fhir">
                        <xsl:element name="coding" xmlns="http://hl7.org/fhir">
                            <xsl:element name="code" xmlns="http://hl7.org/fhir">
                                <xsl:attribute name="value"><xsl:value-of select="fhir:verificationStatus/@value"/></xsl:attribute>
                            </xsl:element>
                        </xsl:element>
                    </xsl:element>
                    <xsl:copy-of select="./fhir:type" copy-namespaces="no"/>
                    <xsl:copy-of select="./fhir:category" copy-namespaces="no"/>
                    <xsl:copy-of select="./fhir:criticality" copy-namespaces="no"/>
                    <xsl:element name="code" xmlns="http://hl7.org/fhir">
                        <xsl:for-each select="fhir:code/fhir:coding">
                            <xsl:element name="coding" xmlns="http://hl7.org/fhir">
                                <!-- DescId Extension -->
                                <xsl:if test="./fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']">
                                    <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                                        <xsl:attribute name="url">https://fhir.hl7.org.uk/StructureDefinition/Extension-UKCore-CodingSCTDescId</xsl:attribute>
                                        <xsl:if test="./fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionId']">
                                            <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                                                <xsl:attribute name="url">descriptionId</xsl:attribute>
                                                <xsl:element name="valueIdentifier" xmlns="http://hl7.org/fhir">
                                                    <xsl:element name="system" xmlns="http://hl7.org/fhir">
                                                        <xsl:attribute name="value">http://snomed.info/sct</xsl:attribute>
                                                    </xsl:element>
                                                    <xsl:element name="value" xmlns="http://hl7.org/fhir">
                                                        <xsl:attribute name="value"><xsl:value-of select="fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionId']/fhir:valueId/@value"/></xsl:attribute>
                                                    </xsl:element>
                                                </xsl:element>
                                            </xsl:element>
                                        </xsl:if>
                                        <xsl:if test="./fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionDisplay']">
                                            <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                                                <xsl:attribute name="url">descriptionDisplay</xsl:attribute>
                                                <xsl:element name="valueIdentifier" xmlns="http://hl7.org/fhir">
                                                    <xsl:element name="system" xmlns="http://hl7.org/fhir">
                                                        <xsl:attribute name="value">http://snomed.info/sct</xsl:attribute>
                                                    </xsl:element>
                                                    <xsl:element name="value" xmlns="http://hl7.org/fhir">
                                                        <xsl:attribute name="value"><xsl:value-of select="fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionDisplay']/fhir:valueId/@value"/></xsl:attribute>
                                                    </xsl:element>
                                                </xsl:element>
                                            </xsl:element>
                                        </xsl:if>
                                    </xsl:element>
                                </xsl:if>
                                <xsl:copy-of select="fhir:system" copy-namespaces="no"/>
                                <xsl:copy-of select="fhir:code" copy-namespaces="no"/>
                                <xsl:copy-of select="fhir:display" copy-namespaces="no"/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                    <xsl:copy-of select="./fhir:patient" copy-namespaces="no"/>
                    <!-- Encounter mapping from Extension http://hl7.org/fhir/StructureDefinition/encounter-associatedEncounter -->
                    <xsl:if test="./fhir:extension[@url='http://hl7.org/fhir/StructureDefinition/encounter-associatedEncounter']">
                        <xsl:element name="encounter" xmlns="http://hl7.org/fhir">
                            <xsl:element name="reference" xmlns="http://hl7.org/fhir">
                                <xsl:attribute name="value"><xsl:value-of select="fhir:extension[@url='http://hl7.org/fhir/StructureDefinition/encounter-associatedEncounter']/fhir:valueReference/@value"/></xsl:attribute>
                            </xsl:element>
                        </xsl:element>
                    </xsl:if>
                    <xsl:copy-of select="./fhir:onsetDateTime" copy-namespaces="no"/>
                    <xsl:element name="recordedDate" xmlns="http://hl7.org/fhir">
                        <xsl:attribute name="value"><xsl:value-of select="fhir:assertedDate/@value"/></xsl:attribute>
                    </xsl:element>
                    <xsl:copy-of select="./fhir:recorder" copy-namespaces="no"/>
                    <xsl:copy-of select="./fhir:asserter" copy-namespaces="no"/>
                    <xsl:copy-of select="./fhir:lastOccurrence" copy-namespaces="no"/>
                    <xsl:copy-of select="./fhir:note" copy-namespaces="no"/>
                    <xsl:if test="./fhir:reaction">
                        <xsl:element name="reaction" xmlns="http://hl7.org/fhir">
                            <xsl:for-each select="fhir:reaction/fhir:manifestation">
                                <xsl:element name="manifestation" xmlns="http://hl7.org/fhir">
                                    <xsl:for-each select="fhir:coding">
                                        <xsl:element name="coding" xmlns="http://hl7.org/fhir">
                                            <xsl:if test="./fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']">
                                                <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                                                    <xsl:attribute name="url">https://fhir.hl7.org.uk/StructureDefinition/Extension-UKCore-CodingSCTDescId</xsl:attribute>
                                                    <xsl:if test="./fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionId']">
                                                        <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                                                            <xsl:attribute name="url">descriptionId</xsl:attribute>
                                                            <xsl:element name="valueIdentifier" xmlns="http://hl7.org/fhir">
                                                                <xsl:element name="system" xmlns="http://hl7.org/fhir">
                                                                    <xsl:attribute name="value">http://snomed.info/sct</xsl:attribute>
                                                                </xsl:element>
                                                                <xsl:element name="value" xmlns="http://hl7.org/fhir">
                                                                    <xsl:attribute name="value"><xsl:value-of select="fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionId']/fhir:valueId/@value"/></xsl:attribute>
                                                                </xsl:element>
                                                            </xsl:element>
                                                        </xsl:element>
                                                    </xsl:if>
                                                    <xsl:if test="./fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionDisplay']">
                                                        <xsl:element name="extension" xmlns="http://hl7.org/fhir">
                                                            <xsl:attribute name="url">descriptionDisplay</xsl:attribute>
                                                            <xsl:element name="valueIdentifier" xmlns="http://hl7.org/fhir">
                                                                <xsl:element name="system" xmlns="http://hl7.org/fhir">
                                                                    <xsl:attribute name="value">http://snomed.info/sct</xsl:attribute>
                                                                </xsl:element>
                                                                <xsl:element name="value" xmlns="http://hl7.org/fhir">
                                                                    <xsl:attribute name="value"><xsl:value-of select="fhir:extension[@url='https://fhir.hl7.org.uk/STU3/StructureDefinition/Extension-coding-sctdescid']/fhir:extension[@url='descriptionDisplay']/fhir:valueId/@value"/></xsl:attribute>
                                                                </xsl:element>
                                                            </xsl:element>
                                                        </xsl:element>
                                                    </xsl:if>
                                                </xsl:element>
                                            </xsl:if>
                                            <xsl:copy-of select="fhir:system" copy-namespaces="no"/>
                                            <xsl:copy-of select="fhir:code" copy-namespaces="no"/>
                                            <xsl:copy-of select="fhir:display" copy-namespaces="no"/>
                                        </xsl:element>
                                    </xsl:for-each>
                                </xsl:element>
                            </xsl:for-each>
                            <xsl:copy-of select="./fhir:reaction/fhir:description" copy-namespaces="no"/>
                            <xsl:copy-of select="./fhir:reaction/fhir:severity" copy-namespaces="no"/>
                            <xsl:copy-of select="./fhir:reaction/fhir:exposureRoute" copy-namespaces="no"/>
                        </xsl:element>
                    </xsl:if>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
    </xsl:template>
    <xsl:template match="node()">
        <xsl:apply-templates select="child::node()"/>
    </xsl:template>
</xsl:stylesheet>
