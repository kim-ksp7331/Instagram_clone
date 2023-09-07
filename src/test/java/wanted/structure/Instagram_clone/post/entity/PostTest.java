package wanted.structure.Instagram_clone.post.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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