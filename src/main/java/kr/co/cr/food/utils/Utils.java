package kr.co.cr.food.utils;

public class Utils {

  public static Integer doubleToInteger(Double value) {
    if (value == null) return null;
    return value.intValue();
  }
}
