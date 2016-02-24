/**
 * Erstellt am: Jan 12, 2013 6:18:52 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package matchreplace;

import misc.Messages;
import variables.Variables;
import burp.IHttpRequestResponse;
import burp.ISessionHandlingAction;



public class DelaySessionHandlingAction implements ISessionHandlingAction {

    /*
     * (non-Javadoc)
     * 
     * @see burp.ISessionHandlingAction#getActionName()
     */
    @Override
    public String getActionName() {

        return Messages.getString("produce.delay");
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
            Thread.sleep(Variables.getInstance()
                    .getSessionHandlingProduceDelayTimeInMillis());
        }catch (InterruptedException e) {
            // DO NOTHING
        }
    }

}
