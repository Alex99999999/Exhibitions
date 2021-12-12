package com.my.service;

import com.my.exception.ServiceException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class UserService {

  private static final Logger LOG = Logger.getLogger(UserService.class);
  private static UserService instance;

  private UserService() {
  }

  public static UserService getInstance() {
    if (instance == null) {
      instance = new UserService();
    }
    return instance;
  }

  public String hash(String input) throws ServiceException {
    String errorMes;
    MessageDigest digest;
    String algorithm = "MD5";
    try {
      digest = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      errorMes = "No such hashing algorithm found";
      LOG.error(errorMes);
      throw new ServiceException(errorMes);
    }
    digest.update(input.getBytes());
    byte[] hash = digest.digest();
    return getHexadecimalString(hash);
  }

  private static String getHexadecimalString(byte[] hash) {
    StringBuilder sb = new StringBuilder();
    for (byte b : hash) {
      String hexInt = Integer.toHexString(b & 0xff);
      if (hexInt.length() == 1) {
        hexInt = String.format("0%s", hexInt);
        sb.append(hexInt);
      } else {
        sb.append(hexInt);
      }
    }
    return sb.toString().toUpperCase();
  }
}
