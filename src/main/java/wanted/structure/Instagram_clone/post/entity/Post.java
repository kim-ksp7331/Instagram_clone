package wanted.structure.Instagram_clone.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mediaKey;

    @Builder
    public Post(Long id, String mediaKey) {
        this.id = id;
        this.mediaKey = mediaKey;
    }
}
