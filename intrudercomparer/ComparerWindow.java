/**
 * Erstellt am: Nov 28, 2013 8:11:37 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package intrudercomparer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import misc.Library;
import misc.Messages;
import misc.SearchPanel;
import variables.ComparerWindowSettings;
import variables.Variables;
import difflib.google.diff_match_patch.Diff;
import difflib.google.diff_match_patch.Operation;


class ComparerHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    public ComparerHighlighter(Color color) {

        super(color);
    }
}


/**
 * This class is the actual Comparer, which shows differences within 2 textareas
 * It is also responsible for highlighting the diffs, and extracting all
 * relevant information of the diffbuilders
 */
public class ComparerWindow extends JFrame {


    // for removed Lines
    private final Highlighter.HighlightPainter orangeHighlighter;

    // for changed lines - now obsolete (new difflib)
    private final Highlighter.HighlightPainter redHighlighter;

    // for added lines
    private final Highlighter.HighlightPainter greenHighlighter;

    private final JTextArea                    leftTextArea                   = new JTextArea(
                                                                                      50,
                                                                                      50);

    private final JTextArea                    rightTextArea                  = new JTextArea(
                                                                                      50,
                                                                                      50);

    // JScrollPane scrollingArea = new JScrollPane(
    // this.rightTextArea);

    private final JScrollPane                  leftScrollingArea              = new JScrollPane(
                                                                                      this.leftTextArea,
                                                                                      ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                                                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    private final JScrollPane                  rightScrollingArea             = new JScrollPane(
                                                                                      this.rightTextArea,
                                                                                      ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                                                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private final JPanel                       bottomContent                  = new JPanel();
    private final JCheckBox                    syncViews                      = new JCheckBox(
                                                                                      Messages.getString("sync.views"));
    private final JButton                      nextMatchButton                = new JButton(
                                                                                      new ImageIcon(
                                                                                              ComparerWindow.class
                                                                                                      .getResource("/pictures/forward.png")));
    private final JButton                      previousMatchButton            = new JButton(
                                                                                      new ImageIcon(
                                                                                              ComparerWindow.class
                                                                                                      .getResource("/pictures/back.png")));

    private int                                currentHighlightIndex          = 0;
    private int                                currentLineHighlightIndex      = 0;
    private ArrayList<int[]>                   leftDiffIndexes                = null;
    private ArrayList<int[]>                   rightDiffIndexes               = null;
    private ArrayList<int[]>                   jumpToIndexes                  = null;
    private ArrayList<int[]>                   jumpToLineIndexes              = null;
    private List<Diff>                         diffrows                       = null;
    private int                                currentRequestNumber           = 0;
    private ActionListener                     cancelActionlistener           = null;
    private final JPanel                       leftTextareaJpanel             = new JPanel();
    private final JPanel                       rightTextareaJpanel            = new JPanel();
    private final JPanel                       textareasJpanel                = new JPanel();
    private final JPanel                       syncViewAndDiffCountPanel      = new JPanel();
    private final JLabel                       countOfDifferences             = new JLabel();
    private JButton                            cancelButton                   = null;
    private JButton                            nextButton                     = null;
    private JCheckBox                          useLineIndexes                 = null;
    private JCheckBox                          useRightTextareaForSearchfield = null;
    private SearchPanel                        searchPanel                    = null;
    private final ComparerWindowSettings       settings                       = Variables
                                                                                      .getInstance()
                                                                                      .getComparerWindowSettings();
    private int                                diffCharsCount                 = 0;
    private int                                equalCharsCount                = 0;


    public ComparerWindow(int currentRequestNumber, List<Diff> diffrows,
            ActionListener cancelActionlistener, String title) {

        this.diffrows = diffrows;
        this.currentRequestNumber = currentRequestNumber;
        this.cancelActionlistener = cancelActionlistener;
        this.redHighlighter = new ComparerHighlighter(Color.red);
        this.orangeHighlighter = new ComparerHighlighter(Color.LIGHT_GRAY);
        this.greenHighlighter = new ComparerHighlighter(Color.MAGENTA);


        // this.initialize(true);
        this.setTitle(title);
        this.setAlwaysOnTop(false);
        this.setLayout(new BorderLayout());

        this.initializeTextareas();
        this.generateDiffedTextfieldContent();
        this.setRequestInformation();
        this.add(this.textareasJpanel, BorderLayout.CENTER);

        this.initializeBottomPanel();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        this.searchPanel = new SearchPanel(null, Variables.getInstance()
                .getgComparerRegexdropdownHashmap(), true,
                Messages.getString("regex.based.search_regex.test"));
        bottomPanel.add(this.searchPanel);
        bottomPanel.add(this.bottomContent);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.restoreSettings();
        this.jumpToCurrentHighlight();
    }


    private void adjustLeftScrollpanePosition() {

        if (this.syncViews.isSelected()) {
            this.leftScrollingArea.getVerticalScrollBar().setValue(
                    this.rightScrollingArea.getVerticalScrollBar().getValue());
        }

    }


    private void adjustRightScrollpanePosition() {

        if (this.syncViews.isSelected()) {
            this.rightScrollingArea.getVerticalScrollBar().setValue(
                    this.leftScrollingArea.getVerticalScrollBar().getValue());
        }

    }

    private void generateDiffedTextfieldContent() {

        String leftText = "";
        String rightText = "";
        this.leftDiffIndexes = new ArrayList<int[]>();
        this.rightDiffIndexes = new ArrayList<int[]>();

        // is used for jumping to every single diff, even on multiple diffs
        // within one row
        this.jumpToIndexes = new ArrayList<int[]>();

        // is used to jump line-wise
        this.jumpToLineIndexes = new ArrayList<int[]>();

        int[] currentDiffIndexes = null;
        int[] jumpToIndex = null;
        int[] jumpToLineIndex = null;
        boolean left = false;
        for (Diff diff:this.diffrows) {
            if (diff.operation != difflib.google.diff_match_patch.Operation.EQUAL) {

                if (diff.operation == Operation.DELETE) {
                    left = true;
                }
                else if (diff.operation == Operation.INSERT) {
                    left = false;
                }

                currentDiffIndexes = new int[3];
                jumpToIndex = new int[2];
                jumpToLineIndex = new int[3];


                jumpToLineIndex[1] = left ? 0:1;
                int tempint = 0;

                if (diff.text.startsWith("\n") || diff.text.startsWith("\r")) {
                    tempint = left ? leftText.length():rightText.length();

                    jumpToLineIndex[0] = tempint;
                    jumpToLineIndex[2] = this.getNewlinecountUntilOffset(
                            left ? leftText:rightText, tempint);
                    this.jumpToLineIndexes.add(jumpToLineIndex);

                }
                else {
                    tempint = left ? leftText.lastIndexOf("\n"):rightText
                            .lastIndexOf("\n");

                    jumpToLineIndex[0] = tempint;
                    jumpToLineIndex[2] = this.getNewlinecountUntilOffset(
                            left ? leftText:rightText, tempint);
                    boolean doesExist = false;

                    for (int[] i:this.jumpToLineIndexes) {
                        if (jumpToLineIndex[2] == i[2]) {
                            // && (jumpToLineIndex[1] == i[1])) {
                            doesExist = true;
                        }
                    }

                    if (!doesExist) {
                        this.jumpToLineIndexes.add(jumpToLineIndex);
                    }
                }

                if (left) {
                    currentDiffIndexes[0] = leftText.length();
                    leftText += diff.text;
                    currentDiffIndexes[1] = leftText.length();
                    currentDiffIndexes[2] = 2;
                    this.leftDiffIndexes.add(currentDiffIndexes);
                }
                else {
                    currentDiffIndexes[0] = rightText.length();
                    rightText += diff.text;
                    currentDiffIndexes[1] = rightText.length();
                    currentDiffIndexes[2] = 3;
                    this.rightDiffIndexes.add(currentDiffIndexes);
                }

                jumpToIndex[0] = currentDiffIndexes[0];
                jumpToIndex[1] = left ? 0:1;
                this.jumpToIndexes.add(jumpToIndex);


                this.diffCharsCount += diff.text.length();
            }
            else {
                leftText += diff.text;
                rightText += diff.text;
                this.equalCharsCount += diff.text.length();
            }
        }


        this.leftTextArea.setText(leftText);
        this.rightTextArea.setText(rightText);
        Highlighter leftHilite = this.leftTextArea.getHighlighter();
        Highlighter rightHilite = this.rightTextArea.getHighlighter();
        Highlighter.HighlightPainter currentHilite = null;
        try {
            for (int[] js:this.leftDiffIndexes) {
                if (js[2] != 1) {
                    currentHilite = (js[2] == 2) ? this.orangeHighlighter
                            :this.greenHighlighter;
                }
                else {
                    currentHilite = this.redHighlighter;
                }

                leftHilite.addHighlight(js[0], js[1], currentHilite);

            }
        }catch (BadLocationException e) {
            e.printStackTrace();
        }

        try {
            for (int[] js:this.rightDiffIndexes) {
                if (js[2] != 1) {
                    currentHilite = (js[2] == 2) ? this.orangeHighlighter
                            :this.greenHighlighter;
                }

                else {
                    currentHilite = this.redHighlighter;
                }
                rightHilite.addHighlight(js[0], js[1], currentHilite);
            }
        }catch (BadLocationException e) {
            e.printStackTrace();
        }

    }


    private int getNewlinecountUntilOffset(String text, int offset) {

        int linenumber = 1;
        try {
            String temp = text.substring(0, offset);
            int index = 0;
            while ((index != -1) && (index < temp.length())) {
                index = temp.indexOf("\n", index);
                if (index >= 0) {
                    linenumber++;
                    index++;
                }
            }
        }catch (Exception e) {
            // do nothing
        }
        return linenumber;
    }


    private void initializeBottomPanel() {

        ActionListener nextMatch = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ComparerWindow.this.jumpToNextOrPreviousHighlight(true);
            }
        };

        ActionListener previousMatch = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ComparerWindow.this.jumpToNextOrPreviousHighlight(false);
            }
        };
        this.nextMatchButton.addActionListener(nextMatch);
        this.nextMatchButton.setPreferredSize(new Dimension(25, 25));
        this.previousMatchButton.setPreferredSize(new Dimension(25, 25));
        this.previousMatchButton.addActionListener(previousMatch);
        this.bottomContent.setLayout(new BorderLayout());
        this.syncViews.setSelected(true);
        this.syncViewAndDiffCountPanel.setLayout(new BorderLayout());
        this.syncViewAndDiffCountPanel.add(this.countOfDifferences,
                BorderLayout.CENTER);
        this.bottomContent.add(this.syncViewAndDiffCountPanel,
                BorderLayout.WEST);
        JPanel nextHighlightButtonsPanel = new JPanel();
        nextHighlightButtonsPanel.add(this.previousMatchButton);
        nextHighlightButtonsPanel.add(this.nextMatchButton);
        this.bottomContent.add(nextHighlightButtonsPanel, BorderLayout.EAST);

        JMenu menu = new JMenu(Messages.getString("options"));
        JMenuBar menubar = new JMenuBar();
        menubar.add(menu);
        menu.add(this.syncViews);
        this.syncViewAndDiffCountPanel.add(menubar, BorderLayout.WEST);

        this.useRightTextareaForSearchfield = new JCheckBox(
                Messages.getString("use.right.textarea.for.search"));
        this.useRightTextareaForSearchfield
                .addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                        ComparerWindow.this.setTextAreaForSearch();

                    }
                });
        menu.add(this.useRightTextareaForSearchfield);

        this.useLineIndexes = new JCheckBox(
                Messages.getString("use.line.indexes"));
        this.useLineIndexes.setSelected(true);
        this.useLineIndexes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                ComparerWindow.this.jumpToCurrentHighlight();
            }
        });
        menu.add(this.useLineIndexes);

        if (this.cancelActionlistener != null) {
            JPanel temp = new JPanel();
            this.cancelButton = new JButton(
                    Messages.getString("abort.the.whole.comparison.process"));
            this.nextButton = new JButton(Messages.getString("next.comparison"));
            this.cancelButton.addActionListener(this.cancelActionlistener);
            this.nextButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {

                    ComparerWindow.this.saveSettings();
                    ComparerWindow.this.dispose();
                }
            });

            temp.setLayout(new GridLayout(1, 2));
            temp.add(this.cancelButton);
            temp.add(this.nextButton);
            this.bottomContent.add(temp, BorderLayout.CENTER);
        }

    }

    private void initializeTextareas() {

        this.leftTextArea.setSelectionColor(new Color(255, 150, 0));
        this.rightTextArea.setSelectionColor(new Color(255, 150, 0));
        this.leftTextArea.setLineWrap(true);
        this.rightTextArea.setLineWrap(true);
        this.leftTextArea.setEditable(false);
        this.rightTextArea.setEditable(false);
        this.leftScrollingArea.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {

                ComparerWindow.this.adjustRightScrollpanePosition();

            }
        });
        this.rightScrollingArea.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {

                ComparerWindow.this.adjustLeftScrollpanePosition();

            }
        });

        this.leftScrollingArea.getVerticalScrollBar().addMouseMotionListener(
                new MouseMotionListener() {

                    @Override
                    public void mouseDragged(MouseEvent arg0) {

                        ComparerWindow.this.adjustRightScrollpanePosition();

                    }

                    @Override
                    public void mouseMoved(MouseEvent arg0) {

                    }
                });

        this.rightScrollingArea.getVerticalScrollBar().addMouseMotionListener(
                new MouseMotionListener() {

                    @Override
                    public void mouseDragged(MouseEvent arg0) {

                        ComparerWindow.this.adjustLeftScrollpanePosition();
                    }

                    @Override
                    public void mouseMoved(MouseEvent arg0) {

                    }
                });


        this.leftTextareaJpanel.setLayout(new BorderLayout());
        this.rightTextareaJpanel.setLayout(new BorderLayout());
        this.leftTextareaJpanel
                .add(this.leftScrollingArea, BorderLayout.CENTER);
        this.rightTextareaJpanel.add(this.rightScrollingArea,
                BorderLayout.CENTER);
        this.textareasJpanel.setLayout(new GridLayout(1, 2));
        this.textareasJpanel.add(this.leftTextareaJpanel);
        this.textareasJpanel.add(this.rightTextareaJpanel);
    }



    /**
     * The main jumpTo method, which is also used by "jumpToNextHighlight
     */
    private void jumpToCurrentHighlight() {

        // this.useLineIndexes = false;
        ArrayList<int[]> jumpIndexesArraylistPointer = this.useLineIndexes
                .isSelected() ? this.jumpToLineIndexes:this.jumpToIndexes;

        if (jumpIndexesArraylistPointer.size() > 0) {

            int leftLength = this.leftTextArea.getText().length();
            int rightLength = this.rightTextArea.getText().length();
            int currentHighlightIndex = this.useLineIndexes.isSelected() ? this.currentLineHighlightIndex
                    :this.currentHighlightIndex;
            int highlightOffset = jumpIndexesArraylistPointer
                    .get(currentHighlightIndex)[0];

            if (highlightOffset < 0) {
                highlightOffset = 0;
            }

            this.leftTextArea
                    .setCaretPosition(leftLength >= highlightOffset ? highlightOffset
                            :leftLength - 1);
            this.rightTextArea
                    .setCaretPosition(rightLength >= highlightOffset ? highlightOffset
                            :rightLength - 1);


            boolean highlightOnLeftSide = jumpIndexesArraylistPointer
                    .get(currentHighlightIndex)[1] == 0;
            JTextArea currentTextarea = highlightOnLeftSide ? this.leftTextArea
                    :this.rightTextArea;
            // JScrollPane currentScrollpane = highlightOnLeftSide ?
            // this.leftScrollingArea
            // :this.rightScrollingArea;


            // center highlighted line in the middle
            try {
                int offsetToScrollTo = Library
                        .getValueToScrollHighlightedLineToTheMiddle(currentTextarea);

                this.leftScrollingArea.getVerticalScrollBar().setValue(
                        offsetToScrollTo);

                this.rightScrollingArea.getVerticalScrollBar().setValue(
                        offsetToScrollTo);
                // fix for a unknown internal bug (jumping only once lets the
                // right/left scrollbar sometimes jump to a wrong position)

                this.leftScrollingArea.getVerticalScrollBar().setValue(
                        offsetToScrollTo);

                this.rightScrollingArea.getVerticalScrollBar().setValue(
                        offsetToScrollTo);


            }catch (BadLocationException e) {
                System.out
                        .println("Internal error while scrolling to highlight. Blame the author and send a bug report.");
            }


            if (jumpIndexesArraylistPointer.size() > 0) {
                this.countOfDifferences
                        .setText((this.useLineIndexes.isSelected() ? "  Current line with diffs: "
                                :"  Current diff: ")
                                + (currentHighlightIndex + 1)
                                + "/"
                                + jumpIndexesArraylistPointer.size()
                                + "| DiffCharCount: "
                                + this.diffCharsCount
                                + " "
                                + (this.useLineIndexes.isSelected() ? "| Number of diffs: "
                                        + this.jumpToIndexes.size() + "  "
                                        :" "));
            }
            else {
                this.countOfDifferences.setText("There are no differences");
            }
        }
    }

    private void jumpToNextOrPreviousHighlight(boolean toNextHighlight) {

        ArrayList<int[]> jumpIndexesArraylistPointer = this.useLineIndexes
                .isSelected() ? this.jumpToLineIndexes:this.jumpToIndexes;
        int currentHighlightIndex = this.useLineIndexes.isSelected() ? this.currentLineHighlightIndex
                :this.currentHighlightIndex;

        if (jumpIndexesArraylistPointer.size() > 0) {
            if ((!toNextHighlight && (currentHighlightIndex > 0))
                    || (toNextHighlight && (currentHighlightIndex < (jumpIndexesArraylistPointer
                            .size() - 1)))) {

                currentHighlightIndex = toNextHighlight ? currentHighlightIndex + 1
                        :currentHighlightIndex - 1;
                if (this.useLineIndexes.isSelected()) {
                    this.currentLineHighlightIndex = currentHighlightIndex;
                }
                else {
                    this.currentHighlightIndex = currentHighlightIndex;
                }
            }
        }
        this.jumpToCurrentHighlight();
    }

    private void restoreSettings() {

        if (this.settings.getSize() != null) {
            this.setSize(this.settings.getSize());
        }

        this.useLineIndexes.setSelected(this.settings.isUseLineIndexes());
        this.useRightTextareaForSearchfield.setSelected(this.settings
                .isUseRightTextareaForSearch());
        this.setTextAreaForSearch();
        this.syncViews.setSelected(this.settings.isSyncViews());
    }


    private void saveSettings() {

        this.settings.setSize(this.getSize());
        this.settings.setUseLineIndexes(this.useLineIndexes.isSelected());
        this.settings.setSyncViews(this.syncViews.isSelected());
        this.settings
                .setUseRightTextareaForSearch(this.useRightTextareaForSearchfield
                        .isSelected());
    }

    private void setRequestInformation() {

        if (this.currentRequestNumber >= 0) {
            String leftPayload = "";
            String rightPayload = "";
            int firstRequestNumber = this.currentRequestNumber - 1;
            String[] payloadListArray = Variables.getInstance()
                    .getPayloadListForIntruderCompare();

            if (payloadListArray != null) {
                // if (this.currentRequestNumber == 1) {
                // // leftPayload = "BASELINE_REQUEST";
                // firstRequestNumber = -1;
                // }
                // else {
                leftPayload = Library.getSubstring(payloadListArray[Library
                        .getRealModuloResult((firstRequestNumber),
                                payloadListArray.length)], 14);
                // }
                rightPayload = Library.getSubstring(payloadListArray[Library
                        .getRealModuloResult((firstRequestNumber + 1),
                                payloadListArray.length)], 14);

            }
            else {
                leftPayload = "Payload";
                rightPayload = "Payload";
            }
            this.leftTextareaJpanel.add(new JLabel("Request: "
                    + (this.currentRequestNumber) + "     -     Payload: "
                    + leftPayload), BorderLayout.NORTH);
            this.rightTextareaJpanel.add(new JLabel("Request: "
                    + (this.currentRequestNumber + 1) + "     -     Payload: "
                    + rightPayload), BorderLayout.NORTH);

        }
    }

    private void setTextAreaForSearch() {

        boolean useRightTextarea = this.useRightTextareaForSearchfield
                .isSelected();

        this.searchPanel.removeHighlights();

        this.searchPanel
                .registerNewTextarea(useRightTextarea ? this.rightTextArea
                        :this.leftTextArea);
        this.searchPanel.highlight();
    }
}
