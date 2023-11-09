package kr.co.cr.food.exception.errorcodes;

public enum ErrorCode {
  UNAUTHORIZED(401, "인증되지 않은 클라이언트"),
  BAD_REQUEST(400, "잘못된 접근"),
  NOT_FOUND(404, "해당 정보가 존재하지 않습니다."),
  ;


  public final Integer code;
  public final String value;

  ErrorCode(Integer code, String value) {
    this.code = code;
    this.value = value;
  }
}
