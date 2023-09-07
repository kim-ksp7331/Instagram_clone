package wanted.structure.Instagram_clone.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.global.media.StorageService;
import wanted.structure.Instagram_clone.post.dto.request.CreatePostRequest;
import wanted.structure.Instagram_clone.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.post.entity.Post;
import wanted.structure.Instagram_clone.post.mapper.PostMapper;
import wanted.structure.Instagram_clone.post.repository.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final StorageService storageService;
    private final PostMapper postMapper;

    public PostResponse createPost(CreatePostRequest request) {
        String key = storageService.store(request.getFile(), "post");
        Post post = Post.builder().mediaKey(key).build();
        return postMapper.entityToDto(postRepository.save(post));
    }
}
