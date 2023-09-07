package wanted.structure.Instagram_clone.global.dto;

import lombok.Getter;

@Getter
public class SingleResult extends EmptyResult {
    private final Object content;

    public SingleResult(Object content) {
        super();
        this.content = content;
    }

    public SingleResult(String message, Object content) {
        super(message);
        this.content = content;
    }
}


