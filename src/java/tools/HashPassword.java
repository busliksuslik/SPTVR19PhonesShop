/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author nikita
 */
public class HashPassword implements EncryptPassword{

    @Override
    public String createSalt() {
        SecureRandom rand = new SecureRandom();
        byte[]salt = new byte[16];
        rand.nextBytes(salt);
        return new BigInteger(salt).toString(16);
    }

    @Override
    public String createHash(String password, String salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(),65536,128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[]hash = factory.generateSecret(spec).getEncoded();
            return new BigInteger(hash).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashPassword.class.getName()).log(Level.SEVERE, "Не найден алгоритм", ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(HashPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
