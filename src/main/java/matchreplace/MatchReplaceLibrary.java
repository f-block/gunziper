/**
 * Erstellt am: Jul 4, 2013 3:54:39 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package matchreplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import misc.DisplayText;
import misc.Library;
import misc.Messages;
import unpacking.UnpackingLibrary;
import variables.Variables;
import burp.BurpExtender;
import exceptions.NothingDoneException;


public class MatchReplaceLibrary {

    public static byte[] doReplacementAndInsertionWithAllMatchReplaceSettings(
            byte[] tempRequest, String toolName,
            boolean doOnlyIfGlobalReplacementIsActivated) throws Exception {

        boolean atLeastOneMatchReplaceHasBeenDone = false;
        byte[] actualTemp = null;
        byte[] loopTemp = tempRequest;

        for (MatchReplaceSettings current:Variables.getInstance()
                .getMatchReplaceSettingsArray()) {

            actualTemp = MatchReplaceLibrary
                    .doReplacementAndInsertionWithSingleMatchReplaceSetting(
                            loopTemp, current, toolName,
                            doOnlyIfGlobalReplacementIsActivated);
            if (actualTemp != null) {
                loopTemp = actualTemp;
                atLeastOneMatchReplaceHasBeenDone = true;
            }
        }

        return atLeastOneMatchReplaceHasBeenDone ? loopTemp:null;
    }


    public static byte[] doReplacementAndInsertionWithSingleMatchReplaceSetting(
            byte[] tempRequest,
            MatchReplaceSettings currentMatchReplaceSetting, String toolName,
            boolean doOnlyIfGlobalReplacementIsActivated) throws Exception {

        boolean changesMade = false;

        if (currentMatchReplaceSetting.isMatchReplaceActive()) {
            if (!doOnlyIfGlobalReplacementIsActivated
                    || (doOnlyIfGlobalReplacementIsActivated && currentMatchReplaceSetting
                            .isActivateThisRuleGlobally())) {
                if ((!currentMatchReplaceSetting.isActivateThisRuleGlobally() && !doOnlyIfGlobalReplacementIsActivated)
                        || BurpExtender
                        .isRelevantMessage(
                                tempRequest,
                                currentMatchReplaceSetting
                                .getRelevantReplaceRequestRegex(),
                                currentMatchReplaceSetting
                                .getToolnames(),
                                toolName,
                                currentMatchReplaceSetting
                                .isMatchIndicatesNonrelevanceForMatchreplaceRequest())) {
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("the.relevant.request.regex.matches.for.replacement.on.the.current.request"));

                                    if (currentMatchReplaceSetting
                                            .isDoRequestReplacementAfterUnpacking()) {
                                        byte[] temp = tempRequest.clone();
                                        temp = UnpackingLibrary.tryUnpackOrPackRequest(temp,
                                                toolName, true);
                                        if (temp != null) {
                                            tempRequest = temp;
                                        }
                                    }

                                    tempRequest = MatchReplaceLibrary
                                            .getMatchReplaceResult(
                                                    tempRequest,
                                                    currentMatchReplaceSetting
                                                    .isReplaceOnlyFirstMatch(),
                                                    currentMatchReplaceSetting
                                                    .isProcessingOfReplacestringActive(),
                                                    currentMatchReplaceSetting
                                                    .isOnlyDoProcessingOfMarkedPart(),
                                                    currentMatchReplaceSetting
                                                    .getProcessingOfReplacestringCsvstring(),
                                                    currentMatchReplaceSetting
                                                    .getRegexMarkingRelevantPartForReplacement(),
                                                    currentMatchReplaceSetting
                                                    .getReplaceString());

                                    if (tempRequest != null) {
                                        changesMade = true;
                                        if (currentMatchReplaceSetting
                                                .isDoRequestReplacementAfterUnpacking()) {

                                            byte[] temp = tempRequest.clone();
                                            temp = UnpackingLibrary.tryUnpackOrPackRequest(
                                                    temp, toolName, false);
                                            if (temp != null) {
                                                tempRequest = temp;
                                            }
                                        }
                                    }

                                    if (changesMade) {
                                        if (currentMatchReplaceSetting.getMatchReplaceAction() == 1) {
                                            MatchReplaceLibrary
                                            .makeChangesToReplaceOrInsertionString(
                                                    null, currentMatchReplaceSetting);
                                        }
                                    }
                }

                else {
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("the.current.request.gets.not.replaced.due.to.not.matching.relevant.request.regex.or.wrong.invoking.tool"));
                }
            }
        }

        return changesMade ? tempRequest:null;
    }

    public static byte[] getMatchReplaceResult(byte[] message,
            boolean replaceOnlyFirstMatch, boolean doProcessing,
            boolean doProcessingsOnMarkedPart, String csvDoingstring,
            String regex, String insertString) {

        String request = Library.getStringFromBytearray(message);
        try {
            Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(request);
            if (m.groupCount() == 0) {
                BurpExtender
                .addMessageToDebugOutput(Messages
                        .getString("no.regex.group.could.be.found.within.the.replace.regex"));
                new DisplayText(
                        Messages.getString("errorOccured"),
                        Messages.getString("no.regex.group.could.be.found.within.the.replace.regex"));
            }
            else {

                try {

                    request = Library.getReplacementByRegexGroups(request, m,
                            insertString, doProcessing,
                            doProcessingsOnMarkedPart, false, csvDoingstring,
                            !replaceOnlyFirstMatch);
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("replacement.is.done.on.current.request.with.the.following.replacement.string")
                            + insertString);
                    return Library.getBytearrayFromString(request);
                }catch (NothingDoneException e) {
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("replacement.is.not.done.on.current.request.as.replacement.regex.does.not.match.on.any.part.in.request"));

                }
            }

        }catch (PatternSyntaxException e) {
            BurpExtender
            .addMessageToDebugOutput(Messages
                    .getString("replacement.is.not.done.on.current.request.as.replacement.regex.is.invalid"));
            new DisplayText(
                    Messages.getString("errorOccured"),
                    Messages.getString("replacement.is.not.done.on.current.request.as.replacement.regex.is.invalid"));
            e.printStackTrace();
        }
        return null;
    }

    public static void makeChangesToReplaceOrInsertionString(byte[] response,
            MatchReplaceSettings currentMatchreplaceSetting) {

        if (currentMatchreplaceSetting.getMatchReplaceAction() == 1) {
            try {
                currentMatchreplaceSetting.setReplaceString(""
                        + (Integer.parseInt(currentMatchreplaceSetting
                                .getReplaceString()) + 1));
            }catch (Exception e) {
                new DisplayText(Messages.getString("errorOccured"),
                        Messages.getString("invalidStartNumber"));
                e.printStackTrace();
            }

        }
        else if (currentMatchreplaceSetting.getMatchReplaceAction() == 2) {
            try {
                Pattern p = Pattern.compile(currentMatchreplaceSetting
                        .getRegexForNewReplaceStringFromResponseExtraction(),
                        Pattern.DOTALL);
                Matcher m = p.matcher(Library.getStringFromBytearray(response));

                String result = "TheRe098waS123NO)(/neW456STrING{[]}ToextraCt";
                if (m.groupCount() == 0) {
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("no.regex.group.could.be.found.within.the.response.extraction.regex")
                            + currentMatchreplaceSetting
                            .getRegexForNewReplaceStringFromResponseExtraction());
                    new DisplayText(
                            Messages.getString("errorOccured"),
                            Messages.getString("no.regex.group.could.be.found.within.the.response.extraction.regex")
                            + currentMatchreplaceSetting
                            .getRegexForNewReplaceStringFromResponseExtraction());
                }
                else {
                    try {
                        // go to the desired match within the response, or to
                        // the last one if matchnumber equals -1
                        for (int i = 0; ((i < currentMatchreplaceSetting
                                .getResponseExtractionMatchingNumber()) || (currentMatchreplaceSetting
                                        .getResponseExtractionMatchingNumber() < 0))
                                        && m.find(); i++) {
                            result = m.group(1);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (!result
                        .equals("TheRe098waS123NO)(/neW456STrING{[]}ToextraCt")) {
                    // if (toReplaceString) {
                    currentMatchreplaceSetting.setReplaceString(result);
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("the.following.new.replace.string.has.been.extracted.from.the.current.response")
                            + result);

                }
                else {
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("no.new.replace.or.static.insert.string.could.be.extracted.from.the.current.response"));
                }
            }catch (PatternSyntaxException e) {
                BurpExtender
                .addMessageToDebugOutput(Messages
                        .getString("regex.for.response.extraction.is.invalid")
                        + currentMatchreplaceSetting
                        .getRegexForNewReplaceStringFromResponseExtraction());
                new DisplayText(
                        Messages.getString("errorOccured"),
                        Messages.getString("regex.for.response.extraction.is.invalid")
                        + currentMatchreplaceSetting
                        .getRegexForNewReplaceStringFromResponseExtraction());
                e.printStackTrace();
            }
        }
    }
}
