/**
 * Erstellt am: Jul 4, 2013 2:30:21 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking.api;

import java.util.ArrayList;

import misc.DisplayText;
import misc.Library;


public class AsciiHexDeEncrypter extends AbstractDeEncrypter {

    String decryptIdentifierString = "hexToAscii";
    String encryptIdentifierString = "asciiToHex";

    /**
     * 
     * @return returns hexToAscii
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        String hex = Library.getStringFromBytearray(encryptedByteArray);

        if (hex.length() % 2 != 0) {
            new DisplayText("Error occured",
                    "The length of the input hex string for convertion must be a multiple of 2");
            return null;
        }
        ArrayList<Byte> byteArray = new ArrayList<Byte>();

        String hexValue = "";


        for (int i = 0; i < hex.length(); i += 2) {

            hexValue = hex.substring(i, (i + 2));

            byteArray.add((byte) Integer.parseInt(hexValue, 16));

        }

        byte[] output = new byte[byteArray.size()];

        for (int i = 0; i < byteArray.size(); i++) {
            output[i] = byteArray.get(i);
        }
        return output;
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
     * @return returns asciiToHex
     */
    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        StringBuilder sb = new StringBuilder();
        for (byte b:plaintextInputBytearray) {
            sb.append(String.format("%02X ", b));
        }

        return Library
                .getBytearrayFromString(sb.toString().replaceAll(" ", ""));
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
