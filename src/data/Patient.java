package data;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

@XmlRootElement
public class Patient {
	private String address;
	private int age;
	private String contact;
	private Date date;
	private String diagnose;
	private String firstName;
	private String gender;
	private String lastName;
	private String medication;
	private int paid;
	private int total;
	private String treatmentPlan;

	public Patient() {

	}

	public Patient(String firstName, String lastName, String age, String gender, Date date, String address,
			String contact, String diagnose, String treatmentPlan, String medication, String total, String paid) {
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		try {
			this.age = Integer.parseInt(age.trim());
		} catch (Exception e) {
			throw new RuntimeException("Age is Empty.");
		}
		this.gender = gender.trim();
		this.date = date;
		this.address = address.trim();
		this.contact = contact.trim();
		this.diagnose = diagnose.trim();
		this.treatmentPlan = treatmentPlan.trim();
		this.medication = medication.trim();
		try {
			this.total = Integer.parseInt(total.trim());
		} catch (Exception e) {
			throw new RuntimeException("Total is Empty.");
		}
		try {
			this.paid = Integer.parseInt(paid.trim());
		} catch (Exception e) {
			throw new RuntimeException("Paid is Empty.");
		}
		check();
	}

	private void check() {
		if (firstName.length() == 0)
			throw new RuntimeException("First Name is Empty.");
		if (lastName.length() == 0)
			throw new RuntimeException("Last Name is Empty.");
		if (gender.length() == 0)
			throw new RuntimeException("Gender is Not Selected.");
		if (diagnose.length() == 0)
			throw new RuntimeException("Patient has no diagnose.");
		if (treatmentPlan.length() == 0)
			throw new RuntimeException("Patient has no treatment plan.");
		if (medication.length() == 0)
			throw new RuntimeException("Patient has no medication.");
		if (date == null)
			throw new RuntimeException("Date is not set yet.");
	}

	public String getAddress() {
		return address;
	}

	public int getAge() {
		return age;
	}

	public String getContact() {
		return contact;
	}

	public Date getDate() {
		return date;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getGender() {
		return gender;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMedication() {
		return medication;
	}

	public int getPaid() {
		return paid;
	}

	public int getTotal() {
		return total;
	}

	public String getTreatmentPlan() {
		return treatmentPlan;
	}

	@XmlElement
	public void setAddress(String address) {
		this.address = address;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	@XmlElement
	public void setContact(String contact) {
		this.contact = contact;
	}

	@XmlElement
	@XmlSchemaType(name = "date")
	public void setDate(Date date) {
		this.date = date;
	}

	@XmlElement
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public void setGender(String gender) {
		this.gender = gender;
	}

	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement
	public void setMedication(String medicaton) {
		this.medication = medicaton;
	}

	@XmlElement
	public void setPaid(int paid) {
		this.paid = paid;
	}

	@XmlElement
	public void setTotal(int total) {
		this.total = total;
	}

	@XmlElement
	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

}
