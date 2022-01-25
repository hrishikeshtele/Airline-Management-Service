package com.ht.airline.security;

import com.ht.airline.exception.ServiceException;
import java.io.Serializable;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class RandomStringGenerator implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;

  @Value("${app.security.password.hash-algorithm}")
  private String hashingAlgorithm;

  @Value("${app.generationPolicy.password.uppercase}")
  private Integer upperCaseLength;

  @Value("${app.generationPolicy.password.lowercase}")
  private Integer lowerCaseLength;

  @Value("${app.generationPolicy.password.numbers}")
  private Integer numbersLength;

  @Value("${app.generationPolicy.password.numberSpecialCharacter}")
  private Integer specialCharacterLength;

  @Value("${app.generationPolicy.password.specialCharacter}")
  private String specialCharacter;

  @Value("${app.generationPolicy.pnr.uppercase}")
  private Integer upperCaseLengthPnr;

  @Value("${app.generationPolicy.pnr.lowercase}")
  private Integer lowerCaseLengthPnr;

  @Value("${app.generationPolicy.pnr.numbers}")
  private Integer numbersLengthPnr;

  @Value("${app.generationPolicy.pnr.numberSpecialCharacter}")
  private Integer specialCharacterLengthPnr;

  @Value("${app.generationPolicy.pnr.specialCharacter}")
  private String specialCharacterPnr;

  @Value("${app.security.nonce}")
  private String nonce;

  /**
   * Create secure password string.
   *
   * @param password the password
   * @return the string
   */
  public String createSecurePassword(String password) {
    try {
      byte[] decodeByte = Base64.getDecoder().decode(nonce);
      KeySpec spec = new PBEKeySpec(password.toCharArray(), decodeByte, 1000, 512);
      SecretKeyFactory factory = SecretKeyFactory.getInstance(hashingAlgorithm);
      byte[] bytes = factory.generateSecret(spec).getEncoded();
      // Get complete hashed password in hex format
      // new String(Base64.getEncoder().encode(bytes));
      return password;
    } catch (Exception e) {
      log.error("Password generation failed" + e.getMessage());
      throw new ServiceException("Password generation failed");
    }
  }

  /**
   * Generate random password string.
   *
   * @return the string
   */
  public String generateRandomPassword() {
    log.info("generate random Password");
    return generateRandomString(
        lowerCaseLength, upperCaseLength, specialCharacterLength, numbersLength, specialCharacter);
  }

  /**
   * Generate random pnr string.
   *
   * @return the string
   */
  public String generateRandomPnr() {
    log.info("generate random Pnr");
    return generateRandomString(
        lowerCaseLengthPnr,
        upperCaseLengthPnr,
        specialCharacterLengthPnr,
        numbersLengthPnr,
        specialCharacterPnr);
  }

  /**
   * Generate random password string.
   *
   * @return the string
   */
  public String generateRandomString(
      Integer lowerCaseLength,
      Integer upperCaseLength,
      Integer specialCharacterLength,
      Integer numbersLength,
      String specialCharacter) {
    org.passay.PasswordGenerator gen = new org.passay.PasswordGenerator();

    ArrayList<CharacterRule> rules = new ArrayList<>();

    if (lowerCaseLength > 0) {
      CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
      CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
      lowerCaseRule.setNumberOfCharacters(lowerCaseLength);
      rules.add(lowerCaseRule);
    }

    if (upperCaseLength > 0) {
      CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
      CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
      upperCaseRule.setNumberOfCharacters(upperCaseLength);
      rules.add(upperCaseRule);
    }

    if (numbersLength > 0) {
      CharacterData digitChars = EnglishCharacterData.Digit;
      CharacterRule numbersRule = new CharacterRule(digitChars);
      numbersRule.setNumberOfCharacters(numbersLength);
      rules.add(numbersRule);
    }
    if (specialCharacterLength > 0) {
      CharacterData specialChars =
          new CharacterData() {
            public String getErrorCode() {
              return "ERROR_CODE";
            }

            public String getCharacters() {
              return specialCharacter;
            }
          };
      CharacterRule splCharRule = new CharacterRule(specialChars);
      splCharRule.setNumberOfCharacters(specialCharacterLength);
      rules.add(splCharRule);
    }
    return gen.generatePassword(
        upperCaseLength + lowerCaseLength + numbersLength + specialCharacterLength, rules);
  }
}
