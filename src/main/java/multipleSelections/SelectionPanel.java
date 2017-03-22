/**
 * Erstellt am: Jan 16, 2014 12:34:06 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package multipleSelections;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import misc.CustomDialog;
import misc.DisplayText;
import misc.Messages;
import misc.SearchPanel;



public abstract class SelectionPanel extends JScrollPane {

    private SelectionDropdownMap dropdownElements = null;
    private JMenu                menu             = null;



    public SelectionPanel(Object firstItem, String firstItemIdentifier) {

        super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.dropdownElements = new SelectionDropdownMap(firstItemIdentifier,
                firstItem);
        this.initialize();
    }

    /**
     *
     * @param textarea
     *            the textarea, which the searchfield should be associated with
     * @param regexElements
     *            a hashmap containing regex elements for highlighting. if
     *            regexElements is null, no dropdownlist will be provided
     */
    public SelectionPanel(SelectionDropdownMap dropdownElements) {

        super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.dropdownElements = dropdownElements;
        this.initialize();
    }

    private void addItemToMenu(String caption) {

        ActionListener valueChanged = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                SelectionPanel objectInstance = SelectionPanel.this;

                Object item = objectInstance.dropdownElements
                        .getDropdownValueForKey(((JMenuItem) e.getSource())
                                .getText());
                objectInstance.loadSettings(item);
            }
        };

        JMenuItem tempItem = new JMenuItem(caption);
        tempItem.addActionListener(valueChanged);
        this.menu.add(tempItem);

    }

    public JPanel getDropdownPanel() {

        if (this.dropdownElements != null) {
            this.menu = new JMenu("I");
            JMenuBar tempBar = new JMenuBar();
            tempBar.add(this.menu);

            JButton addItem = new JButton(new ImageIcon(
                    SearchPanel.class.getResource("/pictures/add.png")));

            addItem.setPreferredSize(new Dimension(20, 20));


            for (String temp:this.dropdownElements.getDropdownHashmapKeyset()) {
                this.addItemToMenu(temp);
            }

            JPanel dropdownElements = new JPanel();

            dropdownElements.setLayout(new GridLayout(1, 2));
            dropdownElements.add(tempBar);

            ActionListener addItemAction = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    SelectionPanel objectInstance = SelectionPanel.this;


                    CustomDialog q = new CustomDialog(
                            Messages.getString("add.new.item.to.dropdown"),
                            Messages.getString("enter.identifier.for.new.item"),
                            true);
                    q.setVisible(true);

                    if (q.getAnswerForDefaultActionlistener()) {
                        String newIdentifier = q
                                .getInputstringForStringdialog();
                        Object newItem = objectInstance.getNewItemInstance();

                        if (((newIdentifier != null) && !newIdentifier
                                .equals("")) && (newItem != null)) {

                            if (!objectInstance.dropdownElements
                                    .addNewItemToDropDownList(newIdentifier,
                                            newItem, false)) {

                                CustomDialog qu = new CustomDialog(
                                        null,
                                        Messages.getString("identifier.already.exists.overwrite"),
                                        false);
                                qu.setVisible(true);

                                if (qu.getAnswerForDefaultActionlistener()) {
                                    objectInstance.dropdownElements
                                            .addNewItemToDropDownList(
                                                    newIdentifier, newItem,
                                                    true);
                                }

                            }
                            else {
                                objectInstance.addItemToMenu(newIdentifier);
                            }

                        }
                        else {
                            new DisplayText(
                                    Messages.getString("errorOccured"),
                                    Messages.getString("neither.new.element.nor.identifier.string.must.be.empty"));
                        }
                    }
                }
            };

            addItem.addActionListener(addItemAction);
            // addNewRegexElements.add(this.newRegexIdentifierTextfield);
            // TODO add deleteRegexButton
            // addNewRegexElements.add(addRegex);
            // regexDropdownElements.add(addNewRegexElements,
            // BorderLayout.EAST);



            // this.regexDropdown.addActionListener(valueChanged);

            dropdownElements.add(addItem);

            return dropdownElements;
        }
        else
            return null;
    }

    public abstract Object getNewItemInstance();


    private void initialize() {

        if (this.dropdownElements != null) {
            this.loadSettings(this.dropdownElements.getDefaultItem());
        }

    }

    public abstract void loadSettings(Object item);


    public abstract void saveCurrentSettings();



}