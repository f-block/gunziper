/**
 * Erstellt am: Jan 16, 2014 12:34:06 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;

import variables.RegexDropdownMap;


class SearchHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    public SearchHighlighter(Color color) {

        super(color);
    }
}

public class SearchPanel extends JPanel {

    public JTextArea                           textarea            = null;
    private JTextField                         searchField         = null;
    private int                                destinationMatch    = 1;
    private JLabel                             statusLine          = null;
    private JButton                            nextMatchButton     = null;
    private JButton                            previousMatchButton = null;
    private RegexDropdownMap                   regexElements       = null;
    // private JComboBox<String> regexDropdown = null;
    private JMenu                              menu                = null;
    private String                             explanationString   = "";
    // private final JTextField newRegexIdentifierTextfield = null;

    // for the searchField whole match
    private final Highlighter.HighlightPainter groupHighlighter;

    // for the searchField group 1 match
    private final Highlighter.HighlightPainter matchHighlighter;


    /**
     * 
     * @param textarea
     *            the textarea, which the searchfield should be associated with
     * @param regexElements
     *            a hashmap containing regex elements for highlighting. if
     *            regexElements is null, no dropdownlist will be provided
     */
    public SearchPanel(JTextArea textarea, RegexDropdownMap regexElements,
            boolean includeMatchescountLabel, String explanationString) {

        // super();
        this.explanationString = explanationString;
        this.searchField = new JTextField();
        this.searchField.setMinimumSize(new Dimension(Integer.MAX_VALUE,
                this.searchField.getPreferredSize().height));
        this.searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                this.searchField.getPreferredSize().height));

        this.statusLine = new JLabel();

        this.nextMatchButton = new JButton(new ImageIcon(SearchPanel.class
                .getResource("/pictures/forward.png")));
        this.previousMatchButton = new JButton(new ImageIcon(SearchPanel.class
                .getResource("/pictures/back.png")));

        this.groupHighlighter = new SearchHighlighter(Color.orange);
        this.matchHighlighter = new SearchHighlighter(Color.green);

        this.regexElements = regexElements;


        this.textarea = textarea;
        this.initialize(includeMatchescountLabel);
    }

    private void addNewregexToMenu(String caption) {

        ActionListener valueChanged = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                SearchPanel objectInstance = SearchPanel.this;

                String regex = objectInstance.regexElements
                        .getRegexDropdownValueForKey(((JMenuItem) e.getSource())
                                .getText());
                objectInstance.searchField.setText(regex);
                objectInstance.searchField.requestFocus();
            }
        };

        JMenuItem tempItem = new JMenuItem(caption);
        tempItem.addActionListener(valueChanged);
        this.menu.add(tempItem);

    }

    private JPanel getRegexDropdownPanel() {

        if (this.regexElements != null) {
            this.menu = new JMenu("R");
            JMenuBar tempBar = new JMenuBar();
            tempBar.add(this.menu);

            JButton addRegex = new JButton(new ImageIcon(SearchPanel.class
                    .getResource("/pictures/add.png")));

            addRegex.setPreferredSize(new Dimension(20, 20));


            for (String temp:this.regexElements.getRegexDropdownHashmapKeyset()) {
                this.addNewregexToMenu(temp);
            }


            // this.regexElements = Library.getRegexdropdownHashmap();
            // this.regexDropdown = new JComboBox(this.regexElements
            // .getRegexDropdownHashmapKeyset().toArray());
            // this.regexDropdown.setEditable(false);

            JPanel regexDropdownElements = new JPanel();
            // regexDropdownElements.setLayout(new BorderLayout());

            regexDropdownElements.setLayout(new GridLayout(1, 2));
            regexDropdownElements.add(tempBar);
            // JPanel addNewRegexElements = new JPanel();
            // addNewRegexElements.setLayout(new GridLayout(1, 2));
            // this.newRegexIdentifierTextfield = new JTextField(20);
            // JButton addRegex = new JButton(Messages.getString("addRegex"));
            ActionListener addRegexAction = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    SearchPanel objectInstance = SearchPanel.this;

                    // String newIdentifier = (String)
                    // objectInstance.regexDropdown
                    // .getSelectedItem();
                    CustomDialog q = new CustomDialog(Messages
                            .getString("add.new.regex.to.dropdown"), Messages
                            .getString("enter.identifier.for.new.regex"), true);
                    q.setVisible(true);

                    if (q.getAnswerForDefaultActionlistener()) {
                        String newIdentifier = q
                                .getInputstringForStringdialog();
                        String newRegex = objectInstance.searchField.getText();

                        if (((newIdentifier != null) && !newIdentifier
                                .equals(""))
                                && ((newRegex != null) && !newRegex.equals(""))) {

                            if (!objectInstance.regexElements
                                    .addElementToRegexDropDownList(
                                            newIdentifier, newRegex, false)) {

                                CustomDialog qu = new CustomDialog(
                                        null,
                                        Messages
                                                .getString("identifier.already.exists.overwrite"),
                                        false);
                                qu.setVisible(true);

                                if (qu.getAnswerForDefaultActionlistener()) {
                                    objectInstance.regexElements
                                            .addElementToRegexDropDownList(
                                                    newIdentifier, newRegex,
                                                    true);
                                }

                            }
                            else {
                                objectInstance.addNewregexToMenu(newIdentifier);
                            }

                        }
                        else {
                            new DisplayText(
                                    Messages.getString("errorOccured"),
                                    Messages
                                            .getString("neither.new.regex.nor.identifier.string.must.be.empty"));
                        }
                    }
                }
            };

            addRegex.addActionListener(addRegexAction);
            // addNewRegexElements.add(this.newRegexIdentifierTextfield);
            // TODO add deleteRegexButton
            // addNewRegexElements.add(addRegex);
            // regexDropdownElements.add(addNewRegexElements,
            // BorderLayout.EAST);



            // this.regexDropdown.addActionListener(valueChanged);

            regexDropdownElements.add(addRegex);

            return regexDropdownElements;
        }
        else
            return null;
    }

    public JTextField getSearchTextfield() {

        return this.searchField;
    }

    public void highlight() {

        int matches = 0;
        String regex = this.searchField.getText();
        Pattern matchPattern = null;

        // At first compile regex, no matter if a associated textarea exists, to
        // generate in every case an "invalid regex" message if necessary
        try {
            matchPattern = Pattern.compile(regex, Pattern.DOTALL);
            this.resetStatusline();
            this.removeHighlights();
        }catch (PatternSyntaxException e) {
            this.setStatusline(0, e);
        }



        if (this.textarea != null) {
            if (!regex.equals("") && (matchPattern != null)) {
                this.removeHighlights();
                boolean anyGroupMatches = false;

                try {
                    Highlighter hilite = this.textarea.getHighlighter();
                    String text = this.textarea.getText();
                    Matcher m = matchPattern.matcher(text);


                    while (m.find()) {
                        anyGroupMatches = false;
                        if (m.groupCount() == 0) {
                            hilite.addHighlight(m.start(), m.end(),
                                    this.matchHighlighter);
                            // hilite.addHighlight(m.start(1), m.end(1),
                            // groupHighlighter);

                        }
                        else if (m.start(1) >= 0) {
                            hilite.addHighlight(m.start(), m.start(1),
                                    this.matchHighlighter);
                            hilite.addHighlight(m.start(1), m.end(1),
                                    this.groupHighlighter);
                            anyGroupMatches = true;
                        }


                        if (m.groupCount() > 1) {
                            for (int i = 2; i <= m.groupCount(); i++) {
                                if ((m.start(i) >= 0) && (m.start(i - 1) >= 0)) {
                                    hilite.addHighlight(m.end(i - 1), m
                                            .start(i), this.matchHighlighter);
                                    hilite.addHighlight(m.start(i), m.end(i),
                                            this.groupHighlighter);
                                    anyGroupMatches = true;
                                }

                                else if (m.start(i) >= 0) {
                                    hilite.addHighlight(m.start(), m.start(i),
                                            this.matchHighlighter);
                                    hilite.addHighlight(m.start(i), m.end(i),
                                            this.groupHighlighter);
                                    anyGroupMatches = true;
                                }

                            }
                        }

                        if (m.groupCount() > 0) {
                            int lastGroup = 0;

                            for (int i = 1; i <= m.groupCount(); i++) {
                                if (m.start(i) >= 0) {
                                    lastGroup = i;
                                }
                            }

                            if ((lastGroup >= 0) && (m.start(lastGroup) >= 0)) {
                                hilite.addHighlight(m.end(lastGroup), m.end(),
                                        this.matchHighlighter);
                            }
                        }
                        if (!anyGroupMatches) {
                            hilite.addHighlight(m.start(), m.end(),
                                    this.matchHighlighter);
                        }
                        matches++;
                    }

                    this.destinationMatch = this.jumpToRegexMatch(regex, 1);

                    this.setStatusline(matches, null);

                }catch (BadLocationException e) {
                    // JUST IGNORE IT
                }catch (PatternSyntaxException e) {
                    this.setStatusline(0, e);
                }

            }
        }
    }

    private void initialize(boolean includeMatchescountLabel) {

        if ((this.regexElements == null)
                || (this.regexElements.getLastUsed() == null)
                || this.regexElements.getLastUsed().equals("")) {

            if (this.explanationString != null) {

                this.searchField.setText(this.explanationString);
            }
        }
        else {
            this.searchField.setText(this.regexElements.getLastUsed());
        }

        if ((this.regexElements != null) && (this.explanationString != null)) {
            this.regexElements.setExplanationString(this.explanationString);
        }


        this.searchField.setToolTipText(Messages
                .getString("regex.based.search_regex.test"));

        this.setLayout(new BorderLayout());
        this.add(this.searchField, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        this.previousMatchButton.setPreferredSize(new Dimension(20, 20));
        this.nextMatchButton.setPreferredSize(new Dimension(20, 20));
        buttons.add(this.previousMatchButton);
        buttons.add(this.nextMatchButton);

        if (this.regexElements != null) {
            JPanel temp = new JPanel();
            temp.setLayout(new GridLayout(1, 2));
            temp.add(this.getRegexDropdownPanel());
            temp.add(buttons);
            this.add(temp, BorderLayout.EAST);
        }
        else {
            this.add(buttons, BorderLayout.EAST);
        }

        if (includeMatchescountLabel) {
            this.add(this.statusLine, BorderLayout.WEST);

        }

        this.registerListeners();
        this.resetStatusline();
    }

    /**
     * 
     * @param regex
     * @param matchNumber
     * @return
     *         returns the index of the last match number
     *         that should be either the given matchNumber
     *         or the last found match (if the match number is higher
     *         than the match count)
     */
    private int jumpToRegexMatch(String regex, int matchNumber) {

        if (this.textarea != null) {
            int i = 1;
            try {
                Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(
                        this.textarea.getText());
                if (matchNumber < 1) {
                    matchNumber = 1;
                }
                while ((i <= matchNumber) && m.find()) {
                    i++;
                }
                if (matchNumber > 1) {
                    this.textarea.requestFocus();
                }
                this.textarea.setCaretPosition(m.start());

                // try to center the highlighted line in the middle
                try {
                    Object scrollpane = this.textarea.getParent().getParent();
                    if (scrollpane instanceof JScrollPane) {
                        ((JScrollPane) scrollpane)
                                .getVerticalScrollBar()
                                .setValue(
                                        Library
                                                .getValueToScrollHighlightedLineToTheMiddle(this.textarea));
                    }
                }catch (BadLocationException e) {
                    e.printStackTrace();
                }

            }catch (PatternSyntaxException ex) {
                // JUST IGNORE IT
            }catch (IllegalStateException ex) {
                // JUST IGNORE IT
            }
            return --i;
        }
        else
            return matchNumber;
    }


    private void registerListeners() {

        KeyListener searchStringEntered = new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                SearchPanel objectInstance = SearchPanel.this;

                objectInstance.destinationMatch = 1;

                objectInstance.highlight();

                if (objectInstance.regexElements != null) {
                    objectInstance.regexElements
                            .updateLastUsed(objectInstance.searchField
                                    .getText());
                }
                // TODO remove here, and only update last used when saving

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        };

        FocusListener searchFieldGainedFocus = new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                SearchPanel objectInstance = SearchPanel.this;

                objectInstance.highlight();

                // TODO setLastSearchRegexWithinVariables
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        };

        ActionListener nextMatch = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField tempTextfield = SearchPanel.this.searchField;
                SearchPanel objectInstance = SearchPanel.this;

                objectInstance.destinationMatch = objectInstance
                        .jumpToRegexMatch(tempTextfield.getText(),
                                ++objectInstance.destinationMatch);
            }
        };

        ActionListener previousMatch = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField tempTextfield = SearchPanel.this.searchField;
                SearchPanel objectInstance = SearchPanel.this;

                objectInstance.destinationMatch = objectInstance
                        .jumpToRegexMatch(tempTextfield.getText(),
                                --objectInstance.destinationMatch);
            }
        };


        this.searchField.addKeyListener(searchStringEntered);
        this.searchField.addFocusListener(searchFieldGainedFocus);
        this.nextMatchButton.addActionListener(nextMatch);
        this.previousMatchButton.addActionListener(previousMatch);
    }



    public void registerNewTextarea(JTextArea textarea) {

        this.textarea = textarea;
    }

    public void removeHighlights() {

        if (this.textarea != null) {
            Highlighter hilite = this.textarea.getHighlighter();

            for (Highlight hilite2:hilite.getHighlights()) {
                if (hilite2.getPainter() instanceof SearchHighlighter) {
                    hilite.removeHighlight(hilite2);
                }
            }

            this.resetStatusline();
        }
    }

    private void resetStatusline() {

        this.setStatusline(0, null);
    }

    private void setStatusline(int matches, Exception e) {

        if (e != null) {
            this.statusLine.setForeground(Color.RED);
            this.statusLine.setText("Invalid Regex  ");
            this.statusLine.setToolTipText(e.getMessage()
                    .replaceAll("\n", ": "));
        }
        else {
            this.statusLine.setForeground(Color.BLACK);
            this.statusLine.setText("Matches: " + matches + "  ");
            this.statusLine.setToolTipText("");
        }
    }

    public void setTextfieldSize(int size) {

        this.textarea.setColumns(size);
    }
}