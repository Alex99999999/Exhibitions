package com.my.service;

import com.my.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserServiceTest {

  private static UserService service;

  @BeforeAll
  private static void setup() {
    service = UserService.getInstance();
  }

  @ParameterizedTest
  @ValueSource(strings = {"152", "wersdfsdfqwg", "k", "!@#$%^^&*()", "KHfd125"})
  void validateHashReturnsEncodedPassword(String pass) throws ServiceException {
    String hashed = service.hash(pass);
    Assertions.assertEquals(32, hashed.length());
  }

  @Test
  void getInstanceShouldReturnSameInstance() {
    UserService us = UserService.getInstance();
    Assertions.assertEquals(us, service);
  }
}
