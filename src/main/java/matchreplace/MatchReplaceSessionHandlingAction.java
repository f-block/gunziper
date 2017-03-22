/**
 * Erstellt am: Jan 12, 2013 6:15:12 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package matchreplace;

import misc.Library;
import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.ISessionHandlingAction;




public class MatchReplaceSessionHandlingAction implements
ISessionHandlingAction {

    private final MatchReplaceSettings currentMatchReplaceSettingsInstance;
    private String                     ActionName = "";

    public MatchReplaceSessionHandlingAction(
            MatchReplaceSettings matchReplaceSettingsInstance, String actionName) {

        this.currentMatchReplaceSettingsInstance = matchReplaceSettingsInstance;
        this.ActionName = actionName;

    }


    /*
     * (non-Javadoc)
     *
     * @see burp.ISessionHandlingAction#getActionName()
     */
    @Override
    public String getActionName() {

        return this.ActionName;
    }

    /*
     * (non-Javadoc)
     *
     * @see burp.ISessionHandlingAction#performAction(burp.IHttpRequestResponse,
     * burp.IHttpRequestResponse[])
     */
    @Override
    public void performAction(IHttpRequestResponse currentRequest,
            IHttpRequestResponse[] macroItems) {

        try {
            BurpExtender.updateReplaceStringIfAppropriate(true, currentRequest,
                    "");

            if (macroItems != null) {
                for (IHttpRequestResponse current:macroItems) {
                    BurpExtender.updateReplaceStringIfAppropriate(true,
                            current, "");
                }
            }


            byte[] tempRequest = null;
            tempRequest = MatchReplaceLibrary
                    .doReplacementAndInsertionWithSingleMatchReplaceSetting(
                            currentRequest.getRequest(),
                            this.currentMatchReplaceSettingsInstance, "", false);
            currentRequest.setRequest(tempRequest == null ? currentRequest
                    .getRequest():Library.prepareMessageHeader(tempRequest));

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
