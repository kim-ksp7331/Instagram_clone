package wanted.structure.Instagram_clone.global.dto;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseDto {

    public static ResponseEntity<EmptyResult> of(HttpStatus status) {
        return ResponseEntity.status(status).body(new EmptyResult());
    }
    public static ResponseEntity<EmptyResult> of(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new EmptyResult(message));
    }
    public static ResponseEntity<SingleResult> of(HttpStatus status, Object content) {
        return ResponseEntity.status(status).body(new SingleResult(content));
    }

    public static ResponseEntity<SingleResult> of(HttpStatus status, String message, Object content) {
        return ResponseEntity.status(status).body(new SingleResult(message, content));
    }

    public static ResponseEntity<ListResult> of(HttpStatus status, Slice<Object> slices) {
        return ResponseEntity.status(status).body(new ListResult(slices));
    }

    public static ResponseEntity<ListResult> of(HttpStatus status, String message, Slice<Object> slices) {
        return ResponseEntity.status(status).body(new ListResult(message, slices));
    }

}
