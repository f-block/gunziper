/**
 * # gunziper for Burpsuite
 * #
 * # Copyright (c) 2012, Frank Block <gunziper@f-block.org>
 * #
 * # All rights reserved.
 * #
 * # Redistribution and use in source and binary forms, with or without
 * modification,
 * # are permitted provided that the following conditions are met:
 * #
 * # * Redistributions of source code must retain the above copyright notice,
 * this
 * # list of conditions and the following disclaimer.
 * # * Redistributions in binary form must reproduce the above copyright notice,
 * # this list of conditions and the following disclaimer in the documentation
 * # and/or other materials provided with the distribution.
 * # * The names of the contributors may not be used to endorse or promote
 * products
 * # derived from this software without specific prior written permission.
 * #
 * # THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * # AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * # IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * # ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * # LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL
 * # DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * # SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * # CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY,
 * # OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE
 * # OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * # gunziper for Burpsuite
 * #
 * # Copyright (c) 2012, Frank Block <gunziper@f-block.org>
 * #
 * # All rights reserved.
 * #
 * # Redistribution and use in source and binary forms, with or without
 * modification,
 * # are permitted provided that the following conditions are met:
 * #
 * # * Redistributions of source code must retain the above copyright notice,
 * this
 * # list of conditions and the following disclaimer.
 * # * Redistributions in binary form must reproduce the above copyright notice,
 * # this list of conditions and the following disclaimer in the documentation
 * # and/or other materials provided with the distribution.
 * # * The names of the contributors may not be used to endorse or promote
 * products
 * # derived from this software without specific prior written permission.
 * #
 * # THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * # AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * # IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * # ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * # LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL
 * # DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * # SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * # CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY,
 * # OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE
 * # OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package misc;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import matchreplace.MatchReplaceSettings;
import variables.Variables;
import variables.VariablesFunctions;
import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.ITab;
import docu.HelpPanel;



public class TextFieldsWindow2 extends JTabbedPane implements ITab {

    public static TextFieldsWindow2 getInstance() {

        if (TextFieldsWindow2.instance == null) {
            TextFieldsWindow2.instance = new TextFieldsWindow2(
                    BurpExtender.getCallback());
        }
        return TextFieldsWindow2.instance;
    }

    /**
     * @param args
     */

    final SearchPanel                regexTextfield;
    final JTextField                 replaceStringTextfield;
    final JTextField                 toolnameTextfield;
    final JTextField                 responseToolnameTextfield;
    final JTextField                 requestToolnameTextfield;
    final SearchPanel                responseRelevantRegexTextfield;
    // final JTextField requestPrefix;
    final SearchPanel                requestRelevantRegexTextfield;
    final ProcessingPanel            csvDoing;
    final SearchPanel                relevantRequestRegexForReplaceResponseExtractionTextfield;
    final SearchPanel                relevantReplaceRequestRegexTextfield;
    final SearchPanel                regexForNewReplaceStringFromResponseExtraction;
    final JCheckBox                  activeReplace;
    final ProcessingPanel            csvResponseDoing;
    final JTextField                 replaceResponseExtractionMatchingNumberTextfield;
    final JTextField                 numberOfDebugLines;
    final JCheckBox                  activeResponseUnpacking;
    final JCheckBox                  activeRequestUnpacking;
    // final JCheckBox activeResponseExtraction;
    // final JCheckBox activeRequestPrefix;
    final JCheckBox                  activeDebugMode;
    DisplayText                      regexTestDisplay;
    DisplayText                      debugModeDisplay;
    final JButton                    showRegexTest;
    final JButton                    showRegexTest2;
    final JButton                    showRegexTest3;
    final JButton                    showRegexTest4;
    final JButton                    showRegexTest5;
    final JButton                    showRegexTest6;
    final JButton                    showRegexTest7;
    final JButton                    setValues1;
    final JButton                    setValues2;
    final JButton                    setValues3;
    final JButton                    setValues4;
    final JButton                    setValues5;
    final JButton                    setValues6;
    final JComboBox<String>          ReplaceActionDropdownlist;
    final SearchPanel                dropRequestRegex;
    final JCheckBox                  isDropRequestActive;
    final SearchPanel                relevantUnpackedRequestRegexTextfield;
    final SearchPanel                regexMarkingRequestpartForUnpackingTextfield;
    final SearchPanel                regexMarkingResponsepartForUnpackingTextfield;
    final JComboBox<String>          languageSelection;
    final JComboBox<String>          matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox;
    final JComboBox<String>          matchReplaceActionDropdownlist;
    final JButton                    saveSettingsButton;
    final JButton                    loadSettingsButton;
    // final JTextField regexDropdownListStringTextfield;
    // final JTextField payloadListPathTextfield;
    final JButton                    loadPayloadListButton;

    final JButton                    showLicenseStuffButton;
    final JCheckBox                  matchIndicatesNonrelevanceForMatchreplaceRequest;
    final JCheckBox                  matchIndicatesNonrelevanceForMatchreplaceResponseextraction;
    final JCheckBox                  matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking;
    final JCheckBox                  matchIndicatesNonrelevanceForRequestunpackingAfterunpacking;

    final JCheckBox                  matchIndicatesNonrelevanceForResponseunpacking;
    final JComboBox<String>          matchReplacesIteratorCombobox;

    MatchReplaceSettings             currentMatchReplaceSettings;
    final JCheckBox                  matchReplaceProcessingActiveCheckbox;
    final ProcessingPanel            matchReplaceProcessingCsvstringTextfield;

    final JButton                    matchReplaceSaveCurrentSettingsButton;

    final JTextField                 sessionHandlingActionDelayTextfield;
    final JTextField                 responseExtractionToolnamesTextfield;
    final JCheckBox                  doProcessingsOnMarkedPartCheckbox;
    final JCheckBox                  doRequestReplacementAfterUnpackingCheckbox;
    final JCheckBox                  doResponseExtractionAfterUnpackingCheckbox;
    final JCheckBox                  activateRuleGloballyCheckbox;
    final JCheckBox                  isextractFromRequestInsteadFromResponseCheckbox;


    private static TextFieldsWindow2 instance = null;

    private TextFieldsWindow2(IBurpExtenderCallbacks callbacks) {

        // super("Variables Definition - gunziper " +
        // Variables.getInstance().getVersionNumber()); //JFRAME TO JPANEL
        int textFieldSize = 20;
        JPanel miscPanel = new JPanel();
        miscPanel.setLayout(new GridLayout(4, 4, 4, 4));
        this.regexTextfield = new SearchPanel(null, null, false,
                Messages.getString("regex"));
        this.replaceStringTextfield = new JTextField(textFieldSize);
        this.toolnameTextfield = new JTextField(textFieldSize);
        this.responseToolnameTextfield = new JTextField(textFieldSize);
        this.requestToolnameTextfield = new JTextField(textFieldSize);
        this.responseRelevantRegexTextfield = new SearchPanel(null, null,
                false, Messages.getString("relevantResponseRegex"));
        // this.requestPrefix = new JTextField(20);
        this.requestRelevantRegexTextfield = new SearchPanel(null, null, false,
                Messages.getString("relevantResquestRegex"));
        this.csvDoing = new ProcessingPanel(FeaturesEnum.REQUEST_UNPACKING);

        this.relevantRequestRegexForReplaceResponseExtractionTextfield = new SearchPanel(
                null,
                null,
                false,
                Messages.getString("relevantRequestRegexForReplaceResponseExtraction"));

        this.relevantReplaceRequestRegexTextfield = new SearchPanel(null, null,
                false, Messages.getString("relevantReplaceRequestRegex"));

        this.regexForNewReplaceStringFromResponseExtraction = new SearchPanel(
                null,
                null,
                false,
                Messages.getString("regexForNewReplaceStringFromResponseExtraction"));
        this.activeReplace = new JCheckBox("Activate Match/Replace rule?");
        this.csvResponseDoing = new ProcessingPanel(
                FeaturesEnum.RESPONSE_UNPACKING);

        this.activeResponseUnpacking = new JCheckBox(
                "Activate response unpacking?");
        this.activeRequestUnpacking = new JCheckBox(
                "Activate request unpacking?");


        this.replaceResponseExtractionMatchingNumberTextfield = new JTextField(
                textFieldSize);
        this.ReplaceActionDropdownlist = new JComboBox<String>(new String[] {
                "Replace Only First Match", "Replace All Matches"});
        this.activeDebugMode = new JCheckBox("Activate Debug Mode");
        this.numberOfDebugLines = new JTextField(textFieldSize);
        this.dropRequestRegex = new SearchPanel(
                null,
                null,
                false,
                Messages.getString("a.regex.matching.the.request.that.should.be.dropped"));
        this.isDropRequestActive = new JCheckBox("Drop Proxy Requests");
        this.relevantUnpackedRequestRegexTextfield = new SearchPanel(
                null,
                null,
                false,
                Messages.getString("a.regex.matching.the.unpacked.request.that.should.be.packed.before.sending"));
        this.regexMarkingRequestpartForUnpackingTextfield = new SearchPanel(
                null,
                null,
                false,
                Messages.getString("a.regex.marking.the.relevant.part.within.the.request.which.should.be.unpacked"));
        this.regexMarkingResponsepartForUnpackingTextfield = new SearchPanel(
                null,
                null,
                false,
                Messages.getString("a.regex.marking.the.relevant.part.within.the.response.which.should.be.unpacked"));
        this.languageSelection = new JComboBox<String>(new String[] {"en"});
        this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox = new JComboBox<String>(
                new String[] {"Regex matches Request", "Regex matches Response"});

        this.matchReplaceActionDropdownlist = new JComboBox<String>(
                new String[] {"Static String", "Number iteration",
                "Regex response Extraction"});
        this.saveSettingsButton = new JButton(
                Messages.getString("save.settings"));
        this.loadSettingsButton = new JButton(
                Messages.getString("load.settings"));
        // this.regexDropdownListStringTextfield = new
        // JTextField(textFieldSize);
        // this.payloadListPathTextfield = new JTextField(textFieldSize);
        this.loadPayloadListButton = new JButton(
                Messages.getString("load.payloadlist"));
        this.showLicenseStuffButton = new JButton(
                Messages.getString("show.license"));
        this.matchIndicatesNonrelevanceForMatchreplaceRequest = new JCheckBox(
                Messages.getString("invert.match.indicates.non.relevance"));
        this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction = new JCheckBox(
                Messages.getString("invert.match.indicates.non.relevance"));;
                this.matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking = new JCheckBox(
                        Messages.getString("invert.match.indicates.non.relevance"));
                this.matchIndicatesNonrelevanceForRequestunpackingAfterunpacking = new JCheckBox(
                        Messages.getString("invert.match.indicates.non.relevance"));
                this.matchIndicatesNonrelevanceForResponseunpacking = new JCheckBox(
                        Messages.getString("invert.match.indicates.non.relevance"));
                this.matchReplacesIteratorCombobox = new JComboBox<String>(
                        new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});
                this.matchReplaceProcessingActiveCheckbox = new JCheckBox(
                Messages.getString("activate.processing.of.the.replace.string"));
                this.matchReplaceProcessingCsvstringTextfield = new ProcessingPanel(
                        FeaturesEnum.MATCH_REPLACE);
                this.matchReplaceSaveCurrentSettingsButton = new JButton(
                Messages.getString("save.current.settings"));

                this.sessionHandlingActionDelayTextfield = new JTextField(textFieldSize);

                this.responseExtractionToolnamesTextfield = new JTextField(
                        textFieldSize);
                this.doProcessingsOnMarkedPartCheckbox = new JCheckBox(
                        Messages.getString("do.only.the.processings.on.the.marked.part.and.dont.insert.the.replace.string"));
                this.doRequestReplacementAfterUnpackingCheckbox = new JCheckBox(
                        Messages.getString("do.the.replacement.after.unpacking.the.request"));
                this.doResponseExtractionAfterUnpackingCheckbox = new JCheckBox(
                        Messages.getString("do.the.extraction.after.unpacking.the.response"));
                this.activateRuleGloballyCheckbox = new JCheckBox(
                Messages.getString("activate.this.rule.globally"));
                this.isextractFromRequestInsteadFromResponseCheckbox = new JCheckBox(
                        Messages.getString("extract.new.string.from.request.instead.from.response"));

                this.regexTestDisplay = DisplayText.getInstanceOfRegexTestWindow();





                this.setCurrentMatchReplaceSettings();

                this.regexTextfield.getSearchTextfield().setText(
                        Variables.getInstance().getRegex());
                this.regexTextfield.getSearchTextfield().setToolTipText(
                        Messages.getString("regex"));
                // this.regexTextfield.addKeyListener(regexChanged);
                // this.regexTextfield.addFocusListener(focusListener);

                this.replaceStringTextfield.setText(this.currentMatchReplaceSettings
                        .getReplaceString());
                this.replaceStringTextfield.setToolTipText(Messages
                        .getString("replaceString"));

                this.toolnameTextfield.setText(this.currentMatchReplaceSettings
                        .getToolnames());
                this.toolnameTextfield.setToolTipText(Messages
                        .getString("replaceToolName"));

                this.activeReplace.setSelected(this.currentMatchReplaceSettings
                        .isMatchReplaceActive());

                this.regexForNewReplaceStringFromResponseExtraction
                .getSearchTextfield()
                .setText(
                        this.currentMatchReplaceSettings
                        .getRegexForNewReplaceStringFromResponseExtraction());
                this.regexForNewReplaceStringFromResponseExtraction
                .getSearchTextfield()
                .setToolTipText(
                        Messages.getString("regexForNewReplaceStringFromResponseExtraction"));
                // this.regexForNewReplaceStringFromResponseExtraction
                // .addKeyListener(regexChanged);
                // this.regexForNewReplaceStringFromResponseExtraction
                // .addFocusListener(focusListener);

                // this.csvDoing.setText(Variables.getInstance().getCsvDoingString());
                // this.csvDoing.setToolTipText(Messages.getString("csvDoingString")
                // + Variables.getInstance().getProcessingsString());

                this.activeResponseUnpacking.setSelected(Variables.getInstance()
                        .isResponseUnpackingActive());

                this.responseRelevantRegexTextfield.getSearchTextfield().setText(
                        Variables.getInstance().getRelevantResponseRegex());
                this.responseRelevantRegexTextfield.getSearchTextfield()
                .setToolTipText(Messages.getString("relevantResponseRegex"));
                // this.responseRelevantRegexTextfield.addKeyListener(regexChanged);
                // this.responseRelevantRegexTextfield.addFocusListener(focusListener);

                this.responseToolnameTextfield.setText(Variables.getInstance()
                        .getResponseToolName());
                this.responseToolnameTextfield.setToolTipText(Messages
                        .getString("responseToolName"));

                this.requestRelevantRegexTextfield.getSearchTextfield().setText(
                        Variables.getInstance().getRelevantResquestRegex());
                this.requestRelevantRegexTextfield.getSearchTextfield().setToolTipText(
                        Messages.getString("relevantResquestRegex"));
                // this.requestRelevantRegexTextfield.addKeyListener(regexChanged);
                // this.requestRelevantRegexTextfield.addFocusListener(focusListener);

                this.requestToolnameTextfield.setText(Variables.getInstance()
                        .getRequestToolName());
                this.requestToolnameTextfield.setToolTipText(Messages
                        .getString("requestToolName"));

                // this.csvResponseDoing.setText(Variables.getInstance()
                // .getCsvResponseDoingString());
                // this.csvResponseDoing.setToolTipText(Messages
                // .getString("csvDoingString")
                // + Variables.getInstance().getProcessingsString());

                this.activeRequestUnpacking.setSelected(Variables.getInstance()
                        .isRequestUnpackingActive());

                // this.requestPrefix.setText(Variables.getInstance().getPrefix());
                // this.requestPrefix.setToolTipText(Messages.getString("prefix"));

                // this.activeRequestPrefix.setSelected(Variables.getInstance().isPrefixActive());


                this.relevantReplaceRequestRegexTextfield.getSearchTextfield().setText(
                        this.currentMatchReplaceSettings
                        .getRelevantReplaceRequestRegex());
                this.relevantReplaceRequestRegexTextfield.getSearchTextfield()
                .setToolTipText(
                        Messages.getString("relevantReplaceRequestRegex"));
                // this.relevantReplaceRequestRegexTextfield.addKeyListener(regexChanged);
                // this.relevantReplaceRequestRegexTextfield
                // .addFocusListener(focusListener);


                // this.activeResponseExtraction.setSelected(Variables.getInstance() //
                // .isExtractNewReplaceStringFromResponseActive());


                this.relevantRequestRegexForReplaceResponseExtractionTextfield
                .getSearchTextfield()
                .setText(
                        this.currentMatchReplaceSettings
                        .getRelevantRequestRegexForResponseExtraction());
                this.relevantRequestRegexForReplaceResponseExtractionTextfield
                .getSearchTextfield()
                .setToolTipText(
                        Messages.getString("relevantRequestRegexForReplaceResponseExtraction"));
                // this.relevantRequestRegexForReplaceResponseExtractionTextfield
                // .addKeyListener(regexChanged);
                // this.relevantRequestRegexForReplaceResponseExtractionTextfield
                // .addFocusListener(focusListener);


                this.replaceResponseExtractionMatchingNumberTextfield
                .setText(this.currentMatchReplaceSettings
                        .getResponseExtractionMatchingNumber() + "");
                this.replaceResponseExtractionMatchingNumberTextfield
                .setToolTipText(Messages
                        .getString("replaceResponseExtractionMatchingNumber"));

                this.ReplaceActionDropdownlist
                .setSelectedIndex(this.currentMatchReplaceSettings
                        .isReplaceOnlyFirstMatch() ? 0:1);

                this.activeDebugMode.setSelected(Variables.getInstance()
                        .isDebugModeActive());

                this.numberOfDebugLines.setText(""
                        + Variables.getInstance().getNumberOfDebugLines());
                this.numberOfDebugLines.setToolTipText(Messages
                        .getString("number.of.debug.lines"));

                this.isDropRequestActive.setSelected(Variables.getInstance()
                        .isDropRequestActive());

                this.dropRequestRegex.getSearchTextfield().setText(
                        Variables.getInstance().getDropRequestRegex());
                this.dropRequestRegex
                .getSearchTextfield()
                .setToolTipText(
                        Messages.getString("a.regex.matching.the.request.that.should.be.dropped"));
                // this.dropRequestRegex.addKeyListener(regexChanged);
                // this.dropRequestRegex.addFocusListener(focusListener);

                this.relevantUnpackedRequestRegexTextfield.getSearchTextfield()
                .setText(
                        Variables.getInstance()
                        .getRelevantUnpackedRequestRegex());
                this.relevantUnpackedRequestRegexTextfield
                .getSearchTextfield()
                .setToolTipText(
                        Messages.getString("a.regex.matching.the.unpacked.request.that.should.be.packed.before.sending"));
                // this.relevantUnpackedRequestRegexTextfield.addKeyListener(regexChanged);
                // this.relevantUnpackedRequestRegexTextfield
                // .addFocusListener(focusListener);

                this.regexMarkingRequestpartForUnpackingTextfield.getSearchTextfield()
                .setText(
                        Variables.getInstance()
                        .getRegexMarkingRequestpartForUnpacking());
                this.regexMarkingRequestpartForUnpackingTextfield
                .getSearchTextfield()
                .setToolTipText(
                        Messages.getString("a.regex.marking.the.relevant.part.within.the.request.which.should.be.unpacked"));
                // this.regexMarkingRequestpartForUnpackingTextfield
                // .addKeyListener(regexChanged);
                // this.regexMarkingRequestpartForUnpackingTextfield
                // .addFocusListener(focusListener);

                this.regexMarkingResponsepartForUnpackingTextfield.getSearchTextfield()
                .setText(
                        Variables.getInstance()
                        .getRegexMarkingResponsepartForUnpacking());
                this.regexMarkingResponsepartForUnpackingTextfield
                .getSearchTextfield()
                .setToolTipText(
                        Messages.getString("a.regex.marking.the.relevant.part.within.the.response.which.should.be.unpacked"));
                // this.regexMarkingResponsepartForUnpackingTextfield
                // .addKeyListener(regexChanged);
                // this.regexMarkingResponsepartForUnpackingTextfield
                // .addFocusListener(focusListener);

                this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox
                .setSelectedIndex(this.currentMatchReplaceSettings
                        .isRegexForResponseExtractionIdentifiesRequest() ? 0:1);

                this.languageSelection.setSelectedItem(Variables.getInstance()
                        .getSelectedLanguage());

                this.matchReplaceActionDropdownlist
                .setSelectedIndex(this.currentMatchReplaceSettings
                        .getMatchReplaceAction());

                // this.regexDropdownListStringTextfield.setText(Variables.getInstance()
                // .getRegexDropdownList());

                // this.payloadListPathTextfield.setText(Variables.getInstance() //
                // .getPayloadListFilepath());
                // this.payloadListPathTextfield.setToolTipText(Messages
                // .getString("the.file.path.to.the.payload.list"));
                this.matchIndicatesNonrelevanceForMatchreplaceRequest
                .setSelected(this.currentMatchReplaceSettings
                        .isMatchIndicatesNonrelevanceForMatchreplaceRequest());

                this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction
                .setSelected(this.currentMatchReplaceSettings
                        .isMatchIndicatesNonrelevanceForResponseextraction());

                this.matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking
                .setSelected(Variables
                        .getInstance()
                        .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking());
                this.matchIndicatesNonrelevanceForRequestunpackingAfterunpacking
                .setSelected(Variables
                        .getInstance()
                        .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking());
                this.matchIndicatesNonrelevanceForResponseunpacking
                .setSelected(Variables
                        .getInstance()
                        .isRegexmatchIndicatingInrelevanceOnCurrentMessageForResponseunpacking());

                this.matchReplaceProcessingCsvstringTextfield
                .setProcessingString(this.currentMatchReplaceSettings
                        .getProcessingOfReplacestringCsvstring());
                // this.matchReplaceProcessingCsvstringTextfield.setToolTipText(Messages
                // .getString("csvDoingString")
                // + Variables.getInstance().getProcessingsString());

                this.sessionHandlingActionDelayTextfield.setText(""
                        + Variables.getInstance()
                        .getSessionHandlingProduceDelayTimeInMillis());

                this.responseExtractionToolnamesTextfield.setToolTipText(Messages
                        .getString("response.extraction.toolnames"));


                this.loadActualMatchReplaceSettingsIndex();




                this.setValues1 = new JButton(Messages.getString("set.values"));
                this.setValues2 = new JButton(Messages.getString("set.values"));
                this.setValues3 = new JButton(Messages.getString("set.values"));
                this.setValues4 = new JButton(Messages.getString("set.values"));
                this.setValues5 = new JButton(Messages.getString("set.values"));
                this.setValues6 = new JButton(Messages.getString("set.values"));
                ActionListener save = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        TextFieldsWindow2.this.setValues();
                        TextFieldsWindow2.this.setEditable();

                    }
                };

                JButton showDebug = new JButton("Show Debug Output");
                ActionListener showDebugAction = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (TextFieldsWindow2.this.debugModeDisplay == null) {
                            TextFieldsWindow2.this.debugModeDisplay = DisplayText
                                    .getInstanceOfDebugWindow();
                        }
                        else {
                            TextFieldsWindow2.this.debugModeDisplay.showDebugWindow();
                        }
                    }
                };

                ActionListener changesMade = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        TextFieldsWindow2.this.setEditable();

                    }
                };

                this.showRegexTest = new JButton(Messages.getString("regex.text"));
                this.showRegexTest2 = new JButton(Messages.getString("regex.text"));
                this.showRegexTest3 = new JButton(Messages.getString("regex.text"));
                this.showRegexTest4 = new JButton(Messages.getString("regex.text"));
                this.showRegexTest5 = new JButton(Messages.getString("regex.text"));
                this.showRegexTest6 = new JButton(Messages.getString("regex.text"));
                this.showRegexTest7 = new JButton(Messages.getString("regex.text"));
                ActionListener regexTestAction = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (TextFieldsWindow2.this.regexTestDisplay == null) {
                            TextFieldsWindow2.this.regexTestDisplay = DisplayText
                                    .getInstanceOfRegexTestWindow();

                        }
                        // }
                        // if (TextFieldsWindow2.this.regexTestDisplay == null) {
                        // TextFieldsWindow2.this.regexTestDisplay = new DisplayText(
                        // "Regex Test");
                        // }
                        TextFieldsWindow2.this.regexTestDisplay.showRegexTestWindow();
                        TextFieldsWindow2.this.reRegisterSearchlabelsMaintextarea();
                    }
                };

                ActionListener saveValuesToFileActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        MyFileChooser fc = new MyFileChooser(Variables.getInstance()
                                .getWorkingDirectory(), ".gsv", "Gunziper Saved Values");
                        fc.init(true);
                        if ((fc.getFile() != null) && fc.writeOkDependingOnExistingFile) {
                            TextFieldsWindow2.this.setValues();
                            VariablesFunctions.saveValuesToFile(fc.getFile()
                                    .getAbsolutePath());
                        }

                    }
                };

                ActionListener loadValuesFromFileActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        MyFileChooser fc = new MyFileChooser(Variables.getInstance()
                                .getWorkingDirectory(), ".gsv", "Gunziper Saved Values");
                        fc.init(false);
                        if (fc.getFile() != null) {
                            VariablesFunctions.loadValuesFromFile(fc.getFile()
                                    .getAbsolutePath());
                            TextFieldsWindow2.this.resetValues();
                        }

                    }
                };

                ActionListener loadPayloadListActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        MyFileChooser fc = new MyFileChooser(Variables.getInstance()
                                .getWorkingDirectory(), ".*",
                                "Files containing Payload-Strings");
                        fc.init(false);
                        if ((fc.getFile() != null) && fc.getFile().exists()) {
                            Variables
                            .getInstance()
                            .loadPayloadListForIntruderCompareFromSpecifiedFile(
                                    fc.getFile());
                            Variables.getInstance().setWorkingDirectory(
                                    fc.getFile().getParentFile().getAbsolutePath());
                        }
                    }
                };
                this.loadPayloadListButton
                .addActionListener(loadPayloadListActionlistener);


                ActionListener showLicenseStuffActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        new DisplayText("License", Variables.getInstance()
                                .getOwnLicenseString()
                                + "\n------------------------\n\n"
                                + Variables.getInstance().getForeignLicenseString()
                                + "\n------------------------\n\n"
                                + Variables.getInstance().getForeignLicenseString2());
                    }
                };
                this.showLicenseStuffButton
                .addActionListener(showLicenseStuffActionlistener);

                ActionListener changedMatchReplaceNumberActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // TextFieldsWindow2.this.currentMatchReplaceSettings =
                        // Variables.getInstance() // .getMatchReplaceSettingsArray()
                        // .get(
                        // (((JComboBox) e.getSource()).getSelectedIndex()) - 1);
                        TextFieldsWindow2.this.loadActualMatchReplaceSettingsIndex();

                    }
                };
                this.matchReplacesIteratorCombobox
                .addActionListener(changedMatchReplaceNumberActionlistener);

                ActionListener matchReplaceSaveCurrentSettingsActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        TextFieldsWindow2.this.saveActualMatchReplaceSettingsIndex();
                    }
                };
                this.matchReplaceSaveCurrentSettingsButton
                .addActionListener(matchReplaceSaveCurrentSettingsActionlistener);

                this.setEditable();
                // textfield.setHorizontalAlignment(SwingConstants.LEFT);

                JTabbedPane tabbedPane = this;
                JPanel requestUnpackingPanel = new JPanel(new GridLayout(14, 2));
                // JPanel requestUnpackingPanelWrapper = new JPanel();
                JScrollPane requestUnpackingPanelScrollpane = new JScrollPane(
                        requestUnpackingPanel,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                JPanel responseUnpackingPanel = new JPanel(new GridLayout(19, 2, 4, 4));

                JPanel matchReplacePanel = new JPanel(new GridLayout(48, 2, 4, 4));
                JScrollPane matchReplacePanelScrollpane = new JScrollPane(
                        matchReplacePanel,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


                JPanel intruderComparerPanel = new JPanel(new GridLayout(4, 1, 4, 4));
                JPanel debugModePanel = new JPanel(new GridLayout(7, 4, 4, 4));
                JPanel requestDroppingPanel = new JPanel(new GridLayout(7, 4, 4, 4));

                // JEditorPane helpPane = new JEditorPane();
                // helpPane.setEditable(false);
                // JScrollPane helpScrollpane = new JScrollPane(helpPane,
                // ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                // ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


                tabbedPane.addTab(Messages.getString("request.unpacking"),
                        requestUnpackingPanelScrollpane);
                tabbedPane.addTab(Messages.getString("response.unpacking"),
                        responseUnpackingPanel);
                tabbedPane.addTab(Messages.getString("match.replace"),
                        matchReplacePanelScrollpane);
                tabbedPane.addTab(Messages.getString("intruder.comparer"),
                        intruderComparerPanel);
                tabbedPane.addTab(Messages.getString("debug.mode"), debugModePanel);
                tabbedPane.addTab(Messages.getString("request.dropping"),
                        requestDroppingPanel);
                tabbedPane.addTab(Messages.getString("misc"), miscPanel);
                tabbedPane.addTab(Messages.getString("help"), new HelpPanel());




                // requestUnpackingPanel.add(new JLabel("--- Request Unpacking ---"));
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(this.activeRequestUnpacking);
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel
                .add(new JLabel(
                        Messages.getString("this.module.just.adds.an.additional.view.to.proxy.and.repeater.but.does.no.real.modification")));
                requestUnpackingPanel
                .add(new JLabel(
                        Messages.getString("for.usage.within.intruder.scanner.and.so.on.resp.real.modification.see.match.replace.tab")));
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(this.requestToolnameTextfield);
                // requestUnpackingPanel.add(new
                // JLabel(Messages.getString("tool.scope")));
                requestUnpackingPanel.add(this.requestRelevantRegexTextfield);
                requestUnpackingPanel
                .add(new JLabel(
                        Messages.getString("regex.identifying.requests.in.scope.for.processing")));

                requestUnpackingPanel
                .add(this.matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking);
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(this.relevantUnpackedRequestRegexTextfield);

                requestUnpackingPanel
                .add(new JLabel(
                        Messages.getString("regex.identifying.requests.in.scope.after.processing")));
                // requestUnpackingPanel.add(new JPanel());

                requestUnpackingPanel
                .add(this.matchIndicatesNonrelevanceForRequestunpackingAfterunpacking);
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel
                .add(this.regexMarkingRequestpartForUnpackingTextfield);
                requestUnpackingPanel.add(new JLabel(Messages
                        .getString("regex.marking.the.part.for.unpacking")));
                requestUnpackingPanel.add(this.csvDoing);
                requestUnpackingPanel.add(new JLabel(Messages
                        .getString("comma.separated.list.of.processings")));
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());

                requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(this.showRegexTest);
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());
                // requestUnpackingPanel.add(new JPanel());

                // requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(new JPanel());
                requestUnpackingPanel.add(this.setValues1);





                // responseUnpackingPanel.add(new JLabel("--- Response Unpacking ---"));
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(this.activeResponseUnpacking);
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel
                .add(new JLabel(
                        Messages.getString("this.module.just.adds.an.additional.view.to.proxy.and.repeater.but.does.no.real.modification")));
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                // responseUnpackingPanel
                // .add(new JLabel(Messages.getString("tool.scope")));
                responseUnpackingPanel
                .add(new JLabel(
                        Messages.getString("regex.identifying.responses.in.scope.for.processing")));
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                // responseUnpackingPanel.add(this.responseToolnameTextfield);
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(this.responseRelevantRegexTextfield);
                responseUnpackingPanel
                .add(this.matchIndicatesNonrelevanceForResponseunpacking);
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JLabel(Messages
                        .getString("comma.separated.list.of.processings")));
                responseUnpackingPanel.add(new JLabel(Messages
                        .getString("regex.marking.the.part.for.unpacking")));
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(this.csvResponseDoing);
                responseUnpackingPanel
                .add(this.regexMarkingResponsepartForUnpackingTextfield);
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(this.showRegexTest2);
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());

                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                // responseUnpackingPanel.add(new JPanel());
                // responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(new JPanel());
                responseUnpackingPanel.add(this.setValues2);




                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(this.matchReplacesIteratorCombobox);
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());

                matchReplacePanel.add(this.activeReplace);
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());

                matchReplacePanel.add(new JLabel("--- Match/Replace Part ---"));
                matchReplacePanel.add(new JPanel());

                matchReplacePanel.add(this.regexTextfield);
                matchReplacePanel.add(new JLabel(Messages.getString("regex")));
                matchReplacePanel.add(this.replaceStringTextfield);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("replace.it.with.this.string.with.number.iteration.supply.first.number")));
                matchReplacePanel.add(this.ReplaceActionDropdownlist);
                matchReplacePanel.add(new JLabel(Messages
                        .getString("replace.either.all.or.only.the.first.match")));
                matchReplacePanel.add(this.matchReplaceActionDropdownlist);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("number.iterator.increments.the.supplied.number.by.one.on.each.request.response.extraction.extracts.the.next.replace.string.from.responses.see.bottom")));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(this.doRequestReplacementAfterUnpackingCheckbox);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("this.is.necessary.if.the.part.to.be.replaced.is.in.an.packed.area.defined.within.the.request.unpacking.tab")
                        + Messages
                        .getString("see.help.tab.on.the.gunziper.tab.for.more.information")));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());



                matchReplacePanel.add(new JLabel("--- (Additional) Processing ---"));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(this.matchReplaceProcessingActiveCheckbox);
                matchReplacePanel.add(new JPanel());

                matchReplacePanel.add(this.matchReplaceProcessingCsvstringTextfield);
                matchReplacePanel.add(new JLabel(Messages
                        .getString("comma.separated.list.of.processings")));
                matchReplacePanel.add(this.doProcessingsOnMarkedPartCheckbox);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("this.will.prevent.any.replacement.of.the.marked.part.by.the.replace.string.but.only.does.a.processing.of.it")));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());


                matchReplacePanel.add(new JLabel("--- Scope Part ---"));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("see.help.tab.on.the.gunziper.tab.for.more.information")));
                matchReplacePanel.add(new JPanel());


                matchReplacePanel.add(this.activateRuleGloballyCheckbox);
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(this.toolnameTextfield);
                matchReplacePanel.add(new JLabel(Messages.getString("tool.scope")));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());

                matchReplacePanel.add(this.relevantReplaceRequestRegexTextfield);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("regex.identifying.requests.in.scope.for.processing")));

                matchReplacePanel
                .add(this.matchIndicatesNonrelevanceForMatchreplaceRequest);
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());



                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(this.showRegexTest3);
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());




                matchReplacePanel
                .add(new JLabel(
                        "--------- "
                                + Messages
                                .getString("configuration.part.for.response.extraction")
                                + " ---------"));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("only.relevant.when.using.response.extraction.see.match.replace.part")));
                matchReplacePanel.add(new JPanel());

                matchReplacePanel
                .add(this.regexForNewReplaceStringFromResponseExtraction);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("regex.containing.a.regex.group.marking.the.part.which.should.be.extracted.from.the.response.or.request")));

                matchReplacePanel
                .add(this.isextractFromRequestInsteadFromResponseCheckbox);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("extract.the.new.replace.string.from.requests.instead.from.responses")));
                matchReplacePanel
                .add(this.replaceResponseExtractionMatchingNumberTextfield);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("the.standard.value.should.be.fine.for.most.scenarios.it.defines.which.match.is.used")));

                matchReplacePanel.add(this.doResponseExtractionAfterUnpackingCheckbox);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("if.response.unpacking.is.defined.this.can.be.used.if.the.relevant.part.to.extract.is.packed")
                        + Messages
                        .getString("see.help.tab.on.the.gunziper.tab.for.more.information")));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());




                matchReplacePanel.add(new JLabel(
                        "--- Response Extraction Scope Part ---"));
                matchReplacePanel.add(new JPanel());

                matchReplacePanel.add(this.responseExtractionToolnamesTextfield);
                matchReplacePanel.add(new JLabel(Messages.getString("tool.scope")));
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel
                .add(this.relevantRequestRegexForReplaceResponseExtractionTextfield);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("regex.identifying.requests.in.scope.for.processing")));
                matchReplacePanel
                .add(this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox);
                matchReplacePanel
                .add(new JLabel(
                        Messages.getString("defines.whether.the.scope.regex.is.applied.on.the.request.or.response")));

                matchReplacePanel
                .add(this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction);
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());
                matchReplacePanel.add(new JPanel());


                matchReplacePanel.add(this.matchReplaceSaveCurrentSettingsButton);
                matchReplacePanel.add(new JPanel());





                // staticInsertionPanel.add(new JLabel("--- Static Insertion ---"));
                intruderComparerPanel.add(new JPanel());
                // intruderComparerPanel.add(this.regexDropdownListStringTextfield);
                // miscPanel.add(this.payloadListPathTextfield);
                intruderComparerPanel.add(this.loadPayloadListButton);
                intruderComparerPanel.add(this.setValues3);





                debugModePanel.add(new JLabel("--- Debug Mode ---"));
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(this.numberOfDebugLines);
                debugModePanel.add(this.activeDebugMode);
                debugModePanel.add(new JPanel());
                debugModePanel.add(showDebug);
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(this.setValues4);
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                debugModePanel.add(new JPanel());
                // panel.add(new JPanel());
                // panel.add(new JPanel());
                // panel.add(new JPanel());
                // panel.add(new JPanel());
                requestDroppingPanel.add(new JLabel("--- Request Dropping ---"));
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(this.dropRequestRegex);
                requestDroppingPanel.add(this.showRegexTest5);

                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(this.isDropRequestActive);
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(this.setValues5);
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                requestDroppingPanel.add(new JPanel());
                // requestDroppingPanel.add(new JPanel());
                // panel.add(new JPanel());
                // panel.add(new JPanel());
                // panel.add(new JPanel());
                // panel.add(new JPanel());
                miscPanel.add(this.languageSelection);
                // miscPanel.add(this.showRegexTest);
                miscPanel.add(new JPanel());
                miscPanel.add(new JPanel());

                miscPanel.add(this.sessionHandlingActionDelayTextfield);
                miscPanel.add(this.loadSettingsButton);
                miscPanel.add(this.saveSettingsButton);
                miscPanel.add(this.showLicenseStuffButton);
                miscPanel.add(this.setValues6);




                // HTMLEditorKit htmlKit = new HTMLEditorKit();
                // helpPane.setEditorKit(htmlKit);
                // URL startPage = TextFieldsWindow2.class
                // .getResource("../docu/index.html");
                // if (startPage != null) {
                // try {
                // helpPane.setPage(startPage);
                // }catch (IOException e) {
                // helpPane
                // .setText("<h1>"
                // + Messages
                // .getString("an.error.occured.the.documentation.is.not.available.this.shouldn't.happen.just.contact.the.author.of.the.plugin.and.ask.him.what.the.fu...he.has.done.this.time")
                // + "</h1>");
                //
                // }
                // }
                // else {
                // helpPane
                // .setText("<h1>"
                // + Messages
                // .getString("an.error.occured.the.documentation.is.not.available.this.shouldn't.happen.just.contact.the.author.of.the.plugin.and.ask.him.what.the.fu...he.has.done.this.time")
                // + "</h1>");
                // }





                this.setValues1.addActionListener(save);
                this.setValues2.addActionListener(save);
                this.setValues3.addActionListener(save);
                this.setValues4.addActionListener(save);
                this.setValues5.addActionListener(save);
                this.setValues6.addActionListener(save);
                showDebug.addActionListener(showDebugAction);
                this.showRegexTest.addActionListener(regexTestAction);
                this.showRegexTest2.addActionListener(regexTestAction);
                this.showRegexTest3.addActionListener(regexTestAction);
                this.showRegexTest4.addActionListener(regexTestAction);
                this.showRegexTest5.addActionListener(regexTestAction);
                this.showRegexTest6.addActionListener(regexTestAction);
                this.showRegexTest7.addActionListener(regexTestAction);


                this.activeResponseUnpacking.addActionListener(changesMade);
                // this.activeRequestPrefix.addActionListener(changesMade);
                this.activeRequestUnpacking.addActionListener(changesMade);
                // this.activeResponseExtraction.addActionListener(changesMade);
                this.activeReplace.addActionListener(changesMade);
                this.activateRuleGloballyCheckbox.addActionListener(changesMade);
                this.isDropRequestActive.addActionListener(changesMade);
                this.matchReplaceActionDropdownlist.addActionListener(changesMade);
                this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox
                .addActionListener(changesMade);
                this.isextractFromRequestInsteadFromResponseCheckbox
                .addActionListener(changesMade);

                this.saveSettingsButton
                .addActionListener(saveValuesToFileActionlistener);
                this.loadSettingsButton
                .addActionListener(loadValuesFromFileActionlistener);
                this.matchReplaceProcessingActiveCheckbox
                .addActionListener(changesMade);
                this.doProcessingsOnMarkedPartCheckbox.addActionListener(changesMade);
                // callbacks.customizeUiComponent(this); //TODO
                // this.setContentPane(panel); JFRAME TO JPANEL
                // this.add(tabbedPane);

                this.regexTestDisplay = DisplayText.getInstanceOfRegexTestWindow();
                this.reRegisterSearchlabelsMaintextarea();


                this.setVisible(true);
                // this.pack(); JFRAME TO JPANEL
                // this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                // JFRAME TO JPANEL
    }

    private void checkCSVdoingString(String csvDoingString)
            throws IllegalArgumentException {

        if (csvDoingString.equals(""))
            throw new IllegalArgumentException(
                    Messages.getString("the.csv.doing.string.contains.no.processing.string.at.all"));

        boolean wrongDoingStringFound = false;
        String[] doingArray = csvDoingString.split(",");
        String wrongValue = "";
        for (String actTodo:doingArray) {
            if (!Variables.getInstance().getProcessingsString().toLowerCase()
                    .replaceAll("\\s", "").contains(actTodo.toLowerCase())) {
                wrongDoingStringFound = true;
                wrongValue = actTodo;
            }
        }

        if (wrongDoingStringFound)
            throw new IllegalArgumentException(
                    Messages.getString("the.csv.doing.string.contains.at.least.one.wrong.value")
                    + wrongValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see burp.ITab#getTabCaption()
     */
    @Override
    public String getTabCaption() {

        return "gunziper config";
    }

    /*
     * (non-Javadoc)
     *
     * @see burp.ITab#getUiComponent()
     */
    @Override
    public Component getUiComponent() {

        return this;
    }

    private void loadActualMatchReplaceSettingsIndex() {

        this.setCurrentMatchReplaceSettings();

        this.toolnameTextfield.setText(this.currentMatchReplaceSettings
                .getToolnames());

        this.relevantReplaceRequestRegexTextfield.getSearchTextfield().setText(
                this.currentMatchReplaceSettings
                .getRelevantReplaceRequestRegex());

        this.matchIndicatesNonrelevanceForMatchreplaceRequest
        .setSelected(this.currentMatchReplaceSettings
                .isMatchIndicatesNonrelevanceForMatchreplaceRequest());

        this.regexTextfield.getSearchTextfield().setText(
                this.currentMatchReplaceSettings
                .getRegexMarkingRelevantPartForReplacement());

        this.replaceStringTextfield.setText(this.currentMatchReplaceSettings
                .getReplaceString());

        this.ReplaceActionDropdownlist
        .setSelectedIndex(this.currentMatchReplaceSettings
                .isReplaceOnlyFirstMatch() ? 0:1);

        this.matchReplaceActionDropdownlist
        .setSelectedIndex(this.currentMatchReplaceSettings
                .getMatchReplaceAction());

        this.activeReplace.setSelected(this.currentMatchReplaceSettings
                .isMatchReplaceActive());

        this.relevantRequestRegexForReplaceResponseExtractionTextfield
        .getSearchTextfield()
        .setText(
                this.currentMatchReplaceSettings
                .getRelevantRequestRegexForResponseExtraction());

        this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox
        .setSelectedIndex(this.currentMatchReplaceSettings
                .isRegexForResponseExtractionIdentifiesRequest() ? 0:1);

        this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction
        .setSelected(this.currentMatchReplaceSettings
                .isMatchIndicatesNonrelevanceForResponseextraction());

        this.regexForNewReplaceStringFromResponseExtraction
        .getSearchTextfield()
        .setText(
                this.currentMatchReplaceSettings
                .getRegexForNewReplaceStringFromResponseExtraction());

        this.replaceResponseExtractionMatchingNumberTextfield
        .setText(this.currentMatchReplaceSettings
                .getResponseExtractionMatchingNumber() + "");

        this.matchReplaceProcessingActiveCheckbox
        .setSelected(this.currentMatchReplaceSettings
                .isProcessingOfReplacestringActive());

        this.matchReplaceProcessingCsvstringTextfield
        .setProcessingString(this.currentMatchReplaceSettings
                .getProcessingOfReplacestringCsvstring());

        this.responseExtractionToolnamesTextfield
        .setText(this.currentMatchReplaceSettings
                .getResponseToolnames());
        this.doProcessingsOnMarkedPartCheckbox
        .setSelected(this.currentMatchReplaceSettings
                .isOnlyDoProcessingOfMarkedPart());
        this.doRequestReplacementAfterUnpackingCheckbox
        .setSelected(this.currentMatchReplaceSettings
                .isDoRequestReplacementAfterUnpacking());
        this.doResponseExtractionAfterUnpackingCheckbox
        .setSelected(this.currentMatchReplaceSettings
                .isDoResponseExtractionAfterUnpacking());
        this.activateRuleGloballyCheckbox
        .setSelected(this.currentMatchReplaceSettings
                .isActivateThisRuleGlobally());
        this.isextractFromRequestInsteadFromResponseCheckbox
        .setSelected(this.currentMatchReplaceSettings
                .isExtractFromRequestInsteadFromResponse());

        this.setEditable();
    }

    private void reRegisterSearchlabelsMaintextarea() {

        this.regexTextfield.registerNewTextarea(this.regexTestDisplay
                .getMainTextarea());
        this.responseRelevantRegexTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.requestRelevantRegexTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.relevantRequestRegexForReplaceResponseExtractionTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.relevantReplaceRequestRegexTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.regexForNewReplaceStringFromResponseExtraction
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.dropRequestRegex.registerNewTextarea(this.regexTestDisplay
                .getMainTextarea());
        this.relevantUnpackedRequestRegexTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.regexMarkingRequestpartForUnpackingTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());
        this.regexMarkingResponsepartForUnpackingTextfield
        .registerNewTextarea(this.regexTestDisplay.getMainTextarea());

    }

    public void resetValues() {

        this.regexTextfield.getSearchTextfield().setText(
                Variables.getInstance().getRegex());

        this.replaceStringTextfield.setText(this.currentMatchReplaceSettings
                .getReplaceString());

        this.toolnameTextfield.setText(this.currentMatchReplaceSettings
                .getToolnames());

        this.activeReplace.setSelected(this.currentMatchReplaceSettings
                .isMatchReplaceActive());

        this.regexForNewReplaceStringFromResponseExtraction
        .getSearchTextfield()
        .setText(
                this.currentMatchReplaceSettings
                .getRegexForNewReplaceStringFromResponseExtraction());

        this.csvDoing.setProcessingString(Variables.getInstance()
                .getCsvDoingString());

        this.activeResponseUnpacking.setSelected(Variables.getInstance()
                .isResponseUnpackingActive());

        this.responseRelevantRegexTextfield.getSearchTextfield().setText(
                Variables.getInstance().getRelevantResponseRegex());

        this.responseToolnameTextfield.setText(Variables.getInstance()
                .getResponseToolName());

        this.requestRelevantRegexTextfield.getSearchTextfield().setText(
                Variables.getInstance().getRelevantResquestRegex());

        this.requestToolnameTextfield.setText(Variables.getInstance()
                .getRequestToolName());

        this.csvResponseDoing.setProcessingString(Variables.getInstance()
                .getCsvResponseDoingString());

        this.activeRequestUnpacking.setSelected(Variables.getInstance()
                .isRequestUnpackingActive());





        this.relevantReplaceRequestRegexTextfield.getSearchTextfield().setText(
                this.currentMatchReplaceSettings
                .getRelevantReplaceRequestRegex());



        this.relevantRequestRegexForReplaceResponseExtractionTextfield
        .getSearchTextfield()
        .setText(
                this.currentMatchReplaceSettings
                .getRelevantRequestRegexForResponseExtraction());


        this.replaceResponseExtractionMatchingNumberTextfield
        .setText(this.currentMatchReplaceSettings
                .getResponseExtractionMatchingNumber() + "");

        this.ReplaceActionDropdownlist
        .setSelectedIndex(this.currentMatchReplaceSettings
                .isReplaceOnlyFirstMatch() ? 0:1);

        this.activeDebugMode.setSelected(Variables.getInstance()
                .isDebugModeActive());

        this.numberOfDebugLines.setText(""
                + Variables.getInstance().getNumberOfDebugLines());

        this.isDropRequestActive.setSelected(Variables.getInstance()
                .isDropRequestActive());

        this.dropRequestRegex.getSearchTextfield().setText(
                Variables.getInstance().getDropRequestRegex());

        this.relevantUnpackedRequestRegexTextfield.getSearchTextfield()
        .setText(
                Variables.getInstance()
                .getRelevantUnpackedRequestRegex());

        this.regexMarkingRequestpartForUnpackingTextfield.getSearchTextfield()
        .setText(
                Variables.getInstance()
                .getRegexMarkingRequestpartForUnpacking());


        this.regexMarkingResponsepartForUnpackingTextfield.getSearchTextfield()
        .setText(
                Variables.getInstance()
                .getRegexMarkingResponsepartForUnpacking());

        this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox
        .setSelectedIndex(this.currentMatchReplaceSettings
                .isMatchIndicatesNonrelevanceForResponseextraction() ? 0
                        :1);

        this.languageSelection.setSelectedItem(Variables.getInstance()
                .getSelectedLanguage());

        this.matchReplaceActionDropdownlist
        .setSelectedIndex(this.currentMatchReplaceSettings
                .getMatchReplaceAction());

        // this.regexDropdownListStringTextfield.setText(Variables.getInstance()
        // .getRegexDropdownList());

        this.matchIndicatesNonrelevanceForMatchreplaceRequest
        .setSelected(this.currentMatchReplaceSettings
                .isMatchIndicatesNonrelevanceForMatchreplaceRequest());

        this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction
        .setSelected(this.currentMatchReplaceSettings
                .isMatchIndicatesNonrelevanceForResponseextraction());

        this.matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking
        .setSelected(Variables
                .getInstance()
                .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking());
        this.matchIndicatesNonrelevanceForRequestunpackingAfterunpacking
        .setSelected(Variables
                .getInstance()
                .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking());
        this.matchIndicatesNonrelevanceForResponseunpacking
        .setSelected(Variables
                .getInstance()
                .isRegexmatchIndicatingInrelevanceOnCurrentMessageForResponseunpacking());
        this.sessionHandlingActionDelayTextfield.setText(""
                + Variables.getInstance()
                .getSessionHandlingProduceDelayTimeInMillis());

        // this.payloadListPathTextfield.setText(Variables.getInstance() //
        // .getPayloadListFilepath());

        this.loadActualMatchReplaceSettingsIndex();
    }

    private void saveActualMatchReplaceSettingsIndex() {

        this.currentMatchReplaceSettings.setToolnames(this.toolnameTextfield
                .getText());

        this.currentMatchReplaceSettings
        .setRelevantReplaceRequestRegex(this.relevantReplaceRequestRegexTextfield
                .getSearchTextfield().getText());

        this.currentMatchReplaceSettings
        .setMatchIndicatesNonrelevanceForMatchreplaceRequest(this.matchIndicatesNonrelevanceForMatchreplaceRequest
                .isSelected());

        this.currentMatchReplaceSettings
        .setRegexMarkingRelevantPartForReplacement(this.regexTextfield
                .getSearchTextfield().getText());

        this.currentMatchReplaceSettings
        .setReplaceString(this.replaceStringTextfield.getText());

        this.currentMatchReplaceSettings
        .setReplaceOnlyFirstMatch(this.ReplaceActionDropdownlist
                .getSelectedIndex() == 0);

        this.currentMatchReplaceSettings
        .setMatchReplaceAction(this.matchReplaceActionDropdownlist
                .getSelectedIndex());

        this.currentMatchReplaceSettings
        .setMatchReplaceActive(this.activeReplace.isSelected());

        this.currentMatchReplaceSettings
        .setRelevantRequestRegexForResponseExtraction(this.relevantRequestRegexForReplaceResponseExtractionTextfield
                .getSearchTextfield().getText());

        this.currentMatchReplaceSettings
        .setRegexForResponseExtractionIdentifiesRequest(this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox
                .getSelectedIndex() == 0);

        this.currentMatchReplaceSettings
        .setMatchIndicatesNonrelevanceForResponseextraction(this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction
                .isSelected());

        this.currentMatchReplaceSettings
        .setRegexForNewReplaceStringFromResponseExtraction(this.regexForNewReplaceStringFromResponseExtraction
                .getSearchTextfield().getText());

        try {
            this.currentMatchReplaceSettings
            .setResponseExtractionMatchingNumber(Integer
                    .parseInt(this.replaceResponseExtractionMatchingNumberTextfield
                            .getText()));
        }catch (NumberFormatException ex) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("invalid.response.extraction.number"));
            ex.printStackTrace();
        }

        this.currentMatchReplaceSettings
        .setProcessingOfReplacestringActive(this.matchReplaceProcessingActiveCheckbox
                .isSelected());


        try {
            if (this.currentMatchReplaceSettings.isMatchReplaceActive()) {
                this.checkCSVdoingString(this.matchReplaceProcessingCsvstringTextfield
                        .getProcessingString());
                this.currentMatchReplaceSettings
                .setProcessingOfReplacestringCsvstring(this.matchReplaceProcessingCsvstringTextfield
                        .getProcessingString());
            }
        }catch (IllegalArgumentException ex) {
            new DisplayText(Messages.getString("errorOccured"), ex.getMessage());
            // ex.printStackTrace();
        }

        this.currentMatchReplaceSettings
        .setResponseToolnames(this.responseExtractionToolnamesTextfield
                .getText());

        this.currentMatchReplaceSettings
        .setOnlyDoProcessingOfMarkedPart(this.doProcessingsOnMarkedPartCheckbox
                .isSelected());

        this.currentMatchReplaceSettings
        .setDoRequestReplacementAfterUnpacking(this.doRequestReplacementAfterUnpackingCheckbox
                .isSelected());

        this.currentMatchReplaceSettings
        .setDoResponseExtractionAfterUnpacking(this.doResponseExtractionAfterUnpackingCheckbox
                .isSelected());

        this.currentMatchReplaceSettings
        .setActivateThisRuleGlobally(this.activateRuleGloballyCheckbox
                .isSelected());

        this.currentMatchReplaceSettings
        .setExtractFromRequestInsteadFromResponse(this.isextractFromRequestInsteadFromResponseCheckbox
                .isSelected());
    }

    private void setComponentEnabled(JComponent comp, boolean enabled) {

        if (comp.isEnabled() != enabled) {
            comp.setBackground(enabled ? null:Color.GRAY);
            comp.setEnabled(enabled);
        }
    }

    private void setCurrentMatchReplaceSettings() {

        this.currentMatchReplaceSettings = Variables.getInstance()
                .getMatchReplaceSettingsArray()
                .get(this.matchReplacesIteratorCombobox.getSelectedIndex());
    }

    private void setEditable() {

        boolean enabled = false;



        // REQUEST UNPACKING

        enabled = this.activeRequestUnpacking.isSelected();

        this.setComponentEnabled(this.requestToolnameTextfield, enabled);
        this.setComponentEnabled(
                this.requestRelevantRegexTextfield.getSearchTextfield(),
                enabled);
        this.setComponentEnabled(this.csvDoing, enabled);
        this.setComponentEnabled(
                this.relevantUnpackedRequestRegexTextfield.getSearchTextfield(),
                enabled);
        this.setComponentEnabled(
                this.regexMarkingRequestpartForUnpackingTextfield
                .getSearchTextfield(), enabled);
        this.setComponentEnabled(
                this.matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking,
                enabled);
        this.setComponentEnabled(
                this.matchIndicatesNonrelevanceForRequestunpackingAfterunpacking,
                enabled);





        // RESPONSE UNPACKING
        enabled = this.activeResponseUnpacking.isSelected();

        this.setComponentEnabled(this.responseToolnameTextfield, enabled);
        this.setComponentEnabled(
                this.responseRelevantRegexTextfield.getSearchTextfield(),
                enabled);
        this.setComponentEnabled(this.csvResponseDoing, enabled);
        this.setComponentEnabled(
                this.regexMarkingResponsepartForUnpackingTextfield
                .getSearchTextfield(), enabled);
        this.setComponentEnabled(
                this.matchIndicatesNonrelevanceForResponseunpacking, enabled);





        // MATCH REPLACE
        enabled = this.activeReplace.isSelected();
        this.setComponentEnabled(this.toolnameTextfield, enabled
                && this.activateRuleGloballyCheckbox.isSelected());
        this.setComponentEnabled(this.regexTextfield.getSearchTextfield(),
                enabled);
        this.setComponentEnabled(
                this.replaceStringTextfield,
                (!(this.doProcessingsOnMarkedPartCheckbox.isSelected() && this.matchReplaceProcessingActiveCheckbox
                        .isSelected())) && enabled);
        this.setComponentEnabled(
                this.relevantReplaceRequestRegexTextfield.getSearchTextfield(),
                enabled && this.activateRuleGloballyCheckbox.isSelected());
        this.setComponentEnabled(this.ReplaceActionDropdownlist, enabled);
        this.setComponentEnabled(this.matchReplaceActionDropdownlist, enabled);
        this.setComponentEnabled(
                this.matchIndicatesNonrelevanceForMatchreplaceRequest, enabled
                && this.activateRuleGloballyCheckbox.isSelected());
        this.setComponentEnabled(this.matchReplaceProcessingActiveCheckbox,
                enabled);
        this.setComponentEnabled(this.matchReplaceProcessingCsvstringTextfield,
                this.matchReplaceProcessingActiveCheckbox.isSelected()
                && enabled);
        this.setComponentEnabled(this.activateRuleGloballyCheckbox, enabled);
        this.setComponentEnabled(this.doProcessingsOnMarkedPartCheckbox,
                this.matchReplaceProcessingActiveCheckbox.isSelected()
                && enabled);
        this.setComponentEnabled(
                this.doRequestReplacementAfterUnpackingCheckbox, Variables
                .getInstance().isRequestUnpackingActive() && enabled);





        // MATCH REPLACE - RESPONSE EXTRACTION
        enabled = ((this.matchReplaceActionDropdownlist.getSelectedIndex() == 2) && this.activeReplace
                .isSelected());

        this.setComponentEnabled(
                this.regexForNewReplaceStringFromResponseExtraction
                .getSearchTextfield(), enabled);
        this.setComponentEnabled(
                this.relevantRequestRegexForReplaceResponseExtractionTextfield
                .getSearchTextfield(), enabled);
        this.setComponentEnabled(
                this.replaceResponseExtractionMatchingNumberTextfield, enabled);
        this.setComponentEnabled(
                this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox,
                enabled);
        this.setComponentEnabled(
                this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction,
                enabled);
        this.setComponentEnabled(this.responseExtractionToolnamesTextfield,
                enabled);
        this.setComponentEnabled(
                this.doResponseExtractionAfterUnpackingCheckbox, Variables
                .getInstance().isResponseUnpackingActive());
        this.setComponentEnabled(
                this.isextractFromRequestInsteadFromResponseCheckbox, enabled);




        // DROP REQUESTS
        this.setComponentEnabled(this.dropRequestRegex.getSearchTextfield(),
                this.isDropRequestActive.isSelected());

    }

    private void setValues() {

        Variables.getInstance().setRegex(
                this.regexTextfield.getSearchTextfield().getText());
        // this.currentMatchReplaceSettings
        // .setReplaceString(this.replaceStringTextfield.getText());
        // this.currentMatchReplaceSettings.setToolnames(this.toolnameTextfield
        // .getText());
        // TextFieldsWindow2.this.currentMatchReplaceSettings
        // .setMatchReplaceActive(this.activeReplace.isSelected());
        // this.currentMatchReplaceSettings
        // .setRegexForNewReplaceStringFromResponseExtraction(this.regexForNewReplaceStringFromResponseExtraction
        // .getSearchTextfield().getText());
        // Variables.getInstance() //
        // .setExtractNewReplaceStringFromResponseActive(TextFieldsWindow2.this.activeResponseExtraction
        // .isSelected());
        Variables.getInstance().setResponseUnpackingActive(
                this.activeResponseUnpacking.isSelected());
        Variables.getInstance().setRequestUnpackingActive(
                this.activeRequestUnpacking.isSelected());
        Variables.getInstance().setRequestToolName(
                this.requestToolnameTextfield.getText());
        Variables.getInstance().setResponseToolName(
                this.responseToolnameTextfield.getText());
        Variables.getInstance().setRelevantResponseRegex(
                this.responseRelevantRegexTextfield.getSearchTextfield()
                .getText());
        Variables.getInstance().setRelevantResquestRegex(
                this.requestRelevantRegexTextfield.getSearchTextfield()
                .getText());


        // this.currentMatchReplaceSettings
        // .setRelevantReplaceRequestRegex(this.relevantReplaceRequestRegexTextfield
        // .getSearchTextfield().getText());
        // this.currentMatchReplaceSettings
        // .setRelevantRequestRegexForResponseExtraction(this.relevantRequestRegexForReplaceResponseExtractionTextfield
        // .getSearchTextfield().getText());

        // this.currentMatchReplaceSettings
        // .setReplaceOnlyFirstMatch(this.ReplaceActionDropdownlist
        // .getSelectedIndex() == 0);
        Variables.getInstance().setDebugModeActive(
                this.activeDebugMode.isSelected());
        Variables.getInstance().setDropRequestActive(
                this.isDropRequestActive.isSelected());
        Variables.getInstance().setDropRequestRegex(
                this.dropRequestRegex.getSearchTextfield().getText());

        Variables.getInstance().setRelevantUnpackedRequestRegex(
                this.relevantUnpackedRequestRegexTextfield.getSearchTextfield()
                .getText());

        Variables.getInstance().setRegexMarkingRequestpartForUnpacking(
                this.regexMarkingRequestpartForUnpackingTextfield
                .getSearchTextfield().getText());

        Variables.getInstance().setRegexMarkingResponsepartForUnpacking(
                this.regexMarkingResponsepartForUnpackingTextfield
                .getSearchTextfield().getText());

        // this.currentMatchReplaceSettings
        // .setRegexForResponseExtractionIdentifiesRequest(this.matchReplaceResponseExtractionIsRegexMarkingRequestOrResponseJComboBox
        // .getSelectedIndex() == 0);
        Variables.getInstance().setSelectedLanguage(
                (String) this.languageSelection.getSelectedItem());
        Messages.setLanguage((String) this.languageSelection.getSelectedItem());

        // this.currentMatchReplaceSettings
        // .setMatchReplaceAction(this.matchReplaceActionDropdownlist
        // .getSelectedIndex());
        // Variables.getInstance().setRegexDropdownList(
        // this.regexDropdownListStringTextfield.getText());

        // this.currentMatchReplaceSettings
        // .setMatchIndicatesNonrelevanceForMatchreplaceRequest(this.matchIndicatesNonrelevanceForMatchreplaceRequest
        // .isSelected());
        // this.currentMatchReplaceSettings
        // .setMatchIndicatesNonrelevanceForResponseextraction(this.matchIndicatesNonrelevanceForMatchreplaceResponseextraction
        // .isSelected());
        Variables
        .getInstance()
        .setRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking(
                this.matchIndicatesNonrelevanceForRequestunpackingAfterunpacking
                .isSelected());
        Variables
        .getInstance()
        .setRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking(
                this.matchIndicatesNonrelevanceForRequestunpackingBeforeunpacking
                .isSelected());
        Variables
        .getInstance()
        .setRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking(
                this.matchIndicatesNonrelevanceForResponseunpacking
                .isSelected());


        // Variables.getInstance() //
        // .setPayloadListFilepath(this.payloadListPathTextfield
        // .getText());

        try {
            Variables.getInstance().setNumberOfDebugLines(
                    Integer.parseInt(this.numberOfDebugLines.getText()));
        }catch (NumberFormatException ex) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("invalid.number.of.debug.lines"));
            ex.printStackTrace();
        }

        // try {
        // this.currentMatchReplaceSettings
        // .setResponseExtractionMatchingNumber(Integer
        // .parseInt(this.replaceResponseExtractionMatchingNumberTextfield
        // .getText()));
        // }catch (NumberFormatException ex) {
        // new DisplayText(Messages.getString("errorOccured"), Messages
        // .getString("invalid.response.extraction.number"));
        // ex.printStackTrace();
        // }

        try {
            Variables.getInstance().setSessionHandlingProduceDelayTimeInMillis(
                    Integer.parseInt(this.sessionHandlingActionDelayTextfield
                            .getText()));
        }catch (NumberFormatException ex) {
            new DisplayText(
                    Messages.getString("errorOccured"),
                    Messages.getString("invalid.number.for.a.delay.in.milliseconds.for.the.session.handling.action"));
            ex.printStackTrace();
        }

        try {
            if (Variables.getInstance().isRequestUnpackingActive()) {
                this.checkCSVdoingString(this.csvDoing.getProcessingString());
                Variables.getInstance().setCsvDoingString(
                        this.csvDoing.getProcessingString());
            }
            if (Variables.getInstance().isResponseUnpackingActive()) {
                this.checkCSVdoingString(this.csvResponseDoing
                        .getProcessingString());
                Variables.getInstance().setCsvResponseDoingString(
                        this.csvResponseDoing.getProcessingString());
            }
            // if (this.currentMatchReplaceSettings.isMatchReplaceActive()) {
            // this
            // .checkCSVdoingString(this.matchReplaceProcessingCsvstringTextfield
            // .getText());
            // this.currentMatchReplaceSettings
            // .setProcessingOfReplacestringCsvstring(this.matchReplaceProcessingCsvstringTextfield
            // .getText());
            // }
            this.saveActualMatchReplaceSettingsIndex();




            // this.dispose(); JFRAME TO JPANEL

        }catch (IllegalArgumentException ex) {
            new DisplayText(Messages.getString("errorOccured"), ex.getMessage());
            // ex.printStackTrace();
        }
    }
}
