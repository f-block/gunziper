/**
 * Erstellt am: Jan 22, 2014 6:55:37 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package multipleSelections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SelectionDropdownMap implements Serializable {

    private String              DEFAULT_ITEM_STRING = null;
    private Map<String, Object> itemsHashmap        = null;
    private static final long   serialVersionUID    = 13L;


    public SelectionDropdownMap(String firstItemIdentifier, Object firstItem) {

        this.itemsHashmap = new HashMap<String, Object>();
        this.DEFAULT_ITEM_STRING = firstItemIdentifier;
        this.itemsHashmap.put(this.DEFAULT_ITEM_STRING, firstItem);

    }


    /**
     *
     * @param identifier
     *            identifier string for new item
     * @param newItem
     *            new Item Object
     * @param overwrite
     *            set to true, if the Object for an existing identifier should
     *            be
     *            updated
     * @return returns false if identifier already exists and hasn't been
     *         overwritten, or true if
     *         successfully added/modified
     */
    public boolean addNewItemToDropDownList(String identifier, Object newItem,
            boolean overwrite) {

        if (!this.itemsHashmap.containsKey(identifier)) {
            this.itemsHashmap.put(identifier, newItem);
            return true;
        }

        else if (this.itemsHashmap.containsKey(identifier) && overwrite) {

            this.itemsHashmap.remove(identifier);
            this.itemsHashmap.put(identifier, newItem);
            return true;
        }



        return false;
    }

    public void deleteElementFromDropDownList(String identifier) {

        if (!identifier.equals(this.DEFAULT_ITEM_STRING)) {
            this.itemsHashmap.remove(identifier);
        }
    }

    public Object getDefaultItem() {

        return this.getDropdownValueForKey(this.DEFAULT_ITEM_STRING);
    }

    public Set<String> getDropdownHashmapKeyset() {

        return this.itemsHashmap.keySet();
    }


    public Object getDropdownValueForKey(String identifier) {

        return this.itemsHashmap.get(identifier);
    }

    public void setDefaultItem(Object item) {

        this.addNewItemToDropDownList(this.DEFAULT_ITEM_STRING, item, true);
    }


    public void setDropdownHashmap(Map<String, Object> newItemsHashmap) {

        this.itemsHashmap = newItemsHashmap;
    }

}
