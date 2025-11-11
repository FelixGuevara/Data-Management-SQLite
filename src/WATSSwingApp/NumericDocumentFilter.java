/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
 * Class: NumericDocumentFilter.java
 *
 * This is a custom DocumentFilter class that restricts input to numeric characters only.
 * This filter is typically applied to text components to ensure that only digits
 * are entered, preventing invalid input at the UI level. it enforces integer numeric-only input.
 */

package WATSSwingApp;

import javax.swing.text.*;

/**
 * A custom {@link DocumentFilter} that restricts input to numeric characters only.
 * <p>
 * This filter is typically applied to text components to ensure that only digits are entered,
 * preventing invalid input at the UI level. It enforces integer numeric-only input, making it
 * suitable for fields such as Tag ID or Age in the Wildlife Animal Tracking System (WATS) application.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class NumericDocumentFilter extends DocumentFilter {

    /**
     * Inserts text into the document if it matches the numeric pattern (digits only).
     *
     * @param fb     the {@link FilterBypass} to delegate changes
     * @param offset the position in the document to insert the text
     * @param string the text to insert
     * @param attr   the attributes for the inserted content
     * @throws BadLocationException if the insertion is not valid
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string != null && string.matches("\\d+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    /**
     * Replaces text in the document if it matches the numeric pattern (digits only).
     *
     * @param fb     the {@link FilterBypass} to delegate changes
     * @param offset the position in the document to replace text
     * @param length the length of text to replace
     * @param text   the new text to insert
     * @param attrs  the attributes for the inserted content
     * @throws BadLocationException if the replacement is not valid
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text != null && text.matches("\\d+")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}

