/**
 * Erstellt am: Jul 16, 2014 11:43:39 AM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package unpacking.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import misc.Messages;


public class DeflateGzipcompatibleDeUnpacker extends AbstractDeEncrypter {

    String decryptIdentifierString = "inflateGzipcomp";
    String encryptIdentifierString = "deflateGzipcomp";


    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        ByteArrayInputStream byteIn = null;
        InflaterInputStream inflateIn = null;
        Inflater inflater = null;
        ByteArrayOutputStream byteOut = null;

        try {
            int i = 0;
            byteIn = new ByteArrayInputStream(encryptedByteArray);
            inflater = new Inflater(true);
            inflateIn = new InflaterInputStream(byteIn, inflater);
            byteOut = new ByteArrayOutputStream();

            byte[] temp = new byte[512];

            while ((i = inflateIn.read(temp, 0, temp.length)) != -1) {
                byteOut.write(temp, 0, i);
            }
            return byteOut.toByteArray();

        }catch (Exception e) {
            System.out.println(Messages
                    .getString("an.error.occured.while.inflating")
                    + e.getMessage());
        }finally {
            try {
                inflateIn.close();
                byteIn.close();
                byteOut.close();
            }catch (IOException e) {
                // e.printStackTrace();
            }
        }

        return encryptedByteArray;
    }


    @Override
    public String getDecryptIdentifierString() {

        return this.decryptIdentifierString;
    }

    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        byte[] output = null;
        ByteArrayOutputStream byteOut = null;
        DeflaterOutputStream deflateOut = null;
        Deflater deflater = null;

        try {
            byteOut = new ByteArrayOutputStream();
            deflater = new Deflater(7, true);
            deflateOut = new DeflaterOutputStream(byteOut, deflater);

            deflateOut.write(plaintextInputBytearray);

            deflateOut.finish();
            deflateOut.close();
            output = byteOut.toByteArray();
            byteOut.close();
            deflateOut.close();

            return output;

        }catch (Exception e) {
            System.out.println(Messages
                    .getString("an.error.occured.while.deflating")
                    + e.getMessage());
        }finally {
            try {
                deflateOut.close();
                byteOut.close();
            }catch (IOException e) {
                // e.printStackTrace();
            }
        }

        return plaintextInputBytearray;
    }

    @Override
    public String getEncryptIdentifierString() {

        return this.encryptIdentifierString;
    }
}
