package wanted.structure.Instagram_clone.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "옳바른 요청이 아닙니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"인증이 필요합니다"),

    TOKEN_EXPIRE(HttpStatus.UNAUTHORIZED,"토큰 만료"),

    FORBIDDEN(HttpStatus.FORBIDDEN,"접근 권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
