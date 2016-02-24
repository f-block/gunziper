/**
 * Erstellt am: Jul 4, 2013 1:26:03 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking.api;

import misc.Library;
import burp.BurpExtender;


public class Base64DeEncoder extends AbstractDeEncrypter {

    public static String getNormalizedBase64String(String base64String) {

        if (base64String.contains("+"))
            return base64String;
        else if (base64String.contains(":"))
            return base64String.replaceAll("_", "+").replaceAll(":", "/");
        else if (base64String.contains("-")) {
            if (base64String.contains(".")) {
                if (base64String.contains("_"))
                    return base64String.replaceAll(".", "+")
                            .replaceAll("_", "/").replaceAll("-", "=");
                else
                    return base64String.replaceAll(".", "+").replaceAll("-",
                            "/");
            }
            else if (base64String.contains("!"))
                return base64String.replaceAll("!", "+").replaceAll("-", "/");
            else
                return base64String.replaceAll("-", "+").replaceAll("_", "/");
        }
        else if (base64String.contains("_"))
            return base64String.replaceAll("-", "+").replaceAll("_", "/");
        else
            return base64String;

    }

    String decryptIdentifierString = "base64Decode";
    String encryptIdentifierString = "base64Encode";

    // public static final int NORMAL_BASE64 = 1;
    // public static final int BASE64_URL = 2;
    // public static final int BASE64_URL_NONSTANDARD = 4;
    // public static final int BASE64_XML = 8;
    //
    //
    // public static final int BASE64_REGEX = 16;

    /*
     * (non-Javadoc)
     * 
     * @see unpackingapi.DeEncrypterInterface#getDecryptedBytearray(byte[])
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedBase64encodedString) {

        return BurpExtender.getIextensionHelper().base64Decode(
                Base64DeEncoder.getNormalizedBase64String(Library
                        .getStringFromBytearray(encryptedBase64encodedString)));
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

        return Library.getBytearrayFromString(BurpExtender
                .getIextensionHelper().base64Encode(plaintextInputBytearray)
                .replaceAll("\r|\n", ""));
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
