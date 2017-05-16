package files;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import data.Patient;

public class XmlEncoder {

	private Data data;
	private ArrayList<Patient> patients;

	public XmlEncoder() {
		data = new Data();
	}

	public ArrayList<Patient> load() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("data.xml");
		if (!file.exists()) {
			System.out.println("here");
		}
		data = (Data) jaxbUnmarshaller.unmarshal(file);
		patients = data.getData();
		if (patients == null)
			patients = new ArrayList<Patient>();
		return patients;
	}

	public void save(ArrayList<Patient> patients) throws JAXBException {
		File file = new File("data.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		data.setData(patients);
		jaxbMarshaller.marshal(data, file);
	}

}