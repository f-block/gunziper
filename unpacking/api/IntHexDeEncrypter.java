/**
 * Erstellt am: Jul 4, 2013 2:30:21 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking.api;

import java.math.BigInteger;

import misc.Library;


public class IntHexDeEncrypter extends AbstractDeEncrypter {

    String decryptIdentifierString = "hexToInt";
    String encryptIdentifierString = "intToHex";

    /**
     * 
     * @return returns hexToInt
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        return Library.getBytearrayFromString(new BigInteger(Library
                .getStringFromBytearray(encryptedByteArray), 16).toString(10));
        // return Library.getBytearrayFromString(Integer.toString(Integer
        // .parseInt(Library.getStringFromBytearray(encryptedByteArray),
        // 16)));

    }


    /*
     * (non-Javadoc)
     * 
     * @see unpacking.api.DeEncrypterInterface#getDecryptIdentifierString()
     */
    @Override
    public String getDecryptIdentifierString() {

        return this.decryptIdentifierString;
    }


    /**
     * 
     * @return returns intToHex
     */
    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        return Library.getBytearrayFromString(new BigInteger(Library
                .getStringFromBytearray(plaintextInputBytearray), 10)
                .toString(16));

        // return Library.getBytearrayFromString(Integer.toHexString(Integer
        // .parseInt(Library
        // .getStringFromBytearray(plaintextInputBytearray))));

    }

    /*
     * (non-Javadoc)
     * 
     * @see unpacking.api.DeEncrypterInterface#getEncryptIdentifierString()
     */
    @Override
    public String getEncryptIdentifierString() {

        return this.encryptIdentifierString;
    }

}
