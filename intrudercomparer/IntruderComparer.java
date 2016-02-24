package intrudercomparer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;

import misc.DisplayText;
import misc.Library;
import misc.Messages;
import unpacking.UnpackingLibrary;
import variables.Variables;
import burp.BurpExtender;
import burp.IHttpRequestResponse;
import difflib.diffutils.DiffRow;
import difflib.diffutils.DiffRowGenerator;
import difflib.google.diff_match_patch;
import difflib.google.diff_match_patch.Diff;
import exceptions.NothingDoneException;


/**
 * This class uses the provided messages and options, prepares the messages
 * according to the options, checks for differences and opens a new
 * ComparerWindow on a diff.
 */
class IntruderComparer implements Runnable {

    private IHttpRequestResponse[] messageInfo                         = null;
    private boolean                includeHTTPheader                   = false;
    private boolean                includeDifflikeComparison           = true;
    private boolean                doDifflikeComparisonToFirstResponse = false;
    private boolean                interactiveMode                     = false;
    private boolean                abortComparison                     = false;
    private String                 regexForExclusion                   = "";
    private DisplayText            intruderComparerWindow              = null;
    private Thread                 calculateDifferencesThread          = null;
    private boolean                useGoogleDiffForWholeComparison     = false;
    private boolean                doResponseUnpacking                 = false;

    public IntruderComparer(IHttpRequestResponse[] messageInfo,
            boolean includeHTTPheader, boolean doResponseUnpacking,
            boolean includeDifflikeComparison, String regexForExclusion,
            boolean interactiveMode, boolean useGoogleDiffForWholeComparison,
            boolean doDifflikeComparisonToFirstResponse) {

        this.intruderComparerWindow = new DisplayText("Intruder Differences",
                "working...", true);
        this.includeHTTPheader = includeHTTPheader;
        this.doResponseUnpacking = doResponseUnpacking;
        this.includeDifflikeComparison = includeDifflikeComparison;
        this.messageInfo = messageInfo;
        this.regexForExclusion = regexForExclusion;
        this.interactiveMode = interactiveMode;
        this.doDifflikeComparisonToFirstResponse = doDifflikeComparisonToFirstResponse;
        this.useGoogleDiffForWholeComparison = useGoogleDiffForWholeComparison;
        this.calculateDifferencesThread = new Thread(this);
        this.calculateDifferencesThread.start();
    }

    public boolean isVisible() {

        return this.intruderComparerWindow.isVisible();
    }

    // @SuppressWarnings("unchecked")
    public void run() {

        String output = "";
        int firstLength = 0;
        boolean isfirstLengthSet = false;
        int previousLength = 0;
        int actLength = 0;
        // String[] payloadListArray = Variables
        // .getPayloadListForIntruderCompare().split("\n");
        String[] payloadListArray = Variables.getInstance()
                .getPayloadListForIntruderCompare();

        String spaces = "\t\t";
        output += "#" + spaces + "payload" + spaces + "length" + spaces
                + "act - prev" + spaces + "act - first";
        if (this.includeDifflikeComparison) {
            if (this.doDifflikeComparisonToFirstResponse) {
                output += spaces
                        + (this.useGoogleDiffForWholeComparison ? "diffCharsFirst"
                                :"diffLinesFirst");
            }
            output += spaces
                    + (this.useGoogleDiffForWholeComparison ? "diffCharsPrev"
                            :"diffLinesPrev");
        }
        output += "\n";

        int substringLength = 10;
        int differentLinesToPrevious = 0;
        int differentLinesToFirst = 0;
        int differentCharsToPrevious = 0;
        int differentCharsToFirst = 0;
        byte[] actResponse = null;
        // byte[] previousResponse = null;
        String actResponseString = null;
        String previousResponseString = null;
        String actualResponse = null;
        String firstResponse = null;
        LinkedList<String> previousResponseList = null;
        LinkedList<String> actualResponseList = null;
        LinkedList<String> firstResponseList = null;
        DiffRowGenerator.Builder builder = null;
        DiffRowGenerator generator = null;
        List<DiffRow> previousRows = null;
        List<DiffRow> firstRows = null;
        diff_match_patch diffGenerator = null;
        ComparerWindow cw = null;
        List<Diff> previousDiffs = null;
        List<Diff> firstDiffs = null;
        ActionListener cancelWholeDifferenceCalculationActionlistener = null;


        if (this.includeDifflikeComparison) {
            if (this.useGoogleDiffForWholeComparison || this.interactiveMode) {
                previousResponseString = "";
                actualResponse = "";
                firstResponse = "";
                diffGenerator = new diff_match_patch();
            }
            if (!this.useGoogleDiffForWholeComparison) {
                previousResponseList = new LinkedList<String>();
                actualResponseList = new LinkedList<String>();
                firstResponseList = new LinkedList<String>();
                builder = new DiffRowGenerator.Builder();
                generator = builder.build();
            }

            if (this.interactiveMode) {
                cancelWholeDifferenceCalculationActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                        IntruderComparer.this.abortComparison = true;
                        ((JFrame) ((JButton) arg0.getSource())
                                .getTopLevelAncestor()).dispose();
                    }
                };
            }
        }

        int numberOfRequestResponses = IntruderComparer.this.messageInfo.length;
        boolean regexAlertAlreadyDisplayed = false;
        // for (IHttpRequestResponse
        // iHttpRequestResponse:IntruderComparer.this.messageInfo) {
        this.intruderComparerWindow
                .setMaintextAreaText("working...\nResponses Processed: 0/"
                        + numberOfRequestResponses);
        for (int counter = 0; ((counter < this.messageInfo.length)
                && (!this.calculateDifferencesThread.isInterrupted())
                && (!this.intruderComparerWindow
                        .intruderComparerHasBeenInterrupted()) && !this.abortComparison); counter++) {
            try {
                if (this.includeDifflikeComparison) {
                    if (this.useGoogleDiffForWholeComparison) {
                        differentCharsToPrevious = 0;
                        differentCharsToFirst = 0;
                    }
                    else {
                        differentLinesToPrevious = 0;
                        differentLinesToFirst = 0;
                    }
                }

                output += (counter + 1) + ":" + spaces;
                if (payloadListArray != null) {
                    output += Library.getSubstring(payloadListArray[Library
                            .getRealModuloResult((counter + 1),
                                    payloadListArray.length)], substringLength);
                }
                else {
                    output += "Payload";
                }
                output += spaces;
                if (this.messageInfo[counter].getResponse() != null) {

                    actResponse = null;

                    if (this.doResponseUnpacking) {
                        actResponse = UnpackingLibrary.tryUnpackResponse(
                                this.messageInfo[counter].getResponse(),
                                Variables.getInstance().getResponseToolName());
                    }

                    if (actResponse == null) {
                        actResponse = this.messageInfo[counter].getResponse();
                    }

                    if (!IntruderComparer.this.includeHTTPheader) {
                        actResponse = Library.getMessageBody(actResponse);
                    }

                    actResponseString = Library
                            .getStringFromBytearray(actResponse);
                    if (!IntruderComparer.this.regexForExclusion.equals("")) {
                        try {
                            Matcher m = Pattern.compile(
                                    IntruderComparer.this.regexForExclusion,
                                    Pattern.DOTALL).matcher(actResponseString);
                            if ((m.groupCount() == 0)
                                    && !regexAlertAlreadyDisplayed) {
                                BurpExtender
                                        .addMessageToDebugOutput(Messages
                                                .getString("no.regex.group.could.be.found.within.the.regex"));
                                new DisplayText(
                                        Messages.getString("errorOccured"),
                                        Messages.getString("no.regex.group.could.be.found.within.the.regex"));
                                regexAlertAlreadyDisplayed = true;
                            }
                            else {
                                actResponseString = Library
                                        .getReplacementByRegexGroups(
                                                actResponseString, m, "",
                                                false, false, false, "", true);
                            }
                        }catch (NothingDoneException e) {
                            // DO NOTHING
                        }catch (PatternSyntaxException e) {
                            if (!regexAlertAlreadyDisplayed) {
                                BurpExtender
                                        .addMessageToDebugOutput(Messages
                                                .getString("the.regex.for.comparer.exclusion.is.invalid"));
                                new DisplayText(
                                        Messages.getString("errorOccured"),
                                        Messages.getString("the.regex.for.comparer.exclusion.is.invalid"));
                                e.printStackTrace();
                                regexAlertAlreadyDisplayed = true;
                            }
                        }catch (Exception e) {
                            BurpExtender
                                    .addMessageToDebugOutput(Messages
                                            .getString("something.went.wrong.with.the.supplied.regex.for.intruder.comparer.for.exclusion.on.request.number")
                                            + (counter + 1));
                            e.printStackTrace();
                        }
                    }

                    actLength = actResponseString.length();
                    if (this.includeDifflikeComparison) {
                        if (this.useGoogleDiffForWholeComparison
                                || this.interactiveMode) {
                            previousResponseString = actualResponse;
                            actualResponse = actResponseString;
                        }
                        if (!this.useGoogleDiffForWholeComparison) {
                            previousResponseList = new LinkedList<String>(
                                    actualResponseList);
                            actualResponseList = new LinkedList<String>();
                            for (String string:actResponseString
                                    .split("\r{0,1}\n")) {
                                actualResponseList.add(string);
                            }
                        }
                    }
                    if (!isfirstLengthSet) {
                        if (this.useGoogleDiffForWholeComparison) {
                            firstResponse = actualResponse;
                        }
                        else {
                            firstResponseList = actualResponseList;

                        }

                        firstLength = actLength;
                        previousLength = actLength;
                        isfirstLengthSet = true;
                    }
                    else if (this.includeDifflikeComparison) {
                        if (this.useGoogleDiffForWholeComparison
                                || this.interactiveMode) {

                            if (this.doDifflikeComparisonToFirstResponse
                                    && this.useGoogleDiffForWholeComparison) {
                                firstDiffs = diffGenerator.diff_main(
                                        firstResponse, actualResponse, true);
                                for (Diff diff:firstDiffs) {
                                    if (diff.operation != difflib.google.diff_match_patch.Operation.EQUAL) {
                                        differentCharsToFirst += diff.text
                                                .length();
                                    }
                                }
                            }

                            previousDiffs = diffGenerator.diff_main(
                                    previousResponseString, actualResponse,
                                    true);

                            if (this.useGoogleDiffForWholeComparison) {
                                for (Diff diff:previousDiffs) {
                                    if (diff.operation != difflib.google.diff_match_patch.Operation.EQUAL) {
                                        differentCharsToPrevious += diff.text
                                                .length();
                                    }
                                }
                            }

                        }
                        if (!this.useGoogleDiffForWholeComparison) {
                            builder.showInlineDiffs(false);
                            builder.columnWidth(Integer.MAX_VALUE);
                            generator = builder.build();

                            if (this.doDifflikeComparisonToFirstResponse) {
                                firstRows = generator.generateDiffRows(
                                        firstResponseList, actualResponseList);
                                for (DiffRow diffRow:firstRows) {
                                    if (diffRow.getTag() != DiffRow.Tag.EQUAL) {
                                        differentLinesToFirst++;
                                    }
                                }
                            }

                            previousRows = generator.generateDiffRows(
                                    previousResponseList, actualResponseList);
                            for (DiffRow diffRow:previousRows) {
                                if (diffRow.getTag() != DiffRow.Tag.EQUAL) {
                                    differentLinesToPrevious++;
                                }
                            }
                        }

                        if (this.interactiveMode
                                && ((differentCharsToPrevious > 0) || (differentLinesToPrevious > 0))) {
                            cw = new ComparerWindow(
                                    counter,
                                    previousDiffs,
                                    cancelWholeDifferenceCalculationActionlistener,
                                    "Intruder Comparer");
                            while (cw.isVisible()) {
                                try {
                                    Thread.sleep(347);
                                }catch (InterruptedException e) {
                                    // Just ignore
                                }
                            }
                        }
                    }
                    output += actLength;
                    output += spaces;
                    output += actLength - previousLength;
                    output += spaces;
                    output += actLength - firstLength;
                    if (this.includeDifflikeComparison) {
                        if (this.doDifflikeComparisonToFirstResponse) {
                            output += spaces;
                            output += this.useGoogleDiffForWholeComparison ? differentCharsToFirst
                                    :differentLinesToFirst;
                        }
                        output += spaces;
                        output += this.useGoogleDiffForWholeComparison ? differentCharsToPrevious
                                :differentLinesToPrevious;
                    }
                    previousLength = actLength;
                }

                else {
                    output += Messages.getString("there.was.no.response");
                }
                output += "\n";
            }catch (Exception e) {
                e.printStackTrace();
            }
            this.intruderComparerWindow
                    .setMaintextAreaText("working...\nResponses Processed: "
                            + (counter + 1) + "/" + numberOfRequestResponses);
        }
        this.intruderComparerWindow.setMaintextAreaText(output);
    }
}
