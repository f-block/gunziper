/**
 * Erstellt am: Jul 4, 2013 1:35:24 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import misc.Library;


public class UrlDeEncoder extends AbstractDeEncrypter {

    String decryptIdentifierString = "urlDecode";
    String encryptIdentifierString = "urlEncode";

    /*
     * (non-Javadoc)
     * 
     * @see unpackingapi.DeEncrypterInterface#getDecryptedBytearray(byte[])
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedBase64encodedString) {

        String temp = Library
                .getStringFromBytearray(encryptedBase64encodedString);
        try {
            temp = URLDecoder.decode(temp, "utf-8");
        }catch (UnsupportedEncodingException e) {
            System.out.println("Error while urlDecoding string: " + temp);
        }
        return Library.getBytearrayFromString(temp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see unpackingapi.DeEncrypterInterface#getDecryptIdentifierString()
     */
    @Override
    public String getDecryptIdentifierString() {

        return this.decryptIdentifierString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * unpackingapi.DeEncrypterInterface#getEncryptedBase64encodedBytearray(
     * byte[])
     */
    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        String temp = Library.getStringFromBytearray(plaintextInputBytearray);
        try {
            temp = URLEncoder.encode(temp, "utf-8");
        }catch (UnsupportedEncodingException e) {
            System.out.println("Error while urlEncoding string: " + temp);
        }
        return Library.getBytearrayFromString(temp);

    }

    /*
     * (non-Javadoc)
     * 
     * @see unpackingapi.DeEncrypterInterface#getEncryptIdentifierString()
     */
    @Override
    public String getEncryptIdentifierString() {

        return this.encryptIdentifierString;
    }

}
