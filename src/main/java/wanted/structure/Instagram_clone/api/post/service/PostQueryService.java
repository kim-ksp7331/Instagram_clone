package wanted.structure.Instagram_clone.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.mapper.PostMapper;
import wanted.structure.Instagram_clone.api.post.repository.PostCustomRepository;
import wanted.structure.Instagram_clone.global.exception.ApiException;
import wanted.structure.Instagram_clone.global.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService {
    private final PostCustomRepository postCustomRepository;
    private final PostMapper postMapper;

    public PostResponse findPost(Long id) {
        return postMapper.entityToDto(findVefifiedPost(id));
    }

    public Page<PostResponse> findPostPage(int page, int size) {
        int firstPage = page - 1;
        Pageable pageable = PageRequest.of(firstPage, size, Sort.by("id").descending());
        Page<Post> postPage = postCustomRepository.findPage(pageable);
        return postMapper.entityPageToDtoPage(postPage);
    }

    Post findVefifiedPost(Long id) {
        return postCustomRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }
}
