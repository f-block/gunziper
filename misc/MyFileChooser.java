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