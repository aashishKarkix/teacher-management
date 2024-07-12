package com.example.teacher_management.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

  @Test
  void isNullOrZero_nullValue() {
    Long value = null;
    assertTrue(ValidationUtil.isNullOrZero(value));
  }

  @Test
  void isNullOrZero_zeroValue() {
    Long value = 0L;
    assertTrue(ValidationUtil.isNullOrZero(value));
  }

  @Test
  void isNullOrZero_nonZeroValue() {
    Long value = 1L;
    assertFalse(ValidationUtil.isNullOrZero(value));
  }

  @Test
  void isEmptyOrNull_nullString() {
    String value = null;
    assertTrue(ValidationUtil.isEmptyOrNull(value));
  }

  @Test
  void isEmptyOrNull_emptyString() {
    String value = "";
    assertTrue(ValidationUtil.isEmptyOrNull(value));
  }

  @Test
  void isEmptyOrNull_nonEmptyString() {
    String value = "Aashish";
    assertFalse(ValidationUtil.isEmptyOrNull(value));
  }
}

