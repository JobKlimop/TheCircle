package io.antmedia.android.broadcaster.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    public String getHash(String dataToHash) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        digest.reset();
        byte[] datahashed = digest.digest(dataToHash.getBytes());

        return bin2hex(datahashed);

    }

    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }

}
