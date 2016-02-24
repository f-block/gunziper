/**
 * Erstellt am: Jan 7, 2013 10:19:58 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking;

import java.awt.Component;

import misc.Library;
import misc.Messages;
import variables.Variables;
import burp.BurpExtender;
import burp.IMessageEditorTab;
import burp.ITextEditor;



public class GunziperTab implements IMessageEditorTab {

    private final ITextEditor txtInput;

    // private final IMessageEditorController controller;

    byte[]                    currentMessage = null;

    public GunziperTab(ITextEditor txtInput) {

        this.txtInput = txtInput;
        // this.controller = controller;
    }

    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#getMessage()
     */
    @Override
    public byte[] getMessage() {

        if (this.txtInput.isTextModified()) {
            if (Variables.getInstance().isRequestUnpackingActive()) {
                if (BurpExtender
                        .isRelevantMessage(
                                this.txtInput.getText(),
                                Variables.getInstance()
                                .getRelevantUnpackedRequestRegex(),
                                Variables.getInstance().getAlltoolsstring(),
                                "repeater",
                                Variables
                                .getInstance()
                                .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingAfterunpacking())) {

                    BurpExtender.addMessageToDebugOutput(Messages
                            .getString("current.request.gets.packed"));

                    return UnpackingLibrary.getProcessedMessage(this.txtInput
                            .getText(), Variables.getInstance()
                            .getRegexMarkingRequestpartForUnpacking(), true,
                            false);
                }
                else {
                    BurpExtender
                    .addMessageToDebugOutput(Messages
                            .getString("current.request.gets.not.packed.for.sending.due.to.not.matching.relevant.request.regex.or.wrong.invoking.tool"));
                }
            }
        }

        return this.currentMessage;
    }

    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#getSelectedData()
     */
    @Override
    public byte[] getSelectedData() {

        // TODO
        return this.txtInput.getSelectedText();
    }


    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#getTabCaption()
     */
    @Override
    public String getTabCaption() {

        return "gunziper";
    }


    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#getUiComponent()
     */
    @Override
    public Component getUiComponent() {

        return this.txtInput.getComponent();
    }


    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#isEnabled(byte[], boolean)
     */
    @Override
    public boolean isEnabled(byte[] content, boolean isRequest) {

        if (isRequest) {
            if (Variables.getInstance().isRequestUnpackingActive()) {
                if (BurpExtender
                        .isRelevantMessage(
                                content,
                                Variables.getInstance()
                                .getRelevantResquestRegex(),
                                "not_possible_till_now_to_get_tool_context",
                                "not_possible_till_now_to_get_tool_context",
                                Variables
                                .getInstance()
                                .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking()))
                    return true;
            }
        }
        else {
            if (Variables.getInstance().isResponseUnpackingActive()) {
                if (BurpExtender
                        .isRelevantMessage(
                                content,
                                Variables.getInstance()
                                .getRelevantResponseRegex(),
                                "not_possible_till_now_to_get_tool_context",
                                "not_possible_till_now_to_get_tool_context",
                                Variables
                                .getInstance()
                                .isRegexmatchIndicatingInrelevanceOnCurrentMessageForResponseunpacking()))
                    return true;
            }
        }

        return false;

    }


    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#isModified()
     */
    @Override
    public boolean isModified() {

        return this.txtInput.isTextModified();
    }

    /*
     * (non-Javadoc)
     *
     * @see burp.IMessageEditorTab#setMessage(byte[], boolean)
     */
    @Override
    public void setMessage(byte[] content, boolean isRequest) {


        if (isRequest) {
            this.currentMessage = content;
            if (content == null) {
                // this.txtInput
                // .setText(Library
                // .getBytearrayFromString("=== gunziper output ===\n\nthere were no input to display"));
                this.txtInput.setText(null);
                this.txtInput.setEditable(false);
            }
            else if (Variables.getInstance().isRequestUnpackingActive()) {
                if (BurpExtender
                        .isRelevantMessage(
                                content,
                                Variables.getInstance()
                                .getRelevantResquestRegex(),
                                "not_possible_till_now_to_get_tool_context",
                                "not_possible_till_now_to_get_tool_context",
                                Variables
                                .getInstance()
                                .isRegexmatchIndicateInrelevanceOnCurrentMessageForRequestunpackingBeforeunpacking())) {
                    this.txtInput.setText(UnpackingLibrary.getProcessedMessage(
                            content, Variables.getInstance()
                            .getRegexMarkingRequestpartForUnpacking(),
                            true, true));
                    this.txtInput.setEditable(true);
                }
                else {
                    this.txtInput.setText(null);
                    this.txtInput.setEditable(false);
                }
            }
            else {
                this.txtInput.setText(null);
                this.txtInput.setEditable(false);
            }
        }
        else {
            this.txtInput.setEditable(true);
            if (content == null) {

                this.txtInput
                .setText(Library
                        .getBytearrayFromString("=== gunziper output ===\n\nthere was no input to display"));
            }

            else {
                this.txtInput
                .setText(UnpackingLibrary.tryUnpackResponse(content,
                        Variables.getInstance().getResponseToolName()));
            }
        }

    }



}
