/**
 * Erstellt am: Nov 29, 2013 1:18:28 AM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package intrudercomparer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import misc.DisplayText;
import misc.Library;
import burp.IHttpRequestResponse;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation;


/**
 * This class uses the provided messages, checks for differences and opens a new
 * ComparerWindow on a diff
 *
 */
public class GComparer implements Runnable {

    private Thread                 calculateDifferencesThread = null;
    private IHttpRequestResponse[] messageInfo                = null;
    private boolean                isInterrupted              = false;




    public GComparer(IHttpRequestResponse[] messageInfo) {

        this.messageInfo = messageInfo;
        this.calculateDifferencesThread = new Thread(this);
        this.calculateDifferencesThread.start();
    }

    public void run() {


        if ((this.messageInfo != null) && (this.messageInfo.length >= 2)) {

            String previousResponseString = null;
            String actResponseString = null;
            ComparerWindow cw = null;
            List<Diff> previousDiffs = null;
            boolean isFirstRound = true;
            boolean responsesDiffer = false;
            boolean atLeastOnePairOfResponsesThatDifferFound = false;
            DiffMatchPatch diffGenerator = new DiffMatchPatch();
            ActionListener cancelWholeDifferenceCalculationActionlistener = null;

            if (this.messageInfo.length > 2) {
                cancelWholeDifferenceCalculationActionlistener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                        GComparer.this.isInterrupted = true;
                        ((JFrame) ((JButton) arg0.getSource())
                                .getTopLevelAncestor()).dispose();
                    }
                };
            }


            for (int i = 0; ((i < this.messageInfo.length)
                    && (!this.calculateDifferencesThread.isInterrupted()) && (!this.isInterrupted)); i++) {
                responsesDiffer = false;
                if (isFirstRound) {
                    actResponseString = Library
                            .getStringFromBytearray(this.messageInfo[i]
                                    .getResponse());
                    isFirstRound = false;
                }
                else {
                    previousResponseString = actResponseString;
                    actResponseString = Library
                            .getStringFromBytearray(this.messageInfo[i]
                                    .getResponse());


                    previousDiffs = diffGenerator.diffMain(
                            previousResponseString, actResponseString, true);

                    for (Diff diff:previousDiffs) {
                        if (diff.operation != Operation.EQUAL) {
                            responsesDiffer = true;
                            atLeastOnePairOfResponsesThatDifferFound = true;
                        }
                    }

                    if (responsesDiffer) {
                        cw = new ComparerWindow(-1, previousDiffs,
                                cancelWholeDifferenceCalculationActionlistener,
                                "gComparer");
                        while (cw.isVisible()) {
                            try {
                                Thread.sleep(347);
                            }catch (InterruptedException e) {
                                // Ignore
                            }
                        }
                    }
                }
            }

            if (!atLeastOnePairOfResponsesThatDifferFound) {
                new DisplayText("Info", "No differences could be found.");
            }
        }
    }
}
