package wanted.structure.Instagram_clone.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wanted.structure.Instagram_clone.global.dto.EmptyResult;
import wanted.structure.Instagram_clone.global.dto.ResponseDto;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<EmptyResult> handleApiException(ApiException e) {
        return ResponseDto.of(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @Order()
    @ExceptionHandler(Exception.class)
    public ResponseEntity<EmptyResult> handleApiException(Exception e) {
        log.error("unhandled exception: {}", e.getMessage());
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EmptyResult> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage() + "\n");
        }

        log.error(builder.toString());

        return ResponseDto.of(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getMessage());
    }

}
