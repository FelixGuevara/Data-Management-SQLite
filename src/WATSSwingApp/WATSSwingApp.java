/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
 * Class: WATSSwingApp.java
 *
 * This is the main class of the Wildlife Animal Tracking System program.
 * It provides a Swing GUI for WATS Application for users to interact with the system.
 * Users can add, remove, display, and load Wild-Animals from a SQLite database.
 * The program demonstrates basic SQLite database access, object-oriented design, and input validation.
 */
package WATSSwingApp;

import javax.swing.*;


/**
 * The main entry point for the Wildlife Animal Tracking System (WATS) application.
 * <p>
 * This class initializes the Swing-based graphical user interface (GUI) for the WATS application.
 * Users can interact with the system to add, remove, display, and load wild animal records from a SQLite database.
 * The program demonstrates basic SQLite database access, object-oriented design, and input validation.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class WATSSwingApp {

    /**
     * Launches the WATS application.
     * <p>
     * This method serves as the entry point of the program. It uses {@link SwingUtilities#invokeLater(Runnable)}
     * to ensure that the GUI is created and updated on the Event Dispatch Thread (EDT).
     * After the main frame is displayed, the user is prompted to select a database file.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WATSMainFrame mainFrame = new WATSMainFrame();
            mainFrame.setVisible(true);

            // Prompt user to select a database file after GUI is visible
            mainFrame.promptForDatabase();
        });
    }
}
