package wanted.structure.Instagram_clone.global.dto;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class ListResult<T> extends EmptyResult {
    private final List<T> content;
    private final boolean isLast;
    private final int size;
    private final int number;

    public ListResult(Slice<T> slices) {
        super();
        this.content = slices.getContent();
        this.isLast = slices.isLast();
        this.size = slices.getSize();
        this.number = slices.getNumber();
    }

    public ListResult(String message, Slice<T> slices) {
        super(message);
        this.content = slices.getContent();
        this.isLast = slices.isLast();
        this.size = slices.getSize();
        this.number = slices.getNumber();
    }
}
