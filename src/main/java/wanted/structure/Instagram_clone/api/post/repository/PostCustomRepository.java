package wanted.structure.Instagram_clone.api.post.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.structure.Instagram_clone.api.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {
    Optional<Post> findById(Long id);

    Page<Post> findPage(Pageable pageable);
}
