package com.uyou.copenaccount.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by zdd on 2019/10/18.
 * <p>
 * Description:
 */
public class RSAUtils {

    private static final String ENCRYPT_TYPE = "RSA"; // 加密方式
    private static final String KEY_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 安卓端需要使用这种格式 对应服务器RSA格式
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * @Title: publicKeyToString
     * @Description: 得到字符串型的公钥 Java
     * @param publicKey
     * @return String
     */
    public static String publicKeyToString(RSAPublicKey publicKey) {
        return Base64.encode(publicKey.getEncoded());
    }

    /**
     * @Title: privateKeyToString
     * @Description: 得到字符串型的私钥 Java
     * @param privateKey
     * @return String
     */
    public static String privateKeyToString(RSAPrivateKey privateKey) {
        return Base64.encode(privateKey.getEncoded());
    }

    /**
     * @Title: getPublicKey
     * @Description: 将字符串型公钥转化为PublicKey Java
     * @param publicKey
     * @return PublicKey
     */
    public static PublicKey getPublicKey(String publicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
            KeyFactory factory;
            factory = KeyFactory.getInstance(ENCRYPT_TYPE);
            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: getPrivateKey
     * @Description: 将字符串型私钥转化为 PrivateKey Java
     * @param privateKey
     * @return PrivateKey
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory factory = KeyFactory.getInstance(ENCRYPT_TYPE);
            return factory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: generateKeyBytes
     * @Description: 初始化密钥对
     * @return Map<String,Object>
     */
    public static Map<String, Object> generateKeyBytes() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(ENCRYPT_TYPE);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: RSAEncode
     * @Description: 将字符串加密
     * @param key
     * @param plainText
     * @return String
     */
    public static String RSAEncode(PublicKey key, String plainText) {
        byte[] b = plainText.getBytes();
        try {
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return Base64.encode(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: RSADecode
     * @Description: 将字符串解密
     * @param key
     * @param encodedText
     * @return String
     */
    public static String RSADecode(PrivateKey key, String encodedText) {
        try {
            byte[] b = Base64.decode(encodedText);
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
