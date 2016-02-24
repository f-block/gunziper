/**
 * Erstellt am: Jun 25, 2013 10:16:56 AM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking;

import misc.DisplayText;
import misc.Library;
import misc.Messages;
import variables.Variables;
import burp.IHttpRequestResponse;


public class DisplayPackedMessage {

    public DisplayPackedMessage(IHttpRequestResponse[] messageInfo) {

        for (IHttpRequestResponse element:messageInfo) {
            try {
                if (Variables.getInstance().isRequestUnpackingActive()) {
                    new DisplayText(
                            "Packed Request",
                            Library
                                    .getStringFromBytearray(UnpackingLibrary
                                            .getProcessedMessage(
                                                    element.getRequest(),
                                                    Variables
                                                            .getInstance()
                                                            .getRegexMarkingRequestpartForUnpacking(),
                                                    true, false)));
                }

                if (Variables.getInstance().isResponseUnpackingActive()) {
                    new DisplayText(
                            "Packed Response",
                            Library
                                    .getStringFromBytearray(UnpackingLibrary
                                            .getProcessedMessage(
                                                    element.getResponse(),
                                                    Variables
                                                            .getInstance()
                                                            .getRegexMarkingRequestpartForUnpacking(),
                                                    false, false)));
                }
            }catch (Exception e) {
                e.printStackTrace();
                new DisplayText(Messages.getString("errorOccured"), e
                        .toString());
            }
        }
    }
}