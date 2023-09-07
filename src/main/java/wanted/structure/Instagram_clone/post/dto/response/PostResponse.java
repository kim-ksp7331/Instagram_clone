package wanted.structure.Instagram_clone.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String mediaUrl;
}
