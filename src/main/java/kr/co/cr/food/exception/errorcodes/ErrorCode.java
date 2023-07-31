package kr.co.cr.food.exception.errorcodes;

public enum ErrorCode {
  BAD_REQUEST(400, "잘못된 접근");


  private final Integer code;
  private final String value;

  ErrorCode(Integer code, String value) {
    this.code = code;
    this.value = value;
  }
}
