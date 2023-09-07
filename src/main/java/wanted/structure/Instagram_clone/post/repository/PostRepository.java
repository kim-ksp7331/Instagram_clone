package wanted.structure.Instagram_clone.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.structure.Instagram_clone.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
