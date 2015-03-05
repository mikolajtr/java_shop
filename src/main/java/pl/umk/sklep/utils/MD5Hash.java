package pl.umk.sklep.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    public static String getMD5Hash(String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        byte[] bmsg = msg.getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bmsg);

        return digest.toString();
    }
}
