package wanted.structure.Instagram_clone.post.entity;

import org.junit.jupiter.api.Test;
import wanted.structure.Instagram_clone.api.post.entity.Post;

import static org.assertj.core.api.Assertions.*;

class PostTest {

    @Test
    void setHashTags() {
        // given
        String text = "#인스타그램 첫 게시글 등록 #spring";
        Post post = Post.builder().text(text).build();

        // when
        post.setHashTags(text);

        // then
        assertThat(post.getHashTags()).contains("인스타그램");
        assertThat(post.getHashTags()).contains("spring");
    }
}