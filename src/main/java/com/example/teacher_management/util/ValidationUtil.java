package com.example.teacher_management.util;

public class ValidationUtil {
  private ValidationUtil() {
  }

  public static boolean isNullOrZero(Long value) {
    return value == null || value.equals(0L);
  }

  public static boolean isEmptyOrNull(String value){
    return value == null || value.isEmpty();
  }
}
