package com.ht.airline.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Converter
public class DBCryptoConfig implements AttributeConverter<String, String> {

  private final String aesAlgo = "AES/GCM/NoPadding";

  private final String aesSecretKeySpec = "AES";

  private final int ivLength = 16;

  private final Integer gcmTagLength = 16;

  private final String hashAlgorithm = "SHA-256";

  @Value("${app.security.database.secret}")
  private String secret;

  /**
   * AES key derived from a password using HASH_ALGORITHM
   *
   * @param password password
   * @return byte array
   */
  private byte[] getAESKeyFromPassword(final char[] password) {
    try {
      MessageDigest digester = MessageDigest.getInstance(hashAlgorithm);
      digester.update(String.valueOf(password).getBytes(StandardCharsets.UTF_8));
      return digester.digest();
    } catch (Exception e) {
      log.error(e.getMessage());
      return new byte[16];
    }
  }

  /**
   * This method encrypts text
   *
   * @param text text
   * @return encrypted text
   */
  @Override
  public String convertToDatabaseColumn(String text) {
    byte[] key = getAESKeyFromPassword(secret.toCharArray());
    byte[] encryptedBytes;
    byte[] initialVector = new byte[ivLength];
    try {
      if (text != null) {
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(gcmTagLength * 8, initialVector);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesSecretKeySpec);

        // Encrypt
        Cipher aesCipher = Cipher.getInstance(aesAlgo);
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
        encryptedBytes = aesCipher.doFinal(text.getBytes());
        return Base64.encodeBytes(encryptedBytes);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return null;
  }

  /**
   * This method decrypts the texts
   *
   * @param dbData dbData
   * @return decrypted text
   */
  @Override
  public String convertToEntityAttribute(String dbData) {
    byte[] key = getAESKeyFromPassword(secret.toCharArray());
    byte[] decryptedBytes;
    byte[] initialVector = new byte[ivLength];
    try {
      if (dbData != null) {
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(gcmTagLength * 8, initialVector);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesSecretKeySpec);

        // Decrypt
        Cipher aesCipher = Cipher.getInstance(aesAlgo);
        aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
        decryptedBytes = aesCipher.doFinal(Base64.decode(dbData));

        return new String(decryptedBytes);
      }
    } catch (Exception e) {
      log.error(e.getMessage() + e.getCause());
    }
    return null;
  }
}
