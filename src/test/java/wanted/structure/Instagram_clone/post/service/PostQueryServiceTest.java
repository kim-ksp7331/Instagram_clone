package wanted.structure.Instagram_clone.post.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.mapper.PostMapper;
import wanted.structure.Instagram_clone.api.post.repository.PostCustomRepository;
import wanted.structure.Instagram_clone.api.post.service.PostQueryService;

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
}
