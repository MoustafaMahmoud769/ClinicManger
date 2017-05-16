package files;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import data.Patient;

@XmlRootElement(name = "DataBase")
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

	@XmlElement(name = "Patient")
	private ArrayList<Patient> data = null;

	public ArrayList<Patient> getData() {
		return data;
	}

	public void setData(ArrayList<Patient> data) {
		this.data = data;
	}

}
