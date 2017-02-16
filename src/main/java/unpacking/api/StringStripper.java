/**
 * Erstellt am: Nov 10, 2015 5:15:36 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package unpacking.api;

import misc.DisplayText;
import misc.Library;


public class StringStripper extends AbstractDeEncrypter {

    final String decryptIdentifierString = "stripNulls";
    final String encryptIdentifierString = "addNulls";

    /*
     * (non-Javadoc)
     * 
     * @see unpacking.api.AbstractDeEncrypter#getDecryptedBytearray(byte[])
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        String hex = Library.getStringFromBytearray(encryptedByteArray);
        if ((hex.length() % 2) != 0) {
            new DisplayText(
                    "Error occured",
                    "The length of the input hex string for null byte stripping must be a multiple of 2");
            return null;
        }
        String outputString = "";
        for (int i = 2; i < hex.length(); i += 4) {
            outputString += hex.substring(i, i + 2);
        }
        return Library.getBytearrayFromString(outputString);
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

    /*
     * (non-Javadoc)
     * 
     * @see unpacking.api.AbstractDeEncrypter#getEncryptedBytearray(byte[])
     */
    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        String input = Library.getStringFromBytearray(plaintextInputBytearray);
        String outputString = "";

        for (int i = 0; i < input.length(); i += 2) {
            outputString += "00";
            outputString += input.substring(i, i + 2);
        }

        return Library.getBytearrayFromString(outputString);
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

}
