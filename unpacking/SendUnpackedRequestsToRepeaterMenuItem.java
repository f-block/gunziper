/**
 * Erstellt am: Jun 25, 2013 9:51:23 AM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking;

import misc.DisplayText;
import misc.Messages;
import variables.Variables;
import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.IHttpService;


public class SendUnpackedRequestsToRepeaterMenuItem {

    public SendUnpackedRequestsToRepeaterMenuItem(
            IBurpExtenderCallbacks mCallbacks,
            IHttpRequestResponse[] messageInfo) {

        if (Variables.getInstance().isRequestUnpackingActive()) {
            try {
                IHttpService reqInfo;
                for (IHttpRequestResponse element:messageInfo) {
                    reqInfo = element.getHttpService();

                    mCallbacks.sendToRepeater(reqInfo.getHost(), reqInfo
                            .getPort(), reqInfo.getProtocol().equalsIgnoreCase(
                            "https"), UnpackingLibrary.getProcessedMessage(
                            element.getRequest(), Variables.getInstance()
                                    .getRegexMarkingRequestpartForUnpacking(),
                            true, true), "gunziper");


                }

            }catch (Exception e) {
                e.printStackTrace();
                new DisplayText(Messages.getString("errorOccured"), e
                        .toString());
            }
        }
    }
}
