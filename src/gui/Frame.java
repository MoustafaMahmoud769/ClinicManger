package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import data.Patient;

public class Frame extends JFrame {

	private static Frame frame;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) throws InterruptedException {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Frame();
					frame.setTitle("Clinic");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Error Running the Program");
				}
			}
		});
	}
	private EditPage editPage;
	private NewPatientInterface newPatientInterface;
	private OpenInterfce openInterface;
	private Patient patient;

	private SearchInterface searchInterface;

	public Frame() {
		openInterface = new OpenInterfce(this);
		newPatientInterface = new NewPatientInterface(this);
		searchInterface = new SearchInterface(this);
		editPage = new EditPage(this);
		setCurrent(1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 700);
	}

	public void setCurrent(int next) {
		if (next == 1)
			this.setContentPane(openInterface);
		if (next == 2) {
			newPatientInterface.reSet();
			this.setContentPane(newPatientInterface);
		}
		if (next == 3) {
			this.setContentPane(searchInterface);
		}
		if (next == 4) {
			editPage.setPatient(patient);
			this.setContentPane(editPage);
		}
		if (next == 5) {
			searchInterface.editPatient(patient);
			this.setContentPane(searchInterface);
		}
		this.validate();
		this.repaint();
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
