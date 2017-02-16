/**
 * Erstellt am: Aug 29, 2014 4:02:11 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package unpacking.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import misc.Library;
import misc.Messages;
import exceptions.NothingDoneException;


public class SimpleRegexReplacement extends AbstractDeEncrypter {

    private String  decryptIdentifierString = null;
    private String  encryptIdentifierString = null;
    private String  encryptReplaceString    = null;
    private Pattern encryptRegex            = null;
    private String  decryptReplaceString    = null;
    private Pattern decryptRegex            = null;


    public SimpleRegexReplacement(String encryptIdentifierString,
            String decryptIdentifierString, String encryptReplaceString,
            String encryptRegex, String decryptReplaceString,
            String decryptRegex) {

        this.encryptIdentifierString = encryptIdentifierString;
        this.decryptIdentifierString = decryptIdentifierString;
        this.encryptReplaceString = encryptReplaceString;
        this.setEncryptRegex(encryptRegex);
        this.decryptReplaceString = decryptReplaceString;
        this.setDecryptRegex(decryptRegex);
    }


    /*
     * (non-Javadoc)
     *
     * @see unpacking.api.AbstractDeEncrypter#getDecryptedBytearray(byte[])
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        return Library.getBytearrayFromString(this.tryToReplace(
                this.decryptRegex,
                Library.getStringFromBytearray(encryptedByteArray),
                this.decryptReplaceString, this.getDecryptIdentifierString()));
    }


    /*
     * (non-Javadoc)
     *
     * @see unpacking.api.AbstractDeEncrypter#getDecryptIdentifierString()
     */
    @Override
    public String getDecryptIdentifierString() {

        return this.decryptIdentifierString;
    }


    /**
     * @return the decryptRegex
     */
    public String getDecryptRegex() {

        return this.decryptRegex.pattern();
    }


    /**
     * @return the decryptReplaceString
     */
    public String getDecryptReplaceString() {

        return this.decryptReplaceString;
    }


    /*
     * (non-Javadoc)
     *
     * @see unpacking.api.AbstractDeEncrypter#getEncryptedBytearray(byte[])
     */
    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        return Library.getBytearrayFromString(this.tryToReplace(
                this.encryptRegex,
                Library.getStringFromBytearray(plaintextInputBytearray),
                this.encryptReplaceString, this.getEncryptIdentifierString()));
    }



    /*
     * (non-Javadoc)
     *
     * @see unpacking.api.AbstractDeEncrypter#getEncryptIdentifierString()
     */
    @Override
    public String getEncryptIdentifierString() {

        return this.encryptIdentifierString;
    }



    /**
     * @return the encryptRegex
     */
    public String getEncryptRegex() {

        return this.encryptRegex.pattern();
    }



    /**
     * @return the encryptReplaceString
     */
    public String getEncryptReplaceString() {

        return this.encryptReplaceString;
    }



    /**
     * @param decryptIdentifierString
     *            the decryptIdentifierString to set
     */
    public void setDecryptIdentifierString(String decryptIdentifierString) {

        this.decryptIdentifierString = decryptIdentifierString;
    }



    /**
     * @param decryptRegex
     *            the decryptRegex to set
     */
    public void setDecryptRegex(String decryptRegex) {

        this.decryptRegex = Pattern.compile(decryptRegex, Pattern.DOTALL);
    }



    /**
     * @param decryptReplaceString
     *            the decryptReplaceString to set
     */
    public void setDecryptReplaceString(String decryptReplaceString) {

        this.decryptReplaceString = decryptReplaceString;
    }



    /**
     * @param encryptIdentifierString
     *            the encryptIdentifierString to set
     */
    public void setEncryptIdentifierString(String encryptIdentifierString) {

        this.encryptIdentifierString = encryptIdentifierString;
    }



    /**
     * @param encryptRegex
     *            the encryptRegex to set
     */
    public void setEncryptRegex(String encryptRegex) {

        this.encryptRegex = Pattern.compile(encryptRegex, Pattern.DOTALL);;
    }



    /**
     * @param encryptReplaceString
     *            the encryptReplaceString to set
     */
    public void setEncryptReplaceString(String encryptReplaceString) {

        this.encryptReplaceString = encryptReplaceString;
    }



    private String tryToReplace(Pattern p, String input, String replaceString,
            String identifier) {

        try {
            Matcher m = p.matcher(input);
            return Library.getReplacementByRegexGroups(input, m, replaceString,
                    false, false, false, null, true);
        }catch (IndexOutOfBoundsException e) {
            System.out
                    .println(Messages
                            .getString("no.regex.group.could.be.found.within.the.regex")
                            + identifier);
        }catch (NothingDoneException e) {
            // just ignore
        }

        return input;
    }

}
