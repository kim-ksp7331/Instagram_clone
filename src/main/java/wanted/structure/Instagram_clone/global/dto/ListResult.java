package wanted.structure.Instagram_clone.global.dto;

import org.springframework.data.domain.Slice;

import java.util.List;

public class ListResult extends EmptyResult {
    private final List<Object> content;
    private final boolean isLast;
    private final int size;
    private final int number;

    public ListResult(Slice<Object> slices) {
        super();
        this.content = slices.getContent();
        this.isLast = slices.isLast();
        this.size = slices.getSize();
        this.number = slices.getNumber();
    }

    public ListResult(String message, Slice<Object> slices) {
        super(message);
        this.content = slices.getContent();
        this.isLast = slices.isLast();
        this.size = slices.getSize();
        this.number = slices.getNumber();
    }
}
