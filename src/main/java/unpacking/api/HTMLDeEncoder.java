/**
 * Erstellt am: Aug 26, 2014 6:04:10 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package unpacking.api;

import misc.Library;


public class HTMLDeEncoder extends AbstractDeEncrypter {

    String decryptIdentifierString = "htmlToPlain";
    String encryptIdentifierString = "plainToHtml";

    /*
     * (non-Javadoc)
     *
     * @see unpacking.api.AbstractDeEncrypter#getDecryptedBytearray(byte[])
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        String temp = Library.getStringFromBytearray(encryptedByteArray);

        temp = temp.replaceAll("&quot;", "\"");
        temp = temp.replaceAll("&gt;", ">");
        temp = temp.replaceAll("&lt;", "<");
        temp = temp.replaceAll("&amp;", "&");

        return Library.getBytearrayFromString(temp);
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

        String temp = Library.getStringFromBytearray(plaintextInputBytearray);

        temp = temp.replaceAll("&", "&amp;");
        temp = temp.replaceAll("<", "&lt;");
        temp = temp.replaceAll(">", "&gt;");
        temp = temp.replaceAll("\"", "&quot;");

        return Library.getBytearrayFromString(temp);
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
