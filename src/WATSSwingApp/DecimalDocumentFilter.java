/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
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

/**
 * A custom {@link DocumentFilter} that restricts input to decimal numeric values.
 * <p>
 * This filter is designed for text components where decimal number input is required,
 * such as the weight field in the Wildlife Animal Tracking System (WATS) application.
 * It prevents the entry of invalid characters at the UI level, ensuring cleaner and safer
 * data input by allowing only digits and a decimal point.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class DecimalDocumentFilter extends DocumentFilter {

    /**
     * Inserts text into the document if it matches the decimal numeric pattern.
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
        if (string != null && string.matches("[0-9.]*")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    /**
     * Replaces text in the document if it matches the decimal numeric pattern.
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
        if (text != null && text.matches("[0-9.]*")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
