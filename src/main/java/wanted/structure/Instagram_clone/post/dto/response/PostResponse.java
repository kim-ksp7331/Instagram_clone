package wanted.structure.Instagram_clone.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String mediaUrl;
    private String text;
    private List<String> hashTags;
}
