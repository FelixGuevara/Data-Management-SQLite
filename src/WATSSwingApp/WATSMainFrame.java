/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
 * Class: WATSMainFrame.java
 *
 * This is the main JFrame of the Wildlife Animal Tracking System program.
 * Encapsulates the main graphical user interface (GUI) frame for the application.
 * Sets up the primary window and integrates the provided AnimalManager instance
 * Using the AnimalManager object, the frame gains access to all CRUD operations/functionalities,
 * enabling seamless interaction between the user interface and the underlying data management logic.
 */

package WATSSwingApp;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

/**
 * The main JFrame for the Wildlife Animal Tracking System (WATS) application.
 * <p>
 * This class encapsulates the primary graphical user interface (GUI) frame for the application.
 * It sets up the main window, integrates the {@link DatabaseManager} instance, and provides
 * access to all CRUD operations and analytics features. The frame includes a sidebar for
 * navigation and a central panel for displaying animal records in a table.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */

public class WATSMainFrame extends JFrame {


    /** Manages database operations for animal records. */
    private DatabaseManager dbManager;

    /** Table component for displaying animal records. */
    private JTable animalTable;

    /** Table model defining columns and data for the animal table. */
    private DefaultTableModel tableModel;

    /** Main content panel for the application. */
    private JPanel mainPanel;



    /**
     * Constructs the main application frame for the Wildlife Animal Tracking System (WATS).
     * <p>
     * Initializes the frame properties and calls {@link #initUI()} to set up the user interface.
     * </p>
     */
    public WATSMainFrame() {
        setTitle("Wildlife Animal Tracking System (WATS)");
        setSize(1250, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        initUI();
    }


    /**
     * Initializes the user interface components for the main frame.
     * <p>
     * This method sets up the menu bar, sidebar with navigation buttons, and the main content panel
     * containing the animal records table. It also attaches event listeners to buttons for CRUD
     * operations and analytics.
     * </p>
     */
    private void initUI() {

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBackground(new Color(230, 230, 250));

        //Create all sidebar JButtons
        JButton btnImportCSV = new JButton("Upload Animal Records");
        btnImportCSV.addActionListener(e -> promptForDatabase());
        sidebar.add(btnImportCSV);

        JButton btnAnimals = new JButton("Add Animal Record");
        btnAnimals.addActionListener(e -> {
            AddAnimalDialog dialog = new AddAnimalDialog(WATSMainFrame.this, dbManager);
            dialog.setVisible(true);
            refreshAnimalTable();
        });
        sidebar.add(btnAnimals);

        JButton btnEditAnimal = new JButton("Update Animal Record");
        btnEditAnimal.addActionListener(e -> openEditAnimalDialog());
        sidebar.add(btnEditAnimal);

        JButton btnDeleteAnimal = new JButton("Delete Animal Record");
        btnDeleteAnimal.addActionListener(e -> handleDeleteAnimal());
        sidebar.add(btnDeleteAnimal);

        JButton btnAverageWeight = new JButton("Average Weight by Species");
        btnAverageWeight.addActionListener(e -> showAverageWeightDialog());
        sidebar.add(btnAverageWeight);

        // Create main content panel
        mainPanel = new JPanel(new BorderLayout());
        setupAnimalTable();

        // Add components to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the animal table with predefined column headers and a read-only table model.
     * <p>
     * This method sets up the {@link JTable} for displaying animal records, applies center alignment
     * to all columns for better readability, and embeds the table within a scroll pane. It also
     * invokes {@link #setupContextMenu()} to configure the table's context menu.
     * </p>
     *
     * <p>The table uses a custom {@link DefaultTableModel} that overrides
     * {@link DefaultTableModel#isCellEditable(int, int)} to make all cells read-only.</p>
     */
    private void setupAnimalTable() {
        String[] columnNames = {
                "Tag ID", "Name", "Species", "Age", "Gender", "Weight", "Health Status"
        };

        //Override isCellEditable in the DefaultTableModel for read-only cells
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are read-only
            }
        };

        animalTable = new JTable(tableModel);
        animalTable.setRowHeight(30);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply right alignment to all columns
        for (int i = 0; i < animalTable.getColumnCount(); i++) {
            animalTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(animalTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setupContextMenu();
    }

    /**
     * Configures the context menu for the animal table.
     * <p>
     * This method creates a {@link JPopupMenu} with options to update or delete selected animal records.
     * Each menu item is linked to its respective action handler:
     * <ul>
     *     <li><b>Update Animal</b> → {@link #openEditAnimalDialog()}</li>
     *     <li><b>Delete Animal</b> → {@link #handleDeleteAnimal()}</li>
     * </ul>
     * <p>The context menu is then attached to the {@link JTable} component.</p>
     *
     */
    private void setupContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Update Animal");
        JMenuItem deleteItem = new JMenuItem("Delete Animal");

        editItem.addActionListener(e -> openEditAnimalDialog());
        deleteItem.addActionListener(e -> handleDeleteAnimal());

        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        animalTable.setComponentPopupMenu(popupMenu);
    }

    /**
     * Opens a file chooser dialog to allow the user to select a SQLite database file containing animal data.
     * <p>
     * This method uses {@link DatabaseFileChooser} to prompt the user for a database file. If a valid file
     * is selected, a new {@link DatabaseManager} instance is created with the chosen file path, and the
     * animal table is refreshed to display the imported records.
     * </p>
     *
     * <p>Displays an error message if no file is selected and continues without database support.</p>
     */
    public void promptForDatabase() {
        DatabaseFileChooser chooser = new DatabaseFileChooser();
        // Pass parent frame
        String dbPath = chooser.chooseDatabaseFile(this);
        if (dbPath != null) {
            dbManager = new DatabaseManager(dbPath);
            refreshAnimalTable();
        }
    }

    /**
     * Refreshes the animal table by clearing existing rows and repopulating it with the latest data
     * retrieved from the database.
     * <p>
     * This method calls {@link DatabaseManager#getAllWildAnimalRecords()} to fetch all animal records
     * and updates the {@link JTable} with the new data. Each record is displayed in a row with columns
     * for Tag ID, Species, Name, Age, Gender, Weight, and Health Status.
     * </p>
     */
    public void refreshAnimalTable() {

        List<WildAnimal> animals = dbManager.getAllWildAnimalRecords();
        tableModel.setRowCount(0); // Clear existing rows

        for (WildAnimal animal : animals) {
            Object[] row = {
                    animal.getId(),
                    animal.getSpecies(),
                    animal.getName(),
                    animal.getAge(),
                    animal.getGender(),
                    animal.getWeight(),
                    animal.getHealthStatus()
            };
            tableModel.addRow(row);
        }
    }

    /**
     * Opens a dialog window to update the details of a selected animal record.
     * <p>
     * This method validates that the table contains data and that a row is selected before launching
     * the {@link UpdateAnimalDialog}. If no records exist or no row is selected, an appropriate warning
     * message is displayed. After the dialog is closed, the animal table is refreshed to reflect any changes.
     * </p>
     *
     * <p>The selected animal's Tag ID is retrieved from the first column of the selected row.</p>
     */
    private void openEditAnimalDialog() {

        if (animalTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No animal records available to update.", "Empty Table", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = animalTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an animal to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tagId = (int) tableModel.getValueAt(selectedRow, 0);
        UpdateAnimalDialog dialog = new UpdateAnimalDialog(this, dbManager, tagId);
        dialog.setVisible(true);
        refreshAnimalTable();
    }

    /**
     * Handles the deletion of a selected animal record from the table.
     * <p>
     * This method validates that the table contains data and that a row is selected before proceeding.
     * It prompts the user for confirmation and, if confirmed, calls {@link DatabaseManager#deleteWildAnimal(int)}
     * to delete the record. Displays success or error messages based on the outcome and refreshes the table
     * after a successful deletion.
     * </p>
     *
     * <p> The selected animal's Tag ID is retrieved from the first column of the selected row. </p>
     */
    private void handleDeleteAnimal() {

        if (animalTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No animal records available to delete.", "Empty Table", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = animalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tagId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete animal with Tag ID " + tagId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );


        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dbManager.deleteWildAnimal(tagId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Animal deleted successfully.");
                refreshAnimalTable(); // Reload table from DB
            }
            else {
                JOptionPane.showMessageDialog(this, "No record found with Tag ID " + tagId, "Delete Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Prompts the user to enter a species name and calculates the average weight of all animals
     * belonging to that species using the database.
     * <p>
     * This method displays an input dialog for the user to enter a species name. It then executes
     * an SQL query to compute the average weight of animals matching the specified species. The result
     * is displayed in an information dialog. If no animals are found or an error occurs, an appropriate
     * message is shown.
     * </p>
     *
     * <p> Uses {@link DatabaseManager#connect()} to establish a connection and performs the
     * calculation using {@link PreparedStatement} and {@link ResultSet}.</p>
     */
    private void showAverageWeightDialog() {
        String species = JOptionPane.showInputDialog(this, "Enter species name:", "Average Weight", JOptionPane.QUESTION_MESSAGE);

        if (species != null && !species.trim().isEmpty()) {
            String sql = "SELECT AVG(weight) AS avg_weight FROM WildAnimals WHERE species = ?";

            try (Connection conn = dbManager.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, species.trim());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    double average = rs.getDouble("avg_weight");
                    if (rs.wasNull()) {
                        JOptionPane.showMessageDialog(this, "No animals found for species: " + species, "Average Weight", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String message = String.format("Average weight for species '%s': %.2f kg", species, average);
                        JOptionPane.showMessageDialog(this, message, "Average Weight", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
