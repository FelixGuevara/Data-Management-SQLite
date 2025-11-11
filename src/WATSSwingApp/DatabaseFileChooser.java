/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
 * Class: DatabaseFileChooser.java
 *
 * This is a utility class that provides a graphical file chooser dialog for selecting
 * SQLite database files. It filters visible files to only show .db and .sqlite extensions,
 * ensuring users select valid database files for use within the Wildlife Animal Tracking System application.
 */
package WATSSwingApp;

import javax.swing.*;
import java.awt.*;


/**
 * A utility class that provides a graphical file chooser dialog for selecting SQLite database files.
 * <p>
 * This class uses a native {@link FileDialog} to allow users to select a database file for use within
 * the Wildlife Animal Tracking System (WATS) application. It ensures that users select a valid file
 * and returns the full path of the chosen file. If no file is selected, a message is displayed and
 * {@code null} is returned.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class DatabaseFileChooser {

    /**
     * Displays a file chooser dialog for selecting a SQLite database file.
     * <p>
     * The dialog is modal and blocks user interaction until a file is selected or the dialog is closed.
     * If a file is chosen, the method returns its absolute path. If no file is selected, a message
     * is shown and {@code null} is returned.
     * </p>
     *
     * @param parent the parent {@link JFrame} for the dialog (used for positioning and modality)
     * @return the absolute path of the selected database file, or {@code null} if no file was selected
     */
    public String chooseDatabaseFile(JFrame parent) {
        FileDialog fd = new FileDialog(parent, "Choose a file", FileDialog.LOAD);
        fd.setTitle("Select Wildlife Tracking SQLite Database File");

        fd.setVisible(true);
        String filename = fd.getFile();
        String directory = fd.getDirectory();

        if (filename != null) {
            // Return full path of selected file
            return directory + filename;
        } else {
            JOptionPane.showMessageDialog(parent,
                    "No file selected. Application will continue without database support.");
            return null;
        }
    }
}


