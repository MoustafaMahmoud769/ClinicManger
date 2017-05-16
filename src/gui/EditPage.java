package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerListModel;
import javax.swing.border.EmptyBorder;

import data.Patient;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class EditPage extends JPanel implements ActionListener {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField address;
	private JLabel addressLb;
	private JSpinner age;
	private JLabel ageLb;
	private BufferedImage backGroundImage;
	private JButton cancel;
	private ImageIcon cancelImage;
	private JTextField contact;
	private JLabel contactLb;
	private JLabel dateLb;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JTextArea diagnose;
	private JLabel diagnoseLb;
	private JRadioButton female;
	private JTextField firstName;
	private JLabel firstNameLb;
	private Frame frame;
	private ButtonGroup gender;
	private JLabel genderLb;
	private JTextField lastName;
	private JLabel lastNameLb;
	private JRadioButton male;
	private JTextArea medication;
	private JLabel medicationLb;
	private UtilDateModel model;
	private JTextField paid;
	private JLabel paidLb;
	private Patient patient;
	private JButton save;
	private ImageIcon saveImage;
	private JTextField total;
	private JLabel totalLb;
	private JTextArea treatmentPlan;
	private JLabel treatmentPlanLb;

	public EditPage(Frame frame) {
		this.frame = frame;
		try {
			URL url = EditPage.class.getResource("/resources/newBG.jpg");
			backGroundImage = ImageIO.read(url);
			URL url2 = EditPage.class.getResource("/resources/save.jpg");
			saveImage = new ImageIcon(url2);
			URL url3 = EditPage.class.getResource("/resources/cancel.jpg");
			cancelImage = new ImageIcon(url3);
		} catch (Exception e) {
			throw new RuntimeException("Image not found");
		}
		this.setBorder(new EmptyBorder(0, 0, 15, 15));
		this.setLayout(null);
		this.setFocusable(true);
		this.requestFocusInWindow();
		setName();
		setAge();
		setGender();
		setDate();
		setAddress();
		setDiagnose();
		setTreatmentPlan();
		setMedication();
		setButtons();
		setMoney();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			try {
				String patientGender = null;
				if (male.isSelected())
					patientGender = "Male";
				if (female.isSelected())
					patientGender = "Female";
				patient = new Patient(firstName.getText(), lastName.getText(), age.getValue().toString(), patientGender,
						model.getValue(), address.getText(), contact.getText(), diagnose.getText(),
						treatmentPlan.getText(), medication.getText(), total.getText(), paid.getText());
				frame.setPatient(patient);
				frame.setCurrent(5);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
		if (e.getSource() == cancel) {
			frame.setCurrent(3);
		}
	}

	public Patient getPatient() {
		return patient;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backGroundImage, 0, 0, null);
	}

	private void setAddress() {
		addressLb = new JLabel("Address :");
		addressLb.setBounds(10, 130, 100, 30);
		addressLb.setForeground(Color.YELLOW);
		this.add(addressLb);
		address = new JTextField(20);
		address.setBounds(120, 130, 300, 30);
		this.add(address);
		contactLb = new JLabel("Contact :");
		contactLb.setBounds(440, 130, 100, 30);
		contactLb.setForeground(Color.YELLOW);
		this.add(contactLb);
		contact = new JTextField(20);
		contact.setBounds(550, 130, 300, 30);
		this.add(contact);
	}

	private void setAge() {
		ageLb = new JLabel("Age :*");
		ageLb.setBounds(10, 50, 100, 30);
		ageLb.setForeground(Color.YELLOW);
		this.add(ageLb);
		String[] numbers = new String[120];
		for (int i = 0; i < 120; i++) {
			numbers[i] = Integer.toString(i + 1);
		}
		SpinnerListModel ageModel = new SpinnerListModel(numbers);
		age = new JSpinner(ageModel);
		age.setBounds(120, 50, 300, 30);
		this.add(age);
	}

	private void setButtons() {
		save = new JButton(saveImage);
		save.setBounds(120, 600, 200, 50);
		save.setBackground(Color.WHITE);
		save.addActionListener(this);
		this.add(save);
		cancel = new JButton(cancelImage);
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(720, 600, 200, 50);
		cancel.addActionListener(this);
		this.add(cancel);
	}

	private void setDate() {
		dateLb = new JLabel("Date :*");
		dateLb.setBounds(10, 90, 100, 30);
		dateLb.setForeground(Color.YELLOW);
		this.add(dateLb);
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(120, 90, 250, 30);
		this.add(datePicker);
	}

	private void setDiagnose() {
		diagnoseLb = new JLabel("Diagnose :*");
		diagnoseLb.setBounds(10, 180, 100, 30);
		diagnoseLb.setForeground(Color.YELLOW);
		this.add(diagnoseLb);
		diagnose = new JTextArea();
		diagnose.setLineWrap(true);
		diagnose.setWrapStyleWord(false);
		JScrollPane scrollPane = new JScrollPane(diagnose);
		scrollPane.setBounds(120, 180, 800, 130);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
	}

	private void setGender() {
		genderLb = new JLabel("Gender :*");
		genderLb.setBounds(440, 50, 100, 30);
		genderLb.setForeground(Color.YELLOW);
		this.add(genderLb);
		male = new JRadioButton();
		male.setText("Male");
		male.setBounds(550, 50, 100, 25);
		male.setSelected(true);
		female = new JRadioButton();
		female.setText("Female");
		female.setBounds(650, 50, 100, 25);
		gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		this.add(male);
		this.add(female);
	}

	private void setMedication() {
		medicationLb = new JLabel("Medication :*");
		medicationLb.setBounds(10, 450, 100, 30);
		medicationLb.setForeground(Color.YELLOW);
		this.add(medicationLb);
		medication = new JTextArea();
		medication.setLineWrap(true);
		medication.setWrapStyleWord(false);
		JScrollPane scrollPane = new JScrollPane(medication);
		scrollPane.setBounds(120, 450, 800, 100);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
	}

	private void setMoney() {
		totalLb = new JLabel("Total Cost :*");
		totalLb.setBounds(10, 560, 100, 30);
		totalLb.setForeground(Color.RED);
		this.add(totalLb);
		total = new JTextField(20);
		total.setBounds(120, 560, 100, 30);
		this.add(total);
		this.add(total);
		paidLb = new JLabel("Paid :*");
		paidLb.setBounds(440, 560, 100, 30);
		paidLb.setForeground(Color.RED);
		this.add(paidLb);
		paid = new JTextField(20);
		paid.setBounds(550, 560, 100, 30);
		this.add(paid);
	}

	private void setName() {
		firstNameLb = new JLabel("Fisrt name :*");
		firstNameLb.setBounds(10, 10, 100, 30);
		firstNameLb.setForeground(Color.YELLOW);
		this.add(firstNameLb);
		firstName = new JTextField(20);
		firstName.setBounds(120, 10, 300, 30);
		this.add(firstName);
		lastNameLb = new JLabel("Last name :*");
		lastNameLb.setBounds(440, 10, 100, 30);
		lastNameLb.setForeground(Color.YELLOW);
		this.add(lastNameLb);
		lastName = new JTextField(20);
		lastName.setBounds(550, 10, 300, 30);
		this.add(lastName);
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		firstName.setText(patient.getFirstName());
		lastName.setText(patient.getLastName());
		int patientAge = patient.getAge();
		age.setValue(String.valueOf(patientAge));
		model.setValue(patient.getDate());
		address.setText(patient.getAddress());
		contact.setText(patient.getContact());
		diagnose.setText(patient.getDiagnose());
		treatmentPlan.setText(patient.getTreatmentPlan());
		medication.setText(patient.getMedication());
		total.setText(String.valueOf(patient.getTotal()));
		paid.setText(String.valueOf(patient.getPaid()));
		if (patient.getGender().equals("Male"))
			male.setSelected(true);
		else
			female.setSelected(true);
	}

	private void setTreatmentPlan() {
		treatmentPlanLb = new JLabel("Treatment plan :*");
		treatmentPlanLb.setBounds(10, 330, 100, 30);
		treatmentPlanLb.setForeground(Color.YELLOW);
		this.add(treatmentPlanLb);
		treatmentPlan = new JTextArea();
		treatmentPlan.setLineWrap(true);
		treatmentPlan.setWrapStyleWord(false);
		JScrollPane scrollPane = new JScrollPane(treatmentPlan);
		scrollPane.setBounds(120, 330, 800, 100);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
	}

}
