/**
 * Erstellt am: Jul 8, 2013 8:47:23 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package matchreplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import misc.Library;
import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IParameter;
import burp.ISessionHandlingAction;


public class ResendRequests implements ISessionHandlingAction, Serializable {

    private IHttpRequestResponse[] requests                          = null;
    private int                    requestCounter                    = 0;
    private ArrayList<String>      payloadList                       = new ArrayList<String>();
    private ArrayList<Integer>     inScopeRequestParameterIdentifier = new ArrayList<Integer>();
    private List<IParameter>       parameterList                     = new ArrayList<IParameter>();
    private int                    parameterListCounter              = 0;
    private int                    currentPayloadCounter             = 0;


    /*
     * (non-Javadoc)
     * 
     * @see burp.ISessionHandlingAction#getActionName()
     */
    @Override
    public String getActionName() {

        return "Resend Request(s)";
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

        boolean parameterIsRelevant = false;
        String pre = "";
        String post = "";

        if (this.requestCounter == 0) {
            this.parameterList = BurpExtender.getIextensionHelper()
                    .analyzeRequest(
                            this.requests[this.requestCounter].getRequest())
                    .getParameters();
        }

        if (this.requests != null) {
            while (this.requestCounter < this.requests.length) {
                if (this.requests[this.requestCounter] != null) {
                    for (; this.parameterListCounter < this.parameterList
                            .size(); this.parameterListCounter++) {
                        parameterIsRelevant = false;

                        for (int curType:this.inScopeRequestParameterIdentifier) {
                            if (curType == this.parameterList.get(
                                    this.parameterListCounter).getType()) {
                                parameterIsRelevant = true;
                            }
                        }

                        if (parameterIsRelevant) {
                            if (this.currentPayloadCounter < this.payloadList
                                    .size()) {
                                if (this.payloadList.get(
                                        this.currentPayloadCounter).equals("")) {
                                    this.currentPayloadCounter++;
                                    currentRequest
                                            .setRequest(this.requests[this.requestCounter]
                                                    .getRequest());
                                    return;
                                }
                                String temp = Library
                                        .getStringFromBytearray(this.requests[this.requestCounter]
                                                .getRequest());
                                pre = temp.substring(0, this.parameterList.get(
                                        this.parameterListCounter)
                                        .getValueStart());
                                post = temp.substring(this.parameterList.get(
                                        this.parameterListCounter)
                                        .getValueEnd());
                                currentRequest
                                        .setRequest(Library
                                                .prepareMessageHeader(Library
                                                        .getBytearrayFromString(pre
                                                                + this.payloadList
                                                                        .get(this.currentPayloadCounter)
                                                                + post)));
                                this.currentPayloadCounter++;
                                return;
                            }
                            else {
                                this.currentPayloadCounter = 0;
                            }
                        }
                    }
                    this.parameterListCounter = 0;
                    this.requestCounter++;

                    if (this.requestCounter < this.requests.length) {
                        this.parameterList = BurpExtender.getIextensionHelper()
                                .analyzeRequest(
                                        this.requests[this.requestCounter]
                                                .getRequest()).getParameters();
                    }
                }
            }
        }
    }

    public void setInScopeRequestParameterIdentifier(
            ArrayList<Integer> inScopeRequestParameterIdentifier) {

        this.inScopeRequestParameterIdentifier = inScopeRequestParameterIdentifier;
    }

    public void setPayloadList(ArrayList<String> payloadList) {

        this.payloadList = payloadList;
    }

    /**
     * @param requests
     *            the requests to set
     */
    public void setRequests(IHttpRequestResponse[] requests) {

        this.requestCounter = 0;
        this.requests = requests;
    }


}
