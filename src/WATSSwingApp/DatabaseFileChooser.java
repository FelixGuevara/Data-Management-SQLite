/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 31, 2025,
 * Class: DatabaseFileChooser.java
 *
 * This is a utility class that provides a graphical file chooser dialog for selecting
 * SQLite database files. It filters visible files to only show .db and .sqlite extensions,
 * ensuring users select valid database files for use within the Wildlife Animal Tracking System application.
 */
package WATSSwingApp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DatabaseFileChooser {
    public String chooseDatabaseFile(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Wildlife Tracking SQLite Database File");

        // Set filter for .db and .sqlite files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SQLite Database Files (*.db, *.sqlite)", "db", "sqlite");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        else {
            JOptionPane.showMessageDialog(parent, "No file selected. Application will continue without database support.");
            return null;
        }
    }
}

