/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 25, 2025,
 * Class: NumericDocumentFilter.java
 *
 * This is a custom DocumentFilter class that restricts input to numeric characters only.
 * This filter is typically applied to text components to ensure that only digits
 * are entered, preventing invalid input at the UI level. it enforces integer numeric-only input.
 */

package WATSSwingApp;

import javax.swing.text.*;

public class NumericDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.matches("\\d+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.matches("\\d+")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}

