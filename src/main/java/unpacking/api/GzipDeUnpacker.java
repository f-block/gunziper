/**
 * Erstellt am: Jul 4, 2013 2:13:09 PM
 * Erstellt von: surf
 * Projekt: gunziperNewapi
 */

package unpacking.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import misc.DisplayText;
import misc.Messages;


public class GzipDeUnpacker extends AbstractDeEncrypter {

    String decryptIdentifierString = "ungzip";
    String encryptIdentifierString = "gzip";


    /*
     * (non-Javadoc)
     * 
     * @see unpackingapi.DeEncrypterInterface#getDecryptedBytearray(byte[])
     */
    @Override
    public byte[] getDecryptedBytearray(byte[] encryptedByteArray) {

        InputStream is = null;
        ByteArrayOutputStream os = null;
        int nextByte = 0;
        byte[] messageBody = null;
        try {

            is = new GZIPInputStream(new ByteArrayInputStream(
                    encryptedByteArray));
            os = new ByteArrayOutputStream();

            for (nextByte = 0; (nextByte = is.read()) != -1;) {
                os.write(nextByte);
            }

            os.flush();
            messageBody = os.toByteArray();
            return messageBody;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (EOFException e) {
            e.printStackTrace();
            new DisplayText(Messages.getString("errorOccured"), Messages
                    .getString("nogzip"));
        }catch (IOException e) {
            e.printStackTrace();

        }finally {
            if (os != null) {
                try {
                    os.close();
                }catch (IOException e) {}
            }
            if (is != null) {
                try {
                    is.close();
                }catch (IOException e) {}
            }
        }
        return encryptedByteArray;
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
     * @see unpackingapi.DeEncrypterInterface#getEncryptedBytearray(byte[])
     */
    @Override
    public byte[] getEncryptedBytearray(byte[] plaintextInputBytearray) {

        InputStream is = null;
        ByteArrayOutputStream os = null;
        GZIPOutputStream oz = null;
        int nextByte = 0;
        byte[] messageBody = null;
        try {

            os = new ByteArrayOutputStream();
            oz = new GZIPOutputStream(os);
            is = new ByteArrayInputStream(plaintextInputBytearray);

            for (nextByte = 0; (nextByte = is.read()) != -1;) {
                oz.write(nextByte);
            }
            oz.flush();
            oz.close();

            messageBody = os.toByteArray();
            return messageBody;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (EOFException e) {
            e.printStackTrace();
            new DisplayText(Messages.getString("errorOccured"), Messages
                    .getString("nogzip"));
        }catch (IOException e) {
            e.printStackTrace();

        }finally {
            if (os != null) {
                try {
                    os.close();
                }catch (IOException e) {}
            }
            if (is != null) {
                try {
                    is.close();
                }catch (IOException e) {}
            }
        }
        return plaintextInputBytearray;
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
