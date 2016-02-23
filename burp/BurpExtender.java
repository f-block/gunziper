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

package burp;

import intrudercomparer.GComparer;
import intrudercomparer.IntruderSelectionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import matchreplace.DelaySessionHandlingAction;
import matchreplace.MatchReplaceLibrary;
import matchreplace.MatchReplaceSessionHandlingAction;
import matchreplace.MatchReplaceSettings;
import misc.CustomDialog;
import misc.DisplayText;
import misc.Library;
import misc.Messages;
import misc.MyFileChooser;
import misc.SaveAllReqsRespsSelectionWindow;
import misc.TextFieldsWindow2;
import unpacking.GunziperTab;
import unpacking.UnpackSelectionWindow;
import unpacking.UnpackingLibrary;
import unpacking.api.AsciiHexDeEncrypter;
import unpacking.api.Base64DeEncoder;
import unpacking.api.DeSerializer;
import unpacking.api.DeflateDeUnpacker;
import unpacking.api.DeflateGzipcompatibleDeUnpacker;
import unpacking.api.GzipDeUnpacker;
import unpacking.api.HTMLDeEncoder;
import unpacking.api.IntHexDeEncrypter;
import unpacking.api.StringStripper;
import unpacking.api.UrlDeEncoder;
import variables.Variables;
import variables.VariablesFunctions;



public class BurpExtender implements IBurpExtender, IMessageEditorTabFactory,
        IHttpListener, IProxyListener, IContextMenuFactory {

    public static void addMessageToDebugOutput(String message) {

        if (Variables.getInstance().isDebugModeActive()) {
            String temp = Variables.getInstance().getDebugText();
            while (temp.split("\n").length >= Variables.getInstance()
                    .getNumberOfDebugLines()) {
                temp = Variables.getInstance().getDebugText()
                        .replaceFirst("^[^\r\n]*[\r\n]+", "");
            }

            Variables.getInstance()
                    .setDebugText(
                            temp
                                    + new SimpleDateFormat(
                                            "dd.MM.yyyy HH:mm:ss")
                                            .format(new Date()) + ": "
                                    + message + "\n");
        }
    }

    public static IBurpExtenderCallbacks getCallback() {

        return BurpExtender.mCallbacks;
    }

    public static IExtensionHelpers getIextensionHelper() {

        return BurpExtender.helpers;
    }



    public static String getToolnameFromToolIdentNumber(int toolIdentNumber) {

        String toolname = "unknown";
        switch (toolIdentNumber) {
        case IBurpExtenderCallbacks.TOOL_PROXY:
            toolname = "proxy";
            break;

        case IBurpExtenderCallbacks.TOOL_REPEATER:
            toolname = "repeater";
            break;

        case IBurpExtenderCallbacks.TOOL_INTRUDER:
            toolname = "intruder";
            break;

        case IBurpExtenderCallbacks.TOOL_SCANNER:
            toolname = "scanner";
            break;

        case IBurpExtenderCallbacks.TOOL_SEQUENCER:
            toolname = "sequencer";
            break;

        case IBurpExtenderCallbacks.TOOL_SPIDER:
            toolname = "spider";
            break;

        case IBurpExtenderCallbacks.TOOL_SUITE:
            toolname = "suite";
            break;

        case IBurpExtenderCallbacks.TOOL_TARGET:
            toolname = "target";
            break;
        }

        return toolname;
    }


    public static boolean isRelevantMessage(byte[] message, String regex,
            String toolNameList, String curToolname,
            boolean matchIndicatesNonRelevance) {

        try {
            Pattern p = Pattern.compile(regex, Pattern.DOTALL);
            Matcher m = p.matcher(Library.getStringFromBytearray(message));

            if (toolNameList.toLowerCase().replaceAll("\\s", "")
                    .contains(curToolname.toLowerCase().replaceAll("\\s", ""))
                    && m.find())
                return true ^ matchIndicatesNonRelevance;

            return false || matchIndicatesNonRelevance;
        }catch (PatternSyntaxException e) {
            BurpExtender.addMessageToDebugOutput(Messages
                    .getString("relevant.request.regex.is.invalid") + regex);
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("relevant.request.regex.is.invalid")
                    + regex);
            e.printStackTrace();
        }
        return false;
    }


    public static void saveAllReqsRespsToFile(Object[] messageInfo,
            IExtensionHelpers helpers, boolean includePocSeparators,
            boolean includeRequestHeader, boolean includeRequestBody,
            boolean includeResponseHeader, boolean includeResponseBody,
            boolean includeUrl, boolean includeResponsibleParameters,
            boolean includeMarkedRequestParts,
            boolean includeMarkedResponseParts, String regexForExclusion,
            boolean useIncrementingFilenames, boolean appendToFile,
            ArrayList<Integer> uniqFlags, String asvExcludedParametersString) {

        File curFile = null;
        MyFileChooser fc = null;
        int i = 1000;
        String fileExtension = ".req-resp";
        boolean localUseIncrementingFilenames = useIncrementingFilenames;

        if (uniqFlags != null) { // then is unique mode active
            messageInfo = Library
                    .generateUniqeRequestsWithCorrespondingResponses(
                            (IHttpRequestResponse[]) messageInfo, uniqFlags,
                            asvExcludedParametersString);
        }

        if ((messageInfo.length > 10) && !localUseIncrementingFilenames
                && !appendToFile) {
            Variables
                    .getInstance()
                    .setChangeSaveModeOnMoreThanTenMessagesToIncrementingFilenames(
                            false);
            Variables.getInstance()
                    .setCancelButtonPressedOnSafetyQuestionDialog(false);
            BurpExtender.q = new CustomDialog(
                    Messages.getString("info.more.than.ten.messages.selected.should.the.save.mode.be.changed..to.incrementing.filenames"),
                    new ActionListener() {

                        public void actionPerformed(ActionEvent a) {

                            Variables
                                    .getInstance()
                                    .setChangeSaveModeOnMoreThanTenMessagesToIncrementingFilenames(
                                            true);
                            BurpExtender.q.setVisible(false);
                            BurpExtender.q.dispose();
                        }
                    }, new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {

                            Variables
                                    .getInstance()
                                    .setCancelButtonPressedOnSafetyQuestionDialog(
                                            true);
                            BurpExtender.q.setVisible(false);
                            BurpExtender.q.dispose();
                        }
                    });
            BurpExtender.q.setVisible(true);
            if (Variables.getInstance()
                    .isCancelButtonPressedOnSafetyQuestionDialog())
                return;
            localUseIncrementingFilenames = Variables
                    .getInstance()
                    .isChangeSaveModeOnMoreThanTenMessagesToIncrementingFilenames();
        }

        if (localUseIncrementingFilenames || appendToFile) {
            fc = new MyFileChooser(Variables.getInstance()
                    .getWorkingDirectory(), fileExtension,
                    "Saved Request/Responses");
            fc.init(true);

            if (fc.getFile() != null) {
                if (localUseIncrementingFilenames) {
                    curFile = new File(fc
                            .getFile()
                            .getAbsolutePath()
                            .replaceFirst("\\" + fileExtension + "\\d*$",
                                    "_" + i + fileExtension));
                }
                else {
                    curFile = fc.getFile();
                }
            }
        }


        if ((localUseIncrementingFilenames && (curFile != null) && fc
                .isWriteOkDependingOnExistingFile())
                || !localUseIncrementingFilenames) {
            String extension = "";

            for (Object element:messageInfo) {
                try {
                    if (!appendToFile && !localUseIncrementingFilenames) {
                        fc = new MyFileChooser(Variables.getInstance()
                                .getWorkingDirectory(), fileExtension,
                                "Saved Request/Responses");
                        fc.init(true);
                        curFile = fc.getFile();
                    }
                    else if (localUseIncrementingFilenames) {
                        extension = Library.getFirstRegexgroupMatch("(\\"
                                + fileExtension + "\\d*)$",
                                curFile.getAbsolutePath());
                        curFile = new File(curFile.getAbsolutePath()
                                .replaceFirst("_\\d{4,}\\" + extension + "$",
                                        "_" + i + extension));
                    }

                    if ((curFile != null)
                            && fc.isWriteOkDependingOnExistingFile()) {

                        if (element instanceof IScanIssue) {

                            IScanIssue issue = (IScanIssue) element;
                            IHttpRequestResponse[] messages = issue
                                    .getHttpMessages();
                            boolean isParamSpecificFinding = false;
                            String paramSpecificFindingParameter = null;
                            String paramSpecificFindingRegex = null;

                            if (includeResponsibleParameters) {
                                try {
                                    for (Map.Entry<Integer, String> entry:Variables
                                            .getInstance()
                                            .getScannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues()
                                            .entrySet()) {
                                        if (entry.getKey() == issue
                                                .getIssueType()) {
                                            isParamSpecificFinding = true;
                                            paramSpecificFindingRegex = entry
                                                    .getValue();
                                            paramSpecificFindingParameter = Library
                                                    .getFirstRegexgroupMatch(
                                                            paramSpecificFindingRegex,
                                                            issue.getIssueDetail());
                                            paramSpecificFindingParameter = paramSpecificFindingParameter
                                                    .replaceAll("<b>|</b>", "");
                                            break;
                                        }
                                    }
                                }catch (Exception e) {
                                    new DisplayText(
                                            Messages.getString("errorOccured"),
                                            Messages.getString("something.went.wrong.while.trying.to.get.the.responsible.parameter.from.the.scanner.finding.description.this.might.be.the.case.because.this.finding.description.is.not.yet.included.within.gunziper.either.just.drop.me.an.email.or.try.saving.the.pocs.without.the.responsible.parameter.option"));
                                }
                            }


                            if (messages.length > 1) {
                                for (int j = 0; j < messages.length; j++) {
                                    Library.saveRequestResponseToFile(
                                            new File(curFile.getAbsolutePath()
                                                    + (j + 1)), messages[j],
                                            helpers, includePocSeparators,
                                            includeRequestHeader,
                                            includeRequestBody,
                                            includeResponseBody,
                                            includeResponseHeader, includeUrl,
                                            includeResponsibleParameters,
                                            includeMarkedRequestParts,
                                            isParamSpecificFinding,
                                            paramSpecificFindingParameter,
                                            includeMarkedResponseParts,
                                            regexForExclusion, appendToFile);
                                }
                            }

                            else if (messages.length == 0) {
                                JOptionPane
                                        .showMessageDialog(
                                                null,
                                                Messages.getString("there.are.no.messages.to.save"),
                                                Messages.getString("error.occured"),
                                                JOptionPane.ERROR_MESSAGE);
                            }

                            else {
                                Library.saveRequestResponseToFile(curFile,
                                        messages[0], helpers,
                                        includePocSeparators,
                                        includeRequestHeader,
                                        includeRequestBody,
                                        includeResponseBody,
                                        includeResponseHeader, includeUrl,
                                        includeResponsibleParameters,
                                        includeMarkedRequestParts,
                                        isParamSpecificFinding,
                                        paramSpecificFindingParameter,
                                        includeMarkedResponseParts,
                                        regexForExclusion, appendToFile);
                            }

                        }
                        else {
                            Library.saveRequestResponseToFile(curFile, element,
                                    helpers, includePocSeparators,
                                    includeRequestHeader, includeRequestBody,
                                    includeResponseBody, includeResponseHeader,
                                    includeUrl, includeResponsibleParameters,
                                    includeMarkedRequestParts, false, null,
                                    includeMarkedResponseParts,
                                    regexForExclusion, appendToFile);
                        }


                        Variables.getInstance().setWorkingDirectory(
                                curFile.getParentFile().getAbsolutePath());
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                    new DisplayText(Messages.getString("errorOccured"),
                            e.toString());
                }
                i++;
            }


        }
    }


    public static void updateReplaceStringIfAppropriate(boolean isRequest,
            IHttpRequestResponse messageInfo, String toolName) {

        byte[] tempMessage = null;

        for (MatchReplaceSettings current:Variables.getInstance()
                .getMatchReplaceSettingsArray()) {

            if (current.isMatchReplaceActive()
                    && (current.getMatchReplaceAction() == 2)
                    && ((current.isExtractFromRequestInsteadFromResponse() && isRequest) || (!current
                            .isExtractFromRequestInsteadFromResponse() && !isRequest))) {

                if (BurpExtender
                        .isRelevantMessage(
                                current.isRegexForResponseExtractionIdentifiesRequest() ? messageInfo
                                        .getRequest():messageInfo.getResponse(),
                                current.getRelevantRequestRegexForResponseExtraction(),
                                current.getResponseToolnames(),
                                toolName,
                                current.isMatchIndicatesNonrelevanceForResponseextraction())) {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("the.relevant.request.regex.for.the.response.replace.string.extraction.matches.on.the.current.request"));

                    tempMessage = current
                            .isExtractFromRequestInsteadFromResponse() ? messageInfo
                            .getRequest():messageInfo.getResponse();

                    try {
                        if (current.isDoResponseExtractionAfterUnpacking()) {
                            tempMessage = current
                                    .isExtractFromRequestInsteadFromResponse() ? UnpackingLibrary
                                    .tryUnpackOrPackRequest(
                                                                    messageInfo.getRequest(), toolName,
                                                                    true)
                                    :UnpackingLibrary
                                            .tryUnpackResponse(
                                                                            messageInfo.getResponse(),
                                                                            toolName);

                            if (tempMessage == null) {
                                tempMessage = current
                                        .isExtractFromRequestInsteadFromResponse() ? messageInfo
                                        .getRequest():messageInfo.getResponse();
                            }
                        }
                        MatchReplaceLibrary
                                .makeChangesToReplaceOrInsertionString(
                                        tempMessage, current);
                    }catch (NumberFormatException e) {
                        new DisplayText(Messages.getString("errorOccured"),
                                Messages.getString("invalidMatchingNumber"));
                        e.printStackTrace();
                    }
                }
                else {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("new.replace.string.gets.not.extracted.from.current.response.due.to.not.matching.relevant.extraction.request.regex.or.wrong.invoking.tool"));
                }
            }

        }
    }


    private static CustomDialog           q;

    private static IExtensionHelpers      helpers;

    private static IBurpExtenderCallbacks mCallbacks;

    /*
     * (non-Javadoc)
     * 
     * @see
     * burp.IContextMenuFactory#createMenuItems(burp.IContextMenuInvocation)
     */
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {

        final IContextMenuInvocation invoc = invocation;
        final IExtensionHelpers helpers = BurpExtender.helpers;
        final IBurpExtenderCallbacks callbacks = BurpExtender.mCallbacks;
        final String savePocsItemString = "Save PoC(s)";
        final String sendToRegexTestString = "Send to Regex Test";
        final String intruderComparerString = "Intruder Compare";
        final String sendToComparerString = "Send to gComparer";
        final String sendRequestsToRepeaterString = "Send request(s) to Repeater";
        final String sendRequestsToIntruderString = "Send request(s) to Intruder";
        final String unpackPackMessageString = "Unpack/Pack Message...";

        List<JMenuItem> menuItemList = new LinkedList<JMenuItem>();

        JMenuItem savePocMenuItem = new JMenuItem(savePocsItemString);
        JMenuItem sendToRegexTestMenuItem = new JMenuItem(sendToRegexTestString);
        JMenuItem intruderComparerMenuItem = new JMenuItem(
                intruderComparerString);
        JMenuItem sendToComparerMenuItem = new JMenuItem(sendToComparerString);
        JMenuItem sendRequestsToRepeaterMenuItem = new JMenuItem(
                sendRequestsToRepeaterString);
        JMenuItem sendRequestsToIntruderMenuItem = new JMenuItem(
                sendRequestsToIntruderString);
        JMenuItem unpackPackMessageMenuItem = new JMenuItem(
                unpackPackMessageString);


        unpackPackMessageMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand().equals(unpackPackMessageString)) {
                    if (!Variables.getInstance().isRequestUnpackingActive()
                            && !Variables.getInstance()
                                    .isResponseUnpackingActive()) {
                        new DisplayText(
                                Messages.getString("info"),
                                Messages.getString("neither.request.nor.response.unpacking.has.been.configured.within.set.variables"));
                    }
                    else {
                        new UnpackSelectionWindow(callbacks, helpers, invoc
                                .getSelectedMessages());
                    }
                }
            }
        });



        sendRequestsToRepeaterMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand()
                        .equals(sendRequestsToRepeaterString)) {
                    IHttpService reqInfo;
                    boolean proceed = true;
                    if (invoc.getSelectedMessages().length > 10) {
                        Variables.getInstance()
                                .setProceedOnMoreThanTenMessages(false);
                        BurpExtender.q = new CustomDialog(
                                Messages.getString("info.more.than.ten.messages.selected.proceed"),
                                new ActionListener() {

                                    public void actionPerformed(ActionEvent a) {

                                        Variables
                                                .getInstance()
                                                .setProceedOnMoreThanTenMessages(
                                                        true);
                                        BurpExtender.q.setVisible(false);
                                        BurpExtender.q.dispose();
                                    }
                                });
                        BurpExtender.q.setVisible(true);
                        proceed = Variables.getInstance()
                                .isProceedOnMoreThanTenMessages();
                    }
                    if (proceed) {
                        for (IHttpRequestResponse element:invoc
                                .getSelectedMessages()) {
                            reqInfo = element.getHttpService();
                            URL currentUrl = BurpExtender.helpers
                                    .analyzeRequest(element).getUrl();

                            try {
                                String tabName = "newTab";
                                if (currentUrl != null) {
                                    String fileName = currentUrl.getFile();
                                    if ((fileName != null)
                                            && !fileName.equals("")
                                            && !fileName.equals("/")) {
                                        tabName = fileName;
                                    }
                                    else {
                                        fileName = currentUrl.getPath();
                                        if ((fileName != null)
                                                && !fileName.equals("")
                                                && !fileName.equals("/")) {
                                            tabName = fileName;
                                        }
                                        else {
                                            tabName = currentUrl.toString();
                                        }
                                    }

                                }

                                callbacks.sendToRepeater(reqInfo.getHost(),
                                        reqInfo.getPort(), reqInfo
                                                .getProtocol()
                                                .equalsIgnoreCase("https"),
                                        element.getRequest(), tabName);
                            }catch (Exception e) {
                                e.printStackTrace();
                                new DisplayText(Messages
                                        .getString("errorOccured"), e
                                        .toString());
                            }
                        }
                    }
                }
            }
        });

        sendRequestsToIntruderMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand()
                        .equals(sendRequestsToIntruderString)) {
                    IHttpService reqInfo;

                    boolean proceed = true;
                    if (invoc.getSelectedMessages().length > 10) {
                        Variables.getInstance()
                                .setProceedOnMoreThanTenMessages(false);
                        BurpExtender.q = new CustomDialog(
                                Messages.getString("info.more.than.ten.messages.selected.proceed"),
                                new ActionListener() {

                                    public void actionPerformed(ActionEvent a) {

                                        Variables
                                                .getInstance()
                                                .setProceedOnMoreThanTenMessages(
                                                        true);
                                        BurpExtender.q.setVisible(false);
                                        BurpExtender.q.dispose();
                                    }
                                });
                        BurpExtender.q.setVisible(true);
                        proceed = Variables.getInstance()
                                .isProceedOnMoreThanTenMessages();
                    }
                    if (proceed) {
                        for (IHttpRequestResponse element:invoc
                                .getSelectedMessages()) {
                            reqInfo = element.getHttpService();
                            try {
                                callbacks.sendToIntruder(reqInfo.getHost(),
                                        reqInfo.getPort(), reqInfo
                                                .getProtocol()
                                                .equalsIgnoreCase("https"),
                                        element.getRequest());
                            }catch (Exception e) {
                                e.printStackTrace();
                                new DisplayText(Messages
                                        .getString("errorOccured"), e
                                        .toString());
                            }
                        }
                    }
                }
            }
        });


        intruderComparerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand().equals(intruderComparerString)) {
                    boolean itemsAreScanIssues = invoc.getSelectedIssues() != null;
                    Object[] reqResponses = itemsAreScanIssues ? invoc
                            .getSelectedIssues():invoc.getSelectedMessages();
                    if (reqResponses == null) {
                        new DisplayText(
                                "Error",
                                Messages.getString("no.compatible.request.response.parts.have.been.selected"));
                    }
                    else {
                        new IntruderSelectionWindow(reqResponses);
                    }

                }
            }
        });


        savePocMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand().equals(savePocsItemString)) {
                    boolean itemsAreScanIssues = invoc.getSelectedIssues() != null;
                    Object[] reqResponses = itemsAreScanIssues ? invoc
                            .getSelectedIssues():invoc.getSelectedMessages();
                    if (reqResponses == null) {
                        new DisplayText(
                                "Error",
                                Messages.getString("no.compatible.request.response.parts.have.been.selected"));
                    }
                    else {
                        new SaveAllReqsRespsSelectionWindow(reqResponses,
                                helpers, itemsAreScanIssues);
                    }
                }
            }
        });

        sendToRegexTestMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand().equals(sendToRegexTestString)) {
                    if ((invoc.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST)
                            || (invoc.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_REQUEST)) {
                        DisplayText.getInstanceOfRegexTestWindow()
                                .setRegexTestWindowContent(
                                        Library.getStringFromBytearray(invoc
                                                .getSelectedMessages()[0]
                                                .getRequest()));
                        DisplayText.getInstanceOfRegexTestWindow()
                                .showRegexTestWindow();
                    }
                    else if ((invoc.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_RESPONSE)
                            || (invoc.getInvocationContext() == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE)) {
                        DisplayText.getInstanceOfRegexTestWindow()
                                .setRegexTestWindowContent(
                                        Library.getStringFromBytearray(invoc
                                                .getSelectedMessages()[0]
                                                .getResponse()));
                        DisplayText.getInstanceOfRegexTestWindow()
                                .showRegexTestWindow();
                    }
                    else if (invoc.getInvocationContext() == IContextMenuInvocation.CONTEXT_SCANNER_RESULTS) {
                        DisplayText
                                .getInstanceOfRegexTestWindow()
                                .setRegexTestWindowContent(
                                        Library.getStringFromBytearray(invoc
                                                .getSelectedIssues()[0]
                                                .getHttpMessages()[0]
                                                .getRequest())
                                                + "\n\n\n"
                                                + Library
                                                        .getStringFromBytearray(invoc
                                                                .getSelectedIssues()[0]
                                                                .getHttpMessages()[0]
                                                                .getResponse()));
                        DisplayText.getInstanceOfRegexTestWindow()
                                .showRegexTestWindow();
                    }
                    else {
                        DisplayText
                                .getInstanceOfRegexTestWindow()
                                .setRegexTestWindowContent(
                                        Library.getStringFromBytearray(invoc
                                                .getSelectedMessages()[0]
                                                .getRequest())
                                                + "\n\n\n"
                                                + Library
                                                        .getStringFromBytearray(invoc
                                                                .getSelectedMessages()[0]
                                                                .getResponse()));
                        DisplayText.getInstanceOfRegexTestWindow()
                                .showRegexTestWindow();
                    }
                }
            }
        });

        sendToComparerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (arg0.getActionCommand().equals(sendToComparerString)) {
                    new GComparer(invoc.getSelectedMessages());
                }
            }
        });

        menuItemList.add(unpackPackMessageMenuItem);
        menuItemList.add(sendRequestsToIntruderMenuItem);
        menuItemList.add(sendRequestsToRepeaterMenuItem);
        menuItemList.add(sendToComparerMenuItem);
        menuItemList.add(intruderComparerMenuItem);
        menuItemList.add(sendToRegexTestMenuItem);
        menuItemList.add(savePocMenuItem);

        return (menuItemList);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * burp.IMessageEditorTabFactory#createNewInstance(burp.IMessageEditorController
     * , boolean)
     */
    @Override
    public IMessageEditorTab createNewInstance(
            IMessageEditorController controller, boolean editable) {

        return new GunziperTab(BurpExtender.mCallbacks.createTextEditor());
    }

    public void executeCommandLineArguments(String[] args) {

        for (int i = 0; i < (args.length - 1); i += 2) {
            if (args[i].equalsIgnoreCase("restore")) {
                System.out.println(Messages
                        .getString("trying.to.restore.saved.burp.file")
                        + ": "
                        + args[i + 1]);
                File tempFile = new File(args[i + 1]);
                BurpExtender.mCallbacks.restoreState(tempFile);
                new DisplayText("Info",
                        Messages.getString("burp.restore.finished"));
                System.out.println(Messages.getString("burp.restore.finished"));
                Variables.getInstance().setWorkingDirectory(
                        tempFile.getParentFile().getAbsolutePath());
            }


            else if (args[i].equalsIgnoreCase("basedir")) {

                System.out.println(Messages.getString("setting.base.dir.to")
                        + ": " + args[i + 1]);
                Variables.getInstance().setWorkingDirectory(args[i + 1]);
                System.out.println(Messages.getString("done"));

            }
            else if (args[i].equalsIgnoreCase("load")) {
                System.out.println(Messages
                        .getString("trying.to.load.settings.from")
                        + ": "
                        + args[1]);
                if (VariablesFunctions.loadValuesFromFile(args[i + 1])) {
                    TextFieldsWindow2.getInstance().resetValues();
                    System.out.println(Messages
                            .getString("seems.like.it.worked"));
                }

            }
            else if (args[i].equalsIgnoreCase("setvalues")) {
                Map<String, String> map = BurpExtender.mCallbacks.saveConfig();
                if (args[i + 1].equalsIgnoreCase("merge")) {
                    map.put("suite.doAutoSave", "true");
                    map.put("proxy.interceptrequests", "false");
                    map.put("intruder.payloadprocessor.urlencodechars",
                            " ./\\=<>?+&;:#");
                    map.put("suite.autoSaveInterval", "15");
                    map.put("suite.autoreloadextensions", "false");
                    map.put("suite.autoSaveFolder", "/mnt/data/burpStateBackup");
                    map.put("suite.tempDir", "/mnt/data/burpTempFolder");
                    map.put("intruder.numattackthreads", "1");
                    map.put("intruder.newtabbehavior", "2");
                    map.put("intruder.makebaselinerequest", "true");
                    map.put("intruder.autoplacementappend", "true");
                    map.put("intruder.payloadsdir", Variables.getInstance()
                            .getWorkingDirectory());
                    map.put("suite.uilookandfeel", "Metal");
                    map.put("suite.uifontsize", "11");
                    map.put("suite.messageFont", "Courier New");
                    map.put("suite.messageFontSize", "13");

                    BurpExtender.mCallbacks.loadConfig(map);
                }
            }
            else {
                System.out.println(Messages
                        .getString("no.recognized.action.could.be.identified"));
            }

        }
    }

    public void processHttpMessage(int toolIdentNumber,
            boolean messageIsRequest, IHttpRequestResponse messageInfo) {

        // boolean updateContentLength = false;
        String toolName = BurpExtender
                .getToolnameFromToolIdentNumber(toolIdentNumber);

        if (Variables.getInstance().isDebugModeActive()) {
            Variables.getInstance().setDebugText(
                    Variables.getInstance().getDebugText()
                            + Variables.getInstance()
                                    .getToprequestdelimeterstring());
        }

        if (messageIsRequest) {
            byte[] tempRequest = messageInfo.getRequest();
            try {

                BurpExtender.updateReplaceStringIfAppropriate(true,
                        messageInfo, toolName);

                // to see changes within the proxy view
                if (!toolName.equals("proxy")) {
                    tempRequest = MatchReplaceLibrary
                            .doReplacementAndInsertionWithAllMatchReplaceSettings(
                                    tempRequest, toolName, true);
                    // updateContentLength = true;

                    if (tempRequest != null) {
                        tempRequest = Library.prepareMessageHeader(tempRequest);
                        messageInfo.setRequest(tempRequest);
                    }
                }



            }catch (Exception e) {
                e.printStackTrace();
                new DisplayText(Messages.getString("errorOccured"),
                        e.toString());
            }

        }

        else {

            try {

                BurpExtender.updateReplaceStringIfAppropriate(false,
                        messageInfo, toolName);

            }catch (Exception e) {
                e.printStackTrace();
                new DisplayText(Messages.getString("errorOccured"),
                        e.toString());
            }
        }

        if (Variables.getInstance().isDebugModeActive()) {
            Variables.getInstance().setDebugText(
                    Variables.getInstance().getDebugText()
                            + Variables.getInstance()
                                    .getBottomrequestdelimeterstring());
        }
    }


    public void processProxyMessage(boolean messageIsRequest,
            IInterceptedProxyMessage message) {

        if (Variables.getInstance().isDebugModeActive()) {
            Variables.getInstance().setDebugText(
                    Variables.getInstance().getDebugText()
                            + Variables.getInstance()
                                    .getToprequestdelimeterstring());
        }
        if (messageIsRequest) {
            if (Variables.getInstance().isDropRequestActive()) {
                try {
                    Matcher m = Pattern.compile(
                            Variables.getInstance().getDropRequestRegex(),
                            Pattern.DOTALL).matcher(
                            Library.getStringFromBytearray(message
                                    .getMessageInfo().getRequest()));
                    if (m.find()) {
                        BurpExtender.addMessageToDebugOutput(Messages
                                .getString("current.request.has.been.dropped"));
                        message.setInterceptAction(3);
                        return;
                    }
                    else {
                        BurpExtender
                                .addMessageToDebugOutput(Messages
                                        .getString("current.request.has.not.been.dropped.due.to.not.matching.regex"));
                    }
                }catch (PatternSyntaxException e) {
                    BurpExtender.addMessageToDebugOutput(Messages
                            .getString("drop.request.regex.is.invalid"));
                    new DisplayText(Messages.getString("errorOccured"),
                            Messages.getString("drop.request.regex.is.invalid"));
                    e.printStackTrace();
                }
            }


            // to see changes within the proxy view, keep it also here
            byte[] tempRequest;
            try {
                tempRequest = MatchReplaceLibrary
                        .doReplacementAndInsertionWithAllMatchReplaceSettings(
                                message.getMessageInfo().getRequest(), "proxy",
                                true);


                if (tempRequest != null) {
                    tempRequest = Library.prepareMessageHeader(tempRequest);
                    message.getMessageInfo().setRequest(tempRequest);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }


        }


        if (Variables.getInstance().isDebugModeActive()) {
            Variables.getInstance().setDebugText(
                    Variables.getInstance().getDebugText()
                            + Variables.getInstance()
                                    .getBottomrequestdelimeterstring());
        }


    }

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        BurpExtender.mCallbacks = callbacks;

        BurpExtender.helpers = callbacks.getHelpers();

        VariablesFunctions.init();

        callbacks.setExtensionName("gunziper "
                + Variables.getInstance().getVersionNumber());

        callbacks.registerHttpListener(this);

        callbacks.registerProxyListener(this);

        callbacks.registerMessageEditorTabFactory(this);

        callbacks
                .registerSessionHandlingAction(new DelaySessionHandlingAction());



        callbacks.registerContextMenuFactory(this);

        VariablesFunctions.registerDeEncrypter(new UrlDeEncoder());
        VariablesFunctions.registerDeEncrypter(new Base64DeEncoder());
        VariablesFunctions.registerDeEncrypter(new GzipDeUnpacker());
        VariablesFunctions.registerDeEncrypter(new AsciiHexDeEncrypter());
        VariablesFunctions.registerDeEncrypter(new IntHexDeEncrypter());
        VariablesFunctions.registerDeEncrypter(new DeSerializer());
        VariablesFunctions.registerDeEncrypter(new DeflateDeUnpacker());
        VariablesFunctions.registerDeEncrypter(new HTMLDeEncoder());
        VariablesFunctions
        .registerDeEncrypter(new DeflateGzipcompatibleDeUnpacker());
        VariablesFunctions.registerDeEncrypter(new StringStripper());


        callbacks.addSuiteTab(TextFieldsWindow2.getInstance());

        this.executeCommandLineArguments(callbacks.getCommandLineArguments());
        for (int i = 0; i < Variables.getInstance()
                .getMatchReplaceSettingsArray().size(); i++) {
            callbacks
                    .registerSessionHandlingAction(new MatchReplaceSessionHandlingAction(
                            Variables.getInstance()
                                    .getMatchReplaceSettingsArray().get(i),
                            "MatchReplaceSessionhandlingAction" + (i + 1)));
        }

    }
}
