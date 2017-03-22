
package matchreplace;

import java.io.Serializable;


public class MatchReplaceSettings implements Serializable {

    private String            toolnames                                        = "intruder";
    private String            relevantReplaceRequestRegex                      = ".";
    private boolean           matchIndicatesNonrelevanceForMatchreplaceRequest = false;
    private String            regexMarkingRelevantPartForReplacement           = "abcd(partToBeReplaced)";
    private String            replaceString                                    = "replaceString";
    private int               matchReplaceAction                               = 0;
    private boolean           matchReplaceActive                               = false;
    private boolean           replaceOnlyFirstMatch                            = false;
    private String            relevantRequestRegexForResponseExtraction        = ".";
    private boolean           regexForResponseExtractionIdentifiesRequest      = true;
    private boolean           matchIndicatesNonrelevanceForResponseextraction  = false;
    private String            regexForNewReplaceStringFromResponseExtraction   = "abcd(relevantString)";
    private int               responseExtractionMatchingNumber                 = 1;
    private boolean           processingOfReplacestringActive                  = false;
    private String            processingOfReplacestringCsvstring               = "base64encode";
    private String            responseToolnames                                = "intruder";
    private boolean           onlyDoProcessingOfMarkedPart                     = true;
    private boolean           doRequestReplacementAfterUnpacking               = false;
    private boolean           doResponseExtractionAfterUnpacking               = true;
    private boolean           activateThisRuleGlobally                         = false;
    private boolean           extractFromRequestInsteadFromResponse            = false;
    private static final long serialVersionUID                                 = 20L;


    /**
     * @return the replaceAction
     */
    public int getMatchReplaceAction() {

        return this.matchReplaceAction;
    }


    /**
     * @return the processingOfReplacestringCsvstring
     */
    public String getProcessingOfReplacestringCsvstring() {

        return this.processingOfReplacestringCsvstring;
    }

    /**
     * @return the regexForNewReplaceStringFromResponseExtraction
     */
    public String getRegexForNewReplaceStringFromResponseExtraction() {

        return this.regexForNewReplaceStringFromResponseExtraction;
    }

    /**
     * @return the regexMarkingRelevantPartForReplacement
     */
    public String getRegexMarkingRelevantPartForReplacement() {

        return this.regexMarkingRelevantPartForReplacement;
    }

    /**
     * @return the relevantReplaceRequestRegex
     */
    public String getRelevantReplaceRequestRegex() {

        return this.relevantReplaceRequestRegex;
    }

    /**
     * @return the relevantRequestRegexForResponseExtraction
     */
    public String getRelevantRequestRegexForResponseExtraction() {

        return this.relevantRequestRegexForResponseExtraction;
    }

    /**
     * @return the replaceString
     */
    public String getReplaceString() {

        return this.replaceString;
    }

    /**
     * @return the responseExtractionMatchingNumber
     */
    public int getResponseExtractionMatchingNumber() {

        return this.responseExtractionMatchingNumber;
    }

    /**
     * @return the responseToolnames
     */
    public String getResponseToolnames() {

        return this.responseToolnames;
    }

    /**
     * @return the toolnames
     */
    public String getToolnames() {

        return this.toolnames;
    }

    /**
     * @return the activateThisRuleGlobally
     */
    public boolean isActivateThisRuleGlobally() {

        return this.activateThisRuleGlobally;
    }

    /**
     * @return the doRequestReplacementAfterUnpacking
     */
    public boolean isDoRequestReplacementAfterUnpacking() {

        return this.doRequestReplacementAfterUnpacking;
    }

    /**
     * @return the doResponseExtractionAfterUnpacking
     */
    public boolean isDoResponseExtractionAfterUnpacking() {

        return this.doResponseExtractionAfterUnpacking;
    }

    /**
     * @return the extractFromRequestInsteadFromResponse
     */
    public boolean isExtractFromRequestInsteadFromResponse() {

        return this.extractFromRequestInsteadFromResponse;
    }

    /**
     * @return the matchIndicatesNonrelevanceForMatchreplaceRequest
     */
    public boolean isMatchIndicatesNonrelevanceForMatchreplaceRequest() {

        return this.matchIndicatesNonrelevanceForMatchreplaceRequest;
    }

    /**
     * @return the matchIndicatesNonrelevanceForResponseextraction
     */
    public boolean isMatchIndicatesNonrelevanceForResponseextraction() {

        return this.matchIndicatesNonrelevanceForResponseextraction;
    }

    /**
     * @return the matchReplaceActive
     */
    public boolean isMatchReplaceActive() {

        return this.matchReplaceActive;
    }

    /**
     * @return the onlyDoProcessingOfMarkedPart
     */
    public boolean isOnlyDoProcessingOfMarkedPart() {

        return this.onlyDoProcessingOfMarkedPart;
    }

    /**
     * @return the processingOfReplacestringActive
     */
    public boolean isProcessingOfReplacestringActive() {

        return this.processingOfReplacestringActive;
    }

    /**
     * @return the regexForResponseExtractionIdentifiesRequest
     */
    public boolean isRegexForResponseExtractionIdentifiesRequest() {

        return this.regexForResponseExtractionIdentifiesRequest;
    }

    /**
     * @return the replaceOnlyFirstMatch
     */
    public boolean isReplaceOnlyFirstMatch() {

        return this.replaceOnlyFirstMatch;
    }

    /**
     * @param activateThisRuleGlobally
     *            the activateThisRuleGlobally to set
     */
    public void setActivateThisRuleGlobally(boolean activateThisRuleGlobally) {

        this.activateThisRuleGlobally = activateThisRuleGlobally;
    }

    /**
     * @param doRequestReplacementAfterUnpacking
     *            the doRequestReplacementAfterUnpacking to set
     */
    public void setDoRequestReplacementAfterUnpacking(
            boolean doRequestReplacementAfterUnpacking) {

        this.doRequestReplacementAfterUnpacking = doRequestReplacementAfterUnpacking;
    }

    /**
     * @param doResponseExtractionAfterUnpacking
     *            the doResponseExtractionAfterUnpacking to set
     */
    public void setDoResponseExtractionAfterUnpacking(
            boolean doResponseExtractionAfterUnpacking) {

        this.doResponseExtractionAfterUnpacking = doResponseExtractionAfterUnpacking;
    }

    /**
     * set the extractFromRequestInsteadFromResponse
     */
    public void setExtractFromRequestInsteadFromResponse(boolean value) {

        this.extractFromRequestInsteadFromResponse = value;
    }

    /**
     * @param matchIndicatesNonrelevanceForMatchreplaceRequest
     *            the matchIndicatesNonrelevanceForMatchreplaceRequest to set
     */
    public void setMatchIndicatesNonrelevanceForMatchreplaceRequest(
            boolean matchIndicatesNonrelevanceForMatchreplaceRequest) {

        this.matchIndicatesNonrelevanceForMatchreplaceRequest = matchIndicatesNonrelevanceForMatchreplaceRequest;
    }


    /**
     * @param matchIndicatesNonrelevanceForResponseextraction
     *            the matchIndicatesNonrelevanceForResponseextraction to set
     */
    public void setMatchIndicatesNonrelevanceForResponseextraction(
            boolean matchIndicatesNonrelevanceForResponseextraction) {

        this.matchIndicatesNonrelevanceForResponseextraction = matchIndicatesNonrelevanceForResponseextraction;
    }


    /**
     * @param replaceAction
     *            the replaceAction to set
     */
    public void setMatchReplaceAction(int matchReplaceAction) {

        this.matchReplaceAction = matchReplaceAction;
    }


    /**
     * @param matchReplaceActive
     *            the matchReplaceActive to set
     */
    public void setMatchReplaceActive(boolean matchReplaceActive) {

        this.matchReplaceActive = matchReplaceActive;
    }


    /**
     * @param onlyDoProcessingOfMarkedPart
     *            the onlyDoProcessingOfMarkedPart to set
     */
    public void setOnlyDoProcessingOfMarkedPart(
            boolean onlyDoProcessingOfMarkedPart) {

        this.onlyDoProcessingOfMarkedPart = onlyDoProcessingOfMarkedPart;
    }


    /**
     * @param processingOfReplacestringActive
     *            the processingOfReplacestringActive to set
     */
    public void setProcessingOfReplacestringActive(
            boolean processingOfReplacestringActive) {

        this.processingOfReplacestringActive = processingOfReplacestringActive;
    }


    /**
     * @param processingOfReplacestringCsvstring
     *            the processingOfReplacestringCsvstring to set
     */
    public void setProcessingOfReplacestringCsvstring(
            String processingOfReplacestringCsvstring) {

        this.processingOfReplacestringCsvstring = processingOfReplacestringCsvstring;
    }


    /**
     * @param regexForNewReplaceStringFromResponseExtraction
     *            the regexForNewReplaceStringFromResponseExtraction to set
     */
    public void setRegexForNewReplaceStringFromResponseExtraction(
            String regexForNewReplaceStringFromResponseExtraction) {

        this.regexForNewReplaceStringFromResponseExtraction = regexForNewReplaceStringFromResponseExtraction;
    }


    /**
     * @param regexForResponseExtractionIdentifiesRequest
     *            the regexForResponseExtractionIdentifiesRequest to set
     */
    public void setRegexForResponseExtractionIdentifiesRequest(
            boolean regexForResponseExtractionIdentifiesRequest) {

        this.regexForResponseExtractionIdentifiesRequest = regexForResponseExtractionIdentifiesRequest;
    }


    /**
     * @param regexMarkingRelevantPartForReplacement
     *            the regexMarkingRelevantPartForReplacement to set
     */
    public void setRegexMarkingRelevantPartForReplacement(
            String regexMarkingRelevantPartForReplacement) {

        this.regexMarkingRelevantPartForReplacement = regexMarkingRelevantPartForReplacement;
    }


    /**
     * @param relevantReplaceRequestRegex
     *            the relevantReplaceRequestRegex to set
     */
    public void setRelevantReplaceRequestRegex(
            String relevantReplaceRequestRegex) {

        this.relevantReplaceRequestRegex = relevantReplaceRequestRegex;
    }


    /**
     * @param relevantRequestRegexForResponseExtraction
     *            the relevantRequestRegexForResponseExtraction to set
     */
    public void setRelevantRequestRegexForResponseExtraction(
            String relevantRequestRegexForResponseExtraction) {

        this.relevantRequestRegexForResponseExtraction = relevantRequestRegexForResponseExtraction;
    }


    /**
     * @param replaceOnlyFirstMatch
     *            the replaceOnlyFirstMatch to set
     */
    public void setReplaceOnlyFirstMatch(boolean replaceOnlyFirstMatch) {

        this.replaceOnlyFirstMatch = replaceOnlyFirstMatch;
    }


    /**
     * @param replaceString
     *            the replaceString to set
     */
    public void setReplaceString(String replaceString) {

        this.replaceString = replaceString;
    }


    /**
     * @param responseExtractionMatchingNumber
     *            the responseExtractionMatchingNumber to set
     */
    public void setResponseExtractionMatchingNumber(
            int responseExtractionMatchingNumber) {

        this.responseExtractionMatchingNumber = responseExtractionMatchingNumber;
    }


    /**
     * @param responseToolnames
     *            the responseToolnames to set
     */
    public void setResponseToolnames(String responseToolnames) {

        this.responseToolnames = responseToolnames;
    }


    /**
     * @param toolnames
     *            the toolnames to set
     */
    public void setToolnames(String toolnames) {

        this.toolnames = toolnames;
    }


}
