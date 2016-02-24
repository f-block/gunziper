/**
 * Erstellt am: Jun 6, 2014 2:09:55 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package variables;

import java.awt.Dimension;
import java.io.Serializable;


public class ComparerWindowSettings implements Serializable {

    private Dimension         size                      = null;
    private boolean           useLineIndexes            = true;
    private boolean           useRightTextareaForSearch = false;
    private boolean           syncViews                 = true;
    private static final long serialVersionUID          = 14L;




    public ComparerWindowSettings() {

    }



    /**
     * @return the size
     */
    public Dimension getSize() {

        return this.size;
    }


    /**
     * @return the syncViews
     */
    public boolean isSyncViews() {

        return this.syncViews;
    }


    /**
     * @return the useLineIndexes
     */
    public boolean isUseLineIndexes() {

        return this.useLineIndexes;
    }

    /**
     * @return the useRightTextareaForSearch
     */
    public boolean isUseRightTextareaForSearch() {

        return this.useRightTextareaForSearch;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(Dimension size) {

        this.size = size;
    }


    /**
     * @param syncViews
     *            the syncViews to set
     */
    public void setSyncViews(boolean syncViews) {

        this.syncViews = syncViews;
    }


    /**
     * @param useLineIndexes
     *            the useLineIndexes to set
     */
    public void setUseLineIndexes(boolean useLineIndexes) {

        this.useLineIndexes = useLineIndexes;
    }


    /**
     * @param useRightTextareaForSearch
     *            the useRightTextareaForSearch to set
     */
    public void setUseRightTextareaForSearch(boolean useRightTextareaForSearch) {

        this.useRightTextareaForSearch = useRightTextareaForSearch;
    }
}
