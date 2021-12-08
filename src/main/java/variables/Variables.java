

package variables;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import matchreplace.MatchReplaceSettings;
import matchreplace.ResendRequests;
import misc.Library;
import misc.Messages;
import unpacking.api.AbstractDeEncrypter;



public class Variables implements Serializable {


    public static Variables getInstance() {

        if (Variables.variablesInstance == null) {
            Variables.variablesInstance = new Variables();
        }
        return Variables.variablesInstance;
    }

    protected static void setInstance(Variables instance) {

        Variables.variablesInstance = instance;
    }

    // private String prefix = Messages
    // .getString("prefix");
    private String                               regex                                                                                 = Messages
                                                                                                                                               .getString("regex");
    // private String replaceString = Messages
    // .getString("replaceString");
    // private String replaceToolName = Messages
    // .getString("replaceToolName");
    // private String relevantReplaceRequestRegex = Messages
    // .getString("relevantReplaceRequestRegex");
    private String                               relevantStaticInsertionRequestRegex                                                   = Messages
                                                                                                                                               .getString("relevantStaticInsertionRequestRegex");
    // private String regexForNewReplaceStringFromResponseExtraction =
    // Messages
    // .getString("regexForNewReplaceStringFromResponseExtraction");

    private String                               processingsString                                                                     = "";


    private String                               csvDoingString                                                                        = "base64decode";

    private String                               csvResponseDoingString                                                                = this.csvDoingString;

    private String                               relevantResponseRegex                                                                 = Messages
                                                                                                                                               .getString("relevantResponseRegex");

    private int                                  staticInsertionAction                                                                 = 0;
    // private int replaceAction = 0;
    // private int matchReplaceAction = 0;
    private String                               relevantResquestRegex                                                                 = ".";
    private String                               responseToolName                                                                      = Messages
                                                                                                                                               .getString("responseToolName");
    private String                               requestToolName                                                                       = "repeater,intruder";
    private String                               staticInsertString                                                                    = Messages
                                                                                                                                               .getString("staticInsertString");
    private String                               staticInsertMarkerString                                                              = Messages
                                                                                                                                               .getString("staticInsertMarkerString");
    private String                               staticInsertToolname                                                                  = Messages
                                                                                                                                               .getString("staticInsertToolname");
    private String                               staticInsertResponseExtractionRegex                                                   = Messages
                                                                                                                                               .getString("staticInsertResponseExtractionRegex");
    private String                               relevantMessageRegexForStaticResponseExtraction                                       = Messages
                                                                                                                                               .getString("relevantRequestRegexForStaticResponseExtraction");
    // private String relevantMessageRegexForReplaceResponseExtraction =
    // Messages
    // .getString("relevantRequestRegexForReplaceResponseExtraction");
    // private String replaceResponseExtractionMatchingNumber = Messages
    // .getString("replaceResponseExtractionMatchingNumber");
    private String                               staticResponseExtractionMatchingNumber                                                = Messages
                                                                                                                                               .getString("staticResponseExtractionMatchingNumber");
    private String                               dropRequestRegex                                                                      = Messages
                                                                                                                                               .getString("a.regex.matching.the.request.that.should.be.dropped");

    private String                               regexMarkingResponsepartForUnpacking                                                  = Messages
                                                                                                                                               .getString("a.regex.marking.the.relevant.part.within.the.response.which.should.be.unpacked");
    private String                               regexMarkingRequestpartForUnpacking                                                   = "\\r\\n\\r\\n(.*)";
    private String                               relevantUnpackedRequestRegex                                                          = ".";
    private int                                  numberOfDebugLines                                                                    = 200;
    private String                               debugText                                                                             = "";
    private boolean                              staticInsertionActive                                                                 = false;
    private boolean                              requestUnpackingActive                                                                = false;
    // private boolean prefixActive = false;
    private boolean                              responseUnpackingActive                                                               = false;
    // private boolean matchReplaceActive = false;
    // private boolean extractNewReplaceStringFromResponseActive = false;
    private boolean                              debugModeActive                                                                       = false;
    private boolean                              isDropRequestActive                                                                   = false;
    // private boolean
    // relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest = true;
    private boolean                              relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest                   = true;
    private String                               selectedLanguage                                                                      = "en";
    private String                               workingDirectory                                                                      = "";
    // private final String regexDropdownListElementsSeperator =
    // "___SEPERATORelementsSEPERATOR___";
    // private final String regexDropdownListPairwiseSeperator =
    // "___SEPERATORpairwiseSEPERATOR___";
    // private String regexDropdownList = "";
    private String                               lastEnteredIntruderComparerSearchRegex                                                = Messages
                                                                                                                                               .getString("regex.based.search_regex.test");
    private String[]                             payloadListForIntruderCompare                                                         = null;
    private final String                         ownLicenseString                                                                      = "#  "
                                                                                                                                               + this.getExtensionName()
                                                                                                                                               + " for Burpsuite\n#\n#    Copyright (c) 2017, Frank Block <gunziper@f-block.org>\n#\n#    All rights reserved.\n#\n#  Redistribution and use in source and binary forms, with or without modification, \n#    are permitted provided that the following conditions are met:\n#\n#     * Redistributions of source code must retain the above copyright notice, this \n#     list of conditions and the following disclaimer.\n#   * Redistributions in binary form must reproduce the above copyright notice, \n#       this list of conditions and the following disclaimer in the documentation \n#       and/or other materials provided with the distribution.\n#     * The names of the contributors may not be used to endorse or promote products \n#         derived from this software without specific prior written permission.\n#\n#  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" \n#   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE \n#   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE \n#  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE \n#   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL \n#   DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR \n#  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER \n#  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, \n#   OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE \n#   OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n\n";
    private final String                         foreignLicenseString                                                                  = "google-diff-match-patch from http://code.google.com/p/google-diff-match-patch/\n\nDiff Match and Patch\n \n Copyright 2006 Google Inc.\n http://code.google.com/p/google-diff-match-patch\n \n Licensed under the Apache License, Version 2.0 (the \"License\");\n you may not use this file except in compliance with the License.\n You may obtain a copy of the License at\n \n http://www.apache.org/licenses/LICENSE-2.0\n \n Unless required by applicable law or agreed to in writing, software\n distributed under the License is distributed on an \"AS IS\" BASIS,\n WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n See the License for the specific language governing permissions and\n limitations under the License.\n";
    private final String                         foreignLicenseString2                                                                 = "java-diff-utils  from http://code.google.com/p/java-diff-utils/ \n\n   Copyright 2010 Dmitry Naumenko (dm.naumenko@gmail.com)\n\n   Licensed under the Apache License, Version 2.0 (the \"License\");\n   you may not use this file except in compliance with the License.\n   You may obtain a copy of the License at\n\n       http://www.apache.org/licenses/LICENSE-2.0\n\n   Unless required by applicable law or agreed to in writing, software\n   distributed under the License is distributed on an \"AS IS\" BASIS,\n   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n   See the License for the specific language governing permissions and\n   limitations under the License.\n\n\nMyersDiff, written by Juanco Anez, licensed under the Apache Software License, Version 1.1";
    private String                               intruderComparerExclusionRegex                                                        = "";
    private final String                         dummyStringForSaveFileToPreventEmptyLines                                             = "DumMyString_ToReplace-EmptyLines";
    // private boolean
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest =
    // false;
    private boolean                              doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest            = false;
    private boolean                              doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction = false;
    // private boolean
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction
    // = false;
    private boolean                              doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking   = false;
    private boolean                              doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking    = false;
    private boolean                              doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking                 = false;
    private boolean                              includeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames                       = true;
    private boolean                              includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames                         = true;
    private boolean                              includeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames                        = true;
    private boolean                              includeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames                      = true;
    private boolean                              useMarkupOnSaveAllSelectedItemsWithIncrementingNames                                = true;
    private boolean                              includeUrlOnSaveAllSelectedItemsWithIncrementingNames                                 = true;
    private boolean                              includeMarkedRequestPartsOnSavePoc                                                    = true;
    private boolean                              includeMarkedResponsePartsOnSavePoc                                                   = true;
    private String                               regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames            = "";
    private String                               basenameForSaveAllSelectedItemsWithIncrementingNames                                  = "reqs_resps";
    private final String                         topRequestDelimeterString                                                             = "======================= New Message =======================\n";
    private final String                         bottomRequestDelimeterString                                                          = "===========================================================\n";
    private ArrayList<MatchReplaceSettings>      matchReplaceSettingsArray                                                             = new ArrayList<MatchReplaceSettings>();
    private final String                         allToolsString                                                                        = "proxy,repeater,intruder,scanner,sequencer,spider,suite";

    private Map<Integer, String>                 scannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues                      = new HashMap<Integer, String>();
    private int                                  sessionHandlingProduceDelayTimeInMillis                                               = 10000;
    private final int                            scannerApiScanIssueTypeIdentifierForSQLInjection                                      = 1049088;
    private final int                            scannerApiScanIssueTypeIdentifierForXMLInjection                                      = 1050368;
    private final int                            scannerApiScanIssueTypeIdentifierForXSSstored                                         = 2097408;
    private final int                            scannerApiScanIssueTypeIdentifierForSQLstatementInReqParam                            = 4195456;
    private final int                            scannerApiScanIssueTypeIdentifierForFilePathtraversal                                 = 1049344;
    private final int                            scannerApiScanIssueTypeIdentifierForXSSreflected                                      = 2097920;


    private final int                            scannerApiScanIssueTypeIdentifierForOpenRedirect                                      = 5243136;

    private boolean                              proceedOnMoreThanTenMessages                                                          = false;
    private boolean                              changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames                            = false;


    private boolean                              cancelButtonPressedOnSafetyQuestionDialog                                             = false;
    private static Variables                     variablesInstance                                                                     = null;

    private final ArrayList<AbstractDeEncrypter> deEncrypterArraylist                                                                  = new ArrayList<AbstractDeEncrypter>();

    private boolean                              includeResponsibleParametersCheckbox                                                  = true;

    private boolean                              includePocSeparators                                                                  = true;
    private boolean                              appendToPocFile                                                                       = false;
    private ResendRequests                       resendRequestsInstance                                                                = new ResendRequests();
    private boolean                              uniqueModeIncludeParamValues                                                          = false;

    private boolean                              uniqueModeIncludeCookies                                                              = false;
    private boolean                              uniqueModeIncludeCookieValues                                                         = false;
    private boolean                              uniqueModeIgnoreHost                                                                  = false;
    private boolean                              uniqueModeIgnoreProtocol                                                              = false;
    private boolean                              uniqueModeIgnoreHttpMethod                                                            = false;
    private boolean                              uniqueModeIgnoreForExcludedParametersOnlyTheirValue                                   = false;
    private boolean                              uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding                       = false;
    private String                               uniqueModeAmpersandSeparatedValuesForParameterExclusion                               = "";
    private RegexDropdownMap                     intruderComparerRegexdropdownHashmap                                                  = new RegexDropdownMap();
    private RegexDropdownMap                     gComparerRegexdropdownHashmap                                                         = new RegexDropdownMap();
    private RegexDropdownMap                     intruderComparerExclusionRegexdropdownHashmap                                         = new RegexDropdownMap();
    private RegexDropdownMap                     savePocRegexdropdownHashmap                                                           = new RegexDropdownMap();
    private RegexDropdownMap                     regextestRegexdropdownHashmap                                                         = new RegexDropdownMap();
    private final ComparerWindowSettings         comparerWindowSettings                                                                = new ComparerWindowSettings();
    private static final long                    serialVersionUID                                                                      = 10L;



    private Variables() {

    }

    /**
     * @return the alltoolsstring
     */
    public String getAlltoolsstring() {

        return this.allToolsString;
    }


    /**
     * @return the basenameForSaveAllSelectedItemsWithIncrementingNames
     */
    public String getBasenameForSaveAllSelectedItemsWithIncrementingNames() {

        return this.basenameForSaveAllSelectedItemsWithIncrementingNames;
    }


    /**
     * @return the bottomrequestdelimeterstring
     */
    public String getBottomrequestdelimeterstring() {

        return this.bottomRequestDelimeterString;
    }


    /**
     * @return the comparerWindowSettings
     */
    public ComparerWindowSettings getComparerWindowSettings() {

        return this.comparerWindowSettings;
    }


    /**
     * @return the csvDoingString
     */
    public String getCsvDoingString() {

        return this.csvDoingString;
    }


    /**
     * @return the csvResponseDoingString
     */
    public String getCsvResponseDoingString() {

        return this.csvResponseDoingString;
    }


    /**
     * @return the debugText
     */
    public String getDebugText() {

        return this.debugText;
    }


    public ArrayList<AbstractDeEncrypter> getDeEncrypterArraylist() {

        return this.deEncrypterArraylist;
    }

    /**
     * @return the dropRequestRegex
     */
    public String getDropRequestRegex() {

        return this.dropRequestRegex;
    }



    /**
     * @return the dummystringforsavefiletopreventemptylines
     */
    public String getDummystringforsavefiletopreventemptylines() {

        return this.dummyStringForSaveFileToPreventEmptyLines;
    }




    /**
     * @return the foreignLicenseString
     */
    public String getForeignLicenseString() {

        return this.foreignLicenseString;
    }


    /**
     * @return the foreignLicenseString2
     */
    public String getForeignLicenseString2() {

        return this.foreignLicenseString2;
    }


    /**
     * @return the gComparerRegexdropdownHashmap
     */
    public RegexDropdownMap getgComparerRegexdropdownHashmap() {

        return this.gComparerRegexdropdownHashmap;
    }


    /**
     * @return the intruderComparerExclusionRegex
     */
    public String getIntruderComparerExclusionRegex() {

        return this.intruderComparerExclusionRegex;
    }


    /**
     * @return the intruderComparerExclusionRegexdropdownHashmap
     */
    public RegexDropdownMap getIntruderComparerExclusionRegexdropdownHashmap() {

        return this.intruderComparerExclusionRegexdropdownHashmap;
    }


    /**
     * @return the intruderComparerRegexdropdownHashmap
     */
    public RegexDropdownMap getIntruderComparerRegexdropdownHashmap() {

        return this.intruderComparerRegexdropdownHashmap;
    }


    /**
     * @return the lastEnteredIntruderComparerSearchRegex
     */
    public String getLastEnteredIntruderComparerSearchRegex() {

        return this.lastEnteredIntruderComparerSearchRegex;
    }


    /**
     * @return the matchReplaceSettingsArray
     */
    public ArrayList<MatchReplaceSettings> getMatchReplaceSettingsArray() {

        return this.matchReplaceSettingsArray;
    }


    /**
     * @return the numberOfDebugLines
     */
    public int getNumberOfDebugLines() {

        return this.numberOfDebugLines;
    }


    /**
     * @return the ownLicenseString
     */
    public String getOwnLicenseString() {

        return this.ownLicenseString;
    }


    /**
     * @return the payloadListForIntruderCompare
     */
    public String[] getPayloadListForIntruderCompare() {

        return this.payloadListForIntruderCompare;
    }



    /**
     * @return the processingsString
     */
    public String getProcessingsString() {

        return this.processingsString;
    }


    /**
     * @return the regex
     */
    public String getRegex() {

        return this.regex;
    }


    /**
     * @return the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *         regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames
     */
    public String getRegexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @return the regexMarkingRequestpartForUnpacking
     */
    public String getRegexMarkingRequestpartForUnpacking() {

        return this.regexMarkingRequestpartForUnpacking;
    }


    /**
     * @return the regexMarkingResponsepartForUnpacking
     */
    public String getRegexMarkingResponsepartForUnpacking() {

        return this.regexMarkingResponsepartForUnpacking;
    }

    /**
     * @return the regextestRegexdropdownHashmap
     */
    public RegexDropdownMap getRegextestRegexdropdownHashmap() {

        return this.regextestRegexdropdownHashmap;
    }

    /**
     * @return the relevantMessageRegexForStaticResponseExtraction
     */
    public String getRelevantMessageRegexForStaticResponseExtraction() {

        return this.relevantMessageRegexForStaticResponseExtraction;
    }


    /**
     * @return the relevantResponseRegex
     */
    public String getRelevantResponseRegex() {

        return this.relevantResponseRegex;
    }


    /**
     * @return the relevantResquestRegex
     */
    public String getRelevantResquestRegex() {

        return this.relevantResquestRegex;
    }


    /**
     * @return the relevantStaticInsertionRequestRegex
     */
    public String getRelevantStaticInsertionRequestRegex() {

        return this.relevantStaticInsertionRequestRegex;
    }

    /**
     * @return the relevantUnpackedRequestRegex
     */
    public String getRelevantUnpackedRequestRegex() {

        return this.relevantUnpackedRequestRegex;
    }

    /**
     * @return the requestToolName
     */
    public String getRequestToolName() {

        return this.requestToolName;
    }

    /**
     * @return the resendRequestsInstance
     */
    public ResendRequests getResendRequestsInstance() {

        return this.resendRequestsInstance;
    }

    /**
     * @return the responseToolName
     */
    public String getResponseToolName() {

        return this.responseToolName;
    }


    /**
     * @return the savePocRegexdropdownHashmap
     */
    public RegexDropdownMap getSavePocRegexdropdownHashmap() {

        return this.savePocRegexdropdownHashmap;
    }


    /**
     * @return the scannerApiScanIssueTypeIdentifierForFilePathtraversal
     */
    public int getScannerApiScanIssueTypeIdentifierForFilePathtraversal() {

        return this.scannerApiScanIssueTypeIdentifierForFilePathtraversal;
    }

    /**
     * @return the scannerApiScanIssueTypeIdentifierForOpenRedirect
     */
    public int getScannerApiScanIssueTypeIdentifierForOpenRedirect() {

        return this.scannerApiScanIssueTypeIdentifierForOpenRedirect;
    }

    /**
     * @return the scannerApiScanIssueTypeIdentifierForSQLInjection
     */
    public int getScannerApiScanIssueTypeIdentifierForSQLInjection() {

        return this.scannerApiScanIssueTypeIdentifierForSQLInjection;
    }

    /**
     * @return the scannerApiScanIssueTypeIdentifierForSQLstatementInReqParam
     */
    public int getScannerApiScanIssueTypeIdentifierForSQLstatementInReqParam() {

        return this.scannerApiScanIssueTypeIdentifierForSQLstatementInReqParam;
    }


    /**
     * @return the scannerApiScanIssueTypeIdentifierForXMLInjection
     */
    public int getScannerApiScanIssueTypeIdentifierForXMLInjection() {

        return this.scannerApiScanIssueTypeIdentifierForXMLInjection;
    }

    /**
     * @return the scannerApiScanIssueTypeIdentifierForXSSreflected
     */
    public int getScannerApiScanIssueTypeIdentifierForXSSreflected() {

        return this.scannerApiScanIssueTypeIdentifierForXSSreflected;
    }

    // /**
    // * @return the prefix
    // */
    // public String getPrefix() {
    //
    // return this.prefix;
    // }

    /**
     * @return the scannerApiScanIssueTypeIdentifierForXSSstored
     */
    public int getScannerApiScanIssueTypeIdentifierForXSSstored() {

        return this.scannerApiScanIssueTypeIdentifierForXSSstored;
    }

    // /**
    // * @return the matchReplaceAction
    // */
    // public int getMatchReplaceAction() {
    //
    // return this.matchReplaceAction;
    // }

    /**
     * @return the
     *         scannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues
     */
    public Map<Integer, String> getScannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues() {

        return this.scannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues;
    }

    /**
     * @return the selectedLanguage
     */
    public String getSelectedLanguage() {

        return this.selectedLanguage;
    }


    /**
     * @return the sessionHandlingProduceDelayTimeInMillis
     */
    public int getSessionHandlingProduceDelayTimeInMillis() {

        return this.sessionHandlingProduceDelayTimeInMillis;
    }

    /**
     * @return the staticInsertionAction
     */
    public int getStaticInsertionAction() {

        return this.staticInsertionAction;
    }

    /**
     * @return the staticInsertMarkerString
     */
    public String getStaticInsertMarkerString() {

        return this.staticInsertMarkerString;
    }


    /**
     * @return the staticInsertResponseExtractionRegex
     */
    public String getStaticInsertResponseExtractionRegex() {

        return this.staticInsertResponseExtractionRegex;
    }


    /**
     * @return the staticInsertString
     */
    public String getStaticInsertString() {

        return this.staticInsertString;
    }


    /**
     * @return the staticInsertToolname
     */
    public String getStaticInsertToolname() {

        return this.staticInsertToolname;
    }

    /**
     * @return the staticResponseExtractionMatchingNumber
     */
    public String getStaticResponseExtractionMatchingNumber() {

        return this.staticResponseExtractionMatchingNumber;
    }

    /**
     * @return the toprequestdelimeterstring
     */
    public String getToprequestdelimeterstring() {

        return this.topRequestDelimeterString;
    }


    /**
     * @return the uniqueModeAmpersandSeparatedValuesForParameterExclusion
     */
    public String getUniqueModeAmpersandSeparatedValuesForParameterExclusion() {

        return this.uniqueModeAmpersandSeparatedValuesForParameterExclusion;
    }

    public String getExtensionName() {
        String name = "gunziper";
        Package pkg = getClass().getPackage();
        if (pkg != null) {
            name = pkg.getImplementationTitle();
        }
        return name + " " + getVersionNumber();
    }
    
    public String getVersionNumber() {
        Package pkg = getClass().getPackage();
        if (pkg == null) {
            return "unknown";
        } else {
            return pkg.getImplementationVersion();
        }
    }

    // /**
    // * @return the regexForNewReplaceStringFromResponseExtraction
    // */
    // public String getRegexForNewReplaceStringFromResponseExtraction()
    // {
    //
    // return
    // this.regexForNewReplaceStringFromResponseExtraction;
    // }

    /**
     * @return the savePocActualBasedir
     */
    public String getWorkingDirectory() {

        return this.workingDirectory;
    }

    /**
     * @return the appendToPocFile
     */
    public boolean isAppendToPocFile() {

        return this.appendToPocFile;
    }

    // /**
    // * @return the relevantMessageRegexForReplaceResponseExtraction
    // */
    // public String
    // getRelevantMessageRegexForReplaceResponseExtraction() {
    //
    // return
    // this.relevantMessageRegexForReplaceResponseExtraction;
    // }

    /**
     * @return the cancelButtonPressedOnSafetyQuestionDialog
     */
    public boolean isCancelButtonPressedOnSafetyQuestionDialog() {

        return this.cancelButtonPressedOnSafetyQuestionDialog;
    }

    // /**
    // * @return the relevantReplaceRequestRegex
    // */
    // public String getRelevantReplaceRequestRegex() {
    //
    // return this.relevantReplaceRequestRegex;
    // }

    /**
     * This variable is used to control whether or not the application logic
     * should change the save mode of save poc to incrementing filenames
     * when more than 10 messages were selected
     *
     * @return the changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames
     */
    public boolean isChangeSaveModeOnMoreThanTenMessagesToIncrementingFilenames() {

        return this.changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames;
    }


    /**
     * @return the debugModeActive
     */
    public boolean isDebugModeActive() {

        return this.debugModeActive;
    }


    /**
     * @return the isDropRequestActive
     */
    public boolean isDropRequestActive() {

        return this.isDropRequestActive;
    }


    // /**
    // * @return the replaceAction
    // */
    // public int getReplaceAction() {
    //
    // return this.replaceAction;
    // }

    //
    // /**
    // * @return the replaceResponseExtractionMatchingNumber
    // */
    // public String getReplaceResponseExtractionMatchingNumber() {
    //
    // return this.replaceResponseExtractionMatchingNumber;
    // }


    // /**
    // * @return the prefixActive
    // */
    // public boolean isPrefixActive() {
    //
    // return this.prefixActive;
    // }


    // /**
    // * @return the replaceString
    // */
    // public String getReplaceString() {
    //
    // return this.replaceString;
    // }


    // /**
    // * @return the replaceToolName
    // */
    // public String getReplaceToolName() {
    //
    // return this.replaceToolName;
    // }


    // /**
    // * @return the grepNewReplaceStringFromResponse
    // */
    // public boolean isExtractNewReplaceStringFromResponseActive() {
    //
    // return this.extractNewReplaceStringFromResponseActive;
    // }


    /**
     * @return the includeMarkedRequestPartsOnSavePoc
     */
    public boolean isIncludeMarkedRequestPartsOnSavePoc() {

        return this.includeMarkedRequestPartsOnSavePoc;
    }

    // public Object[] getRegexDropDownArray() {
    //
    // return this.getHashmapFromString(
    // this.getRegexDropdownList(),
    // this.getRegexDropdownListElementsSeperator(),
    // this.getRegexDropdownListPairwiseSeperator())
    // .keySet().toArray();
    // }





    /**
     * @return the includeMarkedResponsePartsOnSavePoc
     */
    public boolean isIncludeMarkedResponsePartsOnSavePoc() {

        return this.includeMarkedResponsePartsOnSavePoc;
    }


    /**
     * @return the includePocSeparators
     */
    public boolean isIncludePocSeparators() {

        return this.includePocSeparators;
    }


    /**
     * @return the includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames
     */
    public boolean isIncludeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames;
    }


    /**
     * @return the useRequestsOnSaveAllSelectedItemsWithIncrementingNames
     */
    public boolean isIncludeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.includeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames;
    }


    /**
     * @return the useResponsesOnSaveAllSelectedItemsWithIncrementingNames
     */
    public boolean isIncludeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.includeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames;
    }


    /**
     * @return the includeHeaderOnSaveAllSelectedItemsWithIncrementingNames
     */
    public boolean isIncludeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.includeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames;
    }


    /**
     * @return the includeResponsibleParametersCheckbox
     */
    public boolean isIncludeResponsibleParametersCheckbox() {

        return this.includeResponsibleParametersCheckbox;
    }

    // /**
    // * @param prefix
    // * the prefix to set
    // */
    // public void setPrefix(String prefix) {
    //
    // this.prefix = prefix;
    // }

    // /**
    // * @param prefixActive
    // * the prefixActive to set
    // */
    // public void setPrefixActive(boolean prefixActive) {
    //
    // this.prefixActive = prefixActive;
    // }

    /**
     * @return the includeUrlOnSaveAllSelectedItemsWithIncrementingNames
     */
    public boolean isIncludeUrlOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.includeUrlOnSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @return the useMarkupOnSaveAllSelectedItemsWithIncrementingNames
     */
    public boolean isUseMarkupOnSaveAllSelectedItemsWithIncrementingNames() {

        return this.useMarkupOnSaveAllSelectedItemsWithIncrementingNames;
    }
    /**
     * This variable is used to control whether or not the application logic
     * should proceed with
     * - send requests to repeater
     * - send requests to intruder
     *
     * when more than 10 messages were selected
     *
     * @return the proceedOnMoreThanTenMessages
     */
    public boolean isProceedOnMoreThanTenMessages() {

        return this.proceedOnMoreThanTenMessages;
    }

    /**
     * @return
     *         thedoesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking
     */
    public boolean isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking() {

        return this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking;
    }

    // /**
    // * @param grepNewReplaceStringFromResponse
    // * the grepNewReplaceStringFromResponse to set
    // */
    // public void setExtractNewReplaceStringFromResponseActive(
    // boolean extractNewReplaceStringFromResponseActive) {
    //
    // this.extractNewReplaceStringFromResponseActive =
    // extractNewReplaceStringFromResponseActive;
    // }

    /**
     * @return the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *         doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpacking
     */
    public boolean isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking() {

        return this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking;
    }

    /**
     * @return the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *         doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking
     */
    public boolean isRegexmatchIndicatingInrelevanceOnCurrentMessageForResponseunpacking() {

        return this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking;
    }

    /**
     * @return the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *         doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest
     */
    public boolean isRegexmatchIndicatingInrelevanceOnCurrentMessageForStaticinsertionRequest() {

        return this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest;
    }

    /**
     * @return
     *         thedoesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction
     */
    public boolean isRegexmatchIndicatingInrelevanceOnCurrentMessageForStaticinsertionResponseextraction() {

        return this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction;
    }

    /**
     * @return the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *         relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest
     */
    public boolean isRelevantMessageRegexForInsertionResponseExtractionIdentifiesRequest() {

        return this.relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest;
    }

    /**
     * @return the requestUnpackingActive
     */
    public boolean isRequestUnpackingActive() {

        return this.requestUnpackingActive;
    }

    /**
     * @return the responseUnpackingActive
     */
    public boolean isResponseUnpackingActive() {

        return this.responseUnpackingActive;
    }

    /**
     * @return the staticInsertionActive
     */
    public boolean isStaticInsertionActive() {

        return this.staticInsertionActive;
    }

    /**
     * @return the uniqueModeIgnoreForExcludedParametersOnlyTheirValue
     */
    public boolean isUniqueModeIgnoreForExcludedParametersOnlyTheirValue() {

        return this.uniqueModeIgnoreForExcludedParametersOnlyTheirValue;
    }

    /**
     * @return the uniqueModeIgnoreHost
     */
    public boolean isUniqueModeIgnoreHost() {

        return this.uniqueModeIgnoreHost;
    }

    // /**
    // * @return the matchReplaceActive
    // */
    // public boolean isMatchReplaceActive() {
    //
    // return this.matchReplaceActive;
    // }

    /**
     * @return the uniqueModeIgnoreHttpMethod
     */
    public boolean isUniqueModeIgnoreHttpMethod() {

        return this.uniqueModeIgnoreHttpMethod;
    }

    /**
     * @return the uniqueModeIgnorePort
     */
    public boolean isUniqueModeIgnoreProtocol() {

        return this.uniqueModeIgnoreProtocol;
    }

    // /**
    // * @return the
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest
    // */
    // public boolean
    // isRegexmatchIndicatingInrelevanceOnCurrentMessageForMatchreplaceRequest()
    // {
    //
    // return
    // this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest;
    // }

    // /**
    // * @return
    // thedoesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction
    // */
    // public boolean
    // isRegexmatchIndicatingInrelevanceOnCurrentMessageForMatchreplaceResponseextraction()
    // {
    //
    // return
    // this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction;
    // }

    /**
     * @return the uniqueModeIncludeCookies
     */
    public boolean isUniqueModeIncludeCookies() {

        return this.uniqueModeIncludeCookies;
    }

    /**
     * @return the uniqueModeIncludeCookieValues
     */
    public boolean isUniqueModeIncludeCookieValues() {

        return this.uniqueModeIncludeCookieValues;
    }

    /**
     * @return the
     *         uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding
     */
    public boolean isUniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding() {

        return this.uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding;
    }

    /**
     * @return the uniqueModeIncludeParamValues
     */
    public boolean isUniqueModeIncludeParamValues() {

        return this.uniqueModeIncludeParamValues;
    }

    // /**
    // * @return the
    // * relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest
    // */
    // public boolean
    // isRelevantMessageRegexForReplaceResponseExtractionIdentifiesRequest() {
    //
    // return
    // this.relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest;
    // }

    // public void loadPayloadListForIntruderCompareFromSpecifiedFile() {
    //
    // this.payloadListForIntruderCompare = Library
    // .getPayloadStringArray(this.payloadListFilepath);
    // }

    public void loadPayloadListForIntruderCompareFromSpecifiedFile(File file) {

        this.payloadListForIntruderCompare = Library
                .getPayloadStringArray(file);
    }

    /**
     * @param appendToPocFile
     *            the appendToPocFile to set
     */
    public void setAppendToPocFile(boolean appendToPocFile) {

        this.appendToPocFile = appendToPocFile;
    }

    /**
     * @param basenameForSaveAllSelectedItemsWithIncrementingNames
     *            the basenameForSaveAllSelectedItemsWithIncrementingNames to
     *            set
     */
    public void setBasenameForSaveAllSelectedItemsWithIncrementingNames(
            String basenameForSaveAllSelectedItemsWithIncrementingNames) {

        this.basenameForSaveAllSelectedItemsWithIncrementingNames = basenameForSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @param cancelButtonPressedOnSafetyQuestionDialog
     *            the cancelButtonPressedOnSafetyQuestionDialog to set
     */
    public void setCancelButtonPressedOnSafetyQuestionDialog(
            boolean cancelButtonPressedOnSafetyQuestionDialog) {

        this.cancelButtonPressedOnSafetyQuestionDialog = cancelButtonPressedOnSafetyQuestionDialog;
    }

    /**
     * This variable is used to control whether or not the application logic
     * should change the save mode of save poc to incrementing filenames
     * when more than 10 messages were selected
     *
     * @param changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames
     *            the changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames
     *            to set
     */
    public void setChangeSaveModeOnMoreThanTenMessagesToIncrementingFilenames(
            boolean changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames) {

        this.changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames = changeSaveModeOnMoreThanTenMessagesToIncrementingFilenames;
    }

    /**
     * @param csvDoingString
     *            the csvDoingString to set
     */
    public void setCsvDoingString(String csvDoingString) {

        this.csvDoingString = csvDoingString;
    }

    /**
     * @param csvResponseDoingString
     *            the csvResponseDoingString to set
     */
    public void setCsvResponseDoingString(String csvResponseDoingString) {

        this.csvResponseDoingString = csvResponseDoingString;
    }


    /**
     * @param debugModeActive
     *            the debugModeActive to set
     */
    public void setDebugModeActive(boolean debugModeActive) {

        this.debugModeActive = debugModeActive;
    }

    /**
     * @param debugText
     *            the debugText to set
     */
    public void setDebugText(String debugText) {

        this.debugText = debugText;
    }

    /**
     * @param isDropRequestActive
     *            the isDropRequestActive to set
     */
    public void setDropRequestActive(boolean isDropRequestActive) {

        this.isDropRequestActive = isDropRequestActive;
    }

    /**
     * @param dropRequestRegex
     *            the dropRequestRegex to set
     */
    public void setDropRequestRegex(String dropRequestRegex) {

        this.dropRequestRegex = dropRequestRegex;
    }

    /**
     * @param gComparerRegexdropdownHashmap
     *            the gComparerRegexdropdownHashmap to set
     */
    public void setgComparerRegexdropdownHashmap(
            RegexDropdownMap gComparerRegexdropdownHashmap) {

        this.gComparerRegexdropdownHashmap = gComparerRegexdropdownHashmap;
    }

    /**
     * @param includeMarkedRequestPartsOnSavePoc
     *            the includeMarkedRequestPartsOnSavePoc to set
     */
    public void setIncludeMarkedRequestPartsOnSavePoc(
            boolean includeMarkedRequestPartsOnSavePoc) {

        this.includeMarkedRequestPartsOnSavePoc = includeMarkedRequestPartsOnSavePoc;
    }

    /**
     * @param includeMarkedResponsePartsOnSavePoc
     *            the includeMarkedResponsePartsOnSavePoc to set
     */
    public void setIncludeMarkedResponsePartsOnSavePoc(
            boolean includeMarkedResponsePartsOnSavePoc) {

        this.includeMarkedResponsePartsOnSavePoc = includeMarkedResponsePartsOnSavePoc;
    }

    /**
     * @param includePocSeparators
     *            the includePocSeparators to set
     */
    public void setIncludePocSeparators(boolean includePocSeparators) {

        this.includePocSeparators = includePocSeparators;
    }

    /**
     * @param includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames
     *            the
     *            includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames
     *            to set
     */
    public void setIncludeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames(
            boolean includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames) {

        this.includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames = includeRequestBodyOnSaveAllSelectedItemsWithIncrementingNames;
    }


    /**
     * @param useRequestsOnSaveAllSelectedItemsWithIncrementingNames
     *            the useRequestsOnSaveAllSelectedItemsWithIncrementingNames to
     *            set
     */
    public void setIncludeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames(
            boolean useRequestsOnSaveAllSelectedItemsWithIncrementingNames) {

        this.includeRequestHeaderOnSaveAllSelectedItemsWithIncrementingNames = useRequestsOnSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @param useResponsesOnSaveAllSelectedItemsWithIncrementingNames
     *            the useResponsesOnSaveAllSelectedItemsWithIncrementingNames to
     *            set
     */
    public void setIncludeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames(
            boolean useResponsesOnSaveAllSelectedItemsWithIncrementingNames) {

        this.includeResponseBodyOnSaveAllSelectedItemsWithIncrementingNames = useResponsesOnSaveAllSelectedItemsWithIncrementingNames;
    }

    // /**
    // * @param matchReplaceAction
    // * the matchReplaceAction to set
    // */
    // public void setMatchReplaceAction(int matchReplaceAction) {
    //
    // this.matchReplaceAction = matchReplaceAction;
    // }

    // /**
    // * @param matchReplaceActive
    // * the matchReplaceActive to set
    // */
    // public void setMatchReplaceActive(boolean matchReplaceActive) {
    //
    // this.matchReplaceActive = matchReplaceActive;
    // }

    /**
     * @param includeHeaderOnSaveAllSelectedItemsWithIncrementingNames
     *            the includeHeaderOnSaveAllSelectedItemsWithIncrementingNames
     *            to set
     */
    public void setIncludeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames(
            boolean includeHeaderOnSaveAllSelectedItemsWithIncrementingNames) {

        this.includeResponseHeaderOnSaveAllSelectedItemsWithIncrementingNames = includeHeaderOnSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @param includeResponsibleParametersCheckbox
     *            the includeResponsibleParametersCheckbox to set
     */
    public void setIncludeResponsibleParametersCheckbox(
            boolean includeResponsibleParametersCheckbox) {

        this.includeResponsibleParametersCheckbox = includeResponsibleParametersCheckbox;
    }

    /**
     * @param includeUrlOnSaveAllSelectedItemsWithIncrementingNames
     *            the includeUrlOnSaveAllSelectedItemsWithIncrementingNames to
     *            set
     */
    public void setIncludeUrlOnSaveAllSelectedItemsWithIncrementingNames(
            boolean includeUrlOnSaveAllSelectedItemsWithIncrementingNames) {

        this.includeUrlOnSaveAllSelectedItemsWithIncrementingNames = includeUrlOnSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @param intruderComparerExclusionRegex
     *            the intruderComparerExclusionRegex to set
     */
    public void setIntruderComparerExclusionRegex(
            String intruderComparerExclusionRegex) {

        this.intruderComparerExclusionRegex = intruderComparerExclusionRegex;
    }

    /**
     * @param intruderComparerExclusionRegexdropdownHashmap
     *            the intruderComparerExclusionRegexdropdownHashmap to set
     */
    public void setIntruderComparerExclusionRegexdropdownHashmap(
            RegexDropdownMap intruderComparerExclusionRegexdropdownHashmap) {

        this.intruderComparerExclusionRegexdropdownHashmap = intruderComparerExclusionRegexdropdownHashmap;
    }

    /**
     * @param intruderComparerRegexdropdownHashmap
     *            the intruderComparerRegexdropdownHashmap to set
     */
    public void setIntruderComparerRegexdropdownHashmap(
            RegexDropdownMap intruderComparerRegexdropdownHashmap) {

        this.intruderComparerRegexdropdownHashmap = intruderComparerRegexdropdownHashmap;
    }

    // /**
    // * @param regexForNewReplaceStringFromResponseExtraction
    // * the regexForNewReplaceStringFromResponseExtraction to set
    // */
    // public void setRegexForNewReplaceStringFromResponseExtraction(
    // String regexForNewReplaceStringFromResponseExtraction) {
    //
    // this.regexForNewReplaceStringFromResponseExtraction =
    // regexForNewReplaceStringFromResponseExtraction;
    // }

    /**
     * @param lastEnteredIntruderComparerSearchRegex
     *            the lastEnteredIntruderComparerSearchRegex to set
     */
    public void setLastEnteredIntruderComparerSearchRegex(
            String lastEnteredIntruderComparerSearchRegex) {

        this.lastEnteredIntruderComparerSearchRegex = lastEnteredIntruderComparerSearchRegex;
    }

    public void setMatchReplaceSettingsArray(
            ArrayList<MatchReplaceSettings> matchReplaceSettingsArray) {

        this.matchReplaceSettingsArray = matchReplaceSettingsArray;
    }

    // /**
    // * @param
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest
    // * the
    // * doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest
    // * to set
    // */
    // public void
    // setRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest(
    // boolean
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest)
    // {
    //
    // this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest
    // =
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceRequest;
    // }

    // /**
    // * @param
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction
    // *
    // thedoesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction
    // * to set
    // */
    // public void
    // setRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction(
    // boolean
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction)
    // {
    //
    // this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction
    // =
    // doesRegexmatchIndicateInrelevanceOnCurrentMessageForMatchreplaceResponseextraction;
    // }

    /**
     * @param numberOfDebugLines
     *            the numberOfDebugLines to set
     */
    public void setNumberOfDebugLines(int numberOfDebugLines) {

        this.numberOfDebugLines = numberOfDebugLines;
    }

    /**
     * @param payloadListForIntruderCompare
     *            the payloadListForIntruderCompare to set
     */
    public void setPayloadListForIntruderCompare(
            String[] payloadListForIntruderCompare) {

        this.payloadListForIntruderCompare = payloadListForIntruderCompare;
    }

    /**
     * This variable is used to control whether or not the application logic
     * should proceed with
     * - send requests to repeater
     * - send requests to intruder
     *
     * when more than 10 messages were selected
     *
     * @param proceedOnMoreThanTenMessages
     *            the proceedOnMoreThanTenMessages to set
     */
    public void setProceedOnMoreThanTenMessages(
            boolean proceedOnMoreThanTenMessages) {

        this.proceedOnMoreThanTenMessages = proceedOnMoreThanTenMessages;
    }

    /**
     * @param processingsString
     *            the processingsString to set
     */
    public void setProcessingsString(String processingsString) {

        this.processingsString = processingsString;
    }

    /**
     * @param regex
     *            the regex to set
     */
    public void setRegex(String regex) {

        this.regex = regex;
    }

    // /**
    // * @param relevantMessageRegexForReplaceResponseExtraction
    // * the relevantMessageRegexForReplaceResponseExtraction to set
    // */
    // public void setRelevantMessageRegexForReplaceResponseExtraction(
    // String relevantMessageRegexForReplaceResponseExtraction) {
    //
    // this.relevantMessageRegexForReplaceResponseExtraction
    // =
    // relevantMessageRegexForReplaceResponseExtraction;
    // }

    // /**
    // * @param
    // relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest
    // * the
    // *
    // *
    // *
    // * relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest
    // * to set
    // */
    // public void
    // setRelevantMessageRegexForReplaceResponseExtractionIdentifiesRequest(
    // boolean
    // relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest) {
    //
    // this.relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest
    // = relevantMessageRegexForReplaceResponseExtractionIdentifiesRequest;
    // }


    // /**
    // * @param relevantReplaceRequestRegex
    // * the relevantReplaceRequestRegex to set
    // */
    // public void setRelevantReplaceRequestRegex(
    // String relevantReplaceRequestRegex) {
    //
    // this.relevantReplaceRequestRegex =
    // relevantReplaceRequestRegex;
    // }

    /**
     * @param regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames
     *            the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *            regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames
     *            to set
     */
    public void setRegexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames(
            String regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames) {

        this.regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames = regexForExclusionOfMessagepartsOnSaveAllSelectedItemsWithIncrementingNames;
    }

    /**
     * @param regexMarkingRequestpartForUnpacking
     *            the regexMarkingRequestpartForUnpacking to set
     */
    public void setRegexMarkingRequestpartForUnpacking(
            String regexMarkingRequestpartForUnpacking) {

        this.regexMarkingRequestpartForUnpacking = regexMarkingRequestpartForUnpacking;
    }

    /**
     * @param regexMarkingResponsepartForUnpacking
     *            the regexMarkingResponsepartForUnpacking to set
     */
    public void setRegexMarkingResponsepartForUnpacking(
            String regexMarkingResponsepartForUnpacking) {

        this.regexMarkingResponsepartForUnpacking = regexMarkingResponsepartForUnpacking;
    }

    /**
     * @param doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking
     *            thedoesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking
     *            to set
     */
    public void setRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking(
            boolean doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking) {

        this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking = doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking;
    }

    // /**
    // * @param replaceAction
    // * the replaceAction to set
    // */
    // public void setReplaceAction(int replaceAction) {
    //
    // this.replaceAction = replaceAction;
    // }

    // /**
    // * @param replaceResponseExtractionMatchingNumber
    // * the replaceResponseExtractionMatchingNumber to set
    // */
    // public void setReplaceResponseExtractionMatchingNumber(
    // String replaceResponseExtractionMatchingNumber) {
    //
    // this.replaceResponseExtractionMatchingNumber =
    // replaceResponseExtractionMatchingNumber;
    // }

    // /**
    // * @param replaceString
    // * the replaceString to set
    // */
    // public void setReplaceString(String replaceString) {
    //
    // this.replaceString = replaceString;
    // }

    // /**
    // * @param replaceToolName
    // * the replaceToolName to set
    // */
    // public void setReplaceToolName(String replaceToolName) {
    //
    // this.replaceToolName = replaceToolName;
    // }

    /**
     * @param doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpacking
     *            the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *            doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpacking
     *            to set
     */
    public void setRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking(
            boolean doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpacking) {

        this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking = doesRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpacking;
    }

    /**
     * @param doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking
     *            the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *            doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking
     *            to set
     */
    public void setRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking(
            boolean doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking) {

        this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking = doesRegexmatchIndicateInrelevanceOnCurrentMessageForResponseunpacking;
    }

    /**
     * @param doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest
     *            the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *            doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest
     *            to set
     */
    public void setRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest(
            boolean doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest) {

        this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest = doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionRequest;
    }

    /**
     * @param doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction
     *            thedoesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction
     *            to set
     */
    public void setRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction(
            boolean doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction) {

        this.doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction = doesRegexmatchIndicateInrelevanceOnCurrentMessageForStaticinsertionResponseextraction;
    }

    /**
     * @param regextestRegexdropdownHashmap
     *            the regextestRegexdropdownHashmap to set
     */
    public void setRegextestRegexdropdownHashmap(
            RegexDropdownMap regextestRegexdropdownHashmap) {

        this.regextestRegexdropdownHashmap = regextestRegexdropdownHashmap;
    }

    /**
     * @param relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest
     *            the
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *            relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest
     *            to set
     */
    public void setRelevantMessageRegexForInsertionResponseExtractionIdentifiesRequest(
            boolean relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest) {

        this.relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest = relevantMessageRegexForInsertionResponseExtractionIdentifiesRequest;
    }

    /**
     * @param relevantMessageRegexForStaticResponseExtraction
     *            the relevantMessageRegexForStaticResponseExtraction to set
     */
    public void setRelevantMessageRegexForStaticResponseExtraction(
            String relevantMessageRegexForStaticResponseExtraction) {

        this.relevantMessageRegexForStaticResponseExtraction = relevantMessageRegexForStaticResponseExtraction;
    }

    /**
     * @param relevantResponseRegex
     *            the relevantResponseRegex to set
     */
    public void setRelevantResponseRegex(String relevantResponseRegex) {

        this.relevantResponseRegex = relevantResponseRegex;
    }

    /**
     * @param relevantResquestRegex
     *            the relevantResquestRegex to set
     */
    public void setRelevantResquestRegex(String relevantResquestRegex) {

        this.relevantResquestRegex = relevantResquestRegex;
    }

    /**
     * @param relevantStaticInsertionRequestRegex
     *            the relevantStaticInsertionRequestRegex to set
     */
    public void setRelevantStaticInsertionRequestRegex(
            String relevantStaticInsertionRequestRegex) {

        this.relevantStaticInsertionRequestRegex = relevantStaticInsertionRequestRegex;
    }

    /**
     * @param relevantUnpackedRequestRegex
     *            the relevantUnpackedRequestRegex to set
     */
    public void setRelevantUnpackedRequestRegex(
            String relevantUnpackedRequestRegex) {

        this.relevantUnpackedRequestRegex = relevantUnpackedRequestRegex;
    }

    /**
     * @param requestToolName
     *            the requestToolName to set
     */
    public void setRequestToolName(String requestToolName) {

        this.requestToolName = requestToolName;
    }

    /**
     * @param requestUnpackingActive
     *            the requestUnpackingActive to set
     */
    public void setRequestUnpackingActive(boolean requestUnpackingActive) {

        this.requestUnpackingActive = requestUnpackingActive;
    }

    /**
     * @param resendRequestsInstance
     *            the resendRequestsInstance to set
     */
    public void setResendRequestsInstance(ResendRequests resendRequestsInstance) {

        this.resendRequestsInstance = resendRequestsInstance;
    }

    /**
     * @param responseToolName
     *            the responseToolName to set
     */
    public void setResponseToolName(String responseToolName) {

        this.responseToolName = responseToolName;
    }

    /**
     * @param responseUnpacking
     *            the responseUnpacking to set
     */
    public void setResponseUnpackingActive(boolean responseUnpacking) {

        this.responseUnpackingActive = responseUnpacking;
    }

    /**
     * @param savePocRegexdropdownHashmap
     *            the savePocRegexdropdownHashmap to set
     */
    public void setSavePocRegexdropdownHashmap(
            RegexDropdownMap savePocRegexdropdownHashmap) {

        this.savePocRegexdropdownHashmap = savePocRegexdropdownHashmap;
    }

    public void setScannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues(
            Map<Integer, String> scannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues) {

        this.scannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues = scannerApiScanIssueTypeIdentifierMapRelevantForSavePocScanIssues;
    }

    /**
     * @param selectedLanguage
     *            the selectedLanguage to set
     */
    public void setSelectedLanguage(String selectedLanguage) {

        this.selectedLanguage = selectedLanguage;
    }

    /**
     * @param sessionHandlingProduceDelayTimeInMillis
     *            the sessionHandlingProduceDelayTimeInMillis to set
     */
    public void setSessionHandlingProduceDelayTimeInMillis(
            int sessionHandlingProduceDelayTimeInMillis) {

        this.sessionHandlingProduceDelayTimeInMillis = sessionHandlingProduceDelayTimeInMillis;
    }


    /**
     * @param staticInsertionAction
     *            the staticInsertionAction to set
     */
    public void setStaticInsertionAction(int staticInsertionAction) {

        this.staticInsertionAction = staticInsertionAction;
    }


    /**
     * @param staticInsertionActive
     *            the staticInsertionActive to set
     */
    public void setStaticInsertionActive(boolean staticInsertionActive) {

        this.staticInsertionActive = staticInsertionActive;
    }


    /**
     * @param staticInsertMarkerString
     *            the staticInsertMarkerString to set
     */
    public void setStaticInsertMarkerString(String staticInsertMarkerString) {

        this.staticInsertMarkerString = staticInsertMarkerString;
    }


    /**
     * @param staticInsertResponseExtractionRegex
     *            the staticInsertResponseExtractionRegex to set
     */
    public void setStaticInsertResponseExtractionRegex(
            String staticInsertResponseExtractionRegex) {

        this.staticInsertResponseExtractionRegex = staticInsertResponseExtractionRegex;
    }

    /**
     * @param staticInsertString
     *            the staticInsertString to set
     */
    public void setStaticInsertString(String staticInsertString) {

        this.staticInsertString = staticInsertString;
    }

    /**
     * @param staticInsertToolname
     *            the staticInsertToolname to set
     */
    public void setStaticInsertToolname(String staticInsertToolname) {

        this.staticInsertToolname = staticInsertToolname;
    }

    /**
     * @param staticResponseExtractionMatchingNumber
     *            the staticResponseExtractionMatchingNumber to set
     */
    public void setStaticResponseExtractionMatchingNumber(
            String staticResponseExtractionMatchingNumber) {

        this.staticResponseExtractionMatchingNumber = staticResponseExtractionMatchingNumber;
    }

    /**
     * @param uniqueModeAmpersandSeparatedValuesForParameterExclusion
     *            the uniqueModeAmpersandSeparatedValuesForParameterExclusion to
     *            set
     */
    public void setUniqueModeAmpersandSeparatedValuesForParameterExclusion(
            String uniqueModeAmpersandSeparatedValuesForParameterExclusion) {

        this.uniqueModeAmpersandSeparatedValuesForParameterExclusion = uniqueModeAmpersandSeparatedValuesForParameterExclusion;
    }

    /**
     * @param uniqueModeIgnoreForExcludedParametersOnlyTheirValue
     *            the uniqueModeIgnoreForExcludedParametersOnlyTheirValue to set
     */
    public void setUniqueModeIgnoreForExcludedParametersOnlyTheirValue(
            boolean uniqueModeIgnoreForExcludedParametersOnlyTheirValue) {

        this.uniqueModeIgnoreForExcludedParametersOnlyTheirValue = uniqueModeIgnoreForExcludedParametersOnlyTheirValue;
    }

    /**
     * @param uniqueModeIgnoreHost
     *            the uniqueModeIgnoreHost to set
     */
    public void setUniqueModeIgnoreHost(boolean uniqueModeIgnoreHost) {

        this.uniqueModeIgnoreHost = uniqueModeIgnoreHost;
    }

    /**
     * @param uniqueModeIgnoreHttpMethod
     *            the uniqueModeIgnoreHttpMethod to set
     */
    public void setUniqueModeIgnoreHttpMethod(boolean uniqueModeIgnoreHttpMethod) {

        this.uniqueModeIgnoreHttpMethod = uniqueModeIgnoreHttpMethod;
    }

    /**
     * @param uniqueModeIgnorePort
     *            the uniqueModeIgnorePort to set
     */
    public void setUniqueModeIgnoreProtocol(boolean uniqueModeIgnorePort) {

        this.uniqueModeIgnoreProtocol = uniqueModeIgnorePort;
    }

    /**
     * @param uniqueModeIncludeCookies
     *            the uniqueModeIncludeCookies to set
     */
    public void setUniqueModeIncludeCookies(boolean uniqueModeIncludeCookies) {

        this.uniqueModeIncludeCookies = uniqueModeIncludeCookies;
    }

    /**
     * @param uniqueModeIncludeCookieValues
     *            the uniqueModeIncludeCookieValues to set
     */
    public void setUniqueModeIncludeCookieValues(
            boolean uniqueModeIncludeCookieValues) {

        this.uniqueModeIncludeCookieValues = uniqueModeIncludeCookieValues;
    }

    /**
     * @param uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding
     *            the
     *            uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding
     *            to set
     */
    public void setUniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding(
            boolean uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding) {

        this.uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding = uniqueModeIncludeOnlyTheParametersFromTheListInsteadOfExcluding;
    }

    /**
     * @param uniqueModeIncludeParamValues
     *            the uniqueModeIncludeParamValues to set
     */
    public void setUniqueModeIncludeParamValues(
            boolean uniqueModeIncludeParamValues) {

        this.uniqueModeIncludeParamValues = uniqueModeIncludeParamValues;
    }

    /**
     * @param savePocActualBasedir
     *            the savePocActualBasedir to set
     */
    public void setWorkingDirectory(String workingDirectory) {

        this.workingDirectory = workingDirectory;
    }


}
