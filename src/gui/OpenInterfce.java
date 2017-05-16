package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OpenInterfce extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage backGroundImage;
	private Frame frame;
	private JButton newPatient;
	private ImageIcon newPatientImage;
	private JButton search;
	private ImageIcon searchImage;

	public OpenInterfce(Frame frame) {
		this.frame = frame;
		try {
			URL url = OpenInterfce.class.getResource("/resources/welcomeBG.jpg");
			backGroundImage = ImageIO.read(url);
			URL url2 = OpenInterfce.class.getResource("/resources/newPatient.jpg");
			newPatientImage = new ImageIcon(url2);
			URL url3 = OpenInterfce.class.getResource("/resources/search.jpg");
			searchImage = new ImageIcon(url3);
		} catch (Exception e) {
			throw new RuntimeException("Image not found");
		}
		this.setBorder(new EmptyBorder(15, 15, 15, 15));
		this.setLayout(null);
		this.setFocusable(true);
		this.requestFocusInWindow();
		setButtons();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newPatient) {
			frame.setCurrent(2);
		}
		if (e.getSource() == search) {
			frame.setCurrent(3);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backGroundImage, 0, 0, null);
	}

	private void setButtons() {
		newPatient = new JButton(newPatientImage);
		newPatient.setBounds(50, 500, 400, 100);
		newPatient.setBackground(Color.WHITE);
		newPatient.addActionListener(this);
		newPatient.setToolTipText("Add new patient");
		this.add(newPatient);
		search = new JButton(searchImage);
		search.setBackground(Color.WHITE);
		search.setBounds(550, 500, 400, 100);
		search.addActionListener(this);
		search.setToolTipText("Search Patient");
		this.add(search);
	}

}
