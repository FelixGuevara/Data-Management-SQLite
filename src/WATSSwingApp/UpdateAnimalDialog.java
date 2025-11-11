/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
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

/**
 * A modal dialog window for updating the details of an existing animal record in the Wildlife Animal Tracking System (WATS).
 * <p>
 * This dialog retrieves the animal data using the provided Tag ID and populates the form fields for editing.
 * Input validation is applied using document filters to ensure numeric and decimal values are correctly entered.
 * Upon submission, the updated data is validated and sent to the {@link DatabaseManager} for persistence.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class UpdateAnimalDialog extends JDialog {

    /** Text field for animal species. */
    private JTextField txtSpecies;

    /** Text field for animal name. */
    private JTextField txtName;

    /** Text field for animal age (numeric only). */
    private JTextField txtAge;

    /** Combo box for selecting animal gender. */
    private JComboBox<String> cmbGender;

    /** Text field for animal weight (decimal only). */
    private JTextField txtWeight;

    /** Combo box for selecting health status. */
    private JComboBox<String> cmbHealthStatus;

    /** Button to save the updated animal record. */
    private JButton btnSave;

    /** Button to cancel and close the dialog. */
    private JButton btnCancel;

    /** Reference to the database manager for updating animal records. */
    private final DatabaseManager dbManager;

    /** The Tag ID of the animal being updated. */
    private final int tagId;

    /**
     * Constructs a new {@code UpdateAnimalDialog} instance.
     * <p>
     * This dialog is instantiated and displayed from the main application frame when a user chooses
     * to update an animal record from the table. It retrieves the existing animal data and populates
     * the form fields for editing.
     * </p>
     *
     * @param parent    the parent {@link JFrame} for positioning and modality
     * @param dbManager the {@link DatabaseManager} instance used to update the animal record
     * @param tagId     the unique Tag ID of the animal to be updated
     */
    public UpdateAnimalDialog(JFrame parent, DatabaseManager dbManager, int tagId) {
        super(parent, "Update Animal - Tag ID: " + tagId, true);
        this.dbManager = dbManager;
        this.tagId = tagId;

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Retrieve animal data
        WildAnimal animal = dbManager.getWildAnimalById(tagId);
        if (animal == null) {
            JOptionPane.showMessageDialog(this, "Animal not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Species
        formPanel.add(new JLabel("Species:"));
        txtSpecies = new JTextField(animal.getSpecies());
        formPanel.add(txtSpecies);

        // Name
        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField(animal.getName());
        formPanel.add(txtName);

        // Age
        formPanel.add(new JLabel("Age:"));
        txtAge = new JTextField(String.valueOf(animal.getAge()));
        PlainDocument ageDoc = (PlainDocument) txtAge.getDocument();
        ageDoc.setDocumentFilter(new NumericDocumentFilter());
        formPanel.add(txtAge);

        // Gender
        formPanel.add(new JLabel("Gender:"));
        cmbGender = new JComboBox<>(new String[] { "Male", "Female", "Unknown" });
        cmbGender.setSelectedItem(animal.getGender());
        formPanel.add(cmbGender);

        // Weight
        formPanel.add(new JLabel("Weight (kg):"));
        txtWeight = new JTextField(String.valueOf(animal.getWeight()));
        PlainDocument weightDoc = (PlainDocument) txtWeight.getDocument();
        weightDoc.setDocumentFilter(new DecimalDocumentFilter());
        formPanel.add(txtWeight);

        // Health Status
        formPanel.add(new JLabel("Health Status:"));
        cmbHealthStatus = new JComboBox<>(new String[] { "Healthy", "Injured", "Sick", "Recovering" });
        cmbHealthStatus.setSelectedItem(animal.getHealthStatus());
        formPanel.add(cmbHealthStatus);

        // Button panel
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
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}