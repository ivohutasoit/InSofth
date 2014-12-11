package com.softhaxi.insofth.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Hutasoit
 */
public class RSAUtil {

    private static final String PUBLIC_KEY_FILE = "Public.key";
    private static final String PRIVATE_KEY_FILE = "Private.key";
    
    private static StringBuilder strBuilder;

    public static String generateKey() throws IOException {
        try {
            strBuilder = new StringBuilder();
            strBuilder.append("-------GENRATE PUBLIC and PRIVATE KEY-------------")
                    .append("\n");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); //1024 used for normal securities
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            strBuilder.append("Public Key - ")
                    .append(publicKey)
                    .append("\n")
                    .append("Private Key -")
                    .append(privateKey)
                    .append("\n")
                    .append("\n------- PULLING OUT PARAMETERS WHICH MAKES KEYPAIR----------\n");

            //Pullingout parameters which makes up Key
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            System.out.println("PubKey Modulus : " + rsaPubKeySpec.getModulus());
            System.out.println("PubKey Exponent : " + rsaPubKeySpec.getPublicExponent());
            System.out.println("PrivKey Modulus : " + rsaPrivKeySpec.getModulus());
            System.out.println("PrivKey Exponent : " + rsaPrivKeySpec.getPrivateExponent());

            //Share public key with other so they can encrypt data and decrypt thoses using private key(Don't share with Other)
            System.out.println("\n--------SAVING PUBLIC KEY AND PRIVATE KEY TO FILES-------\n");
            saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
            saveKeys(PRIVATE_KEY_FILE, rsaPrivKeySpec.getModulus(), rsaPrivKeySpec.getPrivateExponent());

            //Encrypt Data using Public Key
            byte[] encryptedData = encryptData("Anuj Patel - Classified Information !");

            //Descypt Data using Private Key
            decryptData(encryptedData);

            return strBuilder.toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }
        return null;
    }

    /**
     * Save Files
     *
     * @param fileName
     * @param mod
     * @param exp
     * @throws IOException
     */
    private static void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            System.out.println("Generating " + fileName + "...");
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));

            oos.writeObject(mod);
            oos.writeObject(exp);

            System.out.println(fileName + " generated successfully");
        } catch (Exception e) {
        } finally {
            if (oos != null) {
                oos.close();

                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    /**
     * Encrypt Data
     *
     * @param data
     * @throws IOException
     */
    private static byte[] encryptData(String data) throws IOException {
        System.out.println("\n----------------ENCRYPTION STARTED------------");

        System.out.println("Data Before Encryption :" + data);
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try {
            PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
            System.out.println("Encryted Data: " + Arrays.toString(encryptedData));

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        }

        System.out.println("----------------ENCRYPTION COMPLETED------------");
        return encryptedData;
    }

    /**
     * Encrypt Data
     *
     * @param data
     * @throws IOException
     */
    private static void decryptData(byte[] data) throws IOException {
        System.out.println("\n----------------DECRYPTION STARTED------------");
        byte[] descryptedData = null;

        try {
            PrivateKey privateKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            descryptedData = cipher.doFinal(data);
            System.out.println("Decrypted Data: " + new String(descryptedData));

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        }

        System.out.println("----------------DECRYPTION COMPLETED------------");
    }

    /**
     * read Public Key From File
     *
     * @param fileName
     * @return PublicKey
     * @throws IOException
     */
    public static PublicKey readPublicKeyFromFile(String fileName) throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);

            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            //Get Public Key
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);

            return publicKey;

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
        } finally {
            if (ois != null) {
                ois.close();
                if (fis != null) {
                    fis.close();
                }
            }
        }
        return null;
    }

    /**
     * read Public Key From File
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static PrivateKey readPrivateKeyFromFile(String fileName) throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);

            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            //Get Private Key
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);

            return privateKey;

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
                if (fis != null) {
                    fis.close();
                }
            }
        }
        return null;
    }
}
