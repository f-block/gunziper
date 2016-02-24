
package misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;

import variables.RegexDropdownMap;
import variables.Variables;


public class DisplayText extends JFrame implements WindowListener {

    public static DisplayText getInstanceOfDebugWindow() {

        if (DisplayText.debugWindowInstance == null) {
            DisplayText.debugWindowInstance = new DisplayText("Debug mode",
                    true);
        }
        else {
            DisplayText.debugWindowInstance.initDebugThread();
        }
        return DisplayText.debugWindowInstance;
    }

    public static DisplayText getInstanceOfRegexTestWindow() {

        if (DisplayText.regexTestWindowInstance == null) {
            DisplayText.regexTestWindowInstance = new DisplayText("Regex Test");
        }
        return DisplayText.regexTestWindowInstance;
    }

    private final JTextArea    mainTextArea                  = new JTextArea(
            30, 50);

    private final JScrollPane  scrollingArea                 = new JScrollPane(
            this.mainTextArea);

    private final JPanel       content                       = new JPanel();

    // private JTextField searchField;
    private static Thread      debugWindowRefreshThread;


    private JLabel             statusLine;

    private JPanel             container;

    private JTextField         firstRequestNumberTextfield;
    //
    private boolean            isIntruderComparer            = false;

    private boolean            isDebugWindow                 = false;
    private boolean            isIntruderComparerInterrupted = false;
    private static DisplayText regexTestWindowInstance       = null;
    private static DisplayText debugWindowInstance;
    private RegexDropdownMap   regexDropdownList             = null;
    private SearchPanel        searchPanel                   = null;


    /**
     * Displays the Regex Test window
     *
     * @param title
     */
    private DisplayText(String title) {

        // Regex Test Window
        this.initialize(true);
        this.setTitle(title);
        this.setAlwaysOnTop(true);
        // this.blueHighlighter = new RegexTestHighlighter(Color.blue);
        // this.orangeHighlighter = new RegexTestHighlighter(Color.orange);
        this.container = new JPanel();
        this.regexDropdownList = Variables.getInstance()
                .getRegextestRegexdropdownHashmap();
        this.initializeSearchfield(1);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.pack();

    }

    /**
     * Displays the Debug Mode Window
     *
     * @param title
     * @param debugMode
     *            Value doesn't matter, as long as second parameter is boolean,
     *            the result will be the Debug Mode Window
     */
    private DisplayText(String title, boolean debugMode) {

        this.isDebugWindow = true;
        this.addWindowListener(this);
        this.initialize(false);
        this.setTitle(title);
        JButton clearDebug = new JButton("Clear");
        ActionListener clearDebugAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Variables.getInstance().setDebugText("");
            }
        };
        clearDebug.addActionListener(clearDebugAction);
        this.container = new JPanel();
        this.container.setLayout(new GridLayout(2, 1));
        this.container.add(clearDebug);
        this.initializeSearchfield(2);
        this.finish(false);
        this.initDebugThread();
    }

    /**
     * Just Display Information (like occured errors)
     *
     * @param title
     * @param text
     */
    public DisplayText(String title, String text) {

        this.initialize(false);
        this.setTitle(title);
        this.mainTextArea.setText(text);
        this.finish(true);
    }

    /**
     * Display Window for Intruder Comparer
     *
     * @param title
     * @param text
     * @param searchField
     *            whether or not the search field should be displayed
     */
    public DisplayText(String title, String text, boolean intruderComparerWindow) {

        this.isIntruderComparer = true;
        this.addWindowListener(this);
        this.initialize(false);
        this.setTitle(title);
        this.regexDropdownList = Variables.getInstance()
                .getIntruderComparerRegexdropdownHashmap();
        this.mainTextArea.setText(text);
        this.container = new JPanel();
        this.initializeSearchfield(3);
        // this.searchField.setText(Variables.getInstance()
        // .getLastEnteredIntruderComparerSearchRegex());
        // this.highlight();

        this.finish(false);
    }

    public void finish(boolean setCloseOperation) {

        this.pack();
        this.setVisible(true);
        if (setCloseOperation) {
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    public JTextArea getMainTextarea() {

        return this.mainTextArea;
    }


    private void initDebugThread() {

        DisplayText.debugWindowRefreshThread = new Thread() {

            @Override
            public void run() {

                while (!this.isInterrupted()) {
                    DisplayText.this.mainTextArea.setText(Variables
                            .getInstance().getDebugText());
                    // DisplayText.this.highlight(DisplayText.this.searchField
                    // .getText(), false, false);
                    DisplayText.this.searchPanel.highlight();
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        this.interrupt();
                    }
                }
            }
        };
        DisplayText.debugWindowRefreshThread.start();
    }

    public void initialize(boolean editable) {

        this.content.setLayout(new BorderLayout());
        this.content.add(this.scrollingArea, BorderLayout.CENTER);
        this.mainTextArea.setEditable(editable);
        this.mainTextArea.setLineWrap(true);
        this.mainTextArea.setSelectionColor(new Color(255, 50, 0));
        this.setContentPane(this.content);
    }

    /**
     *
     * @param initMode
     *            1 for regex test, 2 for debug mode, 3 for intruder comparer
     */
    private void initializeSearchfield(int initMode) {


        if (initMode == 2) {
            this.searchPanel = new SearchPanel(this.mainTextArea,
                    this.regexDropdownList, true,
                    Messages.getString("regex.based.search_debug.window"));
        }
        else {
            this.searchPanel = new SearchPanel(this.mainTextArea,
                    this.regexDropdownList, true,
                    Messages.getString("regex.based.search_regex.test"));
        }

        switch (initMode) {
        case 1:
            this.content.add(this.searchPanel, BorderLayout.SOUTH);
            break;


        case 2:
            this.container.add(this.searchPanel);
            this.content.add(this.container, BorderLayout.SOUTH);
            break;


        case 3:
            JPanel setValueFields = new JPanel();
            setValueFields.setLayout(new GridLayout(1, 2));
            this.container.setLayout(new GridLayout(2, 1));
            JButton setValues = new JButton(Messages.getString("set"));
            this.firstRequestNumberTextfield = new JTextField(20);

            ActionListener setAction = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        DisplayText.this
                        .resetRequestNumbers(Integer
                                .parseInt(DisplayText.this.firstRequestNumberTextfield
                                        .getText()));
                    }catch (Exception ex) {
                        // just ignore
                    }
                }
            };

            setValues.addActionListener(setAction);
            setValueFields.add(this.firstRequestNumberTextfield);
            setValueFields.add(setValues);
            this.container.add(setValueFields);
            this.container.add(this.searchPanel);
            this.content.add(this.container, BorderLayout.SOUTH);
            break;

        default:
            break;
        }

    }

    public boolean intruderComparerHasBeenInterrupted() {

        return this.isIntruderComparerInterrupted;
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
    public int jumpToRegexMatch(String regex, int matchNumber) {

        int i = 1;
        try {
            Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(
                    DisplayText.this.mainTextArea.getText());
            if (matchNumber < 1) {
                matchNumber = 1;
            }
            while ((i <= matchNumber) && m.find()) {
                i++;
            }
            if (matchNumber > 1) {
                DisplayText.this.mainTextArea.requestFocus();
            }
            DisplayText.this.mainTextArea.setCaretPosition(m.start());

        }catch (PatternSyntaxException ex) {
            // JUST IGNORE IT
        }catch (IllegalStateException ex) {
            // JUST IGNORE IT
        }
        return --i;
    }

    public void removeHighlights(boolean isRegexHighlighter) {

        Highlighter hilite = this.mainTextArea.getHighlighter();

        for (Highlight hilite2:hilite.getHighlights()) {
            hilite.removeHighlight(hilite2);
        }

        this.resetStatusline(isRegexHighlighter);
    }

    public void resetRequestNumbers(int startValue) {

        String spaces = "\t\t";
        Matcher m = Pattern.compile(
                "(\n-{0,1}[0-9]+:" + spaces + "[^\t]*" + spaces + ")",
                Pattern.MULTILINE).matcher(this.mainTextArea.getText());
        StringBuffer sb = new StringBuffer();
        String[] payloadListArray = Variables.getInstance()
                .getPayloadListForIntruderCompare();
        while (m.find()) {
            m.appendReplacement(sb, Matcher.quoteReplacement("\n"
                    + startValue
                    + ":"
                    + spaces
                    + (payloadListArray == null ? "Payload":Library
                            .getSubstring(payloadListArray[Library
                                                           .getRealModuloResult((startValue - 1),
                                                                   payloadListArray.length)], 10))
                                                                   + spaces));
            startValue++;
        }

        m.appendTail(sb);
        this.mainTextArea.setText(sb.toString());
        // this.highlight();
    }

    public void resetStatusline(boolean isRegexHighlighter) {

        if (isRegexHighlighter) {
            this.statusLine.setText(this.statusLine.getText()
                    .replaceFirst("RegexTest: [0-9]+ matches",
                            "RegexTest: " + 0 + " matches"));
        }
        else {
            this.statusLine.setText(this.statusLine.getText().replaceFirst(
                    "SearchField: [0-9]+ matches",
                    "SearchField: " + 0 + " matches"));
        }
    }

    public void setMaintextAreaText(String text) {

        this.mainTextArea.setText(text);
    }

    public void setRegexTestWindowContent(String content) {

        DisplayText.regexTestWindowInstance.mainTextArea.setText(content);
    }

    public void showDebugWindow() {

        DisplayText.debugWindowInstance.setVisible(true);
        DisplayText.debugWindowInstance.requestFocus();
    }

    public void showRegexTestWindow() {

        DisplayText.regexTestWindowInstance.setVisible(true);
        DisplayText.regexTestWindowInstance.requestFocus();
    }

    public void windowActivated(WindowEvent event) {

    }

    public void windowClosed(WindowEvent event) {

    }

    public void windowClosing(WindowEvent event) {

        event.getWindow().dispose();
        if (this.isDebugWindow) {
            DisplayText.debugWindowRefreshThread.interrupt();
        }
        else if (this.isIntruderComparer) {
            this.isIntruderComparerInterrupted = true;
        }
    }

    public void windowDeactivated(WindowEvent event) {

    }

    public void windowDeiconified(WindowEvent event) {

    }

    public void windowIconified(WindowEvent event) {

    }

    public void windowOpened(WindowEvent event) {

    }

}

class RegexTestHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    public RegexTestHighlighter(Color color) {

        super(color);
    }
}

class SearchFieldHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    public SearchFieldHighlighter(Color color) {

        super(color);
    }
}
