package ch.ahdis.fhir.r4.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.context.SimpleWorkerContext;
import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.elementmodel.Manager;
import org.hl7.fhir.r4.elementmodel.Manager.FhirFormat;
import org.hl7.fhir.r4.formats.IParser.OutputStyle;
import org.hl7.fhir.r4.formats.XmlParser;
import org.hl7.fhir.r4.model.StructureDefinition;
import org.hl7.fhir.r4.model.StructureMap;
import org.hl7.fhir.r4.test.support.TestingUtilities;
import org.hl7.fhir.r4.utils.FHIRPathEngine;
import org.hl7.fhir.r4.utils.StructureMapUtilities;
import org.hl7.fhir.utilities.TextFile;
import org.hl7.fhir.utilities.Utilities;
import org.junit.Before;
import org.junit.Test;

public class TutorialMapTests {
	
	private FHIRPathEngine fhirPathEngine;
	private StructureMapUtilities structureMapUtilites;
	private Map<String, StructureMap> maps = new HashMap<String, StructureMap>();
	private MappingServices mappingServices = new MappingServices();

	final private String path = ".\\maptutorial\\";
	static private SimpleWorkerContext context;
	private static boolean setupTargetFolders = false;
	
	
	@Before 
	public void setupTest() throws FileNotFoundException, IOException, FHIRException {
		// FIXME: put here the path to build directory of the checkout source of forge
		TestingUtilities.fixedpath = "//Users//oliveregger//fhir//trunk//build";
		if (context == null)
			context = SimpleWorkerContext
			.fromPack(Utilities.path(TestingUtilities.home(), "publish", "igpack.zip"));

		TestingUtilities.context = new SimpleWorkerContext(context);
		fhirPathEngine = new FHIRPathEngine(TestingUtilities.context);
		structureMapUtilites = new StructureMapUtilities(TestingUtilities.context, maps, mappingServices);

		if (setupTargetFolders) {
			return;
		}
		setupTargetFolders = true;
		DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(path.replace("\\", File.separator)));
		for ( Path p : dirStream )
		{
			if ( p.toFile().isDirectory() )
			{
				Path pathTarget = Paths.get(Utilities.path(getPathTarget(p.getFileName().toString())));
				if (Files.notExists(pathTarget))
					Files.createDirectories(pathTarget);
			}
		}
	}
	
	/**
	 * reads the logical structure definition from the directroy and returns the target structure definition
	 * @param pathLogical
	 * @param target
	 * @return
	 * @throws IOException
	 */
	private StructureDefinition readLogicalStructureDefintions(String pathLogical,
			String target) throws IOException {
		StructureDefinition tRight = null;
		for (String f : new File(Utilities.path(pathLogical)).list()) {
			try {
				StructureDefinition sd = (StructureDefinition) new XmlParser()
						.parse(new FileInputStream(Utilities.path(pathLogical, f)));
				((SimpleWorkerContext) TestingUtilities.context).seeResource(sd.getUrl(), sd);
				if (target.equals(sd.getIdBase())) {
					tRight = sd;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tRight;
	}

	private void testParseMap(String pathin, String namemap, String pathout) throws FileNotFoundException, IOException, FHIRException {

		StructureMap map = structureMapUtilites
				.parse(TextFile.fileToString(Utilities.path(pathin, namemap)));

		TextFile.stringToFile(structureMapUtilites.render(map),
				Utilities.path(pathout, namemap));
	}
	
	private void readMaps(String pathMap) throws IOException, FHIRException {
		maps.clear();
		for (String f : new File(Utilities.path(pathMap)).list()) {
				StructureMap map = structureMapUtilites
						.parse(TextFile.fileToString(Utilities.path(pathMap, f)));
				maps.put(map.getUrl(), map);
		}
	}

	
	private String getPathLogical(String step) {
		return path + step +"\\"+"logical";
	}

	private String getPathMap(String step) {
		return path + step +"\\"+"map";
	}

	private String getPathTarget(String step) {
		return path + step +"\\"+"target";
	}

	private String getPathSource(String step) {
		return path + step +"\\"+"source";
	}
	
	@Test
	public void testMapStep1() throws FHIRException, IOException {
		testParseMap(getPathMap("step1"), "step1.map", getPathTarget("step1"));
	}

	@Test
	public void testTransformStep1SimplestPossibleTransform() throws FileNotFoundException, Exception {

		String step = "step1";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source1.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

    assertEquals("test", fhirPathEngine.evaluateToString(tleft, "a.children()"));

		// Element tright = Manager.parse(TestingUtilities.context, new FileInputStream(Utilities
		// .path(pathSource, "target1.xml")),
		// FhirFormat.XML);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source1.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial"), tright);
    assertEquals("test", fhirPathEngine.evaluateToString(tright, "a.children()"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target1.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
	}
	
	@Test
	public void testMapStep2() throws FHIRException, IOException {
		testParseMap(getPathMap("step2"), "step2.map", getPathTarget("step2"));
	}

	@Test
	public void testTransformStep2FieldsWithDifferentNames() throws FileNotFoundException, Exception {

		String step = "step2";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source2.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source2.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a2.children()"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial"), tright);
    assertEquals("test", fhirPathEngine.evaluateToString(tright, "a2.children()"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target2.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
	}
	
	@Test
	public void testMapStep3() throws FHIRException, IOException {
		testParseMap(getPathMap("step3"), "step3a.map", getPathTarget("step3"));
		testParseMap(getPathMap("step3"), "step3b.map", getPathTarget("step3"));
		testParseMap(getPathMap("step3"), "step3c.map", getPathTarget("step3"));
	}

	@Test
	public void testTransformStep3LengthRestriction() throws FileNotFoundException, Exception {

		String step = "step3";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source3.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source3.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a2"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial3a"), tright);
    assertEquals("01234567890123456789", fhirPathEngine.evaluateToString(tright, "a2"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target3.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source3b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial3a"), tright);
    assertEquals("0123456789", fhirPathEngine.evaluateToString(tright, "a2"));
    
    // 3b

		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source3.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial3b"), tright);
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a2"));

		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source3b.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);

		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial3b"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "targetb.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
    assertEquals("0123456789", fhirPathEngine.evaluateToString(tright, "a2"));
    
    //3c
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source3.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		try {
			structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial3c"), tright);
	    assertEquals("", "check should throw an exception");
		} catch(Exception e) {
			
		}

		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source3b.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial3c"), tright);
    assertEquals("0123456789", fhirPathEngine.evaluateToString(tright, "a2"));

	}
	
	@Test
	public void testMapStep4() throws FHIRException, IOException {
		testParseMap(getPathMap("step4"), "step4a.map", getPathTarget("step4"));
		testParseMap(getPathMap("step4"), "step4b.map", getPathTarget("step4"));
		testParseMap(getPathMap("step4"), "step4c.map", getPathTarget("step4"));
	}

	
	
	@Test
	public void testTransformStep4TypeConversion() throws FileNotFoundException, Exception {

		String step = "step4";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source4.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source4.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a21"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial4a"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a21"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target4.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source4b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);		
		try {
			structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial4a"), tright);
	    assertEquals("", "check should throw an exception");
		} catch(Exception e) {
			
		}
    
    // 4b
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source4.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial4b"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a21"));

		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source4b.xml")),
				FhirFormat.XML);
		
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target4b.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial4b"), tright);
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a21"));
    
    //4c
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source4.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial4c"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a21"));

		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source4b.xml")),
				FhirFormat.XML);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial4c"), tright);
    assertEquals("0", fhirPathEngine.evaluateToString(tright, "a21"));

	}
	
	@Test
	public void testMapStep5() throws FHIRException, IOException {
		testParseMap(getPathMap("step5"), "step5.map", getPathTarget("step5"));
	}
	
	@Test
	public void testTransformStep5ManagingListsPart1() throws FileNotFoundException, Exception {

		String step = "step5";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source5.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source5.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a22"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial5"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a22"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target5.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
		
    // 5b
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source5b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft); 
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial5"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target5.xml")), FhirFormat.XML,
				OutputStyle.PRETTY, null);
		
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a22[0]"));
    assertEquals("67890", fhirPathEngine.evaluateToString(tright, "a22[1]"));


	}
	
	@Test
	public void testMapStep6() throws FHIRException, IOException {
		testParseMap(getPathMap("step6"), "step6a.map", getPathTarget("step6"));
		testParseMap(getPathMap("step6"), "step6b.map", getPathTarget("step6"));
		testParseMap(getPathMap("step6"), "step6c.map", getPathTarget("step6"));
	}
	
	@Test
	public void testTransformStep6ManagingListsPart2() throws FileNotFoundException, Exception {

		String step = "step6";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source6.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a23"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6a"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a23"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target6.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
		
    // 6b
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft); 
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		try {
			structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6a"), tright);
	    assertEquals("", "check should throw an exception");
		} catch(Exception e) {
			
		}
		
		// map 6b
		
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a23"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6b"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a23"));
 
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target6.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
		
    // 6b
		 tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft); 
		 tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		try {
			structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6b"), tright);
	    assertEquals("67890", fhirPathEngine.evaluateToString(tright, "a23")); // up to the implementation
		} catch(Exception e) {
			
		}

	// map 6c
		
		 tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);
		 tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "a23"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6c"), tright);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a23"));
 
		
		
    // 6b
    tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft); 
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
	 
		assertEquals("", fhirPathEngine.evaluateToString(tright, "a23"));
	  structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6c"), tright);
	  assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a23"));
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target6brule3.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
		
		
		// map 6d
		
		 tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);
		 tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
   assertEquals("", fhirPathEngine.evaluateToString(tright, "a23"));
   structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6d"), tright);
   assertEquals("12345", fhirPathEngine.evaluateToString(tright, "a23"));

		
		
   // 6d
   tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source6b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft); 
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
	 
		assertEquals("", fhirPathEngine.evaluateToString(tright, "a23"));
	  structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial6d"), tright);
	  assertEquals("67890", fhirPathEngine.evaluateToString(tright, "a23"));
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target6brule3.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

	}

	@Test
	public void testMapStep7() throws FHIRException, IOException {
		testParseMap(getPathMap("step7"), "step7.map", getPathTarget("step7"));
		testParseMap(getPathMap("step7"), "step7b.map", getPathTarget("step7"));
	}

	@Test
	public void testTransformStep7SimpleNesting() throws FileNotFoundException, Exception {

		String step = "step7";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source7.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source7.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "aa[0].ab"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial7"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target7.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "aa[0].ab"));
    assertEquals("6789", fhirPathEngine.evaluateToString(tright, "aa[1].ab"));

    
		tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source7.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source7.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		
		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
    assertEquals("", fhirPathEngine.evaluateToString(tright, "aa[0].ab"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial7b"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target7b.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
    assertEquals("12345", fhirPathEngine.evaluateToString(tright, "aa[0].ab"));
    assertEquals("6789", fhirPathEngine.evaluateToString(tright, "aa[1].ab"));
 
	}


	@Test
	public void testMapStep8() throws FHIRException, IOException {
		testParseMap(getPathMap("step8"), "step8.map", getPathTarget("step8"));
	}
	
	@Test
	public void testTransformStep8TranslateCode() throws FileNotFoundException, Exception {

		String step = "step8";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source8.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source8.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    assertEquals("", fhirPathEngine.evaluateToString(tright, "d"));
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial8"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target8.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
    assertEquals("nach-da", fhirPathEngine.evaluateToString(tright, "d"));
	}

	@Test
	public void testMapStep9() throws FHIRException, IOException {
		testParseMap(getPathMap("step9"), "step9.map", getPathTarget("step9"));
	}

	@Test
	public void testTransformStep9CoDependency() throws FileNotFoundException, Exception {

		String step = "step9";
	  String structureDefintionTarget = "TRight";
		
	  StructureDefinition structureDefinitionTRight= readLogicalStructureDefintions(getPathLogical(step), structureDefintionTarget);
		assertNotNull(structureDefinitionTRight);
		readMaps(this.getPathMap(step));
		
		Element tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source9.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source9.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		Element tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial9"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target9.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
    assertEquals("mkleiner2maptoj", fhirPathEngine.evaluateToString(tright, "j"));
    
    
    tleft = Manager.parse(TestingUtilities.context,
				new FileInputStream(Utilities.path(getPathSource(step), "source9b.xml")),
				FhirFormat.XML);
		assertNotNull(tleft);

		Manager.compose(TestingUtilities.context, tleft,
				new FileOutputStream(Utilities.path(getPathTarget(step), "source9b.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);

		tright = Manager.build(TestingUtilities.context, structureDefinitionTRight);
		
    structureMapUtilites.transform(null, tleft, maps.get("http://hl7.org/fhir/StructureMap/tutorial9"), tright);
		Manager.compose(TestingUtilities.context, tright,
				new FileOutputStream(Utilities.path(getPathTarget(step), "target9b.xml")),
				FhirFormat.XML, OutputStyle.PRETTY, null);
    assertEquals("mgroesser2maptok", fhirPathEngine.evaluateToString(tright, "k"));
	}





}
