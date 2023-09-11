package wanted.structure.Instagram_clone.api.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Builder
public class CreatePostRequest {
    private MultipartFile file;
    private String text;
}