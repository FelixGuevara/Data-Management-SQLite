/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 25, 2025,
 * Class: UpdateAnimalDialog.java
 *
 * This is a modal dialog window used to update the details of an existing animal record
 * in the Wildlife Animal Tracking System (WATS). It retrieves the animal data using the
 * provided tag ID and populates the form fields for editing.
 * Input filters are applied to ensure numeric and decimal validation.Upon submission,
 * the updated data is validated and sent to the DatabaseManager for persistence.
 */

package WATSSwingApp;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateAnimalDialog extends JDialog {

    private JTextField txtSpecies;
    private JTextField txtName;
    private JTextField txtAge;
    private JComboBox<String> cmbGender;
    private JTextField txtWeight;
    private JComboBox<String> cmbHealthStatus;
    private JButton btnSave;
    private JButton btnCancel;

    private final DatabaseManager dbManager;
    private final int tagId;

    /**
     * Method: UpdateAnimalDialog Constructor
     * Instantiated and displayed from the main application frame when a user chooses
     * to update an animal record from the table.
     */
    public UpdateAnimalDialog(JFrame parent, DatabaseManager dbManager, int tagId) {
        super(parent, "Update Animal - Tag ID: " + tagId, true);
        this.dbManager = dbManager;
        this.tagId = tagId;

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        WildAnimal animal = dbManager.getWildAnimalById(tagId);
        if (animal == null) {
            JOptionPane.showMessageDialog(this, "Animal not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Species:"));
        txtSpecies = new JTextField(animal.getSpecies());
        formPanel.add(txtSpecies);

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField(animal.getName());
        formPanel.add(txtName);

        formPanel.add(new JLabel("Age:"));
        txtAge = new JTextField(String.valueOf(animal.getAge()));
        //Apply Numeric filter to text field
        PlainDocument ageDoc = (PlainDocument) txtAge.getDocument();
        ageDoc.setDocumentFilter(new NumericDocumentFilter());
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Gender:"));
        cmbGender = new JComboBox<>(new String[] { "Male", "Female", "Unknown" });
        cmbGender.setSelectedItem(animal.getGender());
        formPanel.add(cmbGender);

        formPanel.add(new JLabel("Weight (kg):"));
        txtWeight = new JTextField(String.valueOf(animal.getWeight()));
        //Apply Decimal filter to Weight text field
        PlainDocument weightDoc = (PlainDocument) txtWeight.getDocument();
        weightDoc.setDocumentFilter(new DecimalDocumentFilter());
        formPanel.add(txtWeight);

        formPanel.add(new JLabel("Health Status:"));
        cmbHealthStatus = new JComboBox<>(new String[] {
                "Healthy", "Injured", "Sick", "Recovering"
        });

        cmbHealthStatus.setSelectedItem(animal.getHealthStatus());
        formPanel.add(cmbHealthStatus);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Update");
        btnCancel = new JButton("Cancel");

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            try {
                String species = txtSpecies.getText().trim();
                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());
                String gender = (String) cmbGender.getSelectedItem();
                double weight = Double.parseDouble(txtWeight.getText().trim());
                String healthStatus = (String) cmbHealthStatus.getSelectedItem();

                WildAnimal updatedAnimal = new WildAnimal(tagId, species, name, age, gender, weight, healthStatus);
                boolean success = this.dbManager.updateWildAnimal(updatedAnimal);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Animal updated successfully!");
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}