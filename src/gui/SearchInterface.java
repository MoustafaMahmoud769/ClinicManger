package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBException;

import data.Patient;
import files.XmlEncoder;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class SearchInterface extends JPanel implements ActionListener,
        ItemListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BufferedImage backGroundImage;
    private boolean byDate;
    private JButton cancel;
    private ImageIcon cancelImage;
    private String[] columns;
    private int current;
    private ArrayList<Patient> data;
    private JLabel dateLb;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JButton delete;
    private ImageIcon deleteImage;
    private JButton edit;
    private ImageIcon editlImage;
    private XmlEncoder encoder;
    private JTextField firstName;
    private JLabel firstNameLb;
    private Frame frame;
    private JTextField lastName;
    private JLabel lastNameLb;
    private HashMap<Integer, Integer> map;
    private JComboBox<String> mode;
    private UtilDateModel model;
    private String[][] rows;
    private JButton search;
    private JTable table;
    private JScrollPane tableContainer;

    public SearchInterface(Frame frame) {
        encoder = new XmlEncoder();
        String[] tmp = {"First Name", "Last Name", "Age", "Gender", "Date",
                "Total", "Paid"};
        columns = new String[7];
        for (int i = 0; i < tmp.length; i++) {
            columns[i] = tmp[i];
        }
        try {
            data = encoder.load();
        } catch (JAXBException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Failed to Load");
        }
        this.frame = frame;
        tableContainer = new JScrollPane();
        this.add(tableContainer);
        try {
            URL url = SearchInterface.class.getResource(
                    "/resources/searchBG.jpg");
            backGroundImage = ImageIO.read(url);
            URL url2 = NewPatientInterface.class.getResource(
                    "/resources/cancel.jpg");
            cancelImage = new ImageIcon(url2);
            URL url3 = SearchInterface.class.getResource("/resources/edit.jpg");
            editlImage = new ImageIcon(url3);
            URL url4 = SearchInterface.class.getResource(
                    "/resources/delete.jpg");
            deleteImage = new ImageIcon(url4);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Image not found");
        }
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setMode();
        setName();
        setDate();
        setByDate();
        setButtons();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            try {
                search();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        if (e.getSource() == cancel) {
            frame.setCurrent(1);
        }
        if (e.getSource() == edit) {
            current = table.getSelectedRow();
            if (current == -1) {
                JOptionPane.showMessageDialog(null, "Select patient.");
            } else {
                frame.setPatient(data.get(map.get(current)));
                frame.setCurrent(4);
            }
        }
        if (e.getSource() == delete) {
            int current = table.getSelectedRow();
            if (current == -1) {
                JOptionPane.showMessageDialog(null, "Select patient.");
            } else {
                delete(current);
            }
        }
    }

    private void delete(int current) {
        int index = map.get(current);
        data.remove(index);
        try {
            encoder.save(data);
        } catch (JAXBException e) {
        }
        JOptionPane.showMessageDialog(null, "Deleted successfully");
        rows = new String[0][7];
        setTable();
    }

    public void editPatient(Patient patient) {
        data.set(current, patient);
        current = -1;
        try {
            encoder.save(data);
            JOptionPane.showMessageDialog(null, "Edited succesfully.");
        } catch (JAXBException e) {
        }
        rows = new String[0][7];
        setTable();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ((e.getStateChange() == ItemEvent.SELECTED)) {
            int selection = mode.getSelectedIndex();
            switch (selection) {
                case 0:
                    setByDate();
                    break;
                case 1:
                    setByName();
                    break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backGroundImage, 0, 0, null);
    }

    private void search() throws Exception {
        if (byDate)
            searchDate(model.getValue());
        else
            searchName(firstName.getText().trim(), lastName.getText().trim());
        setTable();
    }

    private void searchDate(Date date) throws Exception {
        data = encoder.load();
        int size = data.size();
        LocalDate firstDate = date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate secondDate;
        ArrayList<Integer> index = new ArrayList<Integer>();
        map = new HashMap<Integer, Integer>();
        for (int i = 0; i < size; ++i) {
            Patient patient = data.get(i);
            secondDate = patient.getDate().toInstant().atZone(ZoneId
                    .systemDefault()).toLocalDate();
            if (firstDate.equals(secondDate)) {
                index.add(i);
            }
        }
        rows = new String[index.size()][12];
        for (int i = 0; i < index.size(); i++) {
            setRow(data.get(index.get(i)), i);
            map.put(i, index.get(i));
        }
    }

    private void searchName(String firstName, String lastName)
            throws Exception {
        data = encoder.load();
        int size = data.size();
        ArrayList<Integer> index = new ArrayList<Integer>();
        map = new HashMap<Integer, Integer>();
        for (int i = 0; i < size; ++i) {
            Patient patient = data.get(i);
            if (firstName.equalsIgnoreCase(patient.getFirstName())
                    && lastName.equalsIgnoreCase(patient.getLastName())) {
                index.add(i);
            }
        }
        System.out.println(index.size());
        rows = new String[index.size()][12];
        for (int i = 0; i < index.size(); i++) {
            setRow(data.get(index.get(i)), i);
            map.put(i, index.get(i));
        }

    }

    private void setButtons() {
        search = new JButton("Search");
        search.setBackground(Color.MAGENTA);
        search.setBounds(840, 60, 125, 25);
        search.addActionListener(this);
        this.add(search);
        edit = new JButton(editlImage);
        edit.setBackground(Color.WHITE);
        edit.setBounds(10, 600, 200, 50);
        edit.addActionListener(this);
        edit.setVisible(false);
        this.add(edit);
        delete = new JButton(deleteImage);
        delete.setBackground(Color.WHITE);
        delete.setBounds(350, 600, 200, 50);
        delete.addActionListener(this);
        delete.setVisible(false);
        this.add(delete);
        cancel = new JButton(cancelImage);
        cancel.setBackground(Color.WHITE);
        cancel.setBounds(720, 600, 200, 50);
        cancel.addActionListener(this);
        this.add(cancel);
    }

    private void setByDate() {
        byDate = true;
        this.remove(firstName);
        this.remove(firstNameLb);
        this.remove(lastName);
        this.remove(lastNameLb);
        this.add(dateLb);
        this.add(datePicker);
        repaint();
    }

    private void setByName() {
        this.remove(datePicker);
        this.remove(dateLb);
        this.add(firstName);
        this.add(firstNameLb);
        this.add(lastName);
        this.add(lastNameLb);
        byDate = false;
        repaint();
    }

    private void setDate() {
        dateLb = new JLabel("Date :*");
        dateLb.setBounds(10, 20, 100, 30);
        dateLb.setForeground(Color.RED);
        this.add(dateLb);
        model = new UtilDateModel();
        datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        datePicker.setBounds(120, 20, 250, 30);
        this.add(datePicker);
    }

    private void setMode() {
        String[] modes = {"Date", "Name"};
        mode = new JComboBox<String>(modes);
        mode.setSelectedIndex(0);
        mode.addItemListener(this);
        mode.setBounds(850, 25, 100, 25);
        this.add(mode);
    }

    private void setName() {
        firstNameLb = new JLabel("Fisrt name :*");
        firstNameLb.setBounds(10, 20, 100, 30);
        firstNameLb.setForeground(Color.RED);
        firstName = new JTextField(20);
        firstName.setBounds(120, 20, 300, 30);
        lastNameLb = new JLabel("Last name :*");
        lastNameLb.setBounds(440, 20, 100, 30);
        lastNameLb.setForeground(Color.RED);
        lastName = new JTextField(20);
        lastName.setBounds(550, 20, 300, 30);
    }

    private void setRow(Patient patient, int i) {
        rows[i][0] = patient.getFirstName();
        rows[i][1] = patient.getLastName();
        rows[i][2] = String.valueOf(patient.getAge());
        rows[i][3] = patient.getGender();
        rows[i][4] = patient.getDate().toString();
        rows[i][5] = String.valueOf(patient.getTotal());
        rows[i][6] = String.valueOf(patient.getPaid());
    }

    private void setTable() {
        table = new JTable(rows, columns);
        table.setOpaque(false);
        this.remove(tableContainer);
        tableContainer = new JScrollPane(table);
        tableContainer.setBorder(BorderFactory.createEmptyBorder());
        tableContainer.setOpaque(false);
        tableContainer.getViewport().setOpaque(false);
        tableContainer.setBounds(50, 100, 900, 500);
        this.add(tableContainer);
        if (rows.length != 0) {
            edit.setVisible(true);
            delete.setVisible(true);
        }
    }
}
