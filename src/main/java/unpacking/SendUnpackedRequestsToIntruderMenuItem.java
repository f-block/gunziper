/**
 * Erstellt am: Jun 25, 2013 10:14:31 AM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking;

import misc.DisplayText;
import misc.Messages;
import variables.Variables;
import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import burp.IHttpService;


public class SendUnpackedRequestsToIntruderMenuItem {

    public SendUnpackedRequestsToIntruderMenuItem(
            IBurpExtenderCallbacks mCallbacks, IExtensionHelpers helpers,
            IHttpRequestResponse[] messageInfo) {

        if (Variables.getInstance().isRequestUnpackingActive()) {
            try {
                IHttpService reqInfo;
                for (IHttpRequestResponse element:messageInfo) {
                    reqInfo = element.getHttpService();

                    mCallbacks.sendToIntruder(reqInfo.getHost(), reqInfo
                            .getPort(), reqInfo.getProtocol().equalsIgnoreCase(
                            "https"), UnpackingLibrary.getProcessedMessage(
                            element.getRequest(), Variables.getInstance()
                                    .getRegexMarkingRequestpartForUnpacking(),
                            true, true));
                }
            }catch (Exception e) {
                e.printStackTrace();
                new DisplayText(Messages.getString("errorOccured"), e
                        .toString());
            }
        }

    }
}