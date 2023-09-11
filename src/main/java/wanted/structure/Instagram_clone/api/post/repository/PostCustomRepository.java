package wanted.structure.Instagram_clone.api.post.repository;


import wanted.structure.Instagram_clone.api.post.entity.Post;

import java.util.Optional;

public interface PostCustomRepository {
    Optional<Post> findById(Long id);
}
