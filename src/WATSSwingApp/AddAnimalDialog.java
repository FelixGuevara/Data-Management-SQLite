/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 25, 2025,
 * Class: AddAnimalDialog.java
 *
 * This is a modal dialog window used to collect and validate input for adding a new animal
 * record to the Wildlife Animal Tracking System (WATS). It provides form fields for entering
 * animal details such as Tag ID, Name, Species, Age, Gender, Weight, and Health Status.
 * Input filters are applied to ensure numeric and decimal validation. Upon submission,
 * the data is passed to the DatabaseManager for persistence.
 */

package WATSSwingApp;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddAnimalDialog extends JDialog {

    private final DatabaseManager dbManager;

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtSpecies;
    private JTextField txtAge;
    private JComboBox<String> cmbGender;
    private JTextField txtWeight;
    private JComboBox<String> cmbHealthStatus;
    private JButton btnSave;
    private JButton btnCancel;

    /**
     * Method: AddAnimalDialog Constructor
     * Instantiated and displayed from the main application frame when a user chooses
     * to add a new animal record.
     */
    public AddAnimalDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Add New Animal", true);

        //passing a reference to the AnimalManager object
        this.dbManager = dbManager;

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Tag ID:"));
        txtId = new JTextField();
        //Apply Numeric filter to text field
        PlainDocument idDoc = (PlainDocument) txtId.getDocument();
        idDoc.setDocumentFilter(new NumericDocumentFilter());

        formPanel.add(txtId);

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Species:"));
        txtSpecies = new JTextField();
        formPanel.add(txtSpecies);

        formPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        //Apply Numeric filter to text field
        PlainDocument ageDoc = (PlainDocument) txtAge.getDocument();
        ageDoc.setDocumentFilter(new NumericDocumentFilter());
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Gender:"));
        cmbGender = new JComboBox<>(new String[] { "Male", "Female", "Unknown" });
        formPanel.add(cmbGender);

        formPanel.add(new JLabel("Weight (kg):"));
        txtWeight = new JTextField();
        //Apply Decimal filter to Weight text field
        PlainDocument weightDoc = (PlainDocument) txtWeight.getDocument();
        weightDoc.setDocumentFilter(new DecimalDocumentFilter());
        formPanel.add(txtWeight);

        formPanel.add(new JLabel("Health Status:"));
        cmbHealthStatus = new JComboBox<>(new String[] {
                "Healthy", "Injured", "Sick", "Recovering"
        });
        formPanel.add(cmbHealthStatus);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String name = txtName.getText().trim();
                String species = txtSpecies.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());
                String gender = (String) cmbGender.getSelectedItem();
                double weight = Double.parseDouble(txtWeight.getText().trim());
                String healthStatus = (String) cmbHealthStatus.getSelectedItem();

                WildAnimal animal = new WildAnimal(id, species, name, age, gender, weight, healthStatus);
                boolean success = this.dbManager.insertWildAnimal(animal);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Animal added successfully!");
                    dispose();
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}