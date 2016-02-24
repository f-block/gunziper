/**
 * Erstellt am: Jun 25, 2013 10:17:25 AM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking;

import misc.DisplayText;
import misc.Library;
import misc.Messages;
import variables.Variables;
import burp.IHttpRequestResponse;



public class DisplayUnpackedMessage {

    public DisplayUnpackedMessage(IHttpRequestResponse[] messageInfo) {

        for (IHttpRequestResponse element:messageInfo) {
            try {
                if (Variables.getInstance().isRequestUnpackingActive()) {
                    new DisplayText(
                            "Unpacked Request",
                            Library
                                    .getStringFromBytearray(UnpackingLibrary
                                            .getProcessedMessage(
                                                    element.getRequest(),
                                                    Variables
                                                            .getInstance()
                                                            .getRegexMarkingRequestpartForUnpacking(),
                                                    true, true)));
                }
                if (Variables.getInstance().isResponseUnpackingActive()) {
                    new DisplayText(
                            "Unpacked Response",
                            Library
                                    .getStringFromBytearray(UnpackingLibrary
                                            .getProcessedMessage(
                                                    element.getResponse(),
                                                    Variables
                                                            .getInstance()
                                                            .getRegexMarkingRequestpartForUnpacking(),
                                                    false, true)));
                }
            }catch (Exception e) {
                e.printStackTrace();
                new DisplayText(Messages.getString("errorOccured"), e
                        .toString());
            }
        }
    }
}
