/**
 * Erstellt am: Jul 20, 2014 8:05:32 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import variables.Variables;


public class ProcessingPanel extends JPanel {

    private JTextField        processingStringsTextfield = null;
    private JComboBox<String> processingSelector         = null;

    public ProcessingPanel(FeaturesEnum feature) {

        this.setLayout(new BorderLayout());

        this.processingStringsTextfield = new JTextField(20);

        if (feature.equals(FeaturesEnum.REQUEST_UNPACKING)) {
            this.processingStringsTextfield.setText(Variables.getInstance()
                    .getCsvDoingString());
        }
        else if (feature.equals(FeaturesEnum.RESPONSE_UNPACKING)) {
            this.processingStringsTextfield.setText(Variables.getInstance()
                    .getCsvResponseDoingString());
        }


        this.processingStringsTextfield.setToolTipText(Messages
                .getString("csvDoingString")
                + Variables.getInstance().getProcessingsString());
        this.add(this.processingStringsTextfield, BorderLayout.CENTER);

        this.processingSelector = new JComboBox<String>(Variables.getInstance()
                .getProcessingsString().split(","));

        // this.processingSelector.addActionListener(new ActionListener() {
        //
        // @Override
        // public void actionPerformed(ActionEvent arg0) {
        // if (arg0.getStateChange() == ItemEvent.SELECTED) {
        // ProcessingPanel.this.addProcessingString((String) arg0
        // .getItem());
        // }
        // }
        // });

        this.processingSelector.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {

                if (arg0.getStateChange() == ItemEvent.SELECTED) {
                    ProcessingPanel.this.addProcessingString((String) arg0
                            .getItem());
                }
            }
        });

        this.add(this.processingSelector, BorderLayout.EAST);

    }

    private void addProcessingString(String procString) {

        String temp = this.processingStringsTextfield.getText();

        if (!temp.equals("")) {
            temp += ",";
        }

        this.processingStringsTextfield.setText(temp + procString);

    }


    public String getProcessingString() {

        return this.processingStringsTextfield.getText();
    }

    @Override
    public boolean isEnabled() {

        return this.processingStringsTextfield.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {

        this.processingStringsTextfield
        .setBackground(enabled ? null:Color.GRAY);
        this.processingSelector.setBackground(enabled ? null:Color.GRAY);
        this.processingStringsTextfield.setEnabled(enabled);
        this.processingSelector.setEnabled(enabled);
    }

    public void setProcessingString(String procString) {

        this.processingStringsTextfield.setText(procString);
    }
}
