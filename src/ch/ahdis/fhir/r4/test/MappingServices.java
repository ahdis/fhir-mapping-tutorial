package ch.ahdis.fhir.r4.test;

import java.util.List;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.utils.StructureMapUtilities.ITransformerServices;

public class MappingServices implements ITransformerServices {

	public MappingServices() {
	}

	@Override
	public void log(String message) {
		System.out.println(message);
	}

	@Override
	public Base createType(Object appInfo, String name) throws FHIRException {
		return null;
	}

	@Override
	public Base createResource(Object appInfo, Base res) {
		return null;
	}

	@Override
	public Coding translate(Object appInfo, Coding source, String conceptMapUrl)
			throws FHIRException {
		return null;
	}

	@Override
	public Base resolveReference(Object appContext, String url) {
		return null;
	}

	@Override
	public List<Base> performSearch(Object appContext, String url) {
		return null;
	}

}
