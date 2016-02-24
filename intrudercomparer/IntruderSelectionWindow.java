
package intrudercomparer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import misc.DisplayText;
import misc.Messages;
import misc.SearchPanel;
import variables.Variables;
import burp.IHttpRequestResponse;
import burp.IScanIssue;



public class IntruderSelectionWindow extends JFrame {

    /**
     * @param args
     */
    private Object[]          messages;
    private final JCheckBox   includeHTTPHeaderInComparisonCheckbox;
    private final JCheckBox   doComparisonOnUnpackedResponseCheckbox;
    private final JCheckBox   includeDifflikeComparisonCheckbox;
    private final JCheckBox   interactiveModeCheckbox;
    private final JCheckBox   useGoogleDiffForWholeComparisonCheckbox;
    private final JCheckBox   doDifflikeComparisonToFirstResponseCheckbox;
    private final SearchPanel labelContainingSearchfield;
    // private final JLabel regexCheckOutputLabel;
    private DisplayText       regexTestWindow;

    public IntruderSelectionWindow(Object[] messageInfo) {

        this.messages = messageInfo;
        this.setLayout(new GridLayout(9, 1));
        this.setTitle(Messages.getString("intruder.comparer.options"));
        this.includeHTTPHeaderInComparisonCheckbox = new JCheckBox(
                Messages.getString("include.http.header.in.all.comparisons"));
        this.doComparisonOnUnpackedResponseCheckbox = new JCheckBox(
                Messages.getString("do.comparison.on.unpacked.responses"));
        this.includeDifflikeComparisonCheckbox = new JCheckBox(
                Messages.getString("do.a.diff.like.comparison"));
        this.includeDifflikeComparisonCheckbox.setSelected(true);
        this.labelContainingSearchfield = new SearchPanel(
                null,
                Variables.getInstance()
                .getIntruderComparerExclusionRegexdropdownHashmap(),
                true,
                Messages.getString("a.regex.containing.a.regex.group.marking.the.textarea.which.should.not.be.included.on.difference.computation"));
        this.labelContainingSearchfield
        .getSearchTextfield()
        .setToolTipText(
                Messages.getString("a.regex.containing.a.regex.group.marking.the.textarea.which.should.not.be.included.on.difference.computation"));
        this.labelContainingSearchfield.getSearchTextfield().setText(
                Variables.getInstance().getIntruderComparerExclusionRegex());
        // this.regexCheckOutputLabel = new
        // JLabel(Messages.getString("regex.is"));
        JButton startIntruderComparerButton = new JButton(
                Messages.getString("start.the.intruder.comparer"));
        this.interactiveModeCheckbox = new JCheckBox(
                Messages.getString("interactive.mode"));
        this.interactiveModeCheckbox.setSelected(true);
        this.doDifflikeComparisonToFirstResponseCheckbox = new JCheckBox(
                Messages.getString("do.diff.like.comparison.each.time.also.against.first.response"));
        this.doDifflikeComparisonToFirstResponseCheckbox
        .setToolTipText(Messages
                .getString("approximately.doubles.the.runtime"));
        this.useGoogleDiffForWholeComparisonCheckbox = new JCheckBox(
                Messages.getString("do.google.diff.instead.of.java.diff"));
        this.useGoogleDiffForWholeComparisonCheckbox
        .setToolTipText(Messages
                .getString("might.be.faster.under.some.circumstances.but.sometimes.also.10.times.slower.google.diff.returns.count.of.different.chars.instead.of.lines"));

        this.regexTestWindow = DisplayText.getInstanceOfRegexTestWindow();
        this.labelContainingSearchfield
        .registerNewTextarea(this.regexTestWindow.getMainTextarea());


        ActionListener startIntruderComparerActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String regex = IntruderSelectionWindow.this.labelContainingSearchfield
                            .getSearchTextfield().getText();
                    Matcher m = Pattern.compile(regex).matcher("");

                    if (regex.equals("") || (m.groupCount() > 0)) {
                        Variables.getInstance()
                        .setIntruderComparerExclusionRegex(regex);

                        IntruderSelectionWindow.this.dispose();

                        if ((IntruderSelectionWindow.this.messages != null)
                                && (IntruderSelectionWindow.this.messages.length > 0)
                                && (IntruderSelectionWindow.this.messages[0] instanceof IScanIssue)) {
                            for (IScanIssue scanIssue:(IScanIssue[]) IntruderSelectionWindow.this.messages) {

                                new IntruderComparer(
                                        scanIssue.getHttpMessages(),
                                        IntruderSelectionWindow.this.includeHTTPHeaderInComparisonCheckbox
                                        .isSelected(),
                                        IntruderSelectionWindow.this.doComparisonOnUnpackedResponseCheckbox
                                                .isSelected(),
                                        IntruderSelectionWindow.this.includeDifflikeComparisonCheckbox
                                        .isSelected(),
                                        regex,
                                        IntruderSelectionWindow.this.interactiveModeCheckbox
                                        .isSelected(),
                                        IntruderSelectionWindow.this.useGoogleDiffForWholeComparisonCheckbox
                                        .isSelected(),
                                        IntruderSelectionWindow.this.doDifflikeComparisonToFirstResponseCheckbox
                                        .isSelected());

                            }
                        }
                        else {
                            new IntruderComparer(
                                    (IHttpRequestResponse[]) IntruderSelectionWindow.this.messages,
                                    IntruderSelectionWindow.this.includeHTTPHeaderInComparisonCheckbox
                                    .isSelected(),
                                    IntruderSelectionWindow.this.doComparisonOnUnpackedResponseCheckbox
                                            .isSelected(),
                                    IntruderSelectionWindow.this.includeDifflikeComparisonCheckbox
                                    .isSelected(),
                                    regex,
                                    IntruderSelectionWindow.this.interactiveModeCheckbox
                                    .isSelected(),
                                    IntruderSelectionWindow.this.useGoogleDiffForWholeComparisonCheckbox
                                    .isSelected(),
                                    IntruderSelectionWindow.this.doDifflikeComparisonToFirstResponseCheckbox
                                    .isSelected());
                        }
                    }
                    else {
                        new DisplayText(
                                Messages.getString("errorOccured"),
                                Messages.getString("at.least.one.regex.group.is.needed"));

                    }

                }catch (PatternSyntaxException ex) {
                    new DisplayText(
                            Messages.getString("errorOccured"),
                            Messages.getString("the.regex.for.comparer.exclusion.is.invalid"));
                    ex.printStackTrace();

                }

            }
        };

        startIntruderComparerButton
        .addActionListener(startIntruderComparerActionlistener);

        JButton regexTestButton = new JButton("Regex Test");

        ActionListener regexTestActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                IntruderSelectionWindow objInstance = ((IntruderSelectionWindow) ((JButton) e
                        .getSource()).getTopLevelAncestor());

                if (objInstance.regexTestWindow == null) {
                    objInstance.regexTestWindow = DisplayText
                            .getInstanceOfRegexTestWindow();
                }
                // }
                // if (TextFieldsWindow.this.regexTestDisplay == null) {
                // TextFieldsWindow.this.regexTestDisplay = new DisplayText(
                // "Regex Test");
                // }
                objInstance.regexTestWindow.showRegexTestWindow();

                objInstance.labelContainingSearchfield
                .registerNewTextarea(objInstance.regexTestWindow
                        .getMainTextarea());
            }
        };

        regexTestButton.addActionListener(regexTestActionlistener);
        this.add(this.includeHTTPHeaderInComparisonCheckbox);
        this.add(this.doComparisonOnUnpackedResponseCheckbox);
        this.add(this.includeDifflikeComparisonCheckbox);
        this.add(this.doDifflikeComparisonToFirstResponseCheckbox);
        this.add(this.useGoogleDiffForWholeComparisonCheckbox);
        this.add(this.interactiveModeCheckbox);
        this.add(this.labelContainingSearchfield);
        this.add(regexTestButton);
        // this.add(this.regexCheckOutputLabel);
        this.add(startIntruderComparerButton);
        this.finish();
    }

    private void finish() {

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void menuItemClicked(String menuItemCaption,
            IHttpRequestResponse[] messageInfo) {

        this.messages = messageInfo;
    }

}
