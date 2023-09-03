package wanted.structure.Instagram_clone.global.dto;

import lombok.Getter;

@Getter
public class EmptyResult {
    private final String message;

    EmptyResult() {
        this.message = null;
    }

    EmptyResult(String message) {
        this.message = message;
    }
}
