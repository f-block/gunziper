
package misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;


import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;

import unpacking.UnpackingLibrary;
import variables.Variables;
import burp.BurpExtender;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import burp.IHttpRequestResponseWithMarkers;
import burp.IParameter;
import burp.IRequestInfo;
import exceptions.NothingDoneException;



public class Library {

    // public static String byteArrayToHex(byte[] input) {
    //
    // StringBuilder sb = new StringBuilder();
    // for (byte b:input) {
    // sb.append(String.format("%02X ", b));
    // }
    //
    // return sb.toString().replaceAll(" ", "");
    // }

    public static String generateHttpRequestIdentificationString(
            IRequestInfo reqInfo, ArrayList<Integer> uniqFlags,
            String asvExcludedParametersString) {

        boolean includeCookies = false;
        boolean includeCookieValues = false;
        boolean includeParamValues = false;
        boolean excludeHost = false;
        boolean excludeProtocol = false;
        boolean excludeForExcludedParamsOnlyTheValues = false;
        boolean excludeHttpMethod = false;
        boolean changeExcludeParamsModeToIncludeMode = false;

        String[] excludedParamsArray = null;
        if ((asvExcludedParametersString != null)
                && !asvExcludedParametersString.equals("")) {
            excludedParamsArray = asvExcludedParametersString.split("&");
        }
        boolean excludeSpecificParams = excludedParamsArray != null;


        for (Integer uniqFlag:uniqFlags) {
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_IGNORE_FOR_EXCLUDED_PARAMS_ONLY_THE_VALUES) {
                excludeForExcludedParamsOnlyTheValues = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_IGNORE_HOST) {
                excludeHost = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_IGNORE_PROTOCOL) {
                excludeProtocol = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_COOKIE) {
                includeCookies = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_COOKIE_VALUE) {
                includeCookieValues = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_PARAM_VALUE) {
                includeParamValues = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_EXCLUDE_HTTP_METHOD) {
                excludeHttpMethod = true;
            }
            if (uniqFlag == SaveAllReqsRespsSelectionWindow.UNIQUE_MODE_INCLUDE_PARAMETERS_INSTEAD_OF_EXCLUSION) {
                changeExcludeParamsModeToIncludeMode = true;
            }
        }
        String output = "";
        URL tempUrl = null;
        try {
            // Workaround, since burps url parsing makes mistakes in the case of
            // more than one ? within url (path is in that case longer than it
            // should be
            tempUrl = new URL(reqInfo.getUrl().toString());

            output = (excludeHttpMethod ? "":reqInfo.getMethod())
                    + "|"
                    + (excludeProtocol ? "":tempUrl.getProtocol())
                    + "|"
                    + tempUrl.getPort()
                    + "|"
                    + (excludeHost ? "":tempUrl.getHost())
                    + "|"
                    + tempUrl.getPath().replaceFirst(
                            ";(?i)(?:jsessionid|phpsessid)=[a-z0-9.]+$", "")
                            + "|"; // exclude appended sessionids/phpsessids within url

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        outerLoop: for (IParameter param:reqInfo.getParameters()) {
            if (excludeSpecificParams) {
                for (String currParam:excludedParamsArray) {
                    if (param.getName().equals(currParam)) {
                        if (!changeExcludeParamsModeToIncludeMode) {
                            if (excludeForExcludedParamsOnlyTheValues) {
                                output += param.getName() + "|";
                            }
                            continue outerLoop;
                        }
                        else {
                            output += param.getName() + "|";
                            if (includeParamValues) {
                                output += param.getValue() + "|";
                            }
                        }
                    }
                }
            }

            else {
                if ((param.getType() == IParameter.PARAM_COOKIE)
                        && includeCookies) {
                    output += param.getName() + "|";
                    if (includeCookieValues) {
                        output += param.getValue() + "|";
                    }
                }
                else if (param.getType() != IParameter.PARAM_COOKIE) {
                    output += param.getName() + "|";
                    if (includeParamValues) {
                        output += param.getValue() + "|";
                    }
                }
            }
        }
        return output;
    }

    public static IHttpRequestResponse[] generateUniqeRequestsWithCorrespondingResponses(
            IHttpRequestResponse[] reqResponses, ArrayList<Integer> uniqFlags,
            String asvExcludedParametersString) {

        HashMap<String, IHttpRequestResponse> resultHashmap = new HashMap<String, IHttpRequestResponse>();
        String tempReqHashstring = "";

        for (IHttpRequestResponse currReqResp:reqResponses) {
            tempReqHashstring = Library.isRequestUnique(resultHashmap.keySet(),
                    currReqResp, uniqFlags, asvExcludedParametersString);
            if (!(tempReqHashstring.equals(""))) {
                resultHashmap.put(tempReqHashstring, currReqResp);
            }
        }

        return resultHashmap.values().toArray(new IHttpRequestResponse[0]);
    }

    public static byte[] getBytearrayFromString(String input) {

        try {
            return input.getBytes("ISO-8859-1");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFirstRegexgroupMatch(String regex, String string) {

        Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(string);
        if (m.find())
            return m.group(1);
        return null;

    }

    public static byte[] getMessageBody(byte[] inputMessage) {

        byte[] out = null;
        boolean found = false;
        int offset = 0;
        for (int i = 0; i < inputMessage.length; i++) {
            if (inputMessage[i] == 13) {
                if ((i + 3) < inputMessage.length) {
                    if (inputMessage[i + 3] == 10) {
                        found = true;
                        offset = i + 4;
                        break;
                    }
                }
            }
        }

        if (found) {
            out = new byte[inputMessage.length - offset];

            for (int i = offset; i < inputMessage.length; i++) {
                out[i - offset] = inputMessage[i];
            }
        }

        return out;
    }

    public static byte[] getMessageHeader(byte[] inputMessage) {

        byte[] out = null;
        boolean found = false;
        int offset = 0;
        for (int i = 0; i < inputMessage.length; i++) {
            if (inputMessage[i] == 13) {
                if (inputMessage[i + 3] == 10) {
                    found = true;
                    offset = i + 3;
                    break;
                }
            }
        }

        if (found) {
            out = new byte[offset + 1];

            for (int i = 0; i <= offset; i++) {
                out[i] = inputMessage[i];
            }
        }

        return out;
    }


    public static String[] getPayloadStringArray(File file) {

        List<String> lines = new LinkedList<String>();
        String line = "";
        String[] lineArray = null;
        if (file.exists()) {
            BufferedReader in = null;

            try {

                in = new BufferedReader(new FileReader(file));
                while ((line = in.readLine()) != null) {
                    lines.add(line);
                }

            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    in.close();
                }catch (IOException e) {
                    // ignore
                }
            }

            lineArray = new String[lines.size()];
            int i = 0;
            for (Object element:lines) {
                lineArray[i] = (String) element;
                i++;
            }
        }

        return lineArray;
    }

    public static int getRealModuloResult(int start, int modul) {

        return ((start % modul) + modul) % modul;
    }

    /**
     *
     * @param input
     *            The string to process
     * @param m
     *            The regex matcher object, marking the part to be processed
     *            within the input string
     * @param replacementString
     *            What the matched part should be replaced with
     * @param doProcessings
     *            Enables processings (like base64decode) before replacement is
     *            done
     * @param doProcessingsOnMarkedPart
     *            Does only processing on the marked part, but not replacement
     *            with a given string
     * @param packing
     *            If request unpacking is enabled, this flag controls whether it
     *            is
     *            packing or unpacking
     * @param csvProcessingString
     *            The processings to be done
     * @param replaceAll
     *            Whether or not all matched parts should be replaced
     * @return
     * @throws NothingDoneException
     */
    public static String getReplacementByRegexGroups(String input, Matcher m,
            String replacementString, boolean doProcessings,
            boolean doProcessingsOnMarkedPart, boolean packing,
            String csvProcessingString, boolean replaceAll)
                    throws NothingDoneException {

        try {
            StringBuffer sb = new StringBuffer();
            String middleString = "";

            // Convert escaped special chars to real special chars
            replacementString = replacementString.replaceAll("\\\\r", "\r");
            replacementString = replacementString.replaceAll("\\\\n", "\n");
            replacementString = replacementString.replaceAll("\\\\t", "\t");

            boolean match = false;

            while (m.find() && (!match || replaceAll)) {
                match = true;
                middleString = "";
                if (m.start(1) >= 0) {
                    middleString += input.substring(m.start(), m.start(1));
                    if (doProcessings && !csvProcessingString.equals("")) {
                        if (doProcessingsOnMarkedPart) {
                            middleString += Library
                                    .getStringFromBytearray(UnpackingLibrary.getProcessedBytes(
                                            Library.getBytearrayFromString(m
                                                    .group(1)),
                                            csvProcessingString, packing,
                                            Variables.getInstance()
                                                    .getDeEncrypterArraylist()));
                        }
                        else {
                            middleString += Library
                                    .getStringFromBytearray(UnpackingLibrary.getProcessedBytes(
                                            Library.getBytearrayFromString(replacementString),
                                            csvProcessingString, packing,
                                            Variables.getInstance()
                                                    .getDeEncrypterArraylist()));
                        }
                    }
                    else {
                        middleString += replacementString;
                    }
                }
                if (m.groupCount() > 1) {
                    for (int i = 2; i <= m.groupCount(); i++) {
                        if ((m.start(i) >= 0) && (m.start(i - 1) >= 0)) {
                            middleString += input.substring(m.end(i - 1),
                                    m.start(i));
                            if (doProcessings
                                    && !csvProcessingString.equals("")) {
                                if (doProcessingsOnMarkedPart) {
                                    middleString += Library
                                            .getStringFromBytearray(UnpackingLibrary.getProcessedBytes(
                                                    Library.getBytearrayFromString(m
                                                            .group(i)),
                                                    csvProcessingString,
                                                    packing,
                                                    Variables
                                                            .getInstance()
                                                            .getDeEncrypterArraylist()));
                                }
                                else {
                                    middleString += Library
                                            .getStringFromBytearray(UnpackingLibrary.getProcessedBytes(
                                                    Library.getBytearrayFromString(replacementString),
                                                    csvProcessingString,
                                                    packing,
                                                    Variables
                                                            .getInstance()
                                                            .getDeEncrypterArraylist()));
                                }
                            }
                            else {
                                middleString += replacementString;
                            }

                        }
                        else if (m.start(i) >= 0) {
                            middleString += input.substring(m.start(),
                                    m.start(i));
                            if (doProcessings
                                    && !csvProcessingString.equals("")) {
                                if (doProcessingsOnMarkedPart) {
                                    middleString += Library
                                            .getStringFromBytearray(UnpackingLibrary.getProcessedBytes(
                                                    Library.getBytearrayFromString(m
                                                            .group(i)),
                                                    csvProcessingString,
                                                    packing,
                                                    Variables
                                                            .getInstance()
                                                            .getDeEncrypterArraylist()));
                                }
                                else {
                                    middleString += Library
                                            .getStringFromBytearray(UnpackingLibrary.getProcessedBytes(
                                                    Library.getBytearrayFromString(replacementString),
                                                    csvProcessingString,
                                                    packing,
                                                    Variables
                                                            .getInstance()
                                                            .getDeEncrypterArraylist()));
                                }
                            }
                            else {
                                middleString += replacementString;
                            }
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
                        middleString += input.substring(m.end(lastGroup),
                                m.end());
                    }
                }

                m.appendReplacement(sb, Matcher.quoteReplacement(middleString));

            }
            if (!match)
                throw new NothingDoneException("");
            m.appendTail(sb);
            return sb.toString();
        }catch (NothingDoneException e) {
            throw e;
        }catch (IndexOutOfBoundsException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
        }
        throw new NothingDoneException("");
    }

    public static String getStringFromBytearray(byte[] input) {

        try {
            return new String(input, "ISO-8859-1");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringCodeBlockFromBytearray(byte[] input) {

        try {            
            String fromBytearry = new String(input, "ISO-8859-1");
            Scanner scanner = new Scanner(fromBytearry);
            StringBuilder outBuild = new StringBuilder();
            while (scanner.hasNextLine()){
                outBuild.append("  " + scanner.nextLine() + "\n");
            }
            return outBuild.toString();

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSubstring(String input, int substringLength) {

        String output = "";
        int inputLength = input.length();
        int length = inputLength >= substringLength ? substringLength
                :inputLength;

        output += input.substring(0, length);
        if (inputLength > substringLength) {
            output += "...";
        }

        return output;
    }


    public static int getValueToScrollHighlightedLineToTheMiddle(
            JTextArea currentTextarea) throws BadLocationException {

        // how much to scroll to scroll down 1 line
        int textareaUnitIncrement = currentTextarea.getScrollableUnitIncrement(
                null, SwingConstants.VERTICAL, SwingConstants.SOUTH);

        // get y coordinate of cursor position
        int yCoordinateOfCurrentHighlight = currentTextarea
                .modelToView(currentTextarea.getCaretPosition()).y;

        // indent used to scroll highlight to the middle
        int indent = (currentTextarea.getVisibleRect().height / 2)
                - (2 * textareaUnitIncrement);

        yCoordinateOfCurrentHighlight -= indent;

        return yCoordinateOfCurrentHighlight;
    }


    public static String isRequestUnique(Set<String> hashValuesSet,
            IHttpRequestResponse reqResp, ArrayList<Integer> uniqFlags,
            String asvExcludedParametersString) {

        String requestHashstring = "";
        try {
            requestHashstring = Library
                    .generateHttpRequestIdentificationString(BurpExtender
                            .getIextensionHelper().analyzeRequest(reqResp),
                            uniqFlags, asvExcludedParametersString);

            for (String hash:hashValuesSet) {
                if (requestHashstring.equals(hash))
                    return "";
            }
        }catch (NullPointerException e) {
            new DisplayText("Info",
                    Messages.getString("at.least.one.request.seems.to.be.null"));
        }


        return requestHashstring;
    }


    public static Object loadSerializedObjectFromFile(String filename)
            throws Exception {

        ObjectInputStream obj_in = null;
        try {
            obj_in = new ObjectInputStream(new FileInputStream(filename));

            return obj_in.readObject();

        }catch (Exception e) {
            throw e;
        }finally {
            try {
                obj_in.close();
            }catch (IOException e) {
                // do nothing
            }
        }
    }

    public static byte[] mergeByteArrays(byte[] array1, byte[] array2) {

        byte[] out = new byte[array1.length + array2.length];
        for (int i = 0; i < array1.length; i++) {
            out[i] = array1[i];
        }
        for (int i = 0; i < array2.length; i++) {
            out[i + array1.length] = array2[i];
        }

        return out;
    }

    public static byte[] prepareMessageHeader(byte[] message) {

        byte[] body = Library.getMessageBody(message);
        return Library.mergeByteArrays(
                Library.getBytearrayFromString(Library.getStringFromBytearray(
                        Library.getMessageHeader(message)).replaceAll(
                        "\r\nContent-Length: [0-9]+\r\n",
                        Matcher.quoteReplacement("\r\nContent-Length: "
                                + body.length + "\r\n"))), body);
    }

    public static String readFromFile(String filename) {

        String output = "";
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(
                    new FileInputStream(new File(filename)));

            for (int c; (c = in.read()) != -1;) {
                output += (char) c;
            }
        }catch (FileNotFoundException e) {
            new DisplayText("Error occured",
                    "The specified file couldn't be found");
            e.printStackTrace();
        }catch (IOException e) {
            new DisplayText("Error occured", "Couldn't write to specified file");
            e.printStackTrace();
        }finally {
            try {
                in.close();
            }catch (IOException e) {
                // do nothing
            }
        }

        return output;

    }

    public static void saveRequestResponseToFile(File file, Object element,
            IExtensionHelpers helpers, boolean includePocSeparators,
            boolean includeRequestHeader, boolean includeRequestBody,
            boolean includeResponseBody, boolean includeResponseHeader,
            boolean includeUrlInFirstLine, boolean includeResponsibleParameter,
            boolean includeMarkedRequestParts, boolean isParamSpecificFinding,
            String paramSpecificFindingParameters,
            boolean includeMarkedResponseParts, String regexForExclusion,
            boolean appendToFile, boolean useMarkupStyle) throws IOException {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, appendToFile);
            // byte[] reqRespBytes = null;
            boolean isRequestResponseWithMarkers = false;
            IHttpRequestResponse message = (IHttpRequestResponse) element;
            boolean includeResponseHeaderAndBody = includeResponseBody
                    && includeResponseHeader;

            if (element instanceof IHttpRequestResponseWithMarkers) {
                isRequestResponseWithMarkers = true;
            }


            String marker = "=====";
            String codeBlk = "::\n\n";
            boolean codeBlkStarted = false;

            if (includeUrlInFirstLine) {
                if (includePocSeparators) {
                    out.write(Library.getBytearrayFromString(marker + " URL "
                            + marker + "\n"));
                }
                out.write(Library.getBytearrayFromString(helpers
                        .analyzeRequest(message).getUrl().toString()
                        + "\n"));
                if (includePocSeparators) {
                    out.write("\n".getBytes());
                }
            }

            if (includeResponsibleParameter && isParamSpecificFinding) {
                if (includePocSeparators) {
                    out.write(Library.getBytearrayFromString(marker
                            + " Responsible Parameters " + marker + "\n"));
                }

                out.write(Library
                        .getBytearrayFromString(paramSpecificFindingParameters
                                + "\n"));
                if (includePocSeparators) {
                    out.write(Library.getBytearrayFromString("\n"));
                }
            }

            if (includeMarkedRequestParts && isRequestResponseWithMarkers) {
                Iterator<int[]> iterator = ((IHttpRequestResponseWithMarkers) message)
                        .getRequestMarkers().iterator();
                int[] temp = null;

                boolean firstParamWritten = false;

                while (iterator.hasNext()) {
                    if (!firstParamWritten && includePocSeparators) {
                        out.write(Library.getBytearrayFromString(marker
                                + " Marked Request Parts " + marker + "\n"));
                        firstParamWritten = true;
                    }

                    temp = iterator.next();
                    for (int i = temp[0]; i < temp[1]; i++) {
                        out.write(message.getRequest()[i]);
                    }
                    out.write(Library.getBytearrayFromString("\n"));
                }
                if (includePocSeparators) {
                    out.write(Library.getBytearrayFromString("\n"));
                }
                if (!includeMarkedResponseParts && includePocSeparators) {
                    out.write(Library.getBytearrayFromString("\n"));
                }
            }

            if (includeMarkedResponseParts && isRequestResponseWithMarkers) {
                Iterator<int[]> iterator = ((IHttpRequestResponseWithMarkers) message)
                        .getResponseMarkers().iterator();
                int[] temp = null;
                boolean firstResponsePartWritten = false;

                while (iterator.hasNext()) {
                    if (!firstResponsePartWritten && includePocSeparators) {
                        out.write(Library.getBytearrayFromString(marker
                                + " Marked Response Parts " + marker + "\n"));
                        firstResponsePartWritten = true;
                    }

                    temp = iterator.next();
                    for (int i = temp[0]; i < temp[1]; i++) {
                        out.write(message.getResponse()[i]);
                    }
                    out.write(Library.getBytearrayFromString("\n"));
                }
                if (includePocSeparators) {
                    out.write(Library.getBytearrayFromString("\n\n"));
                }
            }

            if ((includeRequestHeader || includeRequestBody
                    || includeResponseHeader || includeResponseBody)
                    && includePocSeparators && !useMarkupStyle) {
                out.write(Library.getBytearrayFromString(marker
                        + " Gunziper Request Section " + marker + "\n"));
            } else if ((includeRequestHeader || includeRequestBody
                    || includeResponseHeader || includeResponseBody)
                    && includePocSeparators && useMarkupStyle) {
                out.write(Library.getBytearrayFromString(marker
                        + " Gunziper Request/Response Section " + marker + "\n\n"));
                out.write(Library.getBytearrayFromString("**Request:**" + "\n\n"));
            }

            String reqRespString = "";

            if (includeRequestHeader) {
                try {
                    if (!useMarkupStyle) {
                        reqRespString += Library.getStringFromBytearray(Library
                                .getMessageHeader(message.getRequest()));
                    } else {
                        if (!codeBlkStarted) {
                            reqRespString += codeBlk;
                            codeBlkStarted = true;
                        }
                        reqRespString += Library.getStringCodeBlockFromBytearray(Library
                                .getMessageHeader(message.getRequest()));
                    }
                    if (!includeRequestBody
                            && (includeResponseHeader || includeResponseBody)
                            && includePocSeparators) {
                        reqRespString += "\n\n";

                    }
                }catch (NullPointerException e) {
                    // DO NOTHING
                }
            }

            if (includeRequestBody) {
                try {
                    if (!useMarkupStyle) {
                        reqRespString += Library.getStringFromBytearray(Library
                                .getMessageBody(message.getRequest()));
                        if ((includeResponseHeader || includeResponseBody)
                                && includePocSeparators) {
                            reqRespString += "\n" + marker + " Gunziper Response Section " + marker + "\n";
                        }
                    } else {
                        if (!codeBlkStarted) {
                            reqRespString += codeBlk;
                            codeBlkStarted = true;
                        }
                        reqRespString += Library.getStringCodeBlockFromBytearray(Library
                                .getMessageBody(message.getRequest()));
                        reqRespString += "\n";
                        codeBlkStarted = false;
                        reqRespString += "**Response:**" + "\n\n";
                    }
                }catch (NullPointerException e) {
                    // DO NOTHING
                }
            }

            if (includeResponseBody || includeResponseHeader) {
                try {
                    if (!useMarkupStyle){
                        if (includeResponseHeaderAndBody) {
                            reqRespString += Library.getStringFromBytearray(message
                                    .getResponse());
                        }
                        else if (includeResponseHeader) {
                            reqRespString += Library.getStringFromBytearray(Library
                                    .getMessageHeader(message.getResponse()));
                        }
                        else if (includeResponseBody) {
                            reqRespString += Library.getStringFromBytearray(Library
                                    .getMessageBody(message.getResponse()));
                        }
                    } else {
                        reqRespString += codeBlk;
                        if (includeResponseHeaderAndBody) {                            
                            reqRespString += Library.getStringCodeBlockFromBytearray(message
                                    .getResponse());
                            
                        }
                        else if (includeResponseHeader) {
                            reqRespString += Library.getStringCodeBlockFromBytearray(Library
                                    .getMessageHeader(message.getResponse()));
                        }
                        else if (includeResponseBody) {
                            reqRespString += Library.getStringCodeBlockFromBytearray(Library
                                    .getMessageBody(message.getResponse()));
                        }
                        //reqRespString += "\n";

                    }

                }catch (NullPointerException e) {
                    // DO NOTHING

                }
            }

            if (!regexForExclusion.equals("")) {
                try {
                    Matcher m = Pattern.compile(regexForExclusion,
                            Pattern.DOTALL).matcher(reqRespString);
                    reqRespString = Library
                            .getReplacementByRegexGroups(reqRespString, m, "",
                                    false, false, false, "", true);
                }catch (NothingDoneException e) {
                    // DO NOTHING
                }
            }

            if (reqRespString != null) {
                try {
                    out.write(Library.getBytearrayFromString(reqRespString));
                }catch (NullPointerException e) {
                    // DO NOTHING
                }
            }

            out.flush();
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
            new DisplayText(Messages.getString("errorOccured"), e.toString());
        }finally {
            out.close();
        }
    }

    public static void writeSerializableObjectToFile(String filename,
            Object object) {

        ObjectOutputStream obj_out = null;
        try {
            obj_out = new ObjectOutputStream(new FileOutputStream(filename));

            obj_out.writeObject(object);
        }catch (FileNotFoundException e) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("the.specified.file.couldnt.be.found"));
            e.printStackTrace();
        }catch (IOException e) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("couldnt.write.to.specified.file"));
            e.printStackTrace();
        }finally {
            try {
                obj_out.close();
            }catch (IOException e) {
                // do nothing
            }
        }
    }

    public static void writeToFile(String filename, String data) {

        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(
                    filename)));
            out.write(Library.getBytearrayFromString(data));
        }catch (FileNotFoundException e) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("the.specified.file.couldnt.be.found"));
            e.printStackTrace();
        }catch (IOException e) {
            new DisplayText(Messages.getString("errorOccured"),
                    Messages.getString("couldnt.write.to.specified.file"));
            e.printStackTrace();
        }finally {
            try {
                out.close();
            }catch (IOException e) {
                // do nothing
            }
        }

    }

}
