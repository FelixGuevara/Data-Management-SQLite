/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
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

/**
 * A modal dialog window for adding a new animal record to the Wildlife Animal Tracking System (WATS).
 * <p>
 * This dialog provides form fields for entering animal details such as Tag ID, Name, Species, Age,
 * Gender, Weight, and Health Status. Input validation is applied using document filters to ensure
 * numeric and decimal values are correctly entered. Upon submission, the data is validated and passed
 * to the {@link DatabaseManager} for persistence.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class AddAnimalDialog extends JDialog {

    /** Reference to the database manager for saving animal records. */
    private final DatabaseManager dbManager;

    /** Text field for animal Tag ID (numeric only). */
    private JTextField txtId;

    /** Text field for animal name. */
    private JTextField txtName;

    /** Text field for animal species. */
    private JTextField txtSpecies;

    /** Text field for animal age (numeric only). */
    private JTextField txtAge;

    /** Combo box for selecting animal gender. */
    private JComboBox<String> cmbGender;

    /** Text field for animal weight (decimal only). */
    private JTextField txtWeight;

    /** Combo box for selecting health status. */
    private JComboBox<String> cmbHealthStatus;

    /** Button to save the new animal record. */
    private JButton btnSave;

    /** Button to cancel and close the dialog. */
    private JButton btnCancel;

    /**
     * Constructs a new {@code AddAnimalDialog} instance.
     * <p>
     * This dialog is instantiated and displayed from the main application frame when a user chooses
     * to add a new animal record.
     * </p>
     *
     * @param parent    the parent {@link JFrame} for positioning and modality
     * @param dbManager the {@link DatabaseManager} instance used to persist the new animal record
     */
    public AddAnimalDialog(JFrame parent, DatabaseManager dbManager) {
        super(parent, "Add New Animal", true);
        this.dbManager = dbManager;

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tag ID
        formPanel.add(new JLabel("Tag ID:"));
        txtId = new JTextField();
        PlainDocument idDoc = (PlainDocument) txtId.getDocument();
        idDoc.setDocumentFilter(new NumericDocumentFilter());
        formPanel.add(txtId);

        // Name
        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        // Species
        formPanel.add(new JLabel("Species:"));
        txtSpecies = new JTextField();
        formPanel.add(txtSpecies);

        // Age
        formPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        PlainDocument ageDoc = (PlainDocument) txtAge.getDocument();
        ageDoc.setDocumentFilter(new NumericDocumentFilter());
        formPanel.add(txtAge);

        // Gender
        formPanel.add(new JLabel("Gender:"));
        cmbGender = new JComboBox<>(new String[] { "Male", "Female", "Unknown" });
        formPanel.add(cmbGender);

        // Weight
        formPanel.add(new JLabel("Weight (kg):"));
        txtWeight = new JTextField();
        PlainDocument weightDoc = (PlainDocument) txtWeight.getDocument();
        weightDoc.setDocumentFilter(new DecimalDocumentFilter());
        formPanel.add(txtWeight);

        // Health Status
        formPanel.add(new JLabel("Health Status:"));
        cmbHealthStatus = new JComboBox<>(new String[] { "Healthy", "Injured", "Sick", "Recovering" });
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
