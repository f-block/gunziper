/**
 * # gunziper for Burpsuite
 * #
 * # Copyright (c) 2012, Frank Block <gunziper@f-block.org>
 * #
 * # All rights reserved.
 * #
 * # Redistribution and use in source and binary forms, with or without
 * modification,
 * # are permitted provided that the following conditions are met:
 * #
 * # * Redistributions of source code must retain the above copyright notice,
 * this
 * # list of conditions and the following disclaimer.
 * # * Redistributions in binary form must reproduce the above copyright notice,
 * # this list of conditions and the following disclaimer in the documentation
 * # and/or other materials provided with the distribution.
 * # * The names of the contributors may not be used to endorse or promote
 * products
 * # derived from this software without specific prior written permission.
 * #
 * # THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * # AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * # IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * # ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * # LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL
 * # DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * # SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * # CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY,
 * # OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE
 * # OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package misc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


public class CustomDialog extends JDialog {

    private String               question              = "Are you sure?";
    private boolean              yesHasBeenPressed     = false;
    private boolean              withTextfield         = false;
    private String               title                 = "Warning";
    private JTextField           inputTextfield        = null;
    private String               yesButtonCaption      = "Yes";
    private String               noButtonCaption       = "No";

    private final ActionListener defaultActionlistener = new ActionListener() {

                                                           @Override
                                                           public void actionPerformed(
                                                                   ActionEvent arg0) {

                                                               CustomDialog temp = ((CustomDialog) ((JButton) (arg0
                                                                       .getSource()))
                                                                       .getTopLevelAncestor());
                                                               temp.yesHasBeenPressed = true;
                                                               temp.setVisible(false);
                                                           }
                                                       };

    public CustomDialog() {

        this.init(this.defaultActionlistener, null);

    }


    public CustomDialog(ActionListener jaAction) {

        this.init(jaAction, null);
    }

    public CustomDialog(String frageText, ActionListener jaAction) {

        this.question = frageText;
        this.init(jaAction, null);
    }

    public CustomDialog(String frageText, ActionListener jaAction,
            ActionListener cancelAction) {

        this.question = frageText;
        this.init(jaAction, cancelAction);
    }

    public CustomDialog(String title, String question, boolean withTextfield) {

        this.withTextfield = withTextfield;
        this.question = question;
        if (!withTextfield) {
            this.init(this.defaultActionlistener, null);
        }
        else {

            this.title = title;
            this.inputTextfield = new JTextField(20);
            this.yesButtonCaption = "Add";
            this.noButtonCaption = "Cancel";
            this.init(this.defaultActionlistener, null);

            // this.init(new ActionListener() {
            //
            // @Override
            // public void actionPerformed(ActionEvent arg0) {
            //
            // CustomDialog temp = ((CustomDialog) ((JButton) (arg0
            // .getSource())).getTopLevelAncestor());
            // temp.inputString = temp.inputTextfield.getText();
            // }
            // }, null);
        }

    }

    public boolean getAnswerForDefaultActionlistener() {

        return this.yesHasBeenPressed;
    }

    public String getInputstringForStringdialog() {

        if (this.withTextfield)
            return this.inputTextfield.getText();
        else
            return null;
    }

    private void init(ActionListener jaAction, ActionListener cancelAction) {

        JPanel buttonsDialog = new JPanel();
        buttonsDialog = new JPanel(new FlowLayout());
        JButton ja = new JButton(this.yesButtonCaption);
        ja.addActionListener(jaAction);
        JButton nein = new JButton(this.noButtonCaption);
        nein.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent a) {

                CustomDialog.this.dispose();
            }
        });

        buttonsDialog.add(ja);
        buttonsDialog.add(nein);

        if (cancelAction != null) {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(cancelAction);
            buttonsDialog.add(cancelButton);
        }

        this.setModal(true);
        this.setLayout(new BorderLayout());
        JLabel text = new JLabel(this.question);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(text, BorderLayout.NORTH);
        if (this.withTextfield) {
            this.add(this.inputTextfield, BorderLayout.CENTER);
        }
        this.add(buttonsDialog, BorderLayout.SOUTH);
        this.setSize(600, 130);
        this.setTitle(this.title);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
