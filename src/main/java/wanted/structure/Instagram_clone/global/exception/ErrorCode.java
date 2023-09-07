package wanted.structure.Instagram_clone.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "옳바른 요청이 아닙니다."),

    CONFLICT(HttpStatus.CONFLICT,"요청이 현재 서버의 상태와 충돌합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
