package io.antmedia.android.broadcaster.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Formatter;

import io.antmedia.android.broadcaster.network.RTMPStreamer;


public class HashSigner {


    String dataToHash;
    int dataToHashInt;
    private HashGenerator hashGen = new HashGenerator();

    /*
    * Sign the frame given. Both hash it here, and sign it.
    */
    public String sign(Context c, RTMPStreamer.Frame frame) throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {

        String dataToReturn;

        // Choose data from frame.obj to hash, and do so using the hashGen
        dataToHashInt = frame.length + frame.timestamp + 5;
        dataToHash = Integer.toString(dataToHashInt);
        Log.i("DATATOHASH", dataToHash);

        String hash = hashGen.getHash(dataToHash);

        // TODO: Following the hash, encrypt it using the private key from the local storage
        dataToReturn = signHash(hash, c);

        return dataToReturn;
    }

    public String signHash(String hash, Context c) {

        // Retrieve the private Key from the SharedPreferences - Idealy in a more secure container
        SharedPreferences settings = c.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        String privateKeyString = settings.getString("privatekey", "unavailabe");
        String publicKeyString = settings.getString("publickey", "unavailable");
        // Declare the byte array in which to store the data that will be signed
        byte[] signedData;
        String signedDataToSend;

        // Replace the beginning and end to make it compatible
        privateKeyString = privateKeyString.replace("-----BEGIN RSA PRIVATE KEY-----", "");
        privateKeyString = privateKeyString.replace("-----END RSA PRIVATE KEY-----", "");

        publicKeyString = publicKeyString.replace("-----BEGIN RSA PUBLIC KEY-----", "");
        publicKeyString = publicKeyString.replace("-----END RSA PUBLIC KEY-----", "");



        try {

            // Declare the KEyFactory and make it use the RSA Encryption method
            KeyFactory kf = KeyFactory.getInstance("RSA");

            // Decode the private key
            byte[] secret = Base64.decode(privateKeyString, Base64.NO_PADDING);
            RSAPrivateKey privateKeyObject = (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(secret));

            // Decode the public key
            byte[] publicKey = Base64.decode(publicKeyString,Base64.NO_PADDING);
            RSAPublicKey publicKeyObject = (RSAPublicKey)  kf.generatePublic(new X509EncodedKeySpec(publicKey));

            // Make a bytearray to sign
            byte[] dataToSign = hash.getBytes();

            // Create a signature, initialize the signing, add the data to the signing, and actually sign the information
            Signature sig = Signature.getInstance("SHA256WithRSA");
            sig.initSign(privateKeyObject);
            sig.update(dataToSign);
            signedData = sig.sign();

            sig.initVerify(publicKeyObject);
            Log.i("verify", Boolean.toString( sig.verify(signedData) ));

            Formatter formatter = new Formatter();
            for (byte b : signedData) {
                formatter.format("%02x", b);
            }
            String charactersToSend = formatter.toString();

            // Print out the signed hash
            System.out.println("Data to send: " + charactersToSend);

            return charactersToSend;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }



}
