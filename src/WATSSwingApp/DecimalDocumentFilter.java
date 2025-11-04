/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 25, 2025,
 * Class: DecimalDocumentFilter.java
 *
 * This filter is designed for text components where decimal number input is required,
 * such as weight field in the WATS application. It prevents the entry of invalid characters
 * at the UI level, ensuring cleaner and safer data input. It enforces decimal numeric-only input.
 */
package WATSSwingApp;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DecimalDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.matches("[0-9.]*")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.matches("[0-9.]*")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
