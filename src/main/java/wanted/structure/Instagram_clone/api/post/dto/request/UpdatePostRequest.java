package wanted.structure.Instagram_clone.api.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Builder
public class UpdatePostRequest {
    @Setter
    private Long id;
    private MultipartFile file;
    private String text;


}
