package wanted.structure.Instagram_clone.post.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.mapper.PostMapper;
import wanted.structure.Instagram_clone.api.post.repository.PostCustomRepository;
import wanted.structure.Instagram_clone.api.post.service.PostQueryService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostQueryServiceTest {
    @InjectMocks
    private PostQueryService postQueryService;
    @Mock
    private PostCustomRepository postCustomRepository;
    @Mock
    private PostMapper postMapper;

    @Test
    void findPost() {
        // given

        Long id = 1L;
        Post post = Post.builder().build();
        PostResponse response = PostResponse.builder().build();
        BDDMockito.given(postCustomRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(post));
        BDDMockito.given(postMapper.entityToDto(post)).willReturn(response);

        // when
        PostResponse result = postQueryService.findPost(id);

        // then
        assertThat(result).isEqualTo(response);

    }

    @Test
    void findPostPage() {
        // given
        Long id1 = 1L;
        String mediaUrl1 = "image.png";
        String text1 = "#인스타그램 첫 게시글 등록 #spring";
        List<String> hashTags1 = List.of("인스타그램", "spring");
        PostResponse response1 = PostResponse.builder().id(id1).mediaUrl(mediaUrl1).text(text1).hashTags(hashTags1).build();

        Long id2 = 2L;
        String mediaUrl2 = "spring.png";
        String text2 = "#dataJpaTest";
        List<String> hashTags2 = List.of("dataJpaTest");
        PostResponse response2 = PostResponse.builder().id(id2).mediaUrl(mediaUrl2).text(text2).hashTags(hashTags2).build();

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());
        List<PostResponse> responseList = List.of(response1, response2);
        PageImpl<PostResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

        PageImpl<Post> postPage = new PageImpl<>(List.of());

        BDDMockito.given(postCustomRepository.findPage(Mockito.any(Pageable.class))).willReturn(postPage);
        BDDMockito.given(postMapper.entityPageToDtoPage(postPage)).willReturn(responsePage);

        // when
        Page<PostResponse> result = postQueryService.findPostPage(1, 1);

        // then
        assertThat(result).isEqualTo(responsePage);
    }
}
