package wanted.structure.Instagram_clone.api.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.structure.Instagram_clone.api.post.entity.Post;
import wanted.structure.Instagram_clone.api.post.entity.QPost;

import java.util.Optional;

import static wanted.structure.Instagram_clone.api.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Post> findById(Long id) {
        return jpaQueryFactory.selectFrom(post).where(post.id.eq(id)).stream().findAny();
    }
}
