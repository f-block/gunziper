
package misc;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import variables.Variables;
import burp.BurpExtender;
import burp.IExtensionHelpers;



public class SaveAllReqsRespsSelectionWindow extends JFrame {

    /**
     * @param args
     */
    private Object[]                messages;
    private final JCheckBox         includeRequestHeaderCheckbox;
    private final JCheckBox         includeRequestBodyCheckbox;
    private final JCheckBox         includeResponseHeaderCheckbox;
    private final JCheckBox         includeResponseBodyCheckbox;
    private final JCheckBox         includeUrlCheckbox;
    private final JCheckBox         includeResponsibleParametersCheckbox;
    private final JCheckBox         includePocSeparators;
    private final JCheckBox         includeMarkedRequestParts;
    private final JCheckBox         includeMarkedResponseParts;
    private final JCheckBox         incrementingFilenamesSaveCheckbox;
    private final JCheckBox         appendToPocFileCheckbox;
    private final JCheckBox         uniqueMode;
    private final JCheckBox         includeParamValuesInComparison;
    private final JCheckBox         includeCookiesInComparison;
    private final JCheckBox         includeCookieValuesInComparison;
    private final JCheckBox         excludeHostFromComparison;
    private final JCheckBox         excludeProtocolFromComparison;
    private final JCheckBox         ignoreValuesForExcludedParams;
    private final JCheckBox         ignoreHttpMethod;
    private final JCheckBox         includeParamsInsteadOfExclude;
    private final JTextField        excludeTheseParamsFromComparison;
    private final SearchPanel       labelContainingTextfieldWithExclusionregex;
    private final JLabel            regexCheckOutput;
    private final JPanel            mainContentPanel;
    // private final JScrollPane mainContentScrollpane;
    public static final int         UNIQUE_MODE_INCLUDE_PARAM_VALUE                        = 1;
    public static final int         UNIQUE_MODE_INCLUDE_COOKIE                             = 3;
    public static final int         UNIQUE_MODE_INCLUDE_COOKIE_VALUE                       = 4;
    public static final int         UNIQUE_MODE_IGNORE_HOST                                = 8;
    public static final int         UNIQUE_MODE_IGNORE_PROTOCOL                            = 9;
    public static final int         UNIQUE_MODE_EXCLUDE_HTTP_METHOD                        = 10;
    public static final int         UNIQUE_MODE_IGNORE_FOR_EXCLUDED_PARAMS_ONLY_THE_VALUES = 20;
    public static final int         UNIQUE_MODE_INCLUDE_PARAMETERS_INSTEAD_OF_EXCLUSION    = 30;
    private DisplayText             regexTestWindow;
    private final IExtensionHelpers helpers;
    private boolean                 itemsAreScanIssues                                     = false;

    public SaveAllReqsRespsSelectionWindow(Object[] messages,
            IExtensionHelpers helpers, boolean itemsAreScanIssues) {

        this.messages = messages;
        this.helpers = helpers;
        // int baseGridlayoutSize = 17;
        this.itemsAreScanIssues = itemsAreScanIssues;
        // this.setLayout(new GridLayout(baseGridlayoutSize, 1));
        this.mainContentPanel = new JPanel();
        // TODO
        // this.mainContentScrollpane = new JScrollPane(this.mainContentPanel,
        // ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        // ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.mainContentPanel.setLayout(new BoxLayout(this.mainContentPanel,
                BoxLayout.Y_AXIS));


        this.setTitle(Messages.getString("choose.what.to.include.on.saving"));
        this.includeRequestHeaderCheckbox = new JCheckBox(
                Messages.getString("include.request.header"));
        this.includeRequestHeaderCheckbox
        .setSelected(Variables
                .getInstance()
                .isIncludeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames());
        this.includeRequestBodyCheckbox = new JCheckBox(
                Messages.getString("include.request.body"));
        this.includeRequestBodyCheckbox
        .setSelected(Variables
                .getInstance()
                .isIncludeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames());
        this.includeResponseHeaderCheckbox = new JCheckBox(
                Messages.getString("include.response.header"));
        this.includeResponseHeaderCheckbox
        .setSelected(Variables
                .getInstance()
                .isIncludeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames());
        this.includeResponseBodyCheckbox = new JCheckBox(
                Messages.getString("include.response.body"));
        this.includeResponseBodyCheckbox
        .setSelected(Variables
                .getInstance()
                .isIncludeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames());
        this.includeUrlCheckbox = new JCheckBox(
                Messages.getString("include.url.in.file"));
        this.includeUrlCheckbox.setSelected(Variables.getInstance()
                .isIncludeUrlOnSaveAllSelectedItemsWithIncrementingNames());
        this.includeMarkedRequestParts = new JCheckBox(
                Messages.getString("include.marked.request.parts.in.file"));
        this.includeMarkedRequestParts.setSelected(Variables.getInstance()
                .isIncludeMarkedRequestPartsOnSavePoc());
        this.includeMarkedResponseParts = new JCheckBox(
                Messages.getString("include.marked.response.parts.in.file"));
        this.includeMarkedResponseParts.setSelected(Variables.getInstance()
                .isIncludeMarkedResponsePartsOnSavePoc());
        this.incrementingFilenamesSaveCheckbox = new JCheckBox(
                Messages.getString("use.incrementing.filenames"));
        this.labelContainingTextfieldWithExclusionregex = new SearchPanel(
                null,
                Variables.getInstance().getSavePocRegexdropdownHashmap(),
                true,
                Messages.getString("a.regex.containing.a.regex.group.marking.the.textarea.which.should.not.be.included.on.saving"));
        // this.regexForExclusionTextfield.setText(Variables.getInstance().getRegexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames());
        this.labelContainingTextfieldWithExclusionregex
        .getSearchTextfield()
        .setToolTipText(
                Messages.getString("a.regex.containing.a.regex.group.marking.the.textarea.which.should.not.be.included.on.saving"));
        this.labelContainingTextfieldWithExclusionregex
        .getSearchTextfield()
        .setText(
                Variables
                .getInstance()
                .getRegexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames());
        this.regexCheckOutput = new JLabel(Messages.getString("regex.is"));
        JButton startSaveAllButton = new JButton(Messages.getString("save"));
        this.includePocSeparators = new JCheckBox(
                Messages.getString("include.pocfile.separators"));
        this.includePocSeparators
        .setToolTipText(Messages
                .getString("include.pocfile.separators.this.switch.can.be.helpful.to.create.a.poclist.for.a.report.not.containing.any.request.or.responses"));
        this.includePocSeparators.setSelected(Variables.getInstance()
                .isIncludePocSeparators());
        this.includeResponsibleParametersCheckbox = new JCheckBox(
                Messages.getString("include.responsible.parameters.if.applicable"));
        this.includeResponsibleParametersCheckbox.setSelected(Variables
                .getInstance().isIncludeResponsibleParametersCheckbox());
        this.appendToPocFileCheckbox = new JCheckBox(
                Messages.getString("save.everything.in.one.file"));
        this.appendToPocFileCheckbox.setSelected(Variables.getInstance()
                .isAppendToPocFile());
        this.uniqueMode = new JCheckBox(
                Messages.getString("activate.unique.mode"));
        this.uniqueMode.setSelected(false);
        this.includeParamValuesInComparison = new JCheckBox(
                Messages.getString("include.parameter.values"));
        this.includeParamValuesInComparison.setSelected(true);
        this.includeParamValuesInComparison.setSelected(Variables.getInstance()
                .isUniqueModeIncludeParamValues());
        this.includeCookiesInComparison = new JCheckBox(
                Messages.getString("include.cookies"));
        this.includeCookiesInComparison.setSelected(Variables.getInstance()
                .isUniqueModeIncludeCookies());
        this.includeCookieValuesInComparison = new JCheckBox(
                Messages.getString("include.cookie.values"));
        this.includeCookieValuesInComparison.setSelected(Variables
                .getInstance().isUniqueModeIncludeCookieValues());
        this.excludeHostFromComparison = new JCheckBox(
                Messages.getString("exclude.host.from.comparison"));
        this.excludeHostFromComparison.setSelected(Variables.getInstance()
                .isUniqueModeIgnoreHost());
        this.excludeProtocolFromComparison = new JCheckBox(
                Messages.getString("exclude.protocol.from.comparison"));
        this.excludeProtocolFromComparison.setSelected(Variables.getInstance()
                .isUniqueModeIgnoreProtocol());
        this.ignoreValuesForExcludedParams = new JCheckBox(
                Messages.getString("ignore.only.values.for.excluded.parameters"));
        this.ignoreValuesForExcludedParams.setSelected(true);
        this.ignoreValuesForExcludedParams.setSelected(Variables.getInstance()
                .isUniqueModeIgnoreForExcludedParametersOnlyTheirValue());
        this.excludeTheseParamsFromComparison = new JTextField(10);
        this.excludeTheseParamsFromComparison
        .setToolTipText(Messages
                .getString("asv.list.of.params.which.should.be.excluded.from.the.unique.comparison"));
        this.excludeTheseParamsFromComparison.setText(Variables.getInstance()
                .getUniqueModeAmpersandSeparatedValuesForParameterExclusion());
        this.ignoreHttpMethod = new JCheckBox(
                Messages.getString("exclude.http.method.from.comparsion"));
        this.ignoreHttpMethod.setSelected(Variables.getInstance()
                .isUniqueModeIgnoreHttpMethod());
        this.includeParamsInsteadOfExclude = new JCheckBox(
                Messages.getString("include.the.parameters.from.the.list.only.instead.of.excluding.them"));
        this.includeParamsInsteadOfExclude
        .setSelected(Variables
                .getInstance()
                .isUniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding());



        ActionListener startSaveAllActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String regex = SaveAllReqsRespsSelectionWindow.this.labelContainingTextfieldWithExclusionregex
                            .getSearchTextfield().getText();
                    Matcher m = Pattern.compile(regex).matcher("");

                    if (regex.equals("") || (m.groupCount() > 0)) {
                        Variables.getInstance()
                        .setIntruderComparerExclusionRegex(regex);
                        SaveAllReqsRespsSelectionWindow temp = SaveAllReqsRespsSelectionWindow.this;
                        temp.dispose();
                        Variables
                        .getInstance()
                        .setIncludeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames(
                                temp.includeRequestHeaderCheckbox
                                .isSelected());
                        Variables
                        .getInstance()
                        .setIncludeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames(
                                temp.includeRequestBodyCheckbox
                                .isSelected());
                        Variables
                        .getInstance()
                        .setIncludeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames(
                                temp.includeResponseHeaderCheckbox
                                .isSelected());
                        Variables
                        .getInstance()
                        .setIncludeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames(
                                temp.includeResponseBodyCheckbox
                                .isSelected());
                        Variables
                        .getInstance()
                        .setIncludeUrlOnSaveAllSelectedItemsWithIncrementingNames(
                                temp.includeUrlCheckbox.isSelected());

                        Variables
                        .getInstance()
                        .setIncludeResponsibleParametersCheckbox(
                                temp.includeResponsibleParametersCheckbox
                                .isSelected());

                        Variables.getInstance().setIncludePocSeparators(
                                temp.includePocSeparators.isSelected());

                        Variables.getInstance()
                        .setIncludeMarkedRequestPartsOnSavePoc(
                                temp.includeMarkedRequestParts
                                .isSelected());
                        Variables.getInstance()
                        .setIncludeMarkedResponsePartsOnSavePoc(
                                temp.includeMarkedResponseParts
                                .isSelected());
                        Variables
                        .getInstance()
                        .setRegexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames(
                                temp.labelContainingTextfieldWithExclusionregex
                                .getSearchTextfield().getText());

                        Variables
                        .getInstance()
                        .setUniqueModeAmpersandSeparatedValuesForParameterExclusion(
                                temp.excludeTheseParamsFromComparison
                                .getText());
                        Variables
                        .getInstance()
                        .setUniqueModeIgnoreForExcludedParametersOnlyTheirValue(
                                temp.ignoreValuesForExcludedParams
                                .isSelected());
                        Variables.getInstance().setUniqueModeIgnoreHost(
                                temp.excludeHostFromComparison.isSelected());
                        Variables.getInstance().setUniqueModeIgnoreHttpMethod(
                                temp.ignoreHttpMethod.isSelected());
                        Variables.getInstance()
                        .setUniqueModeIgnoreProtocol(
                                temp.excludeProtocolFromComparison
                                .isSelected());
                        Variables.getInstance().setUniqueModeIncludeCookies(
                                temp.includeCookiesInComparison.isSelected());
                        Variables.getInstance()
                        .setUniqueModeIncludeCookieValues(
                                temp.includeCookieValuesInComparison
                                .isSelected());
                        Variables
                        .getInstance()
                        .setUniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding(
                                temp.includeParamsInsteadOfExclude
                                .isSelected());
                        Variables.getInstance()
                        .setUniqueModeIncludeParamValues(
                                temp.includeParamValuesInComparison
                                .isSelected());


                        Variables.getInstance().setAppendToPocFile(
                                temp.appendToPocFileCheckbox.isSelected());

                        ArrayList<Integer> uniqFlags = null;
                        if (temp.uniqueMode.isSelected()) {
                            uniqFlags = new ArrayList<Integer>();
                            if (temp.includeParamValuesInComparison
                                    .isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_PARAM_VALUE);
                            }
                            if (temp.includeCookiesInComparison.isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_COOKIE);
                            }
                            if (temp.includeCookieValuesInComparison
                                    .isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_COOKIE_VALUE);
                            }
                            if (temp.excludeHostFromComparison.isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_IGNORE_HOST);
                            }
                            if (temp.excludeProtocolFromComparison.isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_IGNORE_PROTOCOL);
                            }
                            if (temp.ignoreValuesForExcludedParams.isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_IGNORE_FOR_EXCLUDED_PARAMS_ONLY_THE_VALUES);
                            }
                            if (temp.ignoreHttpMethod.isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_EXCLUDE_HTTP_METHOD);
                            }
                            if (temp.includeParamsInsteadOfExclude.isSelected()) {
                                uniqFlags
                                .add(SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_PARAMETERS_INSTEAD_OF_EXCLUSION);
                            }


                        }

                        BurpExtender
                        .saveAllReqsRespsToFile(
                                temp.messages,
                                SaveAllReqsRespsSelectionWindow.this.helpers,
                                temp.includePocSeparators.isSelected(),
                                temp.includeRequestHeaderCheckbox
                                .isSelected(),
                                temp.includeRequestBodyCheckbox
                                .isSelected(),
                                temp.includeResponseHeaderCheckbox
                                .isSelected(),
                                temp.includeResponseBodyCheckbox
                                .isSelected(),
                                temp.includeUrlCheckbox.isSelected(),
                                temp.includeResponsibleParametersCheckbox
                                .isSelected(),
                                temp.includeMarkedRequestParts
                                .isSelected(),
                                temp.includeMarkedResponseParts
                                .isSelected(),
                                temp.labelContainingTextfieldWithExclusionregex
                                .getSearchTextfield().getText(),
                                temp.incrementingFilenamesSaveCheckbox
                                .isSelected(),
                                temp.appendToPocFileCheckbox
                                .isSelected(), uniqFlags,
                                temp.excludeTheseParamsFromComparison
                                .getText());

                        JOptionPane.showMessageDialog(null,
                                Messages.getString("done.saving"),
                                Messages.getString("info"),
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        new DisplayText(
                                Messages.getString("errorOccured"),
                                Messages.getString("at.least.one.regex.group.is.needed"));

                    }

                }catch (PatternSyntaxException ex) {

                    new DisplayText(
                            Messages.getString("errorOccured"),
                            Messages.getString("the.regex.for.exclusion.is.invalid"));
                    ex.printStackTrace();

                }

            }
        };


        startSaveAllButton.addActionListener(startSaveAllActionlistener);

        JButton regexTestButton = new JButton("Regex Test");

        ActionListener regexTestActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                SaveAllReqsRespsSelectionWindow objInstance = ((SaveAllReqsRespsSelectionWindow) ((JButton) e
                        .getSource()).getTopLevelAncestor());
                if (objInstance.regexTestWindow == null) {
                    objInstance.regexTestWindow = DisplayText
                            .getInstanceOfRegexTestWindow();
                    objInstance.regexTestWindow.showRegexTestWindow();
                }
                // }
                // if (TextFieldsWindow.this.regexTestDisplay == null) {
                // TextFieldsWindow.this.regexTestDisplay = new DisplayText(
                // "Regex Test");
                // }
                objInstance.regexTestWindow.showRegexTestWindow();

                objInstance.labelContainingTextfieldWithExclusionregex
                .registerNewTextarea(objInstance.regexTestWindow
                        .getMainTextarea());
            }
        };

        regexTestButton.addActionListener(regexTestActionlistener);


        ActionListener uniqueModeActivatedActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (((JCheckBox) arg0.getSource()).isSelected()) {
                    SaveAllReqsRespsSelectionWindow.this.toggleEnabled(true);
                }
                else {
                    SaveAllReqsRespsSelectionWindow.this.toggleEnabled(false);
                }
            }
        };

        this.uniqueMode.addActionListener(uniqueModeActivatedActionlistener);


        Border blackBorder = BorderFactory.createLineBorder(Color.black);

        // BASIC SETTINGS ITEMS
        int basicSettingGridSize = 6;
        JPanel basicSettingsContainer = new JPanel(new GridLayout(
                basicSettingGridSize, 1));
        basicSettingsContainer.setBorder(BorderFactory.createTitledBorder(
                blackBorder, "Basic options"));
        // basicSettingsContainer.setBackground(Color.LIGHT_GRAY);

        basicSettingsContainer.add(this.includePocSeparators);
        basicSettingsContainer.add(this.includeUrlCheckbox);
        basicSettingsContainer.add(this.includeRequestHeaderCheckbox);
        basicSettingsContainer.add(this.includeRequestBodyCheckbox);
        basicSettingsContainer.add(this.includeResponseHeaderCheckbox);
        basicSettingsContainer.add(this.includeResponseBodyCheckbox);
        this.mainContentPanel.add(basicSettingsContainer);
        this.mainContentPanel.add(new JLabel(" "));



        // SCAN ISSUE ITEMS
        if (this.itemsAreScanIssues) {
            // basicSettingsContainer.setLayout(new GridLayout(
            // ++baseGridlayoutSize, 1));
            JPanel scanissueSettingsPanel = new JPanel(new GridLayout(3, 1));
            scanissueSettingsPanel.setBorder(BorderFactory.createTitledBorder(
                    blackBorder, "Scan issue options"));
            // scanissueSettingsPanel.setBackground(Color.LIGHT_GRAY);

            scanissueSettingsPanel
            .add(this.includeResponsibleParametersCheckbox);
            scanissueSettingsPanel.add(this.includeMarkedRequestParts);
            scanissueSettingsPanel.add(this.includeMarkedResponseParts);
            this.mainContentPanel.add(scanissueSettingsPanel);
            this.mainContentPanel.add(new JLabel(" "));

        }



        // UNIQUE MODE ITEMS
        JPanel uniqueModePanel = new JPanel();
        uniqueModePanel.setLayout(new BoxLayout(uniqueModePanel,
                BoxLayout.Y_AXIS));
        uniqueModePanel.setBorder(BorderFactory.createTitledBorder(blackBorder,
                "Unique mode options"));
        // uniqueModePanel.setBackground(Color.LIGHT_GRAY);

        uniqueModePanel.add(this.uniqueMode);
        uniqueModePanel.add(this.includeParamValuesInComparison);
        uniqueModePanel.add(this.includeCookiesInComparison);
        uniqueModePanel.add(this.includeCookieValuesInComparison);
        uniqueModePanel.add(this.excludeHostFromComparison);
        uniqueModePanel.add(this.excludeProtocolFromComparison);
        uniqueModePanel.add(this.ignoreHttpMethod);
        uniqueModePanel.add(new JLabel(" "));
        // UNIQUE MODE EXCLUSION ITEMS
        JPanel uniqueModeExclusionPanel = new JPanel();
        uniqueModeExclusionPanel.setLayout(new BoxLayout(
                uniqueModeExclusionPanel, BoxLayout.Y_AXIS));
        uniqueModeExclusionPanel.setBorder(BorderFactory
                .createTitledBorder(blackBorder,
                        "Exclude these params from Unique Mode comparison"));
        // uniqueModeExclusionPanel.setBackground(Color.LIGHT_GRAY);

        uniqueModeExclusionPanel.add(this.excludeTheseParamsFromComparison);
        uniqueModeExclusionPanel.add(this.ignoreValuesForExcludedParams);
        uniqueModeExclusionPanel.add(this.includeParamsInsteadOfExclude);
        uniqueModePanel.add(uniqueModeExclusionPanel);
        this.mainContentPanel.add(uniqueModePanel);
        this.mainContentPanel.add(new JLabel(" "));



        // REGEX EXCLUDE ITEMS
        JPanel excludeOptionsPanel = new JPanel(new GridLayout(3, 1));
        excludeOptionsPanel
                .setBorder(BorderFactory.createTitledBorder(
                        blackBorder,
                        Messages.getString("exclude.parts.of.the.selected.request.response.parts")));
        // excludeOptionsPanel.setBackground(Color.LIGHT_GRAY);

        excludeOptionsPanel.add(regexTestButton);
        excludeOptionsPanel
        .add(this.labelContainingTextfieldWithExclusionregex);
        excludeOptionsPanel.add(this.regexCheckOutput);
        this.mainContentPanel.add(excludeOptionsPanel);
        this.mainContentPanel.add(new JLabel(" "));



        // MISC ITEMS
        JPanel miscOptionsPanel = new JPanel(new GridLayout(3, 1));
        miscOptionsPanel.setBorder(BorderFactory.createTitledBorder(
                blackBorder, "Misc options"));
        miscOptionsPanel.add(this.incrementingFilenamesSaveCheckbox);
        // miscOptionsPanel.setBackground(Color.LIGHT_GRAY);

        miscOptionsPanel.add(this.appendToPocFileCheckbox);
        miscOptionsPanel.add(startSaveAllButton);
        this.mainContentPanel.add(miscOptionsPanel);
        this.mainContentPanel.add(new JLabel(" "));
        // this.mainContentScrollpane.add(this.mainContentPanel);
        // this.add(this.mainContentScrollpane);
        this.add(this.mainContentPanel);
        this.finish();
    }

    private void finish() {

        this.toggleEnabled(this.uniqueMode.isSelected());
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void menuItemClicked(String menuItemCaption, Object[] messageInfo) {

        this.messages = messageInfo;
    }

    private void setComponentEnabled(JComponent comp, boolean enabled) {

        if (comp.isEnabled() != enabled) {
            comp.setBackground(enabled ? null:Color.GRAY);
            comp.setEnabled(enabled);
        }
    }

    private void toggleEnabled(boolean enabled) {

        this.setComponentEnabled(this.includeParamValuesInComparison, enabled);
        this.setComponentEnabled(this.includeCookiesInComparison, enabled);
        this.setComponentEnabled(this.includeCookieValuesInComparison, enabled);
        this.setComponentEnabled(this.excludeHostFromComparison, enabled);
        this.setComponentEnabled(this.excludeProtocolFromComparison, enabled);
        this.setComponentEnabled(this.ignoreValuesForExcludedParams, enabled);
        this.setComponentEnabled(this.excludeTheseParamsFromComparison, enabled);
        this.setComponentEnabled(this.ignoreHttpMethod, enabled);
        this.setComponentEnabled(this.includeParamsInsteadOfExclude, enabled);

    }

}
