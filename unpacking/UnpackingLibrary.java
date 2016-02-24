/**
 * Erstellt am: Jul 4, 2013 3:32:39 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import misc.DisplayText;
import misc.Library;
import misc.Messages;
import unpacking.api.AbstractDeEncrypter;
import variables.Variables;
import burp.BurpExtender;
import exceptions.NothingDoneException;


public class UnpackingLibrary {

    public static byte[] getProcessedBytes(byte[] origMessage, String csvDoing,
            boolean packing, ArrayList<AbstractDeEncrypter> deEncrypterArraylist) {

        String[] doingArray = csvDoing.split(",");
        if (packing) {
            doingArray = UnpackingLibrary.reverseOrder(doingArray);
        }

        for (String currentDoing:doingArray) {
            for (AbstractDeEncrypter cur:deEncrypterArraylist) {
                if (currentDoing.equalsIgnoreCase(cur
                        .getDecryptIdentifierString())) {
                    origMessage = cur.getDecryptedBytearray(origMessage);
                }
                else if (currentDoing.equalsIgnoreCase(cur
                        .getEncryptIdentifierString())) {
                    origMessage = cur.getEncryptedBytearray(origMessage);
                }
            }
        }
        return origMessage;
    }


    public static byte[] getProcessedMessage(byte[] message, String regex,
            boolean isRequest, boolean unpack) {

        String requestString = Library.getStringFromBytearray(message);
        try {
            Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(
                    requestString);
            if (m.groupCount() == 0) {
                if (unpack) {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("no.regex.group.could.be.found.within.the.regex.marking.the.message.part.which.should.be.unpacked"));
                    new DisplayText(
                            Messages.getString("errorOccured"),
                            Messages.getString("no.regex.group.could.be.found.within.the.regex.marking.the.message.part.which.should.be.unpacked"));
                }
                else {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("no.regex.group.could.be.found.within.the.regex.marking.the.message.part.which.should.be.packed"));
                    new DisplayText(
                            Messages.getString("errorOccured"),
                            Messages.getString("no.regex.group.could.be.found.within.the.regex.marking.the.message.part.which.should.be.packed"));
                }
            }

            try {
                String csvDoing = "";
                if (isRequest) {
                    csvDoing = Variables.getInstance().getCsvDoingString();
                }
                else {
                    csvDoing = Variables.getInstance()
                            .getCsvResponseDoingString();
                }

                requestString = Library.getReplacementByRegexGroups(
                        requestString, m, "", true, true, !unpack, csvDoing,
                        true);

                if (unpack) {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("unpacking.has.been.done.on.current.message"));
                }
                else {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("packing.has.been.done.on.current.message"));
                }
                return Library.getBytearrayFromString(requestString);
            }catch (NothingDoneException e) {
                if (unpack) {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("unpacking.has.not.been.done.on.current.message.as.the.regex.marking.the.message.part.didnt.match"));
                }
                else {
                    BurpExtender
                            .addMessageToDebugOutput(Messages
                                    .getString("packing.has.not.been.done.on.current.message.as.the.regex.marking.the.message.part.didnt.match"));
                }
            }



        }catch (PatternSyntaxException e) {
            if (unpack) {
                BurpExtender
                        .addMessageToDebugOutput(Messages
                                .getString("unpacking.has.not.been.done.on.current.message.as.the.regex.marking.the.message.part.is.invalid"));
                new DisplayText(
                        Messages.getString("errorOccured"),
                        Messages.getString("unpacking.has.not.been.done.on.current.message.as.the.regex.marking.the.message.part.is.invalid"));
            }
            else {
                BurpExtender
                        .addMessageToDebugOutput(Messages
                                .getString("packing.has.not.been.done.on.current.message.as.the.regex.marking.the.message.part.is.invalid"));
                new DisplayText(
                        Messages.getString("errorOccured"),
                        Messages.getString("packing.has.not.been.done.on.current.message.as.the.regex.marking.the.message.part.is.invalid"));
            }
            e.printStackTrace();
        }
        return message;
    }

    private static String[] reverseOrder(String[] input) {

        String[] out = new String[input.length];
        int j = 0;
        for (int i = input.length - 1; i >= 0; i--) {
            for (AbstractDeEncrypter cur:Variables.getInstance()
                    .getDeEncrypterArraylist()) {
                try {
                    out[j] = cur.getReverseStringFromCurrentString(input[i]);
                    j++;
                }catch (NothingDoneException e) {
                    // Do nothing; this DeEncrypter was not the right one, go to
                    // the next
                }
            }
        }

        return out;
    }

    /**
     *
     * @param request
     *            the http request to process
     * @param toolName
     *            the toolname of the invoking tool
     * @return returns null if request unpacking is not active, or the current
     *         provided message is not relevant, otherwise the unpacked request
     *
     */
    public static byte[] tryUnpackOrPackRequest(byte[] request,
            String toolName, boolean unpack) {

        if (Variables.getInstance().isRequestUnpackingActive()) {

            // TODO either this way with checking for relevant regex after
            // unpacking or only unpack parameter - seems like only parameter is
            // sufficient
            boolean atLeastOneRelevanceCheckMatched = false;
            if (BurpExtender
                    .isRelevantMessage(
                            request,
                            Variables.getInstance().getRelevantResquestRegex(),
                            Variables.getInstance().getRequestToolName(),
                            toolName,
                            Variables
                                    .getInstance()
                                    .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking())) {
                // unpack = true;
                atLeastOneRelevanceCheckMatched = true;

                // TODO
                // BurpExtender.addMessageToDebugOutput(Messages
                // .getString("current.request.gets.unpacked"));
            }

            else if (BurpExtender
                    .isRelevantMessage(
                            request,
                            Variables.getInstance()
                                    .getRelevantUnpackedRequestRegex(),
                            Variables.getInstance().getRequestToolName(),
                            toolName,
                            Variables
                                    .getInstance()
                                    .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking())) {
                // unpack = false;
                atLeastOneRelevanceCheckMatched = true;

                // TODO
                // BurpExtender.addMessageToDebugOutput(Messages
                // .getString("current.request.gets.packed"));
            }
            if (atLeastOneRelevanceCheckMatched)
                return UnpackingLibrary.getProcessedMessage(request,
                        Variables.getInstance()
                                .getRegexMarkingRequestpartForUnpacking(),
                        true, unpack);
            else {
                BurpExtender
                        .addMessageToDebugOutput(Messages
                                .getString("current.request.gets.not.packed.or.unpacked.due.to.not.matching.relevant.request.regex.or.wrong.invoking.tool"));
            }
        }

        return null;
    }

    /**
     *
     * @param response
     *            the http response to process
     * @param toolName
     *            the toolname of the invoking tool
     * @return returns null if response unpacking is not active, or the current
     *         provided message is not relevant, otherwise the unpacked response
     *
     */
    public static byte[] tryUnpackResponse(byte[] response, String toolName) {


        if (Variables.getInstance().isResponseUnpackingActive()) {

            if (BurpExtender
                    .isRelevantMessage(
                            response,
                            Variables.getInstance().getRelevantResponseRegex(),
                            toolName,
                            toolName,
                            Variables
                                    .getInstance()
                                    .isRegexmatchIndicatingInrelevanceOnCurrentMessageForResponseunpacking())) {

                BurpExtender.addMessageToDebugOutput(Messages
                        .getString("current.response.gets.unpacked"));
                return UnpackingLibrary
                        .getProcessedMessage(response, Variables.getInstance()
                                .getRegexMarkingResponsepartForUnpacking(),
                                false, true);
            }
            else {
                BurpExtender
                        .addMessageToDebugOutput(Messages
                                .getString("current.response.gets.not.unpacked.due.to.not.matching.relevant.response.regex.or.wrong.invoking.tool"));
            }
        }
        return null;
    }
}
