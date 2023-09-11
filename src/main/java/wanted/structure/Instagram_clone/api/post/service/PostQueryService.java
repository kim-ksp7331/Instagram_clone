package wanted.structure.Instagram_clone.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.structure.Instagram_clone.api.post.dto.response.PostResponse;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.mapper.PostMapper;
import wanted.structure.Instagram_clone.api.post.repository.PostCustomRepository;
import wanted.structure.Instagram_clone.global.exception.ApiException;
import wanted.structure.Instagram_clone.global.exception.ErrorCode;

@Service
@Transactional
@RequiredArgsConstructor
public class PostQueryService {
    private final PostCustomRepository postCustomRepository;
    private final PostMapper postMapper;

    public PostResponse findPost(Long id) {
        return postMapper.entityToDto(findVefifiedPost(id));
    }

    Post findVefifiedPost(Long id) {
        return postCustomRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }
}
