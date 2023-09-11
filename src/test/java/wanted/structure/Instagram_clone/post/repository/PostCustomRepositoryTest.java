package wanted.structure.Instagram_clone.post.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.repository.PostCustomRepository;
import wanted.structure.Instagram_clone.api.post.repository.PostRepository;
import wanted.structure.Instagram_clone.global.config.QueryDslConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
public class PostCustomRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCustomRepository postCustomRepository;

    @Test
    void findById() {
        // given
        String mediaKey = "image.png";
        String text = "#인스타그램 첫 게시글 등록 #spring";
        String[] hashTags = {"인스타그램", "spring"};
        Post post = Post.builder().mediaKey(mediaKey).text(text).build();
        post.setHashTags(text);
        Long id = postRepository.save(post).getId();

        // when
        Post result = postCustomRepository.findById(id).get();

        // then
        assertThat(result.getMediaKey()).isEqualTo(mediaKey);
        assertThat(result.getText()).isEqualTo(text);
        assertThat(result.getHashTags()).containsExactly(hashTags);
    }

    @Test
    void findPage() {
        // given
        String mediaKey1 = "image.png";
        String text1 = "#인스타그램 첫 게시글 등록 #spring";
        String[] hashTags1 = {"인스타그램", "spring"};
        Post post1 = Post.builder().mediaKey(mediaKey1).text(text1).build();
        post1.setHashTags(text1);

        String mediaKey2 = "spring.png";
        String text2 = "#dataJpaTest";
        String[] hashTags2 = {"dataJpaTest"};
        Post post2 = Post.builder().mediaKey(mediaKey2).text(text2).build();
        post2.setHashTags(text2);
        postRepository.saveAll(List.of(post1, post2));

        int pageNumber = 0;
        int pageSize = 10;
        int totalSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());

        // when
        Page<Post> postPage = postCustomRepository.findPage(pageable);

        // then
        assertThat(postPage.getSize()).isEqualTo(pageSize);
        assertThat(postPage.getNumber()).isEqualTo(pageNumber);
        assertThat(postPage.getTotalElements()).isEqualTo(totalSize);

        assertThat(postPage.getContent()).anySatisfy(post -> {
            assertThat(post.getMediaKey()).isEqualTo(mediaKey1);
            assertThat(post.getText()).isEqualTo(text1);
            assertThat(post.getHashTags()).containsExactly(hashTags1);
        }).anySatisfy(post -> {
            assertThat(post.getMediaKey()).isEqualTo(mediaKey2);
            assertThat(post.getText()).isEqualTo(text2);
            assertThat(post.getHashTags()).containsExactly(hashTags2);
        });

    }
}
