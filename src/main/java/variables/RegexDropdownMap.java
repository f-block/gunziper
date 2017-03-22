/**
 * Erstellt am: Jan 22, 2014 6:55:37 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package variables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class RegexDropdownMap implements Serializable {

    private final String        LAST_USED_REGEX               = "Last used";
    private final String        EXPLANATION_STRING_IDENTIFIER = "Explanation";
    private Map<String, String> regexHashmap                  = null;
    private static final long   serialVersionUID              = 15L;


    public RegexDropdownMap() {

        this.regexHashmap = new HashMap<String, String>();
        this.regexHashmap.put(this.LAST_USED_REGEX, "");

    }


    // public RegexDropdownMap(Map<String, String> regexHashmap) {
    //
    // this.regexHashmap = regexHashmap;
    // }


    /**
     *
     * @param identifier
     *            identifier string for new regex
     * @param regex
     * @param overwrite
     *            set to true, if the regex for an existing identifier should be
     *            updated
     * @return returns false if identifier already exists, or true if
     *         successfully added
     */
    public boolean addElementToRegexDropDownList(String identifier,
            String regex, boolean overwrite) {

        if (!identifier.equals(this.LAST_USED_REGEX)
                && !identifier.equals(this.EXPLANATION_STRING_IDENTIFIER)) {

            if (!this.regexHashmap.containsKey(identifier)) {
                this.regexHashmap.put(identifier, regex);
                return true;
            }

            else if (this.regexHashmap.containsKey(identifier) && overwrite) {

                this.regexHashmap.remove(identifier);
                this.regexHashmap.put(identifier, regex);
                return true;
            }

        }


        return false;
    }

    public void deleteElementFromRegexDropDownList(String identifier) {

        if (!identifier.equals(this.LAST_USED_REGEX)
                && !identifier.equals(this.EXPLANATION_STRING_IDENTIFIER)) {
            this.regexHashmap.remove(identifier);
        }
    }

    public String getExplanationString() {

        return this.regexHashmap.get(this.EXPLANATION_STRING_IDENTIFIER);
    }


    public String getLastUsed() {

        return this.regexHashmap.get(this.LAST_USED_REGEX);
    }


    public Set<String> getRegexDropdownHashmapKeyset() {

        return this.regexHashmap.keySet();
    }

    public String getRegexDropdownValueForKey(String identifier) {

        return this.regexHashmap.get(identifier);
    }


    public void setExplanationString(String explanation) {

        this.regexHashmap.remove(this.EXPLANATION_STRING_IDENTIFIER);
        this.regexHashmap.put(this.EXPLANATION_STRING_IDENTIFIER, explanation);
    }


    public void setRegexDropdownHashmap(Map<String, String> newRegexhashmap) {

        this.regexHashmap = newRegexhashmap;
    }


    public void updateLastUsed(String regex) {

        this.regexHashmap.remove(this.LAST_USED_REGEX);
        this.regexHashmap.put(this.LAST_USED_REGEX, regex);
    }
}
