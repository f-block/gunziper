/**
 * Erstellt am: Jul 14, 2014 6:19:08 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package unpacking.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import misc.Library;
import misc.Messages;

import com.thoughtworks.xstream.XStream;


public class DeSerializer extends AbstractDeEncrypter {


    String decryptIdentifierString = "deserializeJavaObject";
    String encryptIdentifierString = "serializeJavaObject";


    @Override
    public byte[] getDecryptedBytearray(byte[] serializedObjectAsByteArray) {

        ByteArrayInputStream byteIn = null;
        ObjectInputStream in = null;
        Object obj = null;
        try {
            byteIn = new ByteArrayInputStream(serializedObjectAsByteArray);
            in = new ObjectInputStream(byteIn);
            obj = in.readObject();
            return new XStream().toXML(obj).getBytes();
        }catch (Exception e) {
            System.out.println(Messages
                    .getString("an.error.occured.during.deserialization")
                    + e.getMessage());
            // e.printStackTrace();
        }finally {
            try {
                byteIn.close();
                in.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serializedObjectAsByteArray;
    }

    @Override
    public String getDecryptIdentifierString() {

        return this.decryptIdentifierString;
    }


    @Override
    public byte[] getEncryptedBytearray(byte[] deserializedJavaObjectAsXmlString) {

        ByteArrayOutputStream byteOut = null;
        ObjectOutputStream objOut = null;

        try {
            byteOut = new ByteArrayOutputStream();
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(new XStream().fromXML(Library
                    .getStringFromBytearray(deserializedJavaObjectAsXmlString)));
            return byteOut.toByteArray();
        }catch (IOException e) {
            System.out.println(Messages
                    .getString("an.error.occured.during.serialization")
                    + e.getMessage());
            // e.printStackTrace();
        }finally {
            try {
                byteOut.close();
                objOut.close();
            }catch (IOException e) {
                // e.printStackTrace();
            }
        }
        return deserializedJavaObjectAsXmlString;

    }


    @Override
    public String getEncryptIdentifierString() {

        return this.encryptIdentifierString;
    }

}
