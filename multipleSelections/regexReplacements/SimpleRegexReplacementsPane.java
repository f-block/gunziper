/**
 * Erstellt am: Aug 29, 2014 3:45:02 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package multipleSelections.regexReplacements;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import multipleSelections.SelectionPanel;
import unpacking.api.SimpleRegexReplacement;


public class SimpleRegexReplacementsPane extends SelectionPanel {

    private JTextField encryptIdentifierTextfield    = null;
    private JTextField encryptRegexTextfield         = null;
    private JTextField encryptReplaceStringTextfield = null;
    private JTextField decryptIdentifierTextfield    = null;
    private JTextField decryptRegexTextfield         = null;
    private JTextField decryptReplaceStringTextfield = null;

    public SimpleRegexReplacementsPane() {

        super(new SimpleRegexReplacement("exampleDoNothing",
                "exampleRemoveHidden", "", "",
                "<input [^>]*?type=\"(hidden)\"", "text"), "firstRule");
        this.getViewport().add(this.getPanel());
    }

    @Override
    public Object getNewItemInstance() {

        return new SimpleRegexReplacement("exampleDoNothing",
                "exampleRemoveHidden", "", "",
                "<input [^>]*?type=\"(hidden)\"", "text");
    }

    /*
     * (non-Javadoc)
     *
     * @see multipleSelections.SelectionPanel#getNewItemInstance()
     */
    private JPanel getPanel() {

        JPanel panel = new JPanel(new GridLayout(4, 2));

        this.encryptIdentifierTextfield = new JTextField(20);
        this.encryptRegexTextfield = new JTextField(20);
        this.encryptReplaceStringTextfield = new JTextField(20);
        this.decryptIdentifierTextfield = new JTextField(20);
        this.decryptRegexTextfield = new JTextField(20);
        this.decryptReplaceStringTextfield = new JTextField(20);

        panel.add(this.getDropdownPanel());
        panel.add(new JPanel());
        panel.add(this.encryptIdentifierTextfield);
        panel.add(this.encryptRegexTextfield);
        panel.add(this.encryptReplaceStringTextfield);
        panel.add(this.decryptIdentifierTextfield);
        panel.add(this.decryptRegexTextfield);
        panel.add(this.decryptReplaceStringTextfield);

        return panel;
    }


    /*
     * (non-Javadoc)
     *
     * @see multipleSelections.SelectionPanel#loadSettings(java.lang.Object)
     */
    @Override
    public void loadSettings(Object item) {

    }

    /*
     * (non-Javadoc)
     *
     * @see multipleSelections.SelectionPanel#saveCurrentSettings()
     */
    @Override
    public void saveCurrentSettings() {

    }



}
