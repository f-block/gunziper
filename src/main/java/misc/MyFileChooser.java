
package misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class MyFileChooser extends JFileChooser {

    private JDialog frage                          = null;
    File            file                           = null;
    boolean         writeOkDependingOnExistingFile = true;
    String          fileExtension                  = "";
    String          fileDescription                = "";



    public MyFileChooser(String initialDir, String fileExtension,
            String fileDescription) {

        if (!initialDir.equals("")) {
            this.setCurrentDirectory(new File(initialDir));
        }

        this.setDialogTitle("Choose File");
        this.fileExtension = fileExtension;
        this.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {

                return (Library.getFirstRegexgroupMatch("(\\"
                        + MyFileChooser.this.getFileExtension() + "\\d*)$", f
                        .getName()) != null)
                        || f.isDirectory();
            }

            @Override
            public String getDescription() {

                return MyFileChooser.this.fileDescription + " (*"
                        + MyFileChooser.this.getFileExtension() + ")";
            }
        });
    }

    /**
     * @return the file
     */
    public File getFile() {

        return this.file;
    }


    /**
     * @return the fileExtension
     */
    private String getFileExtension() {

        return this.fileExtension;
    }



    public void init(boolean writeToFile) {

        if (writeToFile) {

            if (this.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.file = this.getSelectedFile();

                if (Library.getFirstRegexgroupMatch("(\\"
                        + MyFileChooser.this.getFileExtension() + "\\d*)$",
                        this.file.getName()) == null) {
                    this.file = new File(this.file.getAbsolutePath()
                            + this.fileExtension);
                }

                if (this.file.exists()) {
                    this.initDialog("File already exists, overwrite?",
                            new ActionListener() {

                                public void actionPerformed(ActionEvent a) {

                                    MyFileChooser.this.frage.setVisible(false);
                                    MyFileChooser.this
                                            .setWriteOkDependingOnExistingFile(true);
                                }
                            });
                }
            }
        }
        else {
            if (this.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.file = this.getSelectedFile();
            }
        }
    }


    private void initDialog(String frageText, ActionListener ja) {

        this.frage = new CustomDialog(frageText, ja);
        this.frage.setVisible(true);
    }


    /**
     * @return the writeOkDependingOnExistingFile
     */
    public boolean isWriteOkDependingOnExistingFile() {

        return this.writeOkDependingOnExistingFile;
    }

    /**
     * @param writeOkDependingOnExistingFile
     *            the writeOkDependingOnExistingFile to set
     */
    public void setWriteOkDependingOnExistingFile(
            boolean writeOkDependingOnExistingFile) {

        this.writeOkDependingOnExistingFile = writeOkDependingOnExistingFile;
    }


}
