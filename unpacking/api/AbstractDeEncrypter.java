/**
 * Erstellt am: Jun 20, 2013 11:10:05 AM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking.api;

import java.io.Serializable;

import exceptions.NothingDoneException;


/**
 * This class should be extended from any class, which is supposed to extend
 * gunziper with unpacking functionality
 * Additionally to fully work, it must be registered e.g. in the
 * "registerExtenderCallbacks" within BurpExtender.java
 */
public abstract class AbstractDeEncrypter implements Serializable {

    String decryptIdentifierString;
    String encryptIdentifierString;

    public abstract byte[] getDecryptedBytearray(byte[] encryptedByteArray);


    /**
     * This method is used to identify when the
     * <code>getDecryptedBytearray</code> function should be used
     *
     * @return The String identifier, which tells gunziper to use the
     *         <code>getDecryptedBytearray</code> function
     */
    public abstract String getDecryptIdentifierString();


    public abstract byte[] getEncryptedBytearray(byte[] plaintextInputBytearray);


    /**
     * This method is used to identify when the
     * <code>getEncryptedBase64encodedBytearray</code> function should be used
     *
     * @return The String identifier, which tells gunziper to use the
     *         <code>getEncryptedBase64encodedBytearray</code> function
     */
    public abstract String getEncryptIdentifierString();


    /**
     * This method is used to get the "reverse" identifier
     *
     * @param identifier
     *            the current De- or Encrypt identifier
     * @return The String identifier for the reverse operation (if the given
     *         identifier identifies the encrypt function, the decrypt
     *         identifier is returned and vice versa)
     */
    public String getReverseStringFromCurrentString(String identifier)
            throws NothingDoneException {

        if (identifier.equalsIgnoreCase(this.getEncryptIdentifierString()))
            return this.getDecryptIdentifierString();

        else if (identifier.equalsIgnoreCase(this.getDecryptIdentifierString()))
            return this.getEncryptIdentifierString();


        throw new NothingDoneException("");
    }
}
