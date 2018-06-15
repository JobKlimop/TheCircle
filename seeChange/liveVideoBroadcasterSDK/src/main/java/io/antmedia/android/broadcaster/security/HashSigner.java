package io.antmedia.android.broadcaster.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import io.antmedia.android.broadcaster.network.RTMPStreamer;

public class HashSigner {


    String dataToHash;
    int dataToHashInt;
    private HashGenerator hashGen = new HashGenerator();

    /*
    * Sign the frame given. Both hash it here, and sign it.
    */
    public String sign(Context c, RTMPStreamer.Frame frame) {
        SharedPreferences settings = c.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        String privKey = settings.getString("privatekey", "unavailabe");


        // TODO: Choose data from frame.obj to hash, and do so using the hashGen

        Log.i("HEX-FRAMEDATA", formatHexDump(frame.data, 1, frame.data.length));
        Log.i("HEX-TIMESTAMP", Integer.toString(frame.timestamp));
        Log.i("HEX-LENGTH", Integer.toString(frame.length));

        dataToHashInt = frame.length + frame.timestamp + 5;
        dataToHash = Integer.toString(dataToHashInt);

        Log.i("Data Opgeteld", Integer.toString(dataToHashInt));

        String hash = hashGen.getHash(dataToHash);
        Log.i("hash", hash);

        // TODO: Following the hash, encrypt it using the private key from the local storage


        return privKey;
    }

    public static String formatHexDump(byte[] array, int offset, int length) {
        final int width = 16;

        StringBuilder builder = new StringBuilder();

        for (int rowOffset = offset; rowOffset < offset + length; rowOffset += width) {
            builder.append(String.format("%06d:  ", rowOffset));

            for (int index = 0; index < width; index++) {
                if (rowOffset + index < array.length) {
                    builder.append(String.format("%02x ", array[rowOffset + index]));
                } else {
                    builder.append("   ");
                }
            }

            if (rowOffset < array.length) {
                int asciiWidth = Math.min(width, array.length - rowOffset);
                builder.append("  |  ");
                try {
                    builder.append(new String(array, rowOffset, asciiWidth, "UTF-8").replaceAll("\r\n", " ").replaceAll("\n", " "));
                } catch (UnsupportedEncodingException ignored) {
                    //If UTF-8 isn't available as an encoding then what can we do?!
                }
            }

            builder.append(String.format("%n"));
        }

        return builder.toString();
    }

}
