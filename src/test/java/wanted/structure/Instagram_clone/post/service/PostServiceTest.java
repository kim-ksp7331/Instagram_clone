package wanted.structure.Instagram_clone.post.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import wanted.structure.Instagram_clone.api.post.service.PostService;
import wanted.structure.Instagram_clone.global.media.StorageService;
import wanted.structure.Instagram_clone.api.post.dto.request.CreatePostRequest;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.mapper.PostMapper;
import wanted.structure.Instagram_clone.api.post.repository.PostRepository;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private StorageService storageService;
    @Mock
    private PostMapper postMapper;
    @Test
    void createPost() {
        // given
        String name = "image";
        MockMultipartFile file = new MockMultipartFile(name, new byte[]{});
        Long id = 3L;
        String mediaUrl = "/image.png";
        String text = "abcd";

        CreatePostRequest request = CreatePostRequest.builder().file(file).text(text).build();
        PostResponse response = PostResponse.builder().id(id).mediaUrl(mediaUrl).text(text).build();

        BDDMockito.given(storageService.store(Mockito.any(MultipartFile.class), Mockito.anyString())).willReturn("");
        BDDMockito.given(postRepository.save(Mockito.any(Post.class))).willReturn(Post.builder().build());
        BDDMockito.given(postMapper.entityToDto(Mockito.any(Post.class))).willReturn(response);

        // when
        PostResponse result = postService.createPost(request);

        // then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getMediaUrl()).isEqualTo(mediaUrl);
        assertThat(result.getText()).isEqualTo(text);

    }
}