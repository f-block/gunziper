
package unpacking;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import misc.Messages;
import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;



public class UnpackSelectionWindow extends JFrame {

    // public static void main(String[] args) {
    //
    // new UnpackSelectionWindow();
    // }

    /**
     * @param args
     */
    IBurpExtenderCallbacks callback;
    IExtensionHelpers      helpers;
    IHttpRequestResponse[] messages;

    public UnpackSelectionWindow(IBurpExtenderCallbacks mCallbacks,
            IExtensionHelpers helpers, IHttpRequestResponse[] messageInfo) {

        this.callback = mCallbacks;
        this.messages = messageInfo;
        this.helpers = helpers;
        this.setLayout(new GridLayout(4, 1));
        this.setTitle(Messages.getString("choose.the.desired.action"));
        JButton prepareForRepeaterButton = new JButton(Messages
                .getString("process.request.and.send.to.repeater"));
        JButton prepareForIntruderButton = new JButton(Messages
                .getString("process.request.and.send.to.intruder"));
        JButton displayUnpackedMessageButton = new JButton(Messages
                .getString("display.unpacked.message"));
        JButton displayPackedMessageButton = new JButton(Messages
                .getString("display.packed.message"));

        ActionListener prepareForRepeaterActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new SendUnpackedRequestsToRepeaterMenuItem(
                        UnpackSelectionWindow.this.callback,
                        UnpackSelectionWindow.this.messages);
                UnpackSelectionWindow.this.dispose();
            }
        };

        ActionListener prepareForIntruderActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new SendUnpackedRequestsToIntruderMenuItem(
                        UnpackSelectionWindow.this.callback,
                        UnpackSelectionWindow.this.helpers,
                        UnpackSelectionWindow.this.messages);
                UnpackSelectionWindow.this.dispose();
            }
        };

        ActionListener displayUnpackedMessageActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new DisplayUnpackedMessage(UnpackSelectionWindow.this.messages);
                UnpackSelectionWindow.this.dispose();
            }
        };

        ActionListener displayPackedMessageActionlistener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new DisplayPackedMessage(UnpackSelectionWindow.this.messages);
                UnpackSelectionWindow.this.dispose();
            }
        };

        prepareForRepeaterButton
                .addActionListener(prepareForRepeaterActionlistener);
        prepareForIntruderButton
                .addActionListener(prepareForIntruderActionlistener);
        displayUnpackedMessageButton
                .addActionListener(displayUnpackedMessageActionlistener);
        displayPackedMessageButton
                .addActionListener(displayPackedMessageActionlistener);

        this.add(prepareForRepeaterButton);
        this.add(prepareForIntruderButton);
        this.add(displayUnpackedMessageButton);
        this.add(displayPackedMessageButton);
        this.finish();
    }

    private void finish() {

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
