package wanted.structure.Instagram_clone.post.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mediaKey;
    @Column(columnDefinition = "TEXT")
    private String text;
    @ElementCollection
    private List<String> hashTags = new ArrayList<>();

    @Builder
    public Post(Long id, String mediaKey, String text) {
        this.id = id;
        this.mediaKey = mediaKey;
        this.text = text;
    }

    public void setHashTags(String text) {
        if(text == null) return;
        hashTags.clear();
        String hashTag = "";
        boolean isHash = false;
        for (char c : text.toCharArray()) {
            if(isHash) {
                if(c == ' '){
                    addHashTag(hashTag);
                    isHash = false;
                    hashTag = "";
                    continue;
                }
                hashTag += c;
            }
            if(c == '#') isHash = true;
        }
        if(hashTag.length() > 0) hashTags.add(hashTag);
    }
    private void addHashTag(String hashTag) {
        hashTags.add(hashTag);
    }
}
