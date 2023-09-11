package wanted.structure.Instagram_clone.api.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.structure.Instagram_clone.api.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
