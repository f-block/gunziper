

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
